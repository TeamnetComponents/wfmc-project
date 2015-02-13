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
import org.wfmc.wapi.WMActivityInstanceState;
import org.wfmc.wapi.WMFilter;

/**
 * Describes the activity instance system attributes supported by OBE.  The
 * string constants in this interface can be passed to
 * {@link WAPI#getActivityInstanceAttributeValue} and as the
 * <code>attributeName</code> argument to
 * {@link WMFilter#WMFilter(String,int,Boolean)}.
 *
 * @author Adrian Price
 */
public class ActivityInstanceAttributes {
    /**
     * The ID of the activity definition of which this is an instance.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String ACTIVITY_DEFINITION_ID = "activityDefinitionId";

    /**
     * The unique ID of the activity instance.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String ACTIVITY_INSTANCE_ID = "activityInstanceId";

    /**
     * The iterator used to control a looping BlockActivity.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>org.obe.spi.model.PersistentIterator</code></td>
     * <td>ReadOnly</td></tr>
     * </table>
     */
    public static final String BLOCK_ACTIVITY_ITERATOR =
        "blockActivityIterator";

    /**
     * The date/time at which the activity instance was completed.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String COMPLETED_DATE = "completedDate";

    /**
     * The date/time by which the activity instance must be complete.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String DUE_DATE = "dueDate";

    /**
     * The state of the activity's 'join' transition restriction. Is
     * <code>null</code> for start activities (i.e., those with no afferent
     * transitions).
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>org.obe.spi.model.JoinInstance</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String JOIN = "join";

    /**
     * The activity name (copied from the definition).
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String NAME = "name";

    /**
     * The IDs of the participants assigned to the activity.  These IDs are the
     * concrete participant names as resolved by the participant repository, as
     * opposed to any abstract participants declared in the process definition.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String[]</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String PARTICIPANTS = "participants";

    /**
     * The activity instance priority. This value is set from the activity
     * definition, and defaults to that of the process instance.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>int</code></td><td>Read/Write</td></tr>
     * </table>
     */
    public static final String PRIORITY = "priority";

    /**
     * The ID of the process definition that defines the activity.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String PROCESS_DEFINITION_ID = "processDefinitionId";

    /**
     * The ID of the process instance to which the activity instance belongs.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String PROCESS_INSTANCE_ID = "processInstanceId";

    /**
     * The date/time at which the activity instance was started.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String STARTED_DATE = "startedDate";

    /**
     * The state of the activity instance.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>int</code></td><td>ReadOnly</td></tr>
     * </table>
     *
     * @see WMActivityInstanceState
     */
    public static final String STATE = "state";

    /**
     * The date/time by which the activity instance is expected to be complete.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String TARGET_DATE = "targetDate";

    /**
     * The temporal status of the activity instance.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>org.obe.client.api.model.TemporalStatus</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String TEMPORAL_STATUS = "temporalStatus";

    public static final String OWNER_ID = "ownerId";

    public static final String OWNER_TYPE = "ownerType";

    private ActivityInstanceAttributes() {
    }
}