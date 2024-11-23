package com.energytracker.devicecatalog.application.mapper;

import com.energytracker.devicecatalog.application.dto.ChannelResponseDto;
import com.energytracker.devicecatalog.application.dto.StationResponseDto;
import com.energytracker.devicecatalog.domain.model.DeviceStatus;
import com.energytracker.devicecatalog.domain.model.DeviceType;
import com.energytracker.devicecatalog.domain.model.Station;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class StationMapperTest {

    @Test
    public void testStationResponseDtoToDomain() {

        StationResponseDto stationResponseDto = new StationResponseDto(
                1L,
                LocalDateTime.of(2021, 1, 1, 0, 0),
                LocalDateTime.of(2021, 1, 1, 0, 0),
                "UD223423423",
                "STATION",
                "IN_STOCK",
                "Station 1",
                "U1601",
                90,
                "A2",
                new ArrayList<ChannelResponseDto>()
        );

        Station station = new Station(
                1L,
                "UD223423423",
                DeviceType.STATION,
                DeviceStatus.IN_STOCK,
                "Station 1",
                "U1601",
                "A2",
                90
        );

    }

}
