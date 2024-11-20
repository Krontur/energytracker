package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.application.dto.CalibrationScheduleDto;
import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.dto.CalibrationSchedulePersistenceDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.dto.CreateEnergyMeterRequestPersistenceDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.dto.EnergyMeterResponsePersistenceDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.*;

import java.util.ArrayList;
import java.util.List;

public class EnergyMeterPersistenceMapper {

    /*
    public static CreateEnergyMeterRequestPersistenceDto createRequestEnergyMeterDtoToPersistenceDto(
            CreateEnergyMeterRequestDto createEnergyMeterRequestDto) {

        return new CreateEnergyMeterRequestPersistenceDto(
                createEnergyMeterRequestDto.getSerialNumber(),
                createEnergyMeterRequestDto.getDeviceType(),
                createEnergyMeterRequestDto.getDeviceType(),
                createEnergyMeterRequestDto.getConnectionAddress(),
                createEnergyMeterRequestDto.getEnergyMeterType(),
                createEnergyMeterRequestDto.getReferenceVoltage(),
                createEnergyMeterRequestDto.getConnectionType(),
                createEnergyMeterRequestDto.getMaxCurrent(),
                createEnergyMeterRequestDto.getMidApprovalYear()
        );

    }

    public static EnergyMeterEntity createRequestEnergyMeterPersistenceDtoToEntity(
            CreateEnergyMeterRequestPersistenceDto createRequestEnergyMeterPersistenceDto) {
        return new EnergyMeterEntity(
                createRequestEnergyMeterPersistenceDto.getSerialNumber(),
                DeviceTypeEntity.valueOf(createRequestEnergyMeterPersistenceDto.getDeviceType()),
                createRequestEnergyMeterPersistenceDto.getConnectionAddress(),
                EnergyMeterTypeEntity.valueOf(createRequestEnergyMeterPersistenceDto.getEnergyMeterType()),
                createRequestEnergyMeterPersistenceDto.getReferenceVoltage(),
                ConnectionTypeEntity.valueOf(createRequestEnergyMeterPersistenceDto.getConnectionType()),
                createRequestEnergyMeterPersistenceDto.getMaxCurrent(),
                createRequestEnergyMeterPersistenceDto.getMidApprovalYear(),
                new ArrayList<CalibrationScheduleEntity>()
        );
    }*/

    public static EnergyMeterResponseDto energyMeterEntityToResponseDto(EnergyMeterEntity energyMeterEntity) {

        List<CalibrationScheduleDto> calibrationScheduleDtoList = new ArrayList<CalibrationScheduleDto>();

        if(energyMeterEntity.getCalibrationSchedules() != null && !energyMeterEntity.getCalibrationSchedules().isEmpty()) {
            energyMeterEntity.getCalibrationSchedules().forEach(calibrationSchedule -> {
                calibrationScheduleDtoList.add(
                        CalibrationSchedulePersistenceMapper.calibrationScheduleEntityToDto(calibrationSchedule));
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
                calibrationScheduleDtoList

        );

    }

    public static EnergyMeterResponsePersistenceDto energyMeterResponseFromEntityToPersistenceDto(
            EnergyMeterEntity energyMeterEntity) {

        List<CalibrationSchedulePersistenceDto> calibrationSchedulePersistenceDtoList = new ArrayList<CalibrationSchedulePersistenceDto>();

        energyMeterEntity.getCalibrationSchedules().forEach(calibrationSchedule -> {
            calibrationSchedulePersistenceDtoList.add(
                    CalibrationSchedulePersistenceMapper.calibrationScheduleEntityToPersistenceDto(calibrationSchedule));
        });

        return new EnergyMeterResponsePersistenceDto(
                energyMeterEntity.getId(),
                energyMeterEntity.getSerialNumber(),
                energyMeterEntity.getDeviceType().toString(),
                energyMeterEntity.getDeviceStatus().toString(),
                energyMeterEntity.getConnectionAddress(),
                energyMeterEntity.getEnergyMeterType().name(),
                energyMeterEntity.getReferenceVoltage(),
                energyMeterEntity.getConnectionType().toString(),
                energyMeterEntity.getMaxCurrent(),
                energyMeterEntity.getMidApprovalYear(),
                energyMeterEntity.getCreatedAt(),
                energyMeterEntity.getUpdatedAt(),
                new ArrayList<CalibrationSchedulePersistenceDto>()
        );

    }

    public static EnergyMeterResponseDto energyMeterResponseFromPersistenceDtoToDto(
            EnergyMeterResponsePersistenceDto energyMeterResponsePersistenceDto) {

        return new EnergyMeterResponseDto(
                energyMeterResponsePersistenceDto.getEnergyMeterId(),
                energyMeterResponsePersistenceDto.getSerialNumber(),
                energyMeterResponsePersistenceDto.getDeviceType(),
                energyMeterResponsePersistenceDto.getDeviceStatus(),
                energyMeterResponsePersistenceDto.getConnectionAddress(),
                energyMeterResponsePersistenceDto.getEnergyMeterType(),
                energyMeterResponsePersistenceDto.getReferenceVoltage(),
                energyMeterResponsePersistenceDto.getConnectionType(),
                energyMeterResponsePersistenceDto.getMaxCurrent(),
                energyMeterResponsePersistenceDto.getMidApprovalYear(),
                energyMeterResponsePersistenceDto.getCreatedAt(),
                energyMeterResponsePersistenceDto.getUpdatedAt(),
                new ArrayList<CalibrationScheduleDto>()
        );

    }

    public static CreateEnergyMeterRequestPersistenceDto createEnergyMeterRequestFromDtoToPersistenceDto(
            CreateEnergyMeterRequestDto createEnergyMeterRequestDto) {

        return new CreateEnergyMeterRequestPersistenceDto(
                createEnergyMeterRequestDto.getSerialNumber(),
                createEnergyMeterRequestDto.getDeviceType(),
                createEnergyMeterRequestDto.getDeviceType(),
                createEnergyMeterRequestDto.getConnectionAddress(),
                createEnergyMeterRequestDto.getEnergyMeterType(),
                createEnergyMeterRequestDto.getReferenceVoltage(),
                createEnergyMeterRequestDto.getConnectionType(),
                createEnergyMeterRequestDto.getMaxCurrent(),
                createEnergyMeterRequestDto.getMidApprovalYear()
        );

    }

    public static EnergyMeterEntity createEnergyMeterRequestDtoToEntity(
            CreateEnergyMeterRequestDto createEnergyMeterRequestDto) {

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
                new ArrayList<CalibrationScheduleEntity>()
        );
    }
}
