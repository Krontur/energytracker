package com.energytracker.consumptionservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consumption {

    private Long consumptionId;
    private Long meteringPointId;
    private double consumptionValue;
    private LocalDateTime consumptionTimestamp;

}
