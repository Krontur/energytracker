package com.energytracker.datacollector.infrastructure.adapter.outbound.station;

import com.energytracker.datacollector.application.port.outbound.ConnectionPort;
import com.energytracker.datacollector.application.port.outbound.StationQueryPort;
import com.energytracker.datacollector.domain.model.Command;
import com.energytracker.datacollector.infrastructure.adapter.outbound.connection.TCPConnection;
import org.springframework.stereotype.Repository;

import java.net.SocketException;

@Repository
public class StationQueryAdapter implements StationQueryPort {

    private final ConnectionPort connectionPort;

    public StationQueryAdapter(ConnectionPort connectionPort) {
        this.connectionPort = connectionPort;
    }

    @Override
    public String getConsumptionsByStationTag(Command command) {
        try {
            connectionPort.openConnection();
            connectionPort.sendCommand(command.getCommandValue());
            String data = connectionPort.receiveData();
            connectionPort.closeConnection();
            return data;
        } catch (Exception e) {
            throw new RuntimeException("Error while getting consumptions by station tag", e);
        }
    }

}
