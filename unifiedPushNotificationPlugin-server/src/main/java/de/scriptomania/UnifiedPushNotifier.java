package de.scriptomania;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.Build;
import jetbrains.buildServer.notification.NotificationRulesManager;
import jetbrains.buildServer.notification.Notificator;
import jetbrains.buildServer.notification.NotificatorRegistry;
import jetbrains.buildServer.responsibility.ResponsibilityEntry;
import jetbrains.buildServer.responsibility.TestNameResponsibilityEntry;
import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.serverSide.mute.MuteInfo;
import jetbrains.buildServer.serverSide.problems.BuildProblemInfo;
import jetbrains.buildServer.tests.TestName;
import jetbrains.buildServer.users.NotificatorPropertyKey;
import jetbrains.buildServer.users.PropertyKey;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.vcs.VcsRoot;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Created by leadsinger_mac_mini on 13/05/15.
 */
public class UnifiedPushNotifier implements Notificator {

    private static final Logger LOGGER = Logger.getInstance(UnifiedPushNotifier.class.getName());

    private static final String TYPE = "UnifiedPushNotifier";
    private static final String TYPE_NAME = "Aerogear-Unified-Push-Service-Notifier";
    private static final String SERVER_URL = "SERVER_URL";
    private static final String APPLICATION_ID = "APPLICATION_ID";
    private static final String MASTER_SECRET = "MASTER_SECRET";

    private static final PropertyKey SERVER_URL_KEY = new NotificatorPropertyKey(TYPE, SERVER_URL);
    private static final PropertyKey APPLICATION_ID_KEY = new NotificatorPropertyKey(TYPE, APPLICATION_ID);
    private static final PropertyKey MASTER_SECRET_KEY = new NotificatorPropertyKey(TYPE, MASTER_SECRET);

    public UnifiedPushNotifier(NotificatorRegistry notificatorRegistry, NotificationRulesManager notificationRulesManager) throws IOException {
        ArrayList<UserPropertyInfo> userProps = new ArrayList<UserPropertyInfo>();
        userProps.add(new UserPropertyInfo(SERVER_URL, "Server-URL"));
        userProps.add(new UserPropertyInfo(APPLICATION_ID, "Application-ID"));
        userProps.add(new UserPropertyInfo(MASTER_SECRET, "Master-Secret"));
        notificatorRegistry.register(this, userProps);
    }

    private void sendMessage(SUser user, SRunningBuild sRunningBuild) {
        String serverURL = user.getPropertyValue(SERVER_URL_KEY);
        String applicationID = user.getPropertyValue(APPLICATION_ID_KEY);
        String masterSecret = user.getPropertyValue(MASTER_SECRET_KEY);
        UnifiedPushMessageSender.sendMessage(serverURL, applicationID, masterSecret, sRunningBuild);
    }

    @Override
    public void notifyBuildStarted(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        for (SUser user : sUsers) {
            sendMessage(user, sRunningBuild);
        }
    }

    @Override
    public void notifyBuildSuccessful(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        for (SUser user : sUsers) {
            sendMessage(user, sRunningBuild);
        }
    }

    @Override
    public void notifyBuildFailed(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        for (SUser user : sUsers) {
            sendMessage(user, sRunningBuild);
        }
    }

    @Override
    public void notifyBuildFailedToStart(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        for (SUser user : sUsers) {
            sendMessage(user, sRunningBuild);
        }
    }

    @Override
    public void notifyLabelingFailed(Build build, VcsRoot vcsRoot, Throwable throwable, Set<SUser> sUsers) {
        // ignore
    }

    @Override
    public void notifyBuildFailing(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        for (SUser user : sUsers) {
            sendMessage(user, sRunningBuild);
        }
    }

    @Override
    public void notifyBuildProbablyHanging(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        for (SUser user : sUsers) {
            sendMessage(user, sRunningBuild);
        }
    }

    @Override
    public void notifyResponsibleChanged(SBuildType sBuildType, Set<SUser> sUsers) {
        // ignore
    }

    @Override
    public void notifyResponsibleAssigned(SBuildType sBuildType, Set<SUser> sUsers) {
        // ignore
    }

    @Override
    public void notifyResponsibleChanged(TestNameResponsibilityEntry testNameResponsibilityEntry, TestNameResponsibilityEntry testNameResponsibilityEntry1, SProject sProject, Set<SUser> sUsers) {
        // ignore
    }

    @Override
    public void notifyResponsibleAssigned(TestNameResponsibilityEntry testNameResponsibilityEntry, TestNameResponsibilityEntry testNameResponsibilityEntry1, SProject sProject, Set<SUser> sUsers) {
        // ignore
    }

    @Override
    public void notifyResponsibleChanged(Collection<TestName> collection, ResponsibilityEntry responsibilityEntry, SProject sProject, Set<SUser> sUsers) {
        // ignore
    }

    @Override
    public void notifyResponsibleAssigned(Collection<TestName> collection, ResponsibilityEntry responsibilityEntry, SProject sProject, Set<SUser> sUsers) {
        // ignore
    }

    @Override
    public void notifyBuildProblemResponsibleAssigned(Collection<BuildProblemInfo> collection, ResponsibilityEntry responsibilityEntry, SProject sProject, Set<SUser> sUsers) {
        // ignore
    }

    @Override
    public void notifyBuildProblemResponsibleChanged(Collection<BuildProblemInfo> collection, ResponsibilityEntry responsibilityEntry, SProject sProject, Set<SUser> sUsers) {
        // ignore
    }

    @Override
    public void notifyTestsMuted(Collection<STest> collection, MuteInfo muteInfo, Set<SUser> sUsers) {
        // ignore
    }

    @Override
    public void notifyTestsUnmuted(Collection<STest> collection, MuteInfo muteInfo, SUser sUser, Set<SUser> sUsers) {
        // ignore
    }

    @Override
    public void notifyBuildProblemsMuted(Collection<BuildProblemInfo> collection, MuteInfo muteInfo, Set<SUser> sUsers) {
        // ignore
    }

    @Override
    public void notifyBuildProblemsUnmuted(Collection<BuildProblemInfo> collection, MuteInfo muteInfo, SUser sUser, Set<SUser> sUsers) {
        // ignore
    }

    @NotNull
    @Override
    public String getNotificatorType() {
        return TYPE;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return TYPE_NAME;
    }

}