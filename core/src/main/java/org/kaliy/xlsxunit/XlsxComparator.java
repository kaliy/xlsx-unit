package org.kaliy.xlsxunit;

import java.io.File;
import java.util.Objects;
import lombok.NoArgsConstructor;

/**
 * Compares XLSX files.
 */
@NoArgsConstructor
public final class XlsxComparator {

    /**
     * Compares two XLSX files for equality.
     *
     * @param expected expected file reference
     * @param actual actual file reference
     * @return true when both file references are equal
     */
    public boolean areEqual(final File expected, final File actual) {
        Objects.requireNonNull(expected, "expected must not be null");
        Objects.requireNonNull(actual, "actual must not be null");

        return Objects.equals(expected, actual);
    }
}
