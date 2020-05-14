package com.mydesignerclothing.mobile.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.mydesignerclothing.mobile.android.login.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;

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

       new Handler().postDelayed(this::navigateToLoginPage, SPLASH_ANIMATION_TIME_IN_MILLIS);
    }

    private void navigateToLoginPage() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
