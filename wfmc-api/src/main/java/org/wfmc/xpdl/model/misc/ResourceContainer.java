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

import org.wfmc.xpdl.PackageVisitor;
import org.wfmc.xpdl.model.XPDLProperties;
import org.wfmc.xpdl.model.application.Application;
import org.wfmc.xpdl.model.data.DataField;
import org.wfmc.xpdl.model.ext.AssignmentStrategyDef;
import org.wfmc.xpdl.model.ext.CalendarRef;
import org.wfmc.xpdl.model.participant.Participant;

import java.beans.PropertyVetoException;

/**
 * Abstract container for resources common to XPDLPackage and WorkflowProcess.
 *
 * @author Adrian Price
 */
public abstract class ResourceContainer extends AbstractWFElement
    implements CalendarRef {

    private static final long serialVersionUID = -5828730717399616392L;
    public static final String APPLICATION = XPDLProperties.APPLICATION;
    public static final String ASSIGNMENT_STRATEGY =
        XPDLProperties.ASSIGNMENT_STRATEGY;
    public static final String CALENDAR = XPDLProperties.CALENDAR;
    public static final String DATA_FIELD = XPDLProperties.DATA_FIELD;
    public static final String PARTICIPANT = XPDLProperties.PARTICIPANT;
    public static final String REDEFINABLE_HEADER =
        XPDLProperties.REDEFINABLE_HEADER;
    protected static final Application[] EMPTY_APPLICATION = {};
    protected static final DataField[] EMPTY_DATA_FIELD = {};
    protected static final Participant[] EMPTY_PARTICIPANT = {};
    private static final int APPLICATION_IDX = 0;
    private static final int DATA_FIELD_IDX = 1;
    private static final int PARTICIPANT_IDX = 2;

    private Application[] _application = EMPTY_APPLICATION;
    private AssignmentStrategyDef _assignmentStrategy;
    private String _calendar;
    private DataField[] _dataField = EMPTY_DATA_FIELD;
    private Participant[] _participant = EMPTY_PARTICIPANT;
    private RedefinableHeader _redefinableHeader;

    protected ResourceContainer(String[] propertyNames, Object[] initalValues) {
        super(propertyNames, initalValues);
    }

    protected ResourceContainer(String[] propertyNames, Object[] initalValues,
        String id, String name) {

        super(propertyNames, initalValues, id, name);
    }

    protected void accept(PackageVisitor visitor) {
        for (int i = 0; i < _application.length; i++)
            _application[i].accept(visitor);
        for (int i = 0; i < _dataField.length; i++)
            _dataField[i].accept(visitor);
        for (int i = 0; i < _participant.length; i++)
            _participant[i].accept(visitor);
    }

    public final void add(Application application)
        throws PropertyVetoException {

        _application = (Application[])add(APPLICATION_IDX, application);
    }

    public final void remove(Application application)
        throws PropertyVetoException {

        _application = (Application[])remove(APPLICATION_IDX, application);
    }

    public final Application[] getApplication() {
        return (Application[])get(APPLICATION_IDX);
    }

    public final Application getApplication(int i) {
        return _application[i];
    }

    public final Application getApplication(String id) {
        if (_application != null) {
            for (int i = 0; i < _application.length; i++) {
                Application app = _application[i];
                if (app.getId().equals(id))
                    return app;
            }
        }
        return null;
    }

    public final void setApplication(Application[] applications)
        throws PropertyVetoException {

        set(APPLICATION_IDX, _application = applications == null
            ? EMPTY_APPLICATION : applications);
    }

    public final void setApplication(int i, Application application)
        throws PropertyVetoException {

        set(APPLICATION_IDX, i, application);
    }

    /**
     * Returns the work item assignment strategy.
     *
     * @return Assignment strategy definition, which must have a matching
     *         implementation registered in the AssignmentStrategyRepository.
     */
    public final AssignmentStrategyDef getAssignmentStrategy() {
        return _assignmentStrategy;
    }

    /**
     * Sets the work item assignment strategy. This is an OBE XPDL-1.0 extension.
     *
     * @param strategy Assignment strategy definition, which must have a
     *                 matching implementation registered in the AssignmentStrategyRepository.
     */
    public final void setAssignmentStrategy(AssignmentStrategyDef strategy) {
        _assignmentStrategy = strategy;
    }

    public final String getCalendar() {
        return _calendar;
    }

    public final void setCalendar(String calendar) {
        _calendar = calendar;
    }

    public final void add(DataField dataField) throws PropertyVetoException {
        _dataField = (DataField[])add(DATA_FIELD_IDX, dataField);
    }

    public final void remove(DataField dataField) throws PropertyVetoException {
        _dataField = (DataField[])remove(DATA_FIELD_IDX, dataField);
    }

    public final DataField[] getDataField() {
        return (DataField[])get(DATA_FIELD_IDX);
    }

    public final DataField getDataField(int i) {
        return _dataField[i];
    }

    public final DataField getDataField(String id) {
        if (_dataField != null) {
            for (int i = 0; i < _dataField.length; i++) {
                DataField df = _dataField[i];
                if (df.getId().equals(id))
                    return df;
            }
        }
        return null;
    }

    public final void setDataField(DataField[] dataFields)
        throws PropertyVetoException {

        set(DATA_FIELD_IDX,
            _dataField = dataFields == null ? EMPTY_DATA_FIELD : dataFields);
    }

    public final void setDataField(int i, DataField dataField)
        throws PropertyVetoException {

        set(DATA_FIELD_IDX, i, dataField);
    }

    public final void add(Participant participant)
        throws PropertyVetoException {

        _participant = (Participant[])add(PARTICIPANT_IDX, participant);
    }

    public final void remove(Participant participant)
        throws PropertyVetoException {

        _participant = (Participant[])remove(PARTICIPANT_IDX, participant);
    }

    public final Participant[] getParticipant() {
        return (Participant[])get(PARTICIPANT_IDX);
    }

    public final Participant getParticipant(int i) {
        return _participant[i];
    }

    public final Participant getParticipant(String id) {
        if (_participant != null) {
            for (int i = 0; i < _participant.length; i++) {
                Participant p = _participant[i];
                if (p.getId().equals(id))
                    return p;
            }
        }
        return null;
    }

    public final void setParticipant(Participant[] participants)
        throws PropertyVetoException {

        set(PARTICIPANT_IDX, _participant = participants == null
            ? EMPTY_PARTICIPANT : participants);
    }

    public final void setParticipant(int i, Participant participant)
        throws PropertyVetoException {

        set(PARTICIPANT_IDX, i, participant);
    }

    /**
     * Get the RedefinableHeader.
     *
     * @return The RedefinableHeader
     */
    public final RedefinableHeader getRedefinableHeader() {
        return _redefinableHeader;
    }

    /**
     * Set the RedefinableHeader.
     *
     * @param header The RedefinableHeader
     */
    public final void setRedefinableHeader(RedefinableHeader header) {
        _redefinableHeader = header;
    }
}