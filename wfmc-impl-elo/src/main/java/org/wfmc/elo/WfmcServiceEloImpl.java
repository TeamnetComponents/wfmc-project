package org.wfmc.elo;

import de.elo.ix.client.*;
import de.elo.utils.net.RemoteException;
import org.apache.commons.lang.StringUtils;
import org.wfmc.elo.base.WMErrorElo;
import org.wfmc.elo.model.ELOConstants;
import org.wfmc.elo.utils.EloToWfMCObjectConverter;
import org.wfmc.elo.utils.EloUtilsService;
import org.wfmc.elo.utils.WfMCToEloObjectConverter;
import org.wfmc.impl.base.WMProcessInstanceIteratorImpl;
import org.wfmc.impl.base.WMWorkItemAttributeNames;
import org.wfmc.impl.base.WMWorkItemImpl;
import org.wfmc.impl.base.WMWorkItemIteratorImpl;
import org.wfmc.impl.base.filter.WMFilterProcessInstance;
import org.wfmc.impl.base.filter.WMFilterWorkItem;
import org.wfmc.impl.utils.FileUtils;
import org.wfmc.impl.utils.TemplateEngine;
import org.wfmc.impl.utils.Utils;
import org.wfmc.impl.utils.WfmcUtilsService;
import org.wfmc.service.WfmcServiceCache;
import org.wfmc.service.WfmcServiceImpl_Abstract;
import org.wfmc.wapi.*;
import org.wfmc.xpdl.model.transition.Transition;
import org.wfmc.xpdl.model.workflow.WorkflowProcess;

import java.beans.PropertyVetoException;
import java.util.*;

/**
 * Created by Lucian.Dragomir on 2/28/2015.
 */
public class WfmcServiceEloImpl extends WfmcServiceImpl_Abstract {

    private static final int MAX_RESULT = 1000;
    private static final String WF_SORD_LOCATION_TEMPLATE = "workflow.sord.location.template.path";

    private IXConnection ixConnection;

    private EloUtilsService eloUtilsService = new EloUtilsService();
    private WfmcUtilsService wfmcUtilsService = new WfmcUtilsService();

    protected ResourceBundle errorMessagesResourceBundle = ResourceBundle.getBundle("errorMessages");

    protected EloToWfMCObjectConverter eloToWfMCObjectConverter = new EloToWfMCObjectConverter();

    protected WfMCToEloObjectConverter wfMCToEloObjectConverter = new WfMCToEloObjectConverter();

    private void borrowIxConnection(WMConnectInfo connectInfo) throws WMWorkflowException{
        Properties connProps = IXConnFactory.createConnProps(connectInfo.getScope());
        Properties sessOpts = IXConnFactory.createSessionOptions("IX-Example", "1.0");
        IXConnFactory connFact = null;
        try {
            connFact = new IXConnFactory(connProps, sessOpts);
            String[] usersSplit = eloUtilsService.splitLoginUsers(connectInfo.getUserIdentification());
            ixConnection = connFact.create(usersSplit[1],
                    connectInfo.getPassword(),
                    connectInfo.getEngineName(),
                    usersSplit[1]);
            if (!eloUtilsService.checkIfGroupExist(getIxConnection(), usersSplit[0])) {
                eloUtilsService.createUserGroup(getIxConnection(), usersSplit[0], null);
            }
        } catch (RemoteException remoteException) {
            throw new WMWorkflowException(remoteException);
        }
    }

    private void releaseIXConnection() {
        if (ixConnection != null) {
            ixConnection.logout();
            ixConnection = null;
        }
        ixConnection = null;
    }

    protected IXConnection getIxConnection() {
        return ixConnection;
    }

    public void setEloUtilsService(EloUtilsService eloUtilsService) {
        this.eloUtilsService = eloUtilsService;
    }

    public void setWfmcUtilsService(WfmcUtilsService wfmcUtilsService) {
        this.wfmcUtilsService = wfmcUtilsService;
    }

    public void setEloToWfMCObjectConverter(EloToWfMCObjectConverter eloToWfMCObjectConverter) {
        this.eloToWfMCObjectConverter = eloToWfMCObjectConverter;
    }

    @Override
    public void connect(WMConnectInfo connectInfo) throws WMWorkflowException {
        borrowIxConnection(connectInfo);
    }

    @Override
    public void disconnect() throws WMWorkflowException {
        releaseIXConnection();
    }

    public WMProcessInstance getProcessInstance(String procInstId) throws WMWorkflowException {
        WMProcessInstance processInstanceInCache = getWfmcServiceCache().getProcessInstance(procInstId);
        //process instance wasn't created in ELO, is just in cache
        if (processInstanceInCache != null)
            return processInstanceInCache;
        //process instance exists in ELO, was created
        WFDiagram wfDiagram = null;
        WMProcessInstance wmProcessInstance;
        try {
            //check if workflow is active
            wfDiagram = eloUtilsService.getWorkFlow(getIxConnection(), procInstId, WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO);
            if (wfDiagram == null) {
                //check if workflow is finished
                wfDiagram = eloUtilsService.getWorkFlow(getIxConnection(), procInstId, WFTypeC.FINISHED, WFDiagramC.mbAll, LockC.NO);
                if (wfDiagram == null) {
                    throw  new WMInvalidProcessInstanceException(procInstId);
                } else {
                    wmProcessInstance = eloToWfMCObjectConverter.convertWFDiagramToWMProcessInstanceWithStatus(wfDiagram, WFTypeC.FINISHED);
                }
            } else {
                wmProcessInstance = eloToWfMCObjectConverter.convertWFDiagramToWMProcessInstanceWithStatus(wfDiagram, WFTypeC.ACTIVE);
            }
        } catch (RemoteException e) {
            throw new WMWorkflowException(e);
        } catch (NullPointerException e){
            throw new WMWorkflowException(e);
        }
        return wmProcessInstance;
    }

    @Override
    public void assignProcessInstanceAttribute(String procInstId, String attrName, Object attrValue) throws WMWorkflowException {
        //detect process templateId of this instance
        WMProcessInstance wmProcessInstance = getWfmcServiceCache().getProcessInstance(procInstId);
        if (wmProcessInstance != null) {
            // process is not in ELO, only in cache
            super.assignProcessInstanceAttribute(procInstId, attrName, attrValue);
            return;
        }
        try {
            // 1. search for workflow
            WFDiagram wfDiagram = eloUtilsService.getWorkFlow(getIxConnection(), procInstId, WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO);
            // 2. get sord
            Sord wfSord = eloUtilsService.getSord(getIxConnection(), wfDiagram.getObjId(), SordC.mbAll, LockC.YES);
            // 3. update sord attrs
            if (!eloUtilsService.sordContainsAttribute(wfSord, attrName)){
                throw new WMInvalidAttributeException(attrName);
            }
            Map<String, Object> attrMap = new HashMap<>();
            attrMap.put(attrName, attrValue);
            eloUtilsService.updateSordAttributes(wfSord, attrMap);
            // 4. save sord
            eloUtilsService.saveSord(getIxConnection(), wfSord, SordC.mbAll, LockC.YES);
        } catch (RemoteException e){
            throw  new WMWorkflowException(e);
        }

    }

    @Override
    public String startProcess(String procInstId) throws WMWorkflowException {
        String processInstanceId = null;
        Sord sord = null;
        String sordId = null;
        WFDiagram wfDiagram = null;
        Map<String, String> processDefinitionAttributes;

        WMProcessInstance wmProcessInstance;
        Map<String, WMAttribute> wmProcessInstanceWMAttributeMap;
        Map<String, Object> wmProcessInstanceAttributeMap;

        try {
            //get process definition and attributes from cache
            wmProcessInstance = getWfmcServiceCache().getProcessInstance(procInstId);
            wmProcessInstanceWMAttributeMap = wfmcUtilsService.toWMAttributeMap(getWfmcServiceCache().getProcessInstanceAttributes(procInstId));
            //simple map attributename/attributevalue
            wmProcessInstanceAttributeMap = wfmcUtilsService.toMap(wmProcessInstanceWMAttributeMap);

            //detect the process template associated to the cached instance
            wfDiagram = eloUtilsService.getWorkFlow(getIxConnection(), wmProcessInstance.getProcessDefinitionId(), WFTypeC.TEMPLATE, WFDiagramC.mbAll, LockC.NO);
            //get special attributes from definition template (e.g. ".Mask" or ".Dir" or ".DirHist") - see EloConstants class
            // ca exemplu voi avea in comment: .Mask=10;.Dir=/Fluxuri/${Uat}/
            // Nota: variabila .Dir trebuie sa suporte si placeholdere de tip temporal:  "yyyy", "MM", "dd", "HH", "mm", "ss"
            processDefinitionAttributes = Utils.toMap(wfDiagram.getNodes()[0].getComment());


            //SORD PREPARATIONS
            //am primit sord id -> il folosesc pe instanta de flux
            if (wmProcessInstanceWMAttributeMap.containsKey(ELOConstants.SORD_ID)) {
                sordId = (String) wmProcessInstanceWMAttributeMap.get(ELOConstants.SORD_ID).getValue();
                sord = eloUtilsService.getSord(getIxConnection(), sordId, SordC.mbAll, LockC.YES);
                if (sord == null) {
                    throw new WMExecuteException(errorMessagesResourceBundle.getString(WMErrorElo.ELO_SORD_NOT_EXIST));
                }
            }
            // nu am primit sord si caut mask id pentru a crea un sord cu acea masca
            else {
                String maskId = null;
                String sordDirectory = null;
                String maskPath = null;
                String sordName = wmProcessInstance.getName() + " - T" + wmProcessInstance.getId().replace("-",":");
                //daca am primit mask id
                if (wmProcessInstanceWMAttributeMap.containsKey(ELOConstants.MASK_ID)) {
                    maskId = (String) wmProcessInstanceWMAttributeMap.get(ELOConstants.MASK_ID).getValue();
                }
                //daca a mask id in definitia fluxului
                else if (processDefinitionAttributes.containsKey(ELOConstants.MASK_ID)) {
                    maskId = processDefinitionAttributes.get(ELOConstants.MASK_ID);
                }
                if (StringUtils.isEmpty(maskId)) {
                    throw new WMExecuteException("The mask information does not exist.");
                }
                //determin directorul unde trebuie creat sord-ul
                sordDirectory = processDefinitionAttributes.get(ELOConstants.PATH_TEMPLATE);
                maskPath = processDefinitionAttributes.get(ELOConstants.PATH_MASK_ID);
                if (StringUtils.isEmpty(sordDirectory)) {
                    throw new WMExecuteException("The directory information does not exist.");
                }
                sordDirectory = FileUtils.replaceTemporalItems(sordDirectory);
                sordDirectory = TemplateEngine.getInstance().getValueFromTemplate(sordDirectory, wmProcessInstanceAttributeMap);
                sordDirectory = eloUtilsService.fileUtilsRegular.convertPathName(sordDirectory, eloUtilsService.fileUtilsElo);
                String[] pathNames = sordDirectory.split("¶");
                String pathName = null;
                for (int i = 1; i < pathNames.length; i++) {
                    if (i == 1) {
                        pathName = pathNames[0] + "¶" + pathNames[i];
                        if (!eloUtilsService.existSord(getIxConnection(),pathName)) {
                            Sord newSord = eloUtilsService.createSord(getIxConnection(), "1",maskPath , pathNames[i]);
                            eloUtilsService.saveSord(getIxConnection(),newSord, SordC.mbAll, LockC.YES);
                        }
                    } else {
                        if (eloUtilsService.existSord(getIxConnection(), pathName + "¶" + pathNames[i])) {
                            pathName = pathName + "¶" + pathNames[i];
                        } else  {
                            Sord newSord = eloUtilsService.createSord(getIxConnection(), pathName,  maskPath , pathNames[i]);
                            eloUtilsService.saveSord(getIxConnection(),newSord, SordC.mbAll, LockC.YES);
                            pathName = pathName + "¶" + pathNames[i];
                            //pathName = newSord.getRefPaths()[0].getPathAsString();
                        }
                    }
                }
                sord = eloUtilsService.createSord(getIxConnection(), sordDirectory, maskId, sordName);
            }

            //setez atributele sord-ului
            eloUtilsService.updateSordAttributes(sord, wmProcessInstanceAttributeMap);
            //save the sord
            sordId = String.valueOf(eloUtilsService.saveSord(getIxConnection(), sord, SordC.mbAll, LockC.YES));

            //WORKFLOW PREPARATIONS
            processInstanceId = eloUtilsService.startWorkFlow(getIxConnection(), wmProcessInstance.getProcessDefinitionId(), wmProcessInstance.getName(), sordId);

        } catch (RemoteException e) {
            throw new WMWorkflowException(e);
        }

        //cleanup process temporary data
        super.startProcess(procInstId);

        return processInstanceId;
    }

    /**
     * Metoda sterge o instanta de proces.
     * @param procInstId reprezinta id-ul unei instante de proces. Acesta trebuie sa fie id-ul unei instante existente, altfel o sa arunce un WMWorkflowException.
     * @throws WMWorkflowException
     */
    @Override
    public void terminateProcessInstance(String procInstId) throws WMWorkflowException {
        getWfmcServiceCache().removeProcessInstance(procInstId);
    }

    @Override
    public void abortProcessInstance(String procInstId) throws WMWorkflowException {
        try {
            getIxConnection().ix().deleteWorkFlow(procInstId, WFTypeC.ACTIVE, LockC.NO);
        } catch (RemoteException e) {
            throw new WMWorkflowException(e);
        }
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
        try {
            WFDiagram wfDiagram = eloUtilsService.getWorkFlow(getIxConnection(), procInstId, WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO);
            WFNode[] nodes = wfDiagram.getNodes();
            if ((nodes[Integer.parseInt(workItemId)].getName() != "") && (nodes[Integer.parseInt(workItemId)].getType() != 1)) {
                if (sourceUser.equals(nodes[Integer.parseInt(workItemId)].getUserName())) {
                    nodes[Integer.parseInt(workItemId)].setUserName(targetUser);
                    wfDiagram.setNodes(nodes);
                    getIxConnection().ix().checkinWorkFlow(wfDiagram, WFDiagramC.mbAll, LockC.NO);
                    getIxConnection().ix().checkoutWorkFlow(String.valueOf(wfDiagram.getId()), WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO);
                } else {
                    throw new WMInvalidWorkItemException(nodes[Integer.parseInt(workItemId)].getName());
                }
            } else if (nodes[Integer.parseInt(workItemId)].getType() == 1){
                throw new WMInvalidWorkItemException(nodes[Integer.parseInt(workItemId)].getName());
            } else {
                throw new WMInvalidWorkItemException(nodes[Integer.parseInt(workItemId)].getName());
            }
        } catch (RemoteException e) {
            throw new WMWorkflowException(e);
        }
    }

    protected void setWfmcServiceCache(WfmcServiceCache wfmcServiceCache) {
        super.setWfmcServiceCache(wfmcServiceCache);
    }

    @Override
    public WMWorkItemIterator listWorkItems(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        if (filter instanceof WMFilterWorkItem){
            try {
                FindTasksInfo findTasksInfo = wfMCToEloObjectConverter.convertWMFilterWorkItemToFindTasksInfo((WMFilterWorkItem) filter);
                FindResult findResult = ixConnection.ix().findFirstTasks(findTasksInfo, MAX_RESULT);
                UserTask[] userTasks = findResult.getTasks();
                WMWorkItem[] wmWorkItems = eloToWfMCObjectConverter.convertUserTasksToWMWorkItems(userTasks);
                return new WMWorkItemIteratorImpl(wmWorkItems);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            throw new WMUnsupportedOperationException(errorMessagesResourceBundle.getString(WMErrorElo.ELO_ERROR_FILTER_NOT_SUPPORTED));
        }
        throw new WMUnsupportedOperationException(errorMessagesResourceBundle.getString(WMErrorElo.ELO_ERROR_FILTER_NOT_SUPPORTED));
    }

    @Override
    public WMProcessInstanceIterator listProcessInstances(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        if (filter instanceof WMFilterProcessInstance) {
            try {
                FindWorkflowInfo findWorkflowInfo = wfMCToEloObjectConverter.convertWMFilterProcessInstanceToFindWorkflowInfo((WMFilterProcessInstance)filter);
                FindResult findResult = getIxConnection().ix().findFirstWorkflows(findWorkflowInfo, MAX_RESULT, WFDiagramC.mbAll);
                WFDiagram[] wfDiagrams = findResult.getWorkflows();
                WMProcessInstance[] wmProcessInstances = eloToWfMCObjectConverter.convertWFDiagramsToWMProcessInstances(wfDiagrams);
                return new WMProcessInstanceIteratorImpl(wmProcessInstances);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public void assignWorkItemAttribute(String procInstId, String workItemId, String attrName, Object attrValue) throws WMWorkflowException {
        WMWorkItem workItem = new WMWorkItemImpl(procInstId, workItemId);
        if ((workItem.getId() != null) && (WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString().equals(attrName))){
            super.assignWorkItemAttribute(procInstId, workItemId, attrName, attrValue);
        }
    }

    @Override
    public void completeWorkItem(String procInstId, String workItemId) throws WMWorkflowException {
        WMAttributeIterator workItemAttribute = getWfmcServiceCache().getWorkItemAttribute(procInstId, workItemId);

        int[] nextNodesId = new int[workItemAttribute.getCount()];
        if (workItemAttribute != null) {
            int i = 0;
            while (workItemAttribute.hasNext()){
                WMAttribute wmAttribute = workItemAttribute.tsNext();
                nextNodesId[i++] = Integer.parseInt((String)wmAttribute.getValue());
            }
        }

        Integer processInstanceIdAsInt = Integer.parseInt(procInstId);
        Integer currentWorkItemIdAsInt = Integer.parseInt(workItemId);

        try {
            WFEditNode wfEditNode = getIxConnection().ix().beginEditWorkFlowNode(processInstanceIdAsInt, currentWorkItemIdAsInt, LockC.YES);
            List<WMWorkItem> nextSteps = getNextSteps(procInstId, workItemId);

            List<String> nextStepsId = new ArrayList<>();
            for (WMWorkItem wmWorkItem : nextSteps){
                String id = wmWorkItem.getId();
                nextStepsId.add(id);
            }

            boolean isNextWorkItemASuccessor = false;
            for (int i = 0; i < nextNodesId.length; i++) {
                if (nextStepsId.contains(String.valueOf(nextNodesId[i]))) {
                    isNextWorkItemASuccessor = true;
                } else {
                    isNextWorkItemASuccessor = false;
                    break;
                }
            }
            if (isNextWorkItemASuccessor) {
                getIxConnection().ix().endEditWorkFlowNode(processInstanceIdAsInt, currentWorkItemIdAsInt, false, false, wfEditNode.getNode().getName(),
                        wfEditNode.getNode().getComment(), nextNodesId);
                getWfmcServiceCache().removeWorkItemAttributes(procInstId, workItemId);
            } else {
                throw new WMUnsupportedOperationException(errorMessagesResourceBundle.getString(WMErrorElo.COULD_NOT_COMPLETE_WORK_ITEM));
            }
        } catch (RemoteException e) {
            throw new WMUnsupportedOperationException(errorMessagesResourceBundle.getString(WMErrorElo.COULD_NOT_COMPLETE_WORK_ITEM));
        }
    }

    @Override
    public WMWorkItem getWorkItem(String procInstId, String workItemId) throws WMWorkflowException {

        try {
            WFDiagram wfDiagram = getIxConnection().ix().checkoutWorkFlow(procInstId, WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO);
            WFNode[] nodes = wfDiagram.getNodes();
            WMWorkItem wmWorkItem = null;
            List<WFNode> wfNodes = new ArrayList<>();
            for (WFNode wfNode : nodes) {
                if (Integer.parseInt(workItemId) == wfNode.getId()) {
                    wfNodes.add(wfNode);
                    List<WMWorkItem> wmWorkItemList = eloToWfMCObjectConverter.convertWFNodesToWMWorkItems(wfNodes);
                    for(WMWorkItem workItem : wmWorkItemList){
                        wmWorkItem = workItem;
                    }
                }
            }
            return wmWorkItem;
        } catch (RemoteException e) {
            throw new WMWorkflowException(errorMessagesResourceBundle.getString(WMErrorElo.COULD_NOT_FIND_WORK_ITEM));
        }
    }

    @Override
    public WorkflowProcess getWorkFlowProcess(String processDefinitionId) {
        WorkflowProcess wp = new WorkflowProcess();
        try {

            WFDiagram wfDiagram = eloUtilsService.getWorkFlowTemplate(getIxConnection(),processDefinitionId,"",WFDiagramC.mbAll,LockC.NO);
            //System.out.println("wf"+ wfDiagram);

            WFNodeAssoc[] asocieri = wfDiagram.getMatrix().getAssocs();


            int idTranzitie = 0;
            Transition[] tranzitii = new Transition[asocieri.length];
            for(int i =0; i<asocieri.length;i++){
                Transition t = new Transition();
                t.setFrom(asocieri[i].getNodeFrom()+"");
                t.setTo(asocieri[i].getNodeTo()+"");
                //nu am gasit in elo, dar ii setez id-ul pt ca e folosit de hashcode si crapa daca e null cand folsoim in anumite colectii
                t.setId(idTranzitie++ + "");
                tranzitii[i] = t;

            }
            wp.setTransition(tranzitii);
            wp.setName(wfDiagram.getName());


        } catch (RemoteException | PropertyVetoException e) {
            e.printStackTrace();
        }

        return wp;
    }




    @Override
    public List<WMWorkItem> getNextSteps(String processInstanceId, String workItemId)  {
        List<WFNode> nextNodes= new LinkedList<WFNode>();
        try {
            WFDiagram workFlow = eloUtilsService.getWorkFlow(getIxConnection(), processInstanceId, WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO);
            WorkflowProcess workFlowProcess =  eloToWfMCObjectConverter.convertWfDiagramToWorkflowProcess(workFlow);
            Transition[] transitions = workFlowProcess.getTransition();
            for(Transition t: transitions){
                if(t.getFrom().equals(workItemId+""))
                {
                    WFNode node = eloUtilsService.getNode(getIxConnection(), processInstanceId, Integer.parseInt(t.getTo()));
                    nextNodes.add(node);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        return eloToWfMCObjectConverter.convertWFNodesToWMWorkItems(nextNodes);
    }

}
