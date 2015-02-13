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

package org.wfmc.impl.spi.model;


import org.wfmc.server.core.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Date;

/**
 * Holds the persistent state of an activity instance.  This interface
 * uses only standard Java data types; it does not need to know about
 * WAPI data types - conversions are handled externally to the persistence
 * service.  No parameter validation need be performed by implementations.
 *
 * @author Adrian Price
 * @see ActivityInstanceAttributes
 */
public interface ActivityInstance extends AttributedEntity {
    /**
     * Property descriptors for ActivityInstance.
     * <em>N.B. DO NOT WRITE TO THIS ARRAY!!!</em>
     */
    PropertyDescriptor[] propertyDescriptors = ClassUtils.introspect(
            ActivityInstance.class, AttributedEntity.class);

    /**
     * Attributes for ActivityInstance.
     * <em>N.B. DO NOT WRITE TO THIS ARRAY!!!</em>
     */
    String[] attributes = ClassUtils.getPropertyNames(propertyDescriptors);

    String getActivityDefinitionId();

    String getActivityInstanceId();

    /**
     * Returns the ID of the encompassing block activity instance.
     * N.B. This returns the activity instance ID, not the ActivitySet ID.
     *
     * @return The block activity instance ID, or <code>null</code> if this
     *         activity is not defined within an activity set.
     */
    String getBlockActivityInstanceId();

    /**
     * Returns an iterator for the block activity owned by the instance.
     * The iterator returned is persistence-capable, and can resume iteration
     * through a supplied array.  This feature is required to support the
     * ForEach, Until, and While explicit iteration constructs in an
     * asynchronous mode.
     *
     * @return A persistent iterator.
     * @see PersistentIterator
     */
    PersistentIterator getBlockActivityIterator();

    int getState();

    void setState(int state);

    int getPriority();

    void setPriority(int priority);

    Date getStartedDate();

    void setStartedDate(Date startedDate);

    Date getCompletedDate();

    void setCompletedDate(Date completedDate);

    Date getTargetDate();

    void setTargetDate(Date targetDate);

    Date getDueDate();

    void setDueDate(Date dueDate);

    JoinInstance getJoin();

    String getName();

    void setName(String name);

    String[] getParticipants();

    void setParticipants(String[] participants);

    /**
     * Returns the process instance to which this activity belongs.
     *
     * @return The process instance.
     */
    ProcessInstance getProcessInstance();

    /**
     * Returns a collection of sub-process instance(s) associated
     * with a SubFlow-implementation activity. For synchronous
     * sub-flow activities, the collection will contain at most one open process
     * instance; any others will be in the closed state.  Asynchronous subflow
     * activities can have any number of open subflow instances.  Only subflow
     * activities in loops will have multiple child process instances.
     *
     * @return An immutable collection of process instances.
     */
    Collection getChildProcessInstances();

//    /**
//     * Creates a sub-process instance as a child of this activity instance.
//     *
//     * @param processDefinitionId The ID of the subflow's process definition.
//     * @param instanceName The name of the subflow process instance.
//     * @param priority The subflow process priority.
//     * @param state The subflow process state.
//     * @param createdDate The date/time of subflow process creation.
//     * @param startedDate The date/time at which the subflow was started.
//     * @param participants The participants for the subflow.
//     * @return The child sub-flow.
//     * @throws RepositoryException
//     */
//    ProcessInstance addChildProcessInstance(String processDefinitionId,
//        String instanceName, int priority, int state, Date createdDate,
//        Date startedDate, String[] participants) throws RepositoryException;

    /**
     * Returns a collection of work items for this activity.
     *
     * @return An immutable collection of work items.
     */
    Collection getWorkItems();

//    /**
//     * Creates a new work item for this activity.
//     *
//     * @param state
//     * @param performer
//     * @param participant
//     * @return A new WorkItem object
//     * @throws RepositoryException
//     */
//    WorkItem addWorkItem(int state, String performer, String participant)
//        throws RepositoryException;
}