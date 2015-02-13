package org.wfmc.impl.repository;


import org.wfmc.server.core.xpdl.model.data.DataType;
import org.wfmc.server.core.xpdl.model.data.FormalParameter;

/**
 * Supports key-based event subscriptions.
 *
 * @author Adrian Price
 */
public class EventParameter extends FormalParameter {
    private String _keyExpression;

    public EventParameter() {
    }

    public EventParameter(String id, String mode, DataType dataType,
        String description, String keyExpression) {

        super(id, null, mode, dataType, description);
        _keyExpression = keyExpression;
    }

    public String getKeyExpression() {
        return _keyExpression;
    }

    public void setKeyExpression(String keyExpression) {
        _keyExpression = keyExpression;
    }
}