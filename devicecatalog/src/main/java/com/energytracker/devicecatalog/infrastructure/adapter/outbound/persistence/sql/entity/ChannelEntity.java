package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import com.energytracker.devicecatalog.domain.model.EnergyUnit;
import com.energytracker.devicecatalog.domain.model.PowerUnit;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public ChannelEntity(int channelNumber, String channelName, int channelMode,
                         String channelLongName, String energyUnit, String powerUnit,
                         int uRatio, int iRatio, int pFactor, int lonSubChannel, Boolean lonIsActive) {
        this.channelNumber = channelNumber;
        this.channelName = channelName;
        this.channelMode = channelMode;
        this.channelLongName = channelLongName;
        this.energyUnit = EnergyUnit.valueOf(energyUnit);
        this.powerUnit = PowerUnit.valueOf(powerUnit);
        this.uRatio = uRatio;
        this.iRatio = iRatio;
        this.pFactor = pFactor;
        this.lonSubChannel = lonSubChannel;
        this.lonIsActive = lonIsActive;

    }
}
