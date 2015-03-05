package org.wfmc.elo;

import de.elo.ix.client.*;
import de.elo.utils.net.RemoteException;
import org.apache.commons.lang.StringUtils;
import org.wfmc.elo.model.ELOConstants;
import org.wfmc.elo.utils.EloUtils;
import org.wfmc.impl.utils.FileUtils;
import org.wfmc.impl.utils.TemplateEngine;
import org.wfmc.impl.utils.Utils;
import org.wfmc.impl.utils.WfmcUtils;
import org.wfmc.service.WfmcServiceImpl_Abstract;
import org.wfmc.wapi.*;

import java.util.Map;
import java.util.Properties;

/**
 * Created by Lucian.Dragomir on 2/28/2015.
 */
public class WfmcServiceImpl_Elo extends WfmcServiceImpl_Abstract {

    private static final String WF_SORD_LOCATION_TEMPLATE = "workflow.sord.location.template.path";

    private IXConnection ixConnection;

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

    private IXConnection getIxConnection() {
        return ixConnection;
    }


    //ELO utility methods
    int getProcessMaskId(String procDefId) {
        return 0;
    }


    @Override
    public void connect(WMConnectInfo connectInfo) throws WMWorkflowException {
        borrowIxConnection(connectInfo);
    }

    @Override
    public void disconnect() throws WMWorkflowException {
        releaseIXConnection();
    }


    @Override
    public void assignProcessInstanceAttribute(String procInstId, String attrName, Object attrValue) throws WMWorkflowException {

        //detect process templateId of this instance
        WMProcessInstance wmProcessInstance = getWfmcServiceCache().getProcessInstance(procInstId);

        //detect mask associated to process template
        //String maskId = getEloMask(wmProcessInstance.getProcessDefinitionId());

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
            wmProcessInstanceWMAttributeMap = WfmcUtils.toWMAttributeMap(getWfmcServiceCache().getProcessInstanceAttributes(procInstId));
            //simple map attributename/attributevalue
            wmProcessInstanceAttributeMap = WfmcUtils.toMap(wmProcessInstanceWMAttributeMap);

            //detect the process template associated to the cached instance
            wfDiagram = EloUtils.getWorkFlow(getIxConnection(), wmProcessInstance.getProcessDefinitionId(), WFTypeC.TEMPLATE, WFDiagramC.mbAll, LockC.NO);
            //get special attributes from definition template (e.g. ".Mask" or ".Dir" or ".DirHist") - see EloConstants class
            // ca exemplu voi avea in comment: .Mask=10;.Dir=/Fluxuri/${Uat}/
            // Nota: variabila .Dir trebuie sa suporte si placeholdere de tip temporal:  "yyyy", "MM", "dd", "HH", "mm", "ss"
            processDefinitionAttributes = Utils.toMap(wfDiagram.getNodes()[0].getComment());


            //SORD PREPARATIONS
            //am primit sord id -> il folosesc pe instanta de flux
            if (wmProcessInstanceWMAttributeMap.containsKey(ELOConstants.SORD_ID)) {
                sordId = (String) wmProcessInstanceWMAttributeMap.get(ELOConstants.SORD_ID).getValue();
                sord = EloUtils.getSord(getIxConnection(), sordId, SordC.mbAll, LockC.YES);
                if (sord == null) {
                    throw new WMExecuteException("The provided sord does not exist.");
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
                sordDirectory = EloUtils.fileUtilsRegular.convertPathName(sordDirectory, EloUtils.fileUtilsElo);
                sord = EloUtils.createSord(getIxConnection(), sordDirectory, maskId, sordName);
            }

            //setez atributele sord-ului
            EloUtils.updateSordAttributes(sord, wmProcessInstanceAttributeMap);
            //save the sord
            sordId = String.valueOf(EloUtils.saveSord(getIxConnection(), sord, SordC.mbAll, LockC.YES));

            //WORKFLOW PREPARATIONS
            processInstanceId = EloUtils.startWorkFlow(getIxConnection(), wmProcessInstance.getProcessDefinitionId(), wmProcessInstance.getName(), sordId);

        } catch (RemoteException e) {
            throw new WMWorkflowException(e);
        }

        //cleanup process temporary data
        super.startProcess(procInstId);

        return processInstanceId;
    }

}
