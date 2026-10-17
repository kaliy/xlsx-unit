package org.kaliy.xlsxunit.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class XlsxComparatorTest {

    private final XlsxComparator comparator = new XlsxComparator();

    @Test
    void treats_same_file_as_equal(@TempDir Path directory) {
        File expected = directory.resolve("report.xlsx").toFile();
        File actual = new File(expected.getPath());

        assertThat(comparator.areEqual(expected, actual)).isTrue();
    }

    @Test
    void treats_different_files_as_different(@TempDir Path directory) {
        File expected = directory.resolve("expected.xlsx").toFile();
        File actual = directory.resolve("actual.xlsx").toFile();

        assertThat(comparator.areEqual(expected, actual)).isFalse();
    }
}
