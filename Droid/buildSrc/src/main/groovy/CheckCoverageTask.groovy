import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction

@CacheableTask
class CheckCoverageTask extends DefaultTask {


  Project module
  String variant

  private def alignedThresHolds = [
    commons : coverageDefinition(48.97, 43.33, 48.22, 50.14, 57.2, 66.27),
    login: coverageDefinition(54.71, 60.94, 54.22, 51.33, 52.55, 56.18),
    network: coverageDefinition(56.26, 40.62, 58.94, 44.68, 60.23, 78.77),
    uikit: coverageDefinition(16.42, 22.06, 15.46, 22.66, 24.14, 29.91)
  ]

  private def unalignedThreshHolds = [
    debug : [
      app: coverageDefinition(34.15, 30.82, 33.98, 36.13, 42.55, 56.7),
    ],
  ]

  private def coverageDefinition(double instructionCoverage, double branchCoverage, double lineCoverage, double complexityCoverage, double methodCoverage, double classCoverage) {
    return [
        instruction: instructionCoverage,
        branch: branchCoverage,
        line: lineCoverage,
        complexity: complexityCoverage,
        method: methodCoverage,
        class: classCoverage
      ]
  }

  @TaskAction
  void checkCoverage() {
    group = "Coverage"
    description = "Check Code Coverage"
    def report = new File("${module.projectDir}/build/reports/jacoco/generate${variant}JacocoTestReports/generate${variant}JacocoTestReports.xml")

    if(!report.exists()){
      throw new RuntimeException("You have to set unitTestCoverageEnabled as false in the build.gradle of your module, if your " +
        "module has no unit tests.")
    }
    logger.lifecycle("Checking coverage results: ${report}")

    def parser = new XmlParser()
    parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    parser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)
    def results = parser.parse(report)

    def percentage = {
      def covered = it.'@covered' as Double
      def missed = it.'@missed' as Double
      ((covered / (covered + missed)) * 100).round(2)
    }

    def counters = results.counter
    def metrics = [:]
    metrics << [
      'instruction': percentage(counters.find { it.'@type'.equals('INSTRUCTION') }),
      'branch'     : percentage(counters.find { it.'@type'.equals('BRANCH') }),
      'line'       : percentage(counters.find { it.'@type'.equals('LINE') }),
      'complexity' : percentage(counters.find { it.'@type'.equals('COMPLEXITY') }),
      'method'     : percentage(counters.find { it.'@type'.equals('METHOD') }),
      'class'      : percentage(counters.find { it.'@type'.equals('CLASS') })
    ]

    def failures = []


    def moduleLimits = alignedThresHolds[module.name.toLowerCase(Locale.US)] ?: unalignedThreshHolds[variant.toLowerCase(Locale.US)][module.name.toLowerCase(Locale.US)]
    metrics.each {
      def limit = moduleLimits[it.key]
      def coverage_check = "- ${it.key} coverage rate = ${it.value}%; threshold = ${limit}%"
      logger.quiet(coverage_check)
      if (it.value < limit) {
        failures.add(coverage_check)
      } else {
        def amountOver = it.value - limit;
        logger.quiet("- currently we are over the threshold by ${amountOver}%\n")
      }

    }

    if (failures) {
      logger.quiet("------------------ Code Coverage Failed -----------------------")
      failures.each {
        logger.quiet(it)
      }
      logger.quiet("---------------------------------------------------------------")
      throw new GradleException("Code coverage failed")
    } else {
      logger.quiet("${module.name} - Passed Code Coverage Checks")
    }
  }
}
