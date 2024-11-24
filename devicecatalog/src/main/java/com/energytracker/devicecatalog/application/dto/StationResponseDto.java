package com.energytracker.devicecatalog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StationResponseDto {

    public StationResponseDto(
            Long stationId,
            String serialNumber,
            String deviceType,
            String deviceStatus,
            String stationName,
            String stationType,
            int readingIntervalInSeconds,
            String stationTag,
            List<ChannelResponseDto> channelList
    ) {
        this.stationId = stationId;
        this.serialNumber = serialNumber;
        this.deviceType = deviceType;
        this.deviceStatus = deviceStatus;
        this.stationName = stationName;
        this.stationType = stationType;
        this.readingIntervalInSeconds = readingIntervalInSeconds;
        this.stationTag = stationTag;
        this.channelList = channelList;
    }

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
