package com.energytracker.consumptionservice.infrastructure.adapter.inbound.rest;

import com.energytracker.consumptionservice.application.dto.ConsumptionDto;
import com.energytracker.consumptionservice.application.dto.ConsumptionQueryDto;
import com.energytracker.consumptionservice.application.service.ConsumptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consumptions")
@CrossOrigin(origins = "http://localhost:5173")
public class ConsumptionController {

    private final ConsumptionService consumptionService;

    public ConsumptionController(ConsumptionService consumptionService) {
        this.consumptionService = consumptionService;
    }

    @GetMapping("/{meteringPointId}")
    public ResponseEntity<List<ConsumptionDto>> getConsumptionsByMeteringPointId(@PathVariable Long meteringPointId) {
        List<ConsumptionDto> consumptionsDto = consumptionService.getConsumptionsByMeteringPointId(meteringPointId);
        return new ResponseEntity<>(consumptionsDto, HttpStatus.OK);
    }

    @PostMapping
    public List<ConsumptionDto> getConsumptionsByMeteringPointIdAndInterval(@RequestBody ConsumptionQueryDto consumptionQueryDto) {

        return consumptionService.getConsumptionsByMeteringPointIdAndInterval(consumptionQueryDto);
    }

}
