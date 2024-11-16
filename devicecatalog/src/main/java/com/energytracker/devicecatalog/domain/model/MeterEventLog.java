package com.energytracker.devicecatalog.domain.model;

import java.time.LocalDate;

public class MeterEventLog {

    private Long meterEventLogId;

    private Device device;

    private MeteringPoint meteringPoint;

    private EventType eventType;

    private LocalDate eventDate;

    private String performedBy;

    private String reason;

    private Device oldDevice;

    private int oldEnergyMeterReading;

    private Device newDevice;

    private int newEnergyMeterReading;
}
