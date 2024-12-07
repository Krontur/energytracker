package com.energytracker.datacollector.infrastructure.adapter.inbound.scheduledtasks;

import com.energytracker.datacollector.application.port.inbound.GetConsumptionsByAllStationTagsUseCase;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@Log4j2
public class ScheduledStationQueryController {

    private final GetConsumptionsByAllStationTagsUseCase getConsumptionsByAllStationTagsUseCase;

    public ScheduledStationQueryController(GetConsumptionsByAllStationTagsUseCase getConsumptionsByAllStationTagsUseCase) {
        this.getConsumptionsByAllStationTagsUseCase = getConsumptionsByAllStationTagsUseCase;
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void getConsumptionsByAllStationTags() {
        String timeStamp;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");
        timeStamp = LocalDateTime.now().format(formatter);
        log.info("Scheduled task started at the time: {}", timeStamp);
        getConsumptionsByAllStationTagsUseCase.getConsumptionsByAllStationTags(timeStamp);
    }

}
