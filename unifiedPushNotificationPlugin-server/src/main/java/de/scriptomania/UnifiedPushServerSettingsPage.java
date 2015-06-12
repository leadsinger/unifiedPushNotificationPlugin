package de.scriptomania;

import jetbrains.buildServer.controllers.admin.AdminPage;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by leadsinger_mac_mini on 20/05/15.
 */
public class UnifiedPushServerSettingsPage extends AdminPage {

    private static final String PLUGIN_NAME = "Unified-Push-Service-Notifier";
    private static final String TAB_TITLE = "Unified Push Notifier";
    private static final String PAGE = "serverSettings.jsp";

    private UnifiedPushServerSettings configuration;

    public UnifiedPushServerSettingsPage(@NotNull PagePlaces pagePlaces, @NotNull PluginDescriptor descriptor,
                                         @NotNull UnifiedPushServerSettings configuration) {
        super(pagePlaces);
        this.configuration = configuration;
        setPluginName(PLUGIN_NAME);
        setTabTitle(TAB_TITLE);
        setIncludeUrl(descriptor.getPluginResourcesPath(PAGE));
        register();
    }

    @NotNull
    @Override
    public String getGroup() {
        return SERVER_RELATED_GROUP;
    }

    @Override
    public boolean isAvailable(@NotNull HttpServletRequest request) {
        return true;
    }

    @Override
    public void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request) {
        super.fillModel(model, request);
        model.put(UnifiedPushServerSettings.SERVER_URL, configuration.getServerURL());
        model.put(UnifiedPushServerSettings.APPLICATION_ID, configuration.getApplicationId());
        model.put(UnifiedPushServerSettings.MASTER_SECRET, configuration.getMasterSecret());
    }

}