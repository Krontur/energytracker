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

    private Long meteringPointId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private MeteringPoint parentMeteringPoint;

    private EnergyMeter energyMeter;

    private Channel channel;

    private String locationName;

    private String connectionDescription;

    private Boolean activeStatus;

}
