package com.energytracker.devicecatalog.application.dto.meteringpoint;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MeteringPointResponseToConsumptionServiceDto {

    private Long MeteringPointId;
    private String stationTag;
    private int channelNumber;

}
