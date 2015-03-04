/*--

 Copyright (C) 2002-2005 Adrian Price.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions, and the disclaimer that follows
    these conditions in the documentation and/or other materials
    provided with the distribution.

 3. The names "OBE" and "Open Business Engine" must not be used to
 	endorse or promote products derived from this software without prior
 	written permission.  For written permission, please contact
 	adrianprice@sourceforge.net.

 4. Products derived from this software may not be called "OBE" or
 	"Open Business Engine", nor may "OBE" or "Open Business Engine"
 	appear in their name, without prior written permission from
 	Adrian Price (adrianprice@users.sourceforge.net).

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR(S) BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.

 For more information on OBE, please see
 <http://obe.sourceforge.net/>.

 */

package org.wfmc.util;

import java.io.Serializable;
import java.util.List;

/**
 * Represents an enumerated type as described by JDK 1.5.  Implementing classes
 * are expected to provide the following methods:
 * <p/>
 * <code>
 * public static final List&lt;EnumType&gt; VALUES;<br/>
 * public static &lt;EnumType&gt; valueOf(String s);<br/>
 * public static &lt;EnumType&gt; valueOf(int i);<br/>
 * public final List&lt;EnumType&gt; family();<br/>
 * public final String compareTo(Object o);<br/>
 * public final String equals(Object o);<br/>
 * public final int hashcode();<br/>
 * public String toString();<br/>
 * protected final Object clone(Object o) throws CloneNotSupportedException<br/>
 * protected final Object readResolve() throws ObjectStreamException;<br/>
 * <code>
 *
 * @author Adrian Price
 */
public interface Enum extends Comparable, Serializable {
    /**
     * Returns the ordinal value.
     *
     * @return Ordinal value.
     */
    int value();

    /**
     * Returns an immutable list of the members of the enumerated type.
     *
     * @return The family of allowed values.
     */
    List family();
}