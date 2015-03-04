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

package org.wfmc.xpdl.model.pkg;

import org.wfmc.xpdl.PackageVisitor;
import org.wfmc.xpdl.model.XPDLProperties;
import org.wfmc.xpdl.model.data.TypeDeclaration;
import org.wfmc.xpdl.model.misc.ConformanceClass;
import org.wfmc.xpdl.model.misc.ResourceContainer;
import org.wfmc.xpdl.model.misc.Script;
import org.wfmc.xpdl.model.workflow.WorkflowProcess;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

/**
 * A XPDLPackage is the top-level element in an XPDL document.
 *
 * @author Adrian Price
 */
public final class XPDLPackage extends ResourceContainer {
    private static final long serialVersionUID = -6038418003633747926L;
    private static final ExternalPackage[] EMPTY_EXT_PKG = {};
    private static final TypeDeclaration[] EMPTY_TYPE_DECL = {};
    private static final WorkflowProcess[] EMPTY_WF_PROC = {};
    public static final String COMPLETION_STRATEGY =
        XPDLProperties.COMPLETION_STRATEGY;
    public static final String CONFORMANCE_CLASS =
        XPDLProperties.CONFORMANCE_CLASS;
    public static final String EXTERNAL_PACKAGE =
        XPDLProperties.EXTERNAL_PACKAGE;
    public static final String NAMESPACES = XPDLProperties.NAMESPACES;
    public static final String PACKAGE_HEADER = XPDLProperties.PACKAGE_HEADER;
    public static final String PACKAGE_ID = XPDLProperties.PACKAGE_ID;
    public static final String SCRIPT = XPDLProperties.SCRIPT;
    public static final String TYPE_DECLARATION =
        XPDLProperties.TYPE_DECLARATION;
    public static final String WORKFLOW_PROCESS =
        XPDLProperties.WORKFLOW_PROCESS;
    private static final String[] _indexedPropertyNames = {
        APPLICATION, DATA_FIELD,
        PARTICIPANT, EXTERNAL_PACKAGE,
        TYPE_DECLARATION, WORKFLOW_PROCESS
    };
    private static final Object[] _indexedPropertyValues = {
        EMPTY_APPLICATION, EMPTY_DATA_FIELD, EMPTY_PARTICIPANT, EMPTY_EXT_PKG,
        EMPTY_TYPE_DECL, EMPTY_WF_PROC
    };
    private static final int EXTERNAL_PACKAGE_IDX = 3;
    private static final int TYPE_DECLARATION_IDX = 4;
    private static final int WORKFLOW_PROCESS_IDX = 5;

    private String _completionStrategy;
    private ConformanceClass _conformanceClass;
    private ExternalPackage[] _externalPackage = EMPTY_EXT_PKG;
    private PackageHeader _packageHeader;
    private Script _script;
    private TypeDeclaration[] _typeDeclaration = EMPTY_TYPE_DECL;
    private WorkflowProcess[] _workflowProcess = EMPTY_WF_PROC;
    private final Map _namespaces = new HashMap();

    public XPDLPackage() {
        super(_indexedPropertyNames, _indexedPropertyValues);
        setPackageHeader(new PackageHeader());
    }

    /**
     * Construct a new XPDLPackage.
     *
     * @param id            The package ID
     * @param name          The package name
     * @param packageHeader The PackageHeader object
     */
    public XPDLPackage(String id, String name, PackageHeader packageHeader) {
        super(_indexedPropertyNames, _indexedPropertyValues, id, name);

        setPackageHeader(packageHeader);
    }

    public void accept(PackageVisitor visitor) {
        visitor.visit(this);
        super.accept(visitor);
        for (int i = 0; i < _externalPackage.length; i++)
            _externalPackage[i].accept(visitor);
        for (int i = 0; i < _typeDeclaration.length; i++)
            _typeDeclaration[i].accept(visitor);
        for (int i = 0; i < _workflowProcess.length; i++)
            _workflowProcess[i].accept(visitor);
    }

    /**
     * Returns all XML namespace prefix:URI mappings declared on the Package
     * element of the XPDL document.
     *
     * @return XML namespace prefix:URI mappings.
     */
    public Map getNamespaces() {
        return _namespaces;
    }

    /**
     * Synonym for {@link #getId()}.  Required for compatibility between
     * in-memory and EJB process repositories.
     *
     * @return the package ID.
     */
    public String getPackageId() {
        return getId();
    }

    public String getCompletionStrategy() {
        return _completionStrategy;
    }

    public void setCompletionStrategy(String completionStrategy) {
        _completionStrategy = completionStrategy;
    }

    /**
     * Get the ConformanceClass.
     *
     * @return The ConformanceClass
     */
    public ConformanceClass getConformanceClass() {
        return _conformanceClass;
    }

    /**
     * Set the ConformanceClass.
     *
     * @param conformanceClass
     */
    public void setConformanceClass(ConformanceClass conformanceClass) {
        _conformanceClass = conformanceClass;
    }

    public void add(ExternalPackage extPkg) throws PropertyVetoException {
        _externalPackage = (ExternalPackage[])add(EXTERNAL_PACKAGE_IDX, extPkg);
    }

    public void remove(ExternalPackage extPkg) throws PropertyVetoException {
        _externalPackage = (ExternalPackage[])remove(EXTERNAL_PACKAGE_IDX,
            extPkg);
    }

    public ExternalPackage[] getExternalPackage() {
        return (ExternalPackage[])get(EXTERNAL_PACKAGE_IDX);
    }

    public ExternalPackage getExternalPackage(int i) {
        return _externalPackage[i];
    }

    public ExternalPackage getExternalPackage(String id) {
        if (_externalPackage != null) {
            for (int i = 0; i < _externalPackage.length; i++) {
                ExternalPackage pkg = _externalPackage[i];
                if (pkg.getPackage().getId().equals(id))
                    return pkg;
            }
        }
        return null;
    }

    public void setExternalPackage(ExternalPackage[] externalPackages)
        throws PropertyVetoException {

        set(EXTERNAL_PACKAGE_IDX, _externalPackage = externalPackages == null
            ? EMPTY_EXT_PKG : externalPackages);
    }

    public void setExternalPackage(int i, ExternalPackage externalPackage)
        throws PropertyVetoException {

        set(EXTERNAL_PACKAGE_IDX, i, externalPackage);
    }

    /**
     * Get the PackageHeader.
     *
     * @return The PackageHeader
     */
    public PackageHeader getPackageHeader() {
        return _packageHeader;
    }

    /**
     * Set the PackageHeader.  This method will throw an
     * IllegalArgumentException if the argument is null.
     *
     * @param packageHeader The new package header
     */
    public void setPackageHeader(PackageHeader packageHeader) {
        if (packageHeader == null)
            throw new IllegalArgumentException("Package header cannot be null");

        _packageHeader = packageHeader;
    }

    /**
     * Get an object defining the scripting language to use for expressions.
     *
     * @return The Script
     */
    public Script getScript() {
        return _script;
    }

    /**
     * Set the script language for expressions.
     *
     * @param script The new script language
     */
    public void setScript(Script script) {
        _script = script;
    }

    public void add(TypeDeclaration typeDecl) throws PropertyVetoException {
        _typeDeclaration = (TypeDeclaration[])add(TYPE_DECLARATION_IDX,
            typeDecl);
    }

    public void remove(TypeDeclaration typeDecl) throws PropertyVetoException {
        _typeDeclaration = (TypeDeclaration[])remove(TYPE_DECLARATION_IDX,
            typeDecl);
    }

    public TypeDeclaration[] getTypeDeclaration() {
        return (TypeDeclaration[])get(TYPE_DECLARATION_IDX);
    }

    public TypeDeclaration getTypeDeclaration(int i) {
        return _typeDeclaration[i];
    }

    public TypeDeclaration getTypeDeclaration(String id) {
        if (_typeDeclaration != null) {
            for (int i = 0; i < _typeDeclaration.length; i++) {
                TypeDeclaration td = _typeDeclaration[i];
                if (td.getId().equals(id))
                    return td;
            }
        }
        return null;
    }

    public void setTypeDeclaration(TypeDeclaration[] typeDeclarations)
        throws PropertyVetoException {

        set(TYPE_DECLARATION_IDX, _typeDeclaration = typeDeclarations == null
            ? EMPTY_TYPE_DECL : typeDeclarations);
    }

    public void setTypeDeclaration(int i, TypeDeclaration typeDeclaration)
        throws PropertyVetoException {

        set(TYPE_DECLARATION_IDX, i, typeDeclaration);
    }

    public void add(WorkflowProcess workflowProcess)
        throws PropertyVetoException {

        _workflowProcess = (WorkflowProcess[])add(WORKFLOW_PROCESS_IDX,
            workflowProcess);
    }

    public void remove(WorkflowProcess workflowProcess)
        throws PropertyVetoException {

        _workflowProcess = (WorkflowProcess[])remove(WORKFLOW_PROCESS_IDX,
            workflowProcess);
    }

    public WorkflowProcess[] getWorkflowProcess() {
        return (WorkflowProcess[])get(WORKFLOW_PROCESS_IDX);
    }

    public WorkflowProcess getWorkflowProcess(int i) {
        return _workflowProcess[i];
    }

    public WorkflowProcess getWorkflowProcess(String id) {
        if (_workflowProcess != null) {
            for (int i = 0; i < _workflowProcess.length; i++) {
                WorkflowProcess wf = _workflowProcess[i];
                if (wf.getId().equals(id))
                    return wf;
            }
        }
        return null;
    }

    public void setWorkflowProcess(WorkflowProcess[] workflowProcesses)
        throws PropertyVetoException {

        set(WORKFLOW_PROCESS_IDX, _workflowProcess = workflowProcesses == null
            ? EMPTY_WF_PROC : workflowProcesses);
    }

    public void setWorkflowProcess(int i, WorkflowProcess workflowProcess)
        throws PropertyVetoException {

        set(WORKFLOW_PROCESS_IDX, i, workflowProcess);
    }

    public String toString() {
        return "XPDLPackage[packageId='" + getId() + "', name='" + getName() +
            "']";
    }
}