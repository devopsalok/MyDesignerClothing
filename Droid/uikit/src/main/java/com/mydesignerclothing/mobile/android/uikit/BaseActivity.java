package com.mydesignerclothing.mobile.android.uikit;


import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mydesignerclothing.mobile.android.commons.core.ThreadQueueEngine;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.network.NetworkStateIdentifier;
import com.mydesignerclothing.mobile.android.commons.tracking.CrashTracker;
import com.mydesignerclothing.mobile.android.commons.tracking.performance.PerformanceLogger;
import com.mydesignerclothing.mobile.android.uikit.font.MyDscFont;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.appcompat.app.AppCompatActivity;

import static com.mydesignerclothing.mobile.android.commons.network.AppNetworkStateManager.NETWORK_STATE_CHANGE_ACTION;
import static com.mydesignerclothing.mobile.android.commons.util.MyDesignerClothingUtilConstants.HIDE_ACTION_BAR;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String IS_PAGE_NAME_EMPTY = "";
    private static AtomicInteger activeState = new AtomicInteger(0);
    protected final String TAG = getClass().getSimpleName();
    private String logicalPageName;
    private BroadcastReceiver networkStatusChangeReceiver;
    private ArrayList<Runnable> tasksToExecuteInResumedState;
    private boolean isPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applyTheme();
        super.onCreate(savedInstanceState);
//    LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(NOTIFICATION_BANNER_VISIBILITY));

        if (savedInstanceState == null) {
            DMLog.log(TAG, "onCreate called, savedInstanceState is null", Log.ASSERT);
            ThreadQueueEngine.getInstance().setToCompleteState();
        } else {
            DMLog.log(TAG, "onCreate called, savedInstanceState is NOT null", Log.ASSERT);
        }
        setUpActionBar();
    }

    protected void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanUpMemberLevelObjectsWithContext();
    }

    protected void cleanUpMemberLevelObjectsWithContext() {

    }

    protected void setUpActionBar() {
        if (getIntent() != null && getIntent().getBooleanExtra(HIDE_ACTION_BAR, false)) {
            hideActionBar();
        } else if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(MyDscFont.applyFontToSpan(this, getTitle(), R.font.default_font));
        }
    }

    @Override
    public void onBackPressed() {
        CrashTracker.trackAction(CrashTracker.ACTION_ON_BACK_PRESSED, TAG);
        super.onBackPressed();
    }

    protected Button findButtonById(int id) {
        return findViewById(id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;
    /*OfflineNotificationHandler offlineNotificationHandler = new OfflineNotificationHandler(this);
    if (!offlineNotificationHandler.isOfflineNotificationDisabled()) {
      registerNetworkStatusChangeReceiver();
    }
    if (!(this instanceof DisableOfflineNotification)) {
      AppNetworkStateManager.getInstance().refresh(this);
      presenter.updateOfflineModeNotificationsVisibility();
    }
    if (userClosedNotificationBanner) {
      hideServiceNotification(null);
    }*/
        //executeTasksToRunInResumedState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
    /*OfflineNotificationHandler offlineNotificationHandler = new OfflineNotificationHandler(this);
    if (!offlineNotificationHandler.isOfflineNotificationDisabled()) {
      LocalBroadcastManager.getInstance(this).unregisterReceiver(networkStatusChangeReceiver);
    }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
   /* Context applicationContext = getApplicationContext();
    if (applicationContext instanceof IMenuConfigProviderFactory) {
      MenuConfig menuConfiguration =
        ((IMenuConfigProviderFactory) applicationContext)
          .getMenuConfigProvider()
          .getMenuConfiguration();
      return new MenuItemsInflator().inflateMenuItems(menuConfiguration, getMenuInflater(), menu);
    }*/
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected TextView findTextViewById(int viewId) {
        return findViewById(viewId);
    }

    protected boolean isConnectedToInternet() {
        return NetworkStateIdentifier
                .isConnectedToInternet((ConnectivityManager)
                        getApplicationContext().getSystemService(CONNECTIVITY_SERVICE));
    }

    protected void refresh() {
    }

    @Override
    protected void onStart() {
        activeState.getAndIncrement();
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        decrementActiveCounter();
        if (isAppInBackground()) {
            PerformanceLogger.forceTransactionEnd();
        }
    }

    private void decrementActiveCounter() {
        if (activeState.intValue() > 0) {
            activeState.getAndDecrement();
        }
    }

    public boolean isAppInBackground() {
        return activeState.intValue() < 1;
    }


    @SuppressWarnings("EmptyMethod")
    protected void applyTheme() {
        //NOSONAR
    }

    private void registerNetworkStatusChangeReceiver() {
        IntentFilter intentFilter = new IntentFilter(NETWORK_STATE_CHANGE_ACTION);
   /* networkStatusChangeReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        presenter.updateOfflineModeNotificationsVisibility();
      }
    };
    LocalBroadcastManager.getInstance(this).registerReceiver(networkStatusChangeReceiver, intentFilter);*/
    }

    private View getRootView() {
        return getWindow().getDecorView().getRootView();
    }


    private boolean isContentViewAdded() {
        return ((ViewGroup) getRootView().findViewById(android.R.id.content)).getChildAt(0) != null;
    }

    private void executeWhenInResumedState(Runnable task) {
        tasksToExecuteInResumedState.add(task);
    }

    private void executeTasksToRunInResumedState() {
        for (Runnable task : tasksToExecuteInResumedState) {
            task.run();
        }
        tasksToExecuteInResumedState.clear();
    }
}
