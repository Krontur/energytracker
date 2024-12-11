package com.energytracker.datacollector.application.mapper;

import com.energytracker.datacollector.application.dto.ConsumptionResponseDto;
import com.energytracker.datacollector.domain.model.ConsumptionResult;

public class ConsumptionMapper {
    public static ConsumptionResponseDto consumptionResultDomainToResponseDto(ConsumptionResult consumptionResult) {

        if (consumptionResult != null){
            return new ConsumptionResponseDto(
                    consumptionResult.getMeteringPoint().getMeteringPointId(),
                    consumptionResult.getConsumption(),
                    consumptionResult.getTimestamp()
            );
        }
        return null;
    }
}
