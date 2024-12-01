package com.energytracker.devicecatalog.domain.model;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Device {

    private Long deviceId;

    private String serialNumber;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long version;

    private DeviceType deviceType;

    private DeviceStatus deviceStatus;

    public Device(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Device(String serialNumber, DeviceType deviceType, DeviceStatus deviceStatus) {
        this.serialNumber = serialNumber;
        this.deviceType = deviceType;
        this.deviceStatus = deviceStatus;
    }

    public Device(Long deviceId, String serialNumber, DeviceType deviceType, DeviceStatus deviceStatus, Long version) {
        this.deviceId = deviceId;
        this.serialNumber = serialNumber;
        this.deviceType = deviceType;
        this.deviceStatus = deviceStatus;
        this.version = version;
    }

    public void deactivate() {
        if (this.deviceStatus == DeviceStatus.DEACTIVATED) {
            throw new IllegalStateException("Device is already deactivated");
        }
        this.deviceStatus = DeviceStatus.DEACTIVATED;
    }
}
