/*
 *
 * Copyright (c) 2002 Adrian Price.  All rights reserved.
 */

package org.wfmc.wapi;

import java.util.Iterator;

/**
 * Special iterator that can return an element count.  The sub-interfaces all
 * provide typesafe <code>tsNext()</code> methods to access the elements of the
 * iteration.
 * @author Adrian Price
 */
public interface WMIterator extends Iterator {
    /**
     * Returns the number of items that matched the query criteria.  N.B. This
     * iterator class is used to return either the objects themselves or a count
     * of the total number of objects.  In the former case the count value may
     * be unknown, in which case the method returns -1.
     * @return Iteration count.
     */
    int getCount();
}
