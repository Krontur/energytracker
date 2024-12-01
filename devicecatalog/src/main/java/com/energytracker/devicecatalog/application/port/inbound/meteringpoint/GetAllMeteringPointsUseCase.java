package com.energytracker.devicecatalog.application.port.inbound.meteringpoint;

import com.energytracker.devicecatalog.application.dto.meteringpoint.MeteringPointResponseDto;

import java.util.List;

public interface GetAllMeteringPointsUseCase {
    List<MeteringPointResponseDto> getAllMeteringPoints();
}
