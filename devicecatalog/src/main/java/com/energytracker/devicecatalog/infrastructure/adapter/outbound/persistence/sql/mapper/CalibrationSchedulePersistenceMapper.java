package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper;

import com.energytracker.devicecatalog.application.dto.CalibrationScheduleDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.dto.CalibrationSchedulePersistenceDto;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.CalibrationScheduleEntity;

public class CalibrationSchedulePersistenceMapper {

    public static CalibrationScheduleDto calibrationScheduleEntityToDto(CalibrationScheduleEntity calibrationScheduleEntity) {
        return null;
    }

    public static CalibrationScheduleEntity calibrationScheduleDtoToEntity(CalibrationScheduleDto calibrationScheduleDto) {
        return null;
    }

    public static CalibrationSchedulePersistenceDto calibrationScheduleDtoToPersistenceDto(CalibrationScheduleDto calibrationScheduleDto) {
        return null;
    }

    public static CalibrationScheduleDto calibrationSchedulePersistenceDtoToDto(CalibrationSchedulePersistenceDto calibrationSchedulePersistenceDto) {
        return null;
    }

    public static CalibrationSchedulePersistenceDto calibrationScheduleEntityToPersistenceDto(CalibrationScheduleEntity calibrationSchedule) {

        CalibrationSchedulePersistenceDto calibrationSchedulePersistenceDto = new CalibrationSchedulePersistenceDto();
        calibrationSchedulePersistenceDto.setId(calibrationSchedule.getId());
        calibrationSchedulePersistenceDto.setEnergyMeterId(calibrationSchedule.getEnergyMeterEntity().getId());
        calibrationSchedulePersistenceDto.setNextCalibrationDate(calibrationSchedule.getNextCalibrationDate());
        calibrationSchedulePersistenceDto.setLastCalibrationDate(calibrationSchedule.getLastCalibrationDate());
        calibrationSchedulePersistenceDto.setCalibrationFrequency(calibrationSchedule.getCalibrationFrequency());
        calibrationSchedulePersistenceDto.setComments(calibrationSchedule.getComments());
        calibrationSchedulePersistenceDto.setCalibrationStatus(calibrationSchedule.getCalibrationStatus().toString());
        return calibrationSchedulePersistenceDto;

    }
}
