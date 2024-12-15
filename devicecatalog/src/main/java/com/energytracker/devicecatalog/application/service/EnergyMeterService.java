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
    public EnergyMeterResponseDto updateEnergyMeter(Long energyMeterId, CreateEnergyMeterRequestDto createEnergyMeterRequestDto) {
        EnergyMeter energyMeter = energyMeterRepositoryPort.getEnergyMeterById(energyMeterId);
        if(energyMeter == null) {
            throw new NotFoundException("Energy Meter with id " + energyMeterId + " not found");
        }
        try {
            EnergyMeter checkedEnergyMeter = checkFields(createEnergyMeterRequestDto, energyMeter);
            return EnergyMeterMapper.energyMeterDomainToResponseDto(energyMeterRepositoryPort.updateEnergyMeter(energyMeterId, checkedEnergyMeter));
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("Error updating energy meter, entity has been modified");
        } catch (Exception e) {
            throw new RuntimeException("Error updating energy meter");
        }
    }

    private EnergyMeter checkFields(CreateEnergyMeterRequestDto createEnergyMeterRequestDto, EnergyMeter energyMeter) {
        Field[] fields = createEnergyMeterRequestDto.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object dtoValue = field.get(createEnergyMeterRequestDto);

                if (dtoValue == null) {
                    log.info("{} is null", field.getName());
                } else if (dtoValue instanceof String && ((String) dtoValue).trim().isEmpty()) {
                    log.info("{} is empty", field.getName());
                } else {
                    Field entityField = getFieldFromClassHierarchy(energyMeter.getClass(), field.getName());
                    if (entityField != null) {
                        entityField.setAccessible(true);

                        Object convertedValue = convertValue(dtoValue, entityField.getType());
                        Object entityValue = entityField.get(energyMeter);

                        if (!convertedValue.equals(entityValue)) {
                            log.info("{} has changed", field.getName());
                            entityField.set(energyMeter, convertedValue);
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
        return energyMeter;
    }


    private Field getFieldFromClassHierarchy(Class<?> clazz, String fieldName) {
        Map<String, String> FIELD_NAME_MAP = new HashMap<>();
        FIELD_NAME_MAP.put("energyMeterId", "deviceId");

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
