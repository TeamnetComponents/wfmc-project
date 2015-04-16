package ro.teamnet.wfmc.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.wfmc.wapi.WMConnectInfo;

@Component
@ConfigurationProperties(prefix = "wfmc.wmConnectInfo")
public class WMConnectInfoBean {
    private String userIdentification;
    private String password;
    private String engineName;
    private String scope;

    public WMConnectInfo getWMConnectInfo() {
        return new WMConnectInfo(userIdentification, password, engineName, scope);
    }

    public void setUserIdentification(String userIdentification) {
        this.userIdentification = userIdentification;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
