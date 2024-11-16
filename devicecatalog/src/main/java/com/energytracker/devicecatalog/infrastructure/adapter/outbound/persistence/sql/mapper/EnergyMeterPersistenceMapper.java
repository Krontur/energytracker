package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.application.dto.CalibrationScheduleDto;
import com.energytracker.devicecatalog.application.dto.CreateRequestEnergyMeterDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.dto.CreateRequestEnergyMeterPersistenceDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.CalibrationScheduleEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.ConnectionTypeEnum;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.DeviceTypeEnum;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.EnergyMeterEntity;

import java.util.ArrayList;
import java.util.List;

public class EnergyMeterPersistenceMapper {
    public static CreateRequestEnergyMeterPersistenceDto createRequestEnergyMeterDtoToPersistence(CreateRequestEnergyMeterDto createRequestEnergyMeterDto) {

        return new CreateRequestEnergyMeterPersistenceDto(
                createRequestEnergyMeterDto.getSerialNumber(),
                createRequestEnergyMeterDto.getDeviceType(),
                createRequestEnergyMeterDto.getConnectionAddress(),
                createRequestEnergyMeterDto.getEnergyMeterType(),
                createRequestEnergyMeterDto.getReferenceVoltage(),
                createRequestEnergyMeterDto.getConnectionType(),
                createRequestEnergyMeterDto.getMaxCurrent(),
                createRequestEnergyMeterDto.getMidAprovalYear()
        );

    }

    public static EnergyMeterEntity createRequestEnergyMeterPersistenceDtoToEntity(CreateRequestEnergyMeterPersistenceDto createRequestEnergyMeterPersistenceDto) {
        return new EnergyMeterEntity(
                createRequestEnergyMeterPersistenceDto.getSerialNumber(),
                new DeviceTypeEnum(createRequestEnergyMeterPersistenceDto.getDeviceType()),
                createRequestEnergyMeterPersistenceDto.getConnectionAddress(),
                createRequestEnergyMeterPersistenceDto.getEnergyMeterType(),
                createRequestEnergyMeterPersistenceDto.getReferenceVoltage(),
                new ConnectionTypeEnum(createRequestEnergyMeterPersistenceDto.getConnectionType()),
                createRequestEnergyMeterPersistenceDto.getMaxCurrent(),
                createRequestEnergyMeterPersistenceDto.getMidAprovalYear(),
                new ArrayList<CalibrationScheduleEntity>()
        );
    }

    public static EnergyMeterResponseDto energyMeterEntityToResponseDto(EnergyMeterEntity energyMeterEntity) {

        List<CalibrationScheduleDto> calibrationScheduleDtoList = new ArrayList<CalibrationScheduleDto>();

        energyMeterEntity.getCalibrationSchedules().forEach(calibrationSchedule -> {
            calibrationScheduleDtoList.add(CalibrationScheduleMapper.calibrationScheduleEntityToDto(calibrationSchedule));
        });

        return new EnergyMeterResponseDto(
                energyMeterEntity.getId(),
                energyMeterEntity.getSerialNumber(),
                energyMeterEntity.getDeviceType().toString(),
                energyMeterEntity.getConnectionAddress(),
                energyMeterEntity.getEnergyMeterType(),
                energyMeterEntity.getReferenceVoltage(),
                energyMeterEntity.getConnectionType().toString(),
                energyMeterEntity.getMaxCurrent(),
                energyMeterEntity.getMidAprovalYear(),
                calibrationScheduleDtoList

        );

    }
}
