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


import org.wfmc.server.core.xpdl.model.condition.Condition;
import org.wfmc.server.core.xpdl.model.data.ParameterMode;
import org.wfmc.server.core.xpdl.model.misc.Duration;
import org.xml.sax.EntityResolver;

import java.util.Arrays;

/**
 * Describes a user-defined event type.
 *
 * @author Adrian Price
 */
public class EventTypeMetaData extends AbstractMetaData {
    private static final long serialVersionUID = -3774727312011924066L;
    private String _contentType;
    private String _schema;
    private String _action;
    private Condition _condition;
    private String _scriptType;
    private Duration _timeToLive;
    private String _calendar;
    private EventParameter[] _formalParameters;
    private transient int _inputParameterCount = -1;

    /**
     * Constructs event type meta-data.
     */
    public EventTypeMetaData() {
    }

    public EventTypeMetaData(String eventType, String displayName,
        String description, String docUrl, String author, String contentType,
        String schema, String action, Condition condition, String scriptType,
        String timeToLive, String calendar) {

        super(eventType, displayName, description, docUrl, author, true);
        _contentType = contentType;
        _schema = schema;
        _action = action;
        _condition = condition;
        _scriptType = scriptType;
        _timeToLive = timeToLive == null ? null : Duration.valueOf(timeToLive);
        _calendar = calendar;
    }

    public String getContentType() {
        return _contentType != null ? _contentType
            : _type == null || !allowInheritance ? null :
            ((EventTypeMetaData)_type).getContentType();
    }

    public void setContentType(String contentType) {
        _contentType = contentType;
    }

    public String getSchema() {
        return _schema != null ? _schema
            : _type == null || !allowInheritance ? null :
            ((EventTypeMetaData)_type).getSchema();
    }

    public void setSchema(String schema) {
        _schema = schema;
    }

    public String getAction() {
        return _action != null ? _action
            : _type == null || !allowInheritance ? null :
            ((EventTypeMetaData)_type).getAction();
    }

    public void setAction(String action) {
        _action = action;
    }

    public Condition getCondition() {
        return _condition != null ? _condition
            : _type == null || !allowInheritance ? null :
            ((EventTypeMetaData)_type).getCondition();
    }

    public void setCondition(Condition condition) {
        _condition = condition;
    }

    public Duration getTimeToLive() {
        return _timeToLive != null ? _timeToLive
            : _type == null || !allowInheritance ? null :
            ((EventTypeMetaData)_type).getTimeToLive();
    }

    public void setTimeToLive(Duration timeToLive) {
        _timeToLive = timeToLive;
    }

    public String getCalendar() {
        return _calendar != null ? _calendar
            : _type == null || !allowInheritance ? null :
            ((EventTypeMetaData)_type).getCalendar();
    }

    public void setCalendar(String calendar) {
        _calendar = calendar;
    }

    public EventParameter[] getFormalParameter() {
        return _formalParameters != null
            ? _formalParameters
            : _type == null
            ? null
            : ((EventTypeMetaData)_type).getFormalParameter();
    }

    public void setFormalParameter(EventParameter[] formalParameters) {
        _formalParameters = formalParameters;
    }

    public int getInputParameterCount() {
        if (_inputParameterCount == -1) {
            if (_formalParameters == null)
                throw new IllegalStateException("FormalParameters required");

            int inputParameterCount = 0;
            for (int i = 0; i < _formalParameters.length; i++) {
                // Stop counting at the first output parameter.
                if (_formalParameters[i].getMode() == ParameterMode.OUT)
                    break;
                inputParameterCount++;
            }
            _inputParameterCount = inputParameterCount;
        }
        return _inputParameterCount;
    }

    public String getScriptType() {
        return _scriptType != null ? _scriptType
            : _type == null || !allowInheritance ? null :
            ((EventTypeMetaData)_type).getScriptType();
    }

    public void setScriptType(String scriptType) {
        _scriptType = scriptType;
    }

    public Object createInstance(EntityResolver entityResolver)
        throws RepositoryException {

        // TODO: understand what 'implementation' maps to in the case of events.
        return "implementation";
    }

    protected String getImplClass() {
        return null;
    }

    protected String[] getImplCtorSig() {
        return null;
    }

    public String toString() {
        String className = getClass().getName();
        className = className.substring(className.lastIndexOf('.') + 1);
        return className +
            "[id='" + _id +
            "',displayName='" + _displayName +
            "',description='" + _description +
            "',docUrl='" + _docUrl +
            "',author='" + _author +
            "',threadsafe=" + _threadsafe +
            ",type=" + _type +
            ",contentType='" + _contentType +
            "',schema='" + _schema +
            "',condition=" + _condition +
            ",scriptType='" + _scriptType +
            "',timeToLive=" + _timeToLive +
            ",calendar='" + _calendar +
            "',calendar=" + _calendar +
            "',formalParameters=" + (_formalParameters == null ? null :
            Arrays.asList(_formalParameters)) +
            ']';
    }
}