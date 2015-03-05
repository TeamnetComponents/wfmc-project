package org.elo.testConnection;

import de.elo.ix.client.*;
import de.elo.utils.net.RemoteException;
import org.wfmc.wapi.WMFilter;

import java.util.*;

/**
 * Created by Ioan.Ivan on 2/20/2015.
 */
public class TestPoc {
    private final static String IX_URL = "http://10.6.38.90:8080/ix-elo/ix";
    private final static String LOGIN_NAME = "Administrator";
    private final static String LOGIN_PWD = "elo@RENNS2015";
    private final static String CNN_NAME = "IX-Workshop";
    private static IXConnection ix;
    private static IXServicePortC CONS;
    private static EditInfoC EDIT_INFO;
    private static SordC SORD;
    private static LockC LOCK;
    private static ArchivingModeC ARCHIVING_MODE;
    private static DocMaskC DOC_MASK;
    private static DocMaskLineC DOC_MASK_LINE;
    private static WFTypeC WORKFLOW_TYPE;

    public static void connectIx() throws RemoteException {
        Properties connProps = IXConnFactory.createConnProps(IX_URL); //URL
        Properties sessOpts = IXConnFactory.createSessionOptions("IX-Example", "1.0");
        IXConnFactory connFact = new IXConnFactory(connProps, sessOpts);
        ix = connFact.create(LOGIN_NAME, LOGIN_PWD, CNN_NAME, LOGIN_NAME);
    }

    public static void disconnect() {
        if (ix != null) {
            ix.logout();
            ix = null;

        }
        System.out.println("Logged off");
    }

    public static WFDiagram getActiveWorkflowById (Integer workflowId) throws RemoteException {
        FindWorkflowInfo findWorkflowInfo = new FindWorkflowInfo();
        findWorkflowInfo.setType(WFTypeC.ACTIVE);
        FindResult findResult = ix.ix().findFirstWorkflows(findWorkflowInfo, 10, WFDiagramC.mbAll);
        WFDiagram[] wfDiagrams = findResult.getWorkflows();
        for (WFDiagram wfDiagram : wfDiagrams) {
            if (workflowId == wfDiagram.getId() )
                return wfDiagram;
        }
        return null;
    }

    /**
     * a)Pornirea unui workflow.
     *
     * @param tempFlowId id-ul workflow-ului ce va fi pornit.
     * @param tempFlowName setam numele workflow-ului.
     * @param sordId id-ul sordului pentru care o sa fie pornit fluxul.
     * @return id-ul workflow-ului ce o sa fie pornit. (id-ul din active workflows).
     */
    public static int startWorkflow(String tempFlowId, String tempFlowName, String sordId) {
        try {
            Integer workspaceId = ix.ix().startWorkFlow(tempFlowId, tempFlowName, sordId);
            return workspaceId;
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * b)Adaugare atribute pe un workflow.
     *
     * @param procInstId id-ul workflow-ului ce va fi pornit.
     * @param attrName numele atributului.
     * @param attrValue valoarea atributului.
     */
    public static void setWorkflowAttribute(String procInstId, String attrName, Object attrValue) throws RemoteException {
        FindWorkflowInfo findWorkflowInfo = new FindWorkflowInfo();
        findWorkflowInfo.setType(WFTypeC.ACTIVE);
        FindResult findResult = ix.ix().findFirstWorkflows(findWorkflowInfo, 10, WFDiagramC.mbAll);
        WFDiagram[] wfDiagrams = findResult.getWorkflows();
        System.out.println(wfDiagrams.length);
        for (WFDiagram wfDiagram : wfDiagrams){
            if (Integer.toString(wfDiagram.getId()).equals(procInstId)) {
                System.out.println(wfDiagram);
                switch (attrName) {
                    case "acces" : wfDiagram.setAccess((Integer) attrValue);
                        break;
                    case "acl" : wfDiagram.setAcl((String) attrValue);
                        break;
                    case "acl items" : wfDiagram.setAclItems((AclItem[]) attrValue);
                        break;
                    case "completionDateIso" : wfDiagram.setCompletionDateIso((String) attrValue);
                        break;
                    case "deleted" : wfDiagram.setDeleted((boolean) attrValue);
                        break;
                    case "flags" : wfDiagram.setFlags((Integer) attrValue);
                        break;
                    case "guid" : wfDiagram.setGuid((String) attrValue);
                        break;
                    case "id" : wfDiagram.setId((Integer) attrValue);
                        break;
                    case "lockId" : wfDiagram.setLockId((Integer) attrValue);
                        break;
                    case "lockName" : wfDiagram.setLockName((String) attrValue);
                        break;
                    case "matrix" : wfDiagram.setMatrix((WFNodeMatrix) attrValue);
                        break;
                    case "name" : wfDiagram.setName((String) attrValue);
                        break;
                    case "nodes" : wfDiagram.setNodes((WFNode[]) attrValue);
                        break;
                    case "objId" : wfDiagram.setObjId((String) attrValue);
                        break;
                    case "objName" : wfDiagram.setObjName((String) attrValue);
                        break;
                    case "objType" : wfDiagram.setObjType((Integer) attrValue);
                        break;
                    case "overTimeLimit" : wfDiagram.setOverTimeLimit((boolean) attrValue);
                        break;
                    case "ownerId" : wfDiagram.setOwnerId((Integer) attrValue);
                        break;
                    case "ownerName" : wfDiagram.setOwnerName((String) attrValue);
                        break;
                    case "prio" : wfDiagram.setPrio((Integer) attrValue);
                        break;
                    case "processOnServerId" : wfDiagram.setProcessOnServerId((String) attrValue);
                        break;
                    case "startDateIso" : wfDiagram.setStartDateIso((String) attrValue);
                        break;
                    case "templateId" : wfDiagram.setTemplateId((Integer) attrValue);
                        break;
                    case "templateName" : wfDiagram.setTemplateName((String) attrValue);
                        break;
                    case "timeLimit" : wfDiagram.setTimeLimit((Integer) attrValue);
                        break;
                    case "timeLimitEscalation" : wfDiagram.setTimeLimitEscalations((WFTimeLimit[]) attrValue);
                        break;
                    case "timeLimitIso" : wfDiagram.setTimeLimitIso((String) attrValue);
                        break;
                    case "timeLimitUserId" : wfDiagram.setTimeLimitUserId((Integer) attrValue);
                        break;
                    case "timeLimitUserName" : wfDiagram.setTimeLimitUserName((String) attrValue);
                        break;
                    case "tStamp" : wfDiagram.setTStamp((String) attrValue);
                        break;
                    case "type" : wfDiagram.setType((WFTypeZ) attrValue);
                        break;
                    case "version" : wfDiagram.setVersion((WFVersion) attrValue);
                        break;
                }
            }
            ix.ix().checkinWorkFlow(wfDiagram, WFDiagramC.mbAll, LockC.NO);
        }
    }

    /**
     * c)Next step pentru un nod.
     *
     * @param workflowId id-ul workflow-ului.
     * @param nodeId id-ul nodului pentru care vrem sa aducem urmatoarele noduri.
     * @return o lista cu nodurile gasite.
     */
    public static List<WFNode> getNextNodes(Integer workflowId, Integer nodeId) throws RemoteException {
        List<WFNode> nextNodes = new ArrayList<WFNode>();
        WFNodeAssoc[] wfNodeAssoc = getActiveWorkflowById(workflowId).getMatrix().getAssocs();
        for (WFNodeAssoc wfNode : wfNodeAssoc) {
            if (wfNode.getNodeFrom() == nodeId) {
                nextNodes.add(getNode(workflowId, wfNode.getNodeTo()));
            }
        }
        return nextNodes;
    }

    public static WFNode getNode(Integer workflowId, Integer nodeId) throws RemoteException {
        for (WFNode wFNode : getActiveWorkflowById(workflowId).getNodes()) {
            if (wFNode.getId() == nodeId) {
                return wFNode;
            }
        }
        return null;
    }

    /**
     * d)Atribuim o rezolutie pe un pas din flux.
     * @param workflowId id-ul workflow-ului.
     * @param nodeId id-ul nodului pentru care vrem sa aducem urmatoarele noduri.
     * @param nodeIdToForward id-ul nodului catre care sa ne ducem.
     * Momentan se duce pe toate tranzitiile gasite, cand apelam metoda trebuie sa se duca catre un nod ales de noi.
     */
    public static void forwardTask(Integer workflowId, Integer nodeId, Integer nodeIdToForward) throws RemoteException {
        WFEditNode wfEditNode = ix.ix().beginEditWorkFlowNode(workflowId, nodeId, LockC.YES);
//        List<WFNode> wfNodes = getNextNodes(workflowId, nodeId);
        int[] nextNodesId = new int[1];
//        Integer i = 0;
//        for (WFNode wfNode : wfNodes) {
//            nextNodesId[i] = wfNode.getId();
//        }
        nextNodesId[0] = nodeIdToForward;
        ix.ix().endEditWorkFlowNode(workflowId, nodeId, false, false, wfEditNode.getNode().getName(), wfEditNode.getNode().getComment(), nextNodesId);
    }

    public static void findTaskByUserOrGroupName(String userOrGroupName) throws RemoteException {
        FindTasksInfo findTasksInfo = new FindTasksInfo();
        findTasksInfo.setInclActivities(true);
        findTasksInfo.setInclReminders(true);
        findTasksInfo.setInclWorkflows(true);
        findTasksInfo.setAllUsers(true);
        FindResult findResult = ix.ix().findFirstTasks(findTasksInfo, 20);
        UserTask[] userTasks = findResult.getTasks();
        for (UserTask userTask : userTasks) {
            Sord sord = ix.ix().checkoutSord(String.valueOf(userTask.getWfNode().getObjId()), SordC.mbAll, LockC.NO);
            ObjKey[] objKeys = sord.getObjKeys();

            if (userTask.getWfNode().getUserName().equals(userOrGroupName)){
                userTask.getWfNode().setUserName("Administrator");
                System.out.println(userTask.getWfNode());
            }
        }
    }

    public static void findTaskByUser(String userName) throws RemoteException {
        UserInfo[] groupsInfo = ix.ix().checkoutUsers(null, CheckoutUsersC.ALL_GROUPS, LockC.YES);
        HashMap<Integer, String> groupsName = new HashMap<>();
        UserInfo[] userInfos = ix.ix().checkoutUsers(null, CheckoutUsersC.ALL_USERS, LockC.YES);
        HashMap<String, Integer> usersName = new HashMap();
        Integer i = 0;
        for (UserInfo groupInfo : groupsInfo) {
            groupsName.put(groupInfo.getId(), groupInfo.getName());
        }

        for (UserInfo userInfo : userInfos) {
            usersName.put(userInfo.getName(), i);
            i++;
        }

        if (usersName.containsKey(userName)) {
            findTaskByUserOrGroupName(userName);
            int[] listGroupIds = userInfos[usersName.get(userName)].getGroupList();
            for (Integer groupId : listGroupIds) {
                findTaskByUserOrGroupName(groupsName.get(groupId));
            }

        } else if (groupsName.containsValue(userName)) {
            findTaskByUserOrGroupName(userName);
        } else {
            System.out.println("User Name did not exist!");
        }
    }

    public static void listWorkItems(WMFilter filter) throws RemoteException {
        FindTasksInfo findTasksInfo = new FindTasksInfo();
        findTasksInfo.setInclActivities(true);
        findTasksInfo.setInclReminders(true);
        findTasksInfo.setInclWorkflows(true);
        findTasksInfo.setAllUsers(true);
        FindResult findResult = ix.ix().findFirstTasks(findTasksInfo, 20);
        UserTask[] userTasks = findResult.getTasks();
        for (UserTask userTask : userTasks) {
            if(userTask.getWfNode()!=null){
            Sord sord = ix.ix().checkoutSord(String.valueOf(userTask.getWfNode().getObjId()), SordC.mbAll, LockC.NO);
            ObjKey[] objKeys = sord.getObjKeys();
            for (int i = 0; i < objKeys.length; i++) {
                if (filter.getAttributeName().equals(objKeys[i].getName())&&(filter.getFilterValue().equals(objKeys[i].getData()))){
                    System.out.println(userTask.getWfNode().getFlowName() + userTask.getWfNode().getFlowStatus());
                }
            }
        }
        }
    }

    public static void listSord() throws RemoteException {
        FindInfo findInfo = new FindInfo();
        FindResult firstSords = ix.ix().findFirstSords(findInfo, 2000, SordC.mbAll);
        Sord[] sords = firstSords.getSords();
        for (Sord sord : sords) {
            System.out.println(sord.getName());
        }
    }

    public static void listValuesOfObjKey() throws RemoteException {
        ValuesOfObjKey objKeys = ix.ix().getDistinctValuesOfObjKey("Modificare denumire drum", null);
        System.out.println(objKeys.toString());
    }

    public static List<WFDiagram> getResults(FindWorkflowInfo findWorkflowInfo) throws RemoteException {

        List<WFDiagram> listaFluxuri = new ArrayList<>();
        FindResult findResult = ix.ix().findFirstWorkflows(findWorkflowInfo, 500, WFDiagramC.mbAll);
        listaFluxuri.addAll(Arrays.asList(findResult.getWorkflows()));
        while (findResult.isMoreResults()) {
            findResult = ix.ix().findNextWorkflows(findResult.getSearchId(), listaFluxuri.size(), 500);
            listaFluxuri.addAll(Arrays.asList(findResult.getWorkflows()));
        }
        ix.ix().findClose(findResult.getSearchId());
        return listaFluxuri;
    }

    public static void getMask() throws RemoteException {
        for (int i = 1; i < 100; i++) {
            DocMask docMask = ix.ix().checkoutDocMask("5", DocMaskC.mbAll, LockC.NO);
            System.out.println(docMask);
        }
    }

    public static void getSord() throws RemoteException {
        Sord sord = ix.ix().checkoutSord("104", SordC.mbAll, LockC.NO);
        System.out.println(sord);
    }

    public static void getWorkItem(String procId, String workItemId) throws RemoteException {
        WFDiagram wfDiagram = ix.ix().checkoutWorkFlow(procId, WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO);
        WFNode[] nodes = wfDiagram.getNodes();
        nodes[Integer.parseInt(workItemId)].setUserName(ix.getUserName());
        wfDiagram.setNodes(nodes);
        ix.ix().checkinWorkFlow(wfDiagram, WFDiagramC.mbAll, LockC.NO);
    }







    public static void main(String[] args) {
        try {
            connectIx();

//            int wfId = ix.ix().startWorkFlow("Template1", "Test Metoda 5", "104");
//            WFDiagram wFDiagram = ix.ix().checkoutWorkFlow(String.valueOf(wfId), WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO);
//            wFDiagram.setTemplateName("Test reusit");
//            ix.ix().checkinWorkFlow(wFDiagram, WFDiagramC.mbAll, LockC.NO);
//            FindWorkflowInfo findWorkflowInfo = new FindWorkflowInfo();
//            findWorkflowInfo.setTemplateId("Test reusit");
//            getResults(findWorkflowInfo);

//            getMask();
//            getSord();
//            findTaskByUser("Gigi");
//            listValuesOfObjKey();
            WMFilter filter = new WMFilter("TIPDRUM", WMFilter.EQ, "tip drum 1");
            listWorkItems(filter);
//            listSord();
//            getWorkItem("43","7");
//            getActiveWorkflowById(43);
//            setWorkflowAttribute("35","name", "myWorkflow");
//            startWorkflow("1","fluxul meu", "4");
//            System.out.println(getNextNodes(54, 7));
//            forwardTask(54, 7, 2);
            disconnect();
//            System.out.println(ix);
//            System.out.println("My name is " + ix.getUserName());
        } catch (RemoteException e) {
            System.out.println("Error connection!");
        }
    }
}