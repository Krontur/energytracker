package com.energytracker.datacollector.application.port.outbound;

import com.energytracker.datacollector.domain.model.Command;

public interface StationQueryPort {

    String getConsumptionsByStationTag(Command command);

}
