package com.energytracker.devicecatalog.application.service;

import com.energytracker.devicecatalog.application.dto.energymeter.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.energymeter.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.dto.station.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.mapper.EnergyMeterMapper;
import com.energytracker.devicecatalog.application.port.inbound.energymeter.*;
import com.energytracker.devicecatalog.application.port.outbound.EnergyMeterRepositoryPort;
import com.energytracker.devicecatalog.domain.model.DeviceStatus;
import com.energytracker.devicecatalog.domain.model.energymeter.EnergyMeter;
import com.energytracker.devicecatalog.domain.model.station.Station;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class EnergyMeterService  implements CreateEnergyMeterUseCase, GetAllEnergyMetersUseCase,
        GetEnergyMeterByIdUseCase, DeleteEnergyMeterByIdUseCase, DeactivateEnergyMeterByIdUseCase,
        GetInStockEnergyMetersUseCase, UpdateEnergyMeterUseCase {

    private final EnergyMeterRepositoryPort energyMeterRepositoryPort;

    @Override
    public List<EnergyMeterResponseDto> getAllEnergyMeters() {
        List<EnergyMeter> energyMeters = energyMeterRepositoryPort.getAllEnergyMeters();
        List<EnergyMeterResponseDto> energyMeterResponseDtos = new ArrayList<EnergyMeterResponseDto>();
        energyMeters.forEach(energyMeter -> {
            energyMeterResponseDtos.add(EnergyMeterMapper.energyMeterDomainToResponseDto(energyMeter));
        });
        return energyMeterResponseDtos;
    };

    @Transactional
    public EnergyMeterResponseDto createEnergyMeter(CreateEnergyMeterRequestDto createEnergyMeterRequestDto) {
        EnergyMeter energyMeter = EnergyMeterMapper.createEnergyMeterRequestDtoToDomain(createEnergyMeterRequestDto);
        if (energyMeterRepositoryPort.existsBySerialNumber(energyMeter.getSerialNumber())) {
            throw new IllegalArgumentException("Serial number already exists");
        }
        EnergyMeter createdEnergyMeter;
        try {
            createdEnergyMeter = energyMeterRepositoryPort.createEnergyMeter(energyMeter);
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Error creating energy meter, entity has been modified");
        } catch (Exception e) {
            throw new RuntimeException("Error creating energy meter");
        }
        return EnergyMeterMapper.energyMeterDomainToResponseDto(energyMeterRepositoryPort.createEnergyMeter(createdEnergyMeter));

    }

    @Override
    public EnergyMeterResponseDto getEnergyMeterById(Long energyMeterId) {
        EnergyMeter energyMeter = energyMeterRepositoryPort.getEnergyMeterById(energyMeterId);
        if (energyMeter != null) {
            return EnergyMeterMapper.energyMeterDomainToResponseDto(energyMeter);
        }
        return null;
    }

    public void deleteEnergyMeterById(Long energyMeterId) {
        EnergyMeter energyMeter = energyMeterRepositoryPort.getEnergyMeterById(energyMeterId);
        if(energyMeter == null) {
            throw new NotFoundException("Energy Meter with id " + energyMeterId + " not found");
        }
        try {
            energyMeterRepositoryPort.deleteEnergyMeterById(energyMeterId);
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Error deleting energy meter, entity has been modified");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting energy meter");
        }

    }

    @Override
    public EnergyMeterResponseDto deactivateEnergyMeterById(Long energyMeterId) {
        EnergyMeter energyMeter = energyMeterRepositoryPort.getEnergyMeterById(energyMeterId);
        if(energyMeter == null) {
            return null;
        }
        energyMeter.deactivate();
        try {
            return EnergyMeterMapper.energyMeterDomainToResponseDto(energyMeterRepositoryPort.save(energyMeter));
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Error deactivating energy meter, entity has been modified");
        } catch (Exception e) {
            throw new RuntimeException("Error deactivating energy meter");
        }
    }

    @Override
    public List<EnergyMeterResponseDto> getInStockEnergyMeters() {
        List<EnergyMeter> energyMeters = energyMeterRepositoryPort.getInStockEnergyMeters();
        if (energyMeters == null) {
            return null;
        }
        try {
            List<EnergyMeterResponseDto> energyMeterResponseDtos = new ArrayList<EnergyMeterResponseDto>();
            energyMeters.forEach(energyMeter -> {
                energyMeterResponseDtos.add(EnergyMeterMapper.energyMeterDomainToResponseDto(energyMeter));
            });
            return energyMeterResponseDtos;
        } catch (Exception e) {
            throw new RuntimeException("Error getting in stock energy meters");
        }
    }

    @Override
    public EnergyMeterResponseDto updateEnergyMeter(Long energyMeterId, @Valid CreateEnergyMeterRequestDto requestDto) {
        EnergyMeter energyMeter = energyMeterRepositoryPort.getEnergyMeterById(energyMeterId);
        if (energyMeter == null) {
            throw new NotFoundException("Energy Meter with id " + energyMeterId + " not found");
        }

        try {
            log.info("Updating energy meter with ID: {}", energyMeterId);
            checkFields(requestDto, energyMeter);

            EnergyMeter updatedEnergyMeter = energyMeterRepositoryPort.updateEnergyMeter(energyMeterId, energyMeter);
            log.info("Energy meter updated successfully.");
            return EnergyMeterMapper.energyMeterDomainToResponseDto(updatedEnergyMeter);
        } catch (OptimisticLockException e) {
            log.error("Optimistic lock exception while updating energy meter", e);
            throw new OptimisticLockException("Entity has been modified", e);
        } catch (Exception e) {
            log.error("Error updating energy meter", e);
            throw new RuntimeException("Error updating energy meter", e);
        }
    }

    private void checkFields(CreateEnergyMeterRequestDto requestDto, EnergyMeter energyMeter) {
        Field[] fields = requestDto.getClass().getDeclaredFields();
        Map<String, String> fieldNameMap = Map.of("energyMeterId", "deviceId"); // Mapeo de campos

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object dtoValue = field.get(requestDto);
                if (dtoValue != null && !(dtoValue instanceof String && ((String) dtoValue).trim().isEmpty())) {
                    Field entityField = getFieldFromClassHierarchy(energyMeter.getClass(), field.getName(), fieldNameMap);
                    if (entityField != null) {
                        Object convertedValue = convertValue(dtoValue, entityField.getType());
                        if (!convertedValue.equals(entityField.get(energyMeter))) {
                            log.info("Field '{}' has changed. Updating value...", field.getName());
                            entityField.set(energyMeter, convertedValue);
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                log.warn("Error accessing or setting field: {}", field.getName(), e);
            }
        }
    }

    private Field getFieldFromClassHierarchy(Class<?> clazz, String fieldName, Map<String, String> fieldNameMap) {
        String entityFieldName = fieldNameMap.getOrDefault(fieldName, fieldName);

        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(entityFieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                log.debug("Field '{}' not found in class '{}'. Checking superclass...", entityFieldName, clazz.getName());
                clazz = clazz.getSuperclass();
            }
        }

        log.error("Field '{}' not found in class hierarchy starting from '{}'", entityFieldName, clazz.getName());
        throw new IllegalArgumentException("Field '" + fieldName + "' not found in class hierarchy.");
    }

    private Object convertValue(Object value, Class<?> targetType) {
        if (value == null || targetType.isInstance(value)) return value;

        if (targetType.isEnum()) return Enum.valueOf((Class<Enum>) targetType, value.toString().toUpperCase());
        if (targetType == Integer.class || targetType == int.class) return Integer.parseInt(value.toString());
        if (targetType == Long.class || targetType == long.class) return Long.parseLong(value.toString());
        if (targetType == Boolean.class || targetType == boolean.class) return Boolean.parseBoolean(value.toString());

        throw new IllegalArgumentException("Cannot convert value " + value + " to type " + targetType);
    }
}
