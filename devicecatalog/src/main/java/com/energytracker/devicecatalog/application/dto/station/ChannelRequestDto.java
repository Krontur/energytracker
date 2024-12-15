package com.energytracker.devicecatalog.application.dto.station;


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
public class ChannelRequestDto {

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
