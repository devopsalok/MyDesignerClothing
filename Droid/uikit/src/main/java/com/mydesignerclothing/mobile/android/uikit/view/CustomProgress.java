package com.mydesignerclothing.mobile.android.uikit.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.uikit.R;
import com.mydesignerclothing.mobile.android.uikit.dialog.DialogUtils;

import static com.mydesignerclothing.mobile.android.uikit.view.CustomProgress.State.DISMISSED;
import static com.mydesignerclothing.mobile.android.uikit.view.CustomProgress.State.NOT_SHOWN;

public class CustomProgress {

  private static final int TRACE_DEPTH = 2;
  private static final String TAG = CustomProgress.class.getSimpleName();
  private static final Object progressDialogLock = new Object();
  private static final StateHolder progressStateHolder = new StateHolder(NOT_SHOWN);
  private static Dialog progressDialog = null;

  @SuppressWarnings("EmptyMethod")
  public CustomProgress() {
    //NOSONAR
  }


  /**
   * Show a custom progress dialog
   *
   * @param context     - Context of current application, use "this"
   * @param text        - Text to show, when possible use getString and a string value from strings.xml
   * @param cancellable - True or false if the dialog can be canceled.
   */
  public static void showProgressDialog(Context context, String text, boolean cancellable) {
    synchronized (progressDialogLock) {
      removeProgressDialog();
      DMLog.withInfo("showProgressDialog():: " + text, TRACE_DEPTH);
      if (!isCallerFinishing(context)) {
        progressDialog = new Dialog(context, R.style.Theme_App_Translucent_NoTitleBar);

        setViewMessage(context, text);

        progressDialog.setOnKeyListener((dialog, keyCode, event) -> {
          switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
              return true;
            case KeyEvent.KEYCODE_SEARCH:
              return true;
            default:
              return false;
          }
        });

        progressDialog.setCancelable(cancellable);
        progressDialog.show();
        updateState(State.SHOWING);
      }
    }
  }

  public static void showProgressDialogInContinuation(Context context, String text, boolean cancellable) {
    if (isShowing()) {
      setViewMessage(context, text);
    } else {
      showProgressDialog(context, text, cancellable);
    }
  }

  private static boolean isCallerFinishing(Context context) {
    Activity caller = (Activity) context;
    return caller.isFinishing();
  }

  /**
   * Remove the progress dialog from the screen
   */
  public static void removeProgressDialog() {
    try {
      synchronized (progressDialogLock) {
        if (isShowing()) {
          DMLog.withInfo("dismissProgressDialog():: ", TRACE_DEPTH);
          DialogUtils.safeDismissDialog(progressDialog);
          progressDialog = null;
          updateState(State.DISMISSED);
        }
      }
    } catch (Exception e) {
      DMLog.log(TAG, e, Log.ERROR);
    }
  }

  /**
   * Set the message for the view.  This is called from displayProgressDialog.  In your activities you can utilize this interface so that messages can be updated rather than having the jerky behavior of removing and adding dialogs back
   *
   * @param context - Current context
   * @param text    - text to set in the view
   */
  public static void setViewMessage(Context context, String text) {
    synchronized (progressDialogLock) {
      if (progressDialog != null && !isCallerFinishing(context)) {
        LayoutInflater mInflater = LayoutInflater.from(context);

        View layout = mInflater.inflate(R.layout.dialogue, null);
        progressDialog.setContentView(layout);

        TextView loadingDetails = layout.findViewById(R.id.diatext);
        TextView loadingMessage = layout.findViewById(R.id.fixedtext);
        loadingMessage.setText(R.string.uikit_dialog_splash);
        if (text.isEmpty()) {
          loadingDetails.setVisibility(View.INVISIBLE);
        } else {
          loadingDetails.setText(text);
        }
      }
    }
  }

  public static void setFixedText(Context context, String text) {
    synchronized (progressDialogLock) {
      if (progressDialog != null && !isCallerFinishing(context)) {
        LayoutInflater mInflater = LayoutInflater.from(context);

        View layout = mInflater.inflate(R.layout.dialogue, null);
        progressDialog.setContentView(layout);

        TextView loadingMessage = layout.findViewById(R.id.fixedtext);
        loadingMessage.setText(text);
      }
    }
  }

  public static boolean isShowing() {
    synchronized (progressDialogLock) {
      DMLog.d(TAG, "progress dialog is null: " + (progressDialog == null));
      if (progressDialog != null) {
        DMLog.d(TAG, "progress dialog is showing: " + progressDialog.isShowing());
      }
      return progressDialog != null && progressDialog.isShowing();
    }
  }

  private static void updateState(State state) {
    synchronized (progressStateHolder) {
      progressStateHolder.updateState(state);
    }
  }

  private static class StateHolder {
    private State progressState;

    StateHolder(State state) {
      progressState = state;
    }

    void updateState(State state) {
      if (progressState == NOT_SHOWN && state == DISMISSED) {
        DMLog.d("CustomProgressState", "Can not set state to DISMISSED when dialog is not even shown!");
        return;
      }
      progressState = state;
      DMLog.d("CustomProgressState", progressState.toString());
    }
  }

  public enum State {
    SHOWING, DISMISSED, NOT_SHOWN
  }
}
