package com.energytracker.consumptionservice.application.port.inbound;

import com.energytracker.consumptionservice.domain.model.MeteringPoint;

import java.util.List;

public interface MeteringPointsListenerPort {

    void receiveMessage(List<MeteringPoint> meteringPoints);

}
