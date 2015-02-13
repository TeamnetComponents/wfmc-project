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

package org.wfmc.impl.tool;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;

/**
 * Enables the workflow engine or a client to invoke a tool (application or
 * procedure).  Implementations can be stateful only to the extent of containing
 * service configuration information; their methods must be threadsafe.
 *
 * @author Adrian Price
 */
public interface ToolAgent {
    /**
     * The tool has not yet been invoked.
     */
    int WAITING = 0;
    /**
     * The tool agent is running.
     */
    int RUNNING = 1;
    /**
     * The tool is active.
     */
    int ACTIVE = 2;
    /**
     * The agent or tool encountered an error.
     */
    int ERROR = 3;
    /**
     * The tool was terminated.
     */
    int TERMINATED = 4;
    /**
     * The tool has finished running.
     */
    int FINISHED = 5;
    /**
     * The tool finished normally.
     */
    int EXIT_NORMAL = 0;
    /**
     * The tool was cancelled by the user.
     */
    int EXIT_CANCEL = 1;

    /**
     * Renders the JavaScript necessary to invoke the tool from an HTML browser.
     *
     * @param ti
     * @param writer
     */
    void renderInvocationScript(ToolInvocation ti, Writer writer)
        throws IOException;

    /**
     * Invokes the tool.  The implementation must update the values of any INOUT
     * and OUT parameters appropriately, and should set the values of any IN
     * parameters to <code>null</code>.
     *
     * @param ti The tool invocation context.
     * @return The tool's exit code. One of {@link #EXIT_NORMAL},
     *         {@link #EXIT_CANCEL}.
     * @throws IllegalStateException     if the ToolAgent is not in the
     *                                   {@link #WAITING} state.
     * @throws InvocationTargetException As thrown prior to or during during
     *                                   tool execution.
     */
    int invokeApplication(ToolInvocation ti)
        throws InvocationTargetException;

    /**
     * Returns the tool status.
     *
     * @return One of: {@link #WAITING}, {@link #RUNNING}, {@link #ACTIVE},
     *         {@link #TERMINATED}, {@link #FINISHED}.
     */
    int requestAppStatus();

    /**
     * Terminates the running tool.
     *
     * @throws IllegalStateException if the tool is not running.
     */
    void terminateApp();
}