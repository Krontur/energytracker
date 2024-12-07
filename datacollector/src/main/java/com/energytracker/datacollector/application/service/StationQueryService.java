package com.energytracker.datacollector.application.service;

import com.energytracker.datacollector.application.port.inbound.GetConsumptionsByAllStationTagsUseCase;
import com.energytracker.datacollector.application.port.outbound.*;
import com.energytracker.datacollector.domain.model.ConsumptionResult;
import com.energytracker.datacollector.domain.model.MeteringPoint;
import com.energytracker.datacollector.domain.model.MeteringPointsGroupsByStationTag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class StationQueryService implements GetConsumptionsByAllStationTagsUseCase {
    private final StationQueryPort stationQueryPort;
    private final CommandCreatorPort commandCreatorPort;
    private final MeteringPointFileRepositoryPort meteringPointFileRepositoryPort;
    private final QueueMessagingPort queueMessagingPort;
    private final ConfigLoaderPort configLoaderPort;
    private final ObjectMapper objectMapper;


    public StationQueryService(
            StationQueryPort stationQueryPort,
            CommandCreatorPort commandCreatorPort,
            MeteringPointFileRepositoryPort meteringPointFileRepositoryPort,
            QueueMessagingPort queueMessagingPort,
            ConfigLoaderPort configLoaderPort,
            ObjectMapper objectMapper) {
        this.stationQueryPort = stationQueryPort;
        this.commandCreatorPort = commandCreatorPort;
        this.meteringPointFileRepositoryPort = meteringPointFileRepositoryPort;
        this.queueMessagingPort = queueMessagingPort;
        this.configLoaderPort = configLoaderPort;
        this.objectMapper = objectMapper;
    }


    public void getConsumptionsByAllStationTags(String timeStamp) {

        log.info("Getting consumptions by all station tags");
        List<ConsumptionResult> consumptionResults = new ArrayList<>();
        List<MeteringPoint> meteringPointList = meteringPointFileRepositoryPort.loadMeteringPointsFromFile(
                configLoaderPort.getProperty("meteringpoints.jsonfile.save.location"));

        MeteringPointsGroupsByStationTag meteringPointsGroupByStationTag = new MeteringPointsGroupsByStationTag(meteringPointList);
        meteringPointsGroupByStationTag.getMeteringPointsGroupsByStationTag().forEach(
                (stationTag, meteringPoints) -> {
                    List<Integer> channels = new ArrayList<>();
                    meteringPoints.forEach(
                            meteringPoint -> {
                                channels.add(meteringPoint.getChannelNumber());
                            });
                    var command = commandCreatorPort.createCommandGetIntervalEnergyOfStation(
                            stationTag,
                            channels,
                            timeStamp);
                    String rawResult = stationQueryPort.getConsumptionsByStationTag(command);
                    consumptionResults.addAll(parseConsumptionResults(rawResult, meteringPoints, timeStamp));
                }
        );

        try {
            String consumptionResultsString = objectMapper.writeValueAsString(consumptionResults);
            log.info("Consumptions by all station tags: {}", consumptionResultsString);
            log.info("Sending consumptions by all station tags to queue");
            queueMessagingPort.sendMessage(consumptionResultsString, configLoaderPort.getProperty("rabbitmq.queue.consumptions"));
        } catch (JsonProcessingException e) {
            log.error("Error parsing json consumption results: {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private List<ConsumptionResult> parseConsumptionResults(String rawResult, List<MeteringPoint> meteringPoints, String timeStamp) {
        List<ConsumptionResult> consumptionResults = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");

        try {
            String[] rawResults = rawResult.split(";");
            if (rawResults.length < 3) {
                log.error("Error, unknown format for consumption results: {}", rawResult);
                return consumptionResults;
            }
            String command = rawResults[0].trim();
            int intervalInSeconds = Integer.parseInt(rawResults[1].trim());

            log.info("Processing consumption results for command: {}", command);
            log.info("Interval in seconds: {}", intervalInSeconds);

            for (int i = 2; i < rawResults.length; i++) {
                if (i - 2 >= meteringPoints.size()) {
                    log.error("Error, too many consumptions for metering points: {}", rawResult);
                    break;
                }
                MeteringPoint meteringPoint = meteringPoints.get(i - 2);
                ConsumptionResult consumptionResult = new ConsumptionResult(meteringPoint, LocalDateTime.parse(timeStamp, formatter), Double.parseDouble(rawResults[i]));
                consumptionResults.add(consumptionResult);
            }
        } catch (Exception e) {
            log.error("Error parsing consumption results: {}", e.getMessage());
        }
        return consumptionResults;
    }
}
