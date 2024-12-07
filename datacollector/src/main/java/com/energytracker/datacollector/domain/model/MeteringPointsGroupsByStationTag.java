package com.energytracker.datacollector.domain.model;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class MeteringPointsGroupsByStationTag {

    private final Map<String, List<MeteringPoint>> meteringPointsGroupsByStationTag;

    public MeteringPointsGroupsByStationTag(List<MeteringPoint> meteringPointList) {
        this.meteringPointsGroupsByStationTag = meteringPointList.stream().collect(
                Collectors.groupingBy(MeteringPoint::getStationTag));
    }
}
