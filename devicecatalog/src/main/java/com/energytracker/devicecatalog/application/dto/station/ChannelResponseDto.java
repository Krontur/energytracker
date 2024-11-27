package com.energytracker.devicecatalog.application.dto.station;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChannelResponseDto {

    private Long channelId;
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

}
