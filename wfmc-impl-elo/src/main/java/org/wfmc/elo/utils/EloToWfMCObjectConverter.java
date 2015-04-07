package org.wfmc.elo.utils;

import de.elo.ix.client.*;
import de.elo.utils.net.RemoteException;
import org.wfmc.impl.base.WMParticipantImpl;
import org.wfmc.impl.base.WMProcessInstanceImpl;
import org.wfmc.impl.base.WMWorkItemImpl;
import org.wfmc.wapi.WMProcessInstance;
import org.wfmc.wapi.WMProcessInstanceState;
import org.wfmc.wapi.WMWorkItem;
import org.wfmc.wapi.WMWorkItemState;
import org.wfmc.xpdl.model.transition.Transition;
import org.wfmc.xpdl.model.workflow.WorkflowProcess;

import java.beans.PropertyVetoException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by andras on 3/5/2015.
 */
public class EloToWfMCObjectConverter {

    public EloToWfMCObjectConverter() {

    }

    public WMWorkItem[] convertUserTasksToWMWorkItems(UserTask[] userTasks) {
        WMWorkItem[] wmWorkItems = new WMWorkItem[userTasks.length];
        List<WMWorkItem> wmWorkItemsList = new ArrayList<>();
        for (int i = 0; i < userTasks.length; i++) {
            UserTask userTask = userTasks[i];
            if (userTask == null) {
                break;
            }
            WMWorkItemImpl wmWorkItem = new WMWorkItemImpl();
            wmWorkItem.setName(userTask.getWfNode().getNodeName());
            wmWorkItem.setPriority(userTask.getWfNode().getPrio());
            wmWorkItem.setId(String.valueOf(userTask.getWfNode().getNodeId()));
            wmWorkItem.setProcessInstanceId(String.valueOf(userTask.getWfNode().getFlowId()));
            WMParticipantImpl user = new WMParticipantImpl(userTask.getWfNode().getUserName());
            wmWorkItem.setParticipant(user);
            wmWorkItem.setProcessDefinitionId(null);//TODO: setProcessDefinitionId - ar trebui sa ne incarcam flow-ul
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
            Date d = null;
            try {
                d = sdf.parse((userTask.getWfNode().getActivateDateIso()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            wmWorkItem.setStartedDate(d); //done
            //wmWorkItem.setTargetDate(); //TODO:
            //wmWorkItem.setDueDate(); //TODO:
            //wmWorkItem.setCompletedDate(null);//TODO: Andra: aici vom avea doar din istoric?
            //wmWorkItem.setToolIndex(0); //TODO Andra: de vazut ce e cu ToolSet-ul in OBE
            wmWorkItem.setState(WMWorkItemState.OPEN_RUNNING);//TODO: de vazut cum tratam state-urile astea!
            wmWorkItem.setPerformer(userTask.getWfNode().getUserName());//TODO: e tot userul sau grupul de pe task?

            wmWorkItemsList.add(wmWorkItem);
        }
        wmWorkItemsList.toArray(wmWorkItems);
        return wmWorkItems;
    }

    public List<WMWorkItem> convertWFNodesToWMWorkItems(List<WFNode> wfNodes, String processInstanceId) {

        List<WMWorkItem> wmWorkItems = new ArrayList<>();

        for (WFNode wfNode : wfNodes) {
            WMWorkItemImpl wmWorkItem = new WMWorkItemImpl();
            wmWorkItem.setName(wfNode.getName());
            wmWorkItem.setId(Integer.toString(wfNode.getId()));
            WMParticipantImpl user = new WMParticipantImpl(wfNode.getUserName());
            wmWorkItem.setParticipant(user);
            wmWorkItem.setPerformer(wfNode.getUserName());
            wmWorkItem.setProcessInstanceId(processInstanceId);
            wmWorkItems.add(wmWorkItem);
        }
        return wmWorkItems;
    }

    public WMWorkItem[] convertUserTasksToWMWorkItemsWithConnection(UserTask[] userTasks,IXConnection ixConnection) throws RemoteException {
        WMWorkItem[] wmWorkItems = new WMWorkItem[userTasks.length];
        List<WMWorkItem> wmWorkItemsList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        for (int i = 0; i < userTasks.length; i++) {
            UserTask userTask = userTasks[i];
            if (userTask == null) {
                continue;
            }
            if(userTask.getWfNode() == null){
                continue;
            }
            WMWorkItemImpl wmWorkItem = new WMWorkItemImpl();
            wmWorkItem.setName(userTask.getWfNode().getNodeName());
            wmWorkItem.setPriority(userTask.getWfNode().getPrio());
            wmWorkItem.setId(String.valueOf(userTask.getWfNode().getNodeId()));
            wmWorkItem.setProcessInstanceId(String.valueOf(userTask.getWfNode().getFlowId()));
            WMParticipantImpl user = new WMParticipantImpl(userTask.getWfNode().getUserName());
            wmWorkItem.setParticipant(user);
            //pt asta avem nevoie de conextiune - tb sa luam id-ul workflow template-ului echivalent de proces
            Integer flowId = userTask.getWfNode().getFlowId();
            WFDiagram wfDiagram = ixConnection.ix().checkoutWorkflowTemplate(flowId.toString(), null, WFDiagramC.mbAll, LockC.NO);
            wmWorkItem.setProcessDefinitionId(wfDiagram.getId() + "");//TODO: setProcessDefinitionId - ar trebui sa ne incarcam flow-ul
            Date d = null;
            String activateDate = userTask.getWfNode().getActivateDateIso();
            if(activateDate!=null)
                try {
                    d = sdf.parse(activateDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            wmWorkItem.setStartedDate(d ); //done

            //wmWorkItem.setTargetDate(); //TODO:
            //The date/time by which the work item is expected to be complete.
            //UserDelay - The workflow node is deferred until this date. -> nodul e programat pt data asta
            wmWorkItem.setTargetDate(convertEloDateToWfmc(userTask.getWfNode().getUserDelayDateIso(),sdf));

            // wmWorkItem.setDueDate(); //TODO:
            //dueDate - "  The date/time by which the work item must be complete. "
            // timeLimitIso -    Node must be completed until this date.
            wmWorkItem.setDueDate(convertEloDateToWfmc(userTask.getWfNode().getTimeLimitIso(),sdf));

            //wmWorkItem.setCompletedDate(null); //TODO: Andra: aici vom avea doar din istoric?
            Date c = null;
            String completedDate = userTask.getWfNode().getCompletionDateIso();
            if(completedDate!=null)
                try {
                    c = sdf.parse(completedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            //Daca nu e completata activtatea atunci e null, altfel e data
            wmWorkItem.setCompletedDate(c);
            //ToolSet se refera la subrutina sau aplicatia asociata executarii nodului (daca are unu
            //
            //wmWorkItem.setToolIndex(0); //TODO Andra: de vazut ce e cu ToolSet-ul in OBE

            //tratate stari - pe baza datelor setate din ELO, nu am gasit ceva mai detalitat - sunt mai multe stari in WFMC decat cele de aici
            WMWorkItemState state;

            if(wmWorkItem.getStartedDate() == null)
                state = WMWorkItemState.OPEN_NOTRUNNING;
            else
                state = WMWorkItemState.OPEN_RUNNING;

            if(wmWorkItem.getCompletedDate() != null){
                state = WMWorkItemState.CLOSED_COMPLETED;
            }

            wmWorkItem.setState(state);
            //wmWorkItem.setState(WMWorkItemState.OPEN_RUNNING);//TODO: de vazut cum tratam state-urile astea!
            //valorile astea sunt folosite in principal de engine -

            //trebuie id-ul userului
            wmWorkItem.setPerformer(userTask.getWfNode().getUserId()+"");//TODO: e tot userul sau grupul de pe task?



            wmWorkItemsList.add(wmWorkItem);
        }
        wmWorkItemsList.toArray(wmWorkItems);
        return wmWorkItems;
    }

    private static Date convertEloDateToWfmc(String data, SimpleDateFormat sdf){
        if(data==null)
            return null;
        try {
            Date d = sdf.parse(data);
            return d;
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }





    }

    public WorkflowProcess convertWfDiagramToWorkflowProcess(WFDiagram wfDiagram){
        WorkflowProcess wp = new WorkflowProcess();

        WFNodeAssoc[] asocieri = wfDiagram.getMatrix().getAssocs();


        int idTranzitie = 0;
        Transition[] tranzitii = new Transition[asocieri.length];
        for(int i =0; i<asocieri.length;i++){
            Transition t = new Transition();
            t.setFrom(asocieri[i].getNodeFrom()+"");
            t.setTo(asocieri[i].getNodeTo()+"");
            t.setId(idTranzitie++ + "");
            tranzitii[i] = t;

        }
        try {
            wp.setTransition(tranzitii);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        wp.setName(wfDiagram.getName());

        return wp;
    }


    public WMProcessInstance[] convertWFDiagramsToWMProcessInstances(WFDiagram[] wfDiagrams) {

        WMProcessInstance[] wmProcessInstances = new WMProcessInstance[wfDiagrams.length];
        List<WMProcessInstance> wmProcessInstanceList = new ArrayList<>();
        for(int i = 0; i<wfDiagrams.length;i++){
            wmProcessInstances[i] = this.convertWFDiagramToWMProcessInstance(wfDiagrams[i]);
        }
        wmProcessInstanceList.toArray(wmProcessInstances);
        return wmProcessInstances;

    }

    public WMProcessInstance convertWFDiagramToWMProcessInstance(WFDiagram wfDiagram) {
        WMProcessInstanceImpl wmProcessInstance = new WMProcessInstanceImpl();
        wmProcessInstance.setName(wfDiagram.getName());
        wmProcessInstance.setId(Integer.toString(wfDiagram.getId()));
        wmProcessInstance.setProcessDefinitionId(Integer.toString(wfDiagram.getTemplateId()));
        wmProcessInstance.setState(wfDiagram.getCompletionDateIso() == null || wfDiagram.getCompletionDateIso().equals("")? WMProcessInstanceState.OPEN_RUNNING : WMProcessInstanceState.CLOSED_COMPLETED);
        //TODO: de vazut ce mai trebuie setat pe WMProcessInstanceImpl si ce avem nevoie din WFDiagram si nu putem pune in WMProcessInstanceImpl
        return  wmProcessInstance;
    }

    public WMProcessInstance convertWFDiagramToWMProcessInstanceWithStatus(WFDiagram wfDiagram , WFTypeZ status) {
        WMProcessInstanceImpl wmProcessInstance = new WMProcessInstanceImpl();
        wmProcessInstance.setName(wfDiagram.getName());
        wmProcessInstance.setId(Integer.toString(wfDiagram.getId()));
        wmProcessInstance.setProcessDefinitionId(Integer.toString(wfDiagram.getTemplateId()));
        wmProcessInstance.setState(status == WFTypeC.ACTIVE ?  WMProcessInstanceState.OPEN_RUNNING : WMProcessInstanceState.CLOSED_COMPLETED);
        //TODO: de vazut ce mai trebuie setat pe WMProcessInstanceImpl si ce avem nevoie din WFDiagram si nu putem pune in WMProcessInstanceImpl
        return  wmProcessInstance;
    }

}
