package com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.consumptionservice.application.port.outbound.ConsumptionRepositoryPort;
import com.energytracker.consumptionservice.domain.model.Consumption;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.entity.ConsumptionEntity;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.mapper.ConsumptionPersistenceMapper;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.repository.JpaConsumptionPort;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

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
        List<ConsumptionEntity> consumptionEntities = jpaConsumptionPort.findByMeteringPointIdAndConsumptionTimestampBetween(
                meteringPointId, startDateTime, endDateTime);
        List<Consumption> consumptions = new ArrayList<>();
        if(consumptionEntities != null) {
            consumptionEntities.forEach(consumptionEntity -> consumptions.add(
                    ConsumptionPersistenceMapper.consumptionEntityToDomain(consumptionEntity)));
        }
    return consumptions;
    }

    @Override
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
}
