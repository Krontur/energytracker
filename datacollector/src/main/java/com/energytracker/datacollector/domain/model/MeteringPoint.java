package com.energytracker.datacollector.domain.model;

import lombok.Data;

@Data
public class MeteringPoint {

    private Long meteringPointId;
    private String stationTag;
    private int channelNumber;

}
