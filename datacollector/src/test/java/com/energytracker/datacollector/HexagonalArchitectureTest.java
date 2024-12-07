package com.energytracker.datacollector;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;


@AnalyzeClasses(packages = "com.energytracker.datacollector", importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class HexagonalArchitectureTest {

    @ArchTest
    static final ArchRule hexagonal_architecture_is_respected = onionArchitecture()
            .domainModels("..domain.model..", "..domain.exception..")
            .applicationServices("..application..")
            .adapter("configuration", "..infrastructure.adapter.configuration..")
            .adapter("controller", "..infrastructure.adapter.inbound..")
            .adapter("messaging", "..infrastructure.adapter.inbound.messaging..")
            .adapter("connection", "..infrastructure.adapter.outbound.connection..")
            .adapter("commandcreator", "..infrastructure.adapter.outbound.commandcreator..")
            .adapter("station", "..infrastructure.adapter.outbound.station..")
            .adapter("persistence-messaging", "..infrastructure.adapter.outbound.messaging..")
            .adapter("persistence-file", "..infrastructure.adapter.outbound.persistence.file..")
            .allowEmptyShould(true);

    @ArchTest
    static final ArchRule no_cycles_in_hexagonal_architecture = slices()
            .matching("com.energytracker.datacollector.(*)..")
            .should().beFreeOfCycles();
}