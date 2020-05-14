import org.gradle.api.DefaultTask
import org.gradle.api.Project

class IntegrationTestCoverageTasks {

  static def configure(Project project) {
    GlobalConfig.getBuildVariants(project).each { variant ->
      if (variant.isDebug()) {
        createTasks(project, variant)
      }
    }
  }
/*
Note about codeCoverage
from command line, pass -DintegrationTestCoverageEnabled=true with gradlew command
This is currently handled/allowed with manifest permissions for WRITE_EXTERNAL_STORAGE
There may be a need to modify the build scripts in the event that the permission needs to be granted.
Something like the following would need to be added to the android body in a build.gradle
android.productFlavors.all { flavour ->
    def applicationId = flavour.applicationId
    def adb = android.getAdbExecutable().toString()

    def grantPermissionsTask = tasks.create("grant${flavour.name.capitalize()}Permissions") << {
        "${adb} shell pm grant ${applicationId} android.permission.WRITE_EXTERNAL_STORAGE".execute()
    }
    grantPermissionsTask.description = "Grants permissions for Marshmallow"

    tasks.whenTaskAdded { theTask ->
        def assemblePattern = ~"assemble${flavour.name.capitalize()}DebugAndroidTest"
        if (assemblePattern.matcher(theTask.name).matches()) {
            theTask.dependsOn grantPermissionsTask.name
        }
    }
}
 */
  private static void createTasks(Project project, BuildVariant variant) {
    project.android.buildTypes.debug.testCoverageEnabled = project.rootProject.ext.integrationTestCoverageEnabled
    def integrationCoverageTask = "integration${variant.toCamelCase()}WithCoverage"
    def composerTask = "test${variant.toCamelCase()}Composer"

    project.task(integrationCoverageTask, type: DefaultTask, dependsOn: composerTask)

    project.composer {
      instrumentationArgument('clearPackageData', 'true')
      withOrchestrator true
      if (project.hasProperty('deviceId')) {
        devices(project.deviceId)
      }
      shard true
      verboseOutput true
    }
  }
}
