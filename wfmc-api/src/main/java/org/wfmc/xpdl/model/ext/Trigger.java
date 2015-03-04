package org.wfmc.xpdl.model.ext;

import org.wfmc.xpdl.PackageVisitor;
import org.wfmc.xpdl.model.XPDLProperties;
import org.wfmc.xpdl.model.misc.Invocation;

/**
 * @author Adrian Price
 */
public abstract class Trigger extends Invocation {
    public static final String COUNT = XPDLProperties.COUNT;

    protected int _count;

    protected Trigger() {
        _count = -1;
    }

    protected Trigger(String id, String count) {
        super(id);
        setCount(count == null ? -1 : Integer.parseInt(count));
    }

    public final int getCount() {
        return _count;
    }

    public final void setCount(int count) {
        if (count < -1 || count == 0)
            throw new IllegalArgumentException("illegal count value: " + count);
        _count = count;
    }

    public final void accept(PackageVisitor visitor) {
        visitor.visit(this);
        super.accept(visitor);
    }
}