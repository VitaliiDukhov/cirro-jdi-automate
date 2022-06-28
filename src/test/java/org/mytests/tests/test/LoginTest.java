package org.mytests.tests.test;

import com.jdiai.tools.Timer;
import org.hamcrest.Matchers;
import org.mytests.steps.DashboardSteps;
import org.mytests.steps.LoginSteps;
import org.mytests.steps.ProjectConfigurationSteps;
import org.mytests.tests.TestsInit;
import org.mytests.tests.testng.TestNGListener;
import org.mytests.utils.Project;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.mytests.utils.User;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mytests.uiobjects.example.site.SiteJdi.*;

@Listeners(TestNGListener.class)
public class LoginTest implements TestsInit {

    @BeforeMethod
    public void before() {
        landingPage.open();
        landingPage.shouldBeOpened();
    }

    @Test
    public void test() {
        User user = User.getUser("admin");
        Project<Project.Manual> project = Project.defaultProject();
        landingPage.getSignButton().has()
                .text(Matchers.containsString("Sign in with your Cirro account"));
        landingPage.getSignButton().click();

        LoginSteps.login(user);

        dashboardPage.getAlertMessage().is().visible()
                .and().text("Successfully authenticated from cirro account.");

        dashboardPage.getUserMenu().has().profileRole(user.getRole());

        dashboardPage.getProjectMenu().is().visible();
        DashboardSteps.createProjectButtonClick();

        projectConfigurationPage.shouldBeOpened();
        projectConfigurationPage.getSideBar().getProjectInfo().is().displayed();

        ProjectConfigurationSteps.projectSetupFill(project);
        ProjectConfigurationSteps.clickSubmitButton();

        ProjectConfigurationSteps.projectGeneralFill(project);
        ProjectConfigurationSteps.clickSubmitButton();

        ProjectConfigurationSteps.projectFlexFill(project);
        ProjectConfigurationSteps.clickSubmitButton();

        projectConfigurationPage.getAlertMessage().is().displayed().and().has().cssClass("alert-info")
                .and().has().text(Matchers.containsString("Project was successfully updated"));
        projectConfigurationPage.getProjectMenu().getMenuItem(project.getName(), false).is().displayed();
        projectConfigurationPage.getProjectMenu().collapseMenu();

        //to be refactored
        ProjectConfigurationSteps.projectTeamFill(project);
        ProjectConfigurationSteps.clickContinueButton();

        //to be refactored
        ProjectConfigurationSteps.projectTestCaseManualFill(project);
        ProjectConfigurationSteps.clickSubmitButton();

        Timer.waitCondition(() -> dashboardPage.getAlertMessage().isDisplayed());
        dashboardPage.getAlertMessage().is().displayed().and().has().cssClass("alert-info")
                        .and().has().text(Matchers.containsString("Test manager connection was successfully added"));

        LoginSteps.signOut();

    }
}
