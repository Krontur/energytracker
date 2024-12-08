package com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.consumptionservice.application.port.outbound.MeteringPointRepositoryPort;
import com.energytracker.consumptionservice.domain.model.MeteringPoint;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.entity.MeteringPointEntity;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.mapper.MeteringPointPersistenceMapper;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.repository.JpaMeteringPointPort;
import org.springframework.stereotype.Repository;

@Repository
public class MeteringPointRepositoryAdapter implements MeteringPointRepositoryPort {

    private final JpaMeteringPointPort jpaMeteringPointPort;

    public MeteringPointRepositoryAdapter(JpaMeteringPointPort jpaMeteringPointPort) {
        this.jpaMeteringPointPort = jpaMeteringPointPort;
    }

    @Override
    public MeteringPoint saveMeteringPoint(MeteringPoint meteringPoint) {
        if (meteringPoint == null) {
            return null;
        }
        MeteringPointEntity meteringPointEntity = jpaMeteringPointPort.save(
                MeteringPointPersistenceMapper.meteringPointDomainToEntity(meteringPoint));

        return MeteringPointPersistenceMapper.meteringPointEntityToDomain(meteringPointEntity);
    }
}
