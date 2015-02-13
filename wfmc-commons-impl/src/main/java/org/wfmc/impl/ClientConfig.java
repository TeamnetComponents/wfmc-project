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

/**
 * Provides access to OBE client configuration information.  OBE is configured
 * by several XML and properties files, which can either be located in the OBE
 * configuration directory or in the <code>META-INF/services</code> directory in
 * obeconfig.jar.  Resources in the latter location are accessed via this
 * class's ClassLoader.  The OBE configuration directory can be specified by the
 * system property <code>"obe.config.dir"</code>, defaulting to the current
 * directory.  Loose files take precedence over resources in obeconfig.jar.
 * <p/>
 * The <code>obe.properties</code> file contains the OBE client configuration.
 *
 * @author Adrian Price
 */
public final class ClientConfig extends CommonConfig {
    /**
     * Property key for configuring an override to the default JNDI initial
     * context factory whilst preserving the value of the latter.
     */
    public static final String JNDI_INITIAL_CONTEXT_FACTORY_PROP =
        "obe.client.java.naming.factory.initial";
    /**
     * Property key for the name of a JAAS callback handler class.
     */
    public static final String JAAS_CALLBACK_HANDLER_PROP =
        "obe.client.jaas.callback.handler";
    /**
     * Property key for the primary JAAS principal class.
     */
    public static final String JAAS_CALLER_PRINCIPAL_CLASS_PROP =
        "obe.jaas.caller.principal.class";
    /**
     * Property key for the JAAS login configuration entry to use.
     */
    public static final String JAAS_CONFIG_PROP = "obe.client.jaas.config";
    /**
     * Property key for whether client-side tool agents should use STDIO for
     * user interactions.
     */
    public static final String USE_STDIO_PROP = "obe.client.stdio";
    /**
     * Property key for the public JNDI name of a JMS queue connection factory.
     */
    public static final String JMS_QUEUE_CON_FACTORY_PROP =
        "obe.jms.queue.connection.factory";
    /**
     * Property key for the public JNDI name of a JMS topic connection factory.
     */
    public static final String JMS_TOPIC_CON_FACTORY_PROP =
        "obe.jms.topic.connection.factory";
    /**
     * Property key for the OBE server host URL.
     */
    public static final String SERVER_HOST_URL_PROP = "obe.server.host";

    /**
     * Whether client-side tool agents should use STDIO for user interactions.
     * <p/>
     * The setting reflects the boolean value of the configuration property
     * <code>obe.client.stdio</code>. Default is <code>false</code>.
     *
     * @return <code>true</code> to interact via STDIO, <code>false</code> to
     *         present a graphical UI.
     * @see #USE_STDIO_PROP
     */
    public static boolean useSTDIO() {
        return getBooleanProperty(USE_STDIO_PROP, false);
    }

    /**
     * Returns the name of the JAAS configuration entry to use for client
     * authentication.
     * <p/>
     * The setting reflects the string value of the configuration property
     * <code>obe.client.jaas.config</code>.
     *
     * @return JAAS configuration entry name, defaulting to <code>"other"</code>.
     * @see #JAAS_CONFIG_PROP
     */
    public static String getJAASConfig() {
        return getStringProperty(JAAS_CONFIG_PROP, "other");
    }

    /**
     * Returns the JNDI name of the JMS queue connection factory for clients.
     * <p/>
     * The setting reflects the string value of the configuration property
     * <code>obe.jms.queue.connection.factory</code>.
     *
     * @return JMS connection factory JNDI name, defaulting to
     *         <code>"ConnectionFactory"</code>.
     * @see #JMS_QUEUE_CON_FACTORY_PROP
     */
    public static String getJMSQueueConnectionFactory() {
        return getStringProperty(JMS_QUEUE_CON_FACTORY_PROP,
            "ConnectionFactory");
    }

    /**
     * Returns the JNDI name of the JMS topic connection factory for clients.
     * <p/>
     * The setting reflects the string value of the configuration property
     * <code>obe.jms.topic.connection.factory</code>.
     *
     * @return JMS connection factory JNDI name, defaulting to
     *         <code>"ConnectionFactory"</code>.
     * @see #JMS_TOPIC_CON_FACTORY_PROP
     */
    public static String getJMSTopicConnectionFactory() {
        return getStringProperty(JMS_TOPIC_CON_FACTORY_PROP,
            "TopicConnectionFactory");
    }

    /**
     * Returns the name of the JAAS callback handler to use for client
     * authentication.
     * <p/>
     * The setting reflects the string value of the configuration property
     * <code>obe.client.jaas.callback.handler</code>.  The implementing class
     * must have a public constructor that takes the following arguments:
     * <p/>
     * <code>(String url, String username, String password)</code>
     *
     * @return JAAS callback handler class name, defaulting to
     *         <code>"org.obe.client.api.base.OBECallbackHandler"</code>.
     * @see #JAAS_CALLBACK_HANDLER_PROP
     */
    public static String getJAASCallbackHandlerClass() {
        return getStringProperty(JAAS_CALLBACK_HANDLER_PROP,
            "org.obe.client.api.base.OBECallbackHandler");
    }

    /**
     * Returns the name of the <code>javax.security.Principal</code> class from
     * which to infer the primary identity of an authenticated subject. If
     * <code>null</code>, the client uses the first non-group principal in the
     * authenticated subject.
     * <p/>
     * The setting reflects the string value of the configuration property
     * <code>obe.jaas.caller.principal.class</code>.
     *
     * @return JAAS callback handler class name, defaulting to <code>null</code>.
     * @see #JAAS_CALLER_PRINCIPAL_CLASS_PROP
     */
    public static String getJAASCallerPrincipalClass() {
        return getStringProperty(JAAS_CALLER_PRINCIPAL_CLASS_PROP, null);
    }

    /**
     * Returns an override for the JNDI property
     * <code>java.naming.factory.initial</code>. Generally speaking there is no
     * reason to set this property.  It should only be used if the OBE client
     * needs to bypass the standard JNDI initial context factory as specified
     * by the system property <code>java.naming.factory.initial</code>. For
     * example, certain RPC tunnelling protocols wrap default JNDI objects with
     * their own marshalling mechanism.
     * <p/>
     * The setting reflects the string value of the configuration property
     * <code>obe.client.java.naming.factory.initial</code>.
     *
     * @return JAAS callback handler class name, defaulting to <code>null</code>.
     * @see #JNDI_INITIAL_CONTEXT_FACTORY_PROP
     */
    public static String getJNDIInitialContextFactoryClass() {
        return getStringProperty(JNDI_INITIAL_CONTEXT_FACTORY_PROP, null);
    }

    /**
     * Returns the OBE server host URL.
     * <p/>
     * The setting reflects the string value of the configuration property
     * <code>obe.server.host</code>.
     *
     * @return Server host name, defaulting to
     *         <code>"jnp://localhost:1099"</code>.
     * @see #SERVER_HOST_URL_PROP
     */
    public static String getServerHostURL() {
        return getStringProperty(SERVER_HOST_URL_PROP, "jnp://localhost:1099");
    }

    private ClientConfig() {
    }
}