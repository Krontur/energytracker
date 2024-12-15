package com.energytracker.devicecatalog.application.dto.meteringpoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateMeteringPointRequestDto {

    private String locationName;
    private String connectionDescription;
    private Long parentMeteringPointId;
    private Long energyMeterId;
    private Long channelId;
    private Boolean activeStatus;

}
