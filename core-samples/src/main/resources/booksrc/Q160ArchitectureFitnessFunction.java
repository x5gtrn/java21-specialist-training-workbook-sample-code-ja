@Test
void controllersShouldNotAccessRepositories() {
    noClasses()
        .that().resideInAPackage("..controller..")
        .should().dependOnClassesThat()
        .resideInAPackage("..repository..")
        .check(importedClasses);
}
@@BLOCK@@
@AnalyzeClasses(packages = "com.example")
class ArchitectureTest {
    @ArchTest
    static final ArchRule servicesShouldNotDependOnControllers =
        noClasses().that().resideInAPackage("..service..")
            .should().dependOnClassesThat()
            .resideInAPackage("..controller..");
}
@@BLOCK@@
mockMvc.perform(get("/unknown"))
    .andExpect(status().isNotFound())
    .andExpect(jsonPath("$.type").exists())
    .andExpect(jsonPath("$.title").exists());