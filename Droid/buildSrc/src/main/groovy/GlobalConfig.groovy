import org.gradle.api.Project
import org.slf4j.LoggerFactory

class GlobalConfig {
  static final def androidApplications = ['app'].asImmutable()
  static final def testHelpers = ['testutilities'].asImmutable()
  static final def myDesignerEnv = myDesignerClothingEnv()
  static final def apiProductionHost = getProductionUrl()

  static def getFormattedBuildVariants(Project project) {
    return getBuildVariants(project).collect { it.toCamelCase() }
  }

  static def getBuildVariants(Project project) {
    def flavors = flavors(project)
    def buildTypes = buildTypes(project)
    def buildVariants = []

    if (flavors.isEmpty()) {
      return buildTypes.collect { new BuildVariant(Optional.empty(), it) }
    }

    def addBuildVariant = { f, b ->
      b.each { it ->
        buildVariants.add(new BuildVariant(Optional.of(f), it))
      }
    }
    flavors.each { f ->
      addBuildVariant(f, buildTypes)
    }
    return buildVariants;
  }

  static def buildTypes(Project project) {
    return project.android.buildTypes.collect { it.name }
  }

  static def flavors(Project project) {
    return project.android.productFlavors.collect { it.name }
  }

  static def shouldConfigure(Project project) {
    return project.hasProperty('android') &&
      !testHelpers.contains(project.name)
  }

  static def shouldPublishAsLibrary(String projectName) {
    return !androidApplications.contains(projectName) &&
      !testHelpers.contains(projectName)
  }

  private static def myDesignerClothingEnv() {
    def commandLineDMusingsEnv = System.getenv('MY_DESIGNER_CLOTHING_ENV')
    if (commandLineDMusingsEnv == null) {
      def logger = LoggerFactory.getLogger("GlobalConfig")
      logger.warn("                ******************               ")
      logger.warn("myDesingerClothing_ENV is not specified!! Defaulting to \"dev\"")
      logger.warn("                ******************               ")
      return "dev"
    }
    return commandLineDMusingsEnv
  }

  private static def getProductionUrl() {
    def logger = LoggerFactory.getLogger("GlobalConfig")
    logger.warn("                sssssssssssssssssss               ")
      return "http://mydesignerclothing.com/"
  }
}
