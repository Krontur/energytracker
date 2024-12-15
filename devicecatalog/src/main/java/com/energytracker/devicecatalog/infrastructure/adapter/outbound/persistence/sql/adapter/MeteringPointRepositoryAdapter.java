package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.devicecatalog.application.port.outbound.MeteringPointRepositoryPort;
import com.energytracker.devicecatalog.domain.model.MeteringPoint;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.MeteringPointEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper.MeteringPointPersistenceMapper;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository.JpaMeteringPointPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class MeteringPointRepositoryAdapter implements MeteringPointRepositoryPort {

    private JpaMeteringPointPort jpaMeteringPointPort;

    @Override
    public List<MeteringPoint> getAllMeteringPoints() {
        List<MeteringPointEntity> meteringPointEntities = jpaMeteringPointPort.findAll();
        if (meteringPointEntities.isEmpty()) {
            return null;
        }
        List<MeteringPoint> meteringPoints = new ArrayList<MeteringPoint>();
        meteringPointEntities.forEach(meteringPointEntity -> {
            meteringPoints.add(MeteringPointPersistenceMapper.meteringPointEntityToDomain(meteringPointEntity));
        });
        return meteringPoints;
    }

    @Override
    public MeteringPoint createMeteringPoint(MeteringPoint meteringPoint) {
        MeteringPointEntity meteringPointEntity = MeteringPointPersistenceMapper.meteringPointDomainToEntity(meteringPoint);
        MeteringPointEntity createdMeteringPointEntity = jpaMeteringPointPort.save(meteringPointEntity);
        return MeteringPointPersistenceMapper.meteringPointEntityToDomain(createdMeteringPointEntity);
    }

    @Override
    public MeteringPoint getMeteringPointById(Long meteringPointId) {
        MeteringPointEntity meteringPointEntity = jpaMeteringPointPort.findById(meteringPointId).orElse(null);
        if (meteringPointEntity == null) {
            return null;
        }
        return MeteringPointPersistenceMapper.meteringPointEntityToDomain(meteringPointEntity);
    }

    @Override
    public MeteringPoint updateMeteringPointById(Long meteringPointId, MeteringPoint meteringPoint) {
        MeteringPointEntity meteringPointEntity = MeteringPointPersistenceMapper.meteringPointDomainToEntity(meteringPoint);
        int rowsAffected = jpaMeteringPointPort.updateMeteringPointFields(
                meteringPointId,
                meteringPointEntity.getLocationName(),
                meteringPointEntity.getConnectionDescription(),
                meteringPointEntity.getActiveStatus(),
                meteringPointEntity.getEnergyMeterEntity());
        if (rowsAffected == 0) {
            return null;
        }
        MeteringPointEntity updatedMeteringPointEntity = jpaMeteringPointPort.findById(meteringPointId).orElse(null);
        if (updatedMeteringPointEntity == null) {
            return null;
        }
        return MeteringPointPersistenceMapper.meteringPointEntityToDomain(updatedMeteringPointEntity);
    }
}
