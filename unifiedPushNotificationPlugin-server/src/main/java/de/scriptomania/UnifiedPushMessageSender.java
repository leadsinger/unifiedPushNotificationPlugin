package de.scriptomania;

import jetbrains.buildServer.serverSide.SRunningBuild;
import org.jboss.aerogear.unifiedpush.JavaSender;
import org.jboss.aerogear.unifiedpush.SenderClient;
import org.jboss.aerogear.unifiedpush.message.UnifiedMessage;

/**
 * Created by leadsinger_mac_mini on 17/05/15.
 */
public class UnifiedPushMessageSender {

    private static final String BUILD_NUMBER_KEY = "BUILD_NUMBER_KEY";
    private static final String BUILD_DESCRIPTION_KEY = "BUILD_DESCRIPTION_KEY";
    private static final String BUILD_STATUS_KEY = "BUILD_STATUS_KEY";
    private static final String BUILD_TIMESTAMP_KEY = "BUILD_TIMESTAMP_KEY";

    public static void sendMessage(String serverURL, String applicationID, String masterSecret, SRunningBuild runningBuild) {

        // create message
        UnifiedMessage unifiedMessage = new UnifiedMessage.Builder()
                .pushApplicationId(applicationID)
                .masterSecret(masterSecret)
                .attribute(BUILD_NUMBER_KEY, runningBuild.getBuildNumber())
                .attribute(BUILD_DESCRIPTION_KEY, runningBuild.getBuildDescription())
                .attribute(BUILD_STATUS_KEY, runningBuild.getBuildStatus().getText())
                .attribute(BUILD_TIMESTAMP_KEY, runningBuild.getLastBuildActivityTimestamp().toString())
                .build();

        // send message to server
        JavaSender pushSender = new SenderClient.Builder(serverURL).build();
        pushSender.send(unifiedMessage);
    }

}