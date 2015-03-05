package org.wfmc.elo;

import de.elo.ix.client.*;
import de.elo.utils.net.RemoteException;
import org.apache.commons.lang.StringUtils;
import org.wfmc.elo.base.WMErrorElo;
import org.wfmc.elo.model.ELOConstants;
import org.wfmc.elo.utils.EloToWfMCObjectConverter;
import org.wfmc.elo.utils.WfMCToEloObjectConverter;
import org.wfmc.impl.base.WMWorkItemIteratorImpl;
import org.wfmc.impl.base.filter.WMFilterWorkItem;
import org.wfmc.elo.utils.EloUtilsService;
import org.wfmc.impl.utils.FileUtils;
import org.wfmc.impl.utils.TemplateEngine;
import org.wfmc.impl.utils.Utils;
import org.wfmc.impl.utils.WfmcUtilsService;
import org.wfmc.service.WfmcServiceCache;
import org.wfmc.service.WfmcServiceImpl_Abstract;
import org.wfmc.wapi.*;

import java.util.*;

/**
 * Created by Lucian.Dragomir on 2/28/2015.
 */
public class WfmcServiceImpl_Elo extends WfmcServiceImpl_Abstract {

    private static final int MAX_RESULT = 1000;
    private static final String WF_SORD_LOCATION_TEMPLATE = "workflow.sord.location.template.path";

    private IXConnection ixConnection;

    private EloUtilsService eloUtilsService = new EloUtilsService();
    private WfmcUtilsService wfmcUtilsService = new WfmcUtilsService();

    protected ResourceBundle errorMessagesResourceBundle = ResourceBundle.getBundle("errorMessages");

    protected EloToWfMCObjectConverter eloToWfMCObjectConverter = new EloToWfMCObjectConverter();

    protected WfMCToEloObjectConverter wfMCToEloObjectConverter = new WfMCToEloObjectConverter();

    private void borrowIxConnection(WMConnectInfo connectInfo) {
        Properties connProps = IXConnFactory.createConnProps(connectInfo.getScope());
        Properties sessOpts = IXConnFactory.createSessionOptions("IX-Example", "1.0");
        IXConnFactory connFact = null;
        try {
            connFact = new IXConnFactory(connProps, sessOpts);
            ixConnection = connFact.create(connectInfo.getUserIdentification(),
                    connectInfo.getPassword(),
                    connectInfo.getEngineName(),
                    connectInfo.getUserIdentification());
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


    //ELO utility methods
    int getProcessMaskId(String procDefId) {
        return 0;
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
        return getWfmcServiceCache().getProcessInstance(procInstId);
    }

    @Override
    public void assignProcessInstanceAttribute(String procInstId, String attrName, Object attrValue) throws WMWorkflowException {

        //detect process templateId of this instance
        WMProcessInstance wmProcessInstance = getWfmcServiceCache().getProcessInstance(procInstId);

        //detect mask associated to process template
//        String maskId = getEloMask(wmProcessInstance.getProcessDefinitionId());

        //test if attribute name exists in ELO mask associated to the process and if attrValue is according to the definition

        //if everything is ok then
        super.assignProcessInstanceAttribute(procInstId, attrName, attrValue);
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
                String sordName = wmProcessInstance.getName() + " - T" + wmProcessInstance.getId();
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
                sordDirectory = processDefinitionAttributes.get(ELOConstants.DIR_TEMPLATE);
                if (StringUtils.isEmpty(sordDirectory)) {
                    throw new WMExecuteException("The directory information does not exist.");
                }
                sordDirectory = FileUtils.replaceTemporalItems(sordDirectory);
                sordDirectory = TemplateEngine.getInstance().getValueFromTemplate(sordDirectory, wmProcessInstanceAttributeMap);
                sordDirectory = eloUtilsService.fileUtilsRegular.convertPathName(sordDirectory, eloUtilsService.fileUtilsElo);
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
                } else {
                    throw new WMInvalidWorkItemException("This work item do not belong to " + sourceUser);
                }
            } else if (nodes[Integer.parseInt(workItemId)].getType() == 1){
                throw new WMInvalidWorkItemException("This is a begin node and can not be modified");
            } else {
                throw new WMInvalidWorkItemException("Work item with id " + workItemId + " do not exist!");
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
    public void setTransition(Integer workflowId, Integer currentNodeId, int[] transitionNodeId) throws WMWorkflowException {
        try {
            WFEditNode wfEditNode = getIxConnection().ix().beginEditWorkFlowNode(workflowId, currentNodeId, LockC.YES);
            getIxConnection().ix().endEditWorkFlowNode(workflowId, currentNodeId, false, false, wfEditNode.getNode().getName(), wfEditNode.getNode().getComment(), transitionNodeId);
        } catch (RemoteException e) {
            throw new WMWorkflowException(e);
        }
    }
    
    @Override
    public List<WMWorkItem> getNextSteps(Integer workflowId, Integer nodeId) throws WMWorkflowException {

        List<WFNode> nextNodes = new ArrayList<>();

        try{
            WFNodeAssoc[] wfNodeAssoc = eloUtilsService.getActiveWorkflowById(getIxConnection(), workflowId).getMatrix().getAssocs();
            for (WFNodeAssoc wfNode : wfNodeAssoc) {
                if (wfNode.getNodeFrom() == nodeId.intValue()) {
                    nextNodes.add(eloUtilsService.getNode(getIxConnection(), workflowId, wfNode.getNodeTo()));
                }
            }
        }
        catch(RemoteException e){
            throw new WMWorkflowException(e);
        }

        return eloToWfMCObjectConverter.convertWFNodesToWMWorkItems(nextNodes);
    }
    
}
