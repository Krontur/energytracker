package com.energytracker.devicecatalog.domain.model;

import com.energytracker.devicecatalog.domain.config.ChannelDefaultConfig;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Station extends Device{

    public Station(Long id, String name){
        this.setDeviceId(id);
    }

    private String stationName;

    private String stationTyp;

    private String stationTag;

    private int intervalSeconds;

    List<Channel> channels = new ArrayList<>();

    public void addChannel(Channel channel) {
        channel.setStation(this);
        channels.add(channel);
    }

    public void initializeDefaultChannels(int numberOfChannels) {
        ChannelDefaultConfig channelDefaultConfig = new ChannelDefaultConfig();
        for(int i = 1; i <= numberOfChannels; i++) {
            Channel channel = new Channel(i, this, channelDefaultConfig);
            addChannel(channel);
        }
    }
}
