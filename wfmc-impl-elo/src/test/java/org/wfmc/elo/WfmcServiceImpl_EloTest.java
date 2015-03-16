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
public class WfmcServiceImpl_EloTest {

    private WfmcServiceImpl_Elo wfmcServiceImpl_Elo;

    private WMConnectInfo wmConnectInfo;

    private static ResourceBundle configBundle = ResourceBundle.getBundle("config");

    @Before
    public void setUp() throws IOException {

        // create main service
        wfmcServiceImpl_Elo = new WfmcServiceImpl_Elo();

        // create some test cache properties
        Properties cacheTestProperties = new Properties();
        cacheTestProperties.setProperty(ServiceFactory.INSTANCE_NAME, "testCache");

        // create a test service cache
        WfmcServiceCache wfmcServiceCache = new WfmcServiceCacheImpl_Memory();
        wfmcServiceCache.setWfmcService(wfmcServiceImpl_Elo);
        wfmcServiceImpl_Elo.setWfmcServiceCache(wfmcServiceCache);
        wfmcServiceCache.__initialize(cacheTestProperties);

        // set the ELO connection attributes
        wmConnectInfo = new WMConnectInfo("Andra@"+ configBundle.getString("login.name"),
                configBundle.getString("login.password"),
                configBundle.getString("cnn.name"),
                configBundle.getString("ix.url"));
    }

    @After
    public void cleanUp(){
        wfmcServiceImpl_Elo.disconnect();
    }

    @Test
    public void should_create_elo_connection(){

        // given - all is set up

        // when
        wfmcServiceImpl_Elo.connect(wmConnectInfo);

        // then
        Assertions.assertThat(wfmcServiceImpl_Elo.getIxConnection()).isNotNull();

    }

    @Test
    public void check_elo_disconnection(){
        WMConnectInfo wmConnectInfo = new WMConnectInfo("Andra@"+configBundle.getString("login.name"),
                configBundle.getString("login.password"),
                configBundle.getString("cnn.name"),
                configBundle.getString("ix.url"));
        wfmcServiceImpl_Elo.connect(wmConnectInfo);

        wfmcServiceImpl_Elo.disconnect();

        Assertions.assertThat(wfmcServiceImpl_Elo.getIxConnection() == null);
    }

    @Test
    public void check_list_work_items_for_groups(){
//        //given
//        String name = "Group";
//        int comparison = WMFilter.EQ;
//
//        //when
//        wfmcServiceImpl_Elo.connect(wmConnectInfo);
//        try {
//            UserInfo[] groupsInfo = wfmcServiceImpl_Elo.getEloConnection().ix().checkoutUsers(null, CheckoutUsersC.ALL_GROUPS, LockC.YES);
//            for (UserInfo userInfo : groupsInfo){
//                String groupName = userInfo.getName();
//                WMFilter wmFilter = new WMFilter(name, comparison , groupName);
//                WMWorkItemIteratorImpl wmWorkItemIterator = (WMWorkItemIteratorImpl) wfmcServiceImpl_Elo.listWorkItems(wmFilter, false);
//                while (wmWorkItemIterator.hasNext()){
//                    Assertions.assertThat((wmWorkItemIterator.tsNext().getParticipant().getName())).isEqualTo(groupName);
//                }
//            }
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
        //TODO Daniel: de refacut
    }

    @Ignore
    public void should_start_elo_workflow_process_deprecated(){

        // given
        String processInstanceId = "someProcessInstanceId";
        EloWfmcProcessInstance wmProcessInstance = new EloWfmcProcessInstance();
        wmProcessInstance.setProcessDefinitionId("4"); // setting the template ID
        wmProcessInstance.setName("Test Workflow Name");
        wmProcessInstance.setEloWfMCProcessInstanceAttributes(new ELOWfMCProcessInstanceAttributes("104")); //idSORD fisier TEST WF (se presupune ca acesta va exista permanent in arhiva ELO de test)

        wfmcServiceImpl_Elo = Mockito.spy(wfmcServiceImpl_Elo);
        Mockito.doReturn(wmProcessInstance).when(wfmcServiceImpl_Elo).getProcessInstance(Mockito.<String>any());
        Mockito.doNothing().when(wfmcServiceImpl_Elo).abortProcessInstance(processInstanceId);

        wfmcServiceImpl_Elo.connect(wmConnectInfo);

        // when
        String workspaceId = wfmcServiceImpl_Elo.startProcess(processInstanceId);

        // then
        Assertions.assertThat(workspaceId).isNotNull();

    }

    @Test
    public void should_create_process_instance(){

        // given
        String processDefinitionId = "5";
        String processInstanceName = "TestProcInstName";

        // when
        String processInstanceId = wfmcServiceImpl_Elo.createProcessInstance(processDefinitionId, processInstanceName);

        // then
        Assertions.assertThat(processInstanceId).isNotNull();
        Assertions.assertThat(wfmcServiceImpl_Elo.getWfmcServiceCache().getProcessInstance(processInstanceId)).isNotNull();

    }


    @Test
    public void should_getWorkFlowProcess() throws RemoteException {
        //checking for the right transitions

        // given
        String wfId = "1";
        String nodeId = "2";

        EloUtilsService eloUtilsService = Mockito.mock(EloUtilsService.class);
        wfmcServiceImpl_Elo.setEloUtilsService(eloUtilsService);

        WFNodeAssoc[] wfNodeAssoc = new WFNodeAssoc[6];
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
        WorkflowProcess workFlowProcess = wfmcServiceImpl_Elo.getWorkFlowProcess(wfId);



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
    public void should_assign_process_instance_attribute_mask_id(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = ELOConstants.MASK_ID;
        String attributeValue = "Dosar";
        wfmcServiceImpl_Elo.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        wfmcServiceImpl_Elo = Mockito.spy(wfmcServiceImpl_Elo);
        Mockito.when(wfmcServiceImpl_Elo.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);

        // when
        wfmcServiceImpl_Elo.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

        // then
        Assertions.assertThat(eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes()).isNotNull();
        Assertions.assertThat(((ELOWfMCProcessInstanceAttributes)eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes()).getMaskId()).isEqualTo("Dosar");
        // TODO - complete test assertions after adding metadata
    }

    @Ignore
    public void should_assign_process_instance_attribute_mask_from_comment(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = "Tip drum";
        String attributeValue = "Strada";
        wfmcServiceImpl_Elo.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        wfmcServiceImpl_Elo = Mockito.spy(wfmcServiceImpl_Elo);
        Mockito.when(wfmcServiceImpl_Elo.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);
        eloWfmcProcessInstance.setProcessDefinitionId("Template1");

        // when
        wfmcServiceImpl_Elo.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

        // then
        Assertions.assertThat(eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes()).isNotNull();
//        Assertions.assertThat(((ELOWfMCProcessInstanceAttributes)eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes()).getMaskId()).isEqualTo();
        // TODO - complete test assertions after adding metadata
    }

    @Ignore//(expected = WMAttributeAssignmentException.class)
    public void should_assign_process_instance_attribute_sord_id_exception(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = ELOConstants.SORD_ID;
        String attributeValue = "-1";
        wfmcServiceImpl_Elo.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        wfmcServiceImpl_Elo = Mockito.spy(wfmcServiceImpl_Elo);
        Mockito.when(wfmcServiceImpl_Elo.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);

        // when
        wfmcServiceImpl_Elo.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

        // then
    }

    @Ignore//(expected = WMWorkflowException.class)
    public void should_assign_process_instance_attribute_mask_id_exception(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = ELOConstants.MASK_ID;
        String attributeValue = "-1";
        wfmcServiceImpl_Elo.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        wfmcServiceImpl_Elo = Mockito.spy(wfmcServiceImpl_Elo);
        Mockito.when(wfmcServiceImpl_Elo.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);

        // when
        wfmcServiceImpl_Elo.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

    }

    @Ignore//(expected = WMWorkflowException.class)
    public void should_assign_process_instance_attribute_mask_from_comment_exception(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = "Tip drum";
        String attributeValue = "Strada";
        wfmcServiceImpl_Elo.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        wfmcServiceImpl_Elo = Mockito.spy(wfmcServiceImpl_Elo);
        Mockito.when(wfmcServiceImpl_Elo.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);
        eloWfmcProcessInstance.setProcessDefinitionId("Test");

        // when
        wfmcServiceImpl_Elo.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);
    }

    @Ignore//(expected = WMWorkflowException.class)
    public void should_assign_process_instance_attribute_mask_from_comment_workflow_not_exist_exception(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = "Tip drum";
        String attributeValue = "Strada";
        wfmcServiceImpl_Elo.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        wfmcServiceImpl_Elo = Mockito.spy(wfmcServiceImpl_Elo);
        Mockito.when(wfmcServiceImpl_Elo.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);
        eloWfmcProcessInstance.setProcessDefinitionId("-1");

        // when
        wfmcServiceImpl_Elo.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);
    }

    @Ignore
    public void should_assign_process_instance_attribute_deprecated(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = "Tip drum";
//        Object attributeValue = "Strada";
        Object attributeValue = new String[] {"strada","autostrada"};
        wfmcServiceImpl_Elo.connect(wmConnectInfo);


        wfmcServiceImpl_Elo = Mockito.spy(wfmcServiceImpl_Elo);
        EloWfmcProcessInstance eloWfmcProcessInstance = Mockito.mock(EloWfmcProcessInstance.class);
        Mockito.when(wfmcServiceImpl_Elo.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);
        ELOWfMCProcessInstanceAttributes eloWfMCProcessInstanceAttributes = Mockito.mock(ELOWfMCProcessInstanceAttributes.class);
        Mockito.when(eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes()).thenReturn(eloWfMCProcessInstanceAttributes);
        EloWfmcObjKey[] objKeys = new EloWfmcObjKey[5];
        for (int i = 0; i < 5 ; i++) {
            objKeys[i] = new EloWfmcObjKey();
            if (i == 2) {
                objKeys[i].setName(attributeName);
            }
        }
        Mockito.when(eloWfMCProcessInstanceAttributes.getObjKeys()).thenReturn(objKeys);

        // when
        wfmcServiceImpl_Elo.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

        // then
        if (attributeValue instanceof String[]) {
            Assertions.assertThat(objKeys[2].getValue()).isEqualTo((String[])attributeValue);
        } else {
            Assertions.assertThat(objKeys[2].getValue()).isEqualTo(new String[]{attributeValue.toString()});
        }
    }

    @Test(expected = WMWorkflowException.class)
    public void should_remove_process_instance_from_cache(){

        // given
        String procInstId = "TestProcInstId";

        WMProcessInstance wmProcessInstance = new WMProcessInstanceImpl(procInstId, String.valueOf(UUID.randomUUID()), procInstId);
        wfmcServiceImpl_Elo.getWfmcServiceCache().addProcessInstance(wmProcessInstance);

        // when
        wfmcServiceImpl_Elo.terminateProcessInstance(procInstId);

        // then
         wfmcServiceImpl_Elo.getProcessInstance(procInstId);

    }

    @Test
    public void should_start_elo_workflow_process() throws RemoteException {

        String procInstId = "testProcInstId";

        // given
        wfmcServiceImpl_Elo = Mockito.spy(wfmcServiceImpl_Elo);

        EloUtilsService eloUtilsService = Mockito.mock(EloUtilsService.class);
        WfmcUtilsService wfmcUtilsService = Mockito.mock(WfmcUtilsService.class);
        WfmcServiceCache wfmcServiceCache = Mockito.mock(WfmcServiceCache.class);
        wfmcServiceImpl_Elo.setWfmcServiceCache(wfmcServiceCache);
        wfmcServiceImpl_Elo.setEloUtilsService(eloUtilsService);
        wfmcServiceImpl_Elo.setWfmcUtilsService(wfmcUtilsService);

        WMProcessInstance wmProcessInstance = new WMProcessInstanceImpl_Elo();
        Mockito.when(wfmcServiceCache.getProcessInstance(procInstId)).thenReturn(wmProcessInstance);

        Map<String, WMAttribute> wmProcessInstanceWMAttributeMap = new HashMap<>();
        Map<String, Object> wmProcessInstanceAttributeMap = new HashMap<>();

        Mockito.when(wfmcUtilsService.toWMAttributeMap(Mockito.<WMAttributeIterator>any())).thenReturn(wmProcessInstanceWMAttributeMap);
        Mockito.when(wfmcUtilsService.toMap(wmProcessInstanceWMAttributeMap)).thenReturn(wmProcessInstanceAttributeMap);

        WFDiagram wfDiagram = Mockito.mock(WFDiagram.class);
        Mockito.when(eloUtilsService.getWorkFlow(Mockito.<IXConnection>any(),
                                                 Mockito.<String>any(),
                                                 Mockito.<WFTypeZ>any(),
                                                 Mockito.<WFDiagramZ>any(),
                                                 Mockito.<LockZ>any())).thenReturn(wfDiagram);

        WFNode[] wfNodes = new WFNode[25];
        wfNodes[0] = new WFNode();
        String attributesString = "a=1;b=2;c=3";
        wfNodes[0].setComment(attributesString);
        Mockito.when(wfDiagram.getNodes()).thenReturn(wfNodes);

        // we suppose we have a SORD ID
        String sordId = "5";
        wmProcessInstanceWMAttributeMap.put(ELOConstants.SORD_ID, new WMAttributeImpl("SORD", 1, sordId));

        Sord testSord = new Sord();
        Mockito.when(eloUtilsService.getSord(Mockito.<IXConnection>any(), Mockito.eq(sordId), Mockito.<SordZ>any(), Mockito.<LockZ>any())).thenReturn(testSord);

        Mockito.doNothing().when(eloUtilsService).updateSordAttributes(testSord, wmProcessInstanceAttributeMap);
        Mockito.when(eloUtilsService.saveSord(Mockito.<IXConnection>any(), Mockito.eq(testSord), Mockito.<SordZ>any(), Mockito.<LockZ>any())).thenReturn(Integer.valueOf(sordId));

        String processInstanceId = "123";
        Mockito.when(eloUtilsService.startWorkFlow(Mockito.<IXConnection>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.eq(sordId))).thenReturn(processInstanceId);

        String newProcessInstanceId = "234";
        Mockito.doReturn(newProcessInstanceId).when(wfmcServiceImpl_Elo).startProcess(processInstanceId);

        // when
        String returnedProcInstId = wfmcServiceImpl_Elo.startProcess(procInstId);

        // then
        Mockito.verify(eloUtilsService).getSord(Mockito.<IXConnection>any(), Mockito.eq(sordId), Mockito.<SordZ>any(), Mockito.<LockZ>any());
        Mockito.verify(eloUtilsService).updateSordAttributes(testSord, wmProcessInstanceAttributeMap);
        Mockito.verify(eloUtilsService).startWorkFlow(Mockito.<IXConnection>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.eq(sordId));
        Assertions.assertThat(returnedProcInstId).isEqualTo(processInstanceId);

    }

    @Ignore
    public void integration_workflow_test_deprecated(){

        // given
        String workflowTemplateId = "4";
        String workflowName = "Integration Test Workflow Name";
        String sordId = "104";

        // when
        wfmcServiceImpl_Elo.connect(wmConnectInfo);
        String processInstanceId = wfmcServiceImpl_Elo.createProcessInstance(workflowTemplateId, workflowName);
        wfmcServiceImpl_Elo.assignProcessInstanceAttribute(processInstanceId, ELOConstants.SORD_ID, sordId);
        wfmcServiceImpl_Elo.startProcess(processInstanceId);
        wfmcServiceImpl_Elo.disconnect();

        // then
        assert true; // should terminate peacefully

    }

    @Test
    public void should_reassign_work_item () throws RemoteException {
        String sourceUser = "Andra";
        String targetUser = "Administrator";
        String workItemId = "7";
        wfmcServiceImpl_Elo.connect(wmConnectInfo);

        int test = wfmcServiceImpl_Elo.getIxConnection().ix().startWorkFlow("1", "test", "2");
        WFDiagram wfDiagram = wfmcServiceImpl_Elo.getIxConnection().ix().checkoutWorkFlow(String.valueOf(test), WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO);
        WFNode[] nodes = wfDiagram.getNodes();
        if ((nodes[Integer.parseInt(workItemId)].getName() != null) && (Integer.parseInt(workItemId) != 0)){
            nodes[Integer.valueOf(workItemId)].setUserName(sourceUser);
            wfDiagram.setNodes(nodes);
            wfmcServiceImpl_Elo.getIxConnection().ix().checkinWorkFlow(wfDiagram, WFDiagramC.mbAll, LockC.NO);

            wfmcServiceImpl_Elo.reassignWorkItem(sourceUser, targetUser, String.valueOf(test), workItemId);

            WFDiagram wfDiagram2 = wfmcServiceImpl_Elo.getIxConnection().ix().checkoutWorkFlow(String.valueOf(test), WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO);
            Assertions.assertThat(wfDiagram2.getNodes()[Integer.parseInt(workItemId)].getUserName()).isEqualTo(targetUser);
            wfmcServiceImpl_Elo.getIxConnection().ix().deleteWorkFlow(String.valueOf(test), WFTypeC.ACTIVE, LockC.NO);
        } else {

        }

    }

    @Test
    public void test_error_resource_buldle(){
        Assert.assertEquals(wfmcServiceImpl_Elo.errorMessagesResourceBundle.getString(WMErrorElo.ELO_ERROR_FILTER_NOT_SUPPORTED),
                "WMFilter type not supported!");
    }

    @Test(expected = WMUnsupportedOperationException.class)
    public void test_listWorkItems_WMFilter_not_supported(){
        WMFilter wmFilter = new WMFilter("testing sql filter not supported");
        wfmcServiceImpl_Elo.listWorkItems(wmFilter, false);
    }

    @Test
    public void should_get_next_steps() throws RemoteException {

        // given
        String wfId = "1";
        String nodeId = "20";

        EloUtilsService eloUtilsService = Mockito.mock(EloUtilsService.class);
        wfmcServiceImpl_Elo.setEloUtilsService(eloUtilsService);

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
        List<WMWorkItem> workItems = wfmcServiceImpl_Elo.getNextSteps(wfId, nodeId);

        // then
        Mockito.verify(eloUtilsService).getWorkFlow(Mockito.<IXConnection>any(), Mockito.eq(wfId), Mockito.eq(WFTypeC.ACTIVE), Mockito.eq(WFDiagramC.mbAll), Mockito.eq(LockC.NO));
        Assertions.assertThat(workItems).hasSize(2);
    }

    @Test
    public void should_forward_task() {

    }

    @Ignore
    public void test_getProcessInstance_with_finished_workflow(){
        String flowId = "215";

        wfmcServiceImpl_Elo.connect(wmConnectInfo);
        WMProcessInstance wmProcessInstance = wfmcServiceImpl_Elo.getProcessInstance(flowId);
        wfmcServiceImpl_Elo.disconnect();

        Assertions.assertThat(wmProcessInstance).isNotNull();
    }

    @Test
    public void test_assignProcessInstanceAttribute_for_started_process_with_existing_attr_in_sord() throws RemoteException {
        // given
        String workflowTemplateId = "4";
        String workflowName = "Integration Test Workflow Name";
        String sordId = "104";
        String attributeName = "TIPDRUM";
//        Object attributeValue = "Strada";
        Object attributeValue = new String[] {"strada","autostrada"};
        // when
        wfmcServiceImpl_Elo.connect(wmConnectInfo);
        String processInstanceId = wfmcServiceImpl_Elo.createProcessInstance(workflowTemplateId, workflowName);
        wfmcServiceImpl_Elo.assignProcessInstanceAttribute(processInstanceId, ELOConstants.SORD_ID, sordId);
        String eloProcessId = wfmcServiceImpl_Elo.startProcess(processInstanceId);
        wfmcServiceImpl_Elo.assignProcessInstanceAttribute(eloProcessId, attributeName, attributeValue);
        //verify
        EloUtilsService eloUtilsService = new EloUtilsService();
        Sord sord =  eloUtilsService.getSord(wfmcServiceImpl_Elo.getIxConnection(),sordId, SordC.mbAll, LockC.NO);
        ObjKey[] objKeys = sord.getObjKeys();
        for (ObjKey objKey : Arrays.asList(objKeys)){
            if (objKey.getName().equals(attributeName)){
                Assertions.assertThat(objKey.getData()[0]).isEqualTo("strada") ;
            }
        }
        wfmcServiceImpl_Elo.disconnect();
    }

    @Test(expected = WMInvalidAttributeException.class)
    public void test_assignProcessInstanceAttribute_for_started_process_without_existing_attr_in_sord() throws RemoteException {
        // given
        String workflowTemplateId = "4";
        String workflowName = "Integration Test Workflow Name";
        String sordId = "104";
        String attributeName = "TIP DRUM";
//        Object attributeValue = "Strada";
        Object attributeValue = new String[] {"strada","autostrada"};
        // when
        wfmcServiceImpl_Elo.connect(wmConnectInfo);
        String processInstanceId = wfmcServiceImpl_Elo.createProcessInstance(workflowTemplateId, workflowName);
        wfmcServiceImpl_Elo.assignProcessInstanceAttribute(processInstanceId, ELOConstants.SORD_ID, sordId);
        String eloProcessId = wfmcServiceImpl_Elo.startProcess(processInstanceId);
        wfmcServiceImpl_Elo.assignProcessInstanceAttribute(eloProcessId, attributeName, attributeValue);
        wfmcServiceImpl_Elo.disconnect();
    }

    @Test
    public void should_assignWorkItemAttribute() {
        String processInstanceId = "5";
        String workItemId = "5";
        String attrName = WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString();
        Object attrValue = "5";
        Integer size = 10;

        WMWorkItem wmWorkItem = new WMWorkItemImpl(processInstanceId, workItemId);
        for (int i = 0; i < size; i++) {
            wfmcServiceImpl_Elo.assignWorkItemAttribute(processInstanceId, workItemId, attrName, Integer.parseInt((String)attrValue) +  i );
        }

        Assertions.assertThat(wfmcServiceImpl_Elo.getWfmcServiceCache().getWorkItemAttribute(processInstanceId, workItemId)).isNotNull();
        Assertions.assertThat(wfmcServiceImpl_Elo.getWfmcServiceCache().getWorkItemAttribute(processInstanceId, workItemId).tsNext().getValue()).isEqualTo(Integer.parseInt((String)attrValue));
        Assertions.assertThat(wfmcServiceImpl_Elo.getWfmcServiceCache().getWorkItemAttribute(processInstanceId, workItemId).getCount()).isEqualTo(size);

    }

    @Test
    public void should_listProcessInstances() throws RemoteException {
        String processDefinitionId = "5";
        String processName = "Test";

        wfmcServiceImpl_Elo.connect(wmConnectInfo);
        int processInstanceId = wfmcServiceImpl_Elo.getIxConnection().ix().startWorkFlow(processDefinitionId, processName, "6");
        WMFilter wmFilter = WMFilterBuilder.createWMFilterProcessInstance().isActive(true).addProcessDefinitionId(String.valueOf(processDefinitionId));

        WMProcessInstanceIterator wmProcessInstanceIterator = wfmcServiceImpl_Elo.listProcessInstances(wmFilter, true);
        List<WMProcessInstance> wmProcessInstances = new ArrayList<>();
        String newProcessDefinitionId = null;
        while (wmProcessInstanceIterator.hasNext()) {
            WMProcessInstance wmProcessInstance = wmProcessInstanceIterator.tsNext();
//            newProcessDefinitionId = wmProcessInstance.getProcessDefinitionId();
            wmProcessInstances.add(wmProcessInstance);
        }

        Assertions.assertThat(wmProcessInstances).isNotNull();
//        Assertions.assertThat(newProcessDefinitionId).isEqualTo(processDefinitionId);

        wfmcServiceImpl_Elo.abortProcessInstance(String.valueOf(processInstanceId));
    }

    @Test
    public void should_listWorkItem() throws RemoteException {
        String processDefinitionId = "5";
        String processName = "Test";

        wfmcServiceImpl_Elo.connect(wmConnectInfo);
        int processInstanceId = wfmcServiceImpl_Elo.getIxConnection().ix().startWorkFlow(processDefinitionId, processName, "6");
        WMFilter wmFilter = WMFilterBuilder.createWMFilterWorkItem().addProcessInstanceId(String.valueOf(processInstanceId));

        WMWorkItemIterator wmWorkItemIterator = wfmcServiceImpl_Elo.listWorkItems(wmFilter, true);
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

        wfmcServiceImpl_Elo.abortProcessInstance(String.valueOf(processInstanceId));
    }

    @Test
    public void should_getWorkItem() throws RemoteException {
        String processDefinitionId = "5";
        String processName = "Test";
        String workItemId = "0";

        wfmcServiceImpl_Elo.connect(wmConnectInfo);
        int processInstanceId = wfmcServiceImpl_Elo.getIxConnection().ix().startWorkFlow(processDefinitionId, processName, "5");

        WMWorkItem workItem = wfmcServiceImpl_Elo.getWorkItem(String.valueOf(processInstanceId), workItemId);

        Assertions.assertThat(workItem).isNotNull();
        Assertions.assertThat(workItem.getName()).isNotEqualTo("");

        wfmcServiceImpl_Elo.abortProcessInstance(String.valueOf(processInstanceId));
    }

    @Test
    public void should_completeWorkItem() throws RemoteException {
        String processDefinitionId = "5";
        String processName = "Test";
        String workItemId = "5";
        String attributeValue = "newValue";

        wfmcServiceImpl_Elo.connect(wmConnectInfo);
        int processInstanceId = wfmcServiceImpl_Elo.getIxConnection().ix().startWorkFlow(processDefinitionId, processName, "5");
        List<WMWorkItem> nextSteps = wfmcServiceImpl_Elo.getNextSteps(String.valueOf(processInstanceId), workItemId);
        for (WMWorkItem wmWorkItem : nextSteps) {
            wfmcServiceImpl_Elo.assignWorkItemAttribute(String.valueOf(processInstanceId), workItemId, WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(),wmWorkItem.getId());
        }

        wfmcServiceImpl_Elo.completeWorkItem(String.valueOf(processInstanceId), workItemId);
        wfmcServiceImpl_Elo.assignWorkItemAttribute(String.valueOf(processInstanceId), workItemId, WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(), attributeValue );


        Assertions.assertThat(wfmcServiceImpl_Elo.getWfmcServiceCache().getWorkItemAttribute(String.valueOf(processInstanceId), workItemId).getCount()).isEqualTo(1);
        Assertions.assertThat(wfmcServiceImpl_Elo.getWfmcServiceCache().getWorkItemAttribute(String.valueOf(processInstanceId), workItemId).tsNext().getValue()).isEqualTo(attributeValue);
    }
}
