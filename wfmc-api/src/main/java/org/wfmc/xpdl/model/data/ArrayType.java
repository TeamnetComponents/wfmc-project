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
 * Data type which describes an array.  All items in the array must be of the
 * same type.
 *
 * @author Adrian Price
 */
public final class ArrayType extends MultiValuedType {
    private static final long serialVersionUID = -3322164316372325857L;

    private int _lowerIndex;
    private int _upperIndex;

    public ArrayType() {
    }

    /**
     * Construct a new ArrayType object.
     *
     * @param type       The array data type
     * @param lowerIndex The lower index of the array
     * @param upperIndex The upper index of the array
     */
    public ArrayType(Type type, String lowerIndex, String upperIndex) {
        this(type, Integer.parseInt(lowerIndex), Integer.parseInt(upperIndex));
    }

    /**
     * Construct a new ArrayType object.
     *
     * @param type       The array data type
     * @param lowerIndex The lower index of the array
     * @param upperIndex The upper index of the array
     */
    public ArrayType(Type type, int lowerIndex, int upperIndex) {
        super(type);
        _lowerIndex = lowerIndex;
        _upperIndex = upperIndex;
    }

    public int value() {
        return ARRAY_TYPE;
    }

    public int getLowerIndex() {
        return _lowerIndex;
    }

    public void setLowerIndex(int lowerIndex) {
        _lowerIndex = lowerIndex;
    }

    public void setLowerIndex(String lowerIndex) {
        if (lowerIndex != null)
            setLowerIndex(Integer.parseInt(lowerIndex));
    }

    public int getUpperIndex() {
        return _upperIndex;
    }

    public void setUpperIndex(int upperIndex) {
        _upperIndex = upperIndex;
    }

    public void setUpperIndex(String upperIndex) {
        if (upperIndex != null)
            setUpperIndex(Integer.parseInt(upperIndex));
    }

    public boolean isAssignableFrom(Type fromType) {
        if (!(fromType instanceof ArrayType))
            return false;
        ArrayType that = (ArrayType)fromType;
        int thislen = _upperIndex - _lowerIndex;
        int thatlen = that._upperIndex - that._lowerIndex;
        return _type.isAssignableFrom(that._type) &&
            thislen == thatlen;
    }

    public String toString() {
        return "ArrayType[type=" + _type + ", lowerIndex=" + _lowerIndex +
            ", upperIndex=" + _upperIndex + ']';
    }
}