package com.energytracker.consumptionservice.application.port.inbound;

import com.energytracker.consumptionservice.domain.model.Consumption;

import java.util.List;

public interface ConsumptionsListenerPort {

    void receiveMessage(List<Consumption> consumptions);

}
