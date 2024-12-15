package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.dto.meteringpoint.CreateMeteringPointRequestDto;
import com.energytracker.devicecatalog.application.dto.meteringpoint.MeteringPointResponseDto;
import com.energytracker.devicecatalog.application.mapper.MeteringPointMapper;
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
    public ResponseEntity<MeteringPointResponseDto> getMeteringPointById(@PathVariable Long meteringPointId) {
        MeteringPointResponseDto meteringPoint = meteringPointService.getMeteringPointById(meteringPointId);
        if(meteringPoint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(meteringPoint, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<MeteringPointResponseDto> createMeteringPoint(@RequestBody CreateMeteringPointRequestDto createMeteringPointRequestDto) {
        try {
            MeteringPointResponseDto meteringPoint = meteringPointService.createMeteringPoint(createMeteringPointRequestDto);
            return new ResponseEntity<>(meteringPoint, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{meteringPointId}")
    public ResponseEntity<MeteringPointResponseDto> updateMeteringPoint(@PathVariable Long meteringPointId, @RequestBody CreateMeteringPointRequestDto createMeteringPointRequestDto) {
        try {
            MeteringPointResponseDto meteringPoint = meteringPointService.updateMeteringPointById(meteringPointId, createMeteringPointRequestDto);
            return new ResponseEntity<>(meteringPoint, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
