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

package org.wfmc.impl;


import org.wfmc.server.core.util.CommonConfig;
import org.wfmc.wapi.WMWorkflowException;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class to support pluggable client implementations.  As standard,
 * <a href="rmi/LocalClient.html">local</a>,
 * <a href="rmi/J2EELocalClient.html">j2ee-local</a>,
 * <a href="rmi/J2EERemoteClient.html">rmi</a> and
 * <a href="xmlrpc/WMXmlRpcClient.html">xml-rpc</a> are supported.
 *
 * @author Adrian Price
 */
public final class WMClientFactory {
    private static final String LOCAL_CLASS_NAME =
        "org.obe.client.api.local.LocalClient";
    private static final String J2EE_LOCAL_CLASS_NAME =
        "org.obe.client.api.local.J2EELocalClient";
    private static final String RMI_CLASS_NAME =
        "org.obe.client.api.rmi.J2EERemoteClient";
    private static final String XML_RPC_CLASS_NAME =
        "org.obe.client.api.xmlrpc.WMXmlRpcClient";
    /**
     * Represents a standard, local Java interface.
     */
    public static final String LOCAL = "local";
    /**
     * Represents a standard, local J2EE interface.
     */
    public static final String J2EE_LOCAL = "j2ee-local";
    /**
     * Represents the standard RMI remote protocol.
     */
    public static final String RMI = "rmi";
    /**
     * Represents the XML-RPC protocol.  The default implementation uses the
     * Apache XML-RPC libraries.
     */
    public static final String XML_RPC = "xml-rpc";
    private static final Map CLIENT_CLASSES = new HashMap();

    static {
        registerClientClass(LOCAL, LOCAL_CLASS_NAME);
        registerClientClass(J2EE_LOCAL, J2EE_LOCAL_CLASS_NAME);
        registerClientClass(RMI, RMI_CLASS_NAME);
        registerClientClass(XML_RPC, XML_RPC_CLASS_NAME);
    }

    // Prevent instantiation.
    private WMClientFactory() {
    }

    /**
     * Creates a client instance using the default protocol.  The default
     * protocol is "local", but can be overridden by setting the configuration
     * property <code>obe.client.protocol</code>.
     *
     * @return A client instance.
     * @throws WMWorkflowException      if an exception occurred when instantiating
     *                                  the client.
     * @throws IllegalArgumentException if the protocol name is invalid.
     */
    public static WMClient createClient() throws WMWorkflowException {
        String protocol = CommonConfig.getProtocol();
        return createClient(protocol);
    }

    /**
     * Creates a client instance for the specified protocol.
     *
     * @param protocol The protocol name, one of: {@link #LOCAL},
     *                 {@link #J2EE_LOCAL}, {@link #RMI}, {@link #XML_RPC}, or some
     *                 implementation that was registered by a prior call to
     *                 {@link #registerClientClass}.
     * @return A client instance.
     * @throws WMWorkflowException      if an exception occurred when instantiating
     *                                  the client.
     * @throws IllegalArgumentException if the protocol name is invalid, or if
     *                                  the registered class is not a valid WMClient implementation.
     */
    public static WMClient createClient(String protocol)
        throws WMWorkflowException {

        try {
            // Ensure the class is registered.
            String clientClass = (String)CLIENT_CLASSES.get(protocol);
            if (clientClass == null)
                throw new IllegalArgumentException(protocol);

            // Client class must implement interface WMClient.
            Class clazz = Class.forName(clientClass);
            if (!WMClient.class.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException("The class " + clientClass +
                    " must implement the interface " +
                    WMClient.class.getName());
            }

            // Instantiate it.
            return (WMClient)clazz.newInstance();
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(protocol);
        } catch (InstantiationException e) {
            throw new WMWorkflowException(e);
        } catch (IllegalAccessException e) {
            throw new WMWorkflowException(e);
        }
    }

    /**
     * Registers a <code>WMClient</code> implementation for the specified
     * protocol.
     *
     * @param protocol    The protocol name.
     * @param clientClass The fully qualified name of the implementing class,
     *                    which must implement {@link WMClient} and have a public,
     *                    no-args constructor.
     */
    public static void registerClientClass(String protocol,
        String clientClass) {

        if (protocol == null || clientClass == null) {
            throw new IllegalArgumentException(
                "protocol and clientClass must both be non-null.");
        }

        CLIENT_CLASSES.put(protocol, clientClass);
    }
}