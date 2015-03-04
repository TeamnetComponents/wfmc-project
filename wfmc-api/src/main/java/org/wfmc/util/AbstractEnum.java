package org.wfmc.util;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * An abstract base implementation for enumerated types.  This class is very
 * similar to the primordial generic Enum class described by JDK 1.5:
 * <p/>
 * <code>public abstract class Enum &lt;T extends Enum&lt;T&gt;&gt;<br/>
 * implements Comparable&lt;T&gt;, Serializable<code>
 *
 * @author Adrian Price
 */
public abstract class AbstractEnum implements Enum {
    private static final long serialVersionUID = -8760826706749553497L;
    public final String name;
    public final int ordinal;

    /**
     * Used by subclasses to initialize their static list and map fields.
     *
     * @param values The complete set of enum values.
     * @param tagMap The map-by-name to initialize.
     * @return An unmodifiable list of enumerated values, in declaration order.
     */
    protected static List clinit(AbstractEnum[] values, Map tagMap) {
        for (int i = 0; i < values.length; i++)
            tagMap.put(values[i].name, values[i]);
        return Collections.unmodifiableList(Arrays.asList(values));
    }

    protected AbstractEnum(String name, int ordinal) {
        this.ordinal = ordinal;
        this.name = name;
    }

    protected final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public final int compareTo(Object o) {
        if (o != null && getClass() != o.getClass()) {
            throw new IllegalArgumentException(getClass().getName() +
                    " cannot be compared to " + o.getClass());
        }
        // (null sorts to the top)
        return o == null ? -1 : ordinal - ((AbstractEnum) o).ordinal;
    }

    public final boolean equals(Object obj) {
        return this == obj;
    }

    public final int hashCode() {
        return ordinal;
    }

    protected final Object readResolve() throws ObjectStreamException {
        try {
            return family().get(ordinal);
        } catch (Exception e) {
            throw new InvalidObjectException(String.valueOf(ordinal));
        }
    }

    public final String toString() {
        return name;
    }

    public final int value() {
        return ordinal;
    }
}