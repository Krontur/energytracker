package com.energytracker.consumptionservice.domain.model;

import com.energytracker.consumptionservice.domain.model.IntervalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionQuery {

    private Long meteringPointId;
    private IntervalType intervalType;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

}
