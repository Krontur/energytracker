package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnergyMeterResponseRestDto {

    private Long energyMeterId;
    private String serialNumber;
    private String deviceType;
    private String connectionAddress;
    private String energyMeterType;
    private int referenceVoltage;
    private String connectionType;
    private int maxCurrent;
    private int midApprovalYear;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
