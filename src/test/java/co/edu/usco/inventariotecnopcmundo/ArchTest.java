package co.edu.usco.inventariotecnopcmundo;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("co.edu.usco.inventariotecnopcmundo");

        noClasses()
            .that()
            .resideInAnyPackage("co.edu.usco.inventariotecnopcmundo.service..")
            .or()
            .resideInAnyPackage("co.edu.usco.inventariotecnopcmundo.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..co.edu.usco.inventariotecnopcmundo.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
