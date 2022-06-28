package org.mytests.steps;

import com.epam.jdi.light.common.ElementArea;
import com.epam.jdi.light.elements.common.UIElement;
import com.epam.jdi.light.ui.html.elements.common.FileInput;
import org.mytests.utils.Project;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.epam.jdi.light.common.Exceptions.runtimeException;
import static com.jdiai.tools.Timer.waitCondition;
import static org.mytests.uiobjects.example.site.SiteJdi.projectConfigurationPage;

public class ProjectConfigurationSteps {

    /**
     * Methods fills in all the fields related to Setup step
     *
     * @param prefix Project prefix
     * @param code Project code
     */
    public static void projectSetupFill(String prefix, String code) {
        projectConfigurationPage.getProjectPrefix().setValue(prefix);
        projectConfigurationPage.getProjectCode().setValue(code);
    }

    /**
     * @see ProjectConfigurationSteps#projectSetupFill(String, String) projectSetupFill
     *
     * @param project as {@link Project}
     */
    public static void projectSetupFill(Project<? extends Project.TcSources> project) {
        projectSetupFill(project.getPrefix(), project.getCode());
    }

    /**
     * Methods fills in all the fields related to General step
     *
     * @param name Project name
     * @param desc Project description
     * @param domain Project domain
     * @param framework Automation framework
     * @param language Programming language
     * @param scale Story point scale
     * @param verification if verification is needed 'Yes|No'
     */
    public static void projectGeneralFill(String name, String desc, String domain, String framework,
                                          String scale, String language, String verification) {
        projectConfigurationPage.getProjectName().setValue(name);
        projectConfigurationPage.getProjectDescription().setValue(desc);
        projectConfigurationPage.getProjectDomain().select(domain);
        projectConfigurationPage.getProjectFramework().select(framework);
        projectConfigurationPage.getProjectScale().select(scale);
        projectConfigurationPage.getProjectLanguage().select(language);
        projectConfigurationPage.getProjectVerification().select(verification);
    }

    /**
     * @see ProjectConfigurationSteps#projectGeneralFill(String, String, String, String, String, String, String) projectGeneralFill
     * @param project as {@link Project}
     */
    public static void projectGeneralFill(Project<? extends Project.TcSources> project) {
        projectGeneralFill(project.getName(), project.getDescription(), project.getDomain(), project.getFramework(),
                project.getSpScale(), project.getLanguage(), project.isVerification() ? "Yes": "No");
    }


    /**
     * Method performs click on Submit button
     * It can be 'Save', 'Save and Continue' buttons etc.
     */
    public static void clickSubmitButton() {
        waitCondition(() -> projectConfigurationPage.getSubmitButton().isDisplayed());
        projectConfigurationPage.getSubmitButton().click(ElementArea.JS);
    }

    /**
     * Method performs click on Continue button
     */
    public static void clickContinueButton() {
        waitCondition(() -> projectConfigurationPage.getContinueButton().isDisplayed());
        projectConfigurationPage.getContinueButton().click(ElementArea.JS);
    }

    /**
     * Method fills in all the fields related to Flex Team step
     *
     * @param crowdInv Should Flex Team members be invited 'Yes|No'
     * @param spPrice Story point price
     * @param selectLocation List of locations to select as {@code List<String>}
     * @param rejectLocations List of locations to reject as {@code List<String>}
     * @param autoSkills List of automations skills as {@code List<String>}
     * @param testSkills List of functional testing skills as {@code List<String>}
     * @param onboardingContent text for onBoard content
     */
    public static void projectFlexFill(String crowdInv, String spPrice, List<String> selectLocation,
                                       List<String> rejectLocations, List<String> autoSkills,
                                       List<String> testSkills, String onboardingContent) {
        projectConfigurationPage.getCrowdInvitations().stream().filter(el -> el.text().contains(crowdInv))
                .findFirst().orElseThrow(() -> runtimeException("")).click();
        projectConfigurationPage.getSpPrice().setValue(spPrice);
        Consumer<String> locationSelection = loc -> {
            projectConfigurationPage.getCountriesToOpenSelection().core().click();
            projectConfigurationPage.getCountriesToOpenSelection().sendKeys(loc.substring(0, 3));
            projectConfigurationPage.getCountries().select(loc);
        };
        if (!selectLocation.isEmpty()) {
            projectConfigurationPage.getFilterLocations().stream().filter(el -> el.text().contains("Select locations"))
                    .findFirst().orElseThrow(() -> runtimeException("")).click();
            selectLocation.forEach(locationSelection);
        }

        if (!rejectLocations.isEmpty()) {
            projectConfigurationPage.getFilterLocations().stream().filter(el -> el.text().contains("Reject locations"))
                    .findFirst().orElseThrow(() -> runtimeException("")).click();
            selectLocation.forEach(locationSelection);
        }

        autoSkills.forEach(skill -> {
            projectConfigurationPage.getSkillsToOpenSelection().core().click();
            projectConfigurationPage.getSkillsToOpenSelection().sendKeys(skill.substring(0, 3));
            projectConfigurationPage.getSkills().select(skill);
        });

        testSkills.forEach(skill -> {
            projectConfigurationPage.getTestSkillsToOpenSelection().core().click();
            projectConfigurationPage.getTestSkillsToOpenSelection().sendKeys(skill.substring(0, 3));
            projectConfigurationPage.getTestSkills().select(skill);
        });

        projectConfigurationPage.getOnboardingContent().setValue(onboardingContent);
    }

    /**
     * @see ProjectConfigurationSteps#projectFlexFill(String, String, List, List, List, List, String) projectFlexFill
     * @param project as {@link Project}
     */
    public static void projectFlexFill(Project<? extends Project.TcSources> project) {
        projectFlexFill(project.isInvitation() ? "Yes": "No", String.valueOf(project.getSpPrice()),
                project.getSelectLocations(), project.getRejectLocations(),
                project.getAutoSkills(), project.getTestSkills(),
                project.getOnBoardingContent());
    }

    /**
     * Method fills in all the field related to Team step
     *
     * @param owners List of owners as {@code List<String>}
     * @param managers List of managers as {@code List<String>}
     * @param core List of core team members with roles as {@code Map<String, List<String>>} where key is an e-mail and value is a list or roles
     */
    public static void projectTeamFill(List<String> owners, List<String> managers, Map<String, List<String>> core) {
        owners.forEach(ow -> {
            projectConfigurationPage.getAddOwnerButton().click(ElementArea.JS);
            projectConfigurationPage.getProjectMemberField().setValue(ow);
            projectConfigurationPage.getInviteButton().click(ElementArea.JS);
        });

        managers.forEach(man -> {
            projectConfigurationPage.getAddManagerButton().click(ElementArea.JS);
            projectConfigurationPage.getProjectMemberField().setValue(man);
            projectConfigurationPage.getInviteButton().click(ElementArea.JS);
        });

        core.forEach((name, roles) -> {
            projectConfigurationPage.getAddCoreButton().click(ElementArea.JS);
            projectConfigurationPage.getProjectMemberField().setValue(name);
            roles.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "automator":
                        projectConfigurationPage.getAutomatorRoleCheckbox().check();
                        break;
                    case "verifier":
                        projectConfigurationPage.getVerifierRoleCheckbox().check();
                        break;
                    case "qualifier":
                        projectConfigurationPage.getQualifierRoleCheckbox().check();
                        break;
                    case "reviewer":
                        projectConfigurationPage.getReviewerRoleCheckbox().check();
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown role: " + role);
                }
            });
            projectConfigurationPage.getInviteButton().click(ElementArea.JS);
        });
    }

    /**
     * @see ProjectConfigurationSteps#projectTeamFill(List, List, Map) projectTeamFill
     *
     * @param project as {@link Project}
     */
    public static void projectTeamFill(Project<? extends Project.TcSources> project) {
        projectTeamFill(project.getOwners(), project.getManagers(), project.getCore());
    }

    /**
     * Method fills in all the fields related to Manual TC source
     * TODO refactor to use Strategy pattern to use all the connection types
     *
     * @param connectionType type to use
     * @param cvsSourceType Source type for uploading file
     * @param syncableField Field to filter testcases
     * @param path path to file
     */
    public static void projectTestCaseManualFill(String connectionType, String cvsSourceType,
                                                 String syncableField, String path) {

        waitCondition(() -> projectConfigurationPage.getConnectionTypeList().stream().findFirst().get().isDisplayed());
        projectConfigurationPage.getConnectionTypeList().stream().filter(el -> el.getValue().equalsIgnoreCase(connectionType))
                .findFirst().orElseThrow(() -> runtimeException("")).click(ElementArea.JS);
        projectConfigurationPage.getCvsSourceTypeSelect().select(cvsSourceType);
        projectConfigurationPage.getSyncableFieldName().setValue(syncableField);
        waitCondition(() -> projectConfigurationPage.getFileIcon().isVisible());
        FileInput input = projectConfigurationPage.getInputFile();
        input.core().strictSearch(false);
        input.core().noValidation();
        waitCondition(input::isExist);
        input.uploadFile((new File(path)).getAbsolutePath());
        waitCondition(() -> {
            UIElement preview = projectConfigurationPage.getFilePreview();
            return preview.isDisplayed() && preview.hasClass("dz-complete");
        });
    }

    /**
     * @see ProjectConfigurationSteps#projectTestCaseManualFill(String, String, String, String) projectTestCaseManualFill
     * @param project as {@link Project}
     */
    public static void projectTestCaseManualFill(Project<Project.Manual> project) {
        projectTestCaseManualFill(project.getTestCaseSources().getType(), project.getTestCaseSources().getSourceType(),
                project.getTestCaseSources().getSyncableField(), project.getTestCaseSources().getUpload());
    }
}
