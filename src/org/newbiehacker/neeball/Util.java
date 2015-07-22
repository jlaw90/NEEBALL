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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Copyright 2006 James Lawrence
 * Date: 11-Feb-2007
 * Time: 07:12:44
 * Modification and redistribution without explicit permission by the creator(s) is prohibited
 * This source may be modified for personal use as long as the original author is accredited
 */
public final class Util {
    private Util() {}

    // Cache counts of interfaces
    private static HashMap<String, Integer> countMap;

    static int getParamCount(String descriptor) {
        String n = descriptor.substring(1, descriptor.indexOf(')'));
        if (countMap.containsKey(descriptor))
            return countMap.get(descriptor);
        int count = 1;
        char[] chars = n.toCharArray();
        boolean arrayObj;
        for(int i = chars.length - 1; i >= 0; i--) {
            arrayObj = false;
            while(chars[i] == ']') {
                arrayObj = true;
                --i;
            }
            if(chars[i] == ';') {
                while(chars[i] != 'L')
                    --i;
            }
            if(!arrayObj && i > 0 && (chars[i] == 'J' || chars[i] == 'D'))
                count += 2;
            else
                ++count;
        }
        countMap.put(descriptor, count);
        return count;
    }

    static int getMethodStackUse(String descriptor) {
        String retVal = descriptor.substring(descriptor.indexOf(')') + 1);
        int i = -getParamCount(descriptor) + 2;
        if(retVal.equals("J") || retVal.equals("D"))
            ++i;
        return i;
    }

    public static String getFriendlyMethodString(String name, String descriptor) {
        descriptor = descriptor.substring(1);
        String ret = getFriendlyDescriptorString(descriptor.substring(descriptor.indexOf(')') + 1)) + " ";
        descriptor = descriptor.substring(0, descriptor.indexOf(')'));
        ret += name + "(";
        ArrayList<String> params = new ArrayList<String>();
        String next = "";
        boolean readingDims = false;
        for (int off = 0; off < descriptor.length(); off++) {
            char c = descriptor.charAt(off);
            if (readingDims && c != '[')
                readingDims = false;
            next += c;
            if (c == 'L') {
                while (descriptor.charAt(++off) != ';')
                    next += descriptor.charAt(off);
                String s = getFriendlyDescriptorString(next + ";");
                params.add(s);
                next = "";
            } else if (c == '[') {
                readingDims = true;
            } else {
                String s = getFriendlyDescriptorString(next);
                params.add(s);
                next = "";
            }
        }
        for (int i = 0; i < params.size(); i++)
            ret += params.get(i) + (i + 1 >= params.size() ? "" : ", ");
        return ret += ")";
    }

    public static String getMethodReturnType(String descriptor) {
        return getFriendlyDescriptorString(descriptor.substring(descriptor.indexOf(')') + 1));
    }

    public static String getMethodParams(String descriptor) {
        descriptor = descriptor.substring(1, descriptor.indexOf(')'));
        ArrayList<String> params = new ArrayList<String>();
        HashMap<String, Integer> paramCount = new HashMap<String, Integer>();
        String next = "";
        boolean readingDims = false;
        for (int off = 0; off < descriptor.length(); off++) {
            char c = descriptor.charAt(off);
            if (readingDims && c != '[')
                readingDims = false;
            next += c;
            if (c == 'L') {
                while (descriptor.charAt(++off) != ';')
                    next += descriptor.charAt(off);
                String s = getFriendlyDescriptorString(next + ";");
                String var_name = s.toLowerCase().replace("[]", "Array");
                if (var_name.indexOf('.') > 0)
                    var_name = var_name.substring(var_name.lastIndexOf('.') + 1);
                if (paramCount.containsKey(var_name)) {
                    int i = paramCount.get(var_name);
                    paramCount.put(var_name, paramCount.get(var_name) + 1);
                    var_name += String.valueOf(i);
                } else {
                    paramCount.put(var_name, 1);
                }
                params.add(s + " " + var_name);
                next = "";
            } else if (c == '[') {
                readingDims = true;
            } else {
                String s = getFriendlyDescriptorString(next);
                String var_name = s.replace("[]", "Array").replace("byte", "b").replace("short", "s").replace("int", "i").replace("float", "f").
                        replace("long", "l").replace("double", "d").replace("boolean", "flag");
                if (paramCount.containsKey(var_name)) {
                    int i = paramCount.get(var_name);
                    paramCount.put(var_name, paramCount.get(var_name) + 1);
                    var_name += String.valueOf(i);
                } else {
                    paramCount.put(var_name, 1);
                }
                params.add(s + " " + var_name);
                next = "";
            }
        }
        String ret = "";
        for (int i = 0; i < params.size(); i++)
            ret += params.get(i) + (i + 1 >= params.size() ? "" : ", ");
        return ret;
    }

    public static String getFriendlyDescriptorString(String descriptor) {
        int dimensions = 0;
        while (descriptor.toCharArray()[0] == '[') {
            dimensions++;
            descriptor = descriptor.substring(1);
        }

        char c = descriptor.toCharArray()[0];
        String ret = "";
        if (c == 'B')
            ret = "byte";
        else if (c == 'C')
            ret = "char";
        else if (c == 'D')
            ret = "double";
        else if (c == 'F')
            ret = "float";
        else if (c == 'I')
            ret = "int";
        else if (c == 'J')
            ret = "long";
        else if (c == 'S')
            ret = "short";
        else if (c == 'Z')
            ret = "boolean";
        else if (c == 'L') {
            ret = descriptor.substring(1, descriptor.indexOf(';')).replace('/', '.');
        } else if (c == 'V')
            ret = "void";
        for (int i = 0; i < dimensions; i++)
            ret += "[]";
        return ret;
    }

    static {
        countMap = new HashMap<String, Integer>();
    }
}