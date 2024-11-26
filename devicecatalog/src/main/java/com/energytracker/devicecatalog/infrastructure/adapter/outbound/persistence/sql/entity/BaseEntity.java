package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

    public BaseEntity(
            Long id,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Long version
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_seq")
    @SequenceGenerator(name = "device_seq", sequenceName = "devices.device_entity_seq", allocationSize = 1)
    private Long id;

    @Column( nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column( nullable = false)
    private LocalDateTime updatedAt;

    @Column( nullable = false)
    @Version
    private Long version;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
