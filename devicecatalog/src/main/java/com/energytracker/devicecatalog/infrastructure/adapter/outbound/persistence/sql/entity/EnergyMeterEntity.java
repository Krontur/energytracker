package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
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
    @Column
    private String energyMeterType;

    @NotNull
    @Column
    private int referenceVoltage;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "connection_type_id", nullable = false)
    private ConnectionTypeEnum connectionType;

    @NotNull
    @Column
    private int maxCurrent;

    @NotNull
    @Column
    private int midApprovalYear;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "meter_status_id", nullable = false)
    private EnergyMeterStatusEnum energyMeterStatus;

    @NotNull
    @OneToMany(mappedBy = "energyMeterEntity")
    private List<CalibrationScheduleEntity> calibrationSchedules;

    public EnergyMeterEntity(
            String serialNumber,
            DeviceTypeEnum deviceTypeEnum,
            String connectionAddress,
            String energyMeterType,
            int referenceVoltage,
            ConnectionTypeEnum connectionTypeEnum,
            int maxCurrent,
            int midApprovalYear,
            ArrayList<CalibrationScheduleEntity> calibrationSchedules) {
        super(serialNumber, deviceTypeEnum);
        this.connectionAddress = connectionAddress;
        this.energyMeterType = energyMeterType;
        this.referenceVoltage = referenceVoltage;
        this.connectionType = connectionTypeEnum;
        this.maxCurrent = maxCurrent;
        this.midApprovalYear = midApprovalYear;
        this.calibrationSchedules = calibrationSchedules;
    }
}
