import org.slf4j.LoggerFactory

class AdbCommandsForDevice {
  // of the format ${adbPath} -s ${deviceSerialNumber} shell settings put [scale command] 0
  private static final def scaleCommands = [
    '%s -s %s shell settings put global window_animation_scale 0',
    '%s -s %s shell settings put global transition_animation_scale 0',
    '%s -s %s shell settings put global animator_duration_scale 0',
  ].asImmutable()
  // of the format ${adbPath} -s ${deviceSerialNumber} shell pm revoke ${applicationId} [permission]
  private static final def revokePermissionCommands = [
    '%s -s %s shell pm revoke %s android.permission.CAMERA',
    '%s -s %s shell pm revoke %s android.permission.WRITE_CONTACTS'
  ].asImmutable()
  // of the format '${adbPath} shell pm grant ${applicationId} [permisson]
  private static final def grantPermissionCommands = [
    '%s shell pm grant %s android.permission.WRITE_EXTERNAL_STORAGE'
  ].asImmutable()

  // of the format ${adbPath} -s ${deviceSerialNumber} shell pm uninstall ${applicationId}
  private static final def uninstallCommands = [
    '%s -s %s shell pm uninstall %s'
  ].asImmutable()

  private static final def MIN_API_LEVEL_TO_GRANT_PERMISSIONS = 26

  static def executeAdbCommandsForInstrumentationTests(String adbPath, String deviceSerialNumber, String applicationId, int apiLevel)  {
//    runRevokePermissions(adbPath, deviceSerialNumber, applicationId)
//    if (apiLevel >= MIN_API_LEVEL_TO_GRANT_PERMISSIONS) {
//      runGrantPermissions(adbPath, deviceSerialNumber, applicationId)
//    }

    runUninstallCommands(adbPath, deviceSerialNumber, applicationId)
    runScaleCommands(adbPath, deviceSerialNumber)
  }

  private static def runRevokePermissions(String adbPath, deviceSerialNumber, String applicationId) {
    for (String command : revokePermissionCommands) {
      String commandToRun = String.format(Locale.US, command, adbPath, deviceSerialNumber, applicationId)
      runCommand(commandToRun)
      String testApplicationId = String.format(Locale.US, "%s.test", applicationId)
      commandToRun = String.format(Locale.US, command, adbPath, deviceSerialNumber, testApplicationId)
      runCommand(commandToRun)
    }
  }

  private static def runUninstallCommands(String adbPath, String deviceSerialNumber, String applicationId) {
    for (String command : uninstallCommands) {
      String commandToRun = String.format(Locale.US, command, adbPath, deviceSerialNumber, applicationId)
      runCommand(commandToRun)
      String testApplicationId = String.format(Locale.US, "%s.test", applicationId)
      commandToRun = String.format(Locale.US, command, adbPath, deviceSerialNumber, testApplicationId)
      runCommand(commandToRun)
    }
  }

  private static def runGrantPermissions(String adbPath, deviceSerialNumber, String applicationId) {
    for (String command : grantPermissionCommands) {
      String commandToRun = String.format(Locale.US, command, adbPath, deviceSerialNumber, applicationId)
      runCommand(commandToRun)
      String testApplicationId = String.format(Locale.US, "%s.test", applicationId)
      commandToRun = String.format(Locale.US, command, adbPath, deviceSerialNumber, testApplicationId)
      runCommand(commandToRun)
    }
  }

  private static def runScaleCommands(String adbPath, String deviceSerialNumber) {
    for (String command : scaleCommands) {
      String commandToRun = String.format(Locale.US, command, adbPath, deviceSerialNumber)
      runCommand(commandToRun)
    }
  }

  private static def runCommand(String command) {
    logMessage("running the following command: ${command}")
    def systemOut = new StringBuilder()
    def sysErr = new StringBuilder()
    def process = command.execute()
    process.consumeProcessOutput(systemOut, sysErr)
    process.waitForOrKill(4000)
    logMessage("command result: out> ${systemOut.toString()} err> ${sysErr.toString()}")
  }

  private static def logMessage(String message) {
    def logger = LoggerFactory.getLogger("AdbCommandsForDevice")
    logger.warn("********** AdbCommandsForDevice Message *********")
    logger.warn("${message}")
    logger.warn("                ******************               ")
  }
}
