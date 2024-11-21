package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.CalibrationScheduleRequestDto;
import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.domain.model.EnergyMeter;

import java.util.ArrayList;
import java.util.List;

public class EnergyMeterMapper {
    public static CreateEnergyMeterRequestDto createEnergyMeterRequestDomainToDto(EnergyMeter energyMeter) {
        List<CalibrationScheduleRequestDto> calibrationScheduleRequestDtoList = new ArrayList<CalibrationScheduleRequestDto>();

        if(energyMeter.getCalibrationSchedules().isEmpty()){
            throw new RuntimeException("Calibration Schedules are empty");
        }

        energyMeter.getCalibrationSchedules().forEach(calibrationSchedule -> {
            calibrationScheduleRequestDtoList.add(CalibrationScheduleMapper.calibrationScheduleRequestDomainToDto(calibrationSchedule));
        });

        return new CreateEnergyMeterRequestDto(
                energyMeter.getSerialNumber(),
                energyMeter.getDeviceType().name(),
                energyMeter.getDeviceStatus().name(),
                energyMeter.getConnectionAddress(),
                energyMeter.getEnergyMeterType().name(),
                energyMeter.getReferenceVoltage(),
                energyMeter.getConnectionType().name(),
                energyMeter.getMaxCurrent(),
                energyMeter.getMidApprovalYear(),
                calibrationScheduleRequestDtoList
        );
    };

    public static EnergyMeter createEnergyMeterRequestDtoToDomain(CreateEnergyMeterRequestDto createEnergyMeterRequestDto) {
        return new EnergyMeter(
                createEnergyMeterRequestDto.getSerialNumber(),
                createEnergyMeterRequestDto.getDeviceType(),
                createEnergyMeterRequestDto.getDeviceStatus(),
                createEnergyMeterRequestDto.getConnectionAddress(),
                createEnergyMeterRequestDto.getEnergyMeterType(),
                createEnergyMeterRequestDto.getReferenceVoltage(),
                createEnergyMeterRequestDto.getConnectionType(),
                createEnergyMeterRequestDto.getMaxCurrent(),
                createEnergyMeterRequestDto.getMidApprovalYear()
        );

    }
}
