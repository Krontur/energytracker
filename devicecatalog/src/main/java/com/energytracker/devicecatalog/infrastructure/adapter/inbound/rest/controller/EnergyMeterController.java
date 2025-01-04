package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.dto.energymeter.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.energymeter.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.service.EnergyMeterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/meters")
@CrossOrigin(origins = "${cors.origin.url}")
@RequiredArgsConstructor
public class EnergyMeterController {

    private final EnergyMeterService energyMeterService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EnergyMeterResponseDto> createEnergyMeter(
            @RequestBody CreateEnergyMeterRequestDto createEnergyMeterRequestDto) {

        EnergyMeterResponseDto createdEnergyMeter;
        if (createEnergyMeterRequestDto != null) {
            createdEnergyMeter = energyMeterService.createEnergyMeter(createEnergyMeterRequestDto);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdEnergyMeter, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<EnergyMeterResponseDto>> getAllEnergyMeters() {
        log.info("Getting all energy meters");
        List<EnergyMeterResponseDto> energyMeters = energyMeterService.getAllEnergyMeters();
        if (energyMeters == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(energyMeters, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{energyMeterId}")
    public ResponseEntity<EnergyMeterResponseDto> getEnergyMeterById(@PathVariable Long energyMeterId) {
        EnergyMeterResponseDto energyMeter = energyMeterService.getEnergyMeterById(energyMeterId);
        if (energyMeter == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(energyMeter, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{energyMeterId}")
    public ResponseEntity<Void> deleteEnergyMeterById(@PathVariable Long energyMeterId) {
        energyMeterService.deleteEnergyMeterById(energyMeterId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{energyMeterId}/deactivate")
    public ResponseEntity<EnergyMeterResponseDto> deactivateEnergyMeter(@PathVariable Long energyMeterId) {
        EnergyMeterResponseDto energyMeter = energyMeterService.getEnergyMeterById(energyMeterId);
        if (energyMeter == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(energyMeterService.deactivateEnergyMeterById(energyMeterId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/in-stock")
    public ResponseEntity<List<EnergyMeterResponseDto>> getInStockEnergyMeters() {
        List<EnergyMeterResponseDto> energyMeters = energyMeterService.getInStockEnergyMeters();
        if (energyMeters == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(energyMeters, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{energyMeterId}")
    public ResponseEntity<EnergyMeterResponseDto> updateEnergyMeter(
            @PathVariable Long energyMeterId,
            @RequestBody CreateEnergyMeterRequestDto createEnergyMeterRequestDto) {
        EnergyMeterResponseDto updatedEnergyMeter;
        if (createEnergyMeterRequestDto != null) {
            updatedEnergyMeter = energyMeterService.updateEnergyMeter(energyMeterId, createEnergyMeterRequestDto);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedEnergyMeter, HttpStatus.OK);
    }
}
