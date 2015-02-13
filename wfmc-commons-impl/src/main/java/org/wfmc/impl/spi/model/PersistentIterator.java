/*--

 Copyright (C) 2002-2005 Adrian Price.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions, and the disclaimer that follows
    these conditions in the documentation and/or other materials
    provided with the distribution.

 3. The names "OBE" and "Open Business Engine" must not be used to
 	endorse or promote products derived from this software without prior
 	written permission.  For written permission, please contact
 	adrianprice@sourceforge.net.

 4. Products derived from this software may not be called "OBE" or
 	"Open Business Engine", nor may "OBE" or "Open Business Engine"
 	appear in their name, without prior written permission from
 	Adrian Price (adrianprice@users.sourceforge.net).

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR(S) BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.

 For more information on OBE, please see
 <http://obe.sourceforge.net/>.

 */

package org.wfmc.impl.spi.model;

import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Supports persistent iteration over arbitrary content.  This iterator can be
 * serialized, deserialized, and used to continue iteration.  The target content
 * is not persisted, and the caller must check whether it is still available by
 * calling {@link #hasContent}, and restore the original content if necessary
 * by calling {@link #setContent}.
 *
 * @author Adrian Price
 */
public interface PersistentIterator extends Serializable, Iterator {
    /**
     * Checks whether the target content is available.  The target content is
     * cleared during object serialization, and must be restored externally
     * by calling {@link #setContent} before continuing iteration over a
     * deserialized instance of this class.
     *
     * @return <code>true</code> if the content is available.
     */
    boolean hasContent();

    /**
     * Returns whether the iterator has changed since it was created or last
     * deserialized.  The modified flag is set by {@link #next}, {@link #reset},
     * and {@link #setContent}, and cleared only be serialization.  The flag
     * indicates that the iterator's persistent state needs to be updated.
     *
     * @return <code>true</code> if the iterator has changed.
     */
    boolean isModified();

    /**
     * Resets the iterator, clearing the content, index and internal fields.
     * The iterator must be re-initialied by calling {@link #setContent} before
     * it can be used to perform further iterations.  This method sets the
     * {@link #isModified modified flag}.
     */
    void reset();

    /**
     * Sets the content over which to iterate (or continue iteration).  This
     * method sets the {@link #isModified modified flag}.
     *
     * @param content The target array.
     * @throws IllegalArgumentException if the object is not of the expected
     *                                  class.
     * @throws ConcurrentModificationException
     *                                  if the content has
     *                                  changed since it was first set.
     */
    void setContent(Object content);
}