package com.energytracker.devicecatalog;

import com.energytracker.devicecatalog.config.DatabaseInitializer;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.belongToAnyOf;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;


@AnalyzeClasses(packages = "com.energytracker.devicecatalog", importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class HexagonalArchitectureTest {

    @ArchTest
    static final ArchRule hexagonal_architecture_is_respected = onionArchitecture()
            .domainModels("..domain.model..", "..domain.exception..")
            .applicationServices("..application..")
            .adapter("configuration", "..infrastructure.adapter.configuration..")
            .adapter("persistence", "..infrastructure.adapter.outbound..")
            .adapter("rest", "..infrastructure.adapter.inbound..")
            .ignoreDependency(belongToAnyOf(DatabaseInitializer.class), DescribedPredicate.alwaysTrue())
            .allowEmptyShould(true);
}