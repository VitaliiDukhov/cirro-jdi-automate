package org.mytests.steps;

import static org.mytests.uiobjects.example.site.SiteJdi.dashboardPage;

/**
 * Steps related to Dashboard page
 */
public class DashboardSteps {

    /**
     * Method performs click on 'Create new project' item in Project menu
     */
    public static void createProjectButtonClick() {
        dashboardPage.getProjectMenu().createProject();
    }

    /**
     * Method opens project by project name via Project menu
     *
     * @param projectName project to open
     */
    public static void openProject(String projectName) {
        dashboardPage.getProjectMenu().clickMenuItem(projectName);
    }

    /**
     * Method opens project by prefix and code via Project menu
     *
     * @param prefix Project prefix
     * @param code Project code
     */
    public static void openProjectByPrefixAndCode(String prefix, String code) {
        dashboardPage.getProjectMenu().getProjectByPrefixAndCode(prefix, code).click();
    }
}
