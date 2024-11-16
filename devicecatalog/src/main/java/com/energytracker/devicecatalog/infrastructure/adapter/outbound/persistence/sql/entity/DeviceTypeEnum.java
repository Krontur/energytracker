package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@Setter
public class DeviceTypeEnum extends BaseEntity {


    @Column(name = "device_type", nullable = false, unique = true)
    private String deviceType;

    public DeviceTypeEnum() {

    }
}
