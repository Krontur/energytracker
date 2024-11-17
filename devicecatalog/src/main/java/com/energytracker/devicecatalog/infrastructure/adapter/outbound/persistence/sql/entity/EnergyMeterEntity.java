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

    @ManyToOne
    @JoinColumn(name = "device_type_id")
    private DeviceTypeEnum deviceType;

    @Column
    private String connectionAddress;

    @Column
    private String energyMeterType;

    @Column
    private int referenceVoltage;

    @ManyToOne
    @JoinColumn(name = "connection_type_id", nullable = false)
    private ConnectionTypeEnum connectionType;

    @Column
    private int maxCurrent;

    @Column
    private int midApprovalYear;

    @ManyToOne
    @JoinColumn(name = "meter_status_id", nullable = false)
    private EnergyMeterStatusEnum energyMeterStatus;

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
    }
}
