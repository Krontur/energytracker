package com.energytracker.datacollector.application.service;

import com.energytracker.datacollector.application.port.outbound.MeteringPointFileRepositoryPort;
import com.energytracker.datacollector.domain.model.ActionType;
import com.energytracker.datacollector.domain.model.MeteringPoint;
import io.micrometer.core.instrument.Meter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MeteringPointsPersistenceServiceTest {

    @Mock
    private MeteringPointFileRepositoryPort meteringPointFileRepositoryPort;

    @MockBean
    private MeteringPointsPersistenceService meteringPointsPersistenceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        meteringPointsPersistenceService = new MeteringPointsPersistenceService(meteringPointFileRepositoryPort);
    }

    @Test
    void testLoadMeteringPointsFromFile_validFilePath() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("test-metering-points", ".json");
            Files.writeString(tempFile, "[{\"actionType\":\"ADD\",\"meteringPointId\":1,\"stationTag\":\"Station1\",\"channelNumber\":1}]");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<MeteringPoint> meteringPoints = Collections.singletonList(new MeteringPoint(ActionType.ADD, 1L, "Station1", 1));
        when(meteringPointFileRepositoryPort.loadMeteringPointsFromFile(tempFile.toString())).thenReturn(meteringPoints);

        List<MeteringPoint> result = meteringPointsPersistenceService.loadMeteringPointsFromFile(tempFile.toString());

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(meteringPointFileRepositoryPort, times(1)).loadMeteringPointsFromFile(tempFile.toString());

        try {
            Files.deleteIfExists(tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testLoadMeteringPointsFromFile_nullFilePath() {
        assertThrows(IllegalArgumentException.class, () ->
                meteringPointsPersistenceService.loadMeteringPointsFromFile(null));
        verifyNoInteractions(meteringPointFileRepositoryPort);
    }

    @Test
    void testSaveMeteringPointsToFile_validInputs() {
        String filePath = "valid-file-path.json";
        List<MeteringPoint> meteringPoints = Collections.singletonList(new MeteringPoint(ActionType.ADD, 1L, "Station1", 1));

        meteringPointsPersistenceService.saveMeteringPointsToFile(meteringPoints, filePath);

        verify(meteringPointFileRepositoryPort, times(1)).saveMeteringPointsToFile(meteringPoints, filePath);
    }

    @Test
    void testSaveMeteringPointsToFile_nullFilePath() {
        List<MeteringPoint> meteringPoints = Collections.singletonList(new MeteringPoint(ActionType.ADD, 1L, "Station1", 1));

        assertThrows(IllegalArgumentException.class, () ->
                meteringPointsPersistenceService.saveMeteringPointsToFile(meteringPoints, null));
        verifyNoInteractions(meteringPointFileRepositoryPort);
    }

    @Test
    void testAddMeteringPointToFile_duplicateMeteringPoint() throws IOException {
        Path tempFile = Files.createTempFile("test-metering-points", ".json");
        String jsonContent = "[{\"actionType\":\"ADD\",\"meteringPointId\":1,\"stationTag\":\"Station1\",\"channelNumber\":1}]";
        Files.writeString(tempFile, jsonContent);

        MeteringPoint existingMeteringPoint = new MeteringPoint(ActionType.ADD, 1L, "Station1", 1);
        List<MeteringPoint> meteringPoints = new ArrayList<>();
        meteringPoints.add(existingMeteringPoint);

        when(meteringPointFileRepositoryPort.loadMeteringPointsFromFile(tempFile.toString())).thenReturn(meteringPoints);

        MeteringPoint newMeteringPoint = new MeteringPoint(ActionType.ADD, 1L, "Station1", 1);
        assertThrows(IllegalArgumentException.class, () ->
                meteringPointsPersistenceService.addMeteringPointToFile(newMeteringPoint, tempFile.toString())
        );

        verify(meteringPointFileRepositoryPort, times(1)).loadMeteringPointsFromFile(tempFile.toString());
        verifyNoMoreInteractions(meteringPointFileRepositoryPort);

        Files.deleteIfExists(tempFile);
    }


    @Test
    void testDeleteMeteringPointFromFile_meteringPointExists() throws IOException {
        Path tempFile = Files.createTempFile("test-metering-points", ".json");
        String jsonContent = "[{\"actionType\":\"ADD\",\"meteringPointId\":1,\"stationTag\":\"Station1\",\"channelNumber\":1}," +
                "{\"actionType\":\"DELETE\",\"meteringPointId\":2,\"stationTag\":\"Station1\",\"channelNumber\":2}]";
        Files.writeString(tempFile, jsonContent);

        MeteringPoint existingMeteringPoint = new MeteringPoint(ActionType.ADD, 1L, "Station1", 1);
        MeteringPoint meteringPointToDelete = new MeteringPoint(ActionType.DELETE, 2L, "Station1", 2);
        List<MeteringPoint> meteringPoints = new ArrayList<>();
        meteringPoints.add(existingMeteringPoint);
        meteringPoints.add(meteringPointToDelete);

        when(meteringPointFileRepositoryPort.loadMeteringPointsFromFile(tempFile.toString())).thenReturn(meteringPoints);
        List<MeteringPoint> meteringPointsDeleted = new ArrayList<>(meteringPoints.stream().toList());
        meteringPointsDeleted.remove(meteringPointToDelete);
        meteringPointsPersistenceService.deleteMeteringPointFromFile(meteringPointToDelete, tempFile.toString());

        verify(meteringPointFileRepositoryPort, times(1)).loadMeteringPointsFromFile(tempFile.toString());
        verify(meteringPointFileRepositoryPort, times(1)).saveMeteringPointsToFile(anyList(), eq(tempFile.toString()));

        Files.deleteIfExists(tempFile);
    }


    @Test
    void testDeleteMeteringPointFromFile_meteringPointDoesNotExist() throws IOException {
        Path tempFile = Files.createTempFile("test-metering-points", ".json");
        String jsonContent = "[{\"actionType\":\"ADD\",\"meteringPointId\":1,\"stationTag\":\"Station1\",\"channelNumber\":1}]";
        Files.writeString(tempFile, jsonContent);

        MeteringPoint existingMeteringPoint = new MeteringPoint(ActionType.ADD, 1L, "Station1", 1);
        List<MeteringPoint> meteringPoints = new ArrayList<>();
        meteringPoints.add(existingMeteringPoint);

        when(meteringPointFileRepositoryPort.loadMeteringPointsFromFile(tempFile.toString())).thenReturn(meteringPoints);

        MeteringPoint meteringPointToDelete = new MeteringPoint(ActionType.DELETE, 2L, "Station2", 2);
        assertThrows(IllegalArgumentException.class, () ->
                meteringPointsPersistenceService.deleteMeteringPointFromFile(meteringPointToDelete, tempFile.toString())
        );

        verify(meteringPointFileRepositoryPort, times(1)).loadMeteringPointsFromFile(tempFile.toString());
        verifyNoMoreInteractions(meteringPointFileRepositoryPort);

        Files.deleteIfExists(tempFile);
    }

}
