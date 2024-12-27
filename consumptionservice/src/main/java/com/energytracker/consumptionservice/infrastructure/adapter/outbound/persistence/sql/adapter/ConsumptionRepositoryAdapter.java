package com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.consumptionservice.application.dto.DailyConsumptionDto;
import com.energytracker.consumptionservice.application.dto.MonthlyConsumptionDto;
import com.energytracker.consumptionservice.application.dto.YearlyConsumptionDto;
import com.energytracker.consumptionservice.application.mapper.ConsumptionMapper;
import com.energytracker.consumptionservice.application.port.outbound.ConsumptionRepositoryPort;
import com.energytracker.consumptionservice.domain.model.Consumption;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.entity.ConsumptionEntity;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.mapper.ConsumptionPersistenceMapper;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.repository.JpaConsumptionPort;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Repository
@RequiredArgsConstructor
public class ConsumptionRepositoryAdapter implements ConsumptionRepositoryPort {

    private final JpaConsumptionPort jpaConsumptionPort;

    @Override
    public List<Consumption> findConsumptionsByMeteringPointIdAndInterval(Long meteringPointId,
                                                                          LocalDateTime startDateTime,
                                                                          LocalDateTime endDateTime) {
        List<ConsumptionEntity> consumptionEntities = jpaConsumptionPort.findConsumptionEntitiesByMeteringPointIdAndConsumptionTimestampBetweenOrderByConsumptionTimestampAsc(
                meteringPointId, startDateTime, endDateTime);
        List<Consumption> consumptions = new ArrayList<>();
        if(consumptionEntities != null) {
            consumptionEntities.forEach(consumptionEntity -> consumptions.add(
                    ConsumptionPersistenceMapper.consumptionEntityToDomain(consumptionEntity)));
        }
    return consumptions;
    }

    @Override
    @Transactional
    public Consumption saveConsumption(Consumption consumption) {
        if (consumption == null) {
            log.error("NullPointerException, Parameter consumption is null");
            return null;
        }
        ConsumptionEntity existingConsumptionEntity  = jpaConsumptionPort.findConsumptionByMeteringPointIdAndConsumptionTimestamp(
                consumption.getMeteringPointId(), consumption.getConsumptionTimestamp());
        if (existingConsumptionEntity  != null) {
            log.error("Consumption already exists: {}", existingConsumptionEntity );
            throw new EntityExistsException(String.valueOf(existingConsumptionEntity));
        }
        ConsumptionEntity newConsumptionEntity = ConsumptionPersistenceMapper.consumptionDomainToEntity(consumption);
        ConsumptionEntity savedConsumptionEntity = jpaConsumptionPort.save(newConsumptionEntity);
        return ConsumptionPersistenceMapper.consumptionEntityToDomain(savedConsumptionEntity);
    }

    @Override
    @Transactional
    public List<Consumption> saveAllConsumptions(List<Consumption> consumptions) {
        List<Consumption> savedConsumptions = new ArrayList<>();

        if (consumptions == null || consumptions.isEmpty()) {
            log.error("Parameter consumptions is null or empty");
            return savedConsumptions;
        }
        List<ConsumptionEntity> newConsumptionEntities = new ArrayList<>();
        consumptions.forEach(consumption -> {
            boolean exists = jpaConsumptionPort.existsByMeteringPointIdAndConsumptionTimestamp(
                    consumption.getMeteringPointId(), consumption.getConsumptionTimestamp());
            if (exists) {
                log.error("Consumption already exists: {} at {}", consumption.getMeteringPointId(), consumption.getConsumptionTimestamp());
                throw new EntityExistsException("Consumption already exists for: " + consumption.getMeteringPointId());
            }
            ConsumptionEntity consumptionEntity = ConsumptionPersistenceMapper.consumptionDomainToEntity(consumption);
            newConsumptionEntities.add(consumptionEntity);
        });
        List<ConsumptionEntity> savedEntities = jpaConsumptionPort.saveAll(newConsumptionEntities);
        savedEntities.forEach(entity -> savedConsumptions.add(ConsumptionPersistenceMapper.consumptionEntityToDomain(entity)));
        return savedConsumptions;
    }



    @Override
    public List<Consumption> findConsumptionsByMeteringPointId(Long meteringPointId) {
        List<ConsumptionEntity> consumptionEntities = jpaConsumptionPort.findByMeteringPointId(meteringPointId);
        List<Consumption> consumptions = new ArrayList<>();
        if(consumptionEntities != null) {
            consumptionEntities.forEach(consumptionEntity -> consumptions.add(
                    ConsumptionPersistenceMapper.consumptionEntityToDomain(consumptionEntity)));
        }
        return consumptions;
    }

    @Override
    public List<Consumption> findDailyConsumptionsByMeteringPointId(Long meteringPointId, LocalDateTime startDate, LocalDateTime endDate) {
        List<DailyConsumptionDto> dailyConsumptionDtos = jpaConsumptionPort.findDailyConsumptionByMeteringPointId(
                meteringPointId, startDate, endDate);
        List<Consumption> consumptions = new ArrayList<>();
        if(dailyConsumptionDtos != null) {
            dailyConsumptionDtos.forEach(dailyConsumptionDto -> consumptions.add(
                    ConsumptionMapper.consumptionDailyDtoToDomain(dailyConsumptionDto)));
        }
        return consumptions;
    }

    @Override
    public List<Consumption> findMonthlyConsumptionsByMeteringPointId(Long meteringPointId, LocalDate startDate, LocalDate endDate) {
        List<MonthlyConsumptionDto> monthlyConsumptionDtos = jpaConsumptionPort.findMonthlyConsumptionByMeteringPointId(
                meteringPointId, startDate.atStartOfDay(), endDate.atStartOfDay());
        List<Consumption> consumptions = new ArrayList<>();
        if(monthlyConsumptionDtos != null) {
            monthlyConsumptionDtos.forEach(monthlyConsumptionDto -> consumptions.add(
                    ConsumptionMapper.consumptionMonthlyDtoToDomain(monthlyConsumptionDto)));
        }
        return consumptions;
    }

    @Override
    public List<Consumption> findYearlyConsumptionsByMeteringPointId(Long meteringPointId, int startYear, int endYear) {
        List<YearlyConsumptionDto> yearlyConsumptionDtos = jpaConsumptionPort.findYearlyConsumptionByMeteringPointId(
                meteringPointId, LocalDateTime.of(startYear, 1, 1, 0, 0), LocalDateTime.of(endYear, 12, 31, 23, 59));
        List<Consumption> consumptions = new ArrayList<>();
        if(yearlyConsumptionDtos != null) {
            yearlyConsumptionDtos.forEach(yearlyConsumptionDto -> consumptions.add(
                    ConsumptionMapper.consumptionYearlyDtoToDomain(yearlyConsumptionDto)));
        }
        return consumptions;
    }


}
