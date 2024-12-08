package com.energytracker.consumptionservice.application.service;

import com.energytracker.consumptionservice.application.port.inbound.MeteringPointsMessageHandlerPort;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeteringPointsMessageService implements MeteringPointsMessageHandlerPort {


    @Override
    public void receiveMessage(List<MeteringPoint> meteringPoints, String filePath) {
    }

}
