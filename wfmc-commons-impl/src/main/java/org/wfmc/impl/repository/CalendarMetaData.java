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

import org.xml.sax.EntityResolver;

/**
 * Describes a user-defined business calendar implementation.
 *
 * @author Adrian Price
 */
public final class CalendarMetaData extends ImplClassMetaData {
    private static final long serialVersionUID = -7900131126653741780L;

    /**
     * Constructs calendar meta-data.
     */
    public CalendarMetaData() {
    }

    /**
     * Constructs Calendar meta-data.
     *
     * @param id          The Calendar ID.
     * @param displayName The display name.
     * @param description Textual description of this function set.
     * @param docUrl      URL for documentation.
     * @param author      Author's name.
     * @param implClass   The fully qualified name of the Calendar
     *                    implementation class.
     */
    public CalendarMetaData(String id, String displayName, String description,
        String docUrl, String author, String implClass) {

        super(id, displayName, description, docUrl, author, true, implClass,
            NO_ARGS_SIG);
    }

    /**
     * Returns the class that implements the business calendar.
     *
     * @param entityResolver
     * @return Business calendar implementation.
     * @throws RepositoryException if the class could not be found or
     *                             instantiated.
     */
    public Object createInstance(EntityResolver entityResolver)
        throws RepositoryException {

        return createInstance(EMPTY_ARGS);
    }

    public String toString() {
        return "CalendarMetaData[_id='" + _id + '\'' +
            ",_displayName='" + _displayName + '\'' +
            ",_description='" + _description + '\'' +
            ",_docUrl='" + _docUrl + '\'' +
            ",_author='" + _author + '\'' +
            ",_implClass='" + getImplClass() + '\'' +
            ",_type=" + _type +
            ']';
    }
}