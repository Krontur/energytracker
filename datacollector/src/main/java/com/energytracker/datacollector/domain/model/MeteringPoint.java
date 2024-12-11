package com.energytracker.datacollector.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeteringPoint {

    private Long meteringPointId;
    private String stationTag;
    private int channelNumber;

}
