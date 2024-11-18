package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public class DeviceEntity extends BaseEntity {

    @NotNull
    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "device_type_id")
    private DeviceTypeEnum deviceType;

    public DeviceEntity(String serialNumber, DeviceTypeEnum deviceTypeEnum) {
        this.serialNumber = serialNumber;
        this.deviceType = deviceTypeEnum;
    }

    public DeviceEntity() {

    }
}
