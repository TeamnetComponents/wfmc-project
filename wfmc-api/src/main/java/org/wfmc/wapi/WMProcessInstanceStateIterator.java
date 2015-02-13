/*
 *
 * Copyright (c) 2002 Adrian Price.  All rights reserved.
 */

package org.wfmc.wapi;

/**
 * Iterator for retrieving process instance states.
 *
 * @author Adrian Price
 */
public interface WMProcessInstanceStateIterator extends WMIterator {
    /**
     * Retrieves the next process instance state.
     *
     * @return The process instance state.
     * @throws WMNoMoreDataException if no more data are available.
     */
    WMProcessInstanceState tsNext() throws WMNoMoreDataException;
}