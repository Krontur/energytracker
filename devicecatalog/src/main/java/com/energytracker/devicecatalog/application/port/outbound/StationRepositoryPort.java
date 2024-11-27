package com.energytracker.devicecatalog.application.port.outbound;

import com.energytracker.devicecatalog.domain.model.station.Channel;
import com.energytracker.devicecatalog.domain.model.station.Station;

import java.util.List;

public interface StationRepositoryPort {

    boolean existsBySerialNumber(String serialNumber);

    Station createStation(Station station);

    List<Station> getAllStations();

    Station getStationById(Long stationId);

    void deleteStationById(Long stationId);

    List<Channel> getChannelsByStationId(Long stationId);

    Station save(Station station);
}
