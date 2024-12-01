package com.energytracker.devicecatalog.domain.model;

import com.energytracker.devicecatalog.domain.model.energymeter.EnergyMeter;
import com.energytracker.devicecatalog.domain.model.station.Channel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MeteringPoint {

    public MeteringPoint(String locationName, String connectionDescription, Long parentMeteringPointId,
                         Long energyMeterId, Long channelId, Boolean activeStatus) {
        this.locationName = locationName;
        this.connectionDescription = connectionDescription;
        this.parentMeteringPointId = parentMeteringPointId;
        this.energyMeter = new EnergyMeter(energyMeterId);
        this.channel = new Channel(channelId);
        this.activeStatus = activeStatus;
    }

    private Long meteringPointId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long parentMeteringPointId;

    private EnergyMeter energyMeter;

    private Channel channel;

    private String locationName;

    private String connectionDescription;

    private Boolean activeStatus;

}
