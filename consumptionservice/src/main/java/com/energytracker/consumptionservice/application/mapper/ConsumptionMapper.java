package com.energytracker.consumptionservice.application.mapper;

import com.energytracker.consumptionservice.application.dto.ConsumptionDto;
import com.energytracker.consumptionservice.domain.model.Consumption;

public class ConsumptionMapper {
    public static ConsumptionDto consumptionDomainToDto(Consumption consumption) {
        ConsumptionDto consumptionDto = new ConsumptionDto();
        consumptionDto.setConsumptionId(consumption.getConsumptionId());
        consumptionDto.setMeteringPointId(consumption.getMeteringPointId());
        consumptionDto.setConsumptionValue(consumption.getConsumptionValue());
        consumptionDto.setConsumptionTimestamp(consumption.getConsumptionTimestamp());
        return consumptionDto;
    }
}
