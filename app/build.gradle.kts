plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.esprit.wellnest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.esprit.wellnest"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packaging{
        resources {
            excludes += "META-INF/NOTICE.md"

            excludes += "META-INF/LICENSE.md"
        }
    }
}

dependencies {
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.zxing:core:3.4.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.github.ismaeldivita:chip-navigation-bar:1.4.0")

    implementation("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("androidx.room:room-runtime:2.2.0")
    annotationProcessor("androidx.room:room-compiler:2.2.0") // for Java
    implementation(libs.android.mail)
    implementation(libs.android.activation)

    // wiem
    implementation("com.github.ismaeldivita:chip-navigation-bar:1.4.0")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation(libs.sdp)
    implementation(libs.ssp)
    implementation(libs.roundedimageview)

    // mahdi
    implementation("com.google.android.material:material:1.6.0") // Check the latest version

    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    implementation("org.osmdroid:osmdroid-android:6.1.11") // Check the latest version

    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    implementation(libs.javamail)
    implementation(libs.activation)
    implementation(libs.bcrypt)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.volley)
    implementation(libs.room.common)
    implementation(libs.room.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    annotationProcessor(libs.room.compiler)
}