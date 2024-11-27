package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.application.dto.energymeter.CalibrationScheduleRequestDto;
import com.energytracker.devicecatalog.application.dto.energymeter.CalibrationScheduleResponseDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.energymeter.CalibrationScheduleEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.energymeter.CalibrationStatusEntity;

public class CalibrationSchedulePersistenceMapper {

    public static CalibrationScheduleResponseDto calibrationScheduleResponseEntityToDto(CalibrationScheduleEntity calibrationScheduleEntity) {

        return new CalibrationScheduleResponseDto(
                calibrationScheduleEntity.getId(),
                calibrationScheduleEntity.getNextCalibrationDate(),
                calibrationScheduleEntity.getLastCalibrationDate(),
                calibrationScheduleEntity.getCalibrationFrequencyInYears(),
                calibrationScheduleEntity.getComments(),
                calibrationScheduleEntity.getCalibrationStatus().name()
        );

    }

    public static CalibrationScheduleEntity calibrationScheduleRequestDtoToEntity(
            CalibrationScheduleRequestDto calibrationScheduleRequestDto) {
        return new CalibrationScheduleEntity(
                calibrationScheduleRequestDto.getNextCalibrationDate(),
                calibrationScheduleRequestDto.getLastCalibrationDate(),
                calibrationScheduleRequestDto.getCalibrationFrequencyInYears(),
                calibrationScheduleRequestDto.getComments(),
                CalibrationStatusEntity.valueOf(calibrationScheduleRequestDto.getCalibrationStatus())
        );
    }
}
