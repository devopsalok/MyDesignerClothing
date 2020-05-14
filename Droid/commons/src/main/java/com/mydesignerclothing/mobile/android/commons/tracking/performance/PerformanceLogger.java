package com.mydesignerclothing.mobile.android.commons.tracking.performance;

import android.content.Context;

import com.dynatrace.android.agent.DTXAction;
import com.dynatrace.android.agent.Dynatrace;
import com.dynatrace.android.agent.conf.Configuration;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.util.MetaData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import static com.mydesignerclothing.mobile.android.commons.tracking.performance.PerformanceMetricConstants.DYNATRACE_AGENT_ENVIRONMENT;
import static com.mydesignerclothing.mobile.android.commons.tracking.performance.PerformanceMetricConstants.DYNATRACE_APPLICATION_ID;
import static com.mydesignerclothing.mobile.android.commons.tracking.performance.PerformanceMetricConstants.DYNATRACE_CLUSTER_URL;

/**
 * PerformanceLogger is used to collect instrumentation metrics and send to web metrics server.
 */

public class PerformanceLogger {

    //Log constants
    private static final String TAG = PerformanceLogger.class.getSimpleName();
    private static final String STARTING_ACTION = "Starting Transaction %s";
    private static final String ENDING_ACTION = "Ending Transaction";
    private static final String FLUSH = "Flushing all tracked actions";
    private static final String CONFIGURATION_CREATED = "Dynatrace configuration created,";
    private static final String LOGGER_FLAGS_STATUS = "Extra Dynatrace Configurations, crashes on: %s, debugging on %s";
    private static final String CONFIGURATION_NOT_CREATED = "Configuration not created, check the meta data for one of the following:";
    private static final String CONFIGURATION_PARAMETERS = "applicationId: %s, environmentId %s, clusterUrl %s";
    private static final String PERFORMANCE_MONITOR_STARTED = "Performance Monitor started";
    private static final String PERFORMANCE_MONITOR_NOT_STARTED = "Performance Monitor not started due to failed configuration load. Check logs for the issue";

    private static boolean initialized = false;

    private DTXAction dtxAction;

    private static class InstanceHolder {
        static PerformanceLogger instance = new PerformanceLogger();
    }

    /**
     * Private Constructor
     */
    @SuppressWarnings("EmptyMethod")
    private PerformanceLogger() {
        //NOSONAR
    }

    /**
     * This method is used to get instance of PerformanceLogger.
     *
     * @return instance
     */
    public static PerformanceLogger getInstance() {
        return InstanceHolder.instance;
    }
    /////Core Methods/////

    /**
     * @param transIdentifier action label to be tracked defined in
     *                        {@link com.mydesignerclothing.mobile.android.commons.tracking.performance.PerformanceMetricConstants.ActionsTracked }
     */
    public static boolean startTransaction(@PerformanceMetricConstants.ActionsTracked String transIdentifier) {
        if (initialized) {
            PerformanceLogger.getInstance().transactionStart(transIdentifier);
        }
        return initialized;
    }

    /**
     * This method must be called after {@link PerformanceLogger#startTransaction(String)}
     */
    public static boolean endTransaction() {
        if (initialized) {
            PerformanceLogger.getInstance().transactionEnd();
        }
        return initialized;
    }

    public static boolean forceTransactionEnd() {
        if (initialized) {
            PerformanceLogger.getInstance().cleanTransactions();
        }
        return initialized;
    }

    /**
     * Creates configuration based on the meta-data.xml file for the build variant.
     *
     * @param context
     */
    public void initialize(Context context) {
        if (!initialized) {
            PerformanceLogger.getInstance().setUp(context);
        }
    }

    private void setUp(Context context) {
        Configuration configuration = getConfiguration(context);
        if (configuration != null) {
            Dynatrace.startup(context.getApplicationContext(), configuration);
            initialized = true;
            DMLog.d(TAG, PERFORMANCE_MONITOR_STARTED);
        } else {
            DMLog.e(TAG, PERFORMANCE_MONITOR_NOT_STARTED);
        }
    }

    private void transactionStart(@PerformanceMetricConstants.ActionsTracked String transIdentifier) {
        DMLog.d(TAG, String.format(STARTING_ACTION, transIdentifier));
        dtxAction = Dynatrace.enterAction(transIdentifier);
    }

    private void transactionEnd() {
        if (dtxAction != null) {
            dtxAction.leaveAction();
            DMLog.d(TAG, ENDING_ACTION);
        }
    }

    private void cleanTransactions() {
        Dynatrace.flushEvents();
        DMLog.d(TAG, FLUSH);
    }

  /*private boolean isValidMetaData(@NonNull Context context, @Nullable String applicationId, @Nullable String environmentId, @Nullable String providedClusterUrl) {
    String ignoreValue = context.getString(R.string.dynatrace_ignore_value);
    return !ignoreValue.equalsIgnoreCase(applicationId) &&
      !ignoreValue.equalsIgnoreCase(environmentId) &&
      !ignoreValue.equalsIgnoreCase(providedClusterUrl);

  }*/

    @Nullable
    @VisibleForTesting
    public Configuration getConfiguration(@NonNull Context context) {
        String applicationId = getValueFromMetadata(DYNATRACE_APPLICATION_ID, context);
        String environmentId = getValueFromMetadata(DYNATRACE_AGENT_ENVIRONMENT, context);
        String providedClusterUrl = getValueFromMetadata(DYNATRACE_CLUSTER_URL, context);

    /*if (isValidMetaData(context, applicationId, environmentId, providedClusterUrl)) {
      boolean crashesEnabled = Boolean.parseBoolean(context.getString(R.string.dynatrace_crash_reporting_enabled));
      boolean loggingEnabled = Boolean.parseBoolean(context.getString(R.string.dynatrace_logging_enabled));
      boolean isManagedCluster = Boolean.parseBoolean(context.getString(R.string.dynatrace_managed_cluster));

      Map<String, String> properties = new HashMap<>();
      properties.put("DTXInstrumentLifecycleMonitoring", Boolean.FALSE.toString());

      Configuration configuration = new DynatraceConfigurationBuilder(applicationId, environmentId, providedClusterUrl, isManagedCluster)
        .withCrashReporting(crashesEnabled)
        .withDebugLogging(loggingEnabled)
        .loadProperties(context, properties)
        .buildConfiguration();

      DMLog.d(TAG, String.format(Locale.ROOT, LOGGER_FLAGS_STATUS, Boolean.toString(crashesEnabled), Boolean.toString(loggingEnabled)));
      DMLog.d(TAG, CONFIGURATION_CREATED);
      DMLog.d(TAG, String.format(Locale.ROOT, CONFIGURATION_PARAMETERS, applicationId, environmentId, providedClusterUrl));

      return configuration;
    } else {
      DMLog.e(TAG, CONFIGURATION_NOT_CREATED);
      DMLog.e(TAG, String.format(Locale.ROOT, CONFIGURATION_PARAMETERS, applicationId, environmentId, providedClusterUrl));
    }*/
        return null;
    }

    private String getValueFromMetadata(String metaData, Context context) {
        return new MetaData.Builder().withMetaDataKey(metaData).create(context).getKeyValue();
    }

    @VisibleForTesting
    @SuppressWarnings("unused")
    public static void reset() {
        InstanceHolder.instance = new PerformanceLogger();
        initialized = false;
    }

}
