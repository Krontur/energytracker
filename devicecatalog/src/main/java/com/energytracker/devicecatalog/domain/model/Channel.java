package com.energytracker.devicecatalog.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Channel {

    public Channel(int channelNumber, ChannelDefaultConfig channelDefaultConfig){
        this.channelNumber = channelNumber;
        this.channelName = channelDefaultConfig.getChannelName();
        this.channelMode = channelDefaultConfig.getChannelMode();
        this.channelLongName = channelDefaultConfig.getChannelLongName();
        this.energyUnit = channelDefaultConfig.getEnergyUnit();
        this.powerUnit = channelDefaultConfig.getPowerUnit();
        this.uRatio = channelDefaultConfig.getURatio();
        this.iRatio = channelDefaultConfig.getIRatio();
        this.pFactor = uRatio * iRatio;
        this.lonSubChannel = channelDefaultConfig.getLonSubChannel();
        this.lonIsActive = channelDefaultConfig.getLonIsActive();
    }

    public Channel(
            int channelNumber,
            String channelName,
            int channelMode,
            String channelLongName,
            EnergyUnit energyUnit,
            PowerUnit powerUnit,
            int uRatio,
            int iRatio,
            int lonSubChannel,
            Boolean lonIsActive ){
        this.channelNumber = channelNumber;
        this.channelName = channelName;
        this.channelMode = channelMode;
        this.channelLongName = channelLongName;
        this.energyUnit = energyUnit;
        this.powerUnit = powerUnit;
        this.uRatio = uRatio;
        this.iRatio = iRatio;
        this.pFactor = uRatio * iRatio;
        this.lonSubChannel = lonSubChannel;
        this.lonIsActive = lonIsActive;
    }

    private Long channelId;

    private String channelName;

    private int channelNumber;

    private int channelMode;

    private String channelLongName;

    private EnergyUnit energyUnit;

    private PowerUnit powerUnit;

    private int uRatio;

    private int iRatio;

    private int pFactor;

    private int lonSubChannel;

    private Boolean lonIsActive;
}
