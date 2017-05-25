# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepattributes Signature
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-keepattributes Annotation
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
# jdk8
-dontwarn java.lang.invoke.*
-dontwarn org.slf4j.impl.*
# design
-keep class android.support.design.widget.** { *; }
-keep interface android.support.design.widget.** { *; }
-dontwarn android.support.design.**
#app
-keep class net.kk.xml.**{*;}
-keep class com.kk.imageeditor.bean.**{
*;
}
-keepclassmembers class * extends net.kk.xml.IXmlElement{
    *;
}

-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }