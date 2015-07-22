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

public final class MULTIANEWARRAY extends Instruction {
    /**
     * <p>The fully qualified name of the type that this array will be holding</p>
     */
    public String type;
    int index;
    /**
     * <p>The dimensions of this array</p>
     */
    public int dimensions;

    /**
     * <p>Constructs a new <code>MULTIANEWARRAY</code> with the specified array type and dimensions</p>
     *
     * @param type       the fully qualified class name of the type that this array will be holding
     * @param dimensions the dimensions this array with have
     */
    public MULTIANEWARRAY(String type, int dimensions) {
        this.type = type;
        this.dimensions = dimensions;
    }

    public String toString() {
        return "multianewarray " + this.type + " [" + this.dimensions + "]";
    }

    public int getLength() {
        return 4;
    }

    public int getStackChange() {
        return -dimensions + 1;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        type = null;
    }
}