package com.energytracker.datacollector.application.port.outbound;

public interface ConfigLoaderPort {

    boolean getBooleanProperty(String key);
    int getIntProperty(String key);
    String getProperty(String key);

}
