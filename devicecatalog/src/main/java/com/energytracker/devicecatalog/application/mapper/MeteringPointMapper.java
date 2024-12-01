package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.meteringpoint.CreateMeteringPointRequestDto;
import com.energytracker.devicecatalog.application.dto.meteringpoint.MeteringPointResponseDto;
import com.energytracker.devicecatalog.domain.model.MeteringPoint;

public class MeteringPointMapper {


    public static MeteringPointResponseDto meteringPointDomainToResponseDto(MeteringPoint meteringPoint) {
        return new MeteringPointResponseDto(
                meteringPoint.getMeteringPointId(),
                meteringPoint.getCreatedAt(),
                meteringPoint.getUpdatedAt(),
                meteringPoint.getParentMeteringPointId(),
                EnergyMeterMapper.energyMeterDomainToResponseDto(meteringPoint.getEnergyMeter()),
                StationMapper.channelDomainToDto(meteringPoint.getChannel()),
                meteringPoint.getLocationName(),
                meteringPoint.getConnectionDescription(),
                meteringPoint.getActiveStatus()
        );
    }

    public static MeteringPoint createMeteringPointRequestDtoToDomain(CreateMeteringPointRequestDto createMeteringPointRequestDto) {

        return new MeteringPoint(
                createMeteringPointRequestDto.getLocationName(),
                createMeteringPointRequestDto.getConnectionDescription(),
                createMeteringPointRequestDto.getParentMeteringPointId(),
                createMeteringPointRequestDto.getEnergyMeterId(),
                createMeteringPointRequestDto.getChannelId(),
                createMeteringPointRequestDto.getActiveStatus()
        );
    }
}
