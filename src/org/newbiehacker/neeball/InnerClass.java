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

/**
 * Copyright 2006 James Lawrence
 * Date: 26-Jun-2007
 * Time: 22:08:48
 * Modification and redistribution without explicit permission by the creator(s) is prohibited
 * This source may be modified for personal use as long as the original author is accredited
 */
public final class InnerClass {
    public static final int ACC_PUBLIC = 0x0001;
    public static final int ACC_PRIVATE = 0x0002;
    public static final int ACC_PROTECTED = 0x0004;
    public static final int ACC_STATIC = 0x0008;
    public static final int ACC_FINAL = 0x0010;
    public static final int ACC_INTERFACE = 0x0200;
    public static final int ACC_ABSTRACT = 0x0400;
    public static final int ACC_SYNTHETIC = 0x1000;
    public static final int ACC_ANNOTATION = 0x2000;
    public static final int ACC_ENUM = 0x4000;

    int inner_class_idx;
    int outer_class_idx;
    int inner_name_idx;
    public String inner_class;
    public String outer_class;
    public String inner_name;
    /**
     * <p>Marked or implicitly public in source</p>
     */
    public boolean is_public;
    /**
     * <p>Marked private in source</p>
     */
    public boolean is_private;
    /**
     * <p>Marked protected in source</p>
     */
    public boolean is_protected;
    /**
     * <p>Marked or implicitly static in source</p>
     */
    public boolean is_static;
    /**
     * <p>Marked final in source</p>
     */
    public boolean is_final;
    /**
     * <p>Was an interface in source</p>
     */
    public boolean is_interface;
    /**
     * <p>Marked or implicitly abstract in source</p>
     */
    public boolean is_abstract;
    /**
     * <p>Declared synthetic; not present in the source code</p>
     */
    public boolean is_synthetic;
    /**
     * <p>Declared as an annotation type</p>
     */
    public boolean is_annotation;
    /**
     * <p>Declared as an enum type</p>
     */
    public boolean is_enum;

    InnerClass() {
    }

    public InnerClass(String inner_class, String outer_class, String inner_name, int flags) {
        this.inner_class = inner_class;
        this.outer_class = outer_class;
        this.inner_name = inner_name;
        is_public = (flags & ACC_PUBLIC) != 0;
        is_private = (flags & ACC_PRIVATE) != 0;
        is_protected = (flags & ACC_PROTECTED) != 0;
        is_static = (flags & ACC_STATIC) != 0;
        is_final = (flags & ACC_FINAL) != 0;
        is_interface = (flags & ACC_INTERFACE) != 0;
        is_abstract = (flags & ACC_ABSTRACT) != 0;
        is_synthetic = (flags & ACC_SYNTHETIC) != 0;
        is_annotation = (flags & ACC_ANNOTATION) != 0;
        is_enum = (flags & ACC_ENUM) != 0;
    }

    public String toString() {
        return "InnerClass inner=" + inner_class + ",outer=" + outer_class + ",inner_name=" + inner_name;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        inner_class = null;
        outer_class = null;
        inner_name = null;
    }
}