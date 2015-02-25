package org.wfmc.elo;

import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.wfmc.elo.model.EloWfmcProcessInstance;
import org.wfmc.elo.model.EloWfmcSord;
import org.wfmc.wapi.WMConnectInfo;

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
    public void should_start_elo_workflow_process(){

        // given
        String processInstanceId = "someProcessInstanceId";
        EloWfmcProcessInstance wmProcessInstance = new EloWfmcProcessInstance();
        wmProcessInstance.setProcessDefinitionId("4"); // setting the template ID
        wmProcessInstance.setName("Test Workflow Name");
        wmProcessInstance.setEloWfmcSord(new EloWfmcSord("104")); //idSORD fisier TEST WF

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
    public void should_assign_process_instance_attribute(){

        // given
        String processInstanceId = "TestProcInstId";
        String attributeName = "Sord";
        String attributeValue = "5";

        EloWfmcProcessInstance eloWfmcProcessInstance = new EloWfmcProcessInstance();

        eloWfmcService = Mockito.spy(eloWfmcService);
        Mockito.when(eloWfmcService.getProcessInstance(processInstanceId)).thenReturn(eloWfmcProcessInstance);

        // when
        eloWfmcService.assignProcessInstanceAttribute(processInstanceId, attributeName, attributeValue);

        // then
        Assertions.assertThat(eloWfmcProcessInstance.getEloWfmcSord()).isNotNull();
        Assertions.assertThat(((EloWfmcSord)eloWfmcProcessInstance.getEloWfmcSord()).getSordId()).isEqualTo("5");
        // TODO - complete test assertions after adding metadata

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
