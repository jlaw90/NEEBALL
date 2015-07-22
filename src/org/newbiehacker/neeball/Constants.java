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

public final class Constants {
    private Constants() {
    }

    public static final int AALOAD = 0x32;
    public static final int AASTORE = 0x53;
    public static final int ACONST_NULL = 0x1;
    public static final int ALOAD = 0x19;
    public static final int ALOAD_0 = 0x2a;
    public static final int ALOAD_1 = 0X2b;
    public static final int ALOAD_2 = 0x2c;
    public static final int ALOAD_3 = 0x2d;
    public static final int ANEWARRAY = 0xbd;
    public static final int ARETURN = 0xb0;
    public static final int ARRAYLENGTH = 0xbe;
    public static final int ASTORE = 0x3a;
    public static final int ASTORE_0 = 0x4b;
    public static final int ASTORE_1 = 0x4c;
    public static final int ASTORE_2 = 0x4d;
    public static final int ASTORE_3 = 0x4e;
    public static final int ATHROW = 0xbf;
    public static final int BALOAD = 0x33;
    public static final int BASTORE = 0x54;
    public static final int BIPUSH = 0x10;
    public static final int CALOAD = 0x34;
    public static final int CASTORE = 0x55;
    public static final int CHECKCAST = 0xc0;
    public static final int D2F = 0x90;
    public static final int D2I = 0x8e;
    public static final int D2L = 0x8f;
    public static final int DADD = 0x63;
    public static final int DALOAD = 0x31;
    public static final int DASTORE = 0x52;
    public static final int DCMPG = 0x98;
    public static final int DCMPL = 0x97;
    public static final int DCONST_0 = 0xe;
    public static final int DCONST_1 = 0xf;
    public static final int DDIV = 0x6f;
    public static final int DLOAD = 0x18;
    public static final int DLOAD_0 = 0x26;
    public static final int DLOAD_1 = 0x27;
    public static final int DLOAD_2 = 0x28;
    public static final int DLOAD_3 = 0x29;
    public static final int DMUL = 0x6b;
    public static final int DNEG = 0x77;
    public static final int DREM = 0x73;
    public static final int DRETURN = 0xaf;
    public static final int DSTORE = 0x39;
    public static final int DSTORE_0 = 0x47;
    public static final int DSTORE_1 = 0x48;
    public static final int DSTORE_2 = 0x49;
    public static final int DSTORE_3 = 0x4a;
    public static final int DSUB = 0x67;
    public static final int DUP = 0x59;
    public static final int DUP_X1 = 0x5a;
    public static final int DUP_X2 = 0x5b;
    public static final int DUP2 = 0x5c;
    public static final int DUP2_X1 = 0x5d;
    public static final int DUP2_X2 = 0x5e;
    public static final int F2D = 0x8d;
    public static final int F2I = 0x8b;
    public static final int F2L = 0x8c;
    public static final int FADD = 0x62;
    public static final int FALOAD = 0x30;
    public static final int FASTORE = 0x51;
    public static final int FCMPG = 0x96;
    public static final int FCMPL = 0x95;
    public static final int FCONST_0 = 0xb;
    public static final int FCONST_1 = 0xc;
    public static final int FCONST_2 = 0xd;
    public static final int FDIV = 0x6e;
    public static final int FLOAD = 0x17;
    public static final int FLOAD_0 = 0x22;
    public static final int FLOAD_1 = 0x23;
    public static final int FLOAD_2 = 0x24;
    public static final int FLOAD_3 = 0x25;
    public static final int FMUL = 0x6a;
    public static final int FNEG = 0x76;
    public static final int FREM = 0x72;
    public static final int FRETURN = 0xae;
    public static final int FSTORE = 0x38;
    public static final int FSTORE_0 = 0x43;
    public static final int FSTORE_1 = 0x44;
    public static final int FSTORE_2 = 0x45;
    public static final int FSTORE_3 = 0x46;
    public static final int FSUB = 0x66;
    public static final int GETFIELD = 0xb4;
    public static final int GETSTATIC = 0xb2;
    public static final int GOTO = 0xa7;
    public static final int GOTO_W = 0xc8;
    public static final int I2B = 0x91;
    public static final int I2C = 0x92;
    public static final int I2D = 0x87;
    public static final int I2F = 0x86;
    public static final int I2L = 0x85;
    public static final int I2S = 0x93;
    public static final int IADD = 0x60;
    public static final int IALOAD = 0x2e;
    public static final int IAND = 0x7e;
    public static final int IASTORE = 0x4f;
    public static final int ICONST_M1 = 0x2;
    public static final int ICONST_0 = 0x3;
    public static final int ICONST_1 = 0x4;
    public static final int ICONST_2 = 0x5;
    public static final int ICONST_3 = 0x6;
    public static final int ICONST_4 = 0x7;
    public static final int ICONST_5 = 0x8;
    public static final int IDIV = 0x6c;
    public static final int IF_ACMPEQ = 0xa5;
    public static final int IF_ACMPNE = 0xa6;
    public static final int IF_ICMPEQ = 0x9f;
    public static final int IF_ICMPNE = 0xa0;
    public static final int IF_ICMPLT = 0xa1;
    public static final int IF_ICMPGE = 0xa2;
    public static final int IF_ICMPGT = 0xa3;
    public static final int IF_ICMPLE = 0xa4;
    public static final int IFEQ = 0x99;
    public static final int IFNE = 0x9a;
    public static final int IFLT = 0x9b;
    public static final int IFGE = 0x9c;
    public static final int IFGT = 0x9d;
    public static final int IFLE = 0x9e;
    public static final int IFNONNULL = 0xc7;
    public static final int IFNULL = 0xc6;
    public static final int IINC = 0x84;
    public static final int ILOAD = 0x15;
    public static final int ILOAD_0 = 0x1a;
    public static final int ILOAD_1 = 0x1b;
    public static final int ILOAD_2 = 0x1c;
    public static final int ILOAD_3 = 0x1d;
    public static final int IMUL = 0x68;
    public static final int INEG = 0x74;
    public static final int INSTANCEOF = 0xc1;
    public static final int INVOKEINTERFACE = 0xb9;
    public static final int INVOKESPECIAL = 0xb7;
    public static final int INVOKESTATIC = 0xb8;
    public static final int INVOKEVIRTUAL = 0xb6;
    public static final int IOR = 0x80;
    public static final int IREM = 0x70;
    public static final int IRETURN = 0xac;
    public static final int ISHL = 0x78;
    public static final int ISHR = 0x7a;
    public static final int ISTORE = 0x36;
    public static final int ISTORE_0 = 0x3b;
    public static final int ISTORE_1 = 0x3c;
    public static final int ISTORE_2 = 0x3d;
    public static final int ISTORE_3 = 0x3e;
    public static final int ISUB = 0x64;
    public static final int IUSHR = 0x7c;
    public static final int IXOR = 0x82;
    public static final int JSR = 0xa8;
    public static final int JSR_W = 0xc9;
    public static final int L2D = 0x8a;
    public static final int L2F = 0x89;
    public static final int L2I = 0x88;
    public static final int LADD = 0x61;
    public static final int LALOAD = 0x2f;
    public static final int LAND = 0x7f;
    public static final int LASTORE = 0x50;
    public static final int LCMP = 0x94;
    public static final int LCONST_0 = 0x9;
    public static final int LCONST_1 = 0xa;
    public static final int LDC = 0x12;
    public static final int LDC_W = 0x13;
    public static final int LDC2_W = 0x14;
    public static final int LDIV = 0x6d;
    public static final int LLOAD = 0x16;
    public static final int LLOAD_0 = 0x1e;
    public static final int LLOAD_1 = 0x1f;
    public static final int LLOAD_2 = 0x20;
    public static final int LLOAD_3 = 0x21;
    public static final int LMUL = 0x69;
    public static final int LNEG = 0x75;
    public static final int LOOKUPSWITCH = 0xab;
    public static final int LOR = 0x81;
    public static final int LREM = 0x71;
    public static final int LRETURN = 0xad;
    public static final int LSHL = 0x79;
    public static final int LSHR = 0x7b;
    public static final int LSTORE = 0x37;
    public static final int LSTORE_0 = 0x3f;
    public static final int LSTORE_1 = 0x40;
    public static final int LSTORE_2 = 0x41;
    public static final int LSTORE_3 = 0x42;
    public static final int LSUB = 0x65;
    public static final int LUSHR = 0x7d;
    public static final int LXOR = 0x83;
    public static final int MONITORENTER = 0xc2;
    public static final int MONITOREXIT = 0xc3;
    public static final int MULTIANEWARRAY = 0xc5;
    public static final int NEW = 0xbb;
    public static final int NEWARRAY = 0xbc;
    public static final int NOP = 0x0;
    public static final int POP = 0x57;
    public static final int POP2 = 0x58;
    public static final int PUTFIELD = 0xb5;
    public static final int PUTSTATIC = 0xb3;
    public static final int RET = 0xa9;
    public static final int RETURN = 0xb1;
    public static final int SALOAD = 0x35;
    public static final int SASTORE = 0x56;
    public static final int SIPUSH = 0x11;
    public static final int SWAP = 0x5f;
    public static final int TABLESWITCH = 0xaa;
    public static final int WIDE = 0xc4;

    /**
     * <p>The tag of a ConstantValue if it is of type <code>ConstantClass</code></p>
     */
    public static final int CONSTANT_Class = 7;
    /**
     * <p>The tag of a ConstantValue if it is of type <code>ConstantFieldref</code></p>
     */
    public static final int CONSTANT_Fieldref = 9;
    /**
     * <p>The tag of a ConstantValue if it is of type <code>ConstantMethodref</code></p>
     */
    public static final int CONSTANT_Methodref = 10;
    /**
     * <p>The tag of a ConstantValue if it is of type <code>ConstantInterfaceMethodref</code></p>
     */
    public static final int CONSTANT_InterfaceMethodref = 11;
    /**
     * <p>The tag of a ConstantValue if it is of type <code>ConstantString</code></p>
     */
    public static final int CONSTANT_String = 8;
    /**
     * <p>The tag of a ConstantValue if it is of type <code>ConstantInteger</code></p>
     */
    public static final int CONSTANT_Integer = 3;
    /**
     * <p>The tag of a ConstantValue if it is of type <code>ConstantFloat</code></p>
     */
    public static final int CONSTANT_Float = 4;
    /**
     * <p>The tag of a ConstantValue if it is of type <code>ConstantLong</code></p>
     */
    public static final int CONSTANT_Long = 5;
    /**
     * <p>The tag of a ConstantValue if it is of type <code>ConstantDouble</code></p>
     */
    public static final int CONSTANT_Double = 6;
    /**
     * <p>The tag of a ConstantValue if it is of type <code>ConstantNameAndType</code></p>
     */
    public static final int CONSTANT_NameAndType = 12;
    /**
     * <p>The tag of a ConstantValue if it is of type <code>ConstantUtf8</code></p>
     */
    public static final int CONSTANT_Utf8 = 1;

    /**
     * <p>Used in <code>NEWARRAY</code> to specify that the array is of type <code>boolean</code></p>
     */
    public static final int T_BOOLEAN = 4;
    /**
     * <p>Used in <code>NEWARRAY</code> to specify that the array is of type <code>char</code></p>
     */
    public static final int T_CHAR = 5;
    /**
     * <p>Used in <code>NEWARRAY</code> to specify that the array is of type <code>float</code></p>
     */
    public static final int T_FLOAT = 6;
    /**
     * <p>Used in <code>NEWARRAY</code> to specify that the array is of type <code>double</code></p>
     */
    public static final int T_DOUBLE = 7;
    /**
     * <p>Used in <code>NEWARRAY</code> to specify that the array is of type <code>byte</code></p>
     */
    public static final int T_BYTE = 8;
    /**
     * <p>Used in <code>NEWARRAY</code> to specify that the array is of type <code>short</code></p>
     */
    public static final int T_SHORT = 9;
    /**
     * <p>Used in <code>NEWARRAY</code> to specify that the array is of type <code>int</code></p>
     */
    public static final int T_INT = 10;
    /**
     * <p>Used in <code>NEWARRAY</code> to specify that the array is of type <code>long</code></p>
     */
    public static final int T_LONG = 11;
}