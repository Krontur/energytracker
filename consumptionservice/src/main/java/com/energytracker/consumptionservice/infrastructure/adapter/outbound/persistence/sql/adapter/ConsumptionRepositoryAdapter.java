package com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.consumptionservice.application.port.outbound.ConsumptionRepositoryPort;
import com.energytracker.consumptionservice.domain.model.Consumption;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.entity.ConsumptionEntity;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.mapper.ConsumptionPersistenceMapper;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.repository.JpaConsumptionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConsumptionRepositoryAdapter implements ConsumptionRepositoryPort {

    private final JpaConsumptionPort jpaConsumptionPort;

    @Override
    public List<Consumption> findConsumptions(Long meteringPointId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<ConsumptionEntity> consumptionEntities = jpaConsumptionPort.findByMeteringPointIdAndConsumptionTimestampBetween(meteringPointId, startDateTime, endDateTime);
        List<Consumption> consumptions = new ArrayList<>();
        if(consumptionEntities != null) {
            consumptionEntities.forEach(consumptionEntity -> consumptions.add(
                    ConsumptionPersistenceMapper.consumptionEntityToDomain(consumptionEntity)));
        }
    return consumptions;
    }
}
