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

import org.wfmc.impl.model.WorkItemAttributes;
import org.wfmc.server.core.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.util.Date;

/**
 * Holds the persistent state of a work item.  This interface
 * uses only standard Java data types; it does not need to know about
 * WAPI data types - conversions are handled externally to the persistence
 * service.  No parameter validation need be performed by implementations.
 *
 * @author Adrian Price
 * @author Anthony Eden
 * @see WorkItemAttributes
 */
public interface WorkItem extends AttributedEntity {
    /**
     * Property descriptors for WorkItem.
     * <em>N.B. DO NOT WRITE TO THIS ARRAY!!!</em>
     */
    PropertyDescriptor[] propertyDescriptors = ClassUtils.introspect(
        WorkItem.class, AttributedEntity.class);

    /**
     * Attributes for WorkItem.
     * <em>N.B. DO NOT WRITE TO THIS ARRAY!!!</em>
     */
    String[] attributes = ClassUtils.getPropertyNames(propertyDescriptors);

    String getActivityInstanceId();

    String getActivityDefinitionId();

    String getWorkItemId();

    int getToolIndex();

    void setToolIndex(int index);

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

    String getName();

    void setName(String name);

    String getParticipant();

    void setParticipant(String participant);

    String getPerformer();

    void setPerformer(String performer);

    /**
     * Returns the parent activity instance for this work item.
     *
     * @return The parent ActivityInstance
     */
    ActivityInstance getActivityInstance();
}