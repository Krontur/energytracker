package com.energytracker.devicecatalog.application.port.inbound.meteringpoint;

import com.energytracker.devicecatalog.domain.model.MeteringPoint;

import java.util.List;

public interface GetMeteringPointsUseCase {

    List<MeteringPoint> getMeteringPoints();

}
