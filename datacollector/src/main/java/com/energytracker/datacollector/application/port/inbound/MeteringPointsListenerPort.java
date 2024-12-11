package com.energytracker.datacollector.application.port.inbound;

import com.energytracker.datacollector.domain.model.MeteringPoint;

public interface MeteringPointsListenerPort {

    void receiveMessage(MeteringPoint meteringPoint);

}
