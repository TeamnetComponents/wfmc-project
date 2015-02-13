package org.wfmc.impl.tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wfmc.impl.WMClient;
import org.wfmc.impl.repository.ToolAgentMetaData;
import org.wfmc.server.core.xpdl.model.activity.ToolType;
import org.wfmc.wapi.WMWorkflowException;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

/**
 * Encapsulates the invocation of a tool (application or procedure).  This class
 * can be used directly by thick Java clients (apps &amp; applets), in order to
 * support tool types other than native executables.  Thin clients can use this
 * class to generate the JavaScript to invoke the requisite application from an
 * HTML browser.
 *
 * @author Adrian Price
 * @see WMClient#executeWorkItem
 */
public final class ToolInvocation implements Serializable {
    private static final long serialVersionUID = -3204963265630362248L;
    private static final Log _logger = LogFactory.getLog(ToolInvocation.class);
    private static final String NULL_METADATA = "null meta-data";
    private static final String NULL_AGENT = "null agent";
    private static final String ILLEGAL_STATUS = "illegal status";
    public static final int META_DATA = 0x1;
    public static final int TOOL_AGENT = 0x2;
    public final String procInstId;
    public final String workItemName;
    public final String workItemId;
    public final String toolId;
    public final int toolIndex;
    public final ToolAgentMetaData metaData;
    public final ToolType toolType;
    public final ToolAgent agent;
    public final Parameter[] parameters;
    public final String description;
    public Exception exception;

    public ToolInvocation(String procInstId, String workItemName,
        String workItemId, String toolId, int toolIndex,
        ToolAgentMetaData metaData, ToolType toolType, ToolAgent agent,
        Parameter[] parameters, String description) {

        this.procInstId = procInstId;
        this.workItemName = workItemName;
        this.workItemId = workItemId;
        this.toolId = toolId;
        this.toolIndex = toolIndex;
        this.metaData = metaData;
        this.agent = agent;
        this.parameters = parameters;
        this.toolType = toolType;
        this.description = description;
    }

    /**
     * Invokes the tool agent, synchronously or asynchronously.
     *
     * @param client The client connection to use.  This must be valid if called
     *               by a remote client, and must be <code>null</code> when called internally
     *               by the workflow engine to invoke a procedure.
     * @param sync   <code>true</code> to invoke the tool synchronously.
     */
    public void invokeTool(final WMClient client, boolean sync) {
        if (agent == null)
            throw new IllegalStateException(NULL_AGENT);
        if (agent.requestAppStatus() != ToolAgent.WAITING)
            throw new IllegalStateException(ILLEGAL_STATUS);

        Runnable runner = new Runnable() {
            public void run() {
                int retCode = ToolAgent.EXIT_CANCEL;
                try {
                    // Inform the workflow engine that the tool is starting.
                    if (client != null)
                        client.toolStarted(procInstId, workItemId);

                    // Invoke the tool.
                    retCode = agent.invokeApplication(ToolInvocation.this);
                } catch (Exception e) {
                    exception = e;
                    _logger.error("Error starting tool: " + toolId, e);
                } finally {
                    try {
                        // Inform the workflow engine that the tool has finished.
                        if (client != null) {
                            client.toolFinished(procInstId, workItemId,
                                agent.requestAppStatus(),
                                retCode == ToolAgent.EXIT_NORMAL ? parameters
                                    : null);
                        }
                    } catch (WMWorkflowException e) {
                        _logger.error("Error from workflow engine", e);
                    }
                }
            }
        };
        // Invoke the tool either on this thread or another, depending on
        // execution mode.
        if (sync)
            runner.run();
        else {
            String name = "ToolAgent[id=" + metaData.getId() + ", workItemId=" +
                workItemId + ']';
            new Thread(runner, name).start();
        }
    }

    /**
     * Renders the JavaScript necessary to invoke the tool from an HTML browser.
     * The implementation optionally calls {@link WMClient#toolStarted} to
     * signal to the workflow engine that tool execution has begun.
     *
     * @param client The client connection to use.
     * @param writer The writer to which the JavaScript should be written.
     * @throws IOException         If a problem occurred when writing the JavaScript.
     * @throws WMWorkflowException If a problem occurred when calling the client.
     */
    public void renderInvocationScript(WMClient client, Writer writer)
        throws IOException, WMWorkflowException {

        if (metaData == null)
            throw new IllegalStateException(NULL_METADATA);
        if (agent == null)
            throw new IllegalStateException(NULL_AGENT);

        // Inform the workflow engine that the tool is starting.
        if (client != null)
            client.toolStarted(procInstId, workItemId);

        // Render the JavaScript.
        agent.renderInvocationScript(this, writer);
    }

    public int requestAppStatus() {
        if (agent == null)
            throw new IllegalStateException(NULL_AGENT);
        return agent.requestAppStatus();
    }

    public void terminateApp() {
        if (agent == null)
            throw new IllegalStateException(NULL_AGENT);
        agent.terminateApp();
    }
}