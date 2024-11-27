package com.energytracker.devicecatalog.application.service;

import com.energytracker.devicecatalog.application.dto.MeteringPointResponseDto;
import com.energytracker.devicecatalog.application.mapper.MeteringPointMapper;
import com.energytracker.devicecatalog.application.port.inbound.meteringpoint.GetMeteringPointsUseCase;
import com.energytracker.devicecatalog.application.port.outbound.MeteringPointRepositoryPort;
import com.energytracker.devicecatalog.domain.model.MeteringPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeteringPointService implements GetMeteringPointsUseCase {

    private final MeteringPointRepositoryPort meteringPointRepositoryPort;

    public List<MeteringPointResponseDto> getAllMeteringPoints() {
        List<MeteringPoint> meteringPoints = meteringPointRepositoryPort.getAllMeteringPoints();
        List<MeteringPointResponseDto> meteringPointResponseDtos = new ArrayList<>();
        if (meteringPoints == null || meteringPoints.isEmpty()) {
            return meteringPointResponseDtos;
        }
        meteringPoints.forEach(meteringPoint -> {
            meteringPointResponseDtos.add(
                    MeteringPointMapper.meteringPointDomainToResponseDto(meteringPoint)
                    );
        });
        return meteringPointResponseDtos;
    }

    @Override
    public List<MeteringPoint> getMeteringPoints() {
        return List.of();
    }
}
