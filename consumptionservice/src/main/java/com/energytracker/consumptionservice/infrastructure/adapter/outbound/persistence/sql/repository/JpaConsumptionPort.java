package com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.repository;

import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.entity.ConsumptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.time.LocalDateTime;

public interface JpaConsumptionPort extends JpaRepository<ConsumptionEntity, Long> {

    List<ConsumptionEntity> findByMeteringPointId(Long meteringPointId);

    List<ConsumptionEntity> findByMeteringPointIdAndConsumptionTimestampBetween(Long meteringPointId, LocalDateTime start, LocalDateTime end);

}