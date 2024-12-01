package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.adapter;

import com.energytracker.devicecatalog.application.port.outbound.ChannelRepositoryPort;
import com.energytracker.devicecatalog.domain.model.station.Channel;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity.station.ChannelEntity;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.mapper.ChannelPersistenceMapper;
import com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.repository.JpaChannelPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class ChannelRepositoryAdapter implements ChannelRepositoryPort {

    private JpaChannelPort jpaChannelPort;

    @Override
    public List<Channel> getAllChannels() {
        List<ChannelEntity> channelEntities = jpaChannelPort.findAll();
        if (channelEntities.isEmpty()) {
            return null;
        }
        List<Channel> channels = new ArrayList<Channel>();
        channelEntities.forEach(channelEntity -> {
            channels.add(ChannelPersistenceMapper.channelEntityToDomain(channelEntity));
        });
        return channels;
    }

    @Override
    public Channel getChannelById(Long channelId) {
        ChannelEntity channelEntity = jpaChannelPort.findById(channelId).orElse(null);
        if (channelEntity != null) {
            return ChannelPersistenceMapper.channelEntityToDomain(channelEntity);
        }
        return null;
    }
}
