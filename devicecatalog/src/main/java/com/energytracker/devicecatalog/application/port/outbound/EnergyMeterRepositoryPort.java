package com.energytracker.devicecatalog.application.port.outbound;

import com.energytracker.devicecatalog.domain.model.energymeter.EnergyMeter;

import java.util.List;

public interface EnergyMeterRepositoryPort {

    boolean existsBySerialNumber(String serialNumber);

    EnergyMeter createEnergyMeter(EnergyMeter energyMeter);

    List<EnergyMeter> getAllEnergyMeters();

    EnergyMeter getEnergyMeterById(Long energyMeterId);

    void deleteEnergyMeterById(Long energyMeterId);

    EnergyMeter save(EnergyMeter energyMeter);
}
