package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EnergyMeterTypeEnum extends BaseEntity {

    @Column(name = "energy_meter_type")
    private String energyMeterType;

    @Column
    private String description;
}
