package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.mapper;

import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.dto.CreateEnergyMeterRequestRestDto;
import com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.dto.EnergyMeterResponseRestDto;

public class EnergyMeterRestMapper {
    public static EnergyMeterResponseRestDto energyMeterResponseDtoToRestDto(EnergyMeterResponseDto energyMeterResponseDto) {

        return new EnergyMeterResponseRestDto(
                energyMeterResponseDto.getEnergyMeterId(),
                energyMeterResponseDto.getSerialNumber(),
                energyMeterResponseDto.getDeviceType(),
                energyMeterResponseDto.getConnectionAddress(),
                energyMeterResponseDto.getEnergyMeterType(),
                energyMeterResponseDto.getReferenceVoltage(),
                energyMeterResponseDto.getConnectionType(),
                energyMeterResponseDto.getMaxCurrent(),
                energyMeterResponseDto.getMidApprovalYear(),
                energyMeterResponseDto.getCreatedAt(),
                energyMeterResponseDto.getUpdatedAt()
        );

    };

    public static CreateEnergyMeterRequestDto createRequestEnergyMeterRestDtoToDto(CreateEnergyMeterRequestRestDto createEnergyMeterRequestRestDto) {

        return new CreateEnergyMeterRequestDto(
            createEnergyMeterRequestRestDto.getSerialNumber(),
            createEnergyMeterRequestRestDto.getDeviceType(),
            createEnergyMeterRequestRestDto.getDeviceStatus(),
            createEnergyMeterRequestRestDto.getConnectionAddress(),
            createEnergyMeterRequestRestDto.getEnergyMeterType(),
            createEnergyMeterRequestRestDto.getReferenceVoltage(),
            createEnergyMeterRequestRestDto.getConnectionType(),
            createEnergyMeterRequestRestDto.getMaxCurrent(),
            createEnergyMeterRequestRestDto.getMidApprovalYear()
        );

    }

    public static CreateEnergyMeterRequestDto createEnergyMeterRequestFromRestDtoToDto(CreateEnergyMeterRequestRestDto createEnergyMeterRequestRestDto) {

        return new CreateEnergyMeterRequestDto(
            createEnergyMeterRequestRestDto.getSerialNumber(),
            createEnergyMeterRequestRestDto.getDeviceType(),
            createEnergyMeterRequestRestDto.getDeviceStatus(),
            createEnergyMeterRequestRestDto.getConnectionAddress(),
            createEnergyMeterRequestRestDto.getEnergyMeterType(),
            createEnergyMeterRequestRestDto.getReferenceVoltage(),
            createEnergyMeterRequestRestDto.getConnectionType(),
            createEnergyMeterRequestRestDto.getMaxCurrent(),
            createEnergyMeterRequestRestDto.getMidApprovalYear()
        );

    }
}
