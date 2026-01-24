package org.kaliy.xlsxunit;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class XlsxComparatorTest {
    private final XlsxComparator comparator = new XlsxComparator();

    @Test
    void areEqual_returnsTrueForSamePath() throws Exception {
        Path temp = Files.createTempFile("xlsx-unit", ".xlsx");
        File expected = temp.toFile();
        File actual = new File(temp.toString());

        assertThat(comparator.areEqual(expected, actual)).isTrue();
    }

    @Test
    void areEqual_returnsFalseForDifferentFiles() throws Exception {
        Path left = Files.createTempFile("xlsx-unit-left", ".xlsx");
        Path right = Files.createTempFile("xlsx-unit-right", ".xlsx");

        assertThat(comparator.areEqual(left.toFile(), right.toFile())).isFalse();
    }
}
