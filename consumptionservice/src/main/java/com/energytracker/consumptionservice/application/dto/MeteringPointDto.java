package com.energytracker.consumptionservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MeteringPointDto {

    String actionType;
    Long meteringPointId;
    int channelNumber;
    String stationTag;

}
