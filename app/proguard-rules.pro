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
-useuniqueclassmembernames
-allowaccessmodification
-keep class org.apache.** { *; }
-keep class junit.** { *; }
-keep class !com.rampit.rask3.dailydiary.** { *; }
#-keep class com.testfairy.** { *; }
-keep class com.android.** { *; }
-keep class com.sun.** { *; }
-keep class org.bouncycastle.** { *; }
-keep class com.itextpdf.** { *; }
-keep class com.amitshekhar.** { *; }
-keep class net.gotev.** { *; }
-keep class com.github.** { *; }
-keep class com.google.** { *; }


-keepattributes Signature
-keepattributes *Annotation*
-keepattributes Exceptions, Signature, LineNumberTable


-dontwarn org.apache.**
-dontwarn android.support.**
-dontwarn junit.**
#-dontwarn com.testfairy.**
-dontwarn com.sun.**
-dontwarn org.bouncycastle.**
-dontwarn com.itextpdf.**
-dontwarn com.amitshekhar.**
-dontwarn net.gotev.**
-dontwarn com.github.**
-dontwarn com.google.**
