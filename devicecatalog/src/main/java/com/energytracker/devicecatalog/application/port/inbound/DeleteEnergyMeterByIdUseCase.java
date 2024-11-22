package com.energytracker.devicecatalog.application.port.inbound;

public interface DeleteEnergyMeterByIdUseCase {

    void deleteEnergyMeterById(Long energyMeterId);

}
