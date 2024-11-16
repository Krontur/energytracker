package com.energytracker.devicecatalog.domain.model;

import java.time.LocalDateTime;

public class EnergyConsumption {

    private LocalDateTime consumptionStartIntervalTime;

    private MeteringPoint meteringPoint;

    private float consumption;
}
