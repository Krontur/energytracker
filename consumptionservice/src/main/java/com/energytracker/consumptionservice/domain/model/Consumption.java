package com.energytracker.consumptionservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consumption {

    public Consumption (Long meteringPointId, double consumptionValue, LocalDateTime consumptionTimestamp) {
        this.meteringPointId = meteringPointId;
        this.consumptionValue = consumptionValue;
        this.consumptionTimestamp = consumptionTimestamp;
    }

    private Long consumptionId;
    private Long meteringPointId;
    private double consumptionValue;
    private LocalDateTime consumptionTimestamp;

}
