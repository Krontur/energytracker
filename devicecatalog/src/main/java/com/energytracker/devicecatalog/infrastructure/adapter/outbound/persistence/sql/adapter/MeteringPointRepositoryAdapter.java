package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.devicecatalog.application.port.outbound.MeteringPointRepositoryPort;
import com.energytracker.devicecatalog.domain.model.MeteringPoint;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.MeteringPointEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper.MeteringPointPersistenceMapper;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository.JpaMeteringPoint;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class MeteringPointRepositoryAdapter implements MeteringPointRepositoryPort {

    private JpaMeteringPoint jpaMeteringPoint;

    @Override
    public List<MeteringPoint> getAllMeteringPoints() {
        List<MeteringPointEntity> meteringPointEntities = jpaMeteringPoint.findAll();
        if (meteringPointEntities.isEmpty()) {
            return null;
        }
        List<MeteringPoint> meteringPoints = new ArrayList<MeteringPoint>();
        meteringPointEntities.forEach(meteringPointEntity -> {
            meteringPoints.add(MeteringPointPersistenceMapper.meteringPointEntityToDomain(meteringPointEntity));
        });
        return meteringPoints;
    }
}
