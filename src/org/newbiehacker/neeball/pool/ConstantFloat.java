package org.newbiehacker.neeball.pool;

import org.newbiehacker.neeball.Constants;

public final class ConstantFloat extends TypedConstant<Float> {
    public ConstantFloat(Float value) {
        super(value);
    }

    ConstantFloat() {
        super(null);
    }

    public byte getTag() {
        return Constants.CONSTANT_Float;
    }
}