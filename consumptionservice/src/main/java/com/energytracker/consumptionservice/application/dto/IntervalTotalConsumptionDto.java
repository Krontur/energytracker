package com.energytracker.consumptionservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class IntervalTotalConsumptionDto {

    private Long meteringPointId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private double consumption;

}
