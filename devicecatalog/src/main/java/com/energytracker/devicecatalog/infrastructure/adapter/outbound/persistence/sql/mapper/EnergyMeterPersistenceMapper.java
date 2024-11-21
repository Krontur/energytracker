package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.application.dto.CalibrationScheduleResponseDto;
import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.*;

import java.util.ArrayList;
import java.util.List;

public class EnergyMeterPersistenceMapper {

    public static EnergyMeterResponseDto energyMeterEntityToResponseDto(EnergyMeterEntity energyMeterEntity) {

        List<CalibrationScheduleResponseDto> calibrationScheduleResponseDtoList = new ArrayList<CalibrationScheduleResponseDto>();

        if(energyMeterEntity.getCalibrationSchedules() != null && !energyMeterEntity.getCalibrationSchedules().isEmpty()) {
            energyMeterEntity.getCalibrationSchedules().forEach(calibrationSchedule -> {
                calibrationScheduleResponseDtoList.add(
                        CalibrationSchedulePersistenceMapper.calibrationScheduleEntityToResponseDto(calibrationSchedule));
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
}
