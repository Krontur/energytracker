package com.energytracker.datacollector.application.service;

import com.energytracker.datacollector.application.port.inbound.ConsumptionsMessageHandlerPort;
import com.energytracker.datacollector.application.port.outbound.CommandCreatorPort;
import com.energytracker.datacollector.application.port.outbound.MeteringPointFileRepositoryPort;
import com.energytracker.datacollector.application.port.outbound.StationQueryPort;
import com.energytracker.datacollector.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class StationQueryServiceTest {

    @Mock
    private StationQueryPort stationQueryPort;

    @Mock
    private CommandCreatorPort commandCreatorPort;

    @Mock
    private MeteringPointFileRepositoryPort meteringPointFileRepositoryPort;

    @Mock
    private ConsumptionsMessageHandlerPort consumptionsMessageHandlerPort;

    @MockBean
    private StationQueryService stationQueryService;

    @Value("${external.meteringpoints.jsonfile.save.location}")
    private String meteringPointsFileLocation = "test-file.json";

    @Value("${simulation.without.stations}")
    private boolean simulationWithoutStations = true;

    @Value("${rabbitmq.queue.consumptions}")
    private String consumptionsQueueName = "test-queue";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stationQueryService = new StationQueryService(
                stationQueryPort,
                commandCreatorPort,
                meteringPointFileRepositoryPort,
                consumptionsMessageHandlerPort
        );
    }

    @Test
    void testParseConsumptionResults_withValidResults() {
        String rawResult = "CHAN;10;100.0;200.0";
        List<MeteringPoint> meteringPoints = List.of(
                new MeteringPoint(ActionType.ADD, 1L, "Station1", 1),
                new MeteringPoint(ActionType.ADD, 2L, "Station2", 2)
        );

        List<ConsumptionResult> results = stationQueryService.parseConsumptionResults(rawResult, meteringPoints, "01.01.22 12:00:00");

        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    void testParseConsumptionResults_withInvalidFormat() {
        String rawResult = "command;10";
        List<MeteringPoint> meteringPoints = List.of(
                new MeteringPoint(ActionType.ADD, 1L, "Station1", 1),
                new MeteringPoint(ActionType.ADD, 2L, "Station2", 2)
        );

        List<ConsumptionResult> results = stationQueryService.parseConsumptionResults(rawResult, meteringPoints, "01.01.22 12:00:00");

        assertTrue(results.isEmpty());
    }

    @Test
    void testSimulateConsumptionResults() {
        List<MeteringPoint> meteringPoints = List.of(
                new MeteringPoint(ActionType.ADD, 1L, "Station1", 1),
                new MeteringPoint(ActionType.ADD, 2L, "Station2", 2)
        );

        List<ConsumptionResult> results = stationQueryService.simulateConsumptionResults(meteringPoints, "01.01.22 12:00:00");

        assertNotNull(results);
        assertEquals(meteringPoints.size(), results.size());
    }
}
