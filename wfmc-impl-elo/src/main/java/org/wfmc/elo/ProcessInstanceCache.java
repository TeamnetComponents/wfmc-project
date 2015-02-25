package org.wfmc.elo;

import org.wfmc.elo.model.EloWfmcProcessInstance;

/**
 * @author adrian.zamfirescu
 * @since 25/02/2015
 */
public interface ProcessInstanceCache {

    EloWfmcProcessInstance retrieveEloWfmcProcessInstance(String processInstanceId);

    void createEloWfmcProcessInstance(String processInstanceId, EloWfmcProcessInstance eloWfmcProcessInstance);

    void removeEloWfmcProcessInstance(String processInstanceId);

}
