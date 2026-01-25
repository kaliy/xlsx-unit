plugins {
    id("base")
    id("io.freefair.lombok") version "8.13" apply false
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
    apply(plugin = "checkstyle")
    apply(plugin = "pmd")
    apply(plugin = "io.freefair.lombok")

    extensions.configure<JavaPluginExtension> {
        toolchain {
            val toolchainOverride = providers.gradleProperty("toolchainVersion").orNull
            val toolchainVersion = toolchainOverride?.toIntOrNull() ?: 25
            languageVersion = JavaLanguageVersion.of(toolchainVersion)
        }
    }

    tasks.withType<JavaCompile> {
        options.release.set(17)
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

    extensions.configure<CheckstyleExtension> {
        toolVersion = "13.0.0"
        configFile = rootProject.file("config/checkstyle/google_checks_4spaces.xml")
        isShowViolations = true
    }

    tasks.withType<Checkstyle> {
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
    }

    extensions.configure<PmdExtension> {
        toolVersion = "7.12.0"
        ruleSetFiles = files(rootProject.file("config/pmd/ruleset.xml"))
        isIgnoreFailures = false
    }

    tasks.withType<Pmd> {
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
    }
}
