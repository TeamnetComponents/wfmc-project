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

package org.wfmc.xpdl;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Base exception for all exceptions in the Open Business Engine.
 *
 * @author Anthony Eden
 */
public class XPDLException extends Exception {
// JDK1.4 introduced chained throwables, and printStackTrace() now traverses
// the chain to the root cause, printing all stack traces.
    private static final boolean JDK1_4;
    private static final String CAUSED_BY = "Caused by: ";

    private final Throwable _cause;

    static {
        boolean jdk1_4;
        try {
            Throwable.class.getMethod("getCause", new Class[]{});
            jdk1_4 = true;
        } catch (NoSuchMethodException e) {
            jdk1_4 = false;
        }
        JDK1_4 = jdk1_4;
    }

    /**
     * Construct a new OBEException.
     */
    public XPDLException() {
        _cause = null;
    }

    /**
     * Construct a new OBEException with the given message.
     *
     * @param message The message
     */
    public XPDLException(String message) {
        super(message);
        _cause = null;
    }

    /**
     * Construct a new OBEException with the given nested error.
     *
     * @param t The nested error
     */
    public XPDLException(Throwable t) {
        super(t.getMessage());
        _cause = t;
    }

    /**
     * Construct a new OBEException with the given messager and nested
     * error.
     *
     * @param message The message
     * @param t       The nested error
     */
    public XPDLException(String message, Throwable t) {
        super(message);
        _cause = t;
    }

    /**
     * Get the nested error.
     *
     * @return The nested error
     */
    public Throwable getCause() {
        return _cause;
    }

    public final void printStackTrace() {
        printStackTrace(System.err);
    }

    public final void printStackTrace(PrintStream stream) {
        super.printStackTrace(stream);
        // Only print causal stack traces if pre-JDK 1.4.
        if (!JDK1_4) {
            Throwable t = _cause;
            while (t != null) {
                stream.println(CAUSED_BY + t);
                t.printStackTrace(stream);
                if (t instanceof XPDLException)
                    t = ((XPDLException)t)._cause;
                else
                    break;
            }
        }
    }

    public final void printStackTrace(PrintWriter writer) {
        super.printStackTrace(writer);
        // Only print causal stack traces if pre-JDK 1.4.
        if (!JDK1_4) {
            Throwable t = _cause;
            while (t != null) {
                writer.println(CAUSED_BY + t);
                t.printStackTrace(writer);
                if (t instanceof XPDLException)
                    t = ((XPDLException)t)._cause;
                else
                    break;
            }
        }
    }
}