package com.energytracker.consumptionservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
public class DailyConsumptionDto {

    public DailyConsumptionDto(Long meteringPointId, Date startDate, Date endDate, double consumptionValue) {
        this.meteringPointId = meteringPointId;
        this.startDate = startDate.toLocalDate();
        this.endDate = endDate.toLocalDate();
        this.consumptionValue = consumptionValue;
    }

    private Long meteringPointId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double consumptionValue;

}
