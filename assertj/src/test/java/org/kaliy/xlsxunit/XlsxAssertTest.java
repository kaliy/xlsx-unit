package org.kaliy.xlsxunit;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

@NoArgsConstructor
class XlsxAssertTest {

    @Test
    void isEqualToPassesForSamePath() throws IOException {
        final Path temp = Files.createTempFile("xlsx-unit", ".xlsx");
        final File expected = temp.toFile();
        final File actual = new File(temp.toString());

        assertThatCode(() -> XlsxAssertions.assertThatXlsx(actual).isEqualTo(expected))
                .doesNotThrowAnyException();
    }

    @Test
    void isEqualToFailsForDifferentFiles() throws IOException {
        final Path left = Files.createTempFile("xlsx-unit-left", ".xlsx");
        final Path right = Files.createTempFile("xlsx-unit-right", ".xlsx");

        assertThatThrownBy(
                () -> XlsxAssertions.assertThatXlsx(left.toFile()).isEqualTo(right.toFile())
        ).isInstanceOf(AssertionError.class);
    }
}
