package com.energytracker.devicecatalog.infrastructure.adapter.inbound.rest.controller;

import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;
import com.energytracker.devicecatalog.application.port.inbound.channel.GetAllChannelsUseCase;
import com.energytracker.devicecatalog.application.port.inbound.channel.GetChannelByIdUseCase;
import com.energytracker.devicecatalog.application.port.inbound.channel.UpdateChannelUseCase;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/channels")
@CrossOrigin(origins = "${cors.origin.url}")
@RequiredArgsConstructor
public class ChannelController {

    private final GetAllChannelsUseCase getAllChannelsUseCase;
    private final GetChannelByIdUseCase getChannelByIdUseCase;
    private final UpdateChannelUseCase updateChannelUseCase;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{channelId}")
    public ResponseEntity<ChannelResponseDto> getChannelById(@PathVariable Long channelId) {
        try {
            ChannelResponseDto channel = getChannelByIdUseCase.getChannelById(channelId);
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
            List<ChannelResponseDto> channelResponseDtoList = getAllChannelsUseCase.getAllChannels();
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
            ChannelResponseDto updatedChannel = updateChannelUseCase.updateChannel(channelResponseDto);
            return new ResponseEntity<>(updatedChannel, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
