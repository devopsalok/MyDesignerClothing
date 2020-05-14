import org.gradle.api.Project

class ModuleDependencyResolver {
  static def resolve(Project project, DependencyConfigurer dependencyConfigurer) {
    dependencyConfigurer.configureAsProjects()
    project.repositories.flatDir.dirs "../${project.name}/prebuilt-libs"
  }

  static def getDependentModulesTasks(Project project, String variant, String prefix) {
    ArrayList<String> tasks = new ArrayList<>()

    project.configurations.each { configuration ->
      configuration.getAllDependencies().each { upstreamDep ->
        if (upstreamDep.hasProperty('dependencyProject')) {
          Project depProject = project.rootProject.findProject(":${upstreamDep.dependencyProject.getName()}")
          project.evaluationDependsOn(depProject.path)

          if (depProject.hasProperty('android')) {
            String requiredVariant = GlobalConfig.getFormattedBuildVariants(depProject).find { it ->
              return variant.toLowerCase().contains(it.toLowerCase())
            }

            requiredVariant = requiredVariant == null ? depProject.android.defaultPublishConfig.capitalize() : requiredVariant
            tasks.add(depProject.getTasksByName("${prefix}${requiredVariant.capitalize()}WithDeps", false))
          }
        }
      }
    }
    return tasks
  }
}
