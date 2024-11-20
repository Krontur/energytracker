package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StationEntity extends DeviceEntity {


    public StationEntity(String serialNumber, DeviceTypeEntity deviceTypeEntity, DeviceStatusEntity deviceStatus) {
        super(serialNumber, deviceTypeEntity, deviceStatus);
    }

    public StationEntity() {
        super();
    }
}
