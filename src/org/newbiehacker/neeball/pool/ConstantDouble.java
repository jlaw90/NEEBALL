package org.newbiehacker.neeball.pool;

import org.newbiehacker.neeball.Constants;

public final class ConstantDouble extends TypedConstant<Double> {
    public ConstantDouble(double v) {
        super(v);
    }

    ConstantDouble() {
        super(null);
    }

    public byte getTag() {
        return Constants.CONSTANT_Double;
    }

    public boolean isLong() {
        return true;
    }
}