package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class DeviceEntity extends BaseEntity {

    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "device_type_id")
    private DeviceTypeEnum deviceType;
}
