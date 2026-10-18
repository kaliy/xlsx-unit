package org.kaliy.xlsxunit.assertj;

import java.io.File;

/**
 * Entry point for XLSX assertions.
 */
public final class XlsxAssertions {

    private XlsxAssertions() {}

    /**
     * Creates an AssertJ-style assertion for XLSX files.
     *
     * @param actual actual file reference
     * @return assertion instance
     */
    public static XlsxAssert assertThatXlsx(File actual) {
        return new XlsxAssert(actual);
    }
}
