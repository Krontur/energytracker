package com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.repository;

import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.entity.MeteringPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMeteringPointPort extends JpaRepository<MeteringPointEntity, Long> {

}
