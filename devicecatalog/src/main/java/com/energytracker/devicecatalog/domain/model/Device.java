package com.energytracker.devicecatalog.domain.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Device {

    private Long deviceId;

    private String serialNumber;

    private DeviceType deviceType;

    private DeviceStatus deviceStatus;

    public Device(String serialNumber, String deviceType, String deviceStatus) {
        this.serialNumber = serialNumber;
        this.deviceType = DeviceType.valueOf(deviceType);
        this.deviceStatus = DeviceStatus.valueOf(deviceStatus);
    }
}
