package com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.entity.MeteringPointEntity;

public class MeteringPointPersistenceMapper {


    public static MeteringPointEntity meteringPointDomainToEntity(MeteringPoint meteringPoint) {
        MeteringPointEntity meteringPointEntity = new MeteringPointEntity();
        meteringPointEntity.setMeteringPointId(meteringPoint.getMeteringPointId());
        meteringPointEntity.setStationTag(meteringPoint.getStationTag());
        meteringPointEntity.setChannelNumber(meteringPoint.getChannelNumber());
        return meteringPointEntity;
    }


    public static MeteringPoint meteringPointEntityToDomain(MeteringPointEntity meteringPointEntity) {
        MeteringPoint meteringPoint = new MeteringPoint();
        meteringPoint.setMeteringPointId(meteringPointEntity.getMeteringPointId());
        meteringPoint.setStationTag(meteringPointEntity.getStationTag());
        meteringPoint.setChannelNumber(meteringPointEntity.getChannelNumber());
        return meteringPoint;
    }
}
