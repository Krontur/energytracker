package com.energytracker.consumptionservice.application.port.outbound;

import com.energytracker.consumptionservice.domain.model.Consumption;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsumptionRepositoryPort {

   List<Consumption> findConsumptionsByMeteringPointIdAndInterval(Long meteringPointId, LocalDateTime startDateTime, LocalDateTime endDateTime);

   Consumption saveConsumption(Consumption consumption);
}
