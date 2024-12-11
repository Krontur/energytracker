package com.energytracker.consumptionservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionDto {

    public ConsumptionDto (Long meteringPointId, double consumptionValue, LocalDateTime consumptionTimestamp) {
        this.meteringPointId = meteringPointId;
        this.consumptionValue = consumptionValue;
        this.consumptionTimestamp = consumptionTimestamp;
    }

    private Long consumptionId;
    private Long meteringPointId;
    private double consumptionValue;
    private LocalDateTime consumptionTimestamp;

}
