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

package org.wfmc.xpdl.model.ext;

/**
 * Describes a FOR_EACH iterator loop.
 *
 * @author Adrian Price
 */
public final class ForEach extends LoopBody {
    private static final long serialVersionUID = -4577209367329809689L;

    private String _dataField;
    private String _expr;

    /**
     * Constructs a ForEach loop body.
     */
    public ForEach() {
    }

    /**
     * Constructs a ForEach loop body.
     *
     * @param dataField The data field name.
     * @param expr      The collection expression.
     */
    public ForEach(String dataField, String expr) {
        _dataField = dataField;
        _expr = expr;
    }

    /**
     * Returns the name of the data field with which to iterate.
     *
     * @return Data field name.
     */
    public String getDataField() {
        return _dataField;
    }

    /**
     * Sets the name of the data field with which to iterate.
     *
     * @param dataField The data field name.
     */
    public void setDataField(String dataField) {
        _dataField = dataField;
    }

    /**
     * Returns an expression that defines the collection over which to iterate.
     *
     * @return The collection expression.
     */
    public String getExpr() {
        return _expr;
    }

    /**
     * Sets the expression that defines the collection over which to iterate.
     * The expression must yield an array, a collection or a map when evaluated.
     *
     * @param expr The collection expression.
     */
    public void setExpr(String expr) {
        _expr = expr;
    }

    public int getType() {
        return FOR_EACH;
    }

    public String toString() {
        return "ForEach[dataField=" + _dataField +
            ", expr=" + _expr +
            ']';
    }
}