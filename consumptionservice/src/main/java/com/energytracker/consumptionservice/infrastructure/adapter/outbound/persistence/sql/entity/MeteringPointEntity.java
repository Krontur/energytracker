package com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeteringPointEntity {

    @Id
    @Column(name = "metering_point_id", nullable = false)
    private Long meteringPointId;

    @Column(name = "station_tag", nullable = false)
    private String stationTag;

    @Column(name = "channel_number", nullable = false)
    private int channelNumber;
}
