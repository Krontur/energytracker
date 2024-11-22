package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.service.EnergyMeterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meters")
@CrossOrigin(origins = "http://localhost:5173")
public class EnergyMeterController {

    private final EnergyMeterService energyMeterService;

    public EnergyMeterController(EnergyMeterService energyMeterService) {
        this.energyMeterService = energyMeterService;
    }

    @PostMapping
    public ResponseEntity<EnergyMeterResponseDto> createEnergyMeter(
            @RequestBody CreateEnergyMeterRequestDto createEnergyMeterRequestDto) {

        EnergyMeterResponseDto createdEnergyMeter = null;
        if (createEnergyMeterRequestDto != null) {
            createdEnergyMeter = energyMeterService.createEnergyMeter(createEnergyMeterRequestDto);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdEnergyMeter, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EnergyMeterResponseDto>> getAllEnergyMeters() {
        List<EnergyMeterResponseDto> energyMeters = energyMeterService.getAllEnergyMeters();
        return new ResponseEntity<>(energyMeters, HttpStatus.OK);
    }

    @GetMapping("/{energyMeterId}")
    public ResponseEntity<EnergyMeterResponseDto> getEnergyMeterById(@PathVariable Long energyMeterId) {
        EnergyMeterResponseDto energyMeter = energyMeterService.getEnergyMeterById(energyMeterId);
        return new ResponseEntity<>(energyMeter, HttpStatus.OK);
    }

    @DeleteMapping("/{energyMeterId}")
    public ResponseEntity<Void> deleteEnergyMeterById(@PathVariable Long energyMeterId) {
        energyMeterService.deleteEnergyMeterById(energyMeterId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{energyMeterId}/deactivate")
    public ResponseEntity<EnergyMeterResponseDto> deactivateEnergyMeter(@PathVariable Long energyMeterId) {
        return new ResponseEntity<>(energyMeterService.deactivateEnergyMeterById(energyMeterId), HttpStatus.OK);
    }
}
