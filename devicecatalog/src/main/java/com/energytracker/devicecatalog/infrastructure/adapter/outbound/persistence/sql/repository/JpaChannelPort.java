package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository;

import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.station.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChannelPort extends JpaRepository<ChannelEntity, Long> {
}
