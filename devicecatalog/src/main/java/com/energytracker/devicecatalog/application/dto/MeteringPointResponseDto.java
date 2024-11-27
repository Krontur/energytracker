package com.energytracker.devicecatalog.application.dto;

import com.energytracker.devicecatalog.application.dto.energymeter.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MeteringPointResponseDto {

    private Long meteringPointId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long parentMeteringPointId;
    private EnergyMeterResponseDto energyMeter;
    private ChannelResponseDto channel;
    private String locationName;
    private String connectionDescription;
    private Boolean activeStatus;

    public MeteringPointResponseDto(Long meteringPointId,
                                    LocalDateTime createdAt,
                                    LocalDateTime updatedAt,
                                    MeteringPointResponseDto meteringPointResponseDto,
                                    EnergyMeterResponseDto energyMeterResponseDto,
                                    ChannelResponseDto channelResponseDto,
                                    String locationName,
                                    String connectionDescription,
                                    Boolean activeStatus) {
        this.meteringPointId = meteringPointId;
        this.parentMeteringPointId = meteringPointResponseDto.getMeteringPointId();
        this.energyMeter = energyMeterResponseDto;
        this.channel = channelResponseDto;
        this.locationName = locationName;
        this.connectionDescription = connectionDescription;
        this.createdDate = createdAt;
        this.updatedDate = updatedAt;
        this.activeStatus = activeStatus;

    }
}
