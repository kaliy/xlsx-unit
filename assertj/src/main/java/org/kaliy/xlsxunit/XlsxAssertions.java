package org.kaliy.xlsxunit;

import java.io.File;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Entry point for XLSX assertions.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class XlsxAssertions {

    /**
     * Creates an AssertJ-style assertion for XLSX files.
     *
     * @param actual actual file reference
     * @return assertion instance
     */
    public static XlsxAssert assertThatXlsx(final File actual) {
        return new XlsxAssert(actual);
    }
}
