package esgi;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

class ArchunitApplicationTests {
     private JavaClasses importedClasses;

       @BeforeEach
       public void setup() {
           importedClasses = new ClassFileImporter()
                   .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                   .importPackages("esgi");
       }

    @ArchTest
    static final ArchRule myRule = ArchRuleDefinition.noClasses().that()
            .resideInAPackage("esgi.infra.entity..")
            .should().accessClassesThat().resideInAPackage("esgi.infra.controller..");

    @Test
    void layeredArchitectureShouldBeRespected() {
        myRule.check(importedClasses);
    }

    @ArchTest
    public static final ArchRule layeredArchitectureShouldBeRespected =
            noClasses().that().resideInAPackage("esgi.infra.controller..")
                    .should().accessClassesThat().resideInAPackage("esgi.infra.service")
                    .orShould().accessClassesThat().resideInAPackage("esgi.infra.serviceImpl");

    /* Package Dependency Checks */

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        noClasses()
                .that().resideInAnyPackage("esgi.infra.service..")
                .or().resideInAnyPackage("esgi.infra.repository..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("esgi.infra.controller..")
                .because("Services and repositories should not depend on web layer")
                .check(importedClasses);
    }

    /* Class Dependency Checks */

    @Test
    void serviceClassesShouldOnlyBeAccessedByController() {
        classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage("..service..", "..controller..")
                .check(importedClasses);
    }

    /* naming convention */

    @Test
    void serviceClassesShouldBeNamedXServiceOrXComponentOrXServiceImpl() {
        classes()
                .that().resideInAPackage("..service..")
                .should().haveSimpleNameEndingWith("Service")
                .orShould().haveSimpleNameEndingWith("ServiceImpl")
                .orShould().haveSimpleNameEndingWith("Component")
                .check(importedClasses);
    }

    @Test
    void repositoryClassesShouldBeNamedXRepository() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().haveSimpleNameEndingWith("Repository")
                .check(importedClasses);
    }

    @Test
    void controllerClassesShouldBeNamedXController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().haveSimpleNameEndingWith("Controller")
                .check(importedClasses);
    }

    /* Annotation check */

   @Test
    void fieldInjectionNotUseAutowiredAnnotation() {

        noFields()
                .should().beAnnotatedWith(Autowired.class)
                .check(importedClasses);
    }

    @Test
    void repositoryClassesShouldHaveSpringRepositoryAnnotation() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().beAnnotatedWith(Repository.class)
                .check(importedClasses);
    }

    @Test
    void serviceClassesShouldHaveSpringServiceAnnotation() {
        classes()
                .that().resideInAPackage("..service..")
                .should().beAnnotatedWith(Service.class)
                .check(importedClasses);
    }


    @Test
    void repositoriesMustResideInRepositoryPackage() {
        classes().that().haveNameMatching(".*Repository").should().resideInAPackage("..repository..")
                .as("Repositories should reside in a package '..repository..'")
                .check(importedClasses);
    }

    @Test
    void domainClassesShouldBeSerializable() {
        classes()
                .that().resideInAPackage("..entity..")
                .should()
                .beAssignableTo(Serializable.class)
                .check(importedClasses);
    }

    @Test
    void interfacesShouldNotHaveNamesEndingWithTheWordInterface() {
        noClasses().that().areInterfaces().should().haveNameMatching(".*Interface").check(importedClasses);
    }

    @Test
    void domainClassesShouldBePublic() {
        classes()
                .that().resideInAPackage("..entity..")
                .should()
                .bePublic()
                .check(importedClasses);
    }

}
