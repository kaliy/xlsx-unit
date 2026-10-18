# xlsx-unit

JsonUnit-style assertions for Excel workbooks. Compare an actual `.xlsx` with an expected one the way
a person reading both in Excel would (values, formatting, layout), and get every difference in one
failure, addressed in A1 notation.

> **Status:** early development. Nothing is published yet, and the API below is the target for
> 0.1.0 rather than something you can use today.

## Planned usage

```java
import static org.kaliy.xlsxunit.assertj.XlsxAssertions.assertThatXlsx;

assertThatXlsx(actualBytes)
        .withColumnWidthTolerance(0.20)
        .isEqualTo(Path.of("src/test/resources/expected/report.xlsx"));
```

```text
XLSX documents are different (3 differences)
Sheet "Report":
  A6:H6  font.bold     expected: true     but was: false
  A2:L2  merge         expected: present  but was: missing
  C7     numberFormat  expected: General  but was: 0.00%
```

## Modules

| Artifact | Purpose |
|---|---|
| `org.kaliy:xlsx-unit-core` | Comparison engine, independent of any assertion library |
| `org.kaliy:xlsx-unit-assertj` | AssertJ entry point, `assertThatXlsx(...)` |

## Requirements

Java 17 or later. Apache POI 5 comes in as a transitive dependency.

## Building

```bash
./gradlew build
```

The build compiles with JDK 25, targeting Java 17, and Gradle provisions JDK 25 if it is not
installed. `build` runs the tests plus Error Prone with NullAway, Checkstyle, PMD and SpotBugs. To run
the tests on another JVM:

```bash
./gradlew test -PtestJavaVersion=17
```

## License

[Apache License 2.0](LICENSE)
