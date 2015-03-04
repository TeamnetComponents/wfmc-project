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

package org.wfmc.xpdl.model.data;

/**
 * Standard interface implemented by any data type.
 *
 * @author Adrian Price
 */
public interface Type {
    // These ordinal data type numbers are identical to those in WMAttribute.
    /**
     * Indicates that the type is currently undefined.  This value is only
     * returned by {@link DeclaredType#value()}.
     */
    int UNDEFINED_TYPE = -2;
    /**
     * Preserves the existing type - use only when the type has already been
     * assigned.
     */
    int DEFAULT_TYPE = -1;
    // BasicTypes:
    /**
     * Basic string type.
     */
    int STRING_TYPE = 0;
    /**
     * Basic 64-bit signed double-precision type.
     */
    int FLOAT_TYPE = 1;
    /**
     * Basic 32-bit integer type.
     */
    int INTEGER_TYPE = 2;
    /**
     * @deprecated Use {@link #EXTERNAL_REFERENCE_TYPE} or {@link #SCHEMA_TYPE}.
     */
    int REFERENCE_TYPE = 3;
    /**
     * Basic date/time type.
     */
    int DATETIME_TYPE = 4;
    /**
     * Basic boolean type.
     */
    int BOOLEAN_TYPE = 5;
    /**
     * Basic performer name type (basically a string).
     */
    int PERFORMER_TYPE = 6;

    /**
     * A reference to a Package-level TypeDeclaration.
     * <em>N.B. DECLARED_TYPE must not be used directly when setting attributes.
     * It is always a reference to another type.</em>
     */
    int DECLARED_TYPE = 7;
    /**
     * An XML document whose structure is defined by an XML Schema.
     */
    int SCHEMA_TYPE = 8;
    /**
     * An object whose definition is external (e.g., as a Java class).
     */
    int EXTERNAL_REFERENCE_TYPE = 9;
    /**
     * @deprecated Use {@link #EXTERNAL_REFERENCE_TYPE} or {@link #SCHEMA_TYPE}.
     */
    int RECORD_TYPE = 10;
    /**
     * @deprecated Use {@link #EXTERNAL_REFERENCE_TYPE} or {@link #SCHEMA_TYPE}.
     */
    int UNION_TYPE = 11;
    /**
     * @deprecated Use {@link #EXTERNAL_REFERENCE_TYPE} or {@link #SCHEMA_TYPE}.
     */
    int ENUMERATION_TYPE = 12;
    /**
     * @deprecated Use {@link #EXTERNAL_REFERENCE_TYPE} or {@link #SCHEMA_TYPE}.
     */
    int ARRAY_TYPE = 13;
    /**
     * @deprecated Use {@link #EXTERNAL_REFERENCE_TYPE} or {@link #SCHEMA_TYPE}.
     */
    int LIST_TYPE = 14;

    /**
     * The data type's ordinal value.
     *
     * @return Ordinal value: {@link #STRING_TYPE}, {@link #FLOAT_TYPE},
     *         {@link #INTEGER_TYPE}, {@link #REFERENCE_TYPE}, {@link #DATETIME_TYPE},
     *         {@link #BOOLEAN_TYPE}, {@link #PERFORMER_TYPE}, {@link #DECLARED_TYPE},
     *         {@link #SCHEMA_TYPE}, {@link #EXTERNAL_REFERENCE_TYPE},
     *         {@link #RECORD_TYPE}, {@link #UNION_TYPE}, {@link #ENUMERATION_TYPE},
     *         {@link #ARRAY_TYPE}, {@link #LIST_TYPE}
     */
    int value();

    /**
     * Checks type compatibility in an assignment.
     *
     * @param fromType The type of the 'rvalue'.
     * @return <code>true</code> if a value of type <code>fromType</code> can
     *         be assigned or converted to a value of type <code>toType</code>.
     */
    boolean isAssignableFrom(Type fromType);

    /**
     * Returns the actual type implied by this type.  Some types behave as
     * references to other types.
     *
     * @return The implied type.
     */
    Type getImpliedType();
}