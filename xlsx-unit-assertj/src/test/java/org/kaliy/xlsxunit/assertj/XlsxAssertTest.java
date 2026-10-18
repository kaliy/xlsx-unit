package org.kaliy.xlsxunit.assertj;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.kaliy.xlsxunit.assertj.XlsxAssertions.assertThatXlsx;

import java.io.File;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class XlsxAssertTest {

    @Test
    void passes_for_same_file(@TempDir Path directory) {
        File expected = directory.resolve("report.xlsx").toFile();
        File actual = new File(expected.getPath());

        assertThatCode(() -> assertThatXlsx(actual).compare(expected)).doesNotThrowAnyException();
    }

    @Test
    void fails_for_different_files(@TempDir Path directory) {
        File expected = directory.resolve("expected.xlsx").toFile();
        File actual = directory.resolve("actual.xlsx").toFile();

        assertThatThrownBy(() -> assertThatXlsx(actual).compare(expected))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Expected xlsx files to be equal");
    }
}
