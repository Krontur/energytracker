package com.energytracker.datacollector.infrastructure.adapter.configuration;

import com.energytracker.datacollector.application.port.inbound.ConsumptionsMessageHandlerPort;
import com.energytracker.datacollector.application.port.outbound.MeteringPointFileRepositoryPort;
import com.energytracker.datacollector.domain.model.ConsumptionResult;
import com.energytracker.datacollector.domain.model.MeteringPoint;
import jakarta.ws.rs.core.NoContentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
@DependsOn("rabbitTemplate")
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    @Value("${demo.init}")
    private boolean demoInit;

    @Value("${demo.init.database.reading.months}")
    private int minusMonths;

    @Value("${external.meteringpoints.jsonfile.save.location}")
    private String meteringPointsJsonFileLocation;

    @Value("${rabbitmq.queue.consumptions}")
    private String consumptionsQueue;

    private final ConsumptionsMessageHandlerPort consumptionsMessageHandlerPort;
    private final MeteringPointFileRepositoryPort meteringPointFileRepositoryPort;

    private void initializeAsync() {
        CompletableFuture.runAsync(
                new Runnable() {
                    @Override
                    public void run() {
                        List<MeteringPoint> meteringPoints = meteringPointFileRepositoryPort.loadMeteringPointsFromFile(meteringPointsJsonFileLocation);
                        while (meteringPoints.isEmpty() || meteringPoints.size() < 10
                        ){
                            try {
                                TimeUnit.SECONDS.sleep(10);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            log.info("Metering Point not loaded, retry...");
                            meteringPoints = meteringPointFileRepositoryPort.loadMeteringPointsFromFile(meteringPointsJsonFileLocation);
                        }
                        try {
                            initializeDatabaseMonthly(meteringPoints, minusMonths);
                        } catch (NoContentException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
    }

    private void initializeDatabaseMonthly(List<MeteringPoint> meteringPoints, int minusMonths) throws NoContentException {

        if (meteringPoints == null || meteringPoints.isEmpty()) {
            return;
        }

        LocalDateTime endLimit = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);

        for (int i = minusMonths; i >= 0; i--) {
            LocalDateTime startOfMonth = LocalDateTime.now().minusMonths(i).withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS);
            LocalDateTime endOfMonth = startOfMonth.plusMonths(1).truncatedTo(ChronoUnit.DAYS);

            List<ConsumptionResult> consumptionResultList = new ArrayList<>();

            for (LocalDateTime date = startOfMonth; date.isBefore(endOfMonth) && date.isBefore(endLimit); date = date.plusDays(1)) {

                for (LocalDateTime day = date, endOfDay = date.plusDays(1);
                     day.isBefore(endOfDay) && day.isBefore(endLimit); day = day.plusMinutes(15)) {

                    for (MeteringPoint meteringPoint : meteringPoints) {
                        ConsumptionResult consumption = new ConsumptionResult(
                                meteringPoint,
                                day,
                                Math.random() * 100
                        );
                        consumptionResultList.add(consumption);
                    }
                }
            }
            consumptionsMessageHandlerPort.sendMessage(consumptionResultList, consumptionsQueue);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        if (demoInit) {
            initializeAsync();
        }
    }
}
