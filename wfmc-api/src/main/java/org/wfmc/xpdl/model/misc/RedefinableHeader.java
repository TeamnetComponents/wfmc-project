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

package org.wfmc.xpdl.model.misc;

import org.wfmc.util.AbstractBean;
import org.wfmc.xpdl.model.XPDLProperties;

import java.beans.PropertyVetoException;
import java.util.Arrays;

/**
 * Header information which can be used for entities such as the package or
 * individual workflow processes.
 *
 * @author Adrian Price
 */
public final class RedefinableHeader extends AbstractBean {
    private static final long serialVersionUID = -7749373588413143522L;
    public static final String AUTHOR = XPDLProperties.AUTHOR;
    public static final String CODE_PAGE = XPDLProperties.CODEPAGE;
    public static final String COUNTRY_KEY = XPDLProperties.COUNTRYKEY;
    public static final String PUBLICATION_STATUS =
        XPDLProperties.PUBLICATION_STATUS;
    public static final String RESPONSIBLE = XPDLProperties.RESPONSIBLE;
    public static final String VERSION = XPDLProperties.VERSION;
    private static final String[] EMPTY_RESPONSIBLE = {};
    private static final String[] _indexedPropertyNames = {RESPONSIBLE};
    private static final Object[] _indexedPropertyValues = {EMPTY_RESPONSIBLE};
    private static final int RESPONSIBLE_IDX = 0;

    private String _author;
    private String _version;
    private String _codepage;
    private String _countrykey;
    private String[] _responsible = EMPTY_RESPONSIBLE;
    private PublicationStatus _publicationStatus;

    /**
     * Construct a new RedefinableHeader.
     */
    public RedefinableHeader() {
        super(_indexedPropertyNames, _indexedPropertyValues);
    }

    /**
     * Get the entity's author.
     *
     * @return The entity's author
     */
    public String getAuthor() {
        return _author;
    }

    /**
     * Set the entity's author.
     *
     * @param author The entity's new author
     */
    public void setAuthor(String author) {
        _author = author;
    }

    /**
     * Get the entity's version.
     *
     * @return The version
     */
    public String getVersion() {
        return _version;
    }

    /**
     * Set the entity's version.
     *
     * @param version The version
     */
    public void setVersion(String version) {
        _version = version;
    }

    public String getCodepage() {
        return _codepage;
    }

    public void setCodepage(String codepage) {
        _codepage = codepage;
    }

    public String getCountrykey() {
        return _countrykey;
    }

    public void setCountrykey(String countrykey) {
        _countrykey = countrykey;
    }

    public void add(String responsible) throws PropertyVetoException {
        _responsible = (String[])add(RESPONSIBLE_IDX, responsible);
    }

    public void remove(String responsible) throws PropertyVetoException {
        _responsible = (String[])remove(RESPONSIBLE_IDX, responsible);
    }

    /**
     * Returns the responsible participants' IDs.
     *
     * @return Array of participant IDs.
     */
    public String[] getResponsible() {
        return (String[])get(RESPONSIBLE_IDX);
    }

    public String getResponsible(int i) {
        return _responsible[i];
    }

    public void setResponsible(String[] responsibles)
        throws PropertyVetoException {

        set(RESPONSIBLE_IDX, _responsible = responsibles == null
            ? EMPTY_RESPONSIBLE : responsibles);
    }

    public void setResponsible(int i, String responsible)
        throws PropertyVetoException {

        set(RESPONSIBLE_IDX, i, responsible);
    }

    /**
     * Get the publication status for this workflow definition.
     *
     * @return The publication status
     */
    public PublicationStatus getPublicationStatus() {
        return _publicationStatus;
    }

    /**
     * Set the publication status for this workflow definition.
     *
     * @param publicationStatus The new publication status
     */
    public void setPublicationStatus(PublicationStatus publicationStatus) {
        _publicationStatus = publicationStatus;
    }

    public String toString() {
        return "RedefinableHeader[author=" + _author +
            ", version=" + _version +
            ", codepage=" + _codepage +
            ", countrykey=" + _countrykey +
            ", responsible=" + Arrays.asList(_responsible) +
            ", publicationStatus=" + _publicationStatus +
            ']';
    }
}