import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.0"
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.hibernate:hibernate-core:5.6.9.Final")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("com.github.gwenn:sqlite-dialect:0.1.2")

    implementation(compose.desktop.currentOs)


    implementation("javax.inject:javax.inject:1")
    implementation("com.google.inject:guice:4.2.3")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}
