package org.kaliy.xlsxunit.assertj;

import java.io.File;
import org.assertj.core.api.AbstractAssert;
import org.kaliy.xlsxunit.core.XlsxComparator;

/**
 * AssertJ assertion for XLSX files.
 */
public final class XlsxAssert extends AbstractAssert<XlsxAssert, File> {

    private final XlsxComparator comparator = new XlsxComparator();

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
     * @param expected expected file reference
     * @return this assertion
     */
    public XlsxAssert compare(File expected) {
        isNotNull();

        if (!comparator.areEqual(expected, actual)) {
            failWithMessage(
                    "Expected xlsx files to be equal but they were not.%nExpected: %s%nActual: %s",
                    expected,
                    actual
            );
        }

        return this;
    }
}
