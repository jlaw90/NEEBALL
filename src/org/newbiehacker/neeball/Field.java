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

/**
 * Copyright 2006 James Lawrence
 * Date: 10-Feb-2007
 * Time: 16:01:57
 * Modification and redistribution without explicit permission by the creator(s) is prohibited
 * This source may be modified for personal use as long as the original author is accredited
 */
public final class Field implements Comparable {
    /**
     * <p>Declared public; may be accessed from outside its package.</p>
     */
    public static final int ACC_PUBLIC = 0x0001;
    /**
     * <p>Declared private; usable only within the defining class.</p>
     */
    public static final int ACC_PRIVATE = 0x0002;
    /**
     * <p>Declared protected; may be accessed within subclasses.</p>
     */
    public static final int ACC_PROTECTED = 0x0004;
    /**
     * <p>Declared static.</p>
     */
    public static final int ACC_STATIC = 0x0008;
    /**
     * <p>Declared final; no further assignment after initialization.</p>
     */
    public static final int ACC_FINAL = 0x0010;
    /**
     * <p>Declared volatile; cannot be cached.</p>
     */
    public static final int ACC_VOLATILE = 0x0040;
    /**
     * <p>Declared transient; not written or read by a persistent object manager.</p>
     */
    public static final int ACC_TRANSIENT = 0x0080;
    /**
     * <p>Declared synthetic; Not present in the source code.</p>
     */
    public static final int ACC_SYNTHETIC = 0x1000;
    /**
     * <p>Declared as an element of an enum.</p>
     */
    public static final int ACC_ENUM = 0x4000;

    /**
     * <p>Whether this field is public</p>
     */
    public boolean is_public;
    /**
     * <p>Whether this field is private</p>
     */
    public boolean is_private;
    /**
     * <p>Whether this field is protected</p>
     */
    public boolean is_protected;
    /**
     * <p>Whether this field is static</p>
     */
    public boolean is_static;
    /**
     * <p>Whether this field is final</p>
     */
    public boolean is_final;
    /**
     * <p>Whether this field is volatile</p>
     */
    public boolean is_volatile;
    /**
     * <p>Whether this field is volatile</p>
     */
    public boolean is_transient;
    /**
     * <p>Whether this field is synthetic</p>
     */
    public boolean is_synthetic;
    /**
     * <p>Whether this field holds an enum type</p>
     */
    public boolean is_enum;

    /**
     * <p>Whether this field has a deprecated attribute</p>
     */
    public boolean deprecated;
    /**
     * <p>Whether this field has a synthetic attribute</p>
     */
    public boolean synthetic;
    /**
     * <p>The name of this field</p>
     */
    public String name;
    int name_index;
    /**
     * <p>The descriptor of this field</p>
     */
    public String descriptor;
    int descriptor_index;

    // Flattened from the Signature attribute
    /**
     * <p>The Signature attribute</p>
     */
    public String signature;
    int signature_index;

    // Flattened from ConstantValue attribute
    /**
     * <p>If null then this field does not have a constant value, if not then the constant value is of type <code>ConstantValue</code> and defines what this field's constant value is</p>
     */
    public Constant constantvalue;
    int constantvalue_index;

    /**
     * <p>Constructs a new <code>Field</code> with every possible field set</p>
     *
     * @param name          the name of this field
     * @param descriptor    the descriptor of this field
     * @param access_flags  the access_flags of this field
     * @param signature     the signature attribute of this field
     * @param deprecated    whether this field is deprecated
     * @param synthetic     whether this field is synthetic
     * @param constantvalue the constant value of this field
     */
    public Field(String name, String descriptor, int access_flags, String signature, boolean deprecated, boolean synthetic, Constant constantvalue) {
        this.name = name;
        this.descriptor = descriptor;
        is_public = (access_flags & ACC_PUBLIC) != 0;
        is_private = (access_flags & ACC_PRIVATE) != 0;
        is_protected = (access_flags & ACC_PROTECTED) != 0;
        is_static = (access_flags & ACC_STATIC) != 0;
        is_final = (access_flags & ACC_FINAL) != 0;
        is_volatile = (access_flags & ACC_VOLATILE) != 0;
        is_transient = (access_flags & ACC_TRANSIENT) != 0;
        is_synthetic = (access_flags & ACC_SYNTHETIC) != 0;
        is_enum = (access_flags & ACC_ENUM) != 0;
        this.signature = signature;
        this.deprecated = deprecated;
        this.synthetic = synthetic;
        this.constantvalue = constantvalue;
    }

    /**
     * <p>Constructs a new <code>Field</code> with the given name, descriptor and access flags</p>
     *
     * @param name         the name of this field
     * @param descriptor   the descriptor of this field
     * @param access_flags the access flags of this field
     */
    public Field(String name, String descriptor, int access_flags) {
        this(name, descriptor, access_flags, null, false, false, null);
    }

    /**
     * <p>Constructs a new <code>Field</code> with the specified name and descriptor</p>
     *
     * @param name       the name of this field
     * @param descriptor the name of this descriptor
     */
    public Field(String name, String descriptor) {
        this(name, descriptor, 0);
    }

    /**
     * <p>Constructs a new <code>Field</code> with no values set</p>
     */
    public Field() {
    }

    public int compareTo(Object o) {
        if (!(o instanceof Field))
            return 1;
        Field f = (Field) o;
        int val = 0;
        if (f.is_static && !is_static)
            val += 1;
        else if (!f.is_static && is_static)
            val -= 1;
        if (f.is_final && !is_final)
            val += 1;
        else if (!f.is_final && is_final)
            val -= 1;
        if (val != 0)
            return val;
        return name.compareTo(f.name) + descriptor.compareTo(f.descriptor);
    }

    public String toString() {
        return Util.getFriendlyDescriptorString(descriptor) + " " + name;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        name = null;
        descriptor = null;
        signature = null;
        constantvalue = null;
    }
}