package com.energytracker.datacollector.infrastructure.adapter.configuration;

import com.energytracker.datacollector.application.port.inbound.ConsumptionsMessageHandlerPort;
import com.energytracker.datacollector.application.port.outbound.MeteringPointFileRepositoryPort;
import com.energytracker.datacollector.domain.model.ConsumptionResult;
import com.energytracker.datacollector.domain.model.MeteringPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
@DependsOn("rabbitTemplate")
@RequiredArgsConstructor
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${demo.init}")
    private boolean demoInit;

    @Value("${external.meteringpoints.jsonfile.save.location}")
    private String meteringPointsJsonFileLocation;

    @Value("${rabbitmq.queue.consumptions}")
    private String consumptionsQueue;

    private final ConsumptionsMessageHandlerPort consumptionsMessageHandlerPort;
    private final MeteringPointFileRepositoryPort meteringPointFileRepositoryPort;

    private void initializeDatabase() {

        List<MeteringPoint> meteringPoints = meteringPointFileRepositoryPort.loadMeteringPointsFromFile(meteringPointsJsonFileLocation);
        if (meteringPoints == null || meteringPoints.isEmpty()) {
            return;
        }
        for (LocalDateTime date = LocalDateTime.now().minusMonths(1).truncatedTo(ChronoUnit.DAYS);
             date.isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)); date = date.plusDays(1)) {
            List<ConsumptionResult> consumptionResultList = new ArrayList<>();
            for (LocalDateTime day = date, endOfDay = date.plusDays(1);
                 day.isBefore(endOfDay); day = day.plusMinutes(15)) {
                for (MeteringPoint meteringPoint : meteringPoints) {
                    ConsumptionResult consumption = new ConsumptionResult(
                            meteringPoint,
                            day,
                            Math.random() * 100
                    );
                    consumptionResultList.add(consumption);
                }
            }
            consumptionsMessageHandlerPort.sendMessage(consumptionResultList, consumptionsQueue);
        }
    }

    private void initializeDatabaseMonthly(int minusMonths) {

        List<MeteringPoint> meteringPoints = meteringPointFileRepositoryPort.loadMeteringPointsFromFile(meteringPointsJsonFileLocation);
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
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Application ready event received, sleeping for 45 seconds");
        try {
            TimeUnit.SECONDS.sleep(45);
            if (demoInit){
                log.info("Initializing database consumptions");
                initializeDatabaseMonthly(2);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
