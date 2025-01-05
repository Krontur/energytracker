package com.energytracker.devicecatalog.application.service;

import com.energytracker.devicecatalog.application.dto.meteringpoint.CreateMeteringPointRequestDto;
import com.energytracker.devicecatalog.application.dto.meteringpoint.MeteringPointResponseDto;
import com.energytracker.devicecatalog.application.dto.meteringpoint.MeteringPointResponseToConsumptionServiceDto;
import com.energytracker.devicecatalog.application.dto.station.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.mapper.ChannelMapper;
import com.energytracker.devicecatalog.application.mapper.EnergyMeterMapper;
import com.energytracker.devicecatalog.application.mapper.MeteringPointMapper;
import com.energytracker.devicecatalog.application.port.inbound.meteringpoint.CreateMeteringPointUseCase;
import com.energytracker.devicecatalog.application.port.inbound.meteringpoint.GetAllMeteringPointsUseCase;
import com.energytracker.devicecatalog.application.port.inbound.meteringpoint.GetMeteringPointByIdUseCase;
import com.energytracker.devicecatalog.application.port.inbound.meteringpoint.UpdateMeteringPointByIdUseCase;
import com.energytracker.devicecatalog.application.port.outbound.MeteringPointRepositoryPort;
import com.energytracker.devicecatalog.application.port.outbound.QueueMessagingPort;
import com.energytracker.devicecatalog.domain.model.DeviceStatus;
import com.energytracker.devicecatalog.domain.model.MeteringPoint;
import com.energytracker.devicecatalog.domain.model.station.Station;
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

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class MeteringPointService implements GetAllMeteringPointsUseCase, CreateMeteringPointUseCase, GetMeteringPointByIdUseCase, UpdateMeteringPointByIdUseCase {

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
        energyMeterService.updateEnergyMeter(meteringPoint.getEnergyMeter().getDeviceId(), EnergyMeterMapper.energyMeterDomainToCreateRequestDto(meteringPoint.getEnergyMeter()));
        log.info("Energy meter status updated to INSTALLED for metering point: {}", meteringPoint.getMeteringPointId());
        log.info("Energy meter status: {}", meteringPoint.getEnergyMeter().getDeviceStatus());
        meteringPoint.setChannel(stationService.getChannelById(createMeteringPointRequestDto.getChannelId()));
        meteringPoint.getChannel().setLonIsActive(true);
        channelService.updateChannel(ChannelMapper.channelDomainToDto(meteringPoint.getChannel()));

        MeteringPoint createdMeteringPoint = meteringPointRepositoryPort.createMeteringPoint(meteringPoint);

        MeteringPointResponseToConsumptionServiceDto meteringPointResponseToConsumptionServiceDto =
                new MeteringPointResponseToConsumptionServiceDto(
                        "ADD",
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

    @Override
    public MeteringPointResponseDto getMeteringPointById(Long meteringPointId) {
        try {
            return MeteringPointMapper.meteringPointDomainToResponseDto(meteringPointRepositoryPort.getMeteringPointById(meteringPointId));
        } catch (Exception e) {
            log.error("Error getting metering point by id: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to get metering point by id", e);
        }
    }

    @Override
    public MeteringPointResponseDto updateMeteringPointById(Long meteringPointId, CreateMeteringPointRequestDto createMeteringPointRequestDto) {
        try {

            MeteringPoint meteringPoint = meteringPointRepositoryPort.getMeteringPointById(meteringPointId);
            MeteringPoint checkedMeteringPoint = checkFields(createMeteringPointRequestDto, meteringPoint);
            MeteringPoint updatedMeteringPoint = meteringPointRepositoryPort.updateMeteringPointById(meteringPointId, checkedMeteringPoint);
            if(!updatedMeteringPoint.getActiveStatus()) {
                MeteringPointResponseToConsumptionServiceDto meteringPointResponseToConsumptionServiceDto =
                        new MeteringPointResponseToConsumptionServiceDto(
                                "DELETE",
                                updatedMeteringPoint.getMeteringPointId(),
                                stationService.getStationById(updatedMeteringPoint.getChannel().getStationId()).getStationTag(),
                                updatedMeteringPoint.getChannel().getChannelNumber()
                        );

                sendMeteringPointMessageToQueue(meteringPointResponseToConsumptionServiceDto);
            }
            return MeteringPointMapper.meteringPointDomainToResponseDto(updatedMeteringPoint);
        } catch (Exception e) {
            log.error("Error updating metering point by id: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to update metering point by id", e);
        }
    }

    private MeteringPoint checkFields(CreateMeteringPointRequestDto createMeteringPointRequestDto, MeteringPoint meteringPoint) {
        Field[] fields = createMeteringPointRequestDto.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object dtoValue = field.get(createMeteringPointRequestDto);

                if (dtoValue == null) {
                    log.info("{} is null", field.getName());
                } else if (dtoValue instanceof String && ((String) dtoValue).trim().isEmpty()) {
                    log.info("{} is empty", field.getName());
                } else {
                    Field entityField = getFieldFromClassHierarchy(meteringPoint.getClass(), field.getName());
                    if (entityField != null) {
                        entityField.setAccessible(true);

                        Object convertedValue = convertValue(dtoValue, entityField.getType());
                        Object entityValue = entityField.get(meteringPoint);

                        if (!convertedValue.equals(entityValue)) {
                            log.info("{} has changed", field.getName());
                            entityField.set(meteringPoint, convertedValue);
                        }
                    } else {
                        log.warn("Field {} does not exist in Station or its superclasses", field.getName());
                    }
                }
            } catch (IllegalAccessException e) {
                log.error("Error accessing fields", e);
                throw new RuntimeException("Error accessing fields", e);
            }
        }
        return meteringPoint;
    }


    private Field getFieldFromClassHierarchy(Class<?> clazz, String fieldName) {
        Map<String, String> FIELD_NAME_MAP = new HashMap<>();
        FIELD_NAME_MAP.put("stationId", "deviceId");

        String entityFieldName = FIELD_NAME_MAP.getOrDefault(fieldName, fieldName);

        while (clazz != null) {
            try {
                return clazz.getDeclaredField(entityFieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    private Object convertValue(Object value, Class<?> targetType) {
        if (value == null || targetType.isInstance(value)) {
            return value;
        }

        if (targetType.isEnum() && value instanceof String) {
            return Enum.valueOf((Class<Enum>) targetType, (String) value);
        }

        if (targetType == Integer.class || targetType == int.class) {
            return Integer.valueOf(value.toString());
        }

        if (targetType == Long.class || targetType == long.class) {
            return Long.valueOf(value.toString());
        }

        if (targetType == Double.class || targetType == double.class) {
            return Double.valueOf(value.toString());
        }

        if (targetType == Boolean.class || targetType == boolean.class) {
            return Boolean.valueOf(value.toString());
        }

        throw new IllegalArgumentException("Cannot convert value " + value + " to type " + targetType);
    }
}
