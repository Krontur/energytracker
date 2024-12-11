package com.energytracker.consumptionservice.infrastructure.adapter.inbound.messaging;

import com.energytracker.consumptionservice.application.port.inbound.ConsumptionsListenerPort;
import com.energytracker.consumptionservice.domain.model.Consumption;

public class ConsumptionsListenerAdapter implements ConsumptionsListenerPort {


    @Override
    public void receiveMessage(Consumption consumption) {

    }
}
