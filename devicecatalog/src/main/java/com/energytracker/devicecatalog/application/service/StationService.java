package com.energytracker.devicecatalog.application.service;


import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;
import com.energytracker.devicecatalog.application.dto.station.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.station.StationResponseDto;
import com.energytracker.devicecatalog.application.mapper.ChannelMapper;
import com.energytracker.devicecatalog.application.port.inbound.station.UpdateStationByStationIdUseCase;
import com.energytracker.devicecatalog.application.port.inbound.station.*;
import com.energytracker.devicecatalog.application.port.outbound.ChannelRepositoryPort;
import com.energytracker.devicecatalog.application.port.outbound.StationRepositoryPort;
import com.energytracker.devicecatalog.application.mapper.StationMapper;
import com.energytracker.devicecatalog.domain.model.station.Channel;
import com.energytracker.devicecatalog.domain.model.station.Station;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class StationService implements CreateStationUseCase, GetAllStationsUseCase, GetStationByIdUseCase,
        DeactivateStationByIdUseCase, DeleteStationByIdUseCase, GetChannelsByStationIdUseCase,
        GetLonActiveChannelsByStationIdUseCase, UpdateStationByStationIdUseCase {

    private final StationRepositoryPort stationRepositoryPort;
    private final ChannelRepositoryPort channelRepositoryPort;

    @Override
    @Transactional
    public StationResponseDto createStation(CreateStationRequestDto createStationRequestDto) {
        Station station = StationMapper.createStationRequestDtoToDomain(createStationRequestDto);
        Station createdStation;
        if (stationRepositoryPort.existsBySerialNumber(createStationRequestDto.getSerialNumber())) {
            throw new IllegalArgumentException("Serial number already exists");
        }
        try {
            createdStation = stationRepositoryPort.createStation(station);
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Error creating station");
        } catch (Exception e) {
            throw new RuntimeException("Error creating station");
        }
        if (createdStation == null) {
            throw new IllegalStateException("The save operation did not return the created station.");
        }
        return StationMapper.stationResponseDomainToDto(createdStation);
    }

    @Override
    public List<StationResponseDto> getAllStations() {
        List<Station> stations = stationRepositoryPort.getAllStations();
        List<StationResponseDto> stationResponseDtos = new ArrayList<>();
        if (stations.isEmpty()) {
            return stationResponseDtos;
        }
        stations.forEach(station -> {
            stationResponseDtos.add(StationMapper.stationResponseDomainToDto(station));
        });
        return stationResponseDtos;
    }

    @Override
    public StationResponseDto getStationById(Long stationId) {
        try {
            Station station = stationRepositoryPort.getStationById(stationId);
            return StationMapper.stationResponseDomainToDto(station);
        } catch (NotFoundException e) {
            throw new NotFoundException("Station with id " + stationId + " not found");
        } catch (Exception e) {
            throw new RuntimeException("Error getting station with id " + stationId);
        }
    }

    @Override
    public StationResponseDto deactivateStationById(Long stationId) {
        Station station = stationRepositoryPort.getStationById(stationId);
        if (station == null) {
            return null;
        }
        station.deactivate();
        try {
            return StationMapper.stationResponseDomainToDto(stationRepositoryPort.save(station));
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Error deactivating station with id " + stationId + ", entity has been modified");
        } catch (Exception e) {
            throw new NotFoundException("Error deactivating station with id " + stationId);
        }
    }


    @Override
    public void deleteStationById(Long stationId) {
        try {
            stationRepositoryPort.deleteStationById(stationId);
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Error deleting station with id " + stationId + ", entity has been modified");
        } catch (Exception e) {
            throw new NotFoundException("Error deleting station with id " + stationId);
        }
    }

    @Override
    public List<ChannelResponseDto> getChannelsByStationId(Long stationId) {
        List<Channel> channels = new ArrayList<>();
        channels = stationRepositoryPort.getChannelsByStationId(stationId);
        if (channels == null || channels.isEmpty()) {
            return null;
        }
        List<ChannelResponseDto> channelResponseDtos = new ArrayList<>();
        channels.forEach(channel -> {
            channelResponseDtos.add(ChannelMapper.channelDomainToDto(channel));
        });
        return channelResponseDtos;
    }

    public Channel getChannelById(Long channelId) {
        try {
            return channelRepositoryPort.getChannelById(channelId);
        } catch (NotFoundException e) {
            throw new NotFoundException("Channel with id " + channelId + " not found");
        } catch (Exception e) {
            throw new RuntimeException("Error getting channel with id " + channelId);
        }
    }

    @Override
    public List<ChannelResponseDto> getLonActiveChannelsByStationId(Long stationId) {
        List<Channel> channels = new ArrayList<>();
        channels = stationRepositoryPort.getLonActiveChannelsByStationId(stationId);
        if (channels == null || channels.isEmpty()) {
            return null;
        }
        List<ChannelResponseDto> channelResponseDtos = new ArrayList<>();
        channels.forEach(channel -> {
            channelResponseDtos.add(ChannelMapper.channelDomainToDto(channel));
        });
        return channelResponseDtos;
    }

    @Transactional
    @Override
    public StationResponseDto updateStation(Long stationId, CreateStationRequestDto createStationRequestDto) {

        Station station = stationRepositoryPort.getStationById(stationId);
        if (station == null) {
            throw new NotFoundException("Station with id " + stationId + " not found");
        }
        Station checkedStation = checkFields(createStationRequestDto, station);
        Station updatedStation = stationRepositoryPort.updateStation(checkedStation);
        return StationMapper.stationResponseDomainToDto(updatedStation);
    }

    private Station checkFields(CreateStationRequestDto createStationRequestDto, Station station) {
        Field[] fields = createStationRequestDto.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object dtoValue = field.get(createStationRequestDto);

                if (dtoValue == null) {
                    log.info("{} is null", field.getName());
                } else if (dtoValue instanceof String && ((String) dtoValue).trim().isEmpty()) {
                    log.info("{} is empty", field.getName());
                } else {
                    Field entityField = getFieldFromClassHierarchy(station.getClass(), field.getName());
                    if (entityField != null) {
                        entityField.setAccessible(true);

                        Object convertedValue = convertValue(dtoValue, entityField.getType());
                        Object entityValue = entityField.get(station);

                        if (!convertedValue.equals(entityValue)) {
                            log.info("{} has changed", field.getName());
                            entityField.set(station, convertedValue);
                        }
                    } else {
                        log.warn("Field {} does not exist in Station or its superclasses", field.getName());
                    }
                }
            } catch (IllegalAccessException e) {
                log.error("Error accessing fields", e);
                throw new RuntimeException("Error accessing fields", e);
            }
        }
        return station;
    }


    private Field getFieldFromClassHierarchy(Class<?> clazz, String fieldName) {
        Map<String, String> FIELD_NAME_MAP = new HashMap<>();
        FIELD_NAME_MAP.put("stationId", "deviceId");

        String entityFieldName = FIELD_NAME_MAP.getOrDefault(fieldName, fieldName);

        while (clazz != null) {
            try {
                return clazz.getDeclaredField(entityFieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    private Object convertValue(Object value, Class<?> targetType) {
        if (value == null || targetType.isInstance(value)) {
            return value;
        }

        if (targetType.isEnum() && value instanceof String) {
            return Enum.valueOf((Class<Enum>) targetType, (String) value);
        }

        if (targetType == Integer.class || targetType == int.class) {
            return Integer.valueOf(value.toString());
        }

        if (targetType == Long.class || targetType == long.class) {
            return Long.valueOf(value.toString());
        }

        if (targetType == Double.class || targetType == double.class) {
            return Double.valueOf(value.toString());
        }

        if (targetType == Boolean.class || targetType == boolean.class) {
            return Boolean.valueOf(value.toString());
        }

        throw new IllegalArgumentException("Cannot convert value " + value + " to type " + targetType);
    }
}
