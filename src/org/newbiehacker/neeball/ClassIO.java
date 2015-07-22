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

import org.newbiehacker.neeball.pool.*;

import java.io.*;
import java.util.*;

/**
 * Copyright 2006 James Lawrence
 * Date: 10-Feb-2007
 * Time: 16:22:57
 * Modification and redistribution without explicit permission by the creator(s) is prohibited
 * This source may be modified for personal use as long as the original author is accredited
 */
public final class ClassIO {
    private ClassIO() {
    }

    /**
     * <p>Reads a <code>ClassFile</code> from the given <code>InputStream</code></p>
     *
     * @param is the <code>InputStream</code> to read from
     * @return a <code>ClassFile</code> corresponding the the one read from the stream
     * @throws IOException if the magic number is wrong or if there it an I/O error
     */
    public static ClassFile readClassFile(InputStream is) throws IOException {
        DataInputStream dis = new DataInputStream(is);
        ClassFile cf = new ClassFile();
        if ((cf.magic = dis.readInt()) != 0xCAFEBABE)
            throw new IOException("Invalid magic number: " + Integer.toHexString(cf.magic));
        cf.minor_version = dis.readUnsignedShort();
        cf.major_version = dis.readUnsignedShort();

        Logger.logger.info("Reading class file...");
        ConstantPool cp = ConstantPool.read(dis);
        Logger.logger.finest(cp.toString());

        int access_flags = dis.readUnsignedShort();

        cf.is_public = (access_flags & ClassFile.ACC_PUBLIC) != 0;
        cf.is_final = (access_flags & ClassFile.ACC_FINAL) != 0;
        cf.is_interface = (access_flags & ClassFile.ACC_INTERFACE) != 0;
        cf.is_abstract = (access_flags & ClassFile.ACC_ABSTRACT) != 0;
        cf.is_synthetic = (access_flags & ClassFile.ACC_SYNTHETIC) != 0;
        cf.is_annotation = (access_flags & ClassFile.ACC_ANNOTATION) != 0;
        cf.is_enum = (access_flags & ClassFile.ACC_ENUM) != 0;

        cf.this_class = ((ConstantClass) cp.get(dis.readUnsignedShort())).value;

        int index = dis.readUnsignedShort();
        if (index != 0)
            cf.super_class = ((ConstantClass) cp.get(index)).value;
        int interfaces_count = dis.readUnsignedShort();
        for (int i = 0; i < interfaces_count; i++)
            cf.interfaces.add(((ConstantClass) cp.get(dis.readUnsignedShort())).value);
        int field_count = dis.readUnsignedShort();
        cf.fields = new LinkedList<>();
        for (int i = 0; i < field_count; i++) {
            Field f = new Field();
            int faccess_flags = dis.readUnsignedShort();
            f.is_public = (faccess_flags & Field.ACC_PUBLIC) != 0;
            f.is_private = (faccess_flags & Field.ACC_PRIVATE) != 0;
            f.is_protected = (faccess_flags & Field.ACC_PROTECTED) != 0;
            f.is_static = (faccess_flags & Field.ACC_STATIC) != 0;
            f.is_final = (faccess_flags & Field.ACC_FINAL) != 0;
            f.is_volatile = (faccess_flags & Field.ACC_VOLATILE) != 0;
            f.is_transient = (faccess_flags & Field.ACC_TRANSIENT) != 0;
            f.is_synthetic = (faccess_flags & Field.ACC_SYNTHETIC) != 0;
            f.is_enum = (faccess_flags & Field.ACC_ENUM) != 0;

            f.name = ((ConstantUtf8) cp.get(dis.readUnsignedShort())).value;
            f.descriptor = ((ConstantUtf8) cp.get(dis.readUnsignedShort())).value;
            int attributes_count = dis.readUnsignedShort();
            for (int i1 = 0; i1 < attributes_count; i1++) {
                String name = ((ConstantUtf8) cp.get(dis.readUnsignedShort())).value;
                int length = dis.readInt();
                if (name.equals("Deprecated"))
                    f.deprecated = true;
                else if (name.equals("Synthetic"))
                    f.synthetic = true;
                else if (name.equals("ConstantValue"))
                    f.constantvalue = cp.get(dis.readUnsignedShort());
                else if (name.equals("Signature"))
                    f.signature = ((ConstantUtf8) cp.get(dis.readUnsignedShort())).value;
                else {
                    System.out.println("Ignoring attribute " + name + "...");
                    byte[] garbage = new byte[length];
                    dis.readFully(garbage);
                }
            }
            cf.fields.add(f);
        }

        int method_count = dis.readUnsignedShort();
        cf.methods = new LinkedList<>();
        for (int i = 0; i < method_count; i++) {
            Method m = new Method();
            int maccess_flags = dis.readUnsignedShort();
            m.is_public = (maccess_flags & Method.ACC_PUBLIC) != 0;
            m.is_private = (maccess_flags & Method.ACC_PRIVATE) != 0;
            m.is_protected = (maccess_flags & Method.ACC_PROTECTED) != 0;
            m.is_static = (maccess_flags & Method.ACC_STATIC) != 0;
            m.is_final = (maccess_flags & Method.ACC_FINAL) != 0;
            m.is_synchronized = (maccess_flags & Method.ACC_SYNCHRONIZED) != 0;
            m.is_bridge = (maccess_flags & Method.ACC_BRIDGE) != 0;
            m.has_varargs = (maccess_flags & Method.ACC_VARARGS) != 0;
            m.is_native = (maccess_flags & Method.ACC_NATIVE) != 0;
            m.is_abstract = (maccess_flags & Method.ACC_ABSTRACT) != 0;
            m.is_strict = (maccess_flags & Method.ACC_STRICT) != 0;
            m.is_synthetic = (maccess_flags & Method.ACC_SYNTHETIC) != 0;

            m.name = ((ConstantUtf8) cp.get(dis.readUnsignedShort())).value;
            m.descriptor = ((ConstantUtf8) cp.get(dis.readUnsignedShort())).value;
            int attributes_count = dis.readUnsignedShort();
            for (int i1 = 0; i1 < attributes_count; i1++) {
                String name = ((ConstantUtf8) cp.get(dis.readUnsignedShort())).value;
                int length = dis.readInt();
                if (name.equals("Deprecated"))
                    m.deprecated = true;
                else if (name.equals("Synthetic"))
                    m.synthetic = true;
                else if (name.equals("Code")) {
                    m.max_stack = dis.readUnsignedShort();
                    // Skip max_locals xD
                    dis.readUnsignedShort();
                    int code_length = dis.readInt();
                    byte[] code = new byte[code_length];
                    dis.readFully(code);
                    m.code = new ArrayList<>();
                    m.setBytes(code, cp);
                    int exception_table_length = dis.readUnsignedShort();
                    m.exception_table = new LinkedList<>();
                    for (int i2 = 0; i2 < exception_table_length; i2++) {
                        ExceptionTableEntry ete = new ExceptionTableEntry();
                        ete.start_pc = dis.readUnsignedShort();
                        ete.end_pc = dis.readUnsignedShort();
                        ete.handler_pc = dis.readUnsignedShort();
                        int catch_type = dis.readUnsignedShort();
                        ete.catch_type = catch_type == 0 ? null : ((ConstantClass) cp.get(catch_type)).value;
                        m.exception_table.add(ete);
                    }
                    m.setIndices();
                    int attribute_count = dis.readUnsignedShort();
                    for (int i2 = 0; i2 < attribute_count; i2++) {
                        int name_index = dis.readUnsignedShort();
                        name = ((ConstantUtf8) cp.get(name_index)).value;
                        System.out.println("Ignoring attribute " + name + "...");
                        int olength = dis.readInt();
                        byte[] goAway = new byte[olength];
                        dis.readFully(goAway);
                    }
                } else if (name.equals("Exceptions")) {
                    int number_of_exceptions = dis.readUnsignedShort();
                    m.thrown_exceptions_table = new LinkedList<>();
                    for (int i2 = 0; i2 < number_of_exceptions; i2++)
                        m.thrown_exceptions_table.add(((ConstantClass) cp.get(dis.readUnsignedShort())).value);
                } else if (name.equals("Signature"))
                    m.signature = ((ConstantUtf8) cp.get(dis.readUnsignedShort())).value;
                else {
                    System.out.println("Ignoring attribute " + name + "...");
                    byte[] data = new byte[length];
                    dis.readFully(data);
                }
            }
            cf.methods.add(m);
        }

        int attribute_count = dis.readUnsignedShort();
        for (int i = 0; i < attribute_count; i++) {
            String name = ((ConstantUtf8) cp.get(dis.readUnsignedShort())).value;
            int length = dis.readInt();
            if (name.equals("Deprecated"))
                cf.deprecated = true;
            else if (name.equals("Synthetic"))
                cf.synthetic = true;
            else if (name.equals("SourceFile"))
                cf.sourcefile = ((ConstantUtf8) cp.get(dis.readUnsignedShort())).value;
            else if (name.equals("Signature"))
                cf.signature = ((ConstantUtf8) cp.get(dis.readUnsignedShort())).value;
            else if (name.equals("InnerClasses")) {
                cf.inner_classes = new LinkedList<>();
                InnerClass c;
                int inner_class, outer_class, inner_name, flags, inner_classes = dis.readUnsignedShort();
                for (int i1 = 0; i1 < inner_classes; i1++) {
                    c = new InnerClass();
                    inner_class = dis.readUnsignedShort();
                    c.inner_class = inner_class == 0 ? null : ((ConstantClass) cp.get(inner_class)).value;
                    outer_class = dis.readUnsignedShort();
                    c.outer_class = outer_class == 0 ? null : ((ConstantClass) cp.get(outer_class)).value;
                    inner_name = dis.readUnsignedShort();
                    c.inner_name = inner_name == 0 ? null : ((ConstantUtf8) cp.get(inner_name)).value;
                    flags = dis.readUnsignedShort();
                    c.is_public = (flags & InnerClass.ACC_PUBLIC) != 0;
                    c.is_private = (flags & InnerClass.ACC_PRIVATE) != 0;
                    c.is_protected = (flags & InnerClass.ACC_PROTECTED) != 0;
                    c.is_static = (flags & InnerClass.ACC_STATIC) != 0;
                    c.is_final = (flags & InnerClass.ACC_FINAL) != 0;
                    c.is_interface = (flags & InnerClass.ACC_INTERFACE) != 0;
                    c.is_abstract = (flags & InnerClass.ACC_ABSTRACT) != 0;
                    c.is_synthetic = (flags & InnerClass.ACC_SYNTHETIC) != 0;
                    c.is_annotation = (flags & InnerClass.ACC_ANNOTATION) != 0;
                    c.is_enum = (flags & InnerClass.ACC_ENUM) != 0;
                }
            } else if (name.equals("EnclosingMethod")) {
                cf.enclosing_method = new EnclosingMethod();
                cf.enclosing_method.class_name = ((ConstantClass) cp.get(dis.readUnsignedShort())).value;
                int nat_index = dis.readUnsignedShort();
                if (nat_index != 0) {
                    ConstantNameAndType cnat = (ConstantNameAndType) cp.get(nat_index);
                    cf.enclosing_method.name = cnat.name;
                    cf.enclosing_method.descriptor = cnat.descriptor;
                }
            } else {
                System.out.println("Ignoring attribute " + name + "...");
                byte[] garbage = new byte[length];
                dis.readFully(garbage);
            }
        }

        return cf;
    }

    /**
     * <p>Writes a <code>ClassFile</code> to the given <code>OutputStream</code>
     *
     * @param cf the <code>ClassFile</code> to write
     * @param os the <code>OutputStream</code> to write to
     * @throws IOException if there is an I/O error
     */
    public static void writeClassFile(ClassFile cf, OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(cf.magic);
        dos.writeShort(cf.minor_version);
        dos.writeShort(cf.major_version);

        ConstantPool cpo = new ConstantPool();
        int deprecated_idx = cpo.insert(new ConstantUtf8("Deprecated"));
        int synthetic_idx = cpo.insert(new ConstantUtf8("Synthetic"));
        int sourcefile_idx = 0;
        int constantvalue_idx = cpo.insert(new ConstantUtf8("ConstantValue"));
        int code_idx = cpo.insert(new ConstantUtf8("Code"));
        int exceptions_idx = cpo.insert(new ConstantUtf8("Exceptions"));
        int inner_classes_idx = 0;
        int signature_idx = cpo.insert(new ConstantUtf8("Signature"));
        int enclosing_method_idx = 0;
        if (cf.sourcefile != null)
            sourcefile_idx = cpo.insert(new ConstantUtf8("SourceFile"));
        if (cf.inner_classes != null && cf.inner_classes.size() > 0)
            inner_classes_idx = cpo.insert(new ConstantUtf8("InnerClasses"));
        if (cf.enclosing_method != null)
            enclosing_method_idx = cpo.insert(new ConstantUtf8("EnclosingMethod"));

        int this_class = cpo.insert(new ConstantClass(cf.this_class));
        int super_class = cpo.insert(new ConstantClass(cf.super_class));
        int sourcefile_index = -1;
        if (cf.sourcefile != null)
            sourcefile_index = cpo.insert(new ConstantUtf8(cf.sourcefile));
        int signature_index = -1;
        if (cf.signature != null)
            signature_index = cpo.insert(new ConstantUtf8(cf.signature));
        int[] interfaces = new int[cf.interfaces.size()];
        for (int i = 0; i < interfaces.length; i++)
            interfaces[i] = cpo.insert(new ConstantClass(cf.interfaces.get(i)));
        for (Field f : cf.fields) {
            f.name_index = cpo.insert(new ConstantUtf8(f.name));
            f.descriptor_index = cpo.insert(new ConstantUtf8(f.descriptor));
            if (f.constantvalue != null)
                f.constantvalue_index = cpo.insert(f.constantvalue);
            else
                f.constantvalue_index = -1;
            if (f.signature != null)
                f.signature_index = cpo.insert(new ConstantUtf8(f.signature));
        }
        for (Method m : cf.methods) {
            m.name_index = cpo.insert(new ConstantUtf8(m.name));
            m.descriptor_index = cpo.insert(new ConstantUtf8(m.descriptor));
            if (m.code != null)
                for (Instruction i : m.code) {
                    if (i instanceof ANEWARRAY) {
                        ANEWARRAY ci = (ANEWARRAY) i;
                        ci.type_index = cpo.insert(new ConstantClass(ci.type));
                    } else if (i instanceof CHECKCAST) {
                        CHECKCAST ci = (CHECKCAST) i;
                        ci.index = cpo.insert(new ConstantClass(ci.type));
                    } else if (i instanceof GETFIELD) {
                        GETFIELD ci = (GETFIELD) i;
                        ci.field_index = cpo.insert(ci.field);
                    } else if (i instanceof GETSTATIC) {
                        GETSTATIC ci = (GETSTATIC) i;
                        ci.field_index = cpo.insert(ci.field);
                    } else if (i instanceof INSTANCEOF) {
                        INSTANCEOF ci = (INSTANCEOF) i;
                        ci.index = cpo.insert(new ConstantClass(ci.type));
                    } else if (i instanceof INVOKEINTERFACE) {
                        INVOKEINTERFACE ci = (INVOKEINTERFACE) i;
                        ci.index = cpo.insert(ci.method);
                    } else if (i instanceof INVOKESPECIAL) {
                        INVOKESPECIAL ci = (INVOKESPECIAL) i;
                        ci.index = cpo.insert(ci.method);
                    } else if (i instanceof INVOKESTATIC) {
                        INVOKESTATIC ci = (INVOKESTATIC) i;
                        ci.index = cpo.insert(ci.method);
                    } else if (i instanceof INVOKEVIRTUAL) {
                        INVOKEVIRTUAL ci = (INVOKEVIRTUAL) i;
                        ci.index = cpo.insert(ci.method);
                    } else if (i instanceof LDC) {
                        LDC ci = (LDC) i;
                        ci.index = cpo.insert(ci.c);
                    } else if (i instanceof MULTIANEWARRAY) {
                        MULTIANEWARRAY ci = (MULTIANEWARRAY) i;
                        ci.index = cpo.insert(new ConstantClass(ci.type));
                    } else if (i instanceof NEW) {
                        NEW ci = (NEW) i;
                        ci.index = cpo.insert(new ConstantClass(ci.type));
                    } else if (i instanceof PUTFIELD) {
                        PUTFIELD ci = (PUTFIELD) i;
                        ci.index = cpo.insert(ci.field);
                    } else if (i instanceof PUTSTATIC) {
                        PUTSTATIC ci = (PUTSTATIC) i;
                        ci.index = cpo.insert(ci.field);
                    }
                }
            if (m.thrown_exceptions_table != null) {
                m.exception_index_table = new int[m.thrown_exceptions_table.size()];
                for (int i = 0; i < m.thrown_exceptions_table.size(); i++)
                    m.exception_index_table[i] = cpo.insert(new ConstantClass(m.thrown_exceptions_table.get(i)));
            }
            if (m.exception_table != null)
                for (ExceptionTableEntry ete : m.exception_table) {
                    ete.catch_type_index = 0;
                    if (ete.catch_type != null)
                        ete.catch_type_index = cpo.insert(new ConstantClass(ete.catch_type));
                }
            if (m.signature != null)
                m.signature_index = cpo.insert(new ConstantUtf8(m.signature));
        }
        if (cf.inner_classes != null)
            for (InnerClass ic : cf.inner_classes) {
                ic.inner_class_idx = 0;
                ic.outer_class_idx = 0;
                ic.inner_name_idx = 0;
                if (ic.inner_class != null)
                    ic.inner_class_idx = cpo.insert(new ConstantClass(ic.inner_class));
                if (ic.outer_class != null)
                    ic.outer_class_idx = cpo.insert(new ConstantClass(ic.outer_class));
                if (ic.inner_name != null)
                    ic.inner_name_idx = cpo.insert(new ConstantUtf8(ic.inner_name));
            }
        if (cf.enclosing_method != null) {
            cf.enclosing_method.class_name_index = cpo.insert(new ConstantUtf8(cf.enclosing_method.class_name));
            cf.enclosing_method.nat_index = cpo.insert(new ConstantNameAndType(cf.enclosing_method.name, cf.enclosing_method.descriptor));
        }

        if (cf.constant_pool_modifiers != null)
            for (ConstantPoolModifier cpm : cf.constant_pool_modifiers)
                cpm.modifyConstantPool(cpo);

        int access_flags = 0;
        access_flags |= ClassFile.ACC_SUPER;
        if (cf.is_public)
            access_flags |= ClassFile.ACC_PUBLIC;
        if (cf.is_final)
            access_flags |= ClassFile.ACC_FINAL;
        if (cf.is_interface)
            access_flags |= ClassFile.ACC_INTERFACE;
        if (cf.is_abstract)
            access_flags |= ClassFile.ACC_ABSTRACT;
        if (cf.is_synthetic)
            access_flags |= ClassFile.ACC_SYNTHETIC;
        if (cf.is_annotation)
            access_flags |= ClassFile.ACC_ANNOTATION;
        if (cf.is_enum)
            access_flags |= ClassFile.ACC_ENUM;

        cpo.write(dos);

        dos.writeShort(access_flags);
        dos.writeShort(this_class);
        dos.writeShort(super_class);
        dos.writeShort(interfaces.length);
        for (int anInterface : interfaces)
            dos.writeShort(anInterface);
        dos.writeShort(cf.fields.size());
        for (Field f : cf.fields) {
            int faccess_flags = 0;
            if (f.is_public)
                faccess_flags |= Field.ACC_PUBLIC;
            if (f.is_private)
                faccess_flags |= Field.ACC_PRIVATE;
            if (f.is_protected)
                faccess_flags |= Field.ACC_PROTECTED;
            if (f.is_static)
                faccess_flags |= Field.ACC_STATIC;
            if (f.is_final)
                faccess_flags |= Field.ACC_FINAL;
            if (f.is_volatile)
                faccess_flags |= Field.ACC_VOLATILE;
            if (f.is_transient)
                faccess_flags |= Field.ACC_TRANSIENT;
            if (f.is_synthetic)
                faccess_flags |= Field.ACC_SYNTHETIC;
            if (f.is_enum)
                faccess_flags |= Field.ACC_ENUM;

            dos.writeShort(faccess_flags);
            dos.writeShort(f.name_index);
            dos.writeShort(f.descriptor_index);
            int attributes_count = 0;
            if (f.deprecated)
                attributes_count++;
            if (f.synthetic)
                attributes_count++;
            if (f.constantvalue != null)
                attributes_count++;
            if (f.signature != null)
                attributes_count++;
            dos.writeShort(attributes_count);
            if (f.deprecated) {
                dos.writeShort(deprecated_idx);
                dos.writeInt(0);
            }
            if (f.synthetic) {
                dos.writeShort(synthetic_idx);
                dos.writeInt(0);
            }
            if (f.constantvalue != null) {
                dos.writeShort(constantvalue_idx);
                dos.writeInt(2);
                dos.writeShort(f.constantvalue_index);
            }
            if (f.signature != null) {
                dos.writeShort(signature_idx);
                dos.writeInt(2);
                dos.writeShort(f.signature_index);
            }
        }

        dos.writeShort(cf.methods.size());
        for (Method m : cf.methods) {
            int maccess_flags = 0;

            if (m.is_public)
                maccess_flags |= Method.ACC_PUBLIC;
            if (m.is_private)
                maccess_flags |= Method.ACC_PRIVATE;
            if (m.is_protected)
                maccess_flags |= Method.ACC_PROTECTED;
            if (m.is_static)
                maccess_flags |= Method.ACC_STATIC;
            if (m.is_final)
                maccess_flags |= Method.ACC_FINAL;
            if (m.is_synchronized)
                maccess_flags |= Method.ACC_SYNCHRONIZED;
            if (m.is_bridge)
                maccess_flags |= Method.ACC_BRIDGE;
            if (m.has_varargs)
                maccess_flags |= Method.ACC_VARARGS;
            if (m.is_native)
                maccess_flags |= Method.ACC_NATIVE;
            if (m.is_abstract)
                maccess_flags |= Method.ACC_ABSTRACT;
            if (m.is_strict)
                maccess_flags |= Method.ACC_STRICT;
            if (m.is_synthetic)
                maccess_flags |= Method.ACC_SYNTHETIC;

            dos.writeShort(maccess_flags);
            dos.writeShort(m.name_index);
            dos.writeShort(m.descriptor_index);

            int attributes_count = 0;
            if (m.deprecated)
                attributes_count++;
            if (m.synthetic)
                attributes_count++;
            if (m.code != null)
                attributes_count++;
            if (m.thrown_exceptions_table != null)
                attributes_count++;
            if (m.signature != null)
                attributes_count++;
            dos.writeShort(attributes_count);

            if (m.deprecated) {
                dos.writeShort(deprecated_idx);
                dos.writeInt(0);
            }
            if (m.synthetic) {
                dos.writeShort(synthetic_idx);
                dos.writeInt(0);
            }
            if (m.code != null) {
                dos.writeShort(code_idx);
                // Calculate attribute length
                int length = 12; // Known values
                length += (m.exception_table.size()) * 8;
                byte[] code = m.getBytes();
                length += code.length;
                dos.writeInt(length);
                int max_locals = 0;
                int max_stack = 0;
                int idx;
                if (m.code.size() != 0) {
                    max_locals = Util.getParamCount(m.descriptor);
                    for (Instruction i : m.code) {
                        if (i instanceof LoadInstruction) {
                            idx = ((LoadInstruction) i).index;
                            if (i instanceof LLOAD || i instanceof DLOAD)
                                ++idx;
                            if (idx > max_locals)
                                max_locals = idx;
                        } else if (i instanceof StoreInstruction) {
                            idx = ((StoreInstruction) i).index;
                            if (i instanceof LSTORE || i instanceof DSTORE)
                                ++idx;
                            if (idx > max_locals)
                                max_locals = idx;
                        }
                    }
                    BranchStack branchTargets = new BranchStack();
                    ExceptionTableEntry ete;
                    for (int i = 0; i < m.exception_table.size(); i++) {
                        ete = m.exception_table.get(i);
                        branchTargets.push(ete.handler_index, 1);
                    }

                    int stackDepth = 0, maxStackDepth = 0;
                    int delta;
                    Instruction instruction;
                    BranchInstruction branch;
                    Switch select;
                    Integer[] indices;
                    //echo(colSize, "Instruction", "Stack delta", "Stack afterwards");
                    for (int pc = 0; pc < m.code.size();) {
                        instruction = m.code.get(pc);
                        delta = instruction.getStackChange();
                        stackDepth += delta;
                        //echo(colSize, instruction.toString(), String.valueOf(delta), String.valueOf(stackDepth));
                        if (stackDepth > maxStackDepth)
                            maxStackDepth = stackDepth;
                        if (instruction instanceof BranchInstruction) {
                            branch = (BranchInstruction) instruction;
                            if (instruction instanceof Switch) {
                                select = (Switch) branch;
                                Collection<Integer> values = select.match_pairs.values();
                                indices = values.toArray(new Integer[values.size()]);
                                for (Integer index : indices)
                                    branchTargets.push(index, stackDepth);
                                pc = m.code.size();
                            } else if (instruction instanceof JSR) {
                                branchTargets.push(pc + 1, stackDepth - 1);
                                pc = m.code.size();
                            }
                            branchTargets.push(branch.branch_index, stackDepth - 1);
                        } else {
                            if (instruction instanceof TerminatingInstruction)
                                pc = m.code.size();
                        }
                        if (pc < m.code.size())
                            ++pc;
                        if (pc == m.code.size()) {
                            BranchTarget bt = branchTargets.pop();
                            if (bt != null) {
                                pc = bt.target;
                                stackDepth = bt.stackDepth;
                                
                                // Added as a test
                                if(stackDepth > maxStackDepth)
                                    maxStackDepth = stackDepth;
                            }
                        }
                    }
                    max_stack = maxStackDepth;
                }
                dos.writeShort(max_stack);
                dos.writeShort(max_locals);
                dos.writeInt(code.length);
                dos.write(code);
                dos.writeShort(m.exception_table.size());
                for (ExceptionTableEntry ete : m.exception_table) {
                    dos.writeShort(ete.start_pc);
                    dos.writeShort(ete.end_pc);
                    dos.writeShort(ete.handler_pc);
                    dos.writeShort(ete.catch_type_index);
                }
                dos.writeShort(0);
            }
            if (m.exception_index_table != null) {
                dos.writeShort(exceptions_idx);
                dos.writeInt(2 + (m.exception_index_table.length * 2));
                dos.writeShort(m.exception_index_table.length);
                for (int exception : m.exception_index_table)
                    dos.writeShort(exception);
            }
            if (m.signature != null) {
                dos.writeShort(signature_idx);
                dos.writeShort(2);
                dos.writeShort(m.signature_index);
            }
        }
        int attribute_count = 0;
        if (cf.deprecated)
            attribute_count++;
        if (cf.synthetic)
            attribute_count++;
        if (cf.sourcefile != null)
            attribute_count++;
        if (cf.signature != null)
            attribute_count++;
        if (cf.inner_classes != null && cf.inner_classes.size() > 0)
            attribute_count++;
        if (cf.enclosing_method != null)
            attribute_count++;
        dos.writeShort(attribute_count);
        if (cf.deprecated) {
            dos.writeShort(deprecated_idx);
            dos.writeInt(0);
        }
        if (cf.synthetic) {
            dos.writeShort(synthetic_idx);
            dos.writeInt(0);
        }
        if (cf.sourcefile != null) {
            dos.writeShort(sourcefile_idx);
            dos.writeInt(2);
            dos.writeShort(sourcefile_index);
        }
        if (cf.signature != null) {
            dos.writeShort(signature_idx);
            dos.writeInt(2);
            dos.writeShort(signature_index);
        }
        if (cf.inner_classes != null && cf.inner_classes.size() > 0) {
            dos.writeShort(inner_classes_idx);
            dos.writeInt(2 + (cf.inner_classes.size() * 8));
            for (InnerClass ic : cf.inner_classes) {
                dos.writeShort(ic.inner_class_idx);
                dos.writeShort(ic.outer_class_idx);
                dos.writeShort(ic.inner_name_idx);
                access_flags = 0;
                if (ic.is_public)
                    access_flags |= InnerClass.ACC_PUBLIC;
                if (ic.is_private)
                    access_flags |= InnerClass.ACC_PRIVATE;
                if (ic.is_protected)
                    access_flags |= InnerClass.ACC_PROTECTED;
                if (ic.is_static)
                    access_flags |= InnerClass.ACC_STATIC;
                if (ic.is_final)
                    access_flags |= InnerClass.ACC_FINAL;
                if (ic.is_interface)
                    access_flags |= InnerClass.ACC_INTERFACE;
                if (ic.is_abstract)
                    access_flags |= InnerClass.ACC_ABSTRACT;
                if (ic.is_synthetic)
                    access_flags |= InnerClass.ACC_SYNTHETIC;
                if (ic.is_annotation)
                    access_flags |= InnerClass.ACC_ANNOTATION;
                if (ic.is_enum)
                    access_flags |= InnerClass.ACC_ENUM;
                dos.writeShort(access_flags);
            }
        }
        if (cf.enclosing_method != null) {
            dos.writeShort(enclosing_method_idx);
            dos.writeInt(4);
            dos.writeShort(cf.enclosing_method.class_name_index);
            dos.writeShort(cf.enclosing_method.nat_index);
        }
    }

    private static final class BranchStack {
        Stack<BranchTarget> branchTargets = new Stack<>();
        Hashtable<Integer, BranchTarget> visitedTargets = new Hashtable<>();

        public void push(final int target, final int stackDepth) {
            if (visited(target))
                return;
            branchTargets.push(visit(target, stackDepth));
        }

        public BranchTarget pop() {
            if (!branchTargets.empty())
                return branchTargets.pop();
            return null;
        }

        private BranchTarget visit(final int target, final int stackDepth) {
            BranchTarget bt = new BranchTarget(target, stackDepth);
            visitedTargets.put(target, bt);
            return bt;
        }

        private boolean visited(final int target) {
            return (visitedTargets.get(target) != null);
        }
    }

    private static final class BranchTarget {
        public int target;
        public int stackDepth;

        private BranchTarget(int target, int stackDepth) {
            this.target = target;
            this.stackDepth = stackDepth;
        }
    }
}