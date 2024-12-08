package com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.consumptionservice.domain.model.Consumption;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.entity.ConsumptionEntity;

public class ConsumptionPersistenceMapper {

    public static Consumption consumptionEntityToDomain(ConsumptionEntity consumptionEntity) {
        Consumption consumption = new Consumption();
        consumption.setConsumptionId(consumptionEntity.getConsumptionId());
        consumption.setMeteringPointId(consumptionEntity.getMeteringPointId());
        consumption.setConsumptionValue(consumptionEntity.getConsumptionValue());
        consumption.setConsumptionTimestamp(consumptionEntity.getConsumptionTimestamp());
        return consumption;
    }

    public static ConsumptionEntity consumptionDomainToEntity(Consumption consumption) {
        ConsumptionEntity consumptionEntity = new ConsumptionEntity();
        consumptionEntity.setConsumptionId(consumption.getConsumptionId());
        consumptionEntity.setMeteringPointId(consumption.getMeteringPointId());
        consumptionEntity.setConsumptionValue(consumption.getConsumptionValue());
        consumptionEntity.setConsumptionTimestamp(consumption.getConsumptionTimestamp());
        return consumptionEntity;
    }
}
