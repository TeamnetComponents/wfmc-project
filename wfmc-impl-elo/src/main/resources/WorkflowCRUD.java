package ro.elo.learning.workflows;

import de.elo.ix.client.FindResult;
import de.elo.ix.client.FindTasksInfo;
import de.elo.ix.client.LockC;
import de.elo.ix.client.UserTask;
import de.elo.ix.client.WFDiagram;
import de.elo.ix.client.WFDiagramC;
import de.elo.ix.client.WFEditNode;
import de.elo.ix.client.WFNode;
import de.elo.ix.client.WFNodeAssoc;
import de.elo.ix.client.WFTypeZ;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static ro.elo.learning.sords.SordCRUD.getConnection;
import static ro.elo.learning.sords.SordCRUD.getConnectionRunAs;

public class WorkflowCRUD {

    public static WFDiagram readDiagram(String workflowId, WFTypeZ wFTypeZ) throws RemoteException {
        return getConnection().ix().checkoutWorkFlow(workflowId, wFTypeZ, WFDiagramC.mbAll, LockC.NO);
    }

    // a se folosi acelasi flag ca la checkout flag = WFDiagramC.FLAG (mbAll prefered)
    //Lock = daca sa se blocheze/deblocheze
    public static void saveWorkflow(WFDiagram wFDiagram) throws RemoteException {
        getConnection().ix().checkinWorkFlow(wFDiagram, WFDiagramC.mbAll, LockC.NO);
    }

    //Pornim o instanta de flux pe un template
    public static int startWorkflow(String name, String templateName, String sordId) throws RemoteException {
        return getConnection().ix().startWorkFlow(templateName, name, sordId);
    }

    //e.      Obtinem lista myTasks pentru un ROL  (sau in cazul in care ne conectam cu un singur utilizator … cum filtram instantele de proces care au o metadata setata cu o anumita valoare) 
    public static List<UserTask> getTasks(String userName) throws RemoteException {
        final int NUMBER_OF_RESULTS = 100;
        List<UserTask> tasks = new ArrayList<>();
        //construire criteriu de cautare
        FindTasksInfo findTasksInfo = new FindTasksInfo();
        findTasksInfo.setInclWorkflows(true);

        FindResult findResult = getConnectionRunAs(userName).ix().findFirstTasks(findTasksInfo, NUMBER_OF_RESULTS);
        tasks.addAll(Arrays.asList(findResult.getTasks()));
        int count = 0;
        //cautare se face paginata
        while (findResult.isMoreResults()) {
            getConnectionRunAs(userName).ix().findNextTasks(findResult.getSearchId(), tasks.size(), count * NUMBER_OF_RESULTS);
            count++;
        }
        //inchidem cautarea
        getConnectionRunAs(userName).ix().findClose(findResult.getSearchId());
        return tasks;

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

    public static WFNode getNode(WFDiagram workflow, String nodeName) {

        for (WFNode wFNode : workflow.getNodes()) {
            if (wFNode.getName().equals(nodeName)) {
                return wFNode;
            }
        }
        return null;
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
    
    public static void forwardTask() throws RemoteException{
        String userName = ""; //aici se va introduce numele userului.
        int flowId = 0;   //aici se va introduce id flux dorit pentru a fi trimis mai departe (flowId si nodeId il obtineti din UserTask)
        int nodeId = 0;   //aici se va intoduce if nod
        /**Locks a person task node of an active workflow inside the database and returns data needed to edit it*/
        WFEditNode wFEditNode = getConnectionRunAs(userName).ix().beginEditWorkFlowNode(flowId, nodeId, LockC.YES);
        //in wFEditNode gasiti informatii utile despre taskul curent selectat;
        //id-uri noduri care se vor activa
        int[] succesori = new int[]{};
        //wFEditNode.getSuccNodes() noduri succesoare posibile luate conform wfDiagram.matrix.assocs
        /**Stores an edited person node of an active workflow into the database and unlocks the workflow.
         The workflow is forwarded to the successor nodes as passed in parameter arrEnterNodesIds.*/
       getConnectionRunAs(userName).ix().endEditWorkFlowNode(flowId, nodeId, false, false, wFEditNode.getNode().getName(), wFEditNode.getNode().getComment(), succesori);
    }
    
    public static void main(String[] args) {
        
    }
    

}
