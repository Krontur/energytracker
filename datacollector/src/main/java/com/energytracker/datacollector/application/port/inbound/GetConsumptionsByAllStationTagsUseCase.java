package com.energytracker.datacollector.application.port.inbound;

public interface GetConsumptionsByAllStationTagsUseCase {

    void getConsumptionsByAllStationTags(String timeStamp);

}
