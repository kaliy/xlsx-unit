plugins {
    id("base")
}

allprojects {
    group = "org.kaliy"
    version = "0.1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java-library")

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }

    dependencies {
        add("api", "org.jspecify:jspecify:1.0.0")
        add("testImplementation", "org.assertj:assertj-core:3.27.6")
        add("testImplementation", platform("org.junit:junit-bom:6.0.2"))
        add("testImplementation", "org.junit.jupiter:junit-jupiter")
        add("testRuntimeOnly", "org.junit.platform:junit-platform-launcher")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
