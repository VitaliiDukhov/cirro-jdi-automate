package org.mytests.steps;

import static org.mytests.uiobjects.example.site.SiteJdi.dashboardPage;

public class DashboardSteps {

    public static void createProjectButtonClick() {
        dashboardPage.getProjectMenu().createProject();
    }

    public static void openProject(String projectName) {
        dashboardPage.getProjectMenu().clickMenuItem(projectName);
    }

    public static void openProjectByPrefixAndCode(String prefix, String code) {
        dashboardPage.getProjectMenu().getProjectByPrefixAndCode(prefix, code).click();
    }
}
