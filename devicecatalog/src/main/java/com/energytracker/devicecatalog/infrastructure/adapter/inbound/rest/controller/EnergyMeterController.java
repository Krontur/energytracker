package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.dto.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.service.EnergyMeterService;
import com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.dto.CreateEnergyMeterRequestRestDto;
import com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.dto.EnergyMeterResponseRestDto;
import com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.mapper.EnergyMeterRestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<EnergyMeterResponseRestDto> createEnergyMeter(
            @RequestBody CreateEnergyMeterRequestRestDto createEnergyMeterRequestRestDto) {
        CreateEnergyMeterRequestDto createEnergyMeterRequestDto =
                EnergyMeterRestMapper.createRequestEnergyMeterRestDtoToDto(createEnergyMeterRequestRestDto);
        EnergyMeterResponseDto createdEnergyMeter = null;
        if (createEnergyMeterRequestDto != null) {
            createdEnergyMeter = energyMeterService.createEnergyMeter(createEnergyMeterRequestDto);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(EnergyMeterRestMapper.energyMeterResponseDtoToRestDto(createdEnergyMeter), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EnergyMeterResponseRestDto>> getAllEnergyMeters() {
        List<EnergyMeterResponseDto> energyMeters = energyMeterService.getAllEnergyMeters();
        List<EnergyMeterResponseRestDto> energyMeterResponseRestDtos = new ArrayList<EnergyMeterResponseRestDto>();
        energyMeters.forEach(energyMeter -> energyMeterResponseRestDtos.add(EnergyMeterRestMapper.energyMeterResponseDtoToRestDto(energyMeter)));
        return new ResponseEntity<>(energyMeterResponseRestDtos, HttpStatus.OK);
    }

}
