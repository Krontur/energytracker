package com.energytracker.consumptionservice.application.service;

import com.energytracker.consumptionservice.application.dto.ConsumptionDto;
import com.energytracker.consumptionservice.application.dto.ConsumptionQueryDto;
import com.energytracker.consumptionservice.application.mapper.ConsumptionMapper;
import com.energytracker.consumptionservice.application.port.inbound.ConsumptionMessageHandlerPort;
import com.energytracker.consumptionservice.application.port.inbound.GetConsumptionsByMeteringPointIdAndIntervalUseCase;
import com.energytracker.consumptionservice.application.port.inbound.GetConsumptionsByMeteringPointIdUseCase;
import com.energytracker.consumptionservice.application.port.outbound.ConsumptionRepositoryPort;
import com.energytracker.consumptionservice.domain.model.Consumption;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class ConsumptionService implements GetConsumptionsByMeteringPointIdUseCase,
        GetConsumptionsByMeteringPointIdAndIntervalUseCase, ConsumptionMessageHandlerPort {

    private final ConsumptionRepositoryPort consumptionRepositoryPort;

    public ConsumptionService(ConsumptionRepositoryPort consumptionRepositoryPort) {
        this.consumptionRepositoryPort = consumptionRepositoryPort;
    }

    @Override
    public List<ConsumptionDto> getConsumptionsByMeteringPointIdAndInterval(ConsumptionQueryDto queryDto) {
        List<ConsumptionDto> consumptionDtos = new ArrayList<>();
        if (queryDto != null) {
            List<Consumption> consumptions = consumptionRepositoryPort.findConsumptionsByMeteringPointIdAndInterval(queryDto.getMeteringPointId(), queryDto.getStartDateTime(), queryDto.getEndDateTime());
            log.info("Finding Consumptions by MeteringPointId and Interval");
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

    @Override
    public void receiveMessage(List<Consumption> consumptions) {

        log.info("Received message: {}", consumptions);

        if (consumptions == null) {
            log.warn("Received a null Consumption. Ignoring message.");
            return;
        }

        try {
            log.info("Saving consumption to the database...");
            consumptions.forEach( consumption -> {
                consumptionRepositoryPort.saveConsumption(consumption);
            });
            log.info("Consumption saved successfully: {}", consumptions);
        } catch (DataAccessException e) {
            log.error("Database error occurred while saving consumption: {}", consumptions, e);
        } catch (Exception e) {
            log.error("Unexpected error processing consumption: {}", consumptions, e);
        }
    }
}
