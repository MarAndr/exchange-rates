plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(":core:loading"))

    api(project(":features:favorites:entities"))

    implementation(libs.kotlin.coroutine.core)
}