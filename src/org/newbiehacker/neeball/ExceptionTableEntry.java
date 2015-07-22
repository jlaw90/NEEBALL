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
 * Date: 10-Feb-2007
 * Time: 16:10:05
 * Modification and redistribution without explicit permission by the creator(s) is prohibited
 * This source may be modified for personal use as long as the original author is accredited
 */
public final class ExceptionTableEntry {
    /**
     * <p>The start index that the JVM will be monitoring for an exception that can be handled by this</p>
     */
    public int start_index;
    int start_pc;
    /**
     * <p>The end index that the JVM will be monitoring to handle any exceptions that are the type specified by this entry</p>
     */
    public int end_index;
    int end_pc;
    /**
     * <p>The index of the instruction that this exception handler starts at</p>
     */
    public int handler_index;
    int handler_pc;
    /**
     * <p>The fully qualified name of the exception that this entry will handle</p>
     */
    public String catch_type;
    int catch_type_index;

    ExceptionTableEntry() {
    }

    /**
     * <p>Constructs a new <code>ExceptionTableEntry</code> with the specified start index, end index, handler index and catch type</p>
     *
     * @param start_index   the start index that this entry can handle from
     * @param end_index     the end index that this entry can handle to
     * @param handler_index the handler index that the JVM will jump to if an exception is caught between <code>start_index</code> and <code>end_index</code>
     * @param catch_type    the fully qualified class name of this exception that this entry can handle
     */
    public ExceptionTableEntry(int start_index, int end_index, int handler_index, String catch_type) {
        this.start_index = start_index;
        this.end_index = end_index;
        this.handler_index = handler_index;
        this.catch_type = catch_type;
    }

    public String toString() {
        return "ExceptionTableEntry [start_index=" + start_index + ", end_index=" + end_index + ", handler_index=" + handler_index + ", catch=" + catch_type + "]";
    }

    protected void finalize() throws Throwable {
        super.finalize();
        catch_type = null;
    }
}