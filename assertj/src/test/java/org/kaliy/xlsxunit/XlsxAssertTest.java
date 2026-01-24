package org.kaliy.xlsxunit;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class XlsxAssertTest {
    @Test
    void isEqualTo_passesForSamePath() throws Exception {
        Path temp = Files.createTempFile("xlsx-unit", ".xlsx");
        File expected = temp.toFile();
        File actual = new File(temp.toString());

        assertThatCode(() -> XlsxAssertions.assertThatXlsx(actual).isEqualTo(expected))
                .doesNotThrowAnyException();
    }

    @Test
    void isEqualTo_failsForDifferentFiles() throws Exception {
        Path left = Files.createTempFile("xlsx-unit-left", ".xlsx");
        Path right = Files.createTempFile("xlsx-unit-right", ".xlsx");

        assertThatThrownBy(() -> XlsxAssertions.assertThatXlsx(left.toFile()).isEqualTo(right.toFile()))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Expected xlsx files to be equal");
    }
}
