package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.CalibrationScheduleRequestDto;
import com.energytracker.devicecatalog.application.dto.CalibrationScheduleResponseDto;
import com.energytracker.devicecatalog.domain.model.CalibrationSchedule;
import com.energytracker.devicecatalog.domain.model.CalibrationStatus;

public class CalibrationScheduleMapper {

    public static CalibrationScheduleRequestDto calibrationScheduleRequestDomainToDto(CalibrationSchedule calibrationSchedule) {
        return new CalibrationScheduleRequestDto(
                calibrationSchedule.getNextCalibrationDate(),
                calibrationSchedule.getLastCalibrationDate(),
                calibrationSchedule.getCalibrationFrequencyInYears(),
                calibrationSchedule.getComments(),
                calibrationSchedule.getCalibrationStatus().name()
        );
    }

    public static CalibrationSchedule calibrationScheduleResponseDtoToDomain(CalibrationScheduleResponseDto calibrationSchedule) {

        return new CalibrationSchedule(
                calibrationSchedule.getCalibrationId(),
                calibrationSchedule.getNextCalibrationDate(),
                calibrationSchedule.getLastCalibrationDate(),
                calibrationSchedule.getCalibrationFrequencyInYears(),
                calibrationSchedule.getComments(),
                CalibrationStatus.valueOf(calibrationSchedule.getCalibrationStatus())
        );

    }
}
