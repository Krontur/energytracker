package com.energytracker.consumptionservice.infrastructure.adapter.inbound.rest;

import com.energytracker.consumptionservice.application.dto.MeteringPointDto;
import com.energytracker.consumptionservice.application.service.MeteringPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/metering-points")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class MeteringPointController {

    private final MeteringPointService meteringPointService;

    @GetMapping
    public ResponseEntity<List<MeteringPointDto>> getMeteringPoints() {
        List<MeteringPointDto> meteringPointDtos = meteringPointService.getAllMeteringPoints();
        if (meteringPointDtos == null || meteringPointDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(meteringPointDtos);
    }

}
