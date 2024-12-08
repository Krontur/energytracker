package com.energytracker.consumptionservice.application.service;

import com.energytracker.consumptionservice.application.dto.ConsumptionDto;
import com.energytracker.consumptionservice.application.dto.ConsumptionQueryDto;
import com.energytracker.consumptionservice.application.mapper.ConsumptionMapper;
import com.energytracker.consumptionservice.application.port.inbound.GetConsumptionsByMeteringPointIdAndIntervalUseCase;
import com.energytracker.consumptionservice.application.port.inbound.GetConsumptionsByMeteringPointIdUseCase;
import com.energytracker.consumptionservice.application.port.outbound.ConsumptionRepositoryPort;
import com.energytracker.consumptionservice.domain.model.Consumption;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumptionService implements GetConsumptionsByMeteringPointIdUseCase, GetConsumptionsByMeteringPointIdAndIntervalUseCase {

    private final ConsumptionRepositoryPort consumptionRepositoryPort;

    public ConsumptionService(ConsumptionRepositoryPort consumptionRepositoryPort) {
        this.consumptionRepositoryPort = consumptionRepositoryPort;
    }

    @Override
    public List<ConsumptionDto> getConsumptionsByMeteringPointIdAndInterval(ConsumptionQueryDto queryDto) {
        List<ConsumptionDto> consumptionDtos = new ArrayList<>();
        if (queryDto != null) {
            List<Consumption> consumptions = consumptionRepositoryPort.findConsumptions(queryDto.getMeteringPointId(), queryDto.getStartDateTime(), queryDto.getEndDateTime());
            if (consumptions != null) {
                consumptions.forEach(
                        consumption -> consumptionDtos.add(
                                ConsumptionMapper.consumptionDomainToDto(consumption)
                        )
                );
            }

        }
        return consumptionDtos;
    }

    @Override
    public List<ConsumptionDto> getConsumptionsByMeteringPointId(Long meteringPointId) {
        return List.of();
    }
}
