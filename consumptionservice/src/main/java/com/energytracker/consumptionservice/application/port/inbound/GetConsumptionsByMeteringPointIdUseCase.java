package com.energytracker.consumptionservice.application.port.inbound;

import com.energytracker.consumptionservice.application.dto.ConsumptionDto;

import java.util.List;

public interface GetConsumptionsByMeteringPointIdUseCase {

    List<ConsumptionDto> getConsumptionsByMeteringPointId(Long meteringPointId);

}
