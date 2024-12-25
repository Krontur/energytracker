package com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.consumptionservice.application.port.outbound.MeteringPointRepositoryPort;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.entity.MeteringPointEntity;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.mapper.MeteringPointPersistenceMapper;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.repository.JpaMeteringPointPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
@RequiredArgsConstructor
public class MeteringPointRepositoryAdapter implements MeteringPointRepositoryPort {

    private final JpaMeteringPointPort jpaMeteringPointPort;

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

    @Override
    public List<MeteringPoint> getAllMeteringPoints() {
        List<MeteringPointEntity> meteringPointEntities = jpaMeteringPointPort.findAll();
        if (meteringPointEntities == null || meteringPointEntities.isEmpty()) {
            log.warn("No MeteringPoints found");
            return List.of();
        }
        List<MeteringPoint> meteringPoints = new ArrayList<>();
        meteringPointEntities.forEach(meteringPointEntity -> {
            meteringPoints.add(MeteringPointPersistenceMapper.meteringPointEntityToDomain(meteringPointEntity));
        });

        return meteringPoints;
    }
}
