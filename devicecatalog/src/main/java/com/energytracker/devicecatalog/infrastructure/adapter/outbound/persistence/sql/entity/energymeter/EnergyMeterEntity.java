package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.energymeter;


import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.DeviceEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.DeviceStatusEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.DeviceTypeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
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
    private List<CalibrationScheduleEntity> calibrationScheduleList;

    public EnergyMeterEntity(
            String serialNumber,
            DeviceTypeEntity deviceTypeEntity,
            DeviceStatusEntity deviceStatusEntity,
            String connectionAddress,
            EnergyMeterTypeEntity energyMeterTypeEntity,
            int referenceVoltage,
            ConnectionTypeEntity connectionTypeEntity,
            int maxCurrent,
            int midApprovalYear,
            List<CalibrationScheduleEntity> calibrationScheduleList
            ) {
        super(serialNumber, deviceTypeEntity, deviceStatusEntity);
        this.connectionAddress = connectionAddress;
        this.energyMeterType = energyMeterTypeEntity;
        this.referenceVoltage = referenceVoltage;
        this.connectionType = connectionTypeEntity;
        this.maxCurrent = maxCurrent;
        this.midApprovalYear = midApprovalYear;
        this.calibrationScheduleList = calibrationScheduleList;
    }

    public EnergyMeterEntity(
            Long deviceId,
            String serialNumber,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Long version,
            DeviceTypeEntity deviceTypeEntity,
            DeviceStatusEntity deviceStatusEntity,
            String connectionAddress,
            EnergyMeterTypeEntity energyMeterTypeEntity,
            int referenceVoltage,
            ConnectionTypeEntity connectionTypeEntity,
            int maxCurrent,
            int midApprovalYear,
            List<CalibrationScheduleEntity> calibrationScheduleList
    ) {
        super(deviceId, serialNumber, createdAt, updatedAt, version, deviceTypeEntity, deviceStatusEntity);
        this.connectionAddress = connectionAddress;
        this.energyMeterType = energyMeterTypeEntity;
        this.referenceVoltage = referenceVoltage;
        this.connectionType = connectionTypeEntity;
        this.maxCurrent = maxCurrent;
        this.midApprovalYear = midApprovalYear;
        this.calibrationScheduleList = calibrationScheduleList;
    }
}
