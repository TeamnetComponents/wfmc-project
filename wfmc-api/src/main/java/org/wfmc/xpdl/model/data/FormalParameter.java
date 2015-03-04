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

package org.wfmc.xpdl.model.data;

import org.wfmc.util.AbstractBean;
import org.wfmc.xpdl.PackageVisitor;
import org.wfmc.xpdl.XPDLMessages;

/**
 * FormalParameter defines input and output parameters which are passed to
 * tools.
 *
 * @author Adrian Price
 */
public class FormalParameter extends AbstractBean {
    private static final long serialVersionUID = 3019502908080770457L;

    private String _id;
    private Integer _index;
    private ParameterMode _mode;
    private DataType _dataType;
    private String _description;

    /**
     * Constructs a new FormalParameter object.
     */
    public FormalParameter() {
    }

    /**
     * Constructs a new FormalParameter object.
     *
     * @param id          The unique id
     * @param index       The index of the parameter
     * @param mode        The ParameterMode
     * @param dataType    The DataType
     * @param description The name
     */
    public FormalParameter(String id, String index, String mode,
        DataType dataType, String description) {

        this(id, index == null ? null : Integer.valueOf(index),
            ParameterMode.valueOf(mode), dataType, description);
    }

    /**
     * Constructs a new FormalParameter object.
     *
     * @param id          The unique id
     * @param index       The index of the parameter
     * @param mode        The ParameterMode
     * @param dataType    The DataType
     * @param description The description
     */
    public FormalParameter(String id, Integer index, ParameterMode mode,
        DataType dataType, String description) {

        _id = id;
        _description = description;
        setIndex(index);
        setMode(mode);
        setDataType(dataType);
    }

    public final void accept(PackageVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Get the unique id.
     *
     * @return The id
     */
    public final String getId() {
        return _id;
    }

    public final void setId(String id) {
        _id = id;
    }

    public final String getDescription() {
        return _description;
    }

    public final void setDescription(String description) {
        _description = description;
    }

    public final Integer getIndex() {
        return _index;
    }

    public final void setIndex(Integer index) {
        _index = index;
    }

    public final void setIndex(String index) {
        setIndex(index == null ? null : Integer.valueOf(index));
    }

    public final DataType getDataType() {
        return _dataType;
    }

    public final void setDataType(DataType dataType) {
        if (dataType == null)
            throw new IllegalArgumentException(XPDLMessages.DATA_TYPE_REQUIRED);
        _dataType = dataType;
    }

    public final String getDataTypeString() {
        return _dataType == null || !(_dataType.getType() instanceof BasicType)
            ? null : _dataType.getType().toString();
    }

    public final void setDataTypeString(String basicType) {
        setDataType(new DataType(BasicType.valueOf(basicType)));
    }

    /**
     * Get the parameter mode.  The default parameter mode of
     * <code>ParameterMode.IN</code>.
     *
     * @return The parameter mode
     */
    public final ParameterMode getMode() {
        return _mode;
    }

    /**
     * Set the parameter mode.  If a null value is passed then the parameter
     * mode will be treated as {@link ParameterMode#IN}.
     *
     * @param mode The new parameter mode
     */
    public final void setMode(ParameterMode mode) {
        _mode = mode;
    }

    /**
     * Returns the parameter mode as a string.
     *
     * @return Parameter mode string.
     */
    public final String getModeString() {
        return _mode == null ? null : _mode.toString();
    }

    /**
     * Sets the parameter mode as a string.  If a null value is passed then the
     * parameter mode will be reset to the default (ParameterMode.IN).
     *
     * @param mode The new parameter mode. Must be a valid {@link ParameterMode}.
     */
    public final void setModeString(String mode) {
        setMode(ParameterMode.valueOf(mode));
    }

    public String toString() {
        return "FormalParameter[id=" + _id +
            ", index=" + _index +
            ", mode=" + _mode +
            ", dataType=" + _dataType +
            ", description=" + _description +
            ']';
    }
}