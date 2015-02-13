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

package org.wfmc.impl.model;

import org.wfmc.wapi.WAPI;
import org.wfmc.wapi.WMFilter;
import org.wfmc.wapi.WMWorkItemState;

/**
 * Describes the work item system attributes supported by OBE.  The
 * string constants in this interface can be passed to
 * {@link WAPI#getWorkItemAttributeValue} and as the
 * <code>attributeName</code> argument to
 * {@link WMFilter#WMFilter(String,int,Boolean)}.
 *
 * @author Adrian Price
 */
public class WorkItemAttributes {
    /**
     * The ID of the activity instance with which the work item is associated.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String ACTIVITY_INSTANCE_ID = "activityInstanceId";

    /**
     * The date/time at which the work item was completed.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String COMPLETED_DATE = "completedDate";

    /**
     * The date/time by which the work item must be complete.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String DUE_DATE = "dueDate";

    /**
     * The name of the work item (copied from its activity instance).
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String NAME = "name";

    /**
     * The ID of the participant to whom the work item is assigned.  This ID is
     * the concrete participant name as resolved by the participant repository,
     * as opposed to the (potentially) abstract performer declared in the
     * activity definition.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String PARTICIPANT = "participant";

    /**
     * The ID of the performer to whom the work item is assigned.  This ID is
     * the abstract performer declared in the activity definition.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String PERFORMER = "performer";

    /**
     * The priority of the work item (copied from the activity instance).
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>int</code></td><td>Read/Write</td></tr>
     * </table>
     */
    public static final String PRIORITY = "priority";

    /**
     * The ID of the process definition that defines the work item's activity.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String PROCESS_DEFINITION_ID = "processDefinitionId";

    /**
     * The ID of the process instance to which the work item belongs.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String PROCESS_INSTANCE_ID = "processInstanceId";

    /**
     * The date/time at which the work item was started.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String STARTED_DATE = "startedDate";

    /**
     * The state of the work item.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>int</code></td><td>ReadOnly</td></tr>
     * </table>
     *
     * @see WMWorkItemState
     */
    public static final String STATE = "state";

    /**
     * The date/time by which the work item is expected to be complete.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String TARGET_DATE = "targetDate";

    /**
     * The temporal status of the work item.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>org.obe.client.api.model.TemporalStatus</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String TEMPORAL_STATUS = "temporalStatus";

    /**
     * The index of the tool associated with the work item.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>int</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String TOOL_INDEX = "toolIndex";

    /**
     * The unique ID of the work item.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String WORKITEM_ID = "workItemId";

    private WorkItemAttributes() {
    }
}