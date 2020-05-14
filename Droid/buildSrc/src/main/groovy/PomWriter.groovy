public class PomWriter {
  private def mainNode;

  def PomWriter(mainNode) {
    this.mainNode = mainNode
  }

  def writeDependencies(configurations) {
    def dependenciesNode = this.mainNode.appendNode('dependencies')
    configurations.each { config ->
      config.allDependencies.each {
        addDependency(it, config.name, dependenciesNode)
      }
    }
  }

  private void addDependency(dependency, scope, dependenciesNode) {
    if (dependency.group != null && dependency.name != null) {
      def dependencyNode = dependenciesNode.appendNode('dependency')
      dependencyNode.appendNode('groupId', dependency.group)
      dependencyNode.appendNode('artifactId', dependency.name)
      dependencyNode.appendNode('version', dependency.version)
      dependencyNode.appendNode('scope', scope)

      if (!dependency.excludeRules.isEmpty()) {
        addExclusions(dependencyNode, dependency)
      }
    }
  }

  private void addExclusions(dependencyNode, dependency) {
    def exclusionsNode = dependencyNode.appendNode('exclusions')
    dependency.excludeRules.each { rule ->
      def exclusionNode = exclusionsNode.appendNode('exclusion')
      if (rule.group != null) {
        exclusionNode.appendNode('groupId', rule.group)
      }
      if (rule.module != null) {
        exclusionNode.appendNode('artifactId', rule.module)
      }
    }
  }
}
