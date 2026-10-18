plugins {
    `java-library`
    checkstyle
    pmd
    alias(libs.plugins.errorprone)
    alias(libs.plugins.spotbugs)
}

dependencies {
    api(libs.poi.ooxml)
}
