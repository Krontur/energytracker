package com.energytracker.consumptionservice.application.service;

import com.energytracker.consumptionservice.application.dto.*;
import com.energytracker.consumptionservice.application.mapper.ConsumptionMapper;
import com.energytracker.consumptionservice.application.port.inbound.ConsumptionMessageHandlerPort;
import com.energytracker.consumptionservice.application.port.inbound.GetConsumptionsByMeteringPointIdAndIntervalUseCase;
import com.energytracker.consumptionservice.application.port.inbound.GetConsumptionsByMeteringPointIdUseCase;
import com.energytracker.consumptionservice.application.port.outbound.ConsumptionRepositoryPort;
import com.energytracker.consumptionservice.domain.model.Consumption;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ConsumptionService implements GetConsumptionsByMeteringPointIdUseCase,
        GetConsumptionsByMeteringPointIdAndIntervalUseCase, ConsumptionMessageHandlerPort {

    private final ConsumptionRepositoryPort consumptionRepositoryPort;

    @Override
    public List<ConsumptionDto> getConsumptionsByMeteringPointIdAndInterval(ConsumptionQueryDto queryDto) {

        return switch (queryDto.getIntervalType()) {
            case "INTERVAL" -> {
                log.info("Finding Consumptions by MeteringPointId and Interval");
                yield getIntervalConsumptionsByMeteringPointIdAndInterval(queryDto);
            }
            case "DAILY" -> {
                log.info("Finding Daily Consumptions by MeteringPointId");
                yield getDailyConsumptionsByMeteringPointIdAndInterval(queryDto);
            }
            case "MONTHLY" -> {
                log.info("Finding Monthly Consumptions by MeteringPointId");
                yield getMonthlyConsumptionsByMeteringPointIdAndInterval(queryDto);
            }
            case "YEARLY" -> {
                log.info("Finding Yearly Consumptions by MeteringPointId");
                yield getYearlyConsumptionsByMeteringPointIdAndInterval(queryDto);
            }
            default -> {
                log.warn("Invalid Interval Type: {}", queryDto.getIntervalType());
                yield new ArrayList<>();
            }
        };
    }

    @Override
    public List<ConsumptionDto> getIntervalConsumptionsByMeteringPointIdAndInterval(ConsumptionQueryDto queryDto) {
        List<ConsumptionDto> consumptionDtos = new ArrayList<>();
        if (queryDto != null) {
            List<Consumption> consumptions = null;
            consumptions = consumptionRepositoryPort.findConsumptionsByMeteringPointIdAndInterval(
                    queryDto.getMeteringPointId(), queryDto.getStartDateTime(), queryDto.getEndDateTime());


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
    public List<ConsumptionDto> getDailyConsumptionsByMeteringPointIdAndInterval(ConsumptionQueryDto queryDto) {
        List<Consumption> consumptions = consumptionRepositoryPort.findDailyConsumptionsByMeteringPointId(
                queryDto.getMeteringPointId(), queryDto.getStartDateTime(), queryDto.getEndDateTime());
        List<ConsumptionDto> consumptionDtos = new ArrayList<>();
        if (consumptions != null) {
            consumptions.forEach(
                    consumption -> consumptionDtos.add(
                            ConsumptionMapper.consumptionDomainToDto(consumption)
                    )
            );
        }
        return consumptionDtos;
    }

    @Override
    public List<ConsumptionDto> getMonthlyConsumptionsByMeteringPointIdAndInterval(ConsumptionQueryDto queryDto) {
        List<Consumption> consumptions = consumptionRepositoryPort.findMonthlyConsumptionsByMeteringPointId(
                queryDto.getMeteringPointId(), queryDto.getStartDateTime().toLocalDate(), queryDto.getEndDateTime().toLocalDate());
        List<ConsumptionDto> consumptionDtos = new ArrayList<>();
        if (consumptions != null) {
            consumptions.forEach(
                    consumption -> consumptionDtos.add(
                            ConsumptionMapper.consumptionDomainToDto(consumption)
                    )
            );
        }
        return consumptionDtos;
    }

    @Override
    public List<ConsumptionDto> getYearlyConsumptionsByMeteringPointIdAndInterval(ConsumptionQueryDto queryDto) {
        List<Consumption> consumptions = consumptionRepositoryPort.findYearlyConsumptionsByMeteringPointId(
                queryDto.getMeteringPointId(), queryDto.getStartDateTime().getYear(), queryDto.getEndDateTime().getYear());
        List<ConsumptionDto> consumptionDtos = new ArrayList<>();
        if (consumptions != null) {
            consumptions.forEach(
                    consumption -> consumptionDtos.add(
                            ConsumptionMapper.consumptionDomainToDto(consumption)
                    )
            );
        }
        return consumptionDtos;
    }

    @Override
    public List<ConsumptionDto> getConsumptionsByMeteringPointId(Long meteringPointId) {
        List<ConsumptionDto> consumptionDtos = new ArrayList<>();
        if (meteringPointId != null) {
            List<Consumption> consumptions = consumptionRepositoryPort.findConsumptionsByMeteringPointId(meteringPointId);
            log.info("Finding Consumptions by MeteringPointId");
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
    public void receiveMessage(List<Consumption> consumptions) {

        log.info("Received message: {}", consumptions);

        if (consumptions == null) {
            log.warn("Received a null Consumption. Ignoring message.");
            return;
        }

        try {
            log.info("Saving consumption to the database...");
            consumptionRepositoryPort.saveAllConsumptions(consumptions);
            log.info("Consumption saved successfully: {}", consumptions);
        } catch (DataAccessException e) {
            log.error("Database error occurred while saving consumption: {}", consumptions, e);
        } catch (Exception e) {
            log.error("Unexpected error processing consumption: {}", consumptions, e);
        }
    }
}
