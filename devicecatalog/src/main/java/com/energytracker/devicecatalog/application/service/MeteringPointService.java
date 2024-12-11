package com.energytracker.devicecatalog.application.service;

import com.energytracker.devicecatalog.application.dto.meteringpoint.CreateMeteringPointRequestDto;
import com.energytracker.devicecatalog.application.dto.meteringpoint.MeteringPointResponseDto;
import com.energytracker.devicecatalog.application.dto.meteringpoint.MeteringPointResponseToConsumptionServiceDto;
import com.energytracker.devicecatalog.application.mapper.ChannelMapper;
import com.energytracker.devicecatalog.application.mapper.EnergyMeterMapper;
import com.energytracker.devicecatalog.application.mapper.MeteringPointMapper;
import com.energytracker.devicecatalog.application.port.inbound.meteringpoint.CreateMeteringPointUseCase;
import com.energytracker.devicecatalog.application.port.inbound.meteringpoint.GetAllMeteringPointsUseCase;
import com.energytracker.devicecatalog.application.port.outbound.MeteringPointRepositoryPort;
import com.energytracker.devicecatalog.application.port.outbound.QueueMessagingPort;
import com.energytracker.devicecatalog.domain.model.DeviceStatus;
import com.energytracker.devicecatalog.domain.model.MeteringPoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class MeteringPointService implements GetAllMeteringPointsUseCase, CreateMeteringPointUseCase {

    private final MeteringPointRepositoryPort meteringPointRepositoryPort;
    private final StationService stationService;
    private final EnergyMeterService energyMeterService;
    private final ChannelService channelService;
    private final QueueMessagingPort queueMessagingPort;
    private final ObjectMapper objectMapper;
    private final Binding newMeteringPointBinding;

    public List<MeteringPointResponseDto> getAllMeteringPoints() {
        List<MeteringPoint> meteringPoints = meteringPointRepositoryPort.getAllMeteringPoints();
        List<MeteringPointResponseDto> meteringPointResponseDtos = new ArrayList<>();
        if (meteringPoints == null || meteringPoints.isEmpty()) {
            return meteringPointResponseDtos;
        }
        meteringPoints.forEach(meteringPoint -> {
            meteringPointResponseDtos.add(
                    MeteringPointMapper.meteringPointDomainToResponseDto(meteringPoint)
                    );
        });
        return meteringPointResponseDtos;
    }

    @Override
    @Transactional
    public MeteringPointResponseDto createMeteringPoint(CreateMeteringPointRequestDto createMeteringPointRequestDto) {

        MeteringPoint meteringPoint = MeteringPointMapper.createMeteringPointRequestDtoToDomain(createMeteringPointRequestDto);
        meteringPoint.setEnergyMeter(EnergyMeterMapper.energyMeterResponseDtoToDomain(energyMeterService.getEnergyMeterById(
                createMeteringPointRequestDto.getEnergyMeterId())));

        meteringPoint.getEnergyMeter().setDeviceStatus(DeviceStatus.INSTALLED);
        energyMeterService.updateEnergyMeter(EnergyMeterMapper.energyMeterDomainToResponseDto(meteringPoint.getEnergyMeter()));

        meteringPoint.setChannel(stationService.getChannelById(createMeteringPointRequestDto.getChannelId()));
        meteringPoint.getChannel().setLonIsActive(true);
        channelService.updateChannel(ChannelMapper.channelDomainToDto(meteringPoint.getChannel()));

        MeteringPoint createdMeteringPoint = meteringPointRepositoryPort.createMeteringPoint(meteringPoint);

        MeteringPointResponseToConsumptionServiceDto meteringPointResponseToConsumptionServiceDto =
                new MeteringPointResponseToConsumptionServiceDto(
                        createdMeteringPoint.getMeteringPointId(),
                        stationService.getStationById(createdMeteringPoint.getChannel().getStationId()).getStationTag(),
                        createdMeteringPoint.getChannel().getChannelNumber()
                );

        sendMeteringPointMessageToQueue(meteringPointResponseToConsumptionServiceDto);

        return MeteringPointMapper.meteringPointDomainToResponseDto(createdMeteringPoint);
    }

    private void sendMeteringPointMessageToQueue(MeteringPointResponseToConsumptionServiceDto messageDto) {
        try {
            String message = objectMapper.writeValueAsString(messageDto);
            log.info("Message to send: {}", message);
            Message rabbitMessage = MessageBuilder
                    .withBody(message.getBytes(StandardCharsets.UTF_8))
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();
            queueMessagingPort.sendMessage(newMeteringPointBinding.getExchange(), newMeteringPointBinding.getRoutingKey(), rabbitMessage);
        } catch (JsonProcessingException e) {
            log.error("Error serializing metering point message: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to serialize message for RabbitMQ", e);
        }
    }
}
