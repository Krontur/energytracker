package com.energytracker.consumptionservice.application.service;

import com.energytracker.consumptionservice.application.dto.ConsumptionDto;
import com.energytracker.consumptionservice.application.dto.ConsumptionQueryDto;
import com.energytracker.consumptionservice.application.mapper.ConsumptionMapper;
import com.energytracker.consumptionservice.application.port.inbound.ConsumptionMessageHandlerPort;
import com.energytracker.consumptionservice.application.port.inbound.GetConsumptionsByMeteringPointIdAndIntervalUseCase;
import com.energytracker.consumptionservice.application.port.inbound.GetConsumptionsByMeteringPointIdUseCase;
import com.energytracker.consumptionservice.application.port.outbound.ConsumptionRepositoryPort;
import com.energytracker.consumptionservice.application.port.outbound.QueueMessagingPort;
import com.energytracker.consumptionservice.domain.model.Consumption;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class ConsumptionService implements GetConsumptionsByMeteringPointIdUseCase, GetConsumptionsByMeteringPointIdAndIntervalUseCase, ConsumptionMessageHandlerPort {

    private final ConsumptionRepositoryPort consumptionRepositoryPort;
    private final QueueMessagingPort queueMessagingPort;
    private final ObjectMapper objectMapper;

    public ConsumptionService(ConsumptionRepositoryPort consumptionRepositoryPort, QueueMessagingPort queueMessagingPort, ObjectMapper objectMapper) {
        this.consumptionRepositoryPort = consumptionRepositoryPort;
        this.queueMessagingPort = queueMessagingPort;
        this.objectMapper = objectMapper;
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
    public void receiveMessage(Consumption consumption) {

        log.info("Received message: {}", consumption);

        if (consumption == null) {
            log.warn("Received a null Consumption. Ignoring message.");
            return;
        }

        try {
            log.info("Saving consumption to the database...");
            consumptionRepositoryPort.saveConsumption(consumption);
            log.info("Consumption saved successfully: {}", consumption);
        } catch (DataAccessException e) {
            log.error("Database error occurred while saving consumption: {}", consumption, e);
        } catch (Exception e) {
            log.error("Unexpected error processing consumption: {}", consumption, e);
        }
    }
}
