package com.energytracker.consumptionservice.application.mapper;

import com.energytracker.consumptionservice.application.dto.ConsumptionDto;
import com.energytracker.consumptionservice.application.dto.DailyConsumptionDto;
import com.energytracker.consumptionservice.application.dto.MonthlyConsumptionDto;
import com.energytracker.consumptionservice.application.dto.YearlyConsumptionDto;
import com.energytracker.consumptionservice.domain.model.Consumption;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ConsumptionMapper {
    public static ConsumptionDto consumptionDomainToDto(Consumption consumption) {
        ConsumptionDto consumptionDto = new ConsumptionDto();
        consumptionDto.setConsumptionId(consumption.getConsumptionId());
        consumptionDto.setMeteringPointId(consumption.getMeteringPointId());
        consumptionDto.setConsumptionValue(consumption.getConsumptionValue());
        consumptionDto.setConsumptionTimestamp(consumption.getConsumptionTimestamp());
        return consumptionDto;
    }

    public static Consumption consumptionDailyDtoToDomain(DailyConsumptionDto consumptionDto) {
        Consumption consumption = new Consumption();
        consumption.setMeteringPointId(consumptionDto.getMeteringPointId());
        consumption.setConsumptionValue(consumptionDto.getConsumptionValue());
        consumption.setConsumptionTimestamp(consumptionDto.getStartDate().atStartOfDay());
        return consumption;
    }

    public static Consumption consumptionMonthlyDtoToDomain(MonthlyConsumptionDto monthlyConsumptionDto) {
        Consumption consumption = new Consumption();
        consumption.setMeteringPointId(monthlyConsumptionDto.getMeteringPointId());
        consumption.setConsumptionValue(monthlyConsumptionDto.getConsumption());
        consumption.setConsumptionTimestamp(LocalDateTime.of(LocalDate.of(monthlyConsumptionDto.getYear(), monthlyConsumptionDto.getMonth(), 1), LocalTime.of(0,0,0)));
        return consumption;
    }


    public static Consumption consumptionYearlyDtoToDomain(YearlyConsumptionDto yearlyConsumptionDto) {
        Consumption consumption = new Consumption();
        consumption.setMeteringPointId(yearlyConsumptionDto.getMeteringPointId());
        consumption.setConsumptionValue(yearlyConsumptionDto.getConsumption());
        consumption.setConsumptionTimestamp(LocalDateTime.of(LocalDate.of(yearlyConsumptionDto.getYear(), 1, 1), LocalTime.of(0,0,0)));
        return consumption;
    }
}
