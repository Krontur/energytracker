package com.energytracker.datacollector.application.service;

import com.energytracker.datacollector.application.port.inbound.ConsumptionsMessageHandlerPort;
import com.energytracker.datacollector.application.port.inbound.GetConsumptionsByAllStationTagsUseCase;
import com.energytracker.datacollector.application.port.outbound.*;
import com.energytracker.datacollector.domain.model.ConsumptionResult;
import com.energytracker.datacollector.domain.model.MeteringPoint;
import com.energytracker.datacollector.domain.model.MeteringPointsGroupsByStationTag;
import jakarta.ws.rs.core.NoContentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class StationQueryService implements GetConsumptionsByAllStationTagsUseCase {
    private final StationQueryPort stationQueryPort;
    private final CommandCreatorPort commandCreatorPort;
    private final MeteringPointFileRepositoryPort meteringPointFileRepositoryPort;
    private final ConsumptionsMessageHandlerPort consumptionsMessageHandlerPort;

    @Value("${external.meteringpoints.jsonfile.save.location}")
    private String meteringPointsFileLocation;

    @Value("${simulation.without.stations}")
    private boolean simulationWithoutStations;

    @Value("${rabbitmq.queue.consumptions}")
    private String consumptionsQueueName;

    public void getConsumptionsByAllStationTags(String timeStamp) throws NoContentException {

        List<MeteringPoint> meteringPointList = meteringPointFileRepositoryPort.loadMeteringPointsFromFile(
                meteringPointsFileLocation);

        if (simulationWithoutStations) {
            log.info("Simulation without stations");
            List<ConsumptionResult> simulatedConsumptionResults;
            simulatedConsumptionResults = simulateConsumptionResults(meteringPointList, timeStamp);
            try {
                consumptionsMessageHandlerPort.sendMessage(simulatedConsumptionResults, consumptionsQueueName);
            } catch (NoContentException e) {
                throw new RuntimeException("No content to send" + e.getMessage());
            }
            return;
        }

        log.info("Getting consumptions by all station tags");
        List<ConsumptionResult> consumptionResults = new ArrayList<>();

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

        consumptionsMessageHandlerPort.sendMessage(consumptionResults, consumptionsQueueName);

    }

    public List<ConsumptionResult> parseConsumptionResults(String rawResult, List<MeteringPoint> meteringPoints, String timeStamp) {
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

    public List<ConsumptionResult> simulateConsumptionResults(List<MeteringPoint> meteringPoints, String timeStamp) {
        List<ConsumptionResult> consumptionResults = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");
        double consumption;
        for (MeteringPoint meteringPoint : meteringPoints) {
            consumption = Math.random() * 100;
            ConsumptionResult consumptionResult = new ConsumptionResult(meteringPoint, LocalDateTime.parse(timeStamp, formatter), consumption);
            consumptionResults.add(consumptionResult);
        }
        return consumptionResults;
    }
}
