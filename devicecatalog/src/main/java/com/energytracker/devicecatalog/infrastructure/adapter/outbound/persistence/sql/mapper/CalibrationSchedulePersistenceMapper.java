package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.application.dto.CalibrationScheduleRequestDto;
import com.energytracker.devicecatalog.application.dto.CalibrationScheduleResponseDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.CalibrationScheduleEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.CalibrationStatusEntity;

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
