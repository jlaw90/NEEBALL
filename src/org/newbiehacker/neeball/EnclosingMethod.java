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
 * Date: 27-Jun-2007
 * Time: 10:42:09
 * Modification and redistribution without explicit permission by the creator(s) is prohibited
 * This source may be modified for personal use as long as the original author is accredited
 */
public final class EnclosingMethod {
    public String class_name;
    public String name;
    public String descriptor;
    int class_name_index, nat_index;

    EnclosingMethod() {
    }

    public EnclosingMethod(String class_name, String name, String descriptor) {
        this.class_name = class_name;
        this.name = name;
        this.descriptor = descriptor;
    }

    public String toString() {
        return "EnclosingMethod [class_name=" + class_name + ", name=" + name + ", descriptor=" + descriptor + "]";
    }

    protected void finalize() throws Throwable {
        super.finalize();
        class_name = null;
        name = null;
        descriptor = null;
    }
}