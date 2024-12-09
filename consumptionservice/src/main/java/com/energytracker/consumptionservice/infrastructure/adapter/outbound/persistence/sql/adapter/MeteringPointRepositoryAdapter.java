package com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.consumptionservice.application.port.outbound.MeteringPointRepositoryPort;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.entity.MeteringPointEntity;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.mapper.MeteringPointPersistenceMapper;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.repository.JpaMeteringPointPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Log4j2
@Repository
public class MeteringPointRepositoryAdapter implements MeteringPointRepositoryPort {

    private final JpaMeteringPointPort jpaMeteringPointPort;

    public MeteringPointRepositoryAdapter(JpaMeteringPointPort jpaMeteringPointPort) {
        this.jpaMeteringPointPort = jpaMeteringPointPort;
    }

    @Override
    public MeteringPoint saveMeteringPoint(MeteringPoint meteringPoint) {
        if (meteringPoint == null) {
            log.warn("Attempted to save a null MeteringPoint");
            return null;
        }
        try {
            Optional<MeteringPointEntity> existingMeteringPoint =
                    jpaMeteringPointPort.findById(meteringPoint.getMeteringPointId());

            if (existingMeteringPoint.isPresent()) {
                log.warn("MeteringPoint with ID {} already exists. Skipping save.", meteringPoint.getMeteringPointId());
                return MeteringPointPersistenceMapper.meteringPointEntityToDomain(existingMeteringPoint.get());
            }

            MeteringPointEntity meteringPointEntity = jpaMeteringPointPort.save(
                    MeteringPointPersistenceMapper.meteringPointDomainToEntity(meteringPoint));

            log.info("MeteringPoint saved successfully: {}", meteringPointEntity);
            return MeteringPointPersistenceMapper.meteringPointEntityToDomain(meteringPointEntity);
        } catch (Exception e) {
            log.error("Error saving MeteringPoint: {}", meteringPoint, e);
            throw new RuntimeException("Error saving MeteringPoint: " + meteringPoint, e);
        }
    }

    @Override
    public MeteringPoint deleteMeteringPoint(MeteringPoint meteringPoint) {
        if (meteringPoint == null || meteringPoint.getMeteringPointId() == null) {
            log.warn("Attempted to delete a null MeteringPoint or with null ID");
            return null;
        }
        try {
            Optional<MeteringPointEntity> optionalEntity = jpaMeteringPointPort.findById(meteringPoint.getMeteringPointId());

            if (optionalEntity.isPresent()) {
                MeteringPointEntity entity = optionalEntity.get();
                jpaMeteringPointPort.delete(entity);
                log.info("MeteringPoint deleted successfully: {}", entity);
                return MeteringPointPersistenceMapper.meteringPointEntityToDomain(entity);
            } else {
                log.warn("MeteringPoint not found with ID: {}", meteringPoint.getMeteringPointId());
                return null;
            }
        } catch (Exception e) {
            log.error("Error deleting MeteringPoint: {}", meteringPoint, e);
            throw new RuntimeException("Error deleting MeteringPoint: " + meteringPoint, e);
        }
    }
}
