package com.energytracker.datacollector.application.port.inbound;

import com.energytracker.datacollector.domain.model.MeteringPoint;

import java.util.List;

public interface MeteringPointsListenerPort {

    void receiveMessage(List<MeteringPoint> meteringPoints);

}
