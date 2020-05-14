import org.gradle.api.DefaultTask
import org.gradle.api.Project

class TaskGraphEnhancer {

  static def enhance(Project project) {
    GlobalConfig.getBuildVariants(project).each { buildVariant ->
      def formattedBuildVariant = buildVariant.toCamelCase()
      configureLintTask(project, formattedBuildVariant)
      configureUnitTestTask(project, formattedBuildVariant)

      if (buildVariant.isDebug()) {
        configureIntegrationTestTask(project, formattedBuildVariant)
      }
    }
  }

  private static def configureLintTask(Project project, String formattedBuildVariant) {
    project.task("lint${formattedBuildVariant}WithDeps", type: DefaultTask, dependsOn: "lint${formattedBuildVariant}") {
      group 'Lint'
      description 'Runs lint for this module and all its upstream deps'
      ModuleDependencyResolver.getDependentModulesTasks(project, formattedBuildVariant, 'lint').each { task ->
        dependsOn task
      }
    }
  }

  private static def configureIntegrationTestTask(Project project, String formattedBuildVariant) {
    project.task("integrationTest${formattedBuildVariant}WithDeps", type: DefaultTask, dependsOn: "test${formattedBuildVariant}Composer") {
      group "Test"
      description "Runs Integration tests for this module and all its upstream deps only for Debug Builds"
      ModuleDependencyResolver.getDependentModulesTasks(it.project, formattedBuildVariant, 'integrationTest').each { task ->
        dependsOn task
      }
    }
  }

  private static def configureUnitTestTask(Project project, String formattedBuildVariant) {
    project.task("unitTest${formattedBuildVariant}WithDeps", type: DefaultTask, dependsOn: "test${formattedBuildVariant}UnitTest") {
      group "Test"
      description "Runs Spec for this module and all its upstream deps"
      ModuleDependencyResolver.getDependentModulesTasks(it.project, formattedBuildVariant, 'unitTest').each { task ->
        dependsOn task
      }
      ModuleDependencyResolver.getDependentModulesTasks(it.project, formattedBuildVariant, 'lint').each { task ->
        dependsOn task
        mustRunAfter task
      }
      project.getTasksByName("check${formattedBuildVariant}Coverage", false).each { task ->
        dependsOn "lint${formattedBuildVariant}"
        finalizedBy task
      }
    }
  }
}
