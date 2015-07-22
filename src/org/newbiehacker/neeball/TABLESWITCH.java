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

import java.util.Iterator;
import java.util.List;

/**
 * Copyright 2006 James Lawrence
 * Date: 12-Apr-2007
 * Time: 22:45:55
 * Modification and redistribution without explicit permission by the creator(s) is prohibited
 * This source may be modified for personal use as long as the original author is accredited
 */
public final class TABLESWITCH extends Switch {
    TABLESWITCH(int default_offset, int[] matches, int[] offsets) {
        super(default_offset, matches, offsets);
    }

    public TABLESWITCH(int default_index, List<Integer> matches, List<Integer> indices) {
        super(default_index, matches, indices);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("tableswitch ").append(branch_index).append(" {");
        Iterator it = match_pairs.keySet().iterator();
        Iterator vit = match_pairs.values().iterator();
        for (int i = 0; i < match_pairs.size(); i++)
            sb.append(it.next()).append(" => ").append(vit.next()).append(", ");
        sb.delete(sb.length() - 2, sb.length()).append("}");
        it = null;
        vit = null;
        return sb.toString();
    }

    public int getLength() {
        return 13 + match_pairs.size() * 4;
    }
}