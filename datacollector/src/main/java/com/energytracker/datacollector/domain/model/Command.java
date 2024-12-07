package com.energytracker.datacollector.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Command {

    private CommandType commandType;
    private String commandValue;

}
