package com.energytracker.consumptionservice;

import com.energytracker.consumptionservice.infrastructure.adapter.configuration.SecurityConfig;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.belongToAnyOf;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;


@AnalyzeClasses(packages = "com.energytracker.consumptionservice", importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class HexagonalArchitectureTest {

    @ArchTest
    static final ArchRule hexagonal_architecture_is_respected = onionArchitecture()
            .domainModels("..domain.model..", "..domain.exception..")
            .applicationServices("..application..")
            .adapter("configuration", "..infrastructure.adapter.configuration..")
            .adapter("controller", "..infrastructure.adapter.inbound.rest..")
            .adapter("messaging", "..infrastructure.adapter.inbound.messaging..")
            .adapter("persistence-messaging", "..infrastructure.adapter.outbound.messaging..")
            .adapter("persistence-sql", "..infrastructure.adapter.outbound.persistence.sql..")
            .ignoreDependency(belongToAnyOf(SecurityConfig.class), DescribedPredicate.alwaysTrue())
            .allowEmptyShould(true);

    @ArchTest
    static final ArchRule no_cycles_in_hexagonal_architecture = slices()
            .matching("com.energytracker.consumptionservice.(*)..")
            .should().beFreeOfCycles();
}