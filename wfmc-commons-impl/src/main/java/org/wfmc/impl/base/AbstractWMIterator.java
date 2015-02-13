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

package org.wfmc.impl.base;

import org.wfmc.wapi.WMIterator;
import org.wfmc.wapi.WMNoMoreDataException;

/**
 * An abstract base iterator class for typesafe WAPI iterators.  Subclasses
 * must implement a typesafe accessor method that follows the pattern:
 * <code>&lt;class-name&gt; &lt;class-name&gt;.tsNext()
 * throws WMNoMoreDataException</code>.
 *
 * @author Adrian Price
 */
public abstract class AbstractWMIterator extends ArrayIterator
    implements WMIterator {

    int _count;

    /**
     * Construct a new iterator for a data array.  The value returned from the
     * {@link #getCount()} method is the length of the array.  The
     * <code>array</code> should not contain <code>null</code> elements - to
     * return just the count, call the {@link #AbstractWMIterator(int)}
     * constructor.
     *
     * @param array The objects over which to iterate.
     */
    protected AbstractWMIterator(Object[] array) {
        super(array);
        _count = array == null ? 0 : array.length;
    }

    /**
     * Construct a new iterator that contains only a count value.  Such an
     * iterator will throw <code>java.lang.NoSuchElementException</code from
     * the <code>next()</code method, and {@link WMNoMoreDataException} from
     * the <code>tsNext()</code> method.
     *
     * @param count The count value.
     */
    protected AbstractWMIterator(int count) {
        super(null);
        _count = count;
    }

    // This constructor will not be required until we implement blocked mode
    // retrieval of WAPI query results.
//    /**
//     * Construct a new iterator that contains both data and a count value. This
//     * constructor should be used when the iterator is operating in blocked
//     * mode; the count value may be greater than the size of the array, or it
//     * can be -1 to signify that the total count is unknown.
//     * @param array The objects over which to iterate.
//     * @param count The count value, or -1 if this is unknown.
//     */
//    protected AbstractWMIterator(Object[] array, int count) {
//        super(array);
//        _count = count;
//    }

    public int getCount() {
        return _count;
    }
}