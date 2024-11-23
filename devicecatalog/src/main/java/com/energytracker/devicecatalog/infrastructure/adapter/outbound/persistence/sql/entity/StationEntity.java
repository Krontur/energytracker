package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class StationEntity extends DeviceEntity {


    public StationEntity(String serialNumber, DeviceTypeEntity deviceTypeEntity,
                         DeviceStatusEntity deviceStatus, String stationName, String stationType,
                         int readingIntervalInSeconds, String stationTag, List<ChannelEntity> channelList) {
        super(serialNumber, deviceTypeEntity, deviceStatus);
        this.stationName = stationName;
        this.stationType = stationType;
        this.readingIntervalInSeconds = readingIntervalInSeconds;
        this.stationTag = stationTag;
        this.channelList = channelList;
    }

    public StationEntity() {
        super();
    }

    @NotNull
    private String stationName;

    @NotNull
    private String stationType;

    @NotNull
    private int readingIntervalInSeconds;

    @NotNull
    private String stationTag;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinColumn(name = "station_id")
    private List<ChannelEntity> channelList;

}
