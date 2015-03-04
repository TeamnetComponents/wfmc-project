package org.wfmc.elo;

import de.elo.ix.client.CheckoutUsersC;
import de.elo.ix.client.LockC;
import de.elo.ix.client.UserInfo;
import de.elo.utils.net.RemoteException;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.wfmc.elo.base.WMWorkItemIteratorImpl;
import org.wfmc.elo.model.ELOConstants;
import org.wfmc.elo.model.ELOWfMCProcessInstanceAttributes;
import org.wfmc.elo.model.EloWfmcObjKey;
import org.wfmc.elo.model.EloWfmcProcessInstance;
import org.wfmc.wapi.*;

import java.util.ResourceBundle;

/**
 * @author adrian.zamfirescu
 * @since 20/02/2015
 */
public class EloWfmcServiceTest {

    private EloWfmcService eloWfmcService;

    private WMConnectInfo wmConnectInfo;

    private static ResourceBundle configBundle = ResourceBundle.getBundle("config");

    @Before
    public void setUp(){
        eloWfmcService = new EloWfmcService();
        eloWfmcService.setProcessInstanceCache(new InMemoryProcessInstanceCache());
        wmConnectInfo = new WMConnectInfo(configBundle.getString("login.name"),
                                                configBundle.getString("login.password"),
                                                configBundle.getString("cnn.name"),
                                                configBundle.getString("ix.url"));
    }

    @After
    public void cleanUp(){
        eloWfmcService.disconnect();
    }

    @Test
    public void should_create_elo_connection(){

        // given - all is set up

        // when
        eloWfmcService.connect(wmConnectInfo);

        // then
        Assertions.assertThat(eloWfmcService.getEloConnection()).isNotNull();

    }

    @Test
    public void check_elo_disconnection(){
        WMConnectInfo wmConnectInfo = new WMConnectInfo(configBundle.getString("login.name"),
                configBundle.getString("login.password"),
                configBundle.getString("cnn.name"),
                configBundle.getString("ix.url"));
        eloWfmcService.connect(wmConnectInfo);

        eloWfmcService.disconnect();

        Assertions.assertThat(eloWfmcService.getEloConnection() == null);
    }

    @Test
    public void check_list_work_items_for_groups(){
        //given
        String name = "Group";
        int comparison = WMFilter.EQ;

        //when
        eloWfmcService.connect(wmConnectInfo);
        try {
            UserInfo[] groupsInfo = eloWfmcService.getEloConnection().ix().checkoutUsers(null, CheckoutUsersC.ALL_GROUPS, LockC.YES);
            for (UserInfo userInfo : groupsInfo){
                String groupName = userInfo.getName();
                WMFilter wmFilter = new WMFilter(name, comparison , groupName);
                WMWorkItemIteratorImpl wmWorkItemIterator = (WMWorkItemIteratorImpl) eloWfmcService.listWorkItems(wmFilter, false);
                while (wmWorkItemIterator.hasNext()){
                    Assertions.assertThat((wmWorkItemIterator.tsNext().getParticipant().getName())).isEqualTo(groupName);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void should_start_elo_workflow_process(){

        // given
        String processInstanceId = "someProcessInstanceId";
        EloWfmcProcessInstance wmProcessInstance = new EloWfmcProcessInstance();
        wmProcessInstance.setProcessDefinitionId("4"); // setting the template ID
        wmProcessInstance.setName("Test Workflow Name");
        wmProcessInstance.setEloWfMCProcessInstanceAttributes(new ELOWfMCProcessInstanceAttributes("104")); //idSORD fisier TEST WF

        eloWfmcService = Mockito.spy(eloWfmcService);
        Mockito.when(eloWfmcService.getProcessInstance(Mockito.<String>any())).thenReturn(wmProcessInstance);
        Mockito.doNothing().when(eloWfmcService).abortProcessInstance(processInstanceId);

        eloWfmcService.connect(wmConnectInfo);

        // when
        String workspaceId = eloWfmcService.startProcess(processInstanceId);

        // then
        Assertions.assertThat(workspaceId).isNotNull();

    }

    @Test
    public void should_create_process_instance(){

        // given
        String processDefinitionId = "5";
        String processInstanceName = "TestProcInstName";

        // when
        String processInstanceId = eloWfmcService.createProcessInstance(processDefinitionId, processInstanceName);

        // then
        Assertions.assertThat(processInstanceId).isEqualTo("5TestProcInstName");
        Assertions.assertThat(eloWfmcService.getProcessInstanceCache().retrieveEloWfmcProcessInstance(processInstanceId)).isNotNull();

    }

    @Test
    public void should_assign_process_instance_attribute_sord_id(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = ELOConstants.ELO_SORD_ID;
        String attributeValue = "5";
        eloWfmcService.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        eloWfmcService = Mockito.spy(eloWfmcService);
        Mockito.when(eloWfmcService.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);

        // when
        eloWfmcService.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

        // then
        Assertions.assertThat(eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes()).isNotNull();
        Assertions.assertThat(((ELOWfMCProcessInstanceAttributes)eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes()).getSordId()).isEqualTo(attributeValue);
        // TODO - complete test assertions after adding metadata
    }

    @Test
    public void should_assign_process_instance_attribute_mask_id(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = ELOConstants.ELO_MASK_ID;
        String attributeValue = "Dosar";
        eloWfmcService.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        eloWfmcService = Mockito.spy(eloWfmcService);
        Mockito.when(eloWfmcService.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);

        // when
        eloWfmcService.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

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
        eloWfmcService.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        eloWfmcService = Mockito.spy(eloWfmcService);
        Mockito.when(eloWfmcService.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);
        eloWfmcProcessInstance.setProcessDefinitionId("Template1");

        // when
        eloWfmcService.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

        // then
        Assertions.assertThat(eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes()).isNotNull();
//        Assertions.assertThat(((ELOWfMCProcessInstanceAttributes)eloWfmcProcessInstance.getEloWfMCProcessInstanceAttributes()).getMaskId()).isEqualTo();
        // TODO - complete test assertions after adding metadata
    }

    @Test(expected = WMAttributeAssignmentException.class)
    public void should_assign_process_instance_attribute_sord_id_exception(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = ELOConstants.ELO_SORD_ID;
        String attributeValue = "-1";
        eloWfmcService.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        eloWfmcService = Mockito.spy(eloWfmcService);
        Mockito.when(eloWfmcService.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);

        // when
        eloWfmcService.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

        // then
    }

    @Test(expected = WMWorkflowException.class)
    public void should_assign_process_instance_attribute_mask_id_exception(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = ELOConstants.ELO_MASK_ID;
        String attributeValue = "-1";
        eloWfmcService.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        eloWfmcService = Mockito.spy(eloWfmcService);
        Mockito.when(eloWfmcService.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);

        // when
        eloWfmcService.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

    }

    @Test(expected = WMWorkflowException.class)
    public void should_assign_process_instance_attribute_mask_from_comment_exception(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = "Tip drum";
        String attributeValue = "Strada";
        eloWfmcService.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        eloWfmcService = Mockito.spy(eloWfmcService);
        Mockito.when(eloWfmcService.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);
        eloWfmcProcessInstance.setProcessDefinitionId("Test");

        // when
        eloWfmcService.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);
    }

    @Test(expected = WMWorkflowException.class)
    public void should_assign_process_instance_attribute_mask_from_comment_workflow_not_exist_exception(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = "Tip drum";
        String attributeValue = "Strada";
        eloWfmcService.connect(wmConnectInfo);

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        eloWfmcService = Mockito.spy(eloWfmcService);
        Mockito.when(eloWfmcService.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);
        eloWfmcProcessInstance.setProcessDefinitionId("-1");

        // when
        eloWfmcService.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);
    }

    @Test
    public void should_assign_process_instance_attribute(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = "Tip drum";
//        Object attributeValue = "Strada";
        Object attributeValue = new String[] {"strada","autostrada"};
        eloWfmcService.connect(wmConnectInfo);


        eloWfmcService = Mockito.spy(eloWfmcService);
        EloWfmcProcessInstance eloWfmcProcessInstance = Mockito.mock(EloWfmcProcessInstance.class);
        Mockito.when(eloWfmcService.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);
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
        eloWfmcService.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

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

        EloWfmcProcessInstance testEloWfmcProcessInstance = new EloWfmcProcessInstance();
        eloWfmcService.getProcessInstanceCache().createEloWfmcProcessInstance(procInstId, testEloWfmcProcessInstance);

        // when
        EloWfmcProcessInstance eloWfmcProcessInstance = (EloWfmcProcessInstance)eloWfmcService.getProcessInstance(procInstId);

        // then
        Assertions.assertThat(eloWfmcProcessInstance).isEqualTo(testEloWfmcProcessInstance);

    }

    @Test
    public void should_remove_process_instance_from_cache(){

        // given
        String procInstId = "TestProcInstId";

        EloWfmcProcessInstance testEloWfmcProcessInstance = new EloWfmcProcessInstance();
        eloWfmcService.getProcessInstanceCache().createEloWfmcProcessInstance(procInstId, testEloWfmcProcessInstance);

        // when
        eloWfmcService.abortProcessInstance(procInstId);

        // then
         Assertions.assertThat(eloWfmcService.getProcessInstanceCache().retrieveEloWfmcProcessInstance(procInstId)).isNull();

    }

    @Test
    public void integration_workflow_test(){

        // given
        String workflowTemplateId = "4";
        String workflowName = "Integration Test Workflow Name";
        String sordId = "104";

        // when
        eloWfmcService.connect(wmConnectInfo);
        String processInstanceId = eloWfmcService.createProcessInstance(workflowTemplateId, workflowName);
        eloWfmcService.assignProcessInstanceAttribute(processInstanceId, "Sord", sordId);
        eloWfmcService.startProcess(processInstanceId);
        eloWfmcService.disconnect();

        // then
        assert true; // should terminate peacefully

    }


}
