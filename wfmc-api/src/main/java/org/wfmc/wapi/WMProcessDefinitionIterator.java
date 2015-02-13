/*
 *
 * Copyright (c) 2002 Adrian Price.  All rights reserved.
 */

package org.wfmc.wapi;

/**
 * Iterator for retrieving process definitions.
 *
 * @author Adrian Price
 */
public interface WMProcessDefinitionIterator extends WMIterator {
    /**
     * Retrieves the next process definition.
     *
     * @return The process definition.
     * @throws WMNoMoreDataException if no more data are available.
     */
    WMProcessDefinition tsNext() throws WMNoMoreDataException;
}