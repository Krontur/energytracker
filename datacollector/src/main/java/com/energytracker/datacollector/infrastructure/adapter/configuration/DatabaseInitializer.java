package com.energytracker.datacollector.infrastructure.adapter.configuration;

import com.energytracker.datacollector.application.port.inbound.ConsumptionsMessageHandlerPort;
import com.energytracker.datacollector.application.port.outbound.MeteringPointFileRepositoryPort;
import com.energytracker.datacollector.domain.model.ConsumptionResult;
import com.energytracker.datacollector.domain.model.MeteringPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    @Value("${demo.init}")
    private boolean demoInit;

    @Value("${external.meteringpoints.jsonfile.save.location}")
    private String meteringPointsJsonFileLocation;

    @Value("${rabbitmq.queue.consumptions}")
    private String consumptionsQueue;

    private final ConsumptionsMessageHandlerPort consumptionsMessageHandlerPort;
    private final MeteringPointFileRepositoryPort meteringPointFileRepositoryPort;

    @Override
    public void run(String... args) throws Exception {
        if (demoInit){
            TimeUnit.SECONDS.sleep(1);
            log.info("Initializing database consumptions");
            initializeDatabase();
        }
    }

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
}
