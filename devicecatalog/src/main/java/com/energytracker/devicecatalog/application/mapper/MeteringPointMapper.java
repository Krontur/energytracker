package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.MeteringPointResponseDto;
import com.energytracker.devicecatalog.domain.model.MeteringPoint;

public class MeteringPointMapper {


    public static MeteringPointResponseDto meteringPointDomainToResponseDto(MeteringPoint meteringPoint) {
        return new MeteringPointResponseDto(
                meteringPoint.getMeteringPointId(),
                meteringPoint.getCreatedAt(),
                meteringPoint.getUpdatedAt(),
                meteringPoint.getParentMeteringPoint() == null ? null : MeteringPointMapper.meteringPointDomainToResponseDto(meteringPoint.getParentMeteringPoint()),
                EnergyMeterMapper.energyMeterDomainToResponseDto(meteringPoint.getEnergyMeter()),
                StationMapper.channelDomainToDto(meteringPoint.getChannel()),
                meteringPoint.getLocationName(),
                meteringPoint.getConnectionDescription(),
                meteringPoint.getActiveStatus()
        );
    }
}
