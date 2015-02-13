/*
 *
 * Copyright (c) 2002 Adrian Price.  All rights reserved.
 */

package org.wfmc.wapi2;

import org.wfmc.wapi.WMIterator;
import org.wfmc.wapi.WMNoMoreDataException;

/**
 * Iterator for retrieving WAPI2 entities.
 *
 * @author Adrian Price
 */
public interface WMEntityIterator extends WMIterator {
    /**
     * Retrieves the next entity.
     *
     * @return The entity.
     * @throws WMNoMoreDataException if no more data are available.
     */
    WMEntity tsNext() throws WMNoMoreDataException;
}