package com.energytracker.devicecatalog.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Station extends Device{

    public Station(
            Long deviceId,
            String serialNumber,
            DeviceType deviceType,
            DeviceStatus deviceStatus,
            String stationName,
            String stationType,
            String stationTag,
            int readingIntervalInSeconds
    ) {
        super(deviceId, serialNumber, deviceType, deviceStatus);

        this.stationName = stationName;
        this.stationType = stationType;
        this.stationTag = stationTag;
        this.readingIntervalInSeconds = readingIntervalInSeconds;
    }

    public Station(
            String serialNumber,
            DeviceType deviceType,
            DeviceStatus deviceStatus,
            String stationName,
            String stationType,
            String stationTag,
            int readingIntervalInSeconds
    ) {
        super(serialNumber, deviceType, deviceStatus);

        this.stationName = stationName;
        this.stationType = stationType;
        this.stationTag = stationTag;
        this.readingIntervalInSeconds = readingIntervalInSeconds;
        initializeDefaultChannels(64);
    };

    private String stationName;

    private String stationType;

    private String stationTag;

    private int readingIntervalInSeconds;

    List<Channel> channels = new ArrayList<>();

    private void addChannel(Channel channel) {
        channels.add(channel);
    }

    private void initializeDefaultChannels(int numberOfChannels) {
        ChannelDefaultConfig channelDefaultConfig = new ChannelDefaultConfig();
        for(int i = 1; i <= numberOfChannels; i++) {
            Channel channel = new Channel(i, channelDefaultConfig);
            addChannel(channel);
        }
    }
}
