package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.CalibrationScheduleRequestDto;
import com.energytracker.devicecatalog.domain.model.CalibrationSchedule;

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

}
