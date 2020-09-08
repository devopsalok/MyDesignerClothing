# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn
-ignorewarnings

-dontwarn android.support.v4.**
-dontwarn android.media.**
-dontwarn org.joda.time.**
-dontwarn org.junit.runners.model.InitializationError
-dontwarn org.apache.commons.codec.binary.Base64
-dontwarn javax.xml.stream.**
-dontwarn com.actionbarsherlock.internal.*
-dontwarn com.xtremelabs.robolectric.**


-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class * implements java.io.Serializable { *; }
-keep class * implements android.os.Parcelable { *; }
-keep class * implements com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable { *; }

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*,InnerClasses

# Gson specific classes
-keep class sun.misc.Unsafe { *; }

# Prevent obfuscation of @Expose annotation
-keepnames @interface com.google.gson.annotations.Expose { *; }

# Application classes that will be serialized/deserialized over Gson
-keepclasseswithmembers class * {
   @com.google.gson.annotations.Expose <fields>;
}
-keep class * implements com.google.gson.TypeAdapterFactory { *; }
-keepnames class * implements com.google.gson.TypeAdapterFactory { *; }
-keep class * extends com.google.gson.TypeAdapter { *; }
-keepnames class * extends com.google.gson.TypeAdapter { *; }
##---------------End: proguard configuration for Gson  ----------

-keep interface org.apache.commons.lang3 { *; }

# App specific - some of these may not apply
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-keep class com.actionbarsherlock.** { *; }
-keep interface com.actionbarsherlock.** { *; }
-keep interface com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable { *; }

# ForeSee
-keep class com.foresee.** { *;}
-keep class fs.** { *; }
-keepattributes *Annotation*
-keepattributes Signature

# GSON requirements
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }

-keep class com.google.inject.** { *; }
-keep class javax.annotation.** { *; }
-keep public class * extends android.view.View {
 public <init>(android.content.Context);
 public <init>(android.content.Context, android.util.AttributeSet);
 public <init>(android.content.Context, android.util.AttributeSet, int);
 public void set*(...);
}
-keepclassmembers class * {@com.google.inject.Inject <init>(...);}
-keepclassmembers class * {
 void *(**On*Event);
}

# Misc.
-keepclassmembernames class * {
 java.lang.Class class$(java.lang.String);
 java.lang.Class class$(java.lang.String, boolean);
}

-keepclassmembers enum * {
 public static **[] values();
 public static ** valueOf(java.lang.String);
}

-keep public class * extends android.** { *; }
-keep public class com.android.vending.licensing.ILicensingService

-keep public class org.simpleframework.**{ *; }
-keep class org.simpleframework.xml.**{ *; }
-keep class org.simpleframework.xml.core.**{ *; }
-keep class org.simpleframework.xml.util.**{ *; }
-keep class androidx.renderscript.** { *; }
