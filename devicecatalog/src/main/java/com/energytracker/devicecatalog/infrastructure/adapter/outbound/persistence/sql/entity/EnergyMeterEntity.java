package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnergyMeterEntity extends DeviceEntity {

    @NotNull
    @Column
    private String connectionAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EnergyMeterTypeEntity energyMeterType;

    @NotNull
    @Column
    private int referenceVoltage;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ConnectionTypeEntity connectionType;

    @NotNull
    @Column
    private int maxCurrent;

    @NotNull
    @Column
    private int midApprovalYear;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinColumn(name = "energy_meter_id")
    private List<CalibrationScheduleEntity> calibrationSchedules;

    public EnergyMeterEntity(
            String serialNumber,
            DeviceTypeEntity deviceTypeEntity,
            DeviceStatusEntity deviceStatus,
            String connectionAddress,
            EnergyMeterTypeEntity energyMeterType,
            int referenceVoltage,
            ConnectionTypeEntity connectionType,
            int maxCurrent,
            int midApprovalYear,
            List<CalibrationScheduleEntity> calibrationSchedules) {
        super(serialNumber, deviceTypeEntity, deviceStatus);
        this.connectionAddress = connectionAddress;
        this.energyMeterType = energyMeterType;
        this.referenceVoltage = referenceVoltage;
        this.connectionType = connectionType;
        this.maxCurrent = maxCurrent;
        this.midApprovalYear = midApprovalYear;
        this.calibrationSchedules = calibrationSchedules;
    }
}
