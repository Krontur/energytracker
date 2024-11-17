package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.CalibrationScheduleDto;
import com.energytracker.devicecatalog.domain.model.CalibrationSchedule;

public class CalibrationScheduleMapper {

    public static CalibrationScheduleDto calibrationScheduleDomainToDto(CalibrationSchedule calibrationSchedule) {
        return new CalibrationScheduleDto(
                calibrationSchedule.getCalibrationId(),
                calibrationSchedule.getEnergyMeter().getDeviceId(),
                calibrationSchedule.getNextCalibrationDate(),
                calibrationSchedule.getLastCalibrationDate(),
                calibrationSchedule.getCalibrationFrequencyInYears(),
                calibrationSchedule.getComments(),
                calibrationSchedule.getCalibrationStatus().name()
        );
    }

}
