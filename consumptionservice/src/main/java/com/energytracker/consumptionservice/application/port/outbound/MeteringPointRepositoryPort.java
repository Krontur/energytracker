package com.energytracker.consumptionservice.application.port.outbound;

import com.energytracker.consumptionservice.domain.model.MeteringPoint;

public interface MeteringPointRepositoryPort {

    MeteringPoint saveMeteringPoint(MeteringPoint meteringPoint);

}
