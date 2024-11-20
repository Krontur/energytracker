package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class DeviceEntity extends BaseEntity {

    @NotNull
    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DeviceTypeEntity deviceType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DeviceStatusEntity deviceStatus;

    public DeviceEntity(String serialNumber, DeviceTypeEntity deviceTypeEntity, DeviceStatusEntity deviceStatus) {
        this.serialNumber = serialNumber;
        this.deviceType = deviceTypeEntity;
        this.deviceStatus = deviceStatus;
    }

    public DeviceEntity() {

    }
}
