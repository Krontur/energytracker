package com.energytracker.datacollector.infrastructure.adapter.outbound.commandcreator;

import com.energytracker.datacollector.application.port.outbound.CommandCreatorPort;
import com.energytracker.datacollector.domain.model.Command;
import com.energytracker.datacollector.domain.model.CommandType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ECLCommandCreatorAdapter implements CommandCreatorPort {

    @Override
    public Command createCommandGetIntervalEnergyOfStation(String stationTag, List<Integer> channels, String start) {
        List<Integer> sortedChannelList = new ArrayList<>(channels);
        Collections.sort(sortedChannelList);
        String commandChannelsText= createCommandChannelsText(sortedChannelList);

        String commandString = stationTag + ": index// " + start + ",du,eint-/ " + sortedChannelList.get(0).toString() + " 1,dau,!,dr,eint## " + commandChannelsText + " . 1";

        return new Command(CommandType.EINT, commandString);
    }

    private String createCommandChannelsText(List<Integer> sortedChannelList) {

        StringBuilder commandChannelsText = new StringBuilder();
        int start = sortedChannelList.get(0);
        int end = start;

        for (int i = 1; i < sortedChannelList.size(); i++) {
            int current = sortedChannelList.get(i);
            if (current == end + 1) {
                end = current;
            } else {
                appendRange(commandChannelsText, start, end);
                start = current;
                end = current;
            }
        }

        appendRange(commandChannelsText, start, end);

        return commandChannelsText.toString();
    }

    private void appendRange(StringBuilder builder, int start, int end) {
        if (!builder.isEmpty()) {
            builder.append("+");
        }
        if (start == end) {
            builder.append(start);
        } else {
            builder.append(start).append("..").append(end);
        }
    }
}
