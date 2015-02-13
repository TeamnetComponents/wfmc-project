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

import org.wfmc.impl.repository.ObjectNotFoundException;
import org.wfmc.impl.repository.RepositoryException;

import java.util.Map;

/**
 * Base interface for entities with attributes that are accessible via WAPI.
 *
 * @author Adrian Price
 */
public interface AttributedEntity {
    /**
     * The entity is of type {@link ProcessInstance}
     */
    int PROCESS_INSTANCE_TYPE = 0;
    /**
     * The entity is of type {@link ActivityInstance}
     */
    int ACTIVITY_INSTANCE_TYPE = 1;
    /**
     * The entity is of type {@link WorkItem}
     */
    int WORKITEM_TYPE = 2;

    /**
     * Returns the ID of the associated process definition.
     *
     * @return The process definition ID.
     */
    String getProcessDefinitionId();

    /**
     * Returns the ID of the associated process instance.
     *
     * @return The process instance ID.
     */
    String getProcessInstanceId();

    /**
     * Returns the primary key for the entity.
     *
     * @return The entity's primary key.
     */
    String getEntityId();

    /**
     * Returns the named attribute for the entity, if one exists.
     *
     * @param attributeName The name of the attribute.
     * @return The requested attribute.
     * @throws ObjectNotFoundException if the attribute could not be found.
     * @throws RepositoryException     if some other exception occurred.
     */
    AttributeInstance getAttributeInstance(String attributeName)
        throws RepositoryException;

    /**
     * Returns a map of attributes for this entity, keyed on attribute name.
     *
     * @return An immutable map of attribute instances.
     */
    Map getAttributeInstances() throws RepositoryException;
}