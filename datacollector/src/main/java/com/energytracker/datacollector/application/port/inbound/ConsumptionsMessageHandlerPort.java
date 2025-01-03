package com.energytracker.datacollector.application.port.inbound;

import com.energytracker.datacollector.domain.model.ConsumptionResult;
import com.energytracker.datacollector.domain.model.MeteringPoint;
import jakarta.ws.rs.core.NoContentException;

import java.util.List;

public interface ConsumptionsMessageHandlerPort {

    void sendMessage(List<ConsumptionResult> consumptionResultList, String queueName) throws NoContentException;

}
