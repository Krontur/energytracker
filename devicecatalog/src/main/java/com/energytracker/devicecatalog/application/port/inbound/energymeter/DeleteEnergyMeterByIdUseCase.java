package com.energytracker.devicecatalog.application.port.inbound.energymeter;

public interface DeleteEnergyMeterByIdUseCase {

    void deleteEnergyMeterById(Long energyMeterId);

}
