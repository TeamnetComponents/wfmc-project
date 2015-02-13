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

package org.wfmc.impl.repository;

import org.xml.sax.EntityResolver;

import java.util.Arrays;

/**
 * Metadata class to configure a workflow event listener.
 *
 * @author Adrian Price
 */
public class WorkflowListenerMetaData extends ImplClassMetaData {
    private WorkflowEventSubscription[] _subscription;

    public WorkflowListenerMetaData() {
    }

    public WorkflowListenerMetaData(String id, String displayName,
        String description, String docUrl, String author, boolean threadsafe,
        String implClass, String[] implCtorSig,
        WorkflowEventSubscription[] subscription) {

        super(id, displayName, description, docUrl, author, threadsafe,
            implClass, implCtorSig);
        _subscription = subscription;
    }

    public WorkflowEventSubscription[] getSubscription() {
        return _subscription != null ? _subscription
            : _type == null || !allowInheritance ? null
            : ((WorkflowListenerMetaData)_type).getSubscription();
    }

    public WorkflowEventSubscription getSubscription(int i) {
        return getSubscription()[i];
    }

    public void setSubscription(int i, WorkflowEventSubscription subscription) {
        _subscription[i] = subscription;
    }

    public void setSubscription(WorkflowEventSubscription[] subscription) {
        _subscription = subscription;
    }

    public Object createInstance(EntityResolver entityResolver)
        throws RepositoryException {

        return createInstance(EMPTY_ARGS);
    }

    public String toString() {
        return "WorkflowListenerMetaData[id='" + _id +
            "', implClass='" + getImplClass() +
            "', implCtorSig=" +
            (getImplCtorSig() == null ? null
                : "length:" + getImplCtorSig().length +
                Arrays.asList(getImplCtorSig())) +
            ", displayName='" + _displayName +
            "', description='" + _description +
            "', docUrl='" + _docUrl +
            "', author='" + _author +
            "', threadsafe=" + _threadsafe +
            "', subscription=" + (_subscription == null ? null
            : "length:" + _subscription.length + Arrays.asList(_subscription)) +
            ",_type=" + _type +
            ']';
    }
}