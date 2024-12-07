package com.energytracker.datacollector.domain.model;

import com.netflix.spectator.api.Meter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsumptionQuery {

    private CommandType queryType;
    private MeteringPoint meteringPoint;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
