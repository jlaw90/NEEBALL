package org.newbiehacker.neeball.pool;

import org.newbiehacker.neeball.Constants;

public final class ConstantLong extends TypedConstant<Long> {
    public ConstantLong(long value) {
        super(value);
    }

    ConstantLong() {
        super(null);
    }

    public byte getTag() {
        return Constants.CONSTANT_Long;
    }

    public boolean isLong() {
        return true;
    }
}
