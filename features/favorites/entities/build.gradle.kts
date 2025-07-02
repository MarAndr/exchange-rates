plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(":core:loading"))

    implementation(project(":features:rates:entities"))

    implementation(libs.kotlin.coroutine.core)
}