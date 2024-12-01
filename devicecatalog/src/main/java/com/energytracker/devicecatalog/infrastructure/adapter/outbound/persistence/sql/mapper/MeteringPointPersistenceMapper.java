package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.application.mapper.EnergyMeterMapper;
import com.energytracker.devicecatalog.application.mapper.MeteringPointMapper;
import com.energytracker.devicecatalog.domain.model.MeteringPoint;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.MeteringPointEntity;

public class MeteringPointPersistenceMapper {


    public static MeteringPoint meteringPointEntityToDomain(MeteringPointEntity meteringPointEntity) {
        return new MeteringPoint(
                meteringPointEntity.getId(),
                meteringPointEntity.getCreatedAt(),
                meteringPointEntity.getUpdatedAt(),
                meteringPointEntity.getParentMeteringPointId(),
                EnergyMeterPersistenceMapper.energyMeterEntityToDomain(meteringPointEntity.getEnergyMeterEntity()),
                ChannelPersistenceMapper.channelEntityToDomain(meteringPointEntity.getChannelEntity()),
                meteringPointEntity.getLocationName(),
                meteringPointEntity.getConnectionDescription(),
                meteringPointEntity.getActiveStatus()
        );
    }

    public static MeteringPointEntity meteringPointDomainToEntity(MeteringPoint meteringPoint) {
        return new MeteringPointEntity(
                meteringPoint.getParentMeteringPointId(),
                EnergyMeterPersistenceMapper.energyMeterDomainToEntity(meteringPoint.getEnergyMeter()),
                ChannelPersistenceMapper.channelDomainToEntity(meteringPoint.getChannel()),
                meteringPoint.getLocationName(),
                meteringPoint.getConnectionDescription(),
                meteringPoint.getActiveStatus()
        );
    }
}
