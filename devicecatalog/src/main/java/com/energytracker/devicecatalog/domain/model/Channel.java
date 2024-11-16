package com.energytracker.devicecatalog.domain.model;

import com.energytracker.devicecatalog.domain.config.ChannelDefaultConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Channel {

    public Channel(int channelNumber, Station station, ChannelDefaultConfig channelDefaultConfig){
        this.channelNumber = channelNumber;
        this.station = station;
        this.channelName = channelDefaultConfig.getChannelName();
        this.channelMode = channelDefaultConfig.getChannelMode();
        this.channelLongName = channelDefaultConfig.getChannelLongName();
        this.energyUnit = channelDefaultConfig.getEnergyUnit();
        this.powerUnit = channelDefaultConfig.getPowerUnit();
        this.uRatio = channelDefaultConfig.getURatio();
        this.iRatio = channelDefaultConfig.getIRatio();
        this.pFactor = uRatio * iRatio;
        this.lonSubChannel = channelDefaultConfig.getLonSubChannel();
        this.lonStop = channelDefaultConfig.getLonStop();
    }

    public Channel(
            int channelNumber,
            Station station,
            String channelName,
            int channelMode,
            String channelLongName,
            EnergyUnit energyUnit,
            PowerUnit powerUnit,
            int uRatio,
            int iRatio,
            int lonSubChannel,
            Boolean lonStop ){
        this.channelNumber = channelNumber;
        this.station = station;
        this.channelName = channelName;
        this.channelMode = channelMode;
        this.channelLongName = channelLongName;
        this.energyUnit = energyUnit;
        this.powerUnit = powerUnit;
        this.uRatio = uRatio;
        this.iRatio = iRatio;
        this.pFactor = uRatio * iRatio;
        this.lonSubChannel = lonSubChannel;
        this.lonStop = lonStop;
    }

    private Station station;

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

    private Boolean lonStop;
}
