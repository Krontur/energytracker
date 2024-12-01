package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.energymeter.EnergyMeterEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.station.ChannelEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeteringPointEntity extends BaseEntity {

    @Column(name = "parent_metering_point_id")
    private Long parentMeteringPointId;

    @NotNull
    @OneToOne
    @JoinColumn(name = "energy_meter_entity_id", nullable = false, unique = true)
    private EnergyMeterEntity energyMeterEntity;

    @NotNull
    @OneToOne
    @JoinColumn(name = "channel_entity_id", nullable = false, unique = true)
    private ChannelEntity channelEntity;

    @NotNull
    private String locationName;

    @NotNull
    private String connectionDescription;

    @NotNull
    private Boolean activeStatus;

}
