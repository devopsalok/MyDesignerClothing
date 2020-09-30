package com.mydesignerclothing.mobile.android;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;


import com.mydesignerclothing.mobile.android.login.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class SplashScreenActivity extends AppCompatActivity {
    public static final long SPLASH_ANIMATION_TIME_IN_MILLIS = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();

        if (!isTaskRoot()) {
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        setContentView(R.layout.activity_splash);
setPermissions();
       new Handler().postDelayed(this::navigateToLoginPage, SPLASH_ANIMATION_TIME_IN_MILLIS);
    }

    private void setPermissions() {
        String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 23);
        }
    }

    private void navigateToLoginPage() {


            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
            finish();


    }



    private boolean hasPermissions(Context context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
