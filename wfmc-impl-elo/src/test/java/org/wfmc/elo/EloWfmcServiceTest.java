package org.wfmc.elo;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.wfmc.wapi.WMConnectInfo;

import java.util.ResourceBundle;

/**
 * @author adrian.zamfirescu
 * @since 20/02/2015
 */
public class EloWfmcServiceTest {

    private EloWfmcService eloWfmcService;

    private static ResourceBundle configBundle = ResourceBundle.getBundle("config");

    @Before
    public void setUp(){
        eloWfmcService = new EloWfmcService();
    }

    @Test
    public void should_create_elo_connection(){

        // given
        WMConnectInfo wmConnectInfo = new WMConnectInfo(configBundle.getString("login.name"),
                                                        configBundle.getString("login.password"),
                                                        configBundle.getString("cnn.name"),
                                                        configBundle.getString("ix.url"));

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
}
