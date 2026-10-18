# Agent guidelines

## Build

- `./gradlew build` compiles with Error Prone and NullAway, and runs the tests, Checkstyle, PMD and
  SpotBugs. Everything must pass. Never weaken a check to get a green build without asking.
- `./gradlew test -PtestJavaVersion=17` runs the tests on another JVM. Compilation always uses JDK 25
  with `--release 17`.
- Every module applies the same plugins in its own `plugins {}` block (`java-library`, `checkstyle`,
  `pmd`, Error Prone, SpotBugs) and declares its dependencies. The root `build.gradle.kts` configures
  those plugins for all modules through `pluginManager.withPlugin(...)` in `subprojects {}`.
- Versions live in `gradle/libs.versions.toml`. To check for updates, run
  `./gradlew versionCatalogUpdate --interactive`, review `gradle/libs.versions.updates.toml`, then
  run `./gradlew versionCatalogApplyUpdates`.

## Code

- Public API goes in `org.kaliy.xlsxunit.core` and `org.kaliy.xlsxunit.assertj`; everything else
  goes in `internal` subpackages.
- Every package has a `package-info.java` annotated with JSpecify's `@NullMarked`. Mark nullable
  types with `@Nullable`; NullAway fails the build otherwise.
- Javadoc on public and protected API, which Checkstyle enforces. No other comments unless the logic
  is genuinely non-obvious.
- Records for immutable values, with defensive copies of collections (`List.copyOf`) and arrays.
- Descriptive names everywhere except trivial loop indices.
- No Lombok. Import every type instead of writing fully qualified names.

## Tests

- JUnit 6 and AssertJ only: no JUnit assertions, no Hamcrest.
- Test methods are snake_case and start with a verb: `reports_missing_merged_region`, not
  `testMergedRegion` or `shouldReportMissingMergedRegion`.
- Separate arrange, act and assert with blank lines, not comments.
- Build workbooks in code. Commit a binary fixture only when the point is a specific writer's output,
  together with a note on how it was produced. Never use company or customer files.
- Use `@TempDir` for anything written to disk.

## Pull requests

- PR titles follow Conventional Commits; CI checks them.
