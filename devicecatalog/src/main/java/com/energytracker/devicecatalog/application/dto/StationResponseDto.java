package com.energytracker.devicecatalog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StationResponseDto {

    private Long stationId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String serialNumber;
    private String deviceType;
    private String deviceStatus;
    private String stationName;
    private String stationType;
    private int readingIntervalInSeconds;
    private String stationTag;
    private List<ChannelResponseDto> channelList;

}
