package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.dto.MeteringPointResponseDto;
import com.energytracker.devicecatalog.application.service.MeteringPointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/metering-points")
@CrossOrigin(origins = "http://localhost:5173")
public class MeteringPointController {

    private final MeteringPointService meteringPointService;

    public MeteringPointController(MeteringPointService meteringPointService) {
        this.meteringPointService = meteringPointService;
    }

    @GetMapping
    public ResponseEntity<List<MeteringPointResponseDto>> getMeteringPoints() {
        List<MeteringPointResponseDto> meteringPoints = meteringPointService.getAllMeteringPoints();
        if(meteringPoints.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(meteringPoints, HttpStatus.OK);
    }

    @GetMapping("/{meteringPointId}")
    public String getMeteringPointById() {
        return "getMeteringPointById";
    }

    @PostMapping
    public String createMeteringPoint() {
        return "createMeteringPoint";
    }


}
