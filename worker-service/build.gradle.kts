plugins {
    id("java-library")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}