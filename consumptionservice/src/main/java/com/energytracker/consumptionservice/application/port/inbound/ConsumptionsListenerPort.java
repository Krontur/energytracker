package com.energytracker.consumptionservice.application.port.inbound;

import com.energytracker.consumptionservice.domain.model.Consumption;

public interface ConsumptionsListenerPort {

    void receiveMessage(Consumption consumption);

}
