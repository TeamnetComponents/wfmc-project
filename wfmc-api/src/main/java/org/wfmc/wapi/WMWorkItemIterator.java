/*
 *
 * Copyright (c) 2002 Adrian Price.  All rights reserved.
 */

package org.wfmc.wapi;

/**
 * Iterator for retrieving work items.
 *
 * @author Adrian Price
 */
public interface WMWorkItemIterator extends WMIterator {
    /**
     * Retrieves the next work item.
     *
     * @return The work item.
     * @throws WMNoMoreDataException if no more data are available.
     */
    WMWorkItem tsNext() throws WMNoMoreDataException;
}