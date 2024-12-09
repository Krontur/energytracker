package com.energytracker.consumptionservice.domain.model;

import lombok.Data;

@Data
public class MeteringPoint {

    private ActionType actionType;
    private Long meteringPointId;
    private String stationTag;
    private int channelNumber;

}
