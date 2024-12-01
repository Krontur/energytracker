package com.energytracker.devicecatalog.application.port.inbound.meteringpoint;

import com.energytracker.devicecatalog.application.dto.meteringpoint.CreateMeteringPointRequestDto;
import com.energytracker.devicecatalog.application.dto.meteringpoint.MeteringPointResponseDto;

public interface CreateMeteringPointUseCase {
    MeteringPointResponseDto createMeteringPoint(CreateMeteringPointRequestDto createMeteringPointRequestDto);
}
