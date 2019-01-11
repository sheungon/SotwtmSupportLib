# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Volumes/Programs/Mac/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

-keep class com.sotwtm.support.SotwtmSupportLib { *; }


##############################################
## https://github.com/sheungon/AndroidEasyLog
##############################################
-assumenosideeffects class com.sotwtm.util.Log {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
    public static *** wtf(...);
}


##############################
# Android Databinding
# http://stackoverflow.com/questions/35472130/conflict-between-android-data-binding-and-guava-causes-proguard-error
##############################
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }

# Android class causing build issue
# See, https://stackoverflow.com/questions/44075089/noclassdeffounderror-landroid-arch-lifecycle-lifecycledispatcher
-keep class android.arch.** { *; }

