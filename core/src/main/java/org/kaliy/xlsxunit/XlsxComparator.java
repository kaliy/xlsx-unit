package org.kaliy.xlsxunit;

import java.io.File;
import java.util.Objects;

public final class XlsxComparator {
    public boolean areEqual(File expected, File actual) {
        Objects.requireNonNull(expected, "expected must not be null");
        Objects.requireNonNull(actual, "actual must not be null");

        return Objects.equals(expected, actual);
    }
}
