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

import org.wfmc.util.AbstractBean;
import org.wfmc.xpdl.model.XPDLProperties;

import java.beans.PropertyVetoException;
import java.util.Arrays;

public final class EnumerationType extends AbstractBean implements Type {
    private static final long serialVersionUID = -3051895740565450802L;
    public static final String IMPLIED_TYPE = XPDLProperties.IMPLIED_TYPE;
    public static final String VALUE = XPDLProperties.VALUE;
    private static final EnumerationValue[] EMPTY_VALUE = {};
    private static final String[] _indexedPropertyNames = {VALUE};
    private static final Object[] _indexedPropertyValues = {EMPTY_VALUE};
    private static final int VALUE_IDX = 0;

    private EnumerationValue[] _value = EMPTY_VALUE;

    public EnumerationType() {
        super(_indexedPropertyNames, _indexedPropertyValues);
    }

    public void add(EnumerationValue value) throws PropertyVetoException {
        _value = (EnumerationValue[])add(VALUE_IDX, value);
    }

    public void remove(EnumerationValue value) throws PropertyVetoException {
        _value = (EnumerationValue[])remove(VALUE_IDX, value);
    }

    public EnumerationValue[] getValue() {
        return (EnumerationValue[])get(VALUE_IDX);
    }

    public EnumerationValue getValue(int i) {
        return _value[i];
    }

    public void setValue(EnumerationValue[] value)
        throws PropertyVetoException {

        set(VALUE_IDX, _value = value == null ? EMPTY_VALUE : value);
    }

    public void setValue(int i, EnumerationValue value)
        throws PropertyVetoException {

        set(VALUE_IDX, i, value);
    }

    public int value() {
        return ENUMERATION_TYPE;
    }

    public boolean isAssignableFrom(Type fromType) {
        if (!(fromType instanceof EnumerationType))
            return false;

        return Arrays.equals(_value, ((EnumerationType)fromType)._value);
    }

    public Type getImpliedType() {
        return this;
    }

    public String toString() {
        return "EnumerationType[values=" +
            (_value == null ? null : Arrays.asList(_value)) + ']';
    }
}