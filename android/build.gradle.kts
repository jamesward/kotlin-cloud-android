plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":common"))
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("io.ktor:ktor-client-android:1.3.2")
    implementation("io.ktor:ktor-client-serialization-jvm:1.3.2")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.0"

    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        val serverUrl: String? by project
        if (serverUrl != null) {
            val usesCleartextTraffic = if (serverUrl!!.startsWith("https")) "false" else "true"
            manifestPlaceholders = mapOf("usesCleartextTraffic" to usesCleartextTraffic)
            resValue("string", "server_url", serverUrl!!)
        } else {
            manifestPlaceholders = mapOf("usesCleartextTraffic" to "true")
            resValue("string", "server_url", "http://10.0.2.2:8080/")
        }
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        pickFirst("META-INF/kotlinx-serialization-runtime.kotlin_module")
    }
}
