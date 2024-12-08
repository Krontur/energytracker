package com.energytracker.consumptionservice.application.dto;

import com.energytracker.consumptionservice.domain.model.IntervalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionQueryDto {

    private Long meteringPointId;
    private String intervalType;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

}
