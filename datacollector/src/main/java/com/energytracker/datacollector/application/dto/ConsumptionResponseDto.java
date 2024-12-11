package com.energytracker.datacollector.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ConsumptionResponseDto {

    private Long meteringPointId;
    private double consumptionValue;
    private LocalDateTime consumptionTimestamp;

}
