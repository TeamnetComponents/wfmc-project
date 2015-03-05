/*--

 Copyright (C) 2002 Anthony Eden.
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
    me@anthonyeden.com.

 4. Products derived from this software may not be called "OBE" or
    "Open Business Engine", nor may "OBE" or "Open Business Engine"
    appear in their name, without prior written permission from
    Anthony Eden (me@anthonyeden.com).

 In addition, I request (but do not require) that you include in the
 end-user documentation provided with the redistribution and/or in the
 software itself an acknowledgement equivalent to the following:
     "This product includes software developed by
      Anthony Eden (http://www.anthonyeden.com/)."

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

 For more information on OBE, please see <http://obe.sourceforge.net/>.

 */
package org.wfmc.wapi;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WMFilter implements Serializable {
    private static final long serialVersionUID = 5124556516649744414L;

    // WFMC-TC-1013: wmapi2.h claims there are 255 reserved filter types with
    // ordinals in the range 1..255, however, these constants are not defined
    // anywhere.  So here's an arbitrary stab at assigning two types:
    public static final int SIMPLE_TYPE = 1;
    public static final int SQL_TYPE = 2;

    // WFMC-TC-1013 isn't clear, but it appears that comparisons are represented
    // by integer codes formed from the operators' 8-bit ASCII character codes.
    public static final int NO = 0;
    public static final int LT = '<';
    public static final int LE = '<' << 8 | '=';
    public static final int EQ = '=';
    public static final int NE = '!' << 8 | '=';
    public static final int GE = '>' << 8 | '=';
    public static final int GT = '>';

    // N.B. These are SQL-style operator symbols, not Java.
    private static final String LT_STR = "<";
    private static final String LE_STR = "<=";
    private static final String EQ_STR = "=";
    private static final String NE_STR = "<>";
    private static final String GE_STR = ">=";
    private static final String GT_STR = ">";
    private static final Map SQL_COMPARISONS = new HashMap();

    private int filterType;
    private String attributeName;
    private int attributeType;
    private int comparison;
    private Object filterValue;

    static {
        SQL_COMPARISONS.put(new Integer(LT), LT_STR);
        SQL_COMPARISONS.put(new Integer(LE), LE_STR);
        SQL_COMPARISONS.put(new Integer(EQ), EQ_STR);
        SQL_COMPARISONS.put(new Integer(NE), NE_STR);
        SQL_COMPARISONS.put(new Integer(GE), GE_STR);
        SQL_COMPARISONS.put(new Integer(GT), GT_STR);
    }

    /**
     * Construct a new filter based on the value of a single attribute. The
     * attribute must be of type {@link WMAttribute#BOOLEAN_TYPE}.
     *
     * @param attributeName  The name of the attribute to test.
     * @param comparison     The comparison to use, either {@link #EQ} or
     *                       {@link #NE}.
     * @param attributeValue The boolean value to test.
     */
    public WMFilter(String attributeName, int comparison,
                    Boolean attributeValue) {

        this(SIMPLE_TYPE, attributeName, WMAttribute.BOOLEAN_TYPE, comparison,
                attributeValue);
    }

    /**
     * Construct a new filter based on the value of a single attribute. The
     * attribute must be of type {@link WMAttribute#BOOLEAN_TYPE}.
     *
     * @param attributeName  The name of the attribute to test.
     * @param comparison     The comparison to use, either {@link #EQ} or
     *                       {@link #NE}.
     * @param attributeValue The boolean value to test.
     */
    public WMFilter(String attributeName, int comparison,
                    boolean attributeValue) {

        this(SIMPLE_TYPE, attributeName, WMAttribute.BOOLEAN_TYPE, comparison,
                attributeValue ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * Construct a new filter based on the value of a single attribute. The
     * attribute must be of type {@link WMAttribute#DATETIME_TYPE}.
     *
     * @param attributeName  The name of the attribute to test.
     * @param comparison     The comparison to use, one of:
     *                       {@link #LT}, {@link #LE}, {@link #EQ}, {@link #NE}, {@link #GE},
     *                       {@link #GT}.
     * @param attributeValue The date value to test.
     */
    public WMFilter(String attributeName, int comparison,
                    Date attributeValue) {

        this(SIMPLE_TYPE, attributeName, WMAttribute.DATETIME_TYPE, comparison,
                attributeValue);
    }

    /**
     * Construct a new filter based on the value of a single attribute. The
     * attribute must be of type {@link WMAttribute#FLOAT_TYPE}.
     *
     * @param attributeName  The name of the attribute to test.
     * @param comparison     The comparison to use, one of:
     *                       {@link #LT}, {@link #LE}, {@link #EQ}, {@link #NE}, {@link #GE},
     *                       {@link #GT}.
     * @param attributeValue The floating point value to test.
     */
    public WMFilter(String attributeName, int comparison,
                    Double attributeValue) {

        this(SIMPLE_TYPE, attributeName, WMAttribute.FLOAT_TYPE, comparison,
                attributeValue);
    }

    /**
     * Construct a new filter based on the value of a single attribute. The
     * attribute must be of type {@link WMAttribute#FLOAT_TYPE}.
     *
     * @param attributeName  The name of the attribute to test.
     * @param comparison     The comparison to use, one of:
     *                       {@link #LT}, {@link #LE}, {@link #EQ}, {@link #NE}, {@link #GE},
     *                       {@link #GT}.
     * @param attributeValue The floating point value to test.
     */
    public WMFilter(String attributeName, int comparison,
                    double attributeValue) {

        this(SIMPLE_TYPE, attributeName, WMAttribute.FLOAT_TYPE, comparison,
                new Double(attributeValue));
    }

    /**
     * Construct a new filter based on the value of a single attribute. The
     * attribute must be of type {@link WMAttribute#INTEGER_TYPE}.
     *
     * @param attributeName  The name of the attribute to test.
     * @param comparison     The comparison to use, one of:
     *                       {@link #LT}, {@link #LE}, {@link #EQ}, {@link #NE}, {@link #GE},
     *                       {@link #GT}.
     * @param attributeValue The integer value to test.
     */
    public WMFilter(String attributeName, int comparison,
                    Integer attributeValue) {

        this(SIMPLE_TYPE, attributeName, WMAttribute.INTEGER_TYPE, comparison,
                attributeValue);
    }

    /**
     * Construct a new filter based on the value of a single attribute. The
     * attribute must be of type {@link WMAttribute#INTEGER_TYPE}.
     *
     * @param attributeName  The name of the attribute to test.
     * @param comparison     The comparison to use, one of:
     *                       {@link #LT}, {@link #LE}, {@link #EQ}, {@link #NE}, {@link #GE},
     *                       {@link #GT}.
     * @param attributeValue The integer value to test.
     */
    public WMFilter(String attributeName, int comparison,
                    int attributeValue) {

        this(SIMPLE_TYPE, attributeName, WMAttribute.INTEGER_TYPE, comparison,
                new Integer(attributeValue));
    }

    /**
     * Construct a new filter based on the value of a single attribute. The
     * attribute must be of type {@link WMAttribute#STRING_TYPE} or
     * {@link WMAttribute#PERFORMER_TYPE}.
     *
     * @param attributeName  The name of the attribute to test.
     * @param comparison     The comparison to use, one of:
     *                       {@link #LT}, {@link #LE}, {@link #EQ}, {@link #NE}, {@link #GE},
     *                       {@link #GT}.
     * @param attributeValue The string value to test.
     */
    public WMFilter(String attributeName, int comparison,
                    String attributeValue) {

        this(SIMPLE_TYPE, attributeName, WMAttribute.STRING_TYPE, comparison,
                attributeValue);
    }

    /**
     * Construct a new filter based on an SQL query predicate.  The predicate
     * must only reference fields defined for the entity type to be queried.
     *
     * @param sqlString A standard SQL-92 WHERE clause predicate.  Joins are
     *                  not supported.
     */
    public WMFilter(String sqlString) {
        this(SQL_TYPE, null, WMAttribute.STRING_TYPE, NO, sqlString);
    }

    private WMFilter(int filterType, String attributeName, int attributeType,
                     int comparison, Object filterValue) {

        if (filterType != SIMPLE_TYPE && filterType != SQL_TYPE)
            throw new IllegalArgumentException("filterType: " + filterType);
        if (filterType == SIMPLE_TYPE &&
                comparison != LT &&
                comparison != LE &&
                comparison != EQ &&
                comparison != NE &&
                comparison != GE &&
                comparison != GT) {

            throw new IllegalArgumentException("comparison: " +
                    String.valueOf(comparison));
        }
        if (filterType == SIMPLE_TYPE) {
            if (attributeName != null)
                attributeName = attributeName.trim();
            if (attributeName == null || attributeName.length() == 0) {
                throw new IllegalArgumentException("attributeName: " +
                        attributeName);
            }
        }
        this.filterType = filterType;
        this.attributeName = attributeName;
        this.attributeType = attributeType;
        this.comparison = comparison;
        this.filterValue = filterValue;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public int getAttributeType() {
        return attributeType;
    }

    public int getComparison() {
        return comparison;
    }

    public String getFilterString() {
        return filterValue == null ? null : filterValue.toString();
    }

    public int getFilterType() {
        return filterType;
    }

    public Object getFilterValue() {
        return filterValue;
    }

    public String getSQLComparison() {
        return (String) SQL_COMPARISONS.get(new Integer(comparison));
    }

    public String toString() {
        return "WMFilter[filterType=" + filterType
                + ", attributeName='" + attributeName + '\''
                + ", comparison=" + comparison
                + ", filterValue='" + filterValue + '\''
                + ']';
    }
}