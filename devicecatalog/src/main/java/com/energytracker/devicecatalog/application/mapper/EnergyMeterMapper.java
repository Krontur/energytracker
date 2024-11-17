package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.CalibrationScheduleDto;
import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.domain.model.ConnectionType;
import com.energytracker.devicecatalog.domain.model.DeviceType;
import com.energytracker.devicecatalog.domain.model.EnergyMeter;

import java.util.ArrayList;
import java.util.List;

public class EnergyMeterMapper {
    public static CreateEnergyMeterRequestDto createEnergyMeterRequestDomainToDto(EnergyMeter energyMeter) {
        List<CalibrationScheduleDto> calibrationScheduleDtoList = new ArrayList<CalibrationScheduleDto>();

        energyMeter.getCalibrationSchedules().iterator().forEachRemaining(calibrationSchedule -> {
            calibrationScheduleDtoList.add(new CalibrationScheduleDto(
                    calibrationSchedule.getCalibrationId(),
                    calibrationSchedule.getEnergyMeter().getDeviceId(),
                    calibrationSchedule.getNextCalibrationDate(),
                    calibrationSchedule.getLastCalibrationDate(),
                    calibrationSchedule.getCalibrationFrequencyInYears(),
                    calibrationSchedule.getComments(),
                    calibrationSchedule.getCalibrationStatus().name()
            ));
        });

        return new CreateEnergyMeterRequestDto(
                energyMeter.getSerialNumber(),
                energyMeter.getDeviceType().name(),
                energyMeter.getConnectionAddress(),
                energyMeter.getEnergyMeterType(),
                energyMeter.getReferenceVoltage(),
                energyMeter.getConnectionType().name(),
                energyMeter.getMaxCurrent(),
                energyMeter.getMidApprovalYear(),
                calibrationScheduleDtoList
        );
    };

    public static EnergyMeter createEnergyMeterRequestDtoToDomain(CreateEnergyMeterRequestDto createEnergyMeterRequestDto) {
        return new EnergyMeter(
                createEnergyMeterRequestDto.getSerialNumber(),
                DeviceType.valueOf(createEnergyMeterRequestDto.getDeviceType()),
                createEnergyMeterRequestDto.getConnectionAddress(),
                createEnergyMeterRequestDto.getEnergyMeterType(),
                createEnergyMeterRequestDto.getReferenceVoltage(),
                ConnectionType.valueOf(createEnergyMeterRequestDto.getConnectionType()),
                createEnergyMeterRequestDto.getMaxCurrent(),
                createEnergyMeterRequestDto.getMidApprovalYear()
        );

    }
}
