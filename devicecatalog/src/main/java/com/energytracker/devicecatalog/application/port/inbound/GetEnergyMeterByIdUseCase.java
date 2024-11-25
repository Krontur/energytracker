package com.energytracker.devicecatalog.application.port.inbound;

import com.energytracker.devicecatalog.application.dto.CalibrationScheduleResponseDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;

import java.util.List;

public interface GetEnergyMeterByIdUseCase {

    EnergyMeterResponseDto getEnergyMeterById(Long energyMeterId);
}
