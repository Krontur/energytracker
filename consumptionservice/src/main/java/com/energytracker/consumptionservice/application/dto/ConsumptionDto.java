package com.energytracker.consumptionservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionDto {

    private Long consumptionId;
    private Long meteringPointId;
    private double consumptionValue;
    private LocalDateTime consumptionTimestamp;

}
