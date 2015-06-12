package de.scriptomania;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by leadsinger_mac_mini on 08/06/15.
 */
@XStreamAlias("unfied_push_server")
public class UnifiedPushServerSettings {

    public static final String APPLICATION_ID = "application_id";
    public static final String MASTER_SECRET = "master_secret";
    public static final String SERVER_URL = "server_url";
    public static final String ALIAS = "ALIAS";

    @XStreamAlias("server_url")
    private String serverURL = "";

    @XStreamAlias("application_id")
    private String applicationId = "";

    @XStreamAlias("master_secret")
    private String masterSecret = "";

    public String getServerURL() {
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

}