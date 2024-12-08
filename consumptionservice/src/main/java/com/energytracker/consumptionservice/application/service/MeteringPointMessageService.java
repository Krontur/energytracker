package com.energytracker.consumptionservice.application.service;

import com.energytracker.consumptionservice.application.port.inbound.MeteringPointMessageHandlerPort;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeteringPointMessageService implements MeteringPointMessageHandlerPort {


    @Override
    public void receiveMessage(MeteringPoint meteringPoint, String filePath) {
    }

}
