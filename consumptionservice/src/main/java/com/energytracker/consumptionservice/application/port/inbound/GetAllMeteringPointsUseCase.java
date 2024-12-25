package com.energytracker.consumptionservice.application.port.inbound;

import com.energytracker.consumptionservice.application.dto.MeteringPointDto;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;

import java.util.List;

public interface GetAllMeteringPointsUseCase {

    List<MeteringPointDto> getAllMeteringPoints();
}
