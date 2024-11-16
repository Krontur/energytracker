package com.energytracker.devicecatalog.domain.model;

import java.time.LocalDate;

public class MeteringPoint {

    private MeteringPoint parentMeteringPoint;

    private Long meteringPointId;

    private EnergyMeter energyMeter;

    private Channel channel;

    private String locationName;

    private String connectionDescription;

    private LocalDate createdDate;

    private LocalDate updatedDate;

    private Boolean activeStatus;

}
