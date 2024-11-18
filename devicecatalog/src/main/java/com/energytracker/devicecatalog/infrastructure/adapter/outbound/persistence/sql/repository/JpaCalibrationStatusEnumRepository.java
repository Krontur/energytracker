package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository;

import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.CalibrationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCalibrationStatusEnumRepository extends JpaRepository<CalibrationStatusEnum, Long> {

}
