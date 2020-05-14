package com.mydesignerclothing.mobile.android;


import android.os.Bundle;

import com.mydesignerclothing.mobile.android.uikit.BaseActivity;

public class NavigationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
