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

package org.wfmc.xpdl.model.transition;

import org.wfmc.util.AbstractBean;
import org.wfmc.xpdl.model.XPDLProperties;

import java.beans.PropertyVetoException;

/**
 * A split represents a location in a workflow process where execution can split
 * or change paths based on the outcome of a condition.  Two types of splits are
 * supported: AND and XOR.  AND splits result in multiple threads executing at
 * the same time.  XOR splits result in a continuation of the current thread,
 * albeit on one of several possible paths.  Non-exclusive splits are also
 * possible by using an AND split in conjuction with conditions.
 *
 * @author Adrian Price
 */
public final class Split extends AbstractBean {
    private static final long serialVersionUID = 7654247142688505300L;
    public static final String TRANSITION_REFERENCE =
        XPDLProperties.TRANSITION_REFERENCE;
    public static final String TYPE = XPDLProperties.TYPE;
    private static final String[] EMPTY_TRANS_REF = {};
    private static final String[] _indexedPropertyNames =
        {TRANSITION_REFERENCE};
    private static final Object[] _indexedPropertyValues = {EMPTY_TRANS_REF};
    private static final int TRANSITION_REFERENCE_IDX = 0;

    private SplitType _type;
    private String[] _transitionReference = EMPTY_TRANS_REF;

    /**
     * Construct a new Split.
     */
    public Split() {
        super(_indexedPropertyNames, _indexedPropertyValues);
    }

    /**
     * Gets the split type.
     *
     * @return The split type
     */
    public SplitType getType() {
        return _type;
    }

    /**
     * Sets the split type.
     *
     * @param type The split type
     */
    public void setType(SplitType type) {
        _type = type;
    }

    public void add(String transitionReference) throws PropertyVetoException {
        _transitionReference = (String[])add(TRANSITION_REFERENCE_IDX,
            transitionReference);
    }

    public void remove(String transitionReference)
        throws PropertyVetoException {

        _transitionReference = (String[])remove(TRANSITION_REFERENCE_IDX,
            transitionReference);
    }

    /**
     * Get a list of transition references.  Transition references are used in
     * XOR splits to determine which transitions are part of the possible
     * threads.
     *
     * @return The transition references
     */
    public String[] getTransitionReference() {
        return (String[])get(TRANSITION_REFERENCE_IDX);
    }

    public String getTransitionReference(int i) {
        return _transitionReference[i];
    }

    public void setTransitionReference(String[] transitionReferences)
        throws PropertyVetoException {

        set(TRANSITION_REFERENCE_IDX,
            _transitionReference = transitionReferences == null
                ? EMPTY_TRANS_REF : transitionReferences);
    }

    public void setTransitionReference(int i, String transitionReference)
        throws PropertyVetoException {

        set(TRANSITION_REFERENCE_IDX, i, transitionReference);
    }

    public String toString() {
        return "Split[type=" + _type +
            ", transitionReference=" + _transitionReference +
            ']';
    }
}