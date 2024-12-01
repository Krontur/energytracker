package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.station;

import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChannelEntity extends BaseEntity {

    @NotNull
    private String channelName;

    @NotNull
    @Min(1)
    @Max(64)
    private int channelNumber;

    @NotNull
    @Min(0)
    @Max(4)
    private int channelMode;

    @NotNull
    private String channelLongName;

    @NotNull
    private EnergyUnitEntity energyUnit;

    @NotNull
    private PowerUnitEntity powerUnit;

    @NotNull
    @Min(1)
    private int uRatio;

    @NotNull
    @Min(1)
    private int iRatio;

    @NotNull
    @Min(1)
    private int pFactor;

    @NotNull
    @Min(1)
    private int lonSubChannel;

    @NotNull
    private Boolean lonIsActive;

    @Column(name = "station_id")
    private Long stationId;

    public ChannelEntity(int channelNumber, String channelName, int channelMode,
                         String channelLongName, String energyUnit, String powerUnit,
                         int uRatio, int iRatio, int pFactor, int lonSubChannel, Boolean lonIsActive) {
        this.channelNumber = channelNumber;
        this.channelName = channelName;
        this.channelMode = channelMode;
        this.channelLongName = channelLongName;
        this.energyUnit = EnergyUnitEntity.valueOf(energyUnit);
        this.powerUnit = PowerUnitEntity.valueOf(powerUnit);
        this.uRatio = uRatio;
        this.iRatio = iRatio;
        this.pFactor = pFactor;
        this.lonSubChannel = lonSubChannel;
        this.lonIsActive = lonIsActive;

    }

    public ChannelEntity(Long channelId, LocalDateTime createdAt, LocalDateTime updatedAt, Long version,
                         int channelNumber, String channelName, int channelMode,
                         String channelLongName, String name, String name1, int uRatio,
                         int iRatio, int pFactor, int lonSubChannel, Boolean lonIsActive, Long stationId) {
        super(channelId, createdAt, updatedAt, version);
        this.channelNumber = channelNumber;
        this.channelName = channelName;
        this.channelMode = channelMode;
        this.channelLongName = channelLongName;
        this.energyUnit = EnergyUnitEntity.valueOf(name);
        this.powerUnit = PowerUnitEntity.valueOf(name1);
        this.uRatio = uRatio;
        this.iRatio = iRatio;
        this.pFactor = pFactor;
        this.lonSubChannel = lonSubChannel;
        this.lonIsActive = lonIsActive;
        this.stationId = stationId;

    }
}
