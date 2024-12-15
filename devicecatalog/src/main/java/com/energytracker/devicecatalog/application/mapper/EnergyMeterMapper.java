package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.energymeter.CalibrationScheduleRequestDto;
import com.energytracker.devicecatalog.application.dto.energymeter.CalibrationScheduleResponseDto;
import com.energytracker.devicecatalog.application.dto.energymeter.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.energymeter.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.domain.model.*;
import com.energytracker.devicecatalog.domain.model.energymeter.CalibrationSchedule;
import com.energytracker.devicecatalog.domain.model.energymeter.ConnectionType;
import com.energytracker.devicecatalog.domain.model.energymeter.EnergyMeter;
import com.energytracker.devicecatalog.domain.model.energymeter.EnergyMeterType;

import java.util.ArrayList;
import java.util.List;

public class EnergyMeterMapper {
    public static CreateEnergyMeterRequestDto createEnergyMeterRequestDomainToDto(EnergyMeter energyMeter) {
        List<CalibrationScheduleRequestDto> calibrationScheduleRequestDtoList = new ArrayList<CalibrationScheduleRequestDto>();

        if(energyMeter.getCalibrationScheduleList().isEmpty()){
            throw new RuntimeException("Calibration Schedules are empty");
        }

        energyMeter.getCalibrationScheduleList().forEach(calibrationSchedule -> {
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
                DeviceType.valueOf(createEnergyMeterRequestDto.getDeviceType()),
                DeviceStatus.valueOf(createEnergyMeterRequestDto.getDeviceStatus()),
                createEnergyMeterRequestDto.getConnectionAddress(),
                EnergyMeterType.valueOf(createEnergyMeterRequestDto.getEnergyMeterType()),
                createEnergyMeterRequestDto.getReferenceVoltage(),
                ConnectionType.valueOf(createEnergyMeterRequestDto.getConnectionType()),
                createEnergyMeterRequestDto.getMaxCurrent(),
                createEnergyMeterRequestDto.getMidApprovalYear()
        );

    }
/*
    public static EnergyMeterRequestDto energyMeterRequestDomainToDto(EnergyMeter energyMeter) {

        List<CalibrationScheduleRequestDto> calibrationScheduleRequestDtoList = new ArrayList<CalibrationScheduleRequestDto>();

        if(energyMeter.getCalibrationSchedules().isEmpty()){
            throw new RuntimeException("Calibration Schedules are empty");
        }

        energyMeter.getCalibrationSchedules().forEach(calibrationSchedule -> {
            calibrationScheduleRequestDtoList.add(CalibrationScheduleMapper.calibrationScheduleRequestDomainToDto(calibrationSchedule));
        });

        return new EnergyMeterRequestDto(
                energyMeter.getDeviceId(),
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

    }

    public static EnergyMeter energyMeterResponseDtoToDomain(EnergyMeterResponseDto energyMeterResponseDto) {

        List<CalibrationSchedule> calibrationScheduleList = new ArrayList<CalibrationSchedule>();

        if(energyMeterResponseDto.getCalibrationSchedules().isEmpty()){
            throw new RuntimeException("Calibration Schedules are empty");
        }

        energyMeterResponseDto.getCalibrationSchedules().forEach(calibrationSchedule -> {
            calibrationScheduleList.add(CalibrationScheduleMapper.calibrationScheduleResponseDtoToDomain(calibrationSchedule));
        });

        return new EnergyMeter(
                energyMeterResponseDto.getEnergyMeterId(),
                energyMeterResponseDto.getSerialNumber(),
                DeviceType.valueOf(energyMeterResponseDto.getDeviceType()),
                DeviceStatus.valueOf(energyMeterResponseDto.getDeviceStatus()),
                energyMeterResponseDto.getConnectionAddress(),
                EnergyMeterType.valueOf(energyMeterResponseDto.getEnergyMeterType()),
                energyMeterResponseDto.getReferenceVoltage(),
                ConnectionType.valueOf(energyMeterResponseDto.getConnectionType()),
                energyMeterResponseDto.getMaxCurrent(),
                energyMeterResponseDto.getMidApprovalYear(),
                calibrationScheduleList
        );

    }*/

    public static EnergyMeterResponseDto energyMeterDomainToResponseDto(EnergyMeter energyMeter) {
        List<CalibrationScheduleResponseDto> calibrationScheduleResponseDtoList =
                new ArrayList<CalibrationScheduleResponseDto>();
        energyMeter.getCalibrationScheduleList().forEach(calibrationSchedule -> {
            calibrationScheduleResponseDtoList.add(CalibrationScheduleMapper.calibrationScheduleDomainToResponseDto(calibrationSchedule));
        });
        return new EnergyMeterResponseDto(
                energyMeter.getDeviceId(),
                energyMeter.getSerialNumber(),
                energyMeter.getCreatedAt(),
                energyMeter.getUpdatedAt(),
                energyMeter.getVersion(),
                energyMeter.getDeviceType().name(),
                energyMeter.getDeviceStatus().name(),
                energyMeter.getConnectionAddress(),
                energyMeter.getEnergyMeterType().name(),
                energyMeter.getReferenceVoltage(),
                energyMeter.getConnectionType().name(),
                energyMeter.getMaxCurrent(),
                energyMeter.getMidApprovalYear(),
                calibrationScheduleResponseDtoList
        );
    }

    public static EnergyMeter energyMeterResponseDtoToDomain(EnergyMeterResponseDto energyMeterResponseDto) {
        List<CalibrationSchedule> calibrationScheduleList = new ArrayList<CalibrationSchedule>();
        energyMeterResponseDto.getCalibrationSchedules().forEach(calibrationSchedule -> {
            calibrationScheduleList.add(CalibrationScheduleMapper.calibrationScheduleResponseDtoToDomain(calibrationSchedule));
        });
        return new EnergyMeter(
                energyMeterResponseDto.getEnergyMeterId(),
                energyMeterResponseDto.getSerialNumber(),
                energyMeterResponseDto.getCreatedAt(),
                energyMeterResponseDto.getUpdatedAt(),
                energyMeterResponseDto.getVersion(),
                DeviceType.valueOf(energyMeterResponseDto.getDeviceType()),
                DeviceStatus.valueOf(energyMeterResponseDto.getDeviceStatus()),
                energyMeterResponseDto.getConnectionAddress(),
                EnergyMeterType.valueOf(energyMeterResponseDto.getEnergyMeterType()),
                energyMeterResponseDto.getReferenceVoltage(),
                ConnectionType.valueOf(energyMeterResponseDto.getConnectionType()),
                energyMeterResponseDto.getMaxCurrent(),
                energyMeterResponseDto.getMidApprovalYear(),
                calibrationScheduleList
        );

    }

    public static CreateEnergyMeterRequestDto energyMeterDomainToCreateRequestDto(EnergyMeter energyMeter) {
        return new CreateEnergyMeterRequestDto(
                energyMeter.getSerialNumber(),
                energyMeter.getDeviceType().name(),
                energyMeter.getDeviceStatus().name(),
                energyMeter.getConnectionAddress(),
                energyMeter.getEnergyMeterType().name(),
                energyMeter.getReferenceVoltage(),
                energyMeter.getConnectionType().name(),
                energyMeter.getMaxCurrent(),
                energyMeter.getMidApprovalYear()
        );
    }
}
