/*
 *
 * Copyright (c) 2002 Adrian Price.  All rights reserved.
 */

package org.wfmc.wapi;

/**
 * Iterator for retrieving process definition states.
 *
 * @author Adrian Price
 */
public interface WMProcessDefinitionStateIterator extends WMIterator {
    /**
     * Retrieves the next process definition state.
     *
     * @return The process definition state.
     * @throws WMNoMoreDataException if no more data are available.
     */
    WMProcessDefinitionState tsNext() throws WMNoMoreDataException;
}