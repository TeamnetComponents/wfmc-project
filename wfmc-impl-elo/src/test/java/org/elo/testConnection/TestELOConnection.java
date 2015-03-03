package org.elo.testConnection;

import de.elo.ix.client.*;
import de.elo.utils.net.RemoteException;


import java.util.*;

/**
 * Created by andras on 2/18/2015.
 */

public class TestELOConnection {

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

        // Get contstant values (cached by IXClient object)
        CONS = ix.getCONST();
        EDIT_INFO = CONS.getEDIT_INFO();
        SORD = CONS.getSORD();
        LOCK = CONS.getLOCK();
        ARCHIVING_MODE = CONS.getARCHIVING_MODE();
        DOC_MASK = CONS.getDOC_MASK();
        DOC_MASK_LINE = CONS.getDOC_MASK_LINE();
        WORKFLOW_TYPE = CONS.getWORKFLOW_TYPE();

    }

    // Logout from IndexServer
    public static void disconnectIx() {
        if (ix != null) {
            ix.logout();
        }
        System.out.println("Logged off");
    }


    public static void main(String[] args) {
        try {
            // Login
            connectIx();
            System.out.println("My session id is = " + ix.getJSESSIONID());
            // do the tests

            testCollectWorkFlows();
            forwardTask(43, 7, 2);
           // testCreateWorkFlow();
           // testCollectWorkFlows();
            //testGetTasks();
            getTasks(LOGIN_NAME);
            getSuccNodes(readDiagram("32",WORKFLOW_TYPE.getACTIVE()),1);
        } catch (RemoteException e) {
            System.out.println("ERROR= " +e.toString());
        } catch (java.rmi.RemoteException e) {
            e.printStackTrace();
        } finally {
            // logout from Index Server
            disconnectIx();
        }
    }

    public static void testCollectWorkFlows() throws RemoteException {
        IdName[] template = ix.ix().collectWorkFlows(WORKFLOW_TYPE.getTEMPLATE());
        for (IdName idName: template){
            System.out.println("template = " + idName);
        }
        IdName[] active = ix.ix().collectWorkFlows(WORKFLOW_TYPE.getACTIVE());
        for (IdName idName: active){
            System.out.println("active = " + idName);
        }
        if (active.length == 0){
            System.out.println("nu am active");
        }
//        IdName[] finished = ix.ix().collectWorkFlows(WORKFLOW_TYPE.getFINISHED());
//        for (IdName idName: finished){
//            System.out.println("finished = " + idName);
//        }

    }


    public static void testCreateWorkFlow() throws RemoteException {
        WFTypeZ wfType = CONS.getWORKFLOW_TYPE().getTEMPLATE();
       // WFDiagram  wmDiagram  =  ix.ix().createWorkFlow("Template1", wfType);
       // System.out.println("Am Id! " + wmDiagram.getId());
        int objId = insertSord2();
        int  workspaceId = ix.ix().startWorkFlow("4", "Flow pornit din JAVA mea", Integer.toString(objId));
        System.out.println("id-ul de proces = " + workspaceId);

    }

    private static String parentId;
    private static String maskId;

    public static int insertSord2() throws RemoteException {
        // Pregatire obiect dosar
        // La acest moment nu a fost incarcat inca in arhiva
        parentId = "1"; // parentId = 1 este radacina arhivei
        maskId = "Dosar"; //Formularul de indexare
        EditInfo ed = ix.ix().createSord(parentId, maskId, EDIT_INFO.getMbSord());
        Sord sord = ed.getSord();

        // Setare nume dosar
        sord.setName("FolderTest " + (new Date()));

        // Setare cuvinte cheie
        ObjKey[] objKeys = sord.getObjKeys();
        objKeys[0].setData(new String[] {"FolderTest"});

        //Salvare dosar in arhiva

        int objId = ix.ix().checkinSord(sord, SORD.getMbAll(), LOCK.getNO());

        System.out.println("inserted sord: name=" + sord.getName() + ", id=" + objId + ", guid=" + sord.getGuid());
        return objId;
    }

    public static void testGetTasks() throws RemoteException {
        // Search for all workflow nodes using workflow name
        WFCollectNode[] nodes = ix.ix().collectWorkFlowNodes(
                "Flux editare atribute drumuri",        // Name of workflow
                null,            // WF type - null = active
                null,            // Node name
                0,               // Node type - 0 = all nodes
                Integer.toString(2), // objId for object
                null,            // Enter date
                null,            // Exit date
                null,            // User ids
                false,            // Only active nodes
                false);          // Only start nodes

        // List all found nodes
        System.out.println("Logged in with user WF-User1 - workflow admin rights");
        for (int i = 0; i < nodes.length; i++){

            System.out.println("    Node found:  nodeName= " + nodes[i].getNodeName() +
                    ", userName= " + nodes[i].getUserName() +
                    ", active = " + nodes[i].isActive());
        }
    }

    public static List<UserTask> getTasks(String userName) throws java.rmi.RemoteException {
        final int NUMBER_OF_RESULTS = 100;
        List<UserTask> tasks = new ArrayList<>();
        //construire criteriu de cautare
        FindTasksInfo findTasksInfo = new FindTasksInfo();
        findTasksInfo.setInclWorkflows(true);

        FindResult findResult = ix.ix().findFirstTasks(findTasksInfo, NUMBER_OF_RESULTS);
        tasks.addAll(Arrays.asList(findResult.getTasks()));
        int count = 0;
        //cautare se face paginata
        while (findResult.isMoreResults()) {
            ix.ix().findNextTasks(findResult.getSearchId(), tasks.size(), count * NUMBER_OF_RESULTS);
            count++;
        }
        //inchidem cautarea
        ix.ix().findClose(findResult.getSearchId());
        return tasks;

    }

    public static WFDiagram readDiagram(String workflowId, WFTypeZ wFTypeZ) throws java.rmi.RemoteException {
        return ix.ix().checkoutWorkFlow(workflowId, wFTypeZ, WFDiagramC.mbAll, LockC.NO);
    }

    //Intoarce nodurile succesoare
    //3. Obtinem “next steps” – obtinem lista de rezolutii “tranzitii” posibile pe care le putem utiliza la un pas in flux
    public static List<WFNode> getSuccNodes(WFDiagram workflow, Integer nodeId) {
        List<WFNode> ret = new ArrayList<>();
        for (WFNodeAssoc assoc : workflow.getMatrix().getAssocs()) {
            if (assoc.getNodeFrom() == nodeId) {
                ret.add(getNode(workflow, assoc.getNodeTo()));
            }
        }
        return ret;
    }




    public static WFNode getNode(WFDiagram workflow, String nodeName) {

        for (WFNode wFNode : workflow.getNodes()) {
            if (wFNode.getName().equals(nodeName)) {
                return wFNode;
            }
        }
        return null;
    }

    //Intoarcere noduri in functie de id sau nume
    public static WFNode getNode(WFDiagram workflow, Integer nodeId) {
        for (WFNode wFNode : workflow.getNodes()) {
            if (wFNode.getId() == nodeId) {
                return wFNode;
            }
        }
        return null;
    }

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
}
