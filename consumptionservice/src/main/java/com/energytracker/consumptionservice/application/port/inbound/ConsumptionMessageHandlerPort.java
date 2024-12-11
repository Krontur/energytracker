package com.energytracker.consumptionservice.application.port.inbound;

import com.energytracker.consumptionservice.domain.model.Consumption;

public interface ConsumptionMessageHandlerPort {

    void receiveMessage(Consumption consumption);

}
