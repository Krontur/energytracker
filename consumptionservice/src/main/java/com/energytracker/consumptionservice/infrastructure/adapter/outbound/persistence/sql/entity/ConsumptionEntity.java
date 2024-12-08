package com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consumption_seq")
    @SequenceGenerator(name = "consumption_seq", sequenceName = "consumptions.consumption_entity_seq", allocationSize = 1)
    private Long consumptionId;

    @Column(name = "metering_point_id", nullable = false)
    private Long meteringPointId;

    @Column(name = "consumption_value", nullable = false)
    private double consumptionValue;

    @Column(name = "consumption_timestamp", nullable = false)
    private LocalDateTime consumptionTimestamp;

}
