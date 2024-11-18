package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StationEntity extends DeviceEntity {


    public StationEntity(String serialNumber, DeviceTypeEnum deviceTypeEnum) {
        super(serialNumber, deviceTypeEnum);
    }

    public StationEntity() {
        super();
    }
}
