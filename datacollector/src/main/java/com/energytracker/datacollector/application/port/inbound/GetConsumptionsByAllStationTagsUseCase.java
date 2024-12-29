package com.energytracker.datacollector.application.port.inbound;

import jakarta.ws.rs.core.NoContentException;

public interface GetConsumptionsByAllStationTagsUseCase {

    void getConsumptionsByAllStationTags(String timeStamp) throws NoContentException;

}
