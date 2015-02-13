/*
 *
 * Copyright (c) 2002 Adrian Price.  All rights reserved.
 */

package org.wfmc.wapi;

/**
 * Iterator for retrieving work item states.
 *
 * @author Adrian Price
 */
public interface WMWorkItemStateIterator extends WMIterator {
    /**
     * Retrieves the next work item state.
     *
     * @return The work item state.
     * @throws WMNoMoreDataException if no more data are available.
     */
    WMWorkItemState tsNext() throws WMNoMoreDataException;
}