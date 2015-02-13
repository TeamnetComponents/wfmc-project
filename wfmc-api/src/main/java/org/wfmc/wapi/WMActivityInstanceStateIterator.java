/*
 *
 * Copyright (c) 2002 Adrian Price.  All rights reserved.
 */

package org.wfmc.wapi;

/**
 * Iterator for retrieving activity instance states.
 *
 * @author Adrian Price
 */
public interface WMActivityInstanceStateIterator extends WMIterator {
    /**
     * Retrieves the next activity instance state.
     *
     * @return The activity instance state.
     * @throws WMNoMoreDataException if no more data are available.
     */
    WMActivityInstanceState tsNext() throws WMNoMoreDataException;
}