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

package org.wfmc.xpdl.model.pkg;

import org.wfmc.util.AbstractBean;

import java.net.URL;
import java.util.Date;

/**
 * Standard header for a workflow package.  The PackageHeader is a required
 * element.
 *
 * @author Adrian Price
 */
public final class PackageHeader extends AbstractBean {
    private static final long serialVersionUID = 2873152111677095659L;

    private String _xpdlVersion;
    private String _vendor;
    private Date _created;
    private String _description;
    private URL _documentation;
    private String _priorityUnit;
    private String _costUnit;

    /**
     * Constructs a new PackageHeader.
     */
    public PackageHeader() {
    }

    /**
     * Get version of XPDL used.
     *
     * @return The XPDL version String
     */
    public String getXPDLVersion() {
        return _xpdlVersion;
    }

    public void setXPDLVersion(String xpdlVersion) {
        if (xpdlVersion == null) {
            throw new IllegalArgumentException(
                "XPDL version is required in package header");
        }
        _xpdlVersion = xpdlVersion;
    }

    /**
     * Get the name of the tool vendor.
     *
     * @return The tool vendor name
     */
    public String getVendor() {
        return _vendor;
    }

    public void setVendor(String vendor) {
        if (vendor == null) {
            throw new IllegalArgumentException(
                "Vendor is required in package header");
        }
        _vendor = vendor;
    }

    public Date getCreated() {
        return _created;
    }

    public void setCreated(Date created) {
        if (created == null) {
            throw new IllegalArgumentException(
                "Created date is required in package header");
        }
        _created = created;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public URL getDocumentation() {
        return _documentation;
    }

    public void setDocumentation(URL documentation) {
        _documentation = documentation;
    }

    public String getPriorityUnit() {
        return _priorityUnit;
    }

    public void setPriorityUnit(String priorityUnit) {
        _priorityUnit = priorityUnit;
    }

    public String getCostUnit() {
        return _costUnit;
    }

    public void setCostUnit(String costUnit) {
        _costUnit = costUnit;
    }

    public String toString() {
        return "PackageHeader[xpdlVersion=" + _xpdlVersion +
            ", vendor=" + _vendor +
            ", created=" + _created +
            ", description=" + _description +
            ", documentation=" + _documentation +
            ", priorityUnit=" + _priorityUnit +
            ", costUnit=" + _costUnit +
            ']';
    }
}