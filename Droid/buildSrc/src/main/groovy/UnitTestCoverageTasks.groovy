import org.gradle.api.Project
import org.gradle.testing.jacoco.tasks.JacocoReport

class UnitTestCoverageTasks {
  static def configure(Project project) {
    GlobalConfig.getBuildVariants(project).each { buildVariant ->
      def classesDir = "${project.projectDir}/build/intermediates/javac/${buildVariant.classesDirEnd()}"
      coverageGeneration(project, buildVariant.toCamelCase(), classesDir)
    }
  }

  private static def coverageGeneration(Project project, String buildVariant, String classesDir) {
    project.task("generate${buildVariant}JacocoTestReports", type: JacocoReport, dependsOn: "test${buildVariant}UnitTest") {
      group = "Reporting"
      description = "Generate unit test coverage reports for ${buildVariant}"
      classDirectories.from = project.fileTree(
        dir: "${classesDir}",
        excludes: ['**/R.class',
                   '**/R$*.class',
                   '**/*$ViewInjector*.*',
                   '**/*_MembersInjector.class',
                   '**/BuildConfig.*',
                   '**/Manifest*.*',
                   '**/*Activity.class',
                   '**/*Fragment.class',
                   '**/DataBinderMapperImpl.class',
                   '**/DataBinderMapperImpl*.class',
                   '**/DataBindingInfo.class',
                   '**/*BindingInjection*.class',
                   '**/UserDao_Impl*',
                   '**/*Activity$*',
                   '**/*Fragment$*',
                   '**/*BR.class',
                   '**/*Binding.class',
                   '**/*BindingImpl.class',
                   '**/MyDscActivityLifeCycleCallback.class',
                   '**/ApplicationObserver.class',
                   '**/*CrashTracker.class', // static class dependency, cannot stub in unit tests
                   '**/*MyDesignerClothingUtilConstants.class', //class of static variables, nothing to test
                   '**/*FileUtils.class', //class requires device + context, limit to integration tests
                   '**/MyDscAndroidDeviceUtils.class', //class requires device + context, limit to integration tests
                   'android/databinding']
      )
      sourceDirectories.from = project.fileTree(
        dir: "${project.projectDir}/src/main/java",
        excludes: ['**/*Activity.java', '**/*Fragment.java']
      )
      executionData.from = project.files("${project.projectDir}/build/jacoco/test${buildVariant}UnitTest.exec")
      reports.xml.enabled = true
    }

    project.task("check${buildVariant}Coverage", type: CheckCoverageTask, dependsOn: "generate${buildVariant}JacocoTestReports") {
      variant = buildVariant
      module = project
    }
  }
}
