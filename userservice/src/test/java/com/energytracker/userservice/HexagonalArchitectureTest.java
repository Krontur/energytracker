package com.energytracker.userservice;

import com.energytracker.userservice.application.port.inbound.CreateUserUseCase;
import com.energytracker.userservice.application.port.outbound.UserRepositoryPort;
import com.energytracker.userservice.infrastructure.config.AppConfig;
import com.energytracker.userservice.infrastructure.config.DatabaseInitializer;
import com.energytracker.userservice.infrastructure.config.SecurityConfig;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.conditions.ArchPredicates;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.belongToAnyOf;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;


@AnalyzeClasses(packages = "com.energytracker.userservice", importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class HexagonalArchitectureTest {

    @ArchTest
    static final ArchRule hexagonal_architecture_is_respected = onionArchitecture()
            .domainModels("..domain.model..", "..domain.exception..")
            .applicationServices("..application..")
            .adapter("configuration", "..infrastructure.adapter.configuration..")
            .adapter("persistence", "..infrastructure.adapter.outbound..")
            .adapter("rest", "..infrastructure.adapter.inbound..")
            .ignoreDependency(belongToAnyOf(DatabaseInitializer.class), DescribedPredicate.alwaysTrue())
            .ignoreDependency(belongToAnyOf(AppConfig.class), DescribedPredicate.alwaysTrue())
            .ignoreDependency(belongToAnyOf(SecurityConfig.class), DescribedPredicate.alwaysTrue())
            .allowEmptyShould(true);
}