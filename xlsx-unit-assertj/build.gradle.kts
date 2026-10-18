plugins {
    `java-library`
    checkstyle
    pmd
    alias(libs.plugins.errorprone)
    alias(libs.plugins.spotbugs)
}

dependencies {
    api(project(":xlsx-unit-core"))
    api(libs.assertj.core)
}
