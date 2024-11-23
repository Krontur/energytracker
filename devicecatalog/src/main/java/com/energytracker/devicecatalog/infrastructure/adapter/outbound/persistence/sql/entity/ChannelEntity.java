package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import com.energytracker.devicecatalog.domain.model.EnergyUnit;
import com.energytracker.devicecatalog.domain.model.PowerUnit;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChannelEntity extends BaseEntity {

    private String channelName;

    private int channelNumber;

    private int channelMode;

    private String channelLongName;

    private EnergyUnit energyUnit;

    private PowerUnit powerUnit;

    private int uRatio;

    private int iRatio;

    private int pFactor;

    private int lonSubChannel;

    private Boolean lonIsActive;

}
