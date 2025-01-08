package com.energytracker.consumptionservice.infrastructure.adapter.inbound.rest;

import com.energytracker.consumptionservice.application.dto.ConsumptionDto;
import com.energytracker.consumptionservice.application.dto.ConsumptionQueryDto;
import com.energytracker.consumptionservice.application.port.inbound.GetConsumptionsByMeteringPointIdAndIntervalUseCase;
import com.energytracker.consumptionservice.application.port.inbound.GetConsumptionsByMeteringPointIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/consumptions")
@CrossOrigin(origins = "${cors.origin.url}")
@RequiredArgsConstructor
public class ConsumptionController {

    private final GetConsumptionsByMeteringPointIdUseCase getConsumptionsByMeteringPointIdUseCase;
    private final GetConsumptionsByMeteringPointIdAndIntervalUseCase getConsumptionsByMeteringPointIdAndIntervalUseCase;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/metering-point/{meteringPointId}")
    public ResponseEntity<List<ConsumptionDto>> getConsumptionsByMeteringPointId(@PathVariable Long meteringPointId) {
        List<ConsumptionDto> consumptionsDto = getConsumptionsByMeteringPointIdUseCase.getConsumptionsByMeteringPointId(meteringPointId);
        if (consumptionsDto == null || consumptionsDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(consumptionsDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/metering-point/interval")
    public ResponseEntity<List<ConsumptionDto>> getConsumptionsByMeteringPointIdAndInterval(@RequestBody ConsumptionQueryDto consumptionQueryDto) {
        List<ConsumptionDto> consumptionDtos = new ArrayList<>();
        consumptionDtos = getConsumptionsByMeteringPointIdAndIntervalUseCase.getConsumptionsByMeteringPointIdAndInterval(consumptionQueryDto);
        if ( consumptionDtos == null || consumptionDtos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(consumptionDtos, HttpStatus.OK);
    }

}
