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

import org.newbiehacker.neeball.Constants;
import org.newbiehacker.neeball.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Copyright 2006 James Lawrence
 * Date: 10-Feb-2007
 * Time: 01:53:13
 * Modification and redistribution without explicit permission by the creator(s) is prohibited
 * This source may be modified for personal use as long as the original author is accredited
 */
public final class ConstantPool {
    public List<Constant> constants;

    public ConstantPool() {
        this.constants = new ArrayList<>();
    }

    private ConstantPool(Constant[] cp) {
        this.constants = new ArrayList<>(cp.length);
        Collections.addAll(constants, cp);
    }

    public Constant get(int index) {
        return constants.get(index);
    }

    public int insert(Constant c) {
        if (c == null)
            return 0;
        if (c instanceof ConstantClass) {
            ConstantClass ci = (ConstantClass) c;
            ci.name_index = insert(new ConstantUtf8(ci.value));
        } else if (c instanceof ConstantFieldAndMethodref) {
            ConstantFieldAndMethodref ci = (ConstantFieldAndMethodref) c;
            ci.name_and_type_index = insert(new ConstantNameAndType(ci.name, ci.descriptor));
            ci.class_index = insert(new ConstantClass(ci.class_name));
        } else if (c instanceof ConstantNameAndType) {
            ConstantNameAndType ci = (ConstantNameAndType) c;
            ci.name_index = insert(new ConstantUtf8(ci.name));
            ci.descriptor_index = insert(new ConstantUtf8(ci.descriptor));
        } else if (c instanceof ConstantString) {
            ConstantString ci = (ConstantString) c;
            ci.index = insert(new ConstantUtf8(ci.value));
        }
        int idx = constants.indexOf(c);
        if(idx != -1)
            return idx + 1;
        idx = size();
        constants.add(c);
        if(c.isLong())
            constants.add(null);
        return idx;
    }

    public int size() {
        return constants.size() + 1;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        constants.clear();
        constants = null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("ConstantPool [size=%d] {", constants.size()));
        for(int i = 0; i < constants.size(); i++)
            sb.append(i == 0? "": ", ").append(constants.get(i));
        return sb.append("}").toString();
    }

    public static ConstantPool read(DataInputStream dis) throws IOException {
        int constant_pool_count = dis.readUnsignedShort();
        Constant constant;
        Constant[] cp = new Constant[constant_pool_count];
        Queue<Constant> unresolved = new LinkedList<>();
        Logger.logger.fine("Reading constant pool...");
        for (int i = 1; i < constant_pool_count; i++) {
            int tag = dis.readUnsignedByte();
            switch (tag) {
                case Constants.CONSTANT_Class:
                    ConstantClass cc = new ConstantClass();
                    cc.name_index = dis.readUnsignedShort();
                    constant = cc;
                    unresolved.add(constant); // Needs flattening
                    break;
                case Constants.CONSTANT_Double:
                    constant = new ConstantDouble(dis.readDouble());
                    break;
                case Constants.CONSTANT_Fieldref:
                    ConstantFieldref cfr = new ConstantFieldref();
                    cfr.class_index = dis.readUnsignedShort();
                    cfr.name_and_type_index = dis.readUnsignedShort();
                    constant = cfr;
                    unresolved.add(constant); // Needs flattening
                    break;
                case Constants.CONSTANT_Float:
                    constant = new ConstantFloat(dis.readFloat());
                    break;
                case Constants.CONSTANT_Integer:
                    constant = new ConstantInteger(dis.readInt());
                    break;
                case Constants.CONSTANT_InterfaceMethodref:
                    ConstantInterfaceMethodref cimr = new ConstantInterfaceMethodref();
                    cimr.class_index = dis.readUnsignedShort();
                    cimr.name_and_type_index = dis.readUnsignedShort();
                    constant = cimr;
                    unresolved.add(constant); // Needs flattening
                    break;
                case Constants.CONSTANT_Long:
                    constant = new ConstantLong(dis.readLong());
                    break;
                case Constants.CONSTANT_Methodref:
                    ConstantMethodref cmr = new ConstantMethodref();
                    cmr.class_index = dis.readUnsignedShort();
                    cmr.name_and_type_index = dis.readUnsignedShort();
                    constant = cmr;
                    unresolved.add(constant); // Needs flattening
                    break;
                case Constants.CONSTANT_NameAndType:
                    ConstantNameAndType cnat = new ConstantNameAndType();
                    cnat.name_index = dis.readUnsignedShort();
                    cnat.descriptor_index = dis.readUnsignedShort();
                    constant = cnat;
                    unresolved.add(constant); // Needs flattening
                    break;
                case Constants.CONSTANT_String:
                    ConstantString cs = new ConstantString();
                    cs.index = dis.readUnsignedShort();
                    constant = cs;
                    unresolved.add(constant); // Needs flattening
                    break;
                case Constants.CONSTANT_Utf8:
                    constant = new ConstantUtf8(dis.readUTF());
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown constant with tag " + tag + "!");
            }
            cp[i] = constant;
            if (tag == Constants.CONSTANT_Long || tag == Constants.CONSTANT_Double)
                i++;
        }

        while(!unresolved.isEmpty()) {
            Constant c = unresolved.poll();
            if (c instanceof ConstantString) {
                ConstantString cs = (ConstantString) c;
                cs.value = ((ConstantUtf8) cp[cs.index]).value;
            } else if (c instanceof ConstantClass) {
                ConstantClass cc = (ConstantClass) c;
                cc.value = ((ConstantUtf8) cp[cc.name_index]).value;
            } else if (c instanceof ConstantNameAndType) {
                ConstantNameAndType cnat = (ConstantNameAndType) c;
                cnat.descriptor = ((ConstantUtf8) cp[cnat.descriptor_index]).value;
                cnat.name = ((ConstantUtf8) cp[cnat.name_index]).value;
            } else if (c instanceof ConstantFieldAndMethodref) {
                ConstantFieldAndMethodref cfam = (ConstantFieldAndMethodref) c;
                ConstantClass cc = (ConstantClass) cp[cfam.class_index];
                cfam.class_name = cc == null? null: cc.value;
                ConstantNameAndType cnat = (ConstantNameAndType) cp[cfam.name_and_type_index];
                cfam.name = cnat.name;
                cfam.descriptor = cnat.descriptor;
                if(cfam.class_name == null || cnat.name == null || cnat.descriptor == null)
                    unresolved.add(c); // Try again
            }
        }

        return new ConstantPool(cp);
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeShort(size());
        for (Constant c : constants) {
            if(c == null)
                continue;
            if (c instanceof ConstantNameAndType) {
                ConstantNameAndType cnat = (ConstantNameAndType) c;
                dos.writeByte(Constants.CONSTANT_NameAndType);
                dos.writeShort(cnat.name_index);
                dos.writeShort(cnat.descriptor_index);
            } else if (c instanceof ConstantClass) {
                dos.writeByte(Constants.CONSTANT_Class);
                dos.writeShort(((ConstantClass) c).name_index);
            } else if (c instanceof ConstantFieldAndMethodref) {
                ConstantFieldAndMethodref cfam = (ConstantFieldAndMethodref) c;
                if (c instanceof ConstantFieldref)
                    dos.writeByte(Constants.CONSTANT_Fieldref);
                else if (c instanceof ConstantMethodref)
                    dos.writeByte(Constants.CONSTANT_Methodref);
                else if (c instanceof ConstantInterfaceMethodref)
                    dos.writeByte(Constants.CONSTANT_InterfaceMethodref);
                dos.writeShort(cfam.class_index);
                dos.writeShort(cfam.name_and_type_index);
            } else if (c instanceof ConstantString) {
                dos.writeByte(Constants.CONSTANT_String);
                dos.writeShort(((ConstantString) c).index);
            } else if (c instanceof ConstantInteger) {
                dos.writeByte(Constants.CONSTANT_Integer);
                dos.writeInt(((ConstantInteger) c).value);
            } else if (c instanceof ConstantFloat) {
                dos.writeByte(Constants.CONSTANT_Float);
                dos.writeFloat(((ConstantFloat) c).value);
            } else if (c instanceof ConstantLong) {
                dos.writeByte(Constants.CONSTANT_Long);
                dos.writeLong(((ConstantLong) c).value);
            } else if (c instanceof ConstantDouble) {
                dos.writeByte(Constants.CONSTANT_Double);
                dos.writeDouble(((ConstantDouble) c).value);
            }  else if (c instanceof ConstantUtf8) {
                dos.writeByte(Constants.CONSTANT_Utf8);
                dos.writeUTF(((ConstantUtf8) c).value);
            }
        }
    }
}