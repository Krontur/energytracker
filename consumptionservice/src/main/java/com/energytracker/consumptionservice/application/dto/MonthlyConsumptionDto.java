package com.energytracker.consumptionservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyConsumptionDto {

    private Long meteringPointId;
    private int month;
    private int year;
    private double consumption;

}
