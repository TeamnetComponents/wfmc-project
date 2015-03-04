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

package org.wfmc.xpdl.model.application;

import org.wfmc.xpdl.PackageVisitor;
import org.wfmc.xpdl.model.XPDLProperties;
//import org.wfmc.xpdl.model.data.ExternalReference;
import org.wfmc.xpdl.model.data.FormalParameter;
import org.wfmc.xpdl.model.misc.AbstractWFElement;
import org.wfmc.xpdl.model.misc.Invokable;

import java.beans.PropertyVetoException;
import java.util.Arrays;

/**
 * Implementation of the XPDL Application element.
 *
 * @author Adrian Price
 */
public final class Application extends AbstractWFElement implements Invokable {
    private static final long serialVersionUID = -8057216217821358435L;
    public static final String EXTERNAL_REFERENCE =
        XPDLProperties.EXTERNAL_REFERENCE;
    public static final String FORMAL_PARAMETER =
        XPDLProperties.FORMAL_PARAMETER;
    private static final FormalParameter[] EMPTY_FORMAL_PARM = {};
    private static final String[] _indexedPropertyNames = {FORMAL_PARAMETER};
    private static final Object[] _indexedPropertyValues = {EMPTY_FORMAL_PARM};
    private static final int FORMAL_PARAMETER_IDX = 0;

    //private ExternalReference _externalReference;
    private FormalParameter[] _formalParameter = EMPTY_FORMAL_PARM;

    public Application() {
        super(_indexedPropertyNames, _indexedPropertyValues);
    }

    /**
     * Construct a new application.
     *
     * @param id   The application id
     * @param name The application name
     */
    public Application(String id, String name) {
        super(_indexedPropertyNames, _indexedPropertyValues, id, name);
    }

    public void accept(PackageVisitor visitor) {
        visitor.visit(this);
        for (int i = 0; i < _formalParameter.length; i++)
            _formalParameter[i].accept(visitor);
    }

    public void add(FormalParameter formalParameter)
        throws PropertyVetoException {

        _formalParameter = (FormalParameter[])add(FORMAL_PARAMETER_IDX,
            formalParameter);
    }

    public void remove(FormalParameter formalParameter)
        throws PropertyVetoException {

        _formalParameter = (FormalParameter[])remove(FORMAL_PARAMETER_IDX,
            formalParameter);
    }

    /**
     * Return a List of all FormalParameters for the tool.
     *
     * @return A List of FormalParameter objects
     */
    public FormalParameter[] getFormalParameter() {
        return (FormalParameter[])get(FORMAL_PARAMETER_IDX);
    }

    public FormalParameter getFormalParameter(int i) {
        return _formalParameter[i];
    }

    public FormalParameter getFormalParameter(String id) {
        if (_formalParameter != null) {
            for (int i = 0; i < _formalParameter.length; i++) {
                FormalParameter fp = _formalParameter[i];
                if (fp.getId().equals(id))
                    return fp;
            }
        }
        return null;
    }

    public void setFormalParameter(FormalParameter[] formalParameters)
        throws PropertyVetoException {

        set(FORMAL_PARAMETER_IDX, _formalParameter = formalParameters == null
            ? EMPTY_FORMAL_PARM : formalParameters);
    }

    public void setFormalParameter(int i, FormalParameter formalParameter)
        throws PropertyVetoException {

        set(FORMAL_PARAMETER_IDX, i, formalParameter);
    }

    /**
     * Get an ExternalReference for the tool.  This may be used if the tool is
     * accessible through a URI (for example, a web service).  This method may
     * return null if the formal parameters are specified.
     *
     * @return The ExternalReference
     */
//    public ExternalReference getExternalReference() {
//        return _externalReference;
//    }

    /**
     * Set an ExternalReference for the tool.  This may be used if the tool is
     * accessible through a URI (for example, a web service).
     *
     * @param externalReference The ExternalReference
     */
//    public void setExternalReference(ExternalReference externalReference) {
//        _externalReference = externalReference;
//        _formalParameter = null;
//    }

    public String toString() {
        return "Application[id=" + getId() +
            ", name=" + getName() +
            ", formalParameters=" +
            (_formalParameter == null ? null :
                Arrays.asList(_formalParameter)) +
            //", externalReference=" + _externalReference +
                ']';
    }
}