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

public final class NEWARRAY extends Instruction {
    /**
     * <p>The type that this array will hold</p>
     *
     * @see Constants
     */
    public int type;

    /**
     * <p>Constructs a new <code>NEWARRAY</code> with the specified type that this array will hold</p>
     *
     * @param type a tag specifying what primtive type this arry will hold
     */
    public NEWARRAY(int type) {
        this.type = type;
    }

    public String toString() {
        String atype = "INVALID TYPE";
        if (type == Constants.T_BOOLEAN)
            atype = "boolean";
        else if (type == Constants.T_CHAR)
            atype = "char";
        else if (type == Constants.T_FLOAT)
            atype = "float";
        else if (type == Constants.T_DOUBLE)
            atype = "double";
        else if (type == Constants.T_BYTE)
            atype = "byte";
        else if (type == Constants.T_SHORT)
            atype = "short";
        else if (type == Constants.T_INT)
            atype = "int";
        else if (type == Constants.T_LONG)
            atype = "long";
        return "newarray " + atype + " (" + this.type + ")";
    }

    public int getLength() {
        return 2;
    }
}