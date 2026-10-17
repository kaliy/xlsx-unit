import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import com.github.spotbugs.snom.SpotBugsTask
import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    `java-library`
    checkstyle
    pmd
    id("net.ltgt.errorprone")
    id("com.github.spotbugs")
}

val libs = the<VersionCatalogsExtension>().named("libs")

fun catalogVersion(alias: String): String = libs.findVersion(alias).get().requiredVersion

fun catalogLibrary(alias: String): Provider<MinimalExternalModuleDependency> = libs.findLibrary(alias).get()

val rootDirectory: Directory = isolated.rootProject.projectDirectory
val javaRelease = catalogVersion("java-release").toInt()
val testJavaVersion = providers.gradleProperty("testJavaVersion")

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(catalogVersion("java-toolchain"))
    }
}

dependencies {
    api(catalogLibrary("jspecify"))
    errorprone(catalogLibrary("errorprone-core"))
    errorprone(catalogLibrary("nullaway"))
    testImplementation(catalogLibrary("assertj-core"))
    testImplementation(catalogLibrary("junit-jupiter"))
    testRuntimeOnly(catalogLibrary("junit-platform-launcher"))
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

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    javaLauncher = javaToolchains.launcherFor {
        languageVersion = testJavaVersion.map { JavaLanguageVersion.of(it) }.orElse(java.toolchain.languageVersion)
    }
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}

checkstyle {
    toolVersion = catalogVersion("checkstyle")
    configFile = rootDirectory.file("config/checkstyle/google_checks_4spaces.xml").asFile
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

pmd {
    toolVersion = catalogVersion("pmd")
    ruleSets = emptyList()
    ruleSetFiles = files(rootDirectory.file("config/pmd/ruleset.xml"))
    isIgnoreFailures = false
    isConsoleOutput = true
}

tasks.withType<Pmd>().configureEach {
    reports {
        html.required = true
    }
}

spotbugs {
    toolVersion = catalogVersion("spotbugs-tool")
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
