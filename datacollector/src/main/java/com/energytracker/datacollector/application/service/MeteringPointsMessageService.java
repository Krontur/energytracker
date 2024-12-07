package com.energytracker.datacollector.application.service;

import com.energytracker.datacollector.application.port.inbound.MeteringPointsMessageHandlerPort;
import com.energytracker.datacollector.application.port.outbound.MeteringPointFileRepositoryPort;
import com.energytracker.datacollector.domain.model.MeteringPoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeteringPointsMessageService implements MeteringPointsMessageHandlerPort {

    private final MeteringPointFileRepositoryPort meteringPointFileRepositoryPort;

    public MeteringPointsMessageService(MeteringPointFileRepositoryPort meteringPointFileRepositoryPort) {
        this.meteringPointFileRepositoryPort = meteringPointFileRepositoryPort;
    }

    @Override
    public void receiveMessage(List<MeteringPoint> meteringPoints, String filePath) {
        meteringPointFileRepositoryPort.saveMeteringPointsToFile(meteringPoints, filePath);
    }

}
