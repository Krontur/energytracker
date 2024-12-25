package com.energytracker.consumptionservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YearlyConsumptionDto {

    private Long meteringPointId;
    private int year;
    private double consumption;

}
