package org.kaliy.xlsxunit;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link XlsxComparator}.
 */
class XlsxComparatorTest {
    /**
     * Comparator under test.
     */
    private final XlsxComparator comparator = new XlsxComparator();

    /**
     * Creates the test instance.
     */
    @SuppressWarnings({
            "PMD.UnnecessaryConstructor",
            "PMD.UncommentedEmptyConstructor"
    })
    public XlsxComparatorTest() {
    }

    @Test
    void areEqualReturnsTrueForSamePath() throws Exception {
        final Path temp = Files.createTempFile("xlsx-unit", ".xlsx");
        final File expected = temp.toFile();
        final File actual = new File(temp.toString());

        assertThat(comparator.areEqual(expected, actual)).isTrue();
    }

    @Test
    void areEqualReturnsFalseForDifferentFiles() throws Exception {
        final Path left = Files.createTempFile("xlsx-unit-left", ".xlsx");
        final Path right = Files.createTempFile("xlsx-unit-right", ".xlsx");

        assertThat(comparator.areEqual(left.toFile(), right.toFile())).isFalse();
    }
}
