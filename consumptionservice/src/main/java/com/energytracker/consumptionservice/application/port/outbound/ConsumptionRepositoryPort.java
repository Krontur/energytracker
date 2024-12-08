package com.energytracker.consumptionservice.application.port.outbound;

import com.energytracker.consumptionservice.domain.model.Consumption;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsumptionRepositoryPort {

   List<Consumption> findConsumptions(Long meteringPointId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
