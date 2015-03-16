package org.wfmc.elo;

import de.elo.ix.client.*;
import de.elo.utils.net.RemoteException;
import junit.framework.Assert;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.wfmc.elo.base.WMErrorElo;
import org.wfmc.elo.base.WMProcessInstanceImpl_Elo;
import org.wfmc.elo.model.ELOConstants;
import org.wfmc.elo.model.ELOWfMCProcessInstanceAttributes;
import org.wfmc.elo.model.EloWfmcObjKey;
import org.wfmc.elo.model.EloWfmcProcessInstance;
import org.wfmc.elo.utils.EloUtilsService;
import org.wfmc.impl.base.WMAttributeImpl;
import org.wfmc.impl.base.WMProcessInstanceImpl;
import org.wfmc.impl.base.WMWorkItemAttributeNames;
import org.wfmc.impl.base.WMWorkItemImpl;
import org.wfmc.impl.base.filter.WMFilterBuilder;
import org.wfmc.impl.utils.WfmcUtilsService;
import org.wfmc.service.ServiceFactory;
import org.wfmc.service.WfmcServiceCache;
import org.wfmc.service.WfmcServiceCacheImpl_Memory;
import org.wfmc.wapi.*;
import org.wfmc.xpdl.model.transition.Transition;
import org.wfmc.xpdl.model.workflow.WorkflowProcess;

import java.io.IOException;
import java.util.*;

/**
 * @author adrian.zamfirescu
 * @since 20/02/2015
 */
public class WfmcServiceEloImplTest {
    private static final String STRADA = "strada";
    private WfmcServiceEloImpl wfmcServiceEloImpl;

    private WMConnectInfo wmConnectInfo;

    private static ResourceBundle configBundle = ResourceBundle.getBundle("config");

    @Before
    public void setUp() throws IOException {

        // create main service
        wfmcServiceEloImpl = new WfmcServiceEloImpl();

        // create some test cache properties
        Properties cacheTestProperties = new Properties();
        cacheTestProperties.setProperty(ServiceFactory.INSTANCE_NAME, "testCache");

        // create a test service cache
        WfmcServiceCache wfmcServiceCache = new WfmcServiceCacheImpl_Memory();
        wfmcServiceCache.setWfmcService(wfmcServiceEloImpl);
        wfmcServiceEloImpl.setWfmcServiceCache(wfmcServiceCache);
        wfmcServiceCache.__initialize(cacheTestProperties);

        // set the ELO connection attributes
        String loginName = "Andra@"+ configBundle.getString("login.name");
        wmConnectInfo = new WMConnectInfo(loginName,
                configBundle.getString("login.password"),
                configBundle.getString("cnn.name"),
                configBundle.getString("ix.url"));
    }

    @After
    public void cleanUp(){
        wfmcServiceEloImpl.disconnect();
    }

    @Test
    public void shouldCreateEloConnection(){

        // given - all is set up

        // when
        wfmcServiceEloImpl.connect(wmConnectInfo);

        // then
        Assertions.assertThat(wfmcServiceEloImpl.getIxConnection()).isNotNull();

    }

    @Test
    public void checkEloDisconnection(){
        WMConnectInfo wmConnectInfo = new WMConnectInfo("Andra@"+configBundle.getString("login.name"),
                configBundle.getString("login.password"),
                configBundle.getString("cnn.name"),
                configBundle.getString("ix.url"));
        wfmcServiceEloImpl.connect(wmConnectInfo);

        wfmcServiceEloImpl.disconnect();

        Assertions.assertThat(wfmcServiceEloImpl.getIxConnection() == null);
    }


    @Ignore
    public void shouldStartEloWorkflowProcessDeprecated(){

        // given
        String processInstanceId = "someProcessInstanceId";
        EloWfmcProcessInstance wmProcessInstance = new EloWfmcProcessInstance();
        // setting the template ID
        wmProcessInstance.setProcessDefinitionId("4");
        wmProcessInstance.setName("Test Workflow Name");
        //idSORD fisier TEST WF (se presupune ca acesta va exista permanent in arhiva ELO de test)
        wmProcessInstance.setEloWfMCProcessInstanceAttributes(new ELOWfMCProcessInstanceAttributes("104"));

        wfmcServiceEloImpl = Mockito.spy(wfmcServiceEloImpl);
        Mockito.doReturn(wmProcessInstance).when(wfmcServiceEloImpl).getProcessInstance(Mockito.<String>any());
        Mockito.doNothing().when(wfmcServiceEloImpl).abortProcessInstance(processInstanceId);

        wfmcServiceEloImpl.connect(wmConnectInfo);

        // when
        String workspaceId = wfmcServiceEloImpl.startProcess(processInstanceId);

        // then
        Assertions.assertThat(workspaceId).isNotNull();

    }

    @Test
    public void shouldCreateProcessInstance(){

        // given
        String processDefinitionId = "5";
        String processInstanceName = "TestProcInstName";

        // when
        String processInstanceId = wfmcServiceEloImpl.createProcessInstance(processDefinitionId, processInstanceName);

        // then
        Assertions.assertThat(processInstanceId).isNotNull();
        Assertions.assertThat(wfmcServiceEloImpl.getWfmcServiceCache().getProcessInstance(processInstanceId)).isNotNull();

    }


    @Test
    public void shouldGetWorkFlowProcess() throws RemoteException {
        //checking for the right transitions

        // given
        String wfId = "1";
        String nodeId = "2";

        EloUtilsService eloUtilsService = Mockito.mock(EloUtilsService.class);
        wfmcServiceEloImpl.setEloUtilsService(eloUtilsService);
        int arrayLength = 6;
        WFNodeAssoc[] wfNodeAssoc = new WFNodeAssoc[arrayLength];
        wfNodeAssoc[0] = new WFNodeAssoc();
        wfNodeAssoc[0].setNodeTo(1);
        wfNodeAssoc[0].setNodeFrom(0);

        wfNodeAssoc[1] = new WFNodeAssoc();
        wfNodeAssoc[1].setNodeTo(2);
        wfNodeAssoc[1].setNodeFrom(1);

        wfNodeAssoc[2] = new WFNodeAssoc();
        wfNodeAssoc[2].setNodeTo(4);
        wfNodeAssoc[2].setNodeFrom(2);

        wfNodeAssoc[3] = new WFNodeAssoc();
        wfNodeAssoc[3].setNodeTo(5);
        wfNodeAssoc[3].setNodeFrom(2);

        wfNodeAssoc[4] = new WFNodeAssoc();
        wfNodeAssoc[4].setNodeTo(4);
        wfNodeAssoc[4].setNodeFrom(5);

        wfNodeAssoc[5] = new WFNodeAssoc();
        wfNodeAssoc[5].setNodeTo(5);
        wfNodeAssoc[5].setNodeFrom(4);

        WFDiagram wfDiagram = Mockito.mock(WFDiagram.class);

        Mockito.when(eloUtilsService.getWorkFlowTemplate(Mockito.<IXConnection>any(), Mockito.eq(wfId), Mockito.eq(""), Mockito.eq(WFDiagramC.mbAll), Mockito.eq(LockC.NO))).thenReturn(wfDiagram);

        WFNodeMatrix matrix = Mockito.mock(WFNodeMatrix.class);
        Mockito.when(wfDiagram.getMatrix()).thenReturn(matrix);

        Mockito.when(matrix.getAssocs()).thenReturn(wfNodeAssoc);
        Mockito.when(eloUtilsService.getNode(Mockito.<IXConnection>any(), Mockito.eq(wfId), Mockito.<Integer>any())).thenReturn(new WFNode());

        // when
        WorkflowProcess workFlowProcess = wfmcServiceEloImpl.getWorkFlowProcess(wfId);



        // then
        Transition[] transitions = workFlowProcess.getTransition();

        Mockito.verify(eloUtilsService).getWorkFlowTemplate(Mockito.<IXConnection>any(), Mockito.eq(wfId), Mockito.eq(""), Mockito.eq(WFDiagramC.mbAll), Mockito.eq(LockC.NO));

        Assertions.assertThat(transitions).hasSize(6);

        //verificam tranzitiile de la nodul 2 - (next steps)

        List<String> to = new LinkedList<String>();
        for(int i=0;i<transitions.length;i++){
            if(transitions[i].getFrom().equals(nodeId))
                to.add(transitions[i].getTo());


        }

        Assertions.assertThat(to).hasSize(2);
        Assertions.assertThat(to).contains("4","5");

    }



    @Ignore
    public void shouldAssignPprocessInstanceAttributeMaskId(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = ELOConstants.MASK_ID;
        String attributeValue = "Dosar";
        wfmcServiceEloImpl.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        wfmcServiceEloImpl = Mockito.spy(wfmcServiceEloImpl);
        Mockito.when(wfmcServiceEloImpl.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);

        // when
        wfmcServiceEloImpl.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

        // then
        Assertions.assertThat(eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes()).isNotNull();
        Assertions.assertThat(((ELOWfMCProcessInstanceAttributes)eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes()).getMaskId()).isEqualTo("Dosar");
        // TODO - complete test assertions after adding metadata
    }

    @Ignore
    public void shouldAssignProcessInstanceAttributeMaskFromComment(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = "Tip drum";
        String attributeValue = "Strada";
        wfmcServiceEloImpl.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        wfmcServiceEloImpl = Mockito.spy(wfmcServiceEloImpl);
        Mockito.when(wfmcServiceEloImpl.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);
        eloWfmcProcessInstance.setProcessDefinitionId("Template1");

        // when
        wfmcServiceEloImpl.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

        // then
        Assertions.assertThat(eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes()).isNotNull();

        // TODO - complete test assertions after adding metadata
    }

    @Ignore
    public void shouldAssignProcessInstanceAttributeSordIdException(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = ELOConstants.SORD_ID;
        String attributeValue = "-1";
        wfmcServiceEloImpl.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        wfmcServiceEloImpl = Mockito.spy(wfmcServiceEloImpl);
        Mockito.when(wfmcServiceEloImpl.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);

        // when
        wfmcServiceEloImpl.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

        // then
    }

    @Ignore
    public void shouldAssignProcessInstanceAttributeMaskIdException(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = ELOConstants.MASK_ID;
        String attributeValue = "-1";
        wfmcServiceEloImpl.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        wfmcServiceEloImpl = Mockito.spy(wfmcServiceEloImpl);
        Mockito.when(wfmcServiceEloImpl.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);

        // when
        wfmcServiceEloImpl.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

    }

    @Ignore
    public void shouldAssignProcessInstanceAttributeMaskFromCommentException(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = "Tip drum";
        String attributeValue = "Strada";
        wfmcServiceEloImpl.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        wfmcServiceEloImpl = Mockito.spy(wfmcServiceEloImpl);
        Mockito.when(wfmcServiceEloImpl.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);
        eloWfmcProcessInstance.setProcessDefinitionId("Test");

        // when
        wfmcServiceEloImpl.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);
    }

    @Ignore//(expected = WMWorkflowException.class)
    public void shouldAssignProcessInstanceAttributeMaskFromCommentWorkflowNotExistException(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = "Tip drum";
        String attributeValue = "Strada";
        wfmcServiceEloImpl.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        wfmcServiceEloImpl = Mockito.spy(wfmcServiceEloImpl);
        Mockito.when(wfmcServiceEloImpl.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);
        eloWfmcProcessInstance.setProcessDefinitionId("-1");

        // when
        wfmcServiceEloImpl.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);
    }

    @Ignore
    public void shouldAssignProcessInstanceAttributeDeprecated(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = "Tip drum";
        Object attributeValue = new String[] {STRADA,"autostrada"};
        wfmcServiceEloImpl.connect(wmConnectInfo);


        wfmcServiceEloImpl = Mockito.spy(wfmcServiceEloImpl);
        EloWfmcProcessInstance eloWfmcProcessInstance = Mockito.mock(EloWfmcProcessInstance.class);
        Mockito.when(wfmcServiceEloImpl.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);
        ELOWfMCProcessInstanceAttributes eloWfMCProcessInstanceAttributes = Mockito.mock(ELOWfMCProcessInstanceAttributes.class);
        Mockito.when(eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes()).thenReturn(
            eloWfMCProcessInstanceAttributes);
        EloWfmcObjKey[] objKeys = new EloWfmcObjKey[5];
        for (int i = 0; i < 5 ; i++) {
            objKeys[i] = new EloWfmcObjKey();
            if (i == 2) {
                objKeys[i].setName(attributeName);
            }
        }
        Mockito.when(eloWfMCProcessInstanceAttributes.getObjKeys()).thenReturn(objKeys);

        // when
        wfmcServiceEloImpl.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

        // then
        if (attributeValue instanceof String[]) {
            Assertions.assertThat(objKeys[2].getValue()).isEqualTo((String[])attributeValue);
        } else {
            Assertions.assertThat(objKeys[2].getValue()).isEqualTo(new String[]{attributeValue.toString()});
        }
    }

    @Test(expected = WMWorkflowException.class)
    public void shouldRemoveProcessInstanceFromCache(){

        // given
        String procInstId = "TestProcInstId";

        WMProcessInstance wmProcessInstance = new WMProcessInstanceImpl(procInstId, String.valueOf(UUID.randomUUID()), procInstId);
        wfmcServiceEloImpl.getWfmcServiceCache().addProcessInstance(wmProcessInstance);

        // when
        wfmcServiceEloImpl.terminateProcessInstance(procInstId);

        // then
         wfmcServiceEloImpl.getProcessInstance(procInstId);

    }

    @Test
    public void shouldStartEloWorkflowProcess() throws RemoteException {

        String procInstId = "testProcInstId";

        // given
        wfmcServiceEloImpl = Mockito.spy(wfmcServiceEloImpl);

        EloUtilsService eloUtilsService = Mockito.mock(EloUtilsService.class);
        WfmcUtilsService wfmcUtilsService = Mockito.mock(WfmcUtilsService.class);
        WfmcServiceCache wfmcServiceCache = Mockito.mock(WfmcServiceCache.class);
        wfmcServiceEloImpl.setWfmcServiceCache(wfmcServiceCache);
        wfmcServiceEloImpl.setEloUtilsService(eloUtilsService);
        wfmcServiceEloImpl.setWfmcUtilsService(wfmcUtilsService);

        WMProcessInstance wmProcessInstance = new WMProcessInstanceImpl_Elo();
        Mockito.when(wfmcServiceCache.getProcessInstance(procInstId)).thenReturn(wmProcessInstance);

        Map<String, WMAttribute> wmProcessInstanceWMAttributeMap = new HashMap<>();
        Map<String, Object> wmProcessInstanceAttributeMap = new HashMap<>();

        Mockito.when(wfmcUtilsService.toWMAttributeMap(Mockito.<WMAttributeIterator>any())).thenReturn(wmProcessInstanceWMAttributeMap);
        Mockito.when(wfmcUtilsService.toMap(wmProcessInstanceWMAttributeMap)).thenReturn(wmProcessInstanceAttributeMap);

        WFDiagram wfDiagram = Mockito.mock(WFDiagram.class);
        Mockito.when(eloUtilsService.getWorkFlow(Mockito.<IXConnection>any(), Mockito.<String>any(),
            Mockito.<WFTypeZ>any(), Mockito.<WFDiagramZ>any(), Mockito.<LockZ>any())).thenReturn(wfDiagram);

        WFNode[] wfNodes = new WFNode[25];
        wfNodes[0] = new WFNode();
        String attributesString = "a=1;b=2;c=3";
        wfNodes[0].setComment(attributesString);
        Mockito.when(wfDiagram.getNodes()).thenReturn(wfNodes);

        // we suppose we have a SORD ID
        String sordId = "5";
        wmProcessInstanceWMAttributeMap.put(ELOConstants.SORD_ID, new WMAttributeImpl("SORD", 1, sordId));

        Sord testSord = new Sord();
        Mockito.when(eloUtilsService.getSord(Mockito.<IXConnection>any(), Mockito.eq(sordId), Mockito.<SordZ>any(), Mockito.<LockZ>any())).thenReturn(
            testSord);

        Mockito.doNothing().when(eloUtilsService).updateSordAttributes(testSord, wmProcessInstanceAttributeMap);
        Mockito.when(eloUtilsService.saveSord(Mockito.<IXConnection>any(), Mockito.eq(testSord), Mockito.<SordZ>any(),
            Mockito.<LockZ>any())).thenReturn(Integer.valueOf(sordId));

        String processInstanceId = "123";
        Mockito.when(eloUtilsService.startWorkFlow(Mockito.<IXConnection>any(), Mockito.<String>any(),
            Mockito.<String>any(), Mockito.eq(sordId))).thenReturn(processInstanceId);

        String newProcessInstanceId = "234";
        Mockito.doReturn(newProcessInstanceId).when(wfmcServiceEloImpl).startProcess(processInstanceId);

        // when
        String returnedProcInstId = wfmcServiceEloImpl.startProcess(procInstId);

        // then
        Mockito.verify(eloUtilsService).getSord(Mockito.<IXConnection>any(), Mockito.eq(sordId), Mockito.<SordZ>any(), Mockito.<LockZ>any());
        Mockito.verify(eloUtilsService).updateSordAttributes(testSord, wmProcessInstanceAttributeMap);
        Mockito.verify(eloUtilsService).startWorkFlow(Mockito.<IXConnection>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.eq(sordId));
        Assertions.assertThat(returnedProcInstId).isEqualTo(processInstanceId);

    }

    @Ignore
    public void integrationWorkflowTestDeprecated(){

        // given
        String workflowTemplateId = "4";
        String workflowName = "Integration Test Workflow Name";
        String sordId = "104";

        // when
        wfmcServiceEloImpl.connect(wmConnectInfo);
        String processInstanceId = wfmcServiceEloImpl.createProcessInstance(workflowTemplateId, workflowName);
        wfmcServiceEloImpl.assignProcessInstanceAttribute(processInstanceId, ELOConstants.SORD_ID, sordId);
        wfmcServiceEloImpl.startProcess(processInstanceId);
        wfmcServiceEloImpl.disconnect();

        // then
        // should terminate peacefully
        assert true;

    }

    @Test
    public void shouldReassignWorkItem () throws RemoteException {
        String sourceUser = "Andra";
        String targetUser = "Administrator";
        String workItemId = "7";
        wfmcServiceEloImpl.connect(wmConnectInfo);

        int test = wfmcServiceEloImpl.getIxConnection().ix().startWorkFlow("1", "test", "2");
        WFDiagram wfDiagram = wfmcServiceEloImpl.getIxConnection().ix().checkoutWorkFlow(String.valueOf(test), WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO);
        WFNode[] nodes = wfDiagram.getNodes();
        if ((nodes[Integer.parseInt(workItemId)].getName() != null) && (Integer.parseInt(workItemId) != 0)){
            nodes[Integer.valueOf(workItemId)].setUserName(sourceUser);
            wfDiagram.setNodes(nodes);
            wfmcServiceEloImpl.getIxConnection().ix().checkinWorkFlow(wfDiagram, WFDiagramC.mbAll, LockC.NO);

            wfmcServiceEloImpl.reassignWorkItem(sourceUser, targetUser, String.valueOf(test), workItemId);

            WFDiagram wfDiagram2 = wfmcServiceEloImpl.getIxConnection().ix().checkoutWorkFlow(String.valueOf(test), WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO);
            Assertions.assertThat(wfDiagram2.getNodes()[Integer.parseInt(workItemId)].getUserName()).isEqualTo(
                targetUser);
            wfmcServiceEloImpl.getIxConnection().ix().deleteWorkFlow(String.valueOf(test), WFTypeC.ACTIVE, LockC.NO);
        } else {

        }

    }

    @Test
    public void testErrorResource_buldle(){
        Assert.assertEquals(wfmcServiceEloImpl.getErrorMessagesResourceBundle().getString(WMErrorElo.ELO_ERROR_FILTER_NOT_SUPPORTED),
                "WMFilter type not supported!");
    }

    @Test(expected = WMUnsupportedOperationException.class)
    public void testListWorkItemsWMFilterNotSupported(){
        WMFilter wmFilter = new WMFilter("testing sql filter not supported");
        wfmcServiceEloImpl.listWorkItems(wmFilter, false);
    }

    @Test
    public void shouldGetNextSteps() throws RemoteException {

        // given
        String wfId = "1";
        String nodeId = "20";

        EloUtilsService eloUtilsService = Mockito.mock(EloUtilsService.class);
        wfmcServiceEloImpl.setEloUtilsService(eloUtilsService);

        WFNodeAssoc[] wfNodeAssoc = new WFNodeAssoc[5];
        wfNodeAssoc[0] = new WFNodeAssoc();
        wfNodeAssoc[0].setNodeFrom(15);
        wfNodeAssoc[1] = new WFNodeAssoc();
        wfNodeAssoc[1].setNodeFrom(20);
        wfNodeAssoc[2] = new WFNodeAssoc();
        wfNodeAssoc[2].setNodeFrom(25);
        wfNodeAssoc[3] = new WFNodeAssoc();
        wfNodeAssoc[3].setNodeFrom(20);
        wfNodeAssoc[4] = new WFNodeAssoc();
        wfNodeAssoc[4].setNodeFrom(30);

        WFDiagram wfDiagram = Mockito.mock(WFDiagram.class);
        Mockito.when(eloUtilsService.getWorkFlow(Mockito.<IXConnection>any(), Mockito.eq(wfId), Mockito.eq(WFTypeC.ACTIVE), Mockito.eq(WFDiagramC.mbAll), Mockito.eq(LockC.NO))).thenReturn(wfDiagram);
        WFNodeMatrix matrix = Mockito.mock(WFNodeMatrix.class);
        Mockito.when(wfDiagram.getMatrix()).thenReturn(matrix);

        Mockito.when(matrix.getAssocs()).thenReturn(wfNodeAssoc);
        Mockito.when(eloUtilsService.getNode(Mockito.<IXConnection>any(), Mockito.eq(wfId), Mockito.<Integer>any())).thenReturn(new WFNode());

        // when
        List<WMWorkItem> workItems = wfmcServiceEloImpl.getNextSteps(wfId, nodeId);

        // then
        Mockito.verify(eloUtilsService).getWorkFlow(Mockito.<IXConnection>any(), Mockito.eq(wfId), Mockito.eq(WFTypeC.ACTIVE), Mockito.eq(WFDiagramC.mbAll), Mockito.eq(LockC.NO));
        Assertions.assertThat(workItems).hasSize(2);
    }

    @Test
    public void shouldForwardTask() {

    }

    @Ignore
    public void testGetProcessInstanceWithFinishedWorkflow(){
        String flowId = "215";

        wfmcServiceEloImpl.connect(wmConnectInfo);
        WMProcessInstance wmProcessInstance = wfmcServiceEloImpl.getProcessInstance(flowId);
        wfmcServiceEloImpl.disconnect();

        Assertions.assertThat(wmProcessInstance).isNotNull();
    }

    @Test
    public void testAssignProcessInstanceAttributeForStartedProcessWithExistingAttrInSord() throws RemoteException {
        // given
        String workflowTemplateId = "4";
        String workflowName = "Integration Test Workflow Name";
        String sordId = "104";
        String attributeName = "TIPDRUM";
//        Object attributeValue = "Strada";
        Object attributeValue = new String[] {STRADA,"autostrada"};
        // when
        wfmcServiceEloImpl.connect(wmConnectInfo);
        String processInstanceId = wfmcServiceEloImpl.createProcessInstance(workflowTemplateId, workflowName);
        wfmcServiceEloImpl.assignProcessInstanceAttribute(processInstanceId, ELOConstants.SORD_ID, sordId);
        String eloProcessId = wfmcServiceEloImpl.startProcess(processInstanceId);
        wfmcServiceEloImpl.assignProcessInstanceAttribute(eloProcessId, attributeName, attributeValue);
        //verify
        EloUtilsService eloUtilsService = new EloUtilsService();
        Sord sord =  eloUtilsService.getSord(wfmcServiceEloImpl.getIxConnection(),sordId, SordC.mbAll, LockC.NO);
        ObjKey[] objKeys = sord.getObjKeys();
        for (ObjKey objKey : Arrays.asList(objKeys)){
            if (objKey.getName().equals(attributeName)){
                Assertions.assertThat(objKey.getData()[0]).isEqualTo(STRADA) ;
            }
        }
        wfmcServiceEloImpl.disconnect();
    }

    @Test(expected = WMInvalidAttributeException.class)
    public void testAssignProcessInstanceAttributeForStartedProcessWithoutExistingAttrInSord() throws RemoteException {
        // given
        String workflowTemplateId = "4";
        String workflowName = "Integration Test Workflow Name";
        String sordId = "104";
        String attributeName = "TIP DRUM";
        Object attributeValue = new String[] {STRADA,"autostrada"};
        // when
        wfmcServiceEloImpl.connect(wmConnectInfo);
        String processInstanceId = wfmcServiceEloImpl.createProcessInstance(workflowTemplateId, workflowName);
        wfmcServiceEloImpl.assignProcessInstanceAttribute(processInstanceId, ELOConstants.SORD_ID, sordId);
        String eloProcessId = wfmcServiceEloImpl.startProcess(processInstanceId);
        wfmcServiceEloImpl.assignProcessInstanceAttribute(eloProcessId, attributeName, attributeValue);
        wfmcServiceEloImpl.disconnect();
    }

    @Test
    public void shouldAssignWorkItemAttribute() {
        String processInstanceId = "5";
        String workItemId = "5";
        String attrName = WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString();
        Object attrValue = "5";
        Integer size = 10;

        for (int i = 0; i < size; i++) {
            wfmcServiceEloImpl.assignWorkItemAttribute(processInstanceId, workItemId, attrName, Integer.parseInt(
                (String) attrValue) + i);
        }

        Assertions.assertThat(wfmcServiceEloImpl.getWfmcServiceCache().getWorkItemAttribute(processInstanceId,
            workItemId)).isNotNull();
        Assertions.assertThat(
            wfmcServiceEloImpl.getWfmcServiceCache().getWorkItemAttribute(processInstanceId, workItemId).tsNext().getValue()).isEqualTo(
            Integer.parseInt((String) attrValue));
        Assertions.assertThat(
            wfmcServiceEloImpl.getWfmcServiceCache().getWorkItemAttribute(processInstanceId, workItemId).getCount()).isEqualTo(
            size);

    }

    @Test
    public void shouldListProcessInstances() throws RemoteException {
        String processDefinitionId = "5";
        String processName = "Test";

        wfmcServiceEloImpl.connect(wmConnectInfo);
        int processInstanceId = wfmcServiceEloImpl.getIxConnection().ix().startWorkFlow(processDefinitionId, processName, "6");
        WMFilter wmFilter = WMFilterBuilder.createWMFilterProcessInstance().isActive(true).addProcessDefinitionId(String.valueOf(processDefinitionId));

        WMProcessInstanceIterator wmProcessInstanceIterator = wfmcServiceEloImpl.listProcessInstances(wmFilter, true);
        List<WMProcessInstance> wmProcessInstances = new ArrayList<>();
        while (wmProcessInstanceIterator.hasNext()) {
            WMProcessInstance wmProcessInstance = wmProcessInstanceIterator.tsNext();

            wmProcessInstances.add(wmProcessInstance);
        }

        Assertions.assertThat(wmProcessInstances).isNotNull();
        wfmcServiceEloImpl.abortProcessInstance(String.valueOf(processInstanceId));
    }

    @Test
    public void shouldListWorkItem() throws RemoteException {
        String processDefinitionId = "5";
        String processName = "Test";

        wfmcServiceEloImpl.connect(wmConnectInfo);
        int processInstanceId = wfmcServiceEloImpl.getIxConnection().ix().startWorkFlow(processDefinitionId, processName, "6");
        WMFilter wmFilter = WMFilterBuilder.createWMFilterWorkItem().addProcessInstanceId(String.valueOf(processInstanceId));

        WMWorkItemIterator wmWorkItemIterator = wfmcServiceEloImpl.listWorkItems(wmFilter, true);
        List<WMWorkItem> wmWorkItems = new ArrayList<>();
        String newProcessInstanceId = null;
        while (wmWorkItemIterator.hasNext()) {
            WMWorkItem wmWorkItem = wmWorkItemIterator.tsNext();
            if (String.valueOf(processInstanceId).equals(wmWorkItem.getProcessInstanceId())) {
                newProcessInstanceId = wmWorkItem.getProcessInstanceId();
                wmWorkItems.add(wmWorkItem);
            }
        }

        Assertions.assertThat(wmWorkItems).isNotNull();
        Assertions.assertThat(newProcessInstanceId).isEqualTo(String.valueOf(processInstanceId));

        wfmcServiceEloImpl.abortProcessInstance(String.valueOf(processInstanceId));
    }

    @Test
    public void shouldGetWorkItem() throws RemoteException {
        String processDefinitionId = "5";
        String processName = "Test";
        String workItemId = "0";

        wfmcServiceEloImpl.connect(wmConnectInfo);
        int processInstanceId = wfmcServiceEloImpl.getIxConnection().ix().startWorkFlow(processDefinitionId, processName, "5");

        WMWorkItem workItem = wfmcServiceEloImpl.getWorkItem(String.valueOf(processInstanceId), workItemId);

        Assertions.assertThat(workItem).isNotNull();
        Assertions.assertThat(workItem.getName()).isNotEqualTo("");

        wfmcServiceEloImpl.abortProcessInstance(String.valueOf(processInstanceId));
    }

    @Test
    public void shouldCompleteWorkItem() throws RemoteException {
        String processDefinitionId = "5";
        String processName = "Test";
        String workItemId = "5";
        String attributeValue = "newValue";

        wfmcServiceEloImpl.connect(wmConnectInfo);
        int processInstanceId = wfmcServiceEloImpl.getIxConnection().ix().startWorkFlow(processDefinitionId, processName, "5");
        List<WMWorkItem> nextSteps = wfmcServiceEloImpl.getNextSteps(String.valueOf(processInstanceId), workItemId);
        for (WMWorkItem wmWorkItem : nextSteps) {
            wfmcServiceEloImpl.assignWorkItemAttribute(String.valueOf(processInstanceId), workItemId, WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(),wmWorkItem.getId());
        }

        wfmcServiceEloImpl.completeWorkItem(String.valueOf(processInstanceId), workItemId);
        wfmcServiceEloImpl.assignWorkItemAttribute(String.valueOf(processInstanceId), workItemId, WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(), attributeValue );


        Assertions.assertThat(
            wfmcServiceEloImpl.getWfmcServiceCache().getWorkItemAttribute(String.valueOf(processInstanceId), workItemId).getCount()).isEqualTo(
            1);
        Assertions.assertThat(
            wfmcServiceEloImpl.getWfmcServiceCache().getWorkItemAttribute(String.valueOf(processInstanceId), workItemId).tsNext().getValue()).isEqualTo(
            attributeValue);
    }
}
