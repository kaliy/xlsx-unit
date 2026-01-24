package org.kaliy.xlsxunit;

import java.io.File;

public final class XlsxAssertions {
    private XlsxAssertions() {
    }

    public static XlsxAssert assertThatXlsx(File actual) {
        return new XlsxAssert(actual);
    }
}
