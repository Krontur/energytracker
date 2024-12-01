package com.energytracker.devicecatalog.domain.model.station;

import lombok.Getter;

@Getter
public class ChannelDefaultConfig {

    private String channelName = "Name";

    private String channelLongName = "Long name";

    private int channelMode = 0;

    private EnergyUnit energyUnit = EnergyUnit.KWH;

    private PowerUnit powerUnit = PowerUnit.KW;

    private int uRatio = 1;

    private int iRatio = 1;

    private int pFactor = uRatio * iRatio;

    private int lonSubChannel = 1;

    private Boolean lonIsActive = false;

}
