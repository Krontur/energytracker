package com.energytracker.consumptionservice.application.port.inbound;

import com.energytracker.consumptionservice.domain.model.MeteringPoint;

import java.util.List;

public interface MeteringPointsMessageHandlerPort {

    void receiveMessage(List<MeteringPoint> meteringPoints, String filePath);

}
