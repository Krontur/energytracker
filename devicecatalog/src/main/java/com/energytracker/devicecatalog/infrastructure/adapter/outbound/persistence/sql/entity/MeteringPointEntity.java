package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.energymeter.EnergyMeterEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.station.ChannelEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class MeteringPointEntity extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "parent_metering_point_entity_metering_point_id")
    private MeteringPointEntity parentMeteringPointEntity;

    @OneToOne
    @JoinColumn(name = "energy_meter_entity_id")
    private EnergyMeterEntity energyMeterEntity;

    @OneToOne
    @JoinColumn(name = "channel_entity_id")
    private ChannelEntity channelEntity;

    private String locationName;

    private String connectionDescription;

    private Boolean activeStatus;

}
