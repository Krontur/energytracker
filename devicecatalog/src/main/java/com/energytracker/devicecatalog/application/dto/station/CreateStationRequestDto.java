package com.energytracker.devicecatalog.application.dto.station;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateStationRequestDto {

    public CreateStationRequestDto(String serialNumber, String deviceType, String deviceStatus,
                                   String stationName, String stationType, String stationTag,
                                   int readingIntervalInSeconds) {
        this.serialNumber = serialNumber;
        this.deviceType = deviceType;
        this.deviceStatus = deviceStatus;
        this.stationName = stationName;
        this.stationType = stationType;
        this.stationTag = stationTag;
        this.readingIntervalInSeconds = readingIntervalInSeconds;
    }

    private String serialNumber;
    private String deviceType;
    private String deviceStatus;
    private String stationName;
    private String stationType;
    private String stationTag;
    private int readingIntervalInSeconds;
    private List<ChannelRequestDto> channelList;

}
