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
 SERVICES = ""; LOSS OF USE, DATA, OR PROFITS = ""; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.

 For more information on OBE, please see
 <http://obe.sourceforge.net/>.

 */

package org.wfmc.impl.model;

import org.wfmc.audit.WMAEventCode;
import org.wfmc.wapi.WMProcessInstanceState;

/**
 * @author Adrian Price
 */
public class WMAAuditEntryAttributes {
    /**
     * The ID of the associated process definition.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String PROCESS_DEFINITION_ID = "processDefinitionId";

    /**
     * The ID of the associated activity definition.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String ACTIVITY_DEFINITION_ID = "activityDefinitionId";

    /**
     * The ID of the parent process instance, if any.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String INITIAL_PROCESS_INSTANCE_ID =
        "initialProcessInstanceId";

    /**
     * The ID of the associated process instance.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String CURRENT_PROCESS_INSTANCE_ID =
        "currentProcessInstanceId";

    /**
     * The ID of the associated activity instance.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String ACTIVITY_INSTANCE_ID = "activityInstanceId";

    /**
     * The state of the associated process instance.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     *
     * @see WMProcessInstanceState
     */
    public static final String PROCESS_STATE = "processState";

    /**
     * The Interface 5 event code.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>WMAEventCode</code></td><td>ReadOnly</td></tr>
     * </table>
     *
     * @see WMAEventCode
     */
    public static final String EVENT_CODE = "eventCode";

    /**
     * The domain ID for the workflow engine.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String DOMAINID = "domainId";

    /**
     * The node ID of the workflow engine.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String NODEID = "nodeId";

    /**
     * The ID of the user causing the event.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String USERID = "userId";

    /**
     * The ID of the role causing the event.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String ROLEID = "roleId";

    /**
     * The date/time at which the entry was logged.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String TIMESTAMP = "timestamp";

    /**
     * The type of information (<code>WfMC</code> or <code>Private</code>).
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String INFORMATIONID = "informationId";

    private WMAAuditEntryAttributes() {
    }
}