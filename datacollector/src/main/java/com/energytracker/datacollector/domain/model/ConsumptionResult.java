package com.energytracker.datacollector.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ConsumptionResult {

    private MeteringPoint meteringPoint;
    private LocalDateTime timestamp;
    private double consumption;

}
