package de.scriptomania;

import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.users.NotificatorPropertyKey;
import jetbrains.buildServer.users.PropertyKey;
import jetbrains.buildServer.users.SUser;
import org.jboss.aerogear.unifiedpush.JavaSender;
import org.jboss.aerogear.unifiedpush.SenderClient;
import org.jboss.aerogear.unifiedpush.message.UnifiedMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by leadsinger_mac_mini on 17/05/15.
 */
public class UnifiedPushMessageSender {

    private static final String BUILD_NUMBER_KEY = "BUILD_NUMBER_KEY";
    private static final String BUILD_DESCRIPTION_KEY = "BUILD_DESCRIPTION_KEY";
    private static final String BUILD_STATUS_KEY = "BUILD_STATUS_KEY";
    private static final String BUILD_TIMESTAMP_KEY = "BUILD_TIMESTAMP_KEY";

    private static final String ALIAS = "ALIAS";
    private static final String TYPE = "UnifiedPushNotifier";
    private static final PropertyKey ALIAS_KEY = new NotificatorPropertyKey(TYPE, ALIAS);

    public static void sendMessage(UnifiedPushServerSettings serverSettings, SRunningBuild runningBuild, Set<SUser> sUsers) {
        // get aliases
        List<String> aliases = new ArrayList<String>();
        for (SUser user : sUsers) {
            String alias = user.getPropertyValue(ALIAS_KEY);
            aliases.add(alias);
        }

        // create message
        UnifiedMessage unifiedMessage = new UnifiedMessage.Builder()
                .pushApplicationId(serverSettings.getApplicationId())
                .masterSecret(serverSettings.getMasterSecret())
                .aliases(aliases)
                .attribute(BUILD_NUMBER_KEY, runningBuild.getBuildNumber())
                .attribute(BUILD_DESCRIPTION_KEY, runningBuild.getBuildDescription())
                .attribute(BUILD_STATUS_KEY, runningBuild.getBuildStatus().getText())
                .attribute(BUILD_TIMESTAMP_KEY, runningBuild.getLastBuildActivityTimestamp().toString())
                .build();

        // send message to server
        JavaSender pushSender = new SenderClient.Builder(serverSettings.getServerURL()).build();
        pushSender.send(unifiedMessage);
    }

}