package com.energytracker.datacollector.infrastructure.adapter.outbound.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConsumptionResultPersistenceDto {

    private Long meteringPointId;
    private String startTimestamp;
    private String endTimestamp;
    private Double consumption;

}
