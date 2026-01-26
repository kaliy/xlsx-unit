plugins {
    id("base")
    alias(libs.plugins.lombok) apply false
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
            val toolchainVersion = toolchainOverride ?: rootProject.libs.versions.java.toolchain.get()
            languageVersion = JavaLanguageVersion.of(toolchainVersion)
        }
    }

    tasks.withType<JavaCompile> {
        options.release.set(rootProject.libs.versions.java.release.get().toInt())
    }

    dependencies {
        add("api", rootProject.libs.jspecify)
        add("testImplementation", rootProject.libs.assertj.core)
        add("testImplementation", rootProject.libs.junit.jupiter)
        add("testRuntimeOnly", rootProject.libs.junit.platform.launcher)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    extensions.configure<CheckstyleExtension> {
        toolVersion = rootProject.libs.versions.checkstyle.get()
        configFile = rootProject.file("config/checkstyle/google_checks_4spaces.xml")
        maxWarnings = 0
        maxErrors = 0
    }

    tasks.withType<Checkstyle> {
        reports {
            sarif.required = true
            xml.required = true
            html.required = true
        }
    }

    extensions.configure<PmdExtension> {
        toolVersion = rootProject.libs.versions.pmd.get()
        ruleSetFiles = files(rootProject.file("config/pmd/ruleset.xml"))
        isIgnoreFailures = false
        isConsoleOutput = true
    }

    tasks.withType<Pmd> {
        reports {
            html.required = true
        }
    }
}
