package com.energytracker.devicecatalog.application.dto.station;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChannelResponseDto {

    private Long channelId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;
    private String channelName;
    private int channelNumber;
    private int channelMode;
    private String channelLongName;
    private String energyUnit;
    private String powerUnit;
    private int uRatio;
    private int iRatio;
    private int pFactor;
    private int lonSubChannel;
    private Boolean lonIsActive;
    private Long stationId;

}
