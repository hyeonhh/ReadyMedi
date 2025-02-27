import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    alias(libs.plugins.kotlin.kapt)
    kotlin("plugin.serialization") version "2.1.0"
    id("com.google.protobuf") version "0.9.4"
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.teammeditalk.medicalconnect"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.teammeditalk.medicalconnect"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "KakaoApiKey", getApiKey())
        buildConfigField("String", "MedicalApiKey", getMedicalApiKey())
        buildConfigField("String", "FirebaseClientId", getFirebaseClientId())
        buildConfigField("String", "KakaoNativeAppKey", getKakaoNativeAppKey())
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

kapt {
    correctErrorTypes = true
}
dependencies {
    implementation("com.github.skydoves:balloon:1.4.6")

    implementation("com.google.android.material:material:1.12.0")
    implementation("com.github.prolificinteractive:material-calendarview:2.0.1")

    // hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    // proto
    implementation(libs.protobuf.kotlin)
    implementation(libs.androidx.datastore.core)
    implementation("androidx.datastore:datastore:1.0.0")

    implementation("androidx.credentials:credentials:1.5.0-alpha05")
    implementation("androidx.credentials:credentials-play-services-auth:1.5.0-alpha05")

    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")

    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.3.0")

    // xlsx 데이터 다루기
    implementation("org.apache.poi:poi:5.2.3")
    implementation("org.apache.poi:poi-ooxml:5.2.3")

    // navigation
    val nav_version = "2.8.7"
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    // serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

    // viewmodelScope
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // tikxml
    implementation("com.tickaroo.tikxml:annotation:0.8.13")
    implementation("com.tickaroo.tikxml:core:0.8.13")
    implementation("com.tickaroo.tikxml:retrofit-converter:0.8.13")
    kapt("com.tickaroo.tikxml:processor:0.8.13")
    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // KakaoMap
    implementation("com.kakao.maps.open:android:2.12.8")
    implementation("com.google.android.gms:play-services-location:21.1.0")
    // CameraX
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation("androidx.camera:camera-view:1.4.1")
    implementation("androidx.camera:camera-extensions:1.4.1")
    implementation("androidx.camera:camera-effects:1.4.1")

    // text recognition
    implementation("com.google.android.gms:play-services-mlkit-text-recognition-korean:16.0.1")
    implementation("com.google.android.gms:play-services-mlkit-text-recognition:19.0.0")
    implementation("com.google.mlkit:language-id:16.0.0")

    // translation
    implementation("com.google.mlkit:translate:17.0.3")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-analytics")

    // Timber
    implementation(libs.timber)
    implementation(libs.timber.v471)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.common.ktx)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// API 키를 가져오는 함수
fun getApiKey(): String {
    val properties = Properties()
    val localProperties = File(rootProject.projectDir, "local.properties")

    if (localProperties.exists()) {
        properties.load(localProperties.inputStream())
        return "\"${properties.getProperty("kakao.api.key")}\""
    } else {
        throw GradleException("local.properties not found!")
    }
}

// API 키를 가져오는 함수
fun getMedicalApiKey(): String {
    val properties = Properties()
    val localProperties = File(rootProject.projectDir, "local.properties")

    if (localProperties.exists()) {
        properties.load(localProperties.inputStream())
        return "\"${properties.getProperty("medical.api.key")}\""
    } else {
        throw GradleException("local.properties not found!")
    }
}

// Firebase google login client id
fun getFirebaseClientId(): String {
    val properties = Properties()
    val localProperties = File(rootProject.projectDir, "local.properties")

    if (localProperties.exists()) {
        properties.load(localProperties.inputStream())
        return "\"${properties.getProperty("firebase.client.id")}\""
    } else {
        throw GradleException("local.properties not found!")
    }
}

// Firebase google login client id
fun getKakaoNativeAppKey(): String {
    val properties = Properties()
    val localProperties = File(rootProject.projectDir, "local.properties")

    if (localProperties.exists()) {
        properties.load(localProperties.inputStream())
        return "\"${properties.getProperty("kakao.native.app.key")}\""
    } else {
        throw GradleException("local.properties not found!")
    }
}

protobuf {
    val protobufVersion =
        libs.versions.protobuf
            .asProvider()
            .get()

    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }

    generateProtoTasks {
        all().forEach {
            it.plugins {
                register("kotlin") { option("lite") }
                register("java") { option("lite") }
            }
        }
    }
}
