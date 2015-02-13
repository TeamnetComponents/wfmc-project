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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.wfmc.server.core.util.ClassUtils;
import org.wfmc.server.core.xpdl.model.data.*;
import org.xml.sax.EntityResolver;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Describes a procedure implemented by a Java class.
 *
 * @author Adrian Price
 */
public class JavaMethodMetaData extends ToolAgentMetaData {
    private static final long serialVersionUID = -285543509428840735L;
    private static final Log _logger = LogFactory.getLog(
        JavaMethodMetaData.class);
    private static final String IMPL_CLASS = "org.obe.runtime.tool.JavaMethod";
    private static final String[] IMPL_CTOR_SIG = {
        JavaMethodMetaData.class.getName()
    };
    protected String _className;
    protected String _method;
    protected String[] _methodSig;

    public JavaMethodMetaData() {
    }

    // TODO: find a way to pass the target to the execute method.
    public JavaMethodMetaData(String id, String displayName,
        String description, String docUrl, String author, String clazz,
        String method, String[] methodArgTypes) {

        super(id, displayName, description, docUrl,
            author, true);
        _className = clazz;
        _method = method;
        _methodSig = methodArgTypes;
    }

    private JavaMethodMetaData(String className, String methodSig)
        throws ClassNotFoundException, NoSuchMethodException {

        Method method = ClassUtils.findMethod(className, methodSig);
        Class[] parmTypes = method.getParameterTypes();
        _className = method.getDeclaringClass().getName();
        _methodSig = ClassUtils.namesForClasses(parmTypes);
        _formalParameters = new FormalParameter[parmTypes.length];
        for (int i = 0; i < parmTypes.length; i++) {
            Class parmType = parmTypes[i];
            // TODO: Support INOUT parameter mode via ~Holder classes.
            _formalParameters[i] = new FormalParameter("p" + i, null,
                ParameterMode.IN, DataTypes.dataTypeForClass(parmType),
                "Java parameter type is " + parmType.getName());
        }

        if (_logger.isDebugEnabled()) {
            _logger.debug("introspect: _formalParameters=" +
                _formalParameters);
        }
    }

    public ToolAgentMetaData introspect(ExternalReference extRef,
        EntityResolver entityResolver) throws RepositoryException {

        // If the location has a java: prefix or looks like a fully qualified
        // Java class name, attempt to introspect it as such.
        String location = extRef.getLocation();
        boolean javaPrefix = location.startsWith("java:");
        String className = javaPrefix ? location.substring(5) : location;
        int n = location.length();
        boolean validClassName = n != 0;
        for (int i = 0; i < n; i++) {
            char c = className.charAt(i);
            if (i != 0 && !Character.isJavaIdentifierPart(c) ||
                i == 0 && !Character.isJavaIdentifierStart(c)) {

                validClassName = false;
                break;
            }
        }

        JavaMethodMetaData metaData = null;
        if (validClassName) {
            try {
                metaData = new JavaMethodMetaData(className, extRef.getXref());
            } catch (ClassNotFoundException e) {
                throw new RepositoryException(e);
            } catch (NoSuchMethodException e) {
                throw new RepositoryException(e);
            }
        }
        return metaData;
    }

    public void init() {
        if (_methodSig == null)
            return;

        // Load the classes referenced by the method signature.
        Class[] methodSig;
        try {
            methodSig = ClassUtils.classesForNames(_methodSig);
        } catch (ClassNotFoundException e) {
            _logger.error("init() error in " + this, e);
            return;
        }

        // Since we have a method signature, we also need formal parameters.
        if (_formalParameters == null)
            _formalParameters = new FormalParameter[methodSig.length];

        // Ensure that every formal parameter has a data type.
        int len = Math.min(methodSig.length, _formalParameters.length);
        for (int i = 0; i < len; i++) {
            FormalParameter parm = _formalParameters[i];

            // Make sure we know what data type this parameter has.
            DataType dataType = parm == null ? null : parm.getDataType();
            if (dataType == null)
                dataType = DataTypes.dataTypeForClass(methodSig[i]);

            // Create any missing formal parameter.
            if (parm == null) {
                _formalParameters[i] = new FormalParameter("arg" + i, null,
                    ParameterMode.IN, dataType, null);
            } else {
                // Make sure every formal parameter has an ID and a data type.
                if (parm.getId() == null)
                    parm.setId("arg" + i);
                if (parm.getDataType() == null)
                    parm.setDataType(dataType);
            }
        }
    }

    protected String getImplClass() {
        return IMPL_CLASS;
    }

    protected String[] getImplCtorSig() {
        return IMPL_CTOR_SIG;
    }

    public String getClassName() {
        return _className != null ? _className
            : _type == null || !allowInheritance ? null :
            ((JavaMethodMetaData)_type).getClassName();
    }

    public void setClassName(String className) {
        _className = className;
    }

    public String getMethod() {
        return _method != null ? _method
            : _type == null || !allowInheritance ? null :
            ((JavaMethodMetaData)_type).getMethod();
    }

    public void setMethod(String method) {
        _method = method;
    }

    public String[] getMethodSig() {
        return _methodSig != null ? _methodSig
            : _type == null || !allowInheritance ? null :
            ((JavaMethodMetaData)_type).getMethodSig();
    }

    public void setMethodSig(String[] methodSig) {
        _methodSig = methodSig;
    }

    public String toString() {
        return "JavaMethodMetaData[id='" + _id +
            "', implClass='" + IMPL_CLASS +
            "', implCtorSig=" +
            (IMPL_CTOR_SIG == null ? null : Arrays.asList(IMPL_CTOR_SIG)) +
            ", displayName='" + _displayName +
            "', description='" + _description +
            "', docUrl='" + _docUrl +
            "', author='" + _author +
            "', threadsafe=" + _threadsafe +
            "', className=" + _className +
            "', method=" + _method +
            "', methodSig=" +
            (_methodSig == null ? null : Arrays.asList(_methodSig)) +
            ",_type=" + _type +
            ']';
    }
}