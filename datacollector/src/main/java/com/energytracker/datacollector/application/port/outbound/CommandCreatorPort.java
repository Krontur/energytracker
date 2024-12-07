package com.energytracker.datacollector.application.port.outbound;

import com.energytracker.datacollector.domain.model.Command;

import java.util.List;

public interface CommandCreatorPort {

    Command createCommandGetIntervalEnergyOfStation(String stationTag, List<Integer> channels, String start);

}
