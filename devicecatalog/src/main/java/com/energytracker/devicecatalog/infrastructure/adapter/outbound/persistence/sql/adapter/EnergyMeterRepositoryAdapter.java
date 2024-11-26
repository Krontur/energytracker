package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.devicecatalog.application.port.outbound.EnergyMeterRepositoryPort;
import com.energytracker.devicecatalog.domain.model.EnergyMeter;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.EnergyMeterEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper.EnergyMeterPersistenceMapper;import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository.JpaEnergyMeterPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class EnergyMeterRepositoryAdapter implements EnergyMeterRepositoryPort {

    private JpaEnergyMeterPort jpaEnergyMeterPort;

    @Override
    public boolean existsBySerialNumber(String serialNumber) {
        return jpaEnergyMeterPort.existsBySerialNumber(serialNumber);
    }
    @Override
    public EnergyMeter createEnergyMeter(EnergyMeter energyMeter) {
        EnergyMeterEntity energyMeterEntity =
                jpaEnergyMeterPort.save(EnergyMeterPersistenceMapper.energyMeterDomainToEntity(energyMeter));
        return EnergyMeterPersistenceMapper.energyMeterEntityToDomain(energyMeterEntity);
    }


    @Override
    public List<EnergyMeter> getAllEnergyMeters() {
        List<EnergyMeterEntity> energyMeterEntities = jpaEnergyMeterPort.findAll();
        List<EnergyMeter> energyMeters = new ArrayList<EnergyMeter>();
        energyMeterEntities.forEach(energyMeterEntity -> {
            energyMeters.add(EnergyMeterPersistenceMapper.energyMeterEntityToDomain(energyMeterEntity));
        });
        return energyMeters;
    }

    @Override
    public EnergyMeter getEnergyMeterById(Long energyMeterId) {
        EnergyMeterEntity energyMeterEntity = jpaEnergyMeterPort.findById(energyMeterId).orElse(null);
        if (energyMeterEntity != null) {
            return EnergyMeterPersistenceMapper.energyMeterEntityToDomain(energyMeterEntity);
        }
        return null;
    }

    @Override
    public void deleteEnergyMeterById(Long energyMeterId) {
        jpaEnergyMeterPort.deleteById(energyMeterId);
    }

    @Override
    public EnergyMeter save(EnergyMeter energyMeter) {
        EnergyMeterEntity energyMeterEntity = jpaEnergyMeterPort.save(EnergyMeterPersistenceMapper.energyMeterDomainToEntity(energyMeter));
        return EnergyMeterPersistenceMapper.energyMeterEntityToDomain(energyMeterEntity);
    }
}
