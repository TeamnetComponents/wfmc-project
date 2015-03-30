package org.wfmc.elo.utils;

import de.elo.ix.client.*;
import org.wfmc.impl.utils.FileUtils;

import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by Lucian.Dragomir on 3/4/2015.
 */
public class EloUtilsService {

    public static final int FOLDER_ROOT_ID_ELO = 1;
    public final static FileUtils fileUtilsElo = new FileUtils("ARCPATH:", String.valueOf((char) 182));
    public final static FileUtils fileUtilsRegular = new FileUtils("/", "/");

    private final static Integer ACTIVE_WORKFLOWS_MAX_NUMBER = Integer.MAX_VALUE;

    public boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        return currentDate;
    }

    public Calendar convertIso2Calendar(IXConnection ixConnection, String isoDate) {
        if (isoDate == null || isoDate.equals("")) {
            return null;
        }
        try {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(ixConnection.isoToDate(isoDate));
            return calendar;
        } catch (Exception e) {
            return null;
        }
    }

    public String convertCalendar2Iso(IXConnection ixConnection, GregorianCalendar date) {
        return ixConnection.dateToIso(date.getTime());
    }

    public Sord createSord(IXConnection ixConnection, String parentPathNameOrId, String maskId, String name) throws de.elo.utils.net.RemoteException {
        Sord sord = null;
        sord = ixConnection.ix().createSord(parentPathNameOrId, maskId, SordC.mbAll);
        sord.setName(name);
        return sord;
    }

    public Sord getSord(IXConnection ixConnection, String pathNameOrId, SordZ sordZ, LockZ lockZ) throws de.elo.utils.net.RemoteException {
        Sord sord = null;
        if (!isInteger(pathNameOrId)) {
            pathNameOrId = fileUtilsRegular.convertPathName(pathNameOrId, fileUtilsElo);
        }
        try {
            sord = ixConnection.ix().checkoutSord(pathNameOrId, sordZ, lockZ);
        } catch (RemoteException e) {
            IXError ixError = IXError.parseException((de.elo.utils.net.RemoteException) e);
            if (ixError.code != 5023) //cale incorecta
            {
                throw e;
            } else {
                // return null because the sord does not exists
            }
        }
        return sord;
    }

    public void updateSordAttributes(Sord sord, Map<String, Object> attributes) {
        ObjKey[] objKeys = sord.getObjKeys();
        for (int i = 0; i < objKeys.length; i++) {
            if (attributes.containsKey(objKeys[i].getName())) {
                if (isArray(attributes.get(objKeys[i].getName()))) {
                    List<String> objKeyValue = new ArrayList<>();
                    for (Object value : ((String[]) attributes.get(objKeys[i].getName()))) {
                        objKeyValue.add(String.valueOf(value));
                    }
                    objKeys[i].setData(objKeyValue.toArray(new String[objKeyValue.size()]));
                } else {
                    objKeys[i].setData(new String[]{attributes.get(objKeys[i].getName()).toString()});
                }
            }
        }
        sord.setObjKeys(objKeys);
    }

    public boolean sordContainsAttribute(Sord sord, String attributeName) {
        ObjKey[] objKeys = sord.getObjKeys();
        for (int i = 0; i < objKeys.length; i++) {
            if (objKeys[i].getName().equals(attributeName)) {
                return true;
            }
        }
        return false;
    }

    public int saveSord(IXConnection ixConnection, Sord sord, SordZ sordZ, LockZ unlockZ) throws de.elo.utils.net.RemoteException {
        return ixConnection.ix().checkinSord(sord, sordZ, unlockZ);
    }


    public WFDiagram getWorkFlowTemplate(IXConnection ixConnection, String processDefinitionId, String versionId, WFDiagramZ wfDiagramZ, LockZ lockZ) throws de.elo.utils.net.RemoteException {
        WFDiagram wfDiagram = null;
        try {

            wfDiagram = ixConnection.ix().checkoutWorkflowTemplate(processDefinitionId, null, WFDiagramC.mbAll, LockC.NO);


        } catch (RemoteException e) {
            IXError ixError = IXError.parseException((de.elo.utils.net.RemoteException) e);
            if (ixError.code != 5023) //cale incorecta
            {
                throw e;
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            throw e;
        }
        return wfDiagram;
    }


    public WFDiagram getWorkFlow(IXConnection ixConnection, String flowId, WFTypeZ wfTypeZ, WFDiagramZ wfDiagramZ, LockZ lockZ) throws de.elo.utils.net.RemoteException {
        WFDiagram wfDiagram = null;
        try {
            wfDiagram = ixConnection.ix().checkoutWorkFlow(flowId, wfTypeZ, wfDiagramZ, lockZ);
        } catch (RemoteException e) {
            IXError ixError = IXError.parseException((de.elo.utils.net.RemoteException) e);
            if (ixError.code != 5023) //cale incorecta
            {
                throw e;
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            throw e;
        }
        return wfDiagram;
    }

    public String startWorkFlow(IXConnection ixConnection, String templateId, String name, String sordId) throws de.elo.utils.net.RemoteException {
        String workflowGUID = null;
        int workflowId = ixConnection.ix().startWorkFlow(templateId, name, sordId);
        WFDiagram wfDiagram = ixConnection.ix().checkoutWorkFlow(String.valueOf(workflowId), WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO);
        workflowGUID = wfDiagram.getGuid();
        workflowGUID = workflowGUID.replace("(", "").replace(")", "");
        return workflowGUID;
    }

    public WFNode getNode(IXConnection ixConnection, String workflowId, Integer nodeId) throws de.elo.utils.net.RemoteException {
        for (WFNode wFNode : getWorkFlow(ixConnection, workflowId, WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO).getNodes()) {
            if (wFNode.getId() == nodeId) {
                return wFNode;
            }
        }
        return null;
    }

    public String[] splitLoginUsers(String userIdentification) {
        String[] userNames = userIdentification.split("@");
        return userNames;
    }

    public Integer createUserGroup(IXConnection ixConnection, String groupName, String rightsAsUserId) {
        try {
            UserInfo group = ixConnection.ix().createUser(rightsAsUserId);
            group.setType(UserInfoC.TYPE_GROUP);
            group.setName(groupName);
            int[] ints = ixConnection.ix().checkinUsers(new UserInfo[]{group}, CheckinUsersC.NEW_USER, LockC.YES);
            return ints[0];
        } catch (de.elo.utils.net.RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkIfGroupExist(IXConnection ixConnection, String groupName) {
        try {
            ixConnection.ix().checkoutUsers(new String[]{groupName}, CheckoutUsersC.ALL_GROUPS, LockC.NO);
            return true;
        } catch (de.elo.utils.net.RemoteException e) {
            return false;
        }
    }

    public boolean existSord(IXConnection ixConnection, String pathNameOrId) {
        boolean existSord = false;
        try {
            ixConnection.ix().checkoutSord(pathNameOrId, SordC.mbAll, LockC.NO);
            existSord = true;
        } catch (de.elo.utils.net.RemoteException e) {
            existSord = false;
        }
        return existSord;
    }


    public List<WFNode> getCurrentNodesFromWFDiagram(WFDiagram wfDiagram) {
        List<WFNodeAssoc> nodeAssocs = Arrays.asList(wfDiagram.getMatrix().getAssocs());
        List<Integer> nodesFromNotDone = new ArrayList();
        List<Integer> nodesToDone = new ArrayList();
        for (WFNodeAssoc assoc : nodeAssocs) {
            if (assoc.isDone()) {
                nodesToDone.add(assoc.getNodeTo());
            } else {
                nodesFromNotDone.add(assoc.getNodeFrom());
            }
        }
        nodesFromNotDone.retainAll(nodesToDone);
        List<WFNode> allNodes = Arrays.asList(wfDiagram.getNodes());
        List<WFNode> currentNodesList = new ArrayList<>();
        for (Integer currentNodeId : nodesFromNotDone) {
            for (WFNode wfNode : allNodes) {
                if (currentNodeId.compareTo(wfNode.getId()) == 0) {
                    currentNodesList.add(wfNode);
                }
            }
        }
        return currentNodesList;
    }
}
