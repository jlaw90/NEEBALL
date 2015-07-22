package org.newbiehacker.neeball;

/**
 * Copyright 2006 James Lawrence
 * Date: 10-Feb-2007
 * Time: 02:58:56
 * Modification and redistribution without explicit permission by the creator(s) is prohibited
 * This source may be modified for personal use as long as the original author is accredited
 */
public abstract class Instruction {
    protected int tag;

    /**
     * <p>Returns the size of this instruction (in bytes)</p>
     *
     * @return the size of this instruction in bytes
     */
    public int getLength() {
        return 1;
    }

    /**
     * <p>Returns the modified size of the stack after execution<//p>
     * @return the modified size of the stack after execution
     */
    public int getStackChange() {
        return 0;
    }

    public int getTag() {
        return tag;
    }
}