package org.wfmc.elo;

import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.wfmc.elo.base.WMProcessInstanceImpl_Elo;
import org.wfmc.elo.model.ELOConstants;
import org.wfmc.elo.model.ELOWfMCProcessInstanceAttributes;
import org.wfmc.elo.model.EloWfmcObjKey;
import org.wfmc.elo.model.EloWfmcProcessInstance;
import org.wfmc.impl.base.WMProcessInstanceImpl;
import org.wfmc.service.WfmcServiceCache;
import org.wfmc.service.WfmcServiceCacheImpl_Memory;
import org.wfmc.wapi.*;

import java.util.ResourceBundle;
import java.util.UUID;

/**
 * @author adrian.zamfirescu
 * @since 20/02/2015
 */
public class WfmcServiceImpl_EloTest {

    private WfmcServiceImpl_Elo wfmcServiceImpl_Elo;

    private WMConnectInfo wmConnectInfo;

    private static ResourceBundle configBundle = ResourceBundle.getBundle("config");

    @Before
    public void setUp(){
        wfmcServiceImpl_Elo = new WfmcServiceImpl_Elo();
        WfmcServiceCache wfmcServiceCache = new WfmcServiceCacheImpl_Memory();
        wfmcServiceCache.setWfmcService(wfmcServiceImpl_Elo);
        wfmcServiceImpl_Elo.setWfmcServiceCache(wfmcServiceCache);
        wmConnectInfo = new WMConnectInfo(configBundle.getString("login.name"),
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
        WMConnectInfo wmConnectInfo = new WMConnectInfo(configBundle.getString("login.name"),
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

    @Test
    public void should_start_elo_workflow_process(){

        // given
        String processInstanceId = "someProcessInstanceId";
        EloWfmcProcessInstance wmProcessInstance = new EloWfmcProcessInstance();
        wmProcessInstance.setProcessDefinitionId("4"); // setting the template ID
        wmProcessInstance.setName("Test Workflow Name");
        wmProcessInstance.setEloWfMCProcessInstanceAttributes(new ELOWfMCProcessInstanceAttributes("104")); //idSORD fisier TEST WF

        wfmcServiceImpl_Elo = Mockito.spy(wfmcServiceImpl_Elo);
        Mockito.when(wfmcServiceImpl_Elo.getProcessInstance(Mockito.<String>any())).thenReturn(wmProcessInstance);
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
        Assertions.assertThat(processInstanceId).isEqualTo("5TestProcInstName");
        Assertions.assertThat(wfmcServiceImpl_Elo.getWfmcServiceCache().getProcessInstance(processInstanceId)).isNotNull();

    }

    @Test
    public void should_assign_process_instance_attribute_sord_id(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = ELOConstants.SORD_ID;
        String attributeValue = "5";
        wfmcServiceImpl_Elo.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        wfmcServiceImpl_Elo = Mockito.spy(wfmcServiceImpl_Elo);
        Mockito.when(wfmcServiceImpl_Elo.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);

        // when
        wfmcServiceImpl_Elo.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

        // then
        Assertions.assertThat(eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes()).isNotNull();
        Assertions.assertThat(((ELOWfMCProcessInstanceAttributes)eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes()).getSordId()).isEqualTo(attributeValue);
        // TODO - complete test assertions after adding metadata
    }

    @Test
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

    @Test
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

    @Test(expected = WMAttributeAssignmentException.class)
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

    @Test(expected = WMWorkflowException.class)
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

    @Test(expected = WMWorkflowException.class)
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

    @Test(expected = WMWorkflowException.class)
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

    @Test
    public void should_assign_process_instance_attribute(){

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

    @Test
    public void should_retrieve_process_instance_from_cache(){

        // given
        String procInstId = "TestProcInstId";

        WMProcessInstance wmProcessInstance = new WMProcessInstanceImpl(procInstId, String.valueOf(UUID.randomUUID()), procInstId);
        wfmcServiceImpl_Elo.getWfmcServiceCache().addProcessInstance(wmProcessInstance);

        // when
        WMProcessInstanceImpl eloWfmcProcessInstance = (WMProcessInstanceImpl) wfmcServiceImpl_Elo.getProcessInstance(procInstId);

        // then
        Assertions.assertThat(eloWfmcProcessInstance).isEqualTo(wmProcessInstance);

    }

    @Test
    public void should_remove_process_instance_from_cache(){

        // given
        String procInstId = "TestProcInstId";

        WMProcessInstance wmProcessInstance = new WMProcessInstanceImpl(procInstId, String.valueOf(UUID.randomUUID()), procInstId);
        wfmcServiceImpl_Elo.getWfmcServiceCache().addProcessInstance(wmProcessInstance);


        // when
        wfmcServiceImpl_Elo.abortProcessInstance(procInstId);

        // then
         Assertions.assertThat(wfmcServiceImpl_Elo.getProcessInstance(procInstId)).isNull();

    }

    @Test
    public void integration_workflow_test(){

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


}
