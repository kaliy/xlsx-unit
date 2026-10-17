package org.kaliy.xlsxunit.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class XlsxComparatorTest {
    private final XlsxComparator comparator = new XlsxComparator();

    @Test
    void compares_files() throws IOException {
        Path temp = Files.createTempFile("xlsx-unit", ".xlsx");
        File expected = temp.toFile();
        File actual = new File(temp.toString());

        assertThat(comparator.areEqual(expected, actual)).isTrue();
    }
}
