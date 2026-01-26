base {
    archivesName.set("xlsx-unit-assertj")
}

dependencies {
    api(project(":core"))
    api(libs.assertj.core)
}
