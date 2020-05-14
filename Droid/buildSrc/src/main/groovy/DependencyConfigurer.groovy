import org.gradle.api.Project

public class DependencyConfigurer {
  private Project dependency
  private Project dependentProject
  private Map<String, String> configurations

  def DependencyConfigurer(Project dependentProject, Project dependency, Map<String, String> configurations) {
    this.dependentProject = dependentProject
    this.dependency = dependency
    this.configurations = configurations
  }

  def configureAsProjects() {
    addConfiguration()
    configurations.each { scope, config ->
      dependentProject.dependencies.add(scope, dependentProject.dependencies.project(path: ":${dependency.name}"))
    }
  }

  private def addConfiguration() {
    def dependentProjectConfigs = dependentProject.getConfigurations()
    configurations.each { scope, config ->
      dependentProjectConfigs.maybeCreate(scope)
    }
  }
}
