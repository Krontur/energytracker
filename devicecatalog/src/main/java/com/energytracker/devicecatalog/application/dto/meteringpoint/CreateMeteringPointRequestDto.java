package com.energytracker.devicecatalog.application.dto.meteringpoint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMeteringPointRequestDto {

    private String locationName;
    private String connectionDescription;
    private Long parentMeteringPointId;
    private Long energyMeterId;
    private Long channelId;
    private Boolean activeStatus;

}
