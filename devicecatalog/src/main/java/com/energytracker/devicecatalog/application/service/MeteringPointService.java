package com.energytracker.devicecatalog.application.service;

import com.energytracker.devicecatalog.application.dto.meteringpoint.CreateMeteringPointRequestDto;
import com.energytracker.devicecatalog.application.dto.meteringpoint.MeteringPointResponseDto;
import com.energytracker.devicecatalog.application.mapper.EnergyMeterMapper;
import com.energytracker.devicecatalog.application.mapper.MeteringPointMapper;
import com.energytracker.devicecatalog.application.port.inbound.meteringpoint.CreateMeteringPointUseCase;
import com.energytracker.devicecatalog.application.port.inbound.meteringpoint.GetAllMeteringPointsUseCase;
import com.energytracker.devicecatalog.application.port.outbound.MeteringPointRepositoryPort;
import com.energytracker.devicecatalog.domain.model.DeviceStatus;
import com.energytracker.devicecatalog.domain.model.MeteringPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeteringPointService implements GetAllMeteringPointsUseCase, CreateMeteringPointUseCase {

    private final MeteringPointRepositoryPort meteringPointRepositoryPort;
    private final StationService stationService;
    private final EnergyMeterService energyMeterService;

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
    public MeteringPointResponseDto createMeteringPoint(CreateMeteringPointRequestDto createMeteringPointRequestDto) {
        MeteringPoint meteringPoint = MeteringPointMapper.createMeteringPointRequestDtoToDomain(createMeteringPointRequestDto);
        meteringPoint.setEnergyMeter(EnergyMeterMapper.energyMeterResponseDtoToDomain(energyMeterService.getEnergyMeterById(
                createMeteringPointRequestDto.getEnergyMeterId())));
        meteringPoint.getEnergyMeter().setDeviceStatus(DeviceStatus.INSTALLED);
        meteringPoint.setChannel(stationService.getChannelById(createMeteringPointRequestDto.getChannelId()));
        meteringPoint.getChannel().setLonIsActive(true);
        MeteringPoint createdMeteringPoint = meteringPointRepositoryPort.createMeteringPoint(meteringPoint);
        return MeteringPointMapper.meteringPointDomainToResponseDto(createdMeteringPoint);
    }
}
