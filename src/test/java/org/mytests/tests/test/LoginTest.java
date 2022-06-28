package org.mytests.tests.test;

import com.epam.jdi.light.elements.common.Alerts;
import org.hamcrest.Matchers;
import org.mytests.steps.DashboardSteps;
import org.mytests.steps.LoginSteps;
import org.mytests.steps.ProjectConfigurationSteps;
import org.mytests.tests.TestsInit;
import org.mytests.tests.testng.TestNGListener;
import org.mytests.utils.Project;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.mytests.utils.User;

import static com.jdiai.tools.Timer.waitCondition;
import static org.mytests.uiobjects.example.site.SiteJdi.dashboardPage;
import static org.mytests.uiobjects.example.site.SiteJdi.landingPage;
import static org.mytests.uiobjects.example.site.SiteJdi.projectConfigurationPage;


@Listeners(TestNGListener.class)
public class LoginTest implements TestsInit {

    ThreadLocal<Project<Project.Manual>> project = new ThreadLocal<>();
    @BeforeMethod
    public void before() {
        landingPage.open();
        landingPage.shouldBeOpened();
        project.set(Project.defaultProject());
    }

    @Test
    public void test() {
        User user = User.getUser("admin");
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

        ProjectConfigurationSteps.projectSetupFill(project.get());
        ProjectConfigurationSteps.clickSubmitButton();

        ProjectConfigurationSteps.projectGeneralFill(project.get());
        ProjectConfigurationSteps.clickSubmitButton();

        ProjectConfigurationSteps.projectFlexFill(project.get());
        ProjectConfigurationSteps.clickSubmitButton();

        projectConfigurationPage.getAlertMessage().is().displayed().and().has().cssClass("alert-info")
                .and().has().text(Matchers.containsString("Project was successfully updated"));
        projectConfigurationPage.getProjectMenu().getMenuItem(project.get().getName(), false).is().displayed();
        projectConfigurationPage.getProjectMenu().collapseMenu();

        //to be refactored
        ProjectConfigurationSteps.projectTeamFill(project.get());
        ProjectConfigurationSteps.clickContinueButton();

        //to be refactored
        ProjectConfigurationSteps.projectTestCaseManualFill(project.get());
        ProjectConfigurationSteps.clickSubmitButton();

        waitCondition(() -> dashboardPage.getAlertMessage().isDisplayed());
        dashboardPage.getAlertMessage().is().displayed().and().has().cssClass("alert-info")
                        .and().has().text(Matchers.containsString("Test manager connection was successfully added"));



    }

    @AfterMethod
    public void cleanup(ITestResult result) {
        if (result.isSuccess()) { //would cleanup only if test passes
            dashboardPage.open();
            dashboardPage.getProjectMenu().getMenuItem(project.get().getName(), false).click();
            waitCondition(() -> projectConfigurationPage.getSideBar().isDisplayed());
            projectConfigurationPage.getSideBar().clickMenuItem("Configuration");
            waitCondition(() -> projectConfigurationPage.getSubmitButton().isDisplayed());
            projectConfigurationPage.getSubmitButton().click();
            Alerts.acceptAlert();
            waitCondition(() -> dashboardPage.getAlertMessage().text().contains("Project was successfully destroyed"));
        }
        LoginSteps.signOut();
    }
}
