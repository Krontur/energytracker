package com.energytracker.devicecatalog.application.port.outbound;

import com.energytracker.devicecatalog.domain.model.MeteringPoint;

import java.util.List;

public interface MeteringPointRepositoryPort {

    List<MeteringPoint> getAllMeteringPoints();

    MeteringPoint createMeteringPoint(MeteringPoint meteringPoint);

    MeteringPoint getMeteringPointById(Long meteringPointId);

    MeteringPoint updateMeteringPointById(Long meteringPointId, MeteringPoint meteringPoint);
}
