package com.energytracker.consumptionservice.application.service;

import com.energytracker.consumptionservice.application.dto.MeteringPointDto;
import com.energytracker.consumptionservice.application.mapper.MeteringPointMapper;
import com.energytracker.consumptionservice.application.port.inbound.GetAllMeteringPointsUseCase;
import com.energytracker.consumptionservice.application.port.outbound.MeteringPointRepositoryPort;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeteringPointService implements GetAllMeteringPointsUseCase {

    private final MeteringPointRepositoryPort meteringPointRepositoryPort;

    @Override
    public List<MeteringPointDto> getAllMeteringPoints() {
        List<MeteringPoint> meteringPoints = meteringPointRepositoryPort.getAllMeteringPoints();
        List<MeteringPointDto> meteringPointDtoList = new ArrayList<>();
        if (meteringPoints != null) {
            meteringPoints.forEach(
                    meteringPoint -> meteringPointDtoList.add(
                            MeteringPointMapper.meteringPointDomainToDto(meteringPoint)
                    )
            );
        }
        return meteringPointDtoList;
    }
}
