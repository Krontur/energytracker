package com.energytracker.consumptionservice.application.port.inbound;

import com.energytracker.consumptionservice.application.dto.*;
import com.energytracker.consumptionservice.domain.model.ConsumptionQuery;

import java.util.List;

public interface GetConsumptionsByMeteringPointIdAndIntervalUseCase {

    List<ConsumptionDto> getConsumptionsByMeteringPointIdAndInterval(ConsumptionQueryDto queryDto);

    List<ConsumptionDto> getIntervalConsumptionsByMeteringPointIdAndInterval(ConsumptionQueryDto queryDto);

    List<ConsumptionDto> getDailyConsumptionsByMeteringPointIdAndInterval(ConsumptionQueryDto queryDto);

    List<ConsumptionDto> getMonthlyConsumptionsByMeteringPointIdAndInterval(ConsumptionQueryDto queryDto);

    List<ConsumptionDto> getYearlyConsumptionsByMeteringPointIdAndInterval(ConsumptionQueryDto queryDto);

}
