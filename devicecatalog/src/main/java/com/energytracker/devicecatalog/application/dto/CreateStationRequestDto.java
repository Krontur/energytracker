package com.energytracker.devicecatalog.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateStationRequestDto {

    private String serialNumber;
    private String deviceType;
    private String deviceStatus;
    private String stationName;
    private String stationType;
    private String stationTag;
    private int readingIntervalInSeconds;
    private List<ChannelRequestDto> channelList;

}
