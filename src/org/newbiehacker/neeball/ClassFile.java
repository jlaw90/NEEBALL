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

import org.newbiehacker.neeball.pool.ConstantPoolModifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2006 James Lawrence
 * Date: 10-Feb-2007
 * Time: 15:28:02
 * Modification and redistribution without explicit permission by the creator(s) is prohibited
 * This source may be modified for personal use as long as the original author is accredited
 */
public final class ClassFile {
    /**
     * <p>Declared public; may be accessed from outside its package.</p>
     */
    public static final int ACC_PUBLIC = 0x0001;
    /**
     * <p>Declared final; no subclasses allowed</p>
     */
    public static final int ACC_FINAL = 0x0010;
    /**
     * <p>Treat superclass methods specially when invoked by the invokespecial instruction.</p>
     */
    public static final int ACC_SUPER = 0x0020;
    /**
     * <p>Is an interface, not a class.</p
     */
    public static final int ACC_INTERFACE = 0x0200;
    /**
     * <p>Declared abstract; may not be instantiated.</p>
     */
    public static final int ACC_ABSTRACT = 0x0400;
    /**
     * <p>Declared synthetic; Not present in the source code.</p>
     */
    public static final int ACC_SYNTHETIC = 0x1000;
    /**
     * <p>Declared as an annotation type</p>
     */
    public static final int ACC_ANNOTATION = 0x2000;
    /**
     * <p>Declared as an enum type</p>
     */
    public static final int ACC_ENUM = 0x4000;

    /**
     * <p>Magic number of this class file, if not 0xCAFEBABE, then this library and the JVM will not read it</p>
     */
    public int magic = 0xCAFEBABE;
    /**
     * <p>Minor version of this class file</p>
     */
    public int minor_version;
    /**
     * <p>Major version of this class file</p>
     */
    public int major_version;

    /**
     * <p>Whether this class file is public</p>
     */
    public boolean is_public;
    /**
     * <p>Whether this class file is final</p>
     */
    public boolean is_final;
    /**
     * <p>Whether this class file is an interface</p>
     */
    public boolean is_interface;
    /**
     * <p>Whether this class file is abstract</p>
     */
    public boolean is_abstract;
    /**
     * <p>Whether this class file is synthetic</p>
     */
    public boolean is_synthetic;
    /**
     * <p>Whether this class file is an annotation type</p>
     */
    public boolean is_annotation;
    /**
     * <p>Whether this class file is an enum</p>
     */
    public boolean is_enum;

    /**
     * <p>Whether this class file has a deprecated attribute</p>
     */
    public boolean deprecated;
    /**
     * <p>Whether this class file has a synthetic attribute</p>
     */
    public boolean synthetic;
    /**
     * <p>The sourcefile this class was compiled from, or null if not known</p>
     */
    public String sourcefile;
    /**
     * <p>The Signature attribute</p>
     */
    public String signature;

    /**
     * <p>This class's fully qualified name</p>
     */
    public String this_class;
    /**
     * <p>This class files super class's fully qualified name</p>
     */
    public String super_class;
    /**
     * <p>Any interfaces this class file implements</p>
     */
    public List<String> interfaces;
    /**
     * <p>The fields this class contains</p>
     */
    public List<Field> fields;
    /**
     * <p>The methods this class has</p>
     */
    public List<Method> methods;
    // Flattened from InnerClasses attribute
    /**
     * <p>Holds an array of InnerClasses</p>
     */
    public List<InnerClass> inner_classes;
    // Flattened from EnclosingMethod attribute
    public EnclosingMethod enclosing_method;

    // Allows modification of the ConstantPool at write-time
    public List<ConstantPoolModifier> constant_pool_modifiers;

    /**
     * <p>Constructs a new <code>ClassFile</code> with practically every value set</p>
     *
     * @param name         this class's name
     * @param superClass   this class's super class
     * @param access_flags this class's access_flags
     * @param minor        this class's minor version
     * @param major        this class's major version
     * @param sourcefile   this class's sourcefile
     * @param signature    signature for Signature attribute
     * @param deprecated   whether this class is deprecated
     * @param synthetic    whether this class is synthetic
     */
    public ClassFile(String name, String superClass, int access_flags, int minor, int major, String sourcefile, String signature, boolean deprecated, boolean synthetic) {
        this.minor_version = minor;
        this.major_version = major;
        is_public = (access_flags & ACC_PUBLIC) != 0;
        is_final = (access_flags & ACC_FINAL) != 0;
        is_interface = (access_flags & ACC_INTERFACE) != 0;
        is_abstract = (access_flags & ACC_ABSTRACT) != 0;
        is_synthetic = (access_flags & ACC_SYNTHETIC) != 0;
        is_annotation = (access_flags & ACC_ANNOTATION) != 0;
        is_enum = (access_flags & ACC_ENUM) != 0;
        this.deprecated = deprecated;
        this.synthetic = synthetic;
        this.sourcefile = sourcefile;
        this.this_class = name;
        this.super_class = superClass;
        this.interfaces = new ArrayList<String>();
        this.fields = new ArrayList<Field>();
        this.methods = new ArrayList<Method>();
    }

    /**
     * <p>Constructs a new <code>ClassFile</code> with values set as specified below</p>
     *
     * @param name         this class's name
     * @param superClass   this class's super class
     * @param access_flags this class's access flags
     * @param minor        this class's minor version
     * @param major        this class's major version
     * @param sourcefile   this class's sourcefile
     */
    public ClassFile(String name, String superClass, int access_flags, int minor, int major, String sourcefile) {
        this(name, superClass, access_flags, minor, major, sourcefile, null, false, false);
    }

    /**
     * <p>Constructs a new <code>ClassFile</code> with the values below</p>
     *
     * @param name         this class's name
     * @param superClass   this class's super class
     * @param access_flags this class's access flags
     * @param minor        this class's minor version
     * @param major        this class's major version
     */
    public ClassFile(String name, String superClass, int access_flags, int minor, int major) {
        this(name, superClass, access_flags, minor, major, null, null, false, false);
    }

    /**
     * <p>Constructs a new <code>ClassFile</code> with the specified name, super class and access flags</p>
     *
     * @param name         this class's name
     * @param superClass   this class's super class
     * @param access_flags this class's access flags
     */
    public ClassFile(String name, String superClass, int access_flags) {
        this(name, superClass, access_flags, 0, 50, null, null, false, false);
    }

    /**
     * <p>Constructs a new <code>ClassFile</code> with the specified name and super class</p>
     *
     * @param name       this class's name
     * @param superClass this class's super class
     */
    public ClassFile(String name, String superClass) {
        this(name, superClass, 0, 0, 50, null, null, false, false);
    }

    /**
     * <p>Constructs a new <code>ClassFile</code> with the specified name, it will extends java.lang.Object</p>
     *
     * @param name this class's name
     */
    public ClassFile(String name) {
        this(name, "java/lang/Object", 0, 0, 50, null, null, false, false);
    }

    /**
     * <p>Constructs a new class file will all default values as below.</p>
     * <p>magic is 0xCAFEBABE</p>
     * <p>minor_version is 0, major_version = 50 (Java 1.6)</p>
     * <p>super_class is java.lang.Object</p>
     */
    public ClassFile() {
        this(null, "java/lang/Object", 0, 0, 50, null, null, false, false);
    }

    /**
     * <p>Searches this class for a {@code Field} with the specified name and descriptor, if found it returns it, if not it returns null</p>
     *
     * @param name       the name of the field
     * @param descriptor the descriptor of the field
     * @return a field with the specified name and descriptor or null
     */
    public Field getField(String name, String descriptor) {
        for (Field f : fields)
            if (f.name.equals(name) && f.descriptor.equals(descriptor))
                return f;
        return null;
    }

    /**
     * <p>Returns all fields in the class with the specified name, or null if there are none</p>
     *
     * @param name the name of the fields
     * @return all the fields in this class with the same name as the one specified, or null if there are none
     */
    public List<Field> getFieldsByName(String name) {
        List<Field> l = new ArrayList<Field>();
        for (Field f : fields)
            if (f.name.equals(name))
                l.add(f);
        return l.size() == 0 ? null : l;
    }

    /**
     * <p>Returns all fields in the class with the specified descriptor, or null if there are none</p>
     *
     * @param descriptor the descriptor of the fields
     * @return all the fields in this class with the same descriptor as the one specified, or null if there are none
     */
    public List<Field> getFieldsByDescriptor(String descriptor) {
        List<Field> l = new ArrayList<Field>();
        for (Field f : fields)
            if (f.descriptor.equals(descriptor))
                l.add(f);
        return l.size() == 0 ? null : l;
    }

    /**
     * <p>Searches this class for a {@code Method} with the specified name and descriptor, if found it returns it, if not it returns null</p>
     *
     * @param name       the name of the method
     * @param descriptor the descriptor of the method
     * @return a method with the specified name and descriptor or null
     */
    public Method getMethod(String name, String descriptor) {
        for (Method m : methods)
            if (m.name.equals(name) && m.descriptor.equals(descriptor))
                return m;
        return null;
    }

    /**
     * <p>Returns all methods in the class with the specified name, or null if there are none</p>
     *
     * @param name the name of the methods
     * @return all the methods in this class with the same name as the one specified, or null if there are none
     */
    public List<Method> getMethodsByName(String name) {
        List<Method> l = new ArrayList<Method>();
        for (Method m : methods)
            if (m.name.equals(name))
                l.add(m);
        return l.size() == 0 ? null : l;
    }

    /**
     * <p>Returns all methods in the class with the specified descriptor, or null if there are none</p>
     *
     * @param descriptor the descriptor of the methods
     * @return all the methods in this class with the same descriptor as the one specified, or null if there are none
     */
    public List<Method> getMethodsByDescriptor(String descriptor) {
        List<Method> l = new ArrayList<Method>();
        for (Method m : methods)
            if (m.descriptor.equals(descriptor))
                l.add(m);
        return l.size() == 0 ? null : l;
    }

    public String toString() {
        return this_class;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        sourcefile = null;
        signature = null;
        this_class = null;
        super_class = null;
        interfaces.clear();
        interfaces = null;
        fields.clear();
        fields = null;
        methods.clear();
        methods = null;
        inner_classes.clear();
        inner_classes = null;
        enclosing_method = null;
        constant_pool_modifiers.clear();
        constant_pool_modifiers = null;
    }
}