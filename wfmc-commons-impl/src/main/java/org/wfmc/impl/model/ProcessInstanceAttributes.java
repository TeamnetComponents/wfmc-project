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

import org.wfmc.impl.spi.model.AttributedEntity;
import org.wfmc.impl.spi.model.ProcessInstance;
import org.wfmc.server.core.util.ClassUtils;
import org.wfmc.server.core.xpdl.model.data.DataField;
import org.wfmc.server.core.xpdl.model.data.DataTypes;
import org.wfmc.server.core.xpdl.model.misc.AbstractWFElement;
import org.wfmc.wapi.WAPI;
import org.wfmc.wapi.WMFilter;
import org.wfmc.wapi.WMProcessInstanceState;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Describes the process instance system attributes supported by OBE.  Note that
 * process instances can also possess custom attributes, defined in the process
 * definition.  The string constants in this interface can be passed to
 * {@link WAPI#getProcessInstanceAttributeValue} and as the
 * <code>attributeName</code> argument to
 * {@link WMFilter#WMFilter(String, int, Boolean)}.
 *
 * @author Adrian Price
 */
public class ProcessInstanceAttributes {
    /**
     * The date/time by which the earliest of the current activities must be
     * complete.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String ACTIVITY_DUE_DATE = "activityDueDate";

    /**
     * The date/time by which the earliest of the current activities is expected
     * to be complete.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String ACTIVITY_TARGET_DATE = "activityTargetDate";

    /**
     * The date/time at which the process instance was completed.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String COMPLETED_DATE = "completedDate";

    /**
     * The date/time at which the process instance was created.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String CREATED_DATE = "createdDate";

    /**
     * The date/time by which the process instance must be complete.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String DUE_DATE = "dueDate";

    /**
     * The name of the process instance.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>Read/Write</td></tr>
     * </table>
     */
    public static final String NAME = "name";

    /**
     * The parent activity instance ID.  This is only set for a sub-process.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String PARENT_ACTIVITY_INSTANCE_ID =
            "parentActivityInstanceId";

    /**
     * The parent process instance ID.  This is only set for a sub-process.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String PARENT_PROCESS_INSTANCE_ID =
            "parentProcessInstanceId";

    /**
     * The process instance participant IDs.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String[]</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String PARTICIPANTS = "participants";

    /**
     * The process instance priority.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>int</code></td><td>Read/Write</td></tr>
     * </table>
     */
    public static final String PRIORITY = "priority";

    /**
     * The ID of the process definition of which this is an instance.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String PROCESS_DEFINITION_ID = "processDefinitionId";

    /**
     * The unique ID of the process instance.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.lang.String</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String PROCESS_INSTANCE_ID = "processInstanceId";

    /**
     * The date/time at which the process instance was started.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String STARTED_DATE = "startedDate";

    /**
     * The state of the process instance.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>int</code></td><td>ReadOnly</td></tr>
     * </table>
     *
     * @see WMProcessInstanceState
     */
    public static final String STATE = "state";

    /**
     * The date/time by which the process instance is expected to be complete.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>java.util.Date</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String TARGET_DATE = "targetDate";

    /**
     * The temporal status of the process instance.
     * <p/>
     * <table border="1">
     * <tr><th>Data Type</th><th>Access</th></tr>
     * <tr><td><code>org.wfmc.server.core.client.api.model.TemporalStatus</code></td><td>ReadOnly</td></tr>
     * </table>
     */
    public static final String TEMPORAL_STATUS = "temporalStatus";

    /**
     * Property descriptors for ProcessInstance.
     * <em>N.B. DO NOT WRITE TO THIS ARRAY!!!</em>
     */
    public static final PropertyDescriptor[] SYSTEM_PROPERTIES;

    /**
     * Attributes for ProcessInstance.
     * <em>N.B. DO NOT WRITE TO THIS ARRAY!!!</em>
     */
    public static final String[] SYSTEM_ATTRIBUTES;

    /**
     * Virtual DataFields corresponding to the system attributes.
     * <em>N.B. DO NOT WRITE TO THIS ARRAY!!!</em>
     */
    public static final DataField[] SYSTEM_DATAFIELDS;

    private static final Comparator _dataFieldComparator;

    static {
        SYSTEM_PROPERTIES = ClassUtils.introspect(ProcessInstance.class, AttributedEntity.class);
        SYSTEM_ATTRIBUTES = ClassUtils.getPropertyNames(SYSTEM_PROPERTIES);
        int n = SYSTEM_PROPERTIES.length;
        SYSTEM_DATAFIELDS = new DataField[n];
        for (int i = 0; i < n; i++) {
            PropertyDescriptor propDesc = SYSTEM_PROPERTIES[i];
            SYSTEM_DATAFIELDS[i] = new DataField(propDesc.getName(), null,
                    DataTypes.dataTypeForClass(propDesc.getPropertyType()));
        }
        _dataFieldComparator = new Comparator() {
            // ORDER BY id ASC
            public int compare(Object o1, Object o2) {
                return ((Comparable) (o1 instanceof DataField ?
                        ((AbstractWFElement) o1).getId() : o1))
                        .compareTo(
                                o2 instanceof DataField ?
                                        ((AbstractWFElement) o2).getId() : o2);
            }
        };
    }

    public static DataField findSystemDataField(String id) {
        int i = Arrays.binarySearch(SYSTEM_DATAFIELDS, id,
                _dataFieldComparator);
        return i < 0 ? null : SYSTEM_DATAFIELDS[i];
    }

    private ProcessInstanceAttributes() {
    }
}