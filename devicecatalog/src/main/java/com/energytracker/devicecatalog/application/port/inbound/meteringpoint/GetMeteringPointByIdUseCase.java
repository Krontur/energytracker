package com.energytracker.devicecatalog.application.port.inbound.meteringpoint;

import com.energytracker.devicecatalog.application.dto.meteringpoint.MeteringPointResponseDto;

public interface GetMeteringPointByIdUseCase {

    MeteringPointResponseDto getMeteringPointById(Long meteringPointId);

}
