package org.kaliy.xlsxunit;

import java.io.File;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Entry point for XLSX assertions.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class XlsxAssertions {

    public static XlsxAssert assertThatXlsx(final File actual) {
        return new XlsxAssert(actual);
    }
}
