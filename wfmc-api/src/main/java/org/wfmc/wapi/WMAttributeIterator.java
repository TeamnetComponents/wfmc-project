/*
 *
 * Copyright (c) 2002 Adrian Price.  All rights reserved.
 */

package org.wfmc.wapi;

/**
 * Iterator for retrieving attributes.
 *
 * @author Adrian Price
 */
public interface WMAttributeIterator extends WMIterator {
    /**
     * Retrieves the next attribute.
     *
     * @return The attribute.
     * @throws WMNoMoreDataException if no more data are available.
     */
    WMAttribute tsNext() throws WMNoMoreDataException;
}