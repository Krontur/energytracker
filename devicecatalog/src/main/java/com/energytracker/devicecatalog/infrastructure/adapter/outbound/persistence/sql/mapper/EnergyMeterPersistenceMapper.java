package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.application.dto.*;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.*;

import java.util.ArrayList;
import java.util.List;

public class EnergyMeterPersistenceMapper {

    public static EnergyMeterResponseDto energyMeterResponseEntityToDto(EnergyMeterEntity energyMeterEntity) {

        List<CalibrationScheduleResponseDto> calibrationScheduleResponseDtoList = new ArrayList<CalibrationScheduleResponseDto>();

        if(energyMeterEntity.getCalibrationSchedules() != null && !energyMeterEntity.getCalibrationSchedules().isEmpty()) {
            energyMeterEntity.getCalibrationSchedules().forEach(calibrationSchedule -> {
                calibrationScheduleResponseDtoList.add(
                        CalibrationSchedulePersistenceMapper.calibrationScheduleResponseEntityToDto(calibrationSchedule));
            });
        }

        return new EnergyMeterResponseDto(
                energyMeterEntity.getId(),
                energyMeterEntity.getSerialNumber(),
                energyMeterEntity.getDeviceType().name(),
                energyMeterEntity.getDeviceStatus().name(),
                energyMeterEntity.getConnectionAddress(),
                energyMeterEntity.getEnergyMeterType().name(),
                energyMeterEntity.getReferenceVoltage(),
                energyMeterEntity.getConnectionType().toString(),
                energyMeterEntity.getMaxCurrent(),
                energyMeterEntity.getMidApprovalYear(),
                energyMeterEntity.getCreatedAt(),
                energyMeterEntity.getUpdatedAt(),
                calibrationScheduleResponseDtoList

        );

    }

    public static EnergyMeterEntity createEnergyMeterRequestDtoToEntity(
            CreateEnergyMeterRequestDto createEnergyMeterRequestDto) {

        List<CalibrationScheduleEntity> calibrationScheduleEntityList = new ArrayList<CalibrationScheduleEntity>();

        createEnergyMeterRequestDto.getCalibrationSchedules().forEach(calibrationScheduleRequestDto -> {
            calibrationScheduleEntityList.add(
                    CalibrationSchedulePersistenceMapper.calibrationScheduleRequestDtoToEntity(calibrationScheduleRequestDto));
        });

        return new EnergyMeterEntity(
                createEnergyMeterRequestDto.getSerialNumber(),
                DeviceTypeEntity.valueOf(createEnergyMeterRequestDto.getDeviceType()),
                DeviceStatusEntity.valueOf(createEnergyMeterRequestDto.getDeviceStatus()),
                createEnergyMeterRequestDto.getConnectionAddress(),
                EnergyMeterTypeEntity.valueOf(createEnergyMeterRequestDto.getEnergyMeterType()),
                createEnergyMeterRequestDto.getReferenceVoltage(),
                ConnectionTypeEntity.valueOf(createEnergyMeterRequestDto.getConnectionType()),
                createEnergyMeterRequestDto.getMaxCurrent(),
                createEnergyMeterRequestDto.getMidApprovalYear(),
                calibrationScheduleEntityList
        );
    }

    public static EnergyMeterEntity energyMeterRequestDtoToEntity(EnergyMeterRequestDto energyMeterRequestDto) {
        List<CalibrationScheduleEntity> calibrationScheduleEntityList = new ArrayList<CalibrationScheduleEntity>();
        energyMeterRequestDto.getCalibrationSchedules().forEach(
                calibrationScheduleRequestDto -> {
                    calibrationScheduleEntityList.add(
                            new CalibrationScheduleEntity(
                                    calibrationScheduleRequestDto.getNextCalibrationDate(),
                                    calibrationScheduleRequestDto.getLastCalibrationDate(),
                                    calibrationScheduleRequestDto.getCalibrationFrequencyInYears(),
                                    calibrationScheduleRequestDto.getComments(),
                                    CalibrationStatusEntity.valueOf(calibrationScheduleRequestDto.getCalibrationStatus()
                            )));
                }
        );
        return new EnergyMeterEntity(
                energyMeterRequestDto.getSerialNumber(),
                DeviceTypeEntity.valueOf(energyMeterRequestDto.getDeviceType()),
                DeviceStatusEntity.valueOf(energyMeterRequestDto.getDeviceStatus()),
                energyMeterRequestDto.getConnectionAddress(),
                EnergyMeterTypeEntity.valueOf(energyMeterRequestDto.getEnergyMeterType()),
                energyMeterRequestDto.getReferenceVoltage(),
                ConnectionTypeEntity.valueOf(energyMeterRequestDto.getConnectionType()),
                energyMeterRequestDto.getMaxCurrent(),
                energyMeterRequestDto.getMidApprovalYear(),
                calibrationScheduleEntityList

        );
    }
}
