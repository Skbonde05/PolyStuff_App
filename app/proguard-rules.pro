# Keep Firebase model annotations and members
-keepclassmembers class * {
    @com.google.firebase.database.IgnoreExtraProperties <fields>;
    @com.google.firebase.database.PropertyName <methods>;
}

# Keep PDF Viewer library classes
-keep class com.github.barteksc.pdfviewer.** { *; }

# Keep PdfViewer subclasses referenced via reflection
-keep class myapp.org.userapp.*_pdf { *; }

# Keep class names for reflection-based lookup (SearchManager)
-keepnames class myapp.org.userapp.**

# Keep Glide
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep class com.bumptech.glide.GeneratedAppGlideModuleImpl { *; }

# Keep line numbers for stack traces
-keepattributes SourceFile,LineNumberTable

# Keep Parcelable and Serializable classes
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep Supabase client classes
-keep class io.github.jan.supabase.** { *; }
-keepattributes *Annotation*
