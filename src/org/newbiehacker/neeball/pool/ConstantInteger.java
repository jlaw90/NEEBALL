package org.newbiehacker.neeball.pool;

import org.newbiehacker.neeball.Constants;

public final class ConstantInteger extends TypedConstant<Integer> {
    public ConstantInteger(Integer value) {
        super(value);
    }

    public byte getTag() {
        return Constants.CONSTANT_Integer;
    }
}