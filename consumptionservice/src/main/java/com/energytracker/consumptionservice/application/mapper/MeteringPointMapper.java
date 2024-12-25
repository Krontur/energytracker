package com.energytracker.consumptionservice.application.mapper;

import com.energytracker.consumptionservice.application.dto.MeteringPointDto;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;

public class MeteringPointMapper {
    public static MeteringPointDto meteringPointDomainToDto(MeteringPoint meteringPoint) {

        return new MeteringPointDto(
                meteringPoint.getActionType().name(),
                meteringPoint.getMeteringPointId(),
                meteringPoint.getChannelNumber(),
                meteringPoint.getStationTag());

    }
}
