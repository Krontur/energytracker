package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.dto.ChannelResponseDto;
import com.energytracker.devicecatalog.application.dto.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;
import com.energytracker.devicecatalog.application.service.StationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
@CrossOrigin(origins = "http://localhost:5173")
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping
    public ResponseEntity<StationResponseDto> createStation(
            @RequestBody CreateStationRequestDto createStationRequestDto) {
        StationResponseDto createdStation = null;
        if (createStationRequestDto != null) {
            createdStation = stationService.createStation(createStationRequestDto);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdStation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StationResponseDto>> getAllStations() {
        List<StationResponseDto> stations = stationService.getAllStations();
        if (stations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(stations, HttpStatus.OK);
    }

    @GetMapping("/{stationId}")
    public ResponseEntity<StationResponseDto> getStationById(@PathVariable Long stationId) {
        StationResponseDto station = stationService.getStationById(stationId);
        if (station == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(station, HttpStatus.OK);
    }

    @DeleteMapping("/{stationId}")
    public ResponseEntity<Void> deleteStationById(@PathVariable Long stationId) {
        stationService.deleteStationById(stationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{stationId}/deactivate")
    public ResponseEntity<StationResponseDto> deactivateStation(@PathVariable Long stationId) {
        StationResponseDto station = stationService.deactivateStationById(stationId);
        if (station == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(station, HttpStatus.OK);
    }

    @GetMapping("/{stationId}/channels")
    public ResponseEntity<List<ChannelResponseDto>> getChannelsByStationId(@PathVariable Long stationId) {
        List<ChannelResponseDto> channels = stationService.getChannelsByStationId(stationId);
        if (channels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(channels, HttpStatus.OK);
    }

}
