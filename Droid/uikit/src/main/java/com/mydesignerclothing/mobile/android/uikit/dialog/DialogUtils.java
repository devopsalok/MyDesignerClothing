package com.mydesignerclothing.mobile.android.uikit.dialog;

import android.app.Dialog;
import android.os.Handler;
import android.util.Log;

import com.mydesignerclothing.mobile.android.commons.logger.DMLog;

public class DialogUtils {

    private static final String TAG = DialogUtils.class.getSimpleName();
    private static final Handler HANDLER = new Handler();

    @SuppressWarnings("EmptyMethod")
    private DialogUtils() {
        //NOSONAR
    }

    public static <T extends Dialog> void safeDismissDialog(final T dialog) {
        HANDLER.post(() -> {
            if (dialog != null && dialog.isShowing() &&
                    (dialog.getOwnerActivity() == null || !dialog.getOwnerActivity().isFinishing())) {
                try {
                    dialog.dismiss();
                } catch (IllegalArgumentException e) {
                    DMLog.log(TAG, e, Log.WARN);
                }
            }
        });
    }
}
