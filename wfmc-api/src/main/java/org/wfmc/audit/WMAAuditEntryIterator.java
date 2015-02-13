/*
 *
 * Copyright (c) 2002 Adrian Price.  All rights reserved.
 */

package org.wfmc.audit;

import org.wfmc.wapi.WMIterator;
import org.wfmc.wapi.WMNoMoreDataException;

/**
 * Iterator for retrieving audit entries.
 *
 * @author Adrian Price
 */
public interface WMAAuditEntryIterator extends WMIterator {
    /**
     * Retrieves the next audit entry.
     *
     * @return The audit entry.
     * @throws WMNoMoreDataException if no more data are available.
     */
    WMAAuditEntry tsNext() throws WMNoMoreDataException;
}