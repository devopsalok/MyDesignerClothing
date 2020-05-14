package com.mydesignerclothing.mobile.android.commons.api;

/**
 * A marker interface that marks a class to not be obfuscated by Proguard.
 * This is needed for Jackson serialization/de-serialization to work correctly.
 */
public interface ProguardJsonMappable {
}
