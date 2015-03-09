package org.wfmc.elo.utils;

import de.elo.ix.client.*;
import org.wfmc.impl.base.WMParticipantImpl;
import org.wfmc.impl.base.WMProcessInstanceImpl;
import org.wfmc.impl.base.WMWorkItemImpl;
import org.wfmc.wapi.WMProcessInstance;
import org.wfmc.wapi.WMProcessInstanceState;
import org.wfmc.wapi.WMWorkItem;
import org.wfmc.wapi.WMWorkItemState;

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

    public List<WMWorkItem> convertWFNodesToWMWorkItems(List<WFNode> wfNodes) {

        List<WMWorkItem> wmWorkItems = new ArrayList<>();

        for (WFNode wfNode : wfNodes) {
            WMWorkItemImpl wmWorkItem = new WMWorkItemImpl();
            wmWorkItem.setName(wfNode.getName());
            wmWorkItem.setId(Integer.toString(wfNode.getId()));
            WMParticipantImpl user = new WMParticipantImpl(wfNode.getUserName());
            wmWorkItem.setParticipant(user);
            wmWorkItem.setPerformer(wfNode.getUserName());

            wmWorkItems.add(wmWorkItem);
        }

        return wmWorkItems;
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
