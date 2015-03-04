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

package org.wfmc.xpdl.model.misc;

import org.wfmc.util.AbstractBean;
import org.wfmc.xpdl.PackageVisitor;
import org.wfmc.xpdl.XPDLMessages;
import org.wfmc.xpdl.model.XPDLProperties;
import org.wfmc.xpdl.model.data.ActualParameter;

import java.beans.PropertyVetoException;

/**
 * Describes the invocation interface for a service.
 *
 * @author Adrian Price
 */
public abstract class Invocation extends AbstractBean {
    public static final String ACTUAL_PARAMETER =
        XPDLProperties.ACTUAL_PARAMETER;
    public static final String ID = XPDLProperties.ID;
    private static final ActualParameter[] EMPTY_ACTUAL_PARM = {};
    private static final String[] _indexedPropertyNames = {ACTUAL_PARAMETER};
    private static final Object[] _indexedPropertyValues = {EMPTY_ACTUAL_PARM};
    private static final int ACTUAL_PARAMETER_IDX = 0;

    protected String _id;
    protected ActualParameter[] _actualParameter = EMPTY_ACTUAL_PARM;

    protected Invocation() {
        super(_indexedPropertyNames, _indexedPropertyValues);
    }

    protected Invocation(String id) {
        super(_indexedPropertyNames, _indexedPropertyValues);
        _id = id;
    }

    protected void accept(PackageVisitor visitor) {
        for (int i = 0; i < _actualParameter.length; i++)
            _actualParameter[i].accept(visitor);
    }

    /**
     * Gets the object's XPDL ID.
     *
     * @return The XPDL ID.
     */
    public final String getId() {
        return _id;
    }

    /**
     * Set the object's XPDL ID.
     *
     * @param id The new ID
     */
    public final void setId(String id) {
        if (id == null)
            throw new IllegalArgumentException(XPDLMessages.ID_CANNOT_BE_NULL);
        _id = id;
    }

    public final void add(ActualParameter actualParameter)
        throws PropertyVetoException {

        _actualParameter = (ActualParameter[])add(ACTUAL_PARAMETER_IDX,
            actualParameter);
    }

    public final void remove(ActualParameter actualParameter)
        throws PropertyVetoException {

        _actualParameter = (ActualParameter[])remove(ACTUAL_PARAMETER_IDX,
            actualParameter);
    }

    /**
     * Get a list of actual parameters.  Actual parameters are just Strings
     * which are expressions or names of fields in the workflow relevant data.
     *
     * @return A List of actual parameters
     */
    public final ActualParameter[] getActualParameter() {
        return (ActualParameter[])get(ACTUAL_PARAMETER_IDX);
    }

    public final ActualParameter getActualParameter(int i) {
        return _actualParameter[i];
    }

    public final void setActualParameter(ActualParameter[] actualParameters)
        throws PropertyVetoException {

        set(ACTUAL_PARAMETER_IDX, _actualParameter = actualParameters == null
            ? EMPTY_ACTUAL_PARM : actualParameters);
    }

    public final void setActualParameter(int i,
        ActualParameter actualParameter) throws PropertyVetoException {

        set(ACTUAL_PARAMETER_IDX, i, actualParameter);
    }
}