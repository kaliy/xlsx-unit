package org.kaliy.xlsxunit.core;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.kaliy.xlsxunit.assertj.XlsxAssertions;

class XlsxAssertTest {

    @Test
    void isEqualToPassesForSamePath() throws IOException {
        Path temp = Files.createTempFile("xlsx-unit", ".xlsx");
        File expected = temp.toFile();
        File actual = new File(temp.toString());

        assertThatCode(() -> XlsxAssertions.assertThatXlsx(actual).isEqualTo(expected))
                .doesNotThrowAnyException();
    }

    @Test
    void isEqualToFailsForDifferentFiles() throws IOException {
        Path left = Files.createTempFile("xlsx-unit-left", ".xlsx");
        Path right = Files.createTempFile("xlsx-unit-right", ".xlsx");

        assertThatThrownBy(
                () -> XlsxAssertions.assertThatXlsx(left.toFile()).isEqualTo(right.toFile())
        ).isInstanceOf(AssertionError.class);
    }
}
