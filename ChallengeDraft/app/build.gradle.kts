plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    alias(libs.plugins.google.services)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.challengedraft"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.challengedraft"
        minSdk = 25
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("androidx.fragment:fragment-ktx:1.6.0")

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.recyclerview)

    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation("androidx.compose.material:material:1.6.5")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    implementation ("androidx.room:room-runtime:2.4.1")
    implementation("androidx.room:room-ktx:2.4.1")

    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")


    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)

}