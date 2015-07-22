/*
 * Copyright (c) 2007, James Lawrence
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.newbiehacker.neeball;

import org.newbiehacker.neeball.pool.Constant;

public final class LDC extends Instruction {
    /**
     * <p>The constant to push onto the stack</p>
     */
    public Constant c;
    int index;

    /**
     * <p>Constructs a new <code>LDC</code> with the specified <code>ConstantValue</code> to be pushed on the stack</p>
     * <p>Valid constants are:</p>
     * <p>&nbsp;&nbsp;&nbsp;&nbsp;<code>ConstantFloat</code>,</p>
     * <p>&nbsp;&nbsp;&nbsp;&nbsp;<code>ConstantInteger</code>,</p>
     * <p>&nbsp;&nbsp;&nbsp;&nbsp;<code>ConstantString</code></p>
     * <p><b>NOTE</b>: <code>LDC</code> and <code>LDC_W</code> can be used interchangeably because the library will calculate whether the instruction is wide or not before writing</p>
     *
     * @param c the constant to be pushed onto the stack
     */
    public LDC(Constant c) {
        this.c = c;
    }

    public String toString() {
        return "ldc " + c;
    }

    public int getLength() {
        return 2 + (isWide()? 1: 0);
    }

    public int getStackChange() {
        return 1 + (c.isLong() ? 1: 0);
    }

    public boolean isWide() {
        return (c.isLong() || index > 255);
    }

    protected void finalize() throws Throwable {
        c = null;
        super.finalize();
    }
}