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

import org.wfmc.server.core.XMLException;
import org.wfmc.server.core.util.SchemaUtils;
import org.wfmc.server.core.util.W3CNames;
import org.wfmc.server.core.xpdl.model.data.*;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.wsdl.*;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap.SOAPBinding;
import javax.wsdl.extensions.soap.SOAPOperation;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Describes a tool implemented by a Web Service.
 *
 * @author Adrian Price
 */
public final class WebServiceMetaData extends ToolAgentMetaData {
    private static final long serialVersionUID = -285543509428840735L;
    private static final Log _logger = LogFactory.getLog(
        WebServiceMetaData.class);
    private static final String WSDL_OP_OVERLOADED =
        "xref=\"[service.]operation[(input, output)]\" must be specified if " +
            "operation is overloaded or offered by multiple services";
    private static final String IMPL_CLASS = "org.obe.runtime.tool.WebService";
    private static final String[] IMPL_CTOR_SIG = {
        WebServiceMetaData.class.getName()
    };

    private String _wsdlLocation;
    private boolean _authenticate;
    private String _userName;
    private String _password;
    private String _portName;
    private String _portLocation;
    private String _portTypeName;
    private String _operationStyle;
    private String _targetNamespace;
    private String _serviceName;
    private String _operationName;
    private String _soapAction;
    private Message _inputMessage = new Message();
    private Message _outputMessage = new Message();

//    private QName _serviceQName;
//    private QName _portQName;
//    private QName _operationQName;

    /**
     * No-args constructor JavaBeans compliance.
     */
    public WebServiceMetaData() {
    }

    /**
     * Constructor for dynamically defining a Web Service.
     *
     * @param id
     * @param threadsafe
     * @param wsdlLocation
     * @param authenticate
     * @param userName
     * @param password
     * @param targetNamespace
     * @param serviceName
     * @param operationName
     * @param inputMessage
     * @param outputMessage
     */
    public WebServiceMetaData(String id, boolean threadsafe,
        String wsdlLocation,
        boolean authenticate, String userName, String password,
        String targetNamespace, String serviceName, String operationName,
        String inputMessage, String outputMessage) {

        super(id, null, null, null, null, threadsafe);
        _wsdlLocation = wsdlLocation;
        _authenticate = authenticate;
        _userName = userName;
        _password = password;
        _targetNamespace = targetNamespace;
        _serviceName = serviceName;
        _operationName = operationName;
        _inputMessage.name = inputMessage;
        _outputMessage.name = outputMessage;
    }

    public String getWsdlLocation() {
        return _wsdlLocation != null ? _wsdlLocation
            : _type == null || !allowInheritance ? null :
            ((WebServiceMetaData)_type).getWsdlLocation();
    }

    public void setWsdlLocation(String wsdlLocation) {
        _wsdlLocation = wsdlLocation;
    }

    public boolean isAuthenticate() {
        return _authenticate;
    }

    public boolean getAuthenticate() {
        return _authenticate;
    }

    public void setAuthenticate(boolean authenticate) {
        _authenticate = authenticate;
    }

    public String getUserName() {
        return _userName != null ? _userName
            : _type == null || !allowInheritance ? null :
            ((WebServiceMetaData)_type).getUserName();
    }

    public void setUserName(String userName) {
        _userName = userName;
    }

    public String getPassword() {
        return _password != null ? _password
            : _type == null || !allowInheritance ? null :
            ((WebServiceMetaData)_type).getPassword();
    }

    public void setPassword(String password) {
        _password = password;
    }

    public String getTargetNamespace() {
        return _targetNamespace != null ? _targetNamespace
            : _type == null || !allowInheritance ? null :
            ((WebServiceMetaData)_type).getTargetNamespace();
    }

    public void setTargetNamespace(String targetNamespace) {
        _targetNamespace = targetNamespace;
    }

    public String getPortLocation() {
        return _portLocation != null ? _portLocation
            : _type == null || !allowInheritance ? null :
            ((WebServiceMetaData)_type).getPortLocation();
    }

    public void setPortLocation(String portLocation) {
        _portLocation = portLocation;
    }

    public String getPortName() {
        return _portName != null ? _portName
            : _type == null || !allowInheritance ? null :
            ((WebServiceMetaData)_type).getPortName();
    }

    public void setPortName(String portName) {
        _portName = portName;
    }

    public String getPortTypeName() {
        return _portTypeName != null ? _portTypeName : _type == null ||
            !allowInheritance ? null :
            ((WebServiceMetaData)_type).getPortTypeName();
    }

    public void setPortTypeName(String portTypeName) {
        _portTypeName = portTypeName;
    }

    public String getOperationStyle() {
        return _operationStyle != null ? _operationStyle
            : _type == null || !allowInheritance ? null :
            ((WebServiceMetaData)_type).getOperationStyle();
    }

    public void setOperationStyle(String operationStyle) {
        _operationStyle = operationStyle;
    }

    public String getServiceName() {
        return _serviceName != null ? _serviceName
            : _type == null || !allowInheritance ? null :
            ((WebServiceMetaData)_type).getServiceName();
    }

    public void setServiceName(String serviceName) {
        _serviceName = serviceName;
    }

    public String getOperationName() {
        return _operationName != null ? _operationName
            : _type == null || !allowInheritance ? null :
            ((WebServiceMetaData)_type).getOperationName();
    }

    public void setOperationName(String operationName) {
        _operationName = operationName;
    }

    public String getSoapAction() {
        return _soapAction != null ? _soapAction
            : _type == null || !allowInheritance ? null :
            ((WebServiceMetaData)_type).getSoapAction();
    }

    public void setSoapAction(String soapAction) {
        _soapAction = soapAction;
    }

    public Message getInputMessage() {
        return _inputMessage != null ? _inputMessage
            : _type == null || !allowInheritance ? null :
            ((WebServiceMetaData)_type).getInputMessage();
    }

    public void setInputMessage(Message inputMessage) {
        _inputMessage = inputMessage;
    }

    public Message getOutputMessage() {
        return _outputMessage != null ? _outputMessage
            : _type == null || !allowInheritance ? null :
            ((WebServiceMetaData)_type).getOutputMessage();
    }

    public void setOutputMessage(Message outputMessage) {
        _outputMessage = outputMessage;
    }

    public Object createInstance(EntityResolver entityResolver)
        throws RepositoryException {

        // If we haven't yet introspected the WSDL, we must do this before
        // instantiating a tool agent.
        if (_formalParameters == null) {
            introspect(entityResolver);
        }

        return createInstance(new Object[]{this});
    }

    protected String getImplClass() {
        return IMPL_CLASS;
    }

    protected String[] getImplCtorSig() {
        return IMPL_CTOR_SIG;
    }

    public ToolAgentMetaData introspect(ExternalReference extRef,
        EntityResolver entityResolver) throws RepositoryException {

        WebServiceMetaData metaData = null;
        String location = extRef.getLocation();
        if (location.endsWith(".wsdl") || location.endsWith("?wsdl")) {
            // Parse xref, which is of form: [service.]operation[(input, output)].
            // In order to construct a valid QName for the service, the
            // ExternalReference namespace attribute must be set correctly.
            String namespace = extRef.getNamespace();
            String service = null;
            String xref = extRef.getXref();
            String operation = xref;
            int opNameIndex = xref.indexOf('.');
            if (opNameIndex != -1) {
                service = xref.substring(0, opNameIndex);
                operation = xref.substring(opNameIndex + 1);
            }
            String inputMessage = null;
            String outputMessage = null;
            boolean inOutSpecified = operation.indexOf('(') != -1;
            if (inOutSpecified) {
                StringTokenizer strtok = new StringTokenizer(operation, "(, )");
                if (strtok.countTokens() != 3) {
                    throw new IllegalArgumentException(WSDL_OP_OVERLOADED);
                }
                operation = strtok.nextToken();
                inputMessage = strtok.nextToken();
                outputMessage = strtok.nextToken();
            }

            // Create a new meta-data object and customize it to this WSDL,
            // inheriting this template object's security settings.
            metaData = new WebServiceMetaData(extRef.toString(), _threadsafe,
                location, _authenticate, _userName, _password, namespace,
                service, operation, inputMessage, outputMessage);
            metaData.introspect(entityResolver);
        }
        return metaData;
    }

    private synchronized void introspect(EntityResolver entityResolver)
        throws RepositoryException {

        if (_logger.isDebugEnabled()) {
            _logger.debug("introspect: id=" + getId());
        }

        InputSource in = null;
        try {
            // Load and parse the WSDL.
            in = entityResolver.resolveEntity(null, _wsdlLocation);
            WSDLFactory factory = WSDLFactory.newInstance();
            WSDLReader reader = factory.newWSDLReader();
            Definition definition = reader.readWSDL(_wsdlLocation, in);

            // Search the WSDL for a port that specifies a SOAP address, and
            // which is bound to a port type that supports the required
            // operation.  If more than one service offers the operation, the
            // operation name must be fully qualified in the xref thus:
            // [service.]operation[(inputMessage, outputMessage)]
            _portLocation = null;
            _portTypeName = null;
            QName svcQName = _serviceName == null
                ? null : new QName(_targetNamespace, _serviceName);
            boolean ioSpecified = _inputMessage.name != null &&
                _outputMessage.name != null;
            Operation operation = null;
            boolean operationFound = false;
            Map services = definition.getServices();
            for (Iterator iter = services.entrySet().iterator();
                iter.hasNext();) {
                Map.Entry entry = (Map.Entry)iter.next();
                Service svc = (Service)entry.getValue();

                // If we know the service name, only proceed if it matches.
                if (svcQName != null && !svcQName.equals(svc.getQName())) {
                    continue;
                }

//                _serviceQName = svc.getQName();

                // Look for a port that has a SOAP address.
                Map ports = svc.getPorts();
                for (Iterator iter2 = ports.values().iterator();
                    iter2.hasNext();) {
                    Port port = (Port)iter2.next();
                    List portEE = port.getExtensibilityElements();
                    String portLoc = null;
                    for (int k = 0; k < portEE.size(); k++) {
                        ExtensibilityElement ee = (ExtensibilityElement)
                            portEE.get(k);
                        if (ee instanceof SOAPAddress) {
                            portLoc = ((SOAPAddress)ee).getLocationURI();
                            break;
                        }
                    }
                    if (portLoc != null) {
                        Binding binding = port.getBinding();
                        List bindingEE = binding.getExtensibilityElements();
                        for (int k = 0; k < bindingEE.size(); k++) {
                            ExtensibilityElement ee = (ExtensibilityElement)
                                bindingEE.get(k);
                            if (ee instanceof SOAPBinding) {
                                _operationStyle = ((SOAPBinding)ee).getStyle();
                                break;
                            }
                        }
                        PortType pType = binding.getPortType();
                        List operations = pType.getOperations();
                        for (int k = 0; k < operations.size(); k++) {
                            Operation op = (Operation)operations.get(k);
                            if (op.getName().equals(_operationName)) {
                                if (!ioSpecified ||
                                    op.getInput().getName().equals(
                                        _inputMessage.name) &&
                                        op.getOutput().getName().equals(
                                            _outputMessage.name)) {

//                                    _portQName = port.getName();
//                                    _operationQName = op.getName();

                                    // If we were passed an unqualified WSDL
                                    // operation name (i.e., one that doesn't
                                    // specify the input and output messages),
                                    // this is ambiguous if the operation is
                                    // overloaded. OR if we were passed one that
                                    // doesn't specify the service name, this is
                                    // ambiguous if the operation is provided
                                    // by more than one service.
                                    if (operationFound) {
                                        throw new IllegalArgumentException(
                                            WSDL_OP_OVERLOADED);
                                    }
                                    operationFound = true;

                                    // Save service invocation information.
                                    if (_serviceName == null) {
                                        _serviceName = svc.getQName()
                                            .getLocalPart();
                                    }
                                    operation = op;
                                    _portName = port.getName();
                                    _portLocation = portLoc;
                                    _portTypeName = pType.getQName()
                                        .getLocalPart();

                                    // See if there's an associated SOAP action.
                                    BindingOperation bOp = port.getBinding()
                                        .getBindingOperation(_operationName,
                                            _inputMessage.name,
                                            _outputMessage.name);
                                    List bOpEE = bOp.getExtensibilityElements();
                                    for (int m = 0; m < bOpEE.size(); m++) {
                                        ExtensibilityElement ee =
                                            (ExtensibilityElement)bOpEE.get(m);
                                        if (ee instanceof SOAPOperation) {
                                            _soapAction = ((SOAPOperation)ee)
                                                .getSoapActionURI();
                                            break;
                                        }
                                    }

                                    if (ioSpecified) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (!operationFound) {
                throw new IllegalArgumentException(
                    "Undefined SOAP operation: " + _operationName +
                        " in namespace: " + _targetNamespace);
            }

            // We found a matching service.operation - introspect its messages.
            List formalParameters = new ArrayList();
            introspectSOAPMessage(operation.getInput().getMessage(),
                _inputMessage, ParameterMode.IN, formalParameters);

            Output output = operation.getOutput();
            if (output != null) {
                introspectSOAPMessage(output.getMessage(), _outputMessage,
                    ParameterMode.OUT, formalParameters);
            }
            _formalParameters = (FormalParameter[])formalParameters.toArray(
                new FormalParameter[formalParameters.size()]);

            if (_logger.isDebugEnabled()) {
                _logger.debug("introspect: formalParms=" + formalParameters);
            }
        } catch (IOException e) {
            throw new RepositoryException(e);
        } catch (SAXException e) {
            throw new RepositoryException(e);
        } catch (XMLException e) {
            throw new RepositoryException(e);
        } catch (WSDLException e) {
            throw new RepositoryException(e);
        } finally {
            try {
                SchemaUtils.close(in);
            } catch (IOException e) {
                // Don't throw an exception, there may already be one pending.
            }
        }
    }

    private void introspectSOAPMessage(javax.wsdl.Message wsdlMessage,
        Message message, ParameterMode parameterMode, List formalParameters)
        throws XMLException {

        List wsdlParts = wsdlMessage.getOrderedParts(null);
        List parts = new ArrayList();
        for (int i = 0, n = wsdlParts.size(); i < n; i++) {
            javax.wsdl.Part wsdlPart = (javax.wsdl.Part)wsdlParts.get(i);

            DataType dataType;

            // Is this one of the element types defined in the WSDL?
            QName qname = wsdlPart.getElementName();
            String partName = wsdlPart.getName();
            if (qname == null) {
                // No: is the type defined by XML Schema?
                qname = wsdlPart.getTypeName();
                if (qname.getNamespaceURI().equals(W3CNames.XSD_NS_URI)) {
                    // Map XML Schema type to XPDL type.
                    Class javaClass = SchemaUtils.classForSchemaType(
                            (Reader) null, qname);
                    dataType = DataTypes.dataTypeForClass(javaClass);
                } else {
                    // TODO: How to ensure the ext-ref location is correct?
                    // No - it's defined in some other namespace.
                    dataType = new DataType(new ExternalReference(_wsdlLocation,
                        qname.getLocalPart(), qname.getNamespaceURI()));
                }
            } else {
                // Yes - so use an external reference.
                dataType = new DataType(new ExternalReference(_wsdlLocation,
                    partName, qname.getNamespaceURI()));
            }
            parts.add(new Part(partName, qname.getNamespaceURI(),
                qname.getLocalPart()));

            formalParameters.add(new FormalParameter(partName, null,
                parameterMode, dataType, null));
        }
        message.name = wsdlMessage.getQName().getLocalPart();
        message.parts = (Part[])parts.toArray(new Part[parts.size()]);
    }
}