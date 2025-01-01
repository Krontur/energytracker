package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;
import com.energytracker.devicecatalog.application.dto.station.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.station.StationResponseDto;
import com.energytracker.devicecatalog.application.service.StationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{stationId}")
    public ResponseEntity<StationResponseDto> updateStation(
            @PathVariable Long stationId,
            @RequestBody CreateStationRequestDto createStationRequestDto) {
        StationResponseDto updatedStation = null;
        if (createStationRequestDto != null) {
            updatedStation = stationService.updateStation(stationId, createStationRequestDto);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedStation, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<StationResponseDto>> getAllStations() {
        List<StationResponseDto> stations = stationService.getAllStations();
        if (stations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(stations, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{stationId}")
    public ResponseEntity<StationResponseDto> getStationById(@PathVariable Long stationId) {
        try {
            StationResponseDto station = stationService.getStationById(stationId);
            return new ResponseEntity<>(station, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{stationId}")
    public ResponseEntity<Void> deleteStationById(@PathVariable Long stationId) {
        stationService.deleteStationById(stationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{stationId}/deactivate")
    public ResponseEntity<StationResponseDto> deactivateStation(@PathVariable Long stationId) {
        StationResponseDto station = stationService.deactivateStationById(stationId);
        if (station == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(station, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{stationId}/channels")
    public ResponseEntity<List<ChannelResponseDto>> getChannelsByStationId(@PathVariable Long stationId) {
        List<ChannelResponseDto> channels = stationService.getChannelsByStationId(stationId);
        if (channels == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(channels, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{stationId}/channels/lon-is-active")
    public ResponseEntity<List<ChannelResponseDto>> getLonActiveChannelsByStationId(@PathVariable Long stationId) {
        List<ChannelResponseDto> channels = stationService.getLonActiveChannelsByStationId(stationId);
        if (channels == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(channels, HttpStatus.OK);
    }

}
