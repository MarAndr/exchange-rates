plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(":core:loading"))
    implementation(libs.kotlin.coroutine.core)
}