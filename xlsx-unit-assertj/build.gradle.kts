plugins {
    id("xlsxunit.java-library")
}

dependencies {
    api(project(":xlsx-unit-core"))
    api(libs.assertj.core)
}
