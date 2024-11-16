package com.energytracker.devicecatalog.infrastructure.adapter.outbound.persistence.sql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionTypeEnum extends BaseEntity {

    @Column(name = "connection_type", nullable = false, unique = true)
    private String connectionType;

}
