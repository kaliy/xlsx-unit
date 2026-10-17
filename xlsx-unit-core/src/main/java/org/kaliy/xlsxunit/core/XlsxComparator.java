package org.kaliy.xlsxunit.core;

import java.io.File;
import java.util.Objects;

/**
 * Compares XLSX files.
 */
public final class XlsxComparator {

    /**
     * Compares two XLSX files for equality.
     *
     * @param expected expected file reference
     * @param actual   actual file reference
     * @return true when both file references are equal
     */
    public boolean areEqual(File expected, File actual) {
        Objects.requireNonNull(expected, "expected must not be null");
        Objects.requireNonNull(actual, "actual must not be null");

        return Objects.equals(expected, actual);
    }
}
