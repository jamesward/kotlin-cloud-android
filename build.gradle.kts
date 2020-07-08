plugins {
    id("com.android.application") version "3.6.3" apply false
    kotlin("jvm") version "1.3.72"
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        google()
    }
}

tasks.replace("assemble").dependsOn(":server:installDist")
