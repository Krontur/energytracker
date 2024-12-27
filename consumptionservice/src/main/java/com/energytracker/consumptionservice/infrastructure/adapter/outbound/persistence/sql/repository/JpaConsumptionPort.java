package com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.repository;

import com.energytracker.consumptionservice.application.dto.DailyConsumptionDto;
import com.energytracker.consumptionservice.application.dto.IntervalTotalConsumptionDto;
import com.energytracker.consumptionservice.application.dto.MonthlyConsumptionDto;
import com.energytracker.consumptionservice.application.dto.YearlyConsumptionDto;
import com.energytracker.consumptionservice.infrastructure.adapter.outbound.persistence.sql.entity.ConsumptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

import java.time.LocalDateTime;

public interface JpaConsumptionPort extends JpaRepository<ConsumptionEntity, Long> {

    List<ConsumptionEntity> findByMeteringPointId(Long meteringPointId);

    List<ConsumptionEntity> findConsumptionEntitiesByMeteringPointIdAndConsumptionTimestampBetweenOrderByConsumptionTimestampAsc(Long meteringPointId, LocalDateTime consumptionTimestampAfter, LocalDateTime consumptionTimestampBefore);

    ConsumptionEntity findConsumptionByMeteringPointIdAndConsumptionTimestamp(Long meteringPointId, LocalDateTime consumptionTimestamp);

    @Query("SELECT NEW com.energytracker.consumptionservice.application.dto.IntervalTotalConsumptionDto(" +
            "c.meteringPointId, :startDate, :endDate, SUM(c.consumptionValue)) " +
            "FROM ConsumptionEntity c " +
            "WHERE c.meteringPointId = :meteringPointId AND c.consumptionTimestamp BETWEEN :startDate AND :endDate " +
            "GROUP BY c.meteringPointId")
    IntervalTotalConsumptionDto findIntervalTotalConsumptionByMeteringPointId(
            @Param("meteringPointId") String meteringPointId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = """
            SELECT
                new com.energytracker.consumptionservice.application.dto.DailyConsumptionDto(
                    c.meteringPointId,
                    CAST(c.consumptionTimestamp AS DATE),
                    CAST(c.consumptionTimestamp AS DATE),
                    SUM(c.consumptionValue)
                )
            FROM
                ConsumptionEntity c
            WHERE
                c.meteringPointId = :meteringPointId
              AND c.consumptionTimestamp BETWEEN :startDate AND :endDate
            GROUP BY
                c.meteringPointId, CAST(c.consumptionTimestamp AS DATE)
            ORDER BY
                CAST(c.consumptionTimestamp AS DATE)
            """)
    List<DailyConsumptionDto> findDailyConsumptionByMeteringPointId(
            @Param("meteringPointId") Long meteringPointId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT NEW com.energytracker.consumptionservice.application.dto.MonthlyConsumptionDto(" +
            "c.meteringPointId, " +
            "EXTRACT(MONTH FROM c.consumptionTimestamp), " +
            "EXTRACT(YEAR FROM c.consumptionTimestamp), " +
            "SUM(c.consumptionValue)) " +
            "FROM ConsumptionEntity c " +
            "WHERE c.meteringPointId = :meteringPointId " +
            "AND c.consumptionTimestamp BETWEEN :startDate AND :endDate " +
            "GROUP BY c.meteringPointId, EXTRACT(YEAR FROM c.consumptionTimestamp), EXTRACT(MONTH FROM c.consumptionTimestamp) " +
            "ORDER BY EXTRACT(YEAR FROM c.consumptionTimestamp), EXTRACT(MONTH FROM c.consumptionTimestamp)")
    List<MonthlyConsumptionDto> findMonthlyConsumptionByMeteringPointId(
            @Param("meteringPointId") Long meteringPointId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);


    @Query("SELECT NEW com.energytracker.consumptionservice.application.dto.YearlyConsumptionDto(" +
            "c.meteringPointId, " +
            "EXTRACT(YEAR FROM c.consumptionTimestamp), " +
            "SUM(c.consumptionValue)) " +
            "FROM ConsumptionEntity c " +
            "WHERE c.meteringPointId = :meteringPointId " +
            "AND c.consumptionTimestamp BETWEEN :startDate AND :endDate " +
            "GROUP BY c.meteringPointId, EXTRACT(YEAR FROM c.consumptionTimestamp) " +
            "ORDER BY EXTRACT(YEAR FROM c.consumptionTimestamp)")
    List<YearlyConsumptionDto> findYearlyConsumptionByMeteringPointId(
            @Param("meteringPointId") Long meteringPointId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    boolean existsByMeteringPointIdAndConsumptionTimestamp(Long meteringPointId, LocalDateTime consumptionTimestamp);
}