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

package org.newbiehacker.neeball.pool;

import org.newbiehacker.neeball.Util;

/**
 * Copyright 2006 James Lawrence
 * Date: 10-Feb-2007
 * Time: 15:36:56
 * Modification and redistribution without explicit permission by the creator(s) is prohibited
 * This source may be modified for personal use as long as the original author is accredited
 */
public abstract class ConstantFieldAndMethodref extends Constant {
    /**
     * <p>The fully qualified class name of the class that holds this field or method</p>
     */
    public String class_name;
    /**
     * <p>The name of this field or method</p>
     */
    public String name;
    /**
     * <p>The descriptor of this field or method</p>
     */
    public String descriptor;
    int class_index;
    int name_and_type_index;

    protected ConstantFieldAndMethodref(String class_name, String name, String descriptor) {
        this.class_name = class_name;
        this.name = name;
        this.descriptor = descriptor;
    }

    ConstantFieldAndMethodref() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantFieldAndMethodref that = (ConstantFieldAndMethodref) o;

        if (class_name != null ? !class_name.equals(that.class_name) : that.class_name != null) return false;
        if (descriptor != null ? !descriptor.equals(that.descriptor) : that.descriptor != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = class_name != null ? class_name.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (descriptor != null ? descriptor.hashCode() : 0);
        return result;
    }

    public String toString() {
        if (this instanceof ConstantFieldref)
            return Util.getFriendlyDescriptorString(descriptor) + " " + class_name.replace('/', '.') + "." + name;
        return Util.getMethodReturnType(descriptor) + " " + class_name.replace('/', '.') + "." + name + "(" + Util.getMethodParams(descriptor) + ")";
    }

    protected void finalize() throws Throwable {
        class_name = null;
        name = null;
        descriptor = null;
        super.finalize();
    }
}