package org.kaliy.xlsxunit;

import java.io.File;
import org.assertj.core.api.AbstractAssert;

public final class XlsxAssert extends AbstractAssert<XlsxAssert, File> {
    private final XlsxComparator comparator = new XlsxComparator();

    public XlsxAssert(File actual) {
        super(actual, XlsxAssert.class);
    }

    public XlsxAssert isEqualTo(File expected) {
        isNotNull();

        if (!comparator.areEqual(expected, actual)) {
            failWithMessage("Expected xlsx files to be equal but they were not.\nExpected: %s\nActual: %s", expected, actual);
        }

        return this;
    }
}
