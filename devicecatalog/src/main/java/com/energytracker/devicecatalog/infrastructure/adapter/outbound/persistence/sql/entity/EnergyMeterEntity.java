package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "energy_meter")
public class EnergyMeterEntity extends BaseEntity {

    private String serialNumber;

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
    private int midAprovalYear;

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
            int midAprovalYear,
            ArrayList<CalibrationScheduleEntity> calibrationSchedules) {
    }
}
