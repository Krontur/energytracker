package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;
import com.energytracker.devicecatalog.application.service.ChannelService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/channels")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{channelId}")
    public ResponseEntity<ChannelResponseDto> getChannelById(@PathVariable Long channelId) {
        try {
            ChannelResponseDto channel = channelService.getChannelById(channelId);
            return new ResponseEntity<>(channel, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping()
    public ResponseEntity<List<ChannelResponseDto>> getAllChannels() {
        try {
            List<ChannelResponseDto> channelResponseDtoList = channelService.getAllChannels();
            return new ResponseEntity<>(channelResponseDtoList, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<ChannelResponseDto> updateChannel(
            @RequestBody ChannelResponseDto channelResponseDto) {
        try {
            ChannelResponseDto updatedChannel = channelService.updateChannel(channelResponseDto);
            return new ResponseEntity<>(updatedChannel, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
