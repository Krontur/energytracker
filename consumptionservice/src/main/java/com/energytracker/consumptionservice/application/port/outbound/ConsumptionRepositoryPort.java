package com.energytracker.consumptionservice.application.port.outbound;

import com.energytracker.consumptionservice.domain.model.Consumption;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ConsumptionRepositoryPort {

   List<Consumption> findConsumptionsByMeteringPointIdAndInterval(Long meteringPointId, LocalDateTime startDateTime, LocalDateTime endDateTime);

   Consumption saveConsumption(Consumption consumption);

   List<Consumption> findConsumptionsByMeteringPointId(Long meteringPointId);

   List<Consumption> findDailyConsumptionsByMeteringPointId(Long meteringPointId, LocalDate startDate, LocalDate endDate);

   List<Consumption> findMonthlyConsumptionsByMeteringPointId(Long meteringPointId, LocalDate startDate, LocalDate endDate);

   List<Consumption> findYearlyConsumptionsByMeteringPointId(Long meteringPointId, int startYear, int endYear);
}
