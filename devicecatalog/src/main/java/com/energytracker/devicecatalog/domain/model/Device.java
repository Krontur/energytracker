package com.energytracker.devicecatalog.domain.model;


import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Device {

    private Long deviceId;

    private String serialNumber;

    private DeviceType deviceType;

    public Device(String serialNumber, DeviceType deviceType) {
        this.serialNumber = serialNumber;
        this.deviceType = deviceType;
    }
}
