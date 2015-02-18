package org.elo.testConnection;

import de.elo.ix.client.*;
import de.elo.utils.net.RemoteException;


import java.util.Date;
import java.util.Properties;

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
        ix = connFact.create(LOGIN_NAME, LOGIN_PWD, CNN_NAME, null);

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
            testCreateWorkFlow();
            testCollectWorkFlows();
        } catch (RemoteException e) {
            System.out.println("ERROR= " +e.toString());
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

}
