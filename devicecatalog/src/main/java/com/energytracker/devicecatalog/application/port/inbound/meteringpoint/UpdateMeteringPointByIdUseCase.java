package com.energytracker.devicecatalog.application.port.inbound.meteringpoint;

import com.energytracker.devicecatalog.application.dto.meteringpoint.CreateMeteringPointRequestDto;
import com.energytracker.devicecatalog.application.dto.meteringpoint.MeteringPointResponseDto;
import com.energytracker.devicecatalog.domain.model.MeteringPoint;

public interface UpdateMeteringPointByIdUseCase {

    MeteringPointResponseDto updateMeteringPointById(Long meteringPointId, CreateMeteringPointRequestDto createMeteringPointRequestDto);

}
