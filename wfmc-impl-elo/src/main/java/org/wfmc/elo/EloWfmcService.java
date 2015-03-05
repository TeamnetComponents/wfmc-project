package org.wfmc.elo;

import de.elo.ix.client.*;
import de.elo.utils.net.RemoteException;
import org.wfmc.elo.model.EloConstants;
import org.wfmc.elo.model.ELOWfMCProcessInstanceAttributes;
import org.wfmc.elo.model.EloWfmcObjKey;
import org.wfmc.elo.model.EloWfmcProcessInstance;
import org.wfmc.service.WfmcServiceImpl_Abstract;
import org.wfmc.wapi.*;
import org.wfmc.wapi2.WMEntity;
import org.wfmc.wapi2.WMEntityIterator;

import java.util.Properties;

/**
 * Created by Lucian.Dragomir on 2/10/2015.
 */
public class EloWfmcService extends WfmcServiceImpl_Abstract {

    private IXConnection eloConnection;
    private ProcessInstanceCache processInstanceCache;

    @Override
    public boolean isWorkListHandlerProfileSupported() {
        return false;
    }

    @Override
    public boolean isProcessControlStatusProfileSupported() {
        return false;
    }

    @Override
    public boolean isProcessDefinitionProfileSupported() {
        return false;
    }

    @Override
    public boolean isProcessAdminProfileSupported() {
        return false;
    }

    @Override
    public boolean isActivityControlStatusProfileSupported() {
        return false;
    }

    @Override
    public boolean isActivityAdminProfileSupported() {
        return false;
    }

    @Override
    public boolean isEntityHandlerProfileSupported() {
        return false;
    }

    @Override
    public boolean isAuditRecordProfileSupported() {
        return false;
    }

    @Override
    public boolean isToolAgentProfileSupported() {
        return false;
    }

    @Override
    public WMEntity createEntity(WMEntity scopingEntity, String entityClass, String entityName) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMEntityIterator listEntities(WMEntity scopingEntity, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public void deleteEntity(WMEntity scopingEntity, String entityId) throws WMWorkflowException {

    }

    @Override
    public WMAttributeIterator listEntityAttributes(WMEntity scopingEntity, String entityId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMAttribute getEntityAttributeValue(WMEntity scopingEntity, WMEntity entityHandle, String attributeName) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMAttributeIterator listEntityAttributeValues(WMEntity scopingEntity, String entityHandle, String attributeName) throws WMWorkflowException {
        return null;
    }

    @Override
    public void assignEntityAttributeValue(WMEntity entityHandle, String attributeName, int attributeType, String attributeValue) throws WMWorkflowException {

    }

    @Override
    public void clearEntityAttributeList(WMEntity entityHandle, String attributeName) throws WMWorkflowException {

    }

    @Override
    public void addEntityAttributeValue(WMEntity entityHandle, String attributeName, int attributeType, String attributeValue) throws WMWorkflowException {

    }

    @Override
    public WMEntity openWorkflowDefinition(String name, String scope) throws WMWorkflowException {
        return null;
    }

    @Override
    public void closeWorkflowDefinition(WMEntity workflowDefinitionHandle) throws WMWorkflowException {

    }

    @Override
    public String createPackage() throws WMWorkflowException {
        return null;
    }

    @Override
    public void deleteProcessDefinition(String processDefinitionId) throws WMWorkflowException {

    }

    @Override
    public WMEntity openProcessDefinition(String procDefId) throws WMWorkflowException {
        return null;
    }

    @Override
    public void closeProcessDefinition(WMEntity procModelHandle) throws WMWorkflowException {

    }

    @Override
    public WMEntity addTransition(String procModelId, String sourceActDefId, String targetActDefId) throws WMWorkflowException {
        return null;
    }

    @Override
    public void addProcessDataAttribute(String procModelId, String procDataId, String attributeName, int attributeType, int attributeLength, String attributeValue) throws WMWorkflowException {

    }

    @Override
    public void removeProcessDataAttribute(String procModelId, String procDataId, String attributeName) throws WMWorkflowException {

    }

    /**
     * Metoda creaza o conexiune cu server-ul de ELO.
     * @param connectInfo Este un obiect de tipul WMConnectInfo ce contine urmatoarele campuri:
     *                    userIdentification - reprezinta numele utilizatorului;
     *                    password - parola utilizatorului
     *                    engineName - numele masinii de pe care te conectezi;
     *                    scope - reprezinta ELO index server (IX)
     * @throws WMWorkflowException
     */
    @Override
    public void connect(WMConnectInfo connectInfo) throws WMWorkflowException {

        Properties connProps = IXConnFactory.createConnProps(connectInfo.getScope());
        Properties sessOpts = IXConnFactory.createSessionOptions("IX-Example", "1.0");
        IXConnFactory connFact = null;
        try {
            connFact = new IXConnFactory(connProps, sessOpts);
            eloConnection = connFact.create(connectInfo.getUserIdentification(),
                                            connectInfo.getPassword(),
                                            connectInfo.getEngineName(),
                                            connectInfo.getUserIdentification());
        } catch (RemoteException remoteException) {
            throw new WMWorkflowException(remoteException);
        }

    }

    /**
     * Metoda distruge conexiunea la server-ul de ELO.
     * @throws WMWorkflowException
     */
    @Override
    public void disconnect() throws WMWorkflowException {
        if (eloConnection != null) {
            eloConnection.logout();
            eloConnection = null;
        }
    }

    @Override
    public WMProcessDefinitionIterator listProcessDefinitions(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMProcessDefinitionStateIterator listProcessDefinitionStates(String procDefId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public void changeProcessDefinitionState(String procDefId, WMProcessDefinitionState newState) throws WMWorkflowException {

    }

    /**
     * Metoda creaza o instanta de proces in functie de un process definition si returneaza id-ul acestei instante.
     * @param procDefId    Reprezinta id-ul unui process definition in functie de care o sa fie creat aceasta instanta.
     * @param procInstName Reprezinta numele pe care o sa il aibe instanta.
     * @return Id-ul instantei de proces
     * @throws WMWorkflowException
     */
    @Override
    public String createProcessInstance(String procDefId, String procInstName) throws WMWorkflowException {

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();
        eloWfmcProcessInstance.setProcessDefinitionId(procDefId);
        eloWfmcProcessInstance.setName(procInstName);

        String processInstanceId = procDefId + procInstName;

        processInstanceCache.createEloWfmcProcessInstance(processInstanceId, eloWfmcProcessInstance);

        return processInstanceId;
    }

    /**
     * Metoda porneste o instanta de proces in functie de id-ul pe care il primim cand apelam metoda createProcessInstance
     * si returneaza id-ul procesului pornit care e diferit de id-ul instantei de proces sau de id-ul lui process definition.
     * Inainte de a da startProcess trebuie sa apelam si assignProcessInstanceAttribute pentru a adauga un sord acelei
     * instante de proces ce va fi pornita.
     * @param procInstId  Reprezinta id-ul unei instante de proces. Acesta trebuie sa fie id-ul unei instante existente, altfel o sa arunce un WMWorkflowException.
     * @return Id-ul procesului pornit.
     * @throws WMWorkflowException
     */
    @Override
    public String startProcess(String procInstId) throws WMWorkflowException {

        EloWfmcProcessInstance wmProcessInstance = (EloWfmcProcessInstance)getProcessInstance(procInstId);
        try {
            int workspaceId = eloConnection.ix().startWorkFlow(wmProcessInstance.getProcessDefinitionId(),
                                             wmProcessInstance.getName(),
                                             ((ELOWfMCProcessInstanceAttributes)wmProcessInstance.getEloWfMCProcessInstanceAttributes()).getSordId());

            // remove temporary process instance
            abortProcessInstance(procInstId);

            return Integer.toString(workspaceId);
        } catch (RemoteException remoteException) {
            throw new WMWorkflowException(remoteException);
        }

    }

    @Override
    public void terminateProcessInstance(String procInstId) throws WMWorkflowException {

    }

    @Override
    public WMProcessInstanceStateIterator listProcessInstanceStates(String procInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public void changeProcessInstanceState(String procInstId, WMProcessInstanceState newState) throws WMWorkflowException {

    }

    @Override
    public WMAttributeIterator listProcessInstanceAttributes(String procInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMAttribute getProcessInstanceAttributeValue(String procInstId, String attrName) throws WMWorkflowException {
        return null;
    }

    /**
     * Metoda adauga un sord pe o instanta de proces pentru a putea fi pornita.
     * @param procInstId  Reprezinta id-ul unei instante de proces. Acesta trebuie sa fie id-ul unei instante existente, altfel o sa arunce un WMWorkflowException.
     * @param attrName   Numele.
     * @param attrValue  Id-ul sordului ce va fi pus pe proces.
     * @throws WMWorkflowException
     */
    @Override
    /**
     * A process instance will have a single attribute - the Sord ID (the flow attributes will rest in {@link org.wfmc.elo.model.ELOWfMCProcessInstanceAttributes})
     */
    public void assignProcessInstanceAttribute(String procInstId, String attrName, Object attrValue) throws WMWorkflowException {

        EloWfmcProcessInstance eloWfmcProcessInstance = (EloWfmcProcessInstance)getProcessInstance(procInstId);
        //todo Daniel: de  verificat daca procesul are deja setat eloWfmcProcessInstance pe el. Daca nu are inseamna ca este primul atribut primit pe metoda.
        //Daca este primul atribut primit verificam ca parametru este ori SORDID ordi MASKID. Daca nu e niciunul dam eroare. Daca este unul ditre ele,
        // cream un eloWfmcProcessInstance nou cu sord-ul sau mask-ul primit. Daca procesul are deja setat eloWfmcProcessInstance pe el inseamna ca atributul primit este unul de
        //pe sord si validam asta. (Avem sordul, in care avem objectKeys) Daca nu il gasim dam eroare. Daca il gasim il setam pe sord si in cache (adica pe
        //eloWfmcProcessInstance).

        if (eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes() == null) {
            //treat SORD_ID
            if (attrName.equals(EloConstants.SORD_ID)) {
                try {
                    if (eloConnection.ix().checkoutSord(String.valueOf(attrValue), SordC.mbAll, LockC.NO) != null) {
                        Sord sord = eloConnection.ix().checkoutSord((String) attrValue, SordC.mbAll, LockC.NO);
                        ELOWfMCProcessInstanceAttributes eloWfMCProcessInstanceAttributes = new ELOWfMCProcessInstanceAttributes((String) attrValue);
                        eloWfMCProcessInstanceAttributes.setSordId((String) attrValue);
                        eloWfMCProcessInstanceAttributes.setMaskId(String.valueOf(sord.getMask()));

                        EloWfmcObjKey[] eloWfmcObjKeys = new EloWfmcObjKey[sord.getObjKeys().length];
                        for (int i = 0; i < sord.getObjKeys().length; i++) {
                            eloWfmcObjKeys[i] = new EloWfmcObjKey();
                            eloWfmcObjKeys[i].setId(sord.getObjKeys()[i].getId());
                            eloWfmcObjKeys[i].setObjId(sord.getObjKeys()[i].getObjId());
                            eloWfmcObjKeys[i].setName(sord.getObjKeys()[i].getName());
                            eloWfmcObjKeys[i].setValue(sord.getObjKeys()[i].getData());
                        }
                        eloWfMCProcessInstanceAttributes.setObjKeys(eloWfmcObjKeys);
                        eloWfmcProcessInstance.setEloWfMCProcessInstanceAttributes(eloWfMCProcessInstanceAttributes);
                    }
                } catch (RemoteException e) {
                    throw new WMAttributeAssignmentException(attrName);
                }
            }
            //treat MASK_ID
            else if (attrName.equals(EloConstants.MASK_ID)) {
                try {
                    if (eloConnection.ix().checkoutDocMask(String.valueOf(attrValue), DocMaskC.mbAll, LockC.NO) != null) {
                        Sord sord = eloConnection.ix().createSord("1", (String) attrValue, SordC.mbAll);
                        ELOWfMCProcessInstanceAttributes eloWfMCProcessInstanceAttributes = new ELOWfMCProcessInstanceAttributes(String.valueOf(sord.getId()));
                        eloWfMCProcessInstanceAttributes.setMaskId((String) attrValue);
                        eloWfMCProcessInstanceAttributes.setSordId((String.valueOf(sord.getId())));

                        EloWfmcObjKey[] eloWfmcObjKeys = new EloWfmcObjKey[sord.getObjKeys().length];
                        for (int i = 0; i < sord.getObjKeys().length; i++) {
                            eloWfmcObjKeys[i] = new EloWfmcObjKey();
                            eloWfmcObjKeys[i].setId(sord.getObjKeys()[i].getId());
                            eloWfmcObjKeys[i].setObjId(sord.getObjKeys()[i].getObjId());
                            eloWfmcObjKeys[i].setName(sord.getObjKeys()[i].getName());
                            eloWfmcObjKeys[i].setValue(sord.getObjKeys()[i].getData());
                        }
                        eloWfMCProcessInstanceAttributes.setObjKeys(eloWfmcObjKeys);
                        eloWfmcProcessInstance.setEloWfMCProcessInstanceAttributes(eloWfMCProcessInstanceAttributes);
                        eloConnection.ix().checkinSord(sord, SordC.mbAll, LockC.YES);
                    }
                } catch (RemoteException e) {
                    throw new WMAttributeAssignmentException(attrName);
                }
            } else {
                //treat MASK_ID found in comment in first node of workflow
                try {
                    WFDiagram theWorkflow = eloConnection.ix().checkoutWorkFlow(eloWfmcProcessInstance.getProcessDefinitionId(), WFTypeC.TEMPLATE, WFDiagramC.mbAll, LockC.YES);
                    WFNode theFirstNode = theWorkflow.getNodes()[0];
                    String comment = theFirstNode.getComment();
                    if (comment.contains("Mask=")) {
                        int startPosition = comment.indexOf("Mask=");
                        int endPosition = comment.indexOf(";", startPosition);
                        String substring = comment.substring(startPosition + 5, endPosition);
                        Sord sord = eloConnection.ix().createSord("1", substring, SordC.mbAll);
                        ELOWfMCProcessInstanceAttributes eloWfMCProcessInstanceAttributes = new ELOWfMCProcessInstanceAttributes(String.valueOf(sord.getId()));
                        eloWfMCProcessInstanceAttributes.setMaskId((substring));
                        eloWfMCProcessInstanceAttributes.setSordId((String.valueOf(sord.getId())));
                        EloWfmcObjKey[] eloWfmcObjKeys = new EloWfmcObjKey[sord.getObjKeys().length];
                        for (int i = 0; i < sord.getObjKeys().length; i++) {
                            eloWfmcObjKeys[i] = new EloWfmcObjKey();
                            eloWfmcObjKeys[i].setId(sord.getObjKeys()[i].getId());
                            eloWfmcObjKeys[i].setObjId(sord.getObjKeys()[i].getObjId());
                            eloWfmcObjKeys[i].setName(sord.getObjKeys()[i].getName());
                            eloWfmcObjKeys[i].setValue(sord.getObjKeys()[i].getData());
                        }
                        eloWfMCProcessInstanceAttributes.setObjKeys(eloWfmcObjKeys);
                        eloWfmcProcessInstance.setEloWfMCProcessInstanceAttributes(eloWfMCProcessInstanceAttributes);
                        eloConnection.ix().checkinSord(sord, SordC.mbAll, LockC.YES);
                    } else {
                        throw new WMWorkflowException("Mask not found on first node of the workflow!"); //TODO Adi: de scos textele de eroare intr-un fisier de proprietati
                    }
                } catch (RemoteException e) {
                    throw new WMWorkflowException(e);
                }
            }
        } else {
            ELOWfMCProcessInstanceAttributes eloWfMCProcessInstanceAttributes = (ELOWfMCProcessInstanceAttributes)eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes();
            EloWfmcObjKey[] objKeys = eloWfMCProcessInstanceAttributes.getObjKeys();
            boolean existAttribute = false;
            for (int i = 0; i < objKeys.length; i++) {

                if (attrName.equals(objKeys[i].getName())) {
                    existAttribute = true;

                    if (attrValue instanceof String) {
                        String[] values = new String[]{attrValue.toString()};
                        objKeys[i].setValue(values);
                    } else if (attrValue instanceof String[]) {
                        String[] values = new String[((String[]) attrValue).length];
                        for (int j = 0; j < ((String[]) attrValue).length; j++ ){
                            values[j] = new String();
                            values[j] = ((String[]) attrValue)[j];
                        }
                        objKeys[i].setValue(values);
                    }
                }
            }
            eloWfMCProcessInstanceAttributes.setObjKeys(objKeys);
            eloWfmcProcessInstance.setEloWfMCProcessInstanceAttributes(eloWfMCProcessInstanceAttributes);
            if (existAttribute == false) {
                throw new WMInvalidAttributeException(attrName);
            }
        }
    }

    @Override
    public WMActivityInstanceStateIterator listActivityInstanceStates(String procInstId, String actInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public void changeActivityInstanceState(String procInstId, String actInstId, WMActivityInstanceState newState) throws WMWorkflowException {

    }

    @Override
    public WMAttributeIterator listActivityInstanceAttributes(String procInstId, String actInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMAttribute getActivityInstanceAttributeValue(String procInstId, String actInstId, String attrName) throws WMWorkflowException {
        return null;
    }

    @Override
    public void assignActivityInstanceAttribute(String procInstId, String actInstId, String attrName, Object attrValue) throws WMWorkflowException {

    }

    @Override
    public WMProcessInstanceIterator listProcessInstances(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    /**
     * Metoda intoarce o instanta de proces.
     * @param procInstId Reprezinta id-ul unei instante de proces. Acesta trebuie sa fie id-ul unei instante existente, altfel o sa arunce un WMWorkflowException.
     * @return Instanta dorita.
     * @throws WMWorkflowException
     */
    @Override
    public WMProcessInstance getProcessInstance(String procInstId) throws WMWorkflowException {
        return processInstanceCache.retrieveEloWfmcProcessInstance(procInstId);
    }

    @Override
    public WMActivityInstanceIterator listActivityInstances(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMActivityInstance getActivityInstance(String procInstId, String actInstId) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMWorkItemIterator listWorkItems(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return  null;
    }

    @Override
    public WMWorkItem getWorkItem(String procInstId, String workItemId) throws WMWorkflowException {
        return null;
    }

    @Override
    public void completeWorkItem(String procInstId, String workItemId) throws WMWorkflowException {

    }

    @Override
    public WMWorkItemStateIterator listWorkItemStates(String procInstId, String workItemId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public void changeWorkItemState(String procInstId, String workItemId, WMWorkItemState newState) throws WMWorkflowException {

    }

    /**
     * Metoda atribuie un task unui utilizator.
     * @param sourceUser Utilizatorul care are atribuit task-ul.
     * @param targetUser Utilizatorul caruia i se va atribui task-ul.
     * @param procInstId Id-ul procesului.
     * @param workItemId Id-ul task-ului.
     * @throws WMWorkflowException
     */
    @Override
    public void reassignWorkItem(String sourceUser, String targetUser, String procInstId, String workItemId) throws WMWorkflowException {
    }

    @Override
    public WMAttributeIterator listWorkItemAttributes(String procInstId, String workItemId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMAttribute getWorkItemAttributeValue(String procInstId, String workItemId, String attrName) throws WMWorkflowException {
        return null;
    }

    @Override
    public void assignWorkItemAttribute(String procInstId, String workItemId, String attrName, Object attrValue) throws WMWorkflowException {

    }

    @Override
    public void changeProcessInstancesState(String procDefId, WMFilter filter, WMProcessInstanceState newState) throws WMWorkflowException {

    }

    @Override
    public void changeActivityInstancesState(String procDefId, String actDefId, WMFilter filter, WMActivityInstanceState newState) throws WMWorkflowException {

    }

    @Override
    public void terminateProcessInstances(String procDefId, WMFilter filter) throws WMWorkflowException {

    }

    @Override
    public void assignProcessInstancesAttribute(String procDefId, WMFilter filter, String attrName, Object attrValue) throws WMWorkflowException {

    }

    @Override
    public void assignActivityInstancesAttribute(String procDefId, String actDefId, WMFilter filter, String attrName, Object attrValue) throws WMWorkflowException {

    }

    @Override
    public void abortProcessInstances(String procDefId, WMFilter filter) throws WMWorkflowException {

    }

    /**
     * Metoda sterge o instanta de proces.
     * @param procInstId reprezinta id-ul unei instante de proces. Acesta trebuie sa fie id-ul unei instante existente, altfel o sa arunce un WMWorkflowException.
     * @throws WMWorkflowException
     */
    @Override
    public void abortProcessInstance(String procInstId) throws WMWorkflowException {
        processInstanceCache.removeEloWfmcProcessInstance(procInstId);
    }

    @Override
    public void invokeApplication(int toolAgentHandle, String appName, String procInstId, String workItemId, Object[] parameters, int appMode) throws WMWorkflowException {

    }

    @Override
    public WMAttribute[] requestAppStatus(int toolAgentHandle, String procInstId, String workItemId, int[] status) throws WMWorkflowException {
        return new WMAttribute[0];
    }

    @Override
    public void terminateApp(int toolAgentHandle, String procInstId, String workItemId) throws WMWorkflowException {

    }

    public IXConnection getEloConnection() {
        return eloConnection;
    }

    public void setEloConnection(IXConnection eloConnection) {
        this.eloConnection = eloConnection;
    }

    public ProcessInstanceCache getProcessInstanceCache() {
        return processInstanceCache;
    }

    public void setProcessInstanceCache(ProcessInstanceCache processInstanceCache) {
        this.processInstanceCache = processInstanceCache;
    }

}
