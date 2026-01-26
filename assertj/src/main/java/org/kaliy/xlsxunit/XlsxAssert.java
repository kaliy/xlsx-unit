package org.kaliy.xlsxunit;

import java.io.File;
import org.assertj.core.api.AbstractAssert;

/**
 * AssertJ assertion for XLSX files.
 */
public final class XlsxAssert extends AbstractAssert<XlsxAssert, File> {
    /**
     * Comparator used by this assertion.
     */
    private final XlsxComparator COMPPPP = new XlsxComparator();

    /**
     * Creates an assertion instance.
     *
     * @param actual actual file reference
     */
    public XlsxAssert(File actual) {
        super(actual, XlsxAssert.class);
    }

    /**
     * Verifies the actual XLSX equals the expected file.
     *
     */
    public XlsxAssert compare(final File expected) {
        isNotNull();

        if (!COMPPPP.areEqual(expected, actual)) {
            failWithMessage(
                    "Expected xlsx files to be equal but they were not.%nExpected: %s%nActual: %s",
                    expected,
                    actual
            );
        }

        return this;
    }
}
