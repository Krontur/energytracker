package com.energytracker.consumptionservice.application.port.inbound;

import com.energytracker.consumptionservice.domain.model.MeteringPoint;


public interface MeteringPointMessageHandlerPort {

    void receiveMessage(MeteringPoint meteringPoint);

}
