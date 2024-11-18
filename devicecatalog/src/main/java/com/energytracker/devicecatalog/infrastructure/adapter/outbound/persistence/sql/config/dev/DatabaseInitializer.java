package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.config.dev;

import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.CalibrationStatusEnum;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository.JpaCalibrationStatusEnumRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;



@Component
@Profile("dev")
public class DatabaseInitializer {

    private final JpaCalibrationStatusEnumRepository calibrationStatusEnumRepository;

    public DatabaseInitializer(JpaCalibrationStatusEnumRepository calibrationStatusEnumRepository) {
        this.calibrationStatusEnumRepository = calibrationStatusEnumRepository;
    }

    private enum CalibrationStatus {
        PENDING, COMPLETED, FAILED;
    }

    @PostConstruct
    @Transactional
    public void init() {
        if (calibrationStatusEnumRepository.count() == 0) {
            for (CalibrationStatus status : CalibrationStatus.values()) {
                CalibrationStatusEnum calibrationStatusEnum = new CalibrationStatusEnum();
                calibrationStatusEnum.setCalibrationStatus(status.name());
                calibrationStatusEnumRepository.save(calibrationStatusEnum);
            }
        }
    }

}
