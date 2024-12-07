package com.energytracker.datacollector.application.port.outbound;

import java.io.IOException;

public interface ConnectionPort {

    void openConnection();
    void closeConnection();
    void sendCommand(String command);
    String receiveData() throws IOException;
    boolean isOpened();

}
