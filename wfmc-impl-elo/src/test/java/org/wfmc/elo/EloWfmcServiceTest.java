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

        eloWfmcService.connect(wmConnectInfo);

        // when
        String workspaceId = eloWfmcService.startProcess(processInstanceId);

        // then
        Assertions.assertThat(workspaceId).isNotNull();

    }


}
