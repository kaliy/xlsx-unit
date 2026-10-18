import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import com.github.spotbugs.snom.SpotBugsExtension
import com.github.spotbugs.snom.SpotBugsTask
import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    alias(libs.plugins.errorprone) apply false
    alias(libs.plugins.spotbugs) apply false
}

val compileJavaVersion = JavaLanguageVersion.of(libs.versions.java.toolchain.get())
val javaRelease = libs.versions.java.release.get().toInt()
val testJavaVersion = providers.gradleProperty("testJavaVersion").map { JavaLanguageVersion.of(it) }
val checkstyleVersion = libs.versions.checkstyle.get()
val pmdVersion = libs.versions.pmd.get()
val spotbugsVersion = libs.versions.spotbugs.tool.get()
val checkstyleConfig = file("config/checkstyle/google_checks_4spaces.xml")
val pmdRuleSet = file("config/pmd/ruleset.xml")
val jspecify = libs.jspecify
val errorproneCore = libs.errorprone.core
val nullaway = libs.nullaway
val assertjCore = libs.assertj.core
val junitJupiter = libs.junit.jupiter
val junitPlatformLauncher = libs.junit.platform.launcher

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "checkstyle")
    apply(plugin = "pmd")
    apply(plugin = "net.ltgt.errorprone")
    apply(plugin = "com.github.spotbugs")

    configure<JavaPluginExtension> {
        toolchain {
            languageVersion = compileJavaVersion
        }
    }

    dependencies {
        "api"(jspecify)
        "errorprone"(errorproneCore)
        "errorprone"(nullaway)
        "testImplementation"(assertjCore)
        "testImplementation"(junitJupiter)
        "testRuntimeOnly"(junitPlatformLauncher)
    }

    tasks.withType<JavaCompile>().configureEach {
        options.release = javaRelease
        options.errorprone {
            check("NullAway", CheckSeverity.ERROR)
            check("RequireExplicitNullMarking", CheckSeverity.ERROR)
            option("NullAway:OnlyNullMarked", "true")
            option("NullAway:JSpecifyMode", "true")
        }
    }

    val javaToolchains = the<JavaToolchainService>()

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        javaLauncher = javaToolchains.launcherFor {
            languageVersion = testJavaVersion.orElse(compileJavaVersion)
        }
        testLogging {
            events("passed", "skipped", "failed")
            exceptionFormat = TestExceptionFormat.FULL
            showExceptions = true
            showCauses = true
            showStackTraces = true
        }
    }

    configure<CheckstyleExtension> {
        toolVersion = checkstyleVersion
        configFile = checkstyleConfig
        maxWarnings = 0
        maxErrors = 0
    }

    tasks.withType<Checkstyle>().configureEach {
        reports {
            sarif.required = true
            xml.required = true
            html.required = true
        }
    }

    configure<PmdExtension> {
        toolVersion = pmdVersion
        ruleSetFiles = files(pmdRuleSet)
        isIgnoreFailures = false
        isConsoleOutput = true
    }

    tasks.withType<Pmd>().configureEach {
        reports {
            html.required = true
        }
    }

    configure<SpotBugsExtension> {
        toolVersion = spotbugsVersion
        effort = Effort.MAX
        reportLevel = Confidence.LOW
    }

    tasks.withType<SpotBugsTask>().configureEach {
        reports.create("html") {
            required = true
        }
        reports.create("xml") {
            required = true
        }
    }
}
