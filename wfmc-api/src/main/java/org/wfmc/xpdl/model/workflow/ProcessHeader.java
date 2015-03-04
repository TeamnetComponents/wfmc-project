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

package org.wfmc.xpdl.model.workflow;

import org.wfmc.util.AbstractBean;
import org.wfmc.xpdl.model.misc.Duration;
import org.wfmc.xpdl.model.misc.DurationUnit;
import org.wfmc.xpdl.model.misc.TimeEstimation;

import java.util.Date;

public final class ProcessHeader extends AbstractBean {
    private static final long serialVersionUID = 1047558258115813116L;

    private Date _created;
    private String _priority;
    private Duration _limit;
    private Date _validFrom;
    private Date _validTo;
    private TimeEstimation _timeEstimation;
    private String _description;
    private DurationUnit _durationUnit;

    /**
     * Construct a new ProcessHeader.
     */
    public ProcessHeader() {
    }

    /**
     * Get the date on which the workflow process was created.
     *
     * @return The process creation date
     */
    public Date getCreated() {
        return _created;
    }

    /**
     * Set the date on which the workflow process was created.
     *
     * @param created The created date
     */
    public void setCreated(Date created) {
        _created = created;
    }

    public String getPriority() {
        return _priority;
    }

    public void setPriority(String priority) {
        _priority = priority;
    }

    public Duration getLimit() {
        return _limit;
    }

    public void setLimit(Duration limit) {
        _limit = limit;
    }

    public Date getValidFrom() {
        return _validFrom;
    }

    public void setValidFrom(Date validFrom) {
        _validFrom = validFrom;
    }

    public Date getValidTo() {
        return _validTo;
    }

    public void setValidTo(Date validTo) {
        _validTo = validTo;
    }

    public TimeEstimation getTimeEstimation() {
        return _timeEstimation;
    }

    public void setTimeEstimation(TimeEstimation timeEstimation) {
        _timeEstimation = timeEstimation;
    }

    /**
     * Get the default duration unit for durations which do not specify the
     * unit.
     *
     * @return The default duration unit
     */
    public DurationUnit getDurationUnit() {
        return _durationUnit;
    }

    public void setDurationUnit(DurationUnit durationUnit) {
        _durationUnit = durationUnit;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public String toString() {
        return "ProcessHeader[created=" + _created +
            ", priority=" + _priority +
            ", limit=" + _limit +
            ", validFrom=" + _validFrom +
            ", validTo=" + _validTo +
            ", timeEstimation=" + _timeEstimation +
            ", description=" + _description +
            ", durationUnit=" + _durationUnit +
            ']';
    }
}