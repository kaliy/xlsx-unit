import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone

plugins {
    id("base")
    alias(libs.plugins.errorprone) apply false
    alias(libs.plugins.lombok) apply false
    alias(libs.plugins.spotbugs) apply false
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
    apply(plugin = "net.ltgt.errorprone")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "com.github.spotbugs")

    extensions.configure<JavaPluginExtension> {
        toolchain {
            val toolchainOverride = providers.gradleProperty("toolchainVersion").orNull
            val toolchainVersion = toolchainOverride ?: rootProject.libs.versions.java.toolchain.get()
            languageVersion = JavaLanguageVersion.of(toolchainVersion)
        }
    }

    tasks.withType<JavaCompile> {
        options.release.set(rootProject.libs.versions.java.release.get().toInt())
//        val taskName = name

        options.errorprone {
            errorproneArgs.add("-XepOpt:NullAway:OnlyNullMarked=true")
            errorproneArgs.add("-XepOpt:NullAway:JSpecifyMode=true")
            check("NullAway", CheckSeverity.ERROR)
            check("RequireExplicitNullMarking", CheckSeverity.ERROR)
//            if (taskName.contains("Test")) {
//                disable("NullAway")
//            }
        }
    }

    dependencies {
        add("api", rootProject.libs.jspecify)
        add("errorprone", rootProject.libs.errorprone.core)
        add("errorprone", rootProject.libs.nullaway)
        add("testImplementation", rootProject.libs.assertj.core)
        add("testImplementation", rootProject.libs.junit.jupiter)
        add("testRuntimeOnly", rootProject.libs.junit.platform.launcher)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            showExceptions = true
            showCauses = true
            showStackTraces = true
        }
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

    extensions.configure<com.github.spotbugs.snom.SpotBugsExtension> {
        toolVersion = rootProject.libs.versions.spotbugs.tool
        effort = com.github.spotbugs.snom.Effort.MAX
        reportLevel = com.github.spotbugs.snom.Confidence.LOW
    }

    tasks.withType<com.github.spotbugs.snom.SpotBugsTask> {
        reports.create("html") {
            required = true
        }
        reports.create("xml") {
            required = true
        }
    }
}
