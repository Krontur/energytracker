package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.dto.meteringpoint.CreateMeteringPointRequestDto;
import com.energytracker.devicecatalog.application.dto.meteringpoint.MeteringPointResponseDto;
import com.energytracker.devicecatalog.application.port.inbound.meteringpoint.CreateMeteringPointUseCase;
import com.energytracker.devicecatalog.application.port.inbound.meteringpoint.GetAllMeteringPointsUseCase;
import com.energytracker.devicecatalog.application.port.inbound.meteringpoint.GetMeteringPointByIdUseCase;
import com.energytracker.devicecatalog.application.port.inbound.meteringpoint.UpdateMeteringPointByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/metering-points")
@CrossOrigin(origins = "${cors.origin.url}")
@RequiredArgsConstructor
public class MeteringPointController {

    private final GetAllMeteringPointsUseCase getAllMeteringPointsUseCase;
    private final GetMeteringPointByIdUseCase getMeteringPointByIdUseCase;
    private final CreateMeteringPointUseCase createMeteringPointUseCase;
    private final UpdateMeteringPointByIdUseCase updateMeteringPointByIdUseCase;


    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<MeteringPointResponseDto>> getMeteringPoints() {
        List<MeteringPointResponseDto> meteringPoints = getAllMeteringPointsUseCase.getAllMeteringPoints();
        if(meteringPoints.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(meteringPoints, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{meteringPointId}")
    public ResponseEntity<MeteringPointResponseDto> getMeteringPointById(@PathVariable Long meteringPointId) {
        MeteringPointResponseDto meteringPoint = getMeteringPointByIdUseCase.getMeteringPointById(meteringPointId);
        if(meteringPoint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(meteringPoint, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MeteringPointResponseDto> createMeteringPoint(@RequestBody CreateMeteringPointRequestDto createMeteringPointRequestDto) {
        try {
            MeteringPointResponseDto meteringPoint = createMeteringPointUseCase.createMeteringPoint(createMeteringPointRequestDto);
            return new ResponseEntity<>(meteringPoint, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{meteringPointId}")
    public ResponseEntity<MeteringPointResponseDto> updateMeteringPoint(@PathVariable Long meteringPointId, @RequestBody CreateMeteringPointRequestDto createMeteringPointRequestDto) {
        try {
            MeteringPointResponseDto meteringPoint = updateMeteringPointByIdUseCase.updateMeteringPointById(meteringPointId, createMeteringPointRequestDto);
            return new ResponseEntity<>(meteringPoint, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
