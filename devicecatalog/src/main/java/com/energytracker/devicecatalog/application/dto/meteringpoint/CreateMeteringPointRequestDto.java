package com.energytracker.devicecatalog.application.dto.meteringpoint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateMeteringPointRequestDto {

    private String locationName;
    private String connectionDescription;
    private Long parentMeteringPointId;
    private Long energyMeterId;
    private Long channelId;
    private Boolean activeStatus;

}
