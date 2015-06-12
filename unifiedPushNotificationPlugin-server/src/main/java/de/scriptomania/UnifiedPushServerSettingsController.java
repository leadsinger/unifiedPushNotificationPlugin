package de.scriptomania;

import com.google.common.base.Strings;
import com.thoughtworks.xstream.XStream;
import jetbrains.buildServer.controllers.AjaxRequestProcessor;
import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.ServerPaths;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by leadsinger_mac_mini on 08/06/15.
 */
public class UnifiedPushServerSettingsController extends BaseController {

    private static final String CONFIG_FILE = "unifiedPushServerConfig.xml";
    private static final String CONTROLLER_PATH = "/saveSettings.html";
    private String configFilePath;
    private UnifiedPushServerSettings configuration;

    public UnifiedPushServerSettingsController(@NotNull SBuildServer server, @NotNull ServerPaths serverPaths,
                                               @NotNull WebControllerManager manager,
                                               @NotNull UnifiedPushServerSettings configuration) throws IOException {
        manager.registerController(CONTROLLER_PATH, this);
        configFilePath = (new File(serverPaths.getConfigDir(), CONFIG_FILE)).getCanonicalPath();
        this.configuration = configuration;
    }

    public void initialize() {
        File file = new File(configFilePath);
        if (file.exists()) {
            try {
                loadConfiguration();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            try {
                saveConfiguration();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Nullable
    @Override
    protected ModelAndView doHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        new AjaxRequestProcessor().processRequest(httpServletRequest, httpServletResponse, new AjaxRequestProcessor.RequestHandler() {
            @Override
            public void handleRequest(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull org.jdom.Element element) {
                handleServerConfigurationChange(httpServletRequest);
            }
        });
        return null;
    }

    private void handleServerConfigurationChange(HttpServletRequest request) {
        String serverURL = request.getParameter(UnifiedPushServerSettings.SERVER_URL);
        String applicationId = request.getParameter(UnifiedPushServerSettings.APPLICATION_ID);
        String masterSecret = request.getParameter(UnifiedPushServerSettings.MASTER_SECRET);
        boolean isError = false;

        if (!Strings.isNullOrEmpty(serverURL)) {
            configuration.setServerURL(serverURL);
        } else {
            isError = true;
        }
        if (!Strings.isNullOrEmpty(applicationId)) {
            configuration.setApplicationId(applicationId);
        } else {
            isError = true;
        }
        if (!Strings.isNullOrEmpty(masterSecret)) {
            configuration.setMasterSecret(masterSecret);
        } else {
            isError = true;
        }

        if (!isError) {
            try {
                saveConfiguration();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            getOrCreateMessages(request).addMessage("settingsMessage","Settings successfully saved.");
        } else {
            getOrCreateMessages(request).addMessage("settingsMessage","Please fill in all fields correctly!");
        }
    }

    private void saveConfiguration() throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(getClass());
        File file = new File(configFilePath);
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        xstream.toXML(configuration, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private void loadConfiguration() throws IOException {
        XStream xstream = new XStream();
        xstream.setClassLoader(getClass().getClassLoader());
        xstream.processAnnotations(UnifiedPushServerSettings.class);
        FileReader fileReader = new FileReader(configFilePath);
        UnifiedPushServerSettings loadedConfig = (UnifiedPushServerSettings) xstream.fromXML(fileReader);
        fileReader.close();

        // set configuration
        configuration.setServerURL(loadedConfig.getServerURL());
        configuration.setApplicationId(loadedConfig.getApplicationId());
        configuration.setMasterSecret(loadedConfig.getMasterSecret());
    }

}