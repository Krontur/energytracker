package com.energytracker.devicecatalog.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Station extends Device{

    public Station(
            Long deviceId,
            String serialNumber,
            DeviceType deviceType,
            DeviceStatus deviceStatus,
            String stationName,
            String stationType,
            String stationTag,
            int readingIntervalInSeconds,
            List<Channel> channelList
    ) {
        super(deviceId, serialNumber, deviceType, deviceStatus);

        this.stationName = stationName;
        this.stationType = stationType;
        this.stationTag = stationTag;
        this.readingIntervalInSeconds = readingIntervalInSeconds;
        this.channelList = channelList;
    }

    public Station(
            Long deviceId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String serialNumber,
            DeviceType deviceType,
            DeviceStatus deviceStatus,
            String stationName,
            String stationType,
            String stationTag,
            int readingIntervalInSeconds,
            List<Channel> channelList
    ) {
        super(deviceId, serialNumber, createdAt, updatedAt, deviceType, deviceStatus);

        this.stationName = stationName;
        this.stationType = stationType;
        this.stationTag = stationTag;
        this.readingIntervalInSeconds = readingIntervalInSeconds;
        this.channelList = channelList;
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

    List<Channel> channelList = new ArrayList<>();


    private void addChannel(Channel channel) {
        channelList.add(channel);
    }

    private void initializeDefaultChannels(int numberOfChannels) {
        ChannelDefaultConfig channelDefaultConfig = new ChannelDefaultConfig();
        for(int i = 1; i <= numberOfChannels; i++) {
            Channel channel = new Channel(i, channelDefaultConfig);
            addChannel(channel);
        }
    }
}
