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

package org.wfmc.xpdl;


import org.wfmc.xpdl.model.activity.*;
import org.wfmc.xpdl.model.application.Application;
import org.wfmc.xpdl.model.data.ActualParameter;
import org.wfmc.xpdl.model.data.DataField;
import org.wfmc.xpdl.model.data.FormalParameter;
import org.wfmc.xpdl.model.data.TypeDeclaration;
import org.wfmc.xpdl.model.ext.Trigger;
import org.wfmc.xpdl.model.participant.Participant;
import org.wfmc.xpdl.model.pkg.ExternalPackage;
import org.wfmc.xpdl.model.pkg.XPDLPackage;
import org.wfmc.xpdl.model.transition.Transition;
import org.wfmc.xpdl.model.workflow.WorkflowProcess;

/**
* A general purpose visitor for traversing an XPDL package object graph.
*
* @author Adrian Price
*/
public abstract class PackageVisitor {
    protected PackageVisitor() {
    }

    public void visit(Activity pkg) {
    }

    public void visit(ActivitySet pkg) {
    }

    public void visit(ActualParameter actualParameter) {
    }

    public void visit(Application application) {
    }

    public void visit(DataField dataField) {
    }

    public void visit(Trigger event) {
    }

    public void visit(ExternalPackage externalPackage) {
    }

    public void visit(FormalParameter formalParameter) {
    }

    public void visit(NoImplementation noImplementation) {
    }

    public void visit(Participant participant) {
    }

    public void visit(SubFlow subFlow) {
    }

    public void visit(Tool tool) {
    }

    public void visit(ToolSet toolSet) {
    }

    public void visit(Transition transition) {
    }

    public void visit(TypeDeclaration typeDeclaration) {
    }

    public void visit(WorkflowProcess pkg) {
    }

    public void visit(XPDLPackage pkg) {
    }
}