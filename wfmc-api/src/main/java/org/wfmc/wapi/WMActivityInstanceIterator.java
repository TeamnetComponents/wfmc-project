/*
 *
 * Copyright (c) 2002 Adrian Price.  All rights reserved.
 */

package org.wfmc.wapi;

/**
 * Iterator for retrieving activity instances.
 *
 * @author Adrian Price
 */
public interface WMActivityInstanceIterator extends WMIterator {
    /**
     * Retrieves the next activity instance.
     *
     * @return The activity instance.
     * @throws WMNoMoreDataException if no more data are available.
     */
    WMActivityInstance tsNext() throws WMNoMoreDataException;
}