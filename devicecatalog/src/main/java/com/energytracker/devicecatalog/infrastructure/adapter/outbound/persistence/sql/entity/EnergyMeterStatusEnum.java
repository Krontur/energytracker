package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EnergyMeterStatusEnum extends BaseEntity {

    @Column(name = "meter_status", nullable = false, unique = true)
    private String meterStatus;

}
