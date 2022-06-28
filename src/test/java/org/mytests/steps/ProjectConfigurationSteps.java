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

    public static void projectSetupFill(String prefix, String code) {
        projectConfigurationPage.getProjectPrefix().setValue(prefix);
        projectConfigurationPage.getProjectCode().setValue(code);
    }

    public static void projectSetupFill(Project<? extends Project.TcSources> project) {
        projectSetupFill(project.getPrefix(), project.getCode());
    }

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

    public static void projectGeneralFill(Project<? extends Project.TcSources> project) {
        projectGeneralFill(project.getName(), project.getDescription(), project.getDomain(), project.getFramework(),
                project.getSpScale(), project.getLanguage(), project.isVerification() ? "Yes": "No");
    }



    public static void clickSubmitButton() {
        waitCondition(() -> projectConfigurationPage.getSubmitButton().isDisplayed());
        projectConfigurationPage.getSubmitButton().click(ElementArea.JS);
    }

    public static void clickContinueButton() {
        waitCondition(() -> projectConfigurationPage.getContinueButton().isDisplayed());
        projectConfigurationPage.getContinueButton().click(ElementArea.JS);
    }

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

    public static void projectFlexFill(Project<? extends Project.TcSources> project) {
        projectFlexFill(project.isInvitation() ? "Yes": "No", String.valueOf(project.getSpPrice()),
                project.getSelectLocations(), project.getRejectLocations(),
                project.getAutoSkills(), project.getTestSkills(),
                project.getOnBoardingContent());
    }
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

    public static void projectTeamFill(Project<? extends Project.TcSources> project) {
        projectTeamFill(project.getOwners(), project.getManagers(), project.getCore());
    }

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

    public static void projectTestCaseManualFill(Project<Project.Manual> project) {
        projectTestCaseManualFill(project.getTestCaseSources().getType(), project.getTestCaseSources().getSourceType(),
                project.getTestCaseSources().getSyncableField(), project.getTestCaseSources().getUpload());
    }
}
