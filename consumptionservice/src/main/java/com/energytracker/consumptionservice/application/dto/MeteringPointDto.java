package com.energytracker.consumptionservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeteringPointDto {

    String actionType;
    Long meteringPointId;
    int channelNumber;
    String stationTag;

}
