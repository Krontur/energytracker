package com.energytracker.devicecatalog.domain.model;


import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Device {

    private Long deviceId;

    private String serialNumber;

    private String deviceType;
}
