/*
 *
 * Copyright (c) 2002 Adrian Price.  All rights reserved.
 */

package org.wfmc.wapi;

/**
 * Iterator for retrieving process instances.
 *
 * @author Adrian Price
 */
public interface WMProcessInstanceIterator extends WMIterator {
    /**
     * Retrieves the next process instance.
     *
     * @return The process instance.
     * @throws WMNoMoreDataException if no more data are available.
     */
    WMProcessInstance tsNext() throws WMNoMoreDataException;
}