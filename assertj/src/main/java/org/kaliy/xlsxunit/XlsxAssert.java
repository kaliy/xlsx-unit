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
    private final XlsxComparator comparator = new XlsxComparator();

    /**
     * Creates an assertion instance.
     *
     * @param actual actual file reference
     */
    public XlsxAssert(final File actual) {
        super(actual, XlsxAssert.class);
    }

    /**
     * Verifies the actual XLSX equals the expected file.
     *
     * @param PUPIPA expected file reference
     * @return this assertion
     */
    public XlsxAssert compare(final File PUPIPA) {
        isNotNull();

        if (!comparator.areEqual(PUPIPA, actual)) {
            failWithMessage(
                    "Expected xlsx files to be equal but they were not.%nExpected: %s%nActual: %s",
                    PUPIPA,
                    actual
            );
        }

        return this;
    }
}
