package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.mapper;

import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.dto.CreateEnergyMeterRequestRestDto;
import com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.dto.EnergyMeterResponseRestDto;

public class EnergyMeterRestMapper {
    public static EnergyMeterResponseRestDto energyMeterResponseDtoToRestDto(EnergyMeterResponseDto createdEnergyMeter) {

        return new EnergyMeterResponseRestDto(
                createdEnergyMeter.getEnergyMeterId(),
                createdEnergyMeter.getSerialNumber(),
                createdEnergyMeter.getDeviceType(),
                createdEnergyMeter.getConnectionAddress(),
                createdEnergyMeter.getEnergyMeterType(),
                createdEnergyMeter.getReferenceVoltage(),
                createdEnergyMeter.getConnectionType(),
                createdEnergyMeter.getMaxCurrent(),
                createdEnergyMeter.getMidApprovalYear(),
                createdEnergyMeter.getCreatedAt(),
                createdEnergyMeter.getUpdatedAt()
        );

    };

    public static CreateEnergyMeterRequestDto createRequestEnergyMeterRestDtoToDto(CreateEnergyMeterRequestRestDto createEnergyMeterRequestRestDto) {

        return new CreateEnergyMeterRequestDto(
            createEnergyMeterRequestRestDto.getSerialNumber(),
            createEnergyMeterRequestRestDto.getDeviceType(),
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
            createEnergyMeterRequestRestDto.getConnectionAddress(),
            createEnergyMeterRequestRestDto.getEnergyMeterType(),
            createEnergyMeterRequestRestDto.getReferenceVoltage(),
            createEnergyMeterRequestRestDto.getConnectionType(),
            createEnergyMeterRequestRestDto.getMaxCurrent(),
            createEnergyMeterRequestRestDto.getMidApprovalYear()
        );

    }
}
