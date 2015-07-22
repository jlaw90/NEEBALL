package org.newbiehacker.neeball.pool;

import org.newbiehacker.neeball.Constants;

public final class ConstantUtf8 extends TypedConstant<String> {
    public ConstantUtf8(String value) {
        super(value);
    }

    public byte getTag() {
        return Constants.CONSTANT_Utf8;
    }
}