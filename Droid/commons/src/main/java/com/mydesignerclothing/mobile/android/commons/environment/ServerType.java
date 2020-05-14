package com.mydesignerclothing.mobile.android.commons.environment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

import static com.mydesignerclothing.mobile.android.commons.environment.EnvironmentConfig.V1;
import static com.mydesignerclothing.mobile.android.commons.environment.EnvironmentConfig.V2;
import static com.mydesignerclothing.mobile.android.commons.environment.EnvironmentConfig.V3;

@Retention(RetentionPolicy.SOURCE)
@StringDef({V3, V1, V2})
public @interface ServerType {
}
