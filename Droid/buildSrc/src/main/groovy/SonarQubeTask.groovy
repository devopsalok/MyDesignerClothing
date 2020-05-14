import org.gradle.api.Project

class SonarQubeTask {

  static def setup(Project project) {
    def pipelineVersion = (System.getenv("GO_PIPELINE_COUNTER") ?: 1).toInteger()
    def variant = GlobalConfig.flavors(project).contains("app") ? "Debug" : "debug"
    project.sonarqube {
      properties {
        property "sonar.host.url", System.getenv("SONAR_HOST_URL")
        property "sonar.login", System.getenv("SONAR_LOGIN")
        property "sonar.password", System.getenv("SONAR_PASSWORD")
        property "sonar.projectVersion", pipelineVersion
        property "sonar.projectName", "${project.name}-Android"
        property "sonar.language", "java"
        property "sonar.java.coveragePlugin", "jacoco"
        property "sonar.android.lint.report", "artifacts/lint/lint-results-debug.xml"
        property "sonar.sourceEncoding", "UTF-8"
        property "sonar.coverage.exclusions", "**/*Activity.class,**/*Fragment.class,**/*Activity.java,**/*Fragment.java,**/*BR.class,**/*Binding.class,android/databinding,**/DataBinderMapperImpl.class,**/DataBinderMapperImpl*.class,**/DataBindingInfo.class,**/*Binding.class,**/*BindingImpl.class,**/UserDao_Impl*"
        property "sonar.log.level", "INFO"
        property "sonar.branch", System.getenv("SONAR_BRANCH")
      }
      androidVariant variant
    }
    if (project.unitTestCoverageEnabled) {
      project.sonarqube.properties {
        property "sonar.junit.reportsPath", "artifacts/test-results"
        property "sonar.jacoco.reportPath", "artifacts/unit-coverage/testDebugUnitTest.exec"
        property "sonar.tests", "src/test/java"
      }
    }
    if (project.integrationTestCoverageEnabled) {
      project.sonarqube.properties {
        property "sonar.jacoco.itReportPath", "artifacts/integration-coverage/merged-coverage.ec"
        property "sonar.tests", "src/androidTest/java"
      }
    }
    if (project.integrationTestCoverageEnabled && project.unitTestCoverageEnabled) {
      project.sonarqube.properties {
        property "sonar.tests", "src/test/java, src/androidTest/java"
      }
    }
    project.task("sonarLocalAnalysis", dependsOn: "lint${variant.capitalize()}") {
      doLast {
        project.sonarqube.properties {
          property "sonar.analysis.mode", "issues"
          property "sonar.issuesReport.html.enable", "true"
          property "sonar.scm.enabled", "false"
          property "sonar.scm-stats.enabled", "false"
          property "issueassignplugin.enabeld", "false"
          property "sonar.report.export.path", "sonar-report.json"
        }
      }
      project.sonarLocalAnalysis.finalizedBy "sonarqube"
    }
  }
}
