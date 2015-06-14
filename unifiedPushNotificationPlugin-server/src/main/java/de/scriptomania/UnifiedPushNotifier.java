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

    public static final String TYPE = "UnifiedPushNotifier";
    private static final String TYPE_NAME = "Unified-Push-Notifier";

    private UnifiedPushServerSettings serverSettings;

    public UnifiedPushNotifier(NotificatorRegistry notificatorRegistry, NotificationRulesManager notificationRulesManager, @NotNull UnifiedPushServerSettings serverSettings) throws IOException {
        ArrayList<UserPropertyInfo> userProps = new ArrayList<UserPropertyInfo>();
        userProps.add(new UserPropertyInfo(UnifiedPushServerSettings.ALIAS, "Alias"));
        notificatorRegistry.register(this, userProps);
        this.serverSettings = serverSettings;
    }

    @Override
    public void notifyBuildStarted(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        for (SUser user : sUsers) {
            UnifiedPushMessageSender.sendMessage(serverSettings, sRunningBuild, sUsers);
        }
    }

    @Override
    public void notifyBuildSuccessful(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        for (SUser user : sUsers) {
            UnifiedPushMessageSender.sendMessage(serverSettings, sRunningBuild, sUsers);
        }
    }

    @Override
    public void notifyBuildFailed(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        for (SUser user : sUsers) {
            UnifiedPushMessageSender.sendMessage(serverSettings, sRunningBuild, sUsers);
        }
    }

    @Override
    public void notifyBuildFailedToStart(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        for (SUser user : sUsers) {
            UnifiedPushMessageSender.sendMessage(serverSettings, sRunningBuild, sUsers);
        }
    }

    @Override
    public void notifyLabelingFailed(Build build, VcsRoot vcsRoot, Throwable throwable, Set<SUser> sUsers) {
        // ignore
    }

    @Override
    public void notifyBuildFailing(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        for (SUser user : sUsers) {
            UnifiedPushMessageSender.sendMessage(serverSettings, sRunningBuild, sUsers);
        }
    }

    @Override
    public void notifyBuildProbablyHanging(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        for (SUser user : sUsers) {
            UnifiedPushMessageSender.sendMessage(serverSettings, sRunningBuild, sUsers);
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