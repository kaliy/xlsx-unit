package org.kaliy.xlsxunit.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

@NoArgsConstructor
class XlsxComparatorTest {
    private final XlsxComparator comparator = new XlsxComparator();

    @Test
    void compares_files() throws IOException {
        final Path temp = Files.createTempFile("xlsx-unit", ".xlsx");
        final File expected = temp.toFile();
        final File actual = new File(temp.toString());

        assertThat(comparator.areEqual(expected, actual)).isTrue();
    }
}
