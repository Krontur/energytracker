package com.energytracker.consumptionservice.application.port.inbound;

import com.energytracker.consumptionservice.application.dto.ConsumptionDto;
import com.energytracker.consumptionservice.application.dto.ConsumptionQueryDto;
import com.energytracker.consumptionservice.domain.model.ConsumptionQuery;

import java.util.List;

public interface GetConsumptionsByMeteringPointIdAndIntervalUseCase {

    List<ConsumptionDto> getConsumptionsByMeteringPointIdAndInterval(ConsumptionQueryDto queryDto);

}
