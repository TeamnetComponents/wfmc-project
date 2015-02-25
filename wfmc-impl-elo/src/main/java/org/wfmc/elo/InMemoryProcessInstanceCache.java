package org.wfmc.elo;

import org.wfmc.elo.model.EloWfmcProcessInstance;

import java.util.HashMap;
import java.util.Map;

/**
 * @author adrian.zamfirescu
 * @since 25/02/2015
 */
public class InMemoryProcessInstanceCache implements ProcessInstanceCache{

    Map<String,EloWfmcProcessInstance> processInstancesCache;

    public InMemoryProcessInstanceCache(){
        processInstancesCache = new HashMap<>();
    }

    @Override
    public EloWfmcProcessInstance retrieveEloWfmcProcessInstance(String processInstanceId) {
        return processInstancesCache.get(processInstanceId);
    }

    @Override
    public void createEloWfmcProcessInstance(String processInstanceId, EloWfmcProcessInstance eloWfmcProcessInstance) {
        processInstancesCache.put(processInstanceId, eloWfmcProcessInstance);
    }

    @Override
    public void removeEloWfmcProcessInstance(String processInstanceId) {
        processInstancesCache.remove(processInstanceId);
    }

}
