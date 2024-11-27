package com.energytracker.devicecatalog.application.dto.station;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StationRequestDto {

    private Long stationId;
    private String serialNumber;
    private String deviceType;
    private String deviceStatus;
    private String stationName;
    private String stationType;
    private int readingIntervalInSeconds;
    private String stationTag;
    private List<ChannelRequestDto> channelList;
}
