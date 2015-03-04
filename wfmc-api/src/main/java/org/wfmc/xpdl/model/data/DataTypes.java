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

import org.w3c.dom.Document;
import org.wfmc.util.AbstractEnum;
import org.wfmc.xpdl.XPDLNames;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Adrian Price
 */
public final class DataTypes extends AbstractEnum {
    private static final long serialVersionUID = 8024954267852036171L;

    public static final DataTypes BOOLEAN =
        new DataTypes(XPDLNames.BOOLEAN_KIND,
            Type.BOOLEAN_TYPE);
    public static final DataTypes PERFORMER =
        new DataTypes(XPDLNames.PERFORMER_KIND,
            Type.PERFORMER_TYPE);
    public static final DataTypes STRING = new DataTypes(XPDLNames.STRING_KIND,
        Type.STRING_TYPE);
    public static final DataTypes FLOAT = new DataTypes(XPDLNames.FLOAT_KIND,
        Type.FLOAT_TYPE);
    public static final DataTypes INTEGER =
        new DataTypes(XPDLNames.INTEGER_KIND,
            Type.INTEGER_TYPE);
    public static final DataTypes REFERENCE =
        new DataTypes(XPDLNames.REFERENCE_KIND,
            Type.REFERENCE_TYPE);
    public static final DataTypes DATETIME =
        new DataTypes(XPDLNames.DATETIME_KIND,
            Type.DATETIME_TYPE);
    public static final DataTypes UNION = new DataTypes(XPDLNames.UNION_KIND,
        Type.UNION_TYPE);
    public static final DataTypes ENUMERATION = new DataTypes(
        XPDLNames.ENUMERATION_KIND, Type.ENUMERATION_TYPE);
    public static final DataTypes ARRAY = new DataTypes(XPDLNames.ARRAY_KIND,
        Type.ARRAY_TYPE);
    public static final DataTypes LIST = new DataTypes(XPDLNames.LIST_KIND,
        Type.LIST_TYPE);
    public static final DataTypes EXTERNAL_REFERENCE =
        new DataTypes(XPDLNames.EXTERNAL_REFERENCE_KIND,
            Type.EXTERNAL_REFERENCE_TYPE);
    public static final DataTypes SCHEMA = new DataTypes(XPDLNames.SCHEMA_KIND,
        Type.SCHEMA_TYPE);

    /**
     * Array of all DataTypes.
     */
    private static final DataTypes[] _values = {
        BOOLEAN,
        PERFORMER,
        STRING,
        FLOAT,
        INTEGER,
        REFERENCE,
        DATETIME,
        UNION,
        ENUMERATION,
        ARRAY,
        LIST,
        EXTERNAL_REFERENCE,
        SCHEMA
    };
    private static final Map _tagMap = new HashMap();
    public static final List VALUES = clinit(_values, _tagMap);

    /**
     * Lookup table of classes that correspond to a particular type:
     * <p/>
     * <code>Class c = DataTypes.classForType(type.value());</code>
     * <p/>
     */
    private static final Class[] _xpdlToJava = {
        String.class,   // STRING_TYPE
        Double.class,   // FLOAT_TYPE
        Integer.class,  // INTEGER_TYPE
        Object.class,   // REFERENCE_TYPE
        Date.class,     // DATETIME_TYPE
        Boolean.class,  // BOOLEAN_TYPE
        String.class,   // PERFORMER_TYPE
        null,           // DECLARED_TYPE (always mapped to another type)
        Document.class, // SCHEMA_TYPE
        Object.class,   // EXTERNAL_REFERENCE_TYPE
        Object.class,   // RECORD_TYPE
        Object.class,   // UNION_TYPE
        Object.class,   // ENUMERATION_TYPE
        Object.class,   // ARRAY_TYPE
        List.class      // LIST_TYPE
    };

    private static final Object[][] _javaToXpdl = {
        {boolean.class, new Integer(Type.BOOLEAN_TYPE)},
        {Boolean.class, new Integer(Type.BOOLEAN_TYPE)},
        {byte.class, new Integer(Type.INTEGER_TYPE)},
        {Byte.class, new Integer(Type.INTEGER_TYPE)},
        {char.class, new Integer(Type.INTEGER_TYPE)},
        {Character.class, new Integer(Type.INTEGER_TYPE)},
        {double.class, new Integer(Type.FLOAT_TYPE)},
        {Double.class, new Integer(Type.FLOAT_TYPE)},
        {float.class, new Integer(Type.FLOAT_TYPE)},
        {Float.class, new Integer(Type.FLOAT_TYPE)},
        {int.class, new Integer(Type.INTEGER_TYPE)},
        {Integer.class, new Integer(Type.INTEGER_TYPE)},
        {short.class, new Integer(Type.INTEGER_TYPE)},
        {Short.class, new Integer(Type.INTEGER_TYPE)},
        {Date.class, new Integer(Type.DATETIME_TYPE)},
        {Document.class, new Integer(Type.SCHEMA_TYPE)},
        {String.class, new Integer(Type.STRING_TYPE)},
    };
    private static final Map _javaToXpdlMap = new HashMap();


    static {
        for (int i = 0; i < _javaToXpdl.length; i++)
            _javaToXpdlMap.put(_javaToXpdl[i][0], _javaToXpdl[i][1]);
    }

    /**
     * Returns the XPDL ordinal data type for the specified class.
     *
     * @param type The Java class to map to an XPDL type.
     * @return Ordinal data type.
     * @see DataTypes#typeForClass
     */
    public static Class classForType(int type) {
        return _xpdlToJava[type];
    }

    /**
     * Returns the class for the specified data type.
     *
     * @param dataType The XPDL data type definition.
     * @return Run-time value class for the data type.
     */
    public static Class classForDataType(DataType dataType) {
        return classForType(dataType.getType());
    }

    /**
     * Returns the class for the specified data type.
     *
     * @param type The XPDL data type.
     * @return Run-time value class for the data type.
     */
    public static Class classForType(Type type) {
        return classForType(type.getImpliedType().value());
    }

    /**
     * Looks up the type ordinal that corresponds to a particular class.
     * <p/>
     * <code>int type = DataTypes.typeForClass(clazz);</code>
     * <p/>
     */
    public static int typeForClass(Class clazz) {
        Integer type = (Integer)_javaToXpdlMap.get(clazz);
        return type == null ? Type.EXTERNAL_REFERENCE_TYPE : type.intValue();
    }

    public static DataType dataTypeForClass(Class parmType) {
        Type type;
        int ordinal = typeForClass(parmType);
        switch (ordinal) {
            case Type.STRING_TYPE:
            case Type.FLOAT_TYPE:
            case Type.INTEGER_TYPE:
            case Type.DATETIME_TYPE:
            case Type.BOOLEAN_TYPE:
            case Type.PERFORMER_TYPE:
                type = BasicType.valueOf(ordinal);
                break;
            case Type.SCHEMA_TYPE:
                // No way for us to tell what the schema itself should be.
                type = new SchemaType();
                break;
            case Type.EXTERNAL_REFERENCE_TYPE:
                type = new ExternalReference("java:" + parmType.getName());
                break;
            default:
                // All other types are deprecated or could never be returned.
                throw new IllegalArgumentException(parmType.toString());
        }
        return new DataType(type);
    }

    public static DataTypes valueOf(String tag) {
        DataTypes dataTypes = (DataTypes)_tagMap.get(tag);
        if (dataTypes == null && tag != null)
            throw new IllegalArgumentException(tag);
        return dataTypes;
    }

    /**
     * Construct a new DataTypes object.
     *
     * @param name
     * @param ordinal The int value
     */
    private DataTypes(String name, int ordinal) {
        super(name, ordinal);
    }

    public List family() {
        return VALUES;
    }
}