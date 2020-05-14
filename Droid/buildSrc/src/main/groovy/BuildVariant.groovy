public class BuildVariant {
  private Optional<String> flavor
  private String buildType

  BuildVariant(Optional<String> flavor, String buildType) {
    this.flavor = flavor
    this.buildType = buildType
  }

  def toCamelCase() {
    if (!flavor.isPresent()) {
      return buildType.capitalize()
    }
    return flavor.get().capitalize() + buildType.capitalize()
  }

  def classesDirEnd() {
    return flavor.isPresent() ?
      "${flavor.get().toLowerCase()}${buildType.capitalize()}" :
      "${buildType.toLowerCase()}"
  }

  def getFlavor() {
    return flavor
  }

  def getBuildType() {
    return buildType
  }

  def isDebug() {
    return buildType == 'debug'
  }
}
