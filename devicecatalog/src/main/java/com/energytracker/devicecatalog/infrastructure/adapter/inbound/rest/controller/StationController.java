package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.dto.CreateRequestStationDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;
import com.energytracker.devicecatalog.application.service.StationService;
import com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.dto.CreateRequestStationRestDto;
import com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.dto.StationResponseRestDto;
import com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.mapper.StationRestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/meters")
@CrossOrigin(origins = "http://localhost:5173")
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping
    public ResponseEntity<StationResponseRestDto> createStation(
            @RequestBody CreateRequestStationRestDto createRequestStationRestDto) {
        CreateRequestStationDto createRequestStationDto =
                StationRestMapper.createRequestStationRestDtoToDto(createRequestStationRestDto);
        StationResponseDto createdStation = null;
        if (createRequestStationDto != null) {
            createdStation = stationService.createStation(createRequestStationDto);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(StationRestMapper.stationResponseDtoToRestDto(createdStation), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StationResponseRestDto>> getAllStations() {
        List<StationResponseDto> stations = stationService.getAllStations();
        List<StationResponseRestDto> stationResponseRestDtos = new ArrayList<StationResponseRestDto>();
        stations.forEach(station -> stationResponseRestDtos.add(StationRestMapper.stationResponseDtoToRestDto(station)));
        return new ResponseEntity<>(stationResponseRestDtos, HttpStatus.OK);
    }

}
