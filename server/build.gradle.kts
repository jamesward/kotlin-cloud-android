plugins {
    application
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":common"))
    implementation("io.ktor:ktor-server-cio:1.3.2")
    implementation("io.ktor:ktor-serialization:1.3.2")
    implementation("io.ktor:ktor-client-cio:1.3.2")
    implementation("io.ktor:ktor-client-json-jvm:1.3.2")
    implementation("io.ktor:ktor-client-serialization-jvm:1.3.2")
    runtimeOnly("ch.qos.logback:logback-classic:1.2.3")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

application {
    mainClassName = "com.example.helloworld.ServerKt"
}
