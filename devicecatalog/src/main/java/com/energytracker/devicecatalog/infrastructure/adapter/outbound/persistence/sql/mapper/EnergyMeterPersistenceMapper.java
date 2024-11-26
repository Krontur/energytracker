package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.application.dto.*;
import com.energytracker.devicecatalog.domain.model.*;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.*;

import java.util.ArrayList;
import java.util.List;

public class EnergyMeterPersistenceMapper {

    public static EnergyMeter energyMeterEntityToDomain(EnergyMeterEntity energyMeterEntity) {
        List<CalibrationSchedule> calibrationScheduleList = new ArrayList<>();
        energyMeterEntity.getCalibrationScheduleList().forEach(
                calibrationScheduleEntity -> {
                    calibrationScheduleList.add(
                            new CalibrationSchedule(
                                    calibrationScheduleEntity.getId(),
                                    calibrationScheduleEntity.getNextCalibrationDate(),
                                    calibrationScheduleEntity.getLastCalibrationDate(),
                                    calibrationScheduleEntity.getCalibrationFrequencyInYears(),
                                    calibrationScheduleEntity.getComments(),
                                    CalibrationStatus.valueOf(calibrationScheduleEntity.getCalibrationStatus().name())
                            )
                    );
                }
        );

        return new EnergyMeter(
                energyMeterEntity.getId(),
                energyMeterEntity.getSerialNumber(),
                energyMeterEntity.getCreatedAt(),
                energyMeterEntity.getUpdatedAt(),
                energyMeterEntity.getVersion(),
                DeviceType.valueOf(energyMeterEntity.getDeviceType().name()),
                DeviceStatus.valueOf(energyMeterEntity.getDeviceStatus().name()),
                energyMeterEntity.getConnectionAddress(),
                EnergyMeterType.valueOf(energyMeterEntity.getEnergyMeterType().name()),
                energyMeterEntity.getReferenceVoltage(),
                ConnectionType.valueOf(energyMeterEntity.getConnectionType().toString()),
                energyMeterEntity.getMaxCurrent(),
                energyMeterEntity.getMidApprovalYear(),
                calibrationScheduleList
        );

    }

    public static EnergyMeterEntity energyMeterDomainToEntity(
            EnergyMeter energyMeter) {
        List<CalibrationScheduleEntity> calibrationScheduleEntities = new ArrayList<>();
        energyMeter.getCalibrationScheduleList().forEach(
                calibrationSchedule -> {
                    calibrationScheduleEntities.add(
                            new CalibrationScheduleEntity(
                                    calibrationSchedule.getNextCalibrationDate(),
                                    calibrationSchedule.getLastCalibrationDate(),
                                    calibrationSchedule.getCalibrationFrequencyInYears(),
                                    calibrationSchedule.getComments(),
                                    CalibrationStatusEntity.valueOf(calibrationSchedule.getCalibrationStatus().name())
                            )
                    );
                }
        );
        if(energyMeter.getDeviceId() != null) {

            return new EnergyMeterEntity(
                    energyMeter.getDeviceId(),
                    energyMeter.getSerialNumber(),
                    energyMeter.getCreatedAt(),
                    energyMeter.getUpdatedAt(),
                    energyMeter.getVersion(),
                    DeviceTypeEntity.valueOf(energyMeter.getDeviceType().name()),
                    DeviceStatusEntity.valueOf(energyMeter.getDeviceStatus().name()),
                    energyMeter.getConnectionAddress(),
                    EnergyMeterTypeEntity.valueOf(energyMeter.getEnergyMeterType().name()),
                    energyMeter.getReferenceVoltage(),
                    ConnectionTypeEntity.valueOf(energyMeter.getConnectionType().name()),
                    energyMeter.getMaxCurrent(),
                    energyMeter.getMidApprovalYear(),
                    calibrationScheduleEntities
            );
        } else {
            return new EnergyMeterEntity(
                    energyMeter.getSerialNumber(),
                    DeviceTypeEntity.valueOf(energyMeter.getDeviceType().name()),
                    DeviceStatusEntity.valueOf(energyMeter.getDeviceStatus().name()),
                    energyMeter.getConnectionAddress(),
                    EnergyMeterTypeEntity.valueOf(energyMeter.getEnergyMeterType().name()),
                    energyMeter.getReferenceVoltage(),
                    ConnectionTypeEntity.valueOf(energyMeter.getConnectionType().name()),
                    energyMeter.getMaxCurrent(),
                    energyMeter.getMidApprovalYear(),
                    calibrationScheduleEntities
            );
        }
    }
}
