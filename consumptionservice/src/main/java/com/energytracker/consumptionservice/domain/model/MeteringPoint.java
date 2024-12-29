package com.energytracker.consumptionservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeteringPoint {

    private ActionType actionType;
    private Long meteringPointId;
    private String stationTag;
    private int channelNumber;

}
