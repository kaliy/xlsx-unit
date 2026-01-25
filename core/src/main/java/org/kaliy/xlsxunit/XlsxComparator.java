package org.kaliy.xlsxunit;

import java.io.File;
import java.util.Objects;
import lombok.NoArgsConstructor;

/**
 * Compares XLSX files.
 */
@NoArgsConstructor
public final class XlsxComparator {

    public boolean  areEqual(final File expected, final File actual) {
        Objects.requireNonNull(expected, "expected must not be null");
        Objects.requireNonNull(actual, "actual must not be null");

        return Objects.equals(expected, actual);
    }
}
