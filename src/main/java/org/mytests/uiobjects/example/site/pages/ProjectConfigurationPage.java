package org.mytests.uiobjects.example.site.pages;

import com.epam.jdi.light.elements.common.Label;
import com.epam.jdi.light.elements.common.UIElement;
import com.epam.jdi.light.elements.complex.WebList;
import com.epam.jdi.light.elements.complex.dropdown.Dropdown;
import com.epam.jdi.light.elements.complex.dropdown.DropdownSelect;
import com.epam.jdi.light.elements.pageobjects.annotations.locators.UI;
import com.epam.jdi.light.ui.html.elements.common.*;
import com.epam.jdi.light.ui.html.elements.complex.MultiSelector;
import lombok.Data;
import lombok.Getter;
import org.mytests.uiobjects.example.site.sections.SideBar;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Data
public class ProjectConfigurationPage extends CommonPage {

    @UI("nav.sidebar")
    SideBar sideBar;

    @UI("input[type=submit]")
    Button submitButton;

    //Project
    @UI("#project_prefix")
    TextField projectPrefix;

    @UI("#project_code")
    TextField projectCode;

    //General
    @UI("#project_name")
    TextField projectName;

    @UI("#project_description")
    TextField projectDescription;

    @UI("#project_domain")
    DropdownSelect projectDomain;

    @UI("#project_test_framework")
    DropdownSelect projectFramework;

    @UI("#project_story_point_scale")
    DropdownSelect projectScale;

    @UI("#project_programming_language")
    DropdownSelect projectLanguage;

    @UI("#project_verification_task_enabled")
    DropdownSelect projectVerification;

    //Flex team
    @UI("label[for^=project_crowd_invitations_enabled_]")
    WebList crowdInvitations;

    @UI("#project_crowd_price_per_story_point")
    TextField spPrice;

    @UI("label[for^=project_countries_crowd_filter_select]")
    WebList filterLocations;

    @UI("textarea[aria-describedby*=countries]")
    TextArea countriesToOpenSelection;

    @UI("li[id^=select2-countries_crowd_filter-result-]")
    WebList countries;

    @UI("textarea[aria-describedby*=automated_testing_skills]")
    TextArea skillsToOpenSelection;

    @UI("li[id^=select2-automated_testing_skills_crowd_filter-result-]")
    WebList skills;

    @UI("textarea[aria-describedby*=functional_testing_skills]")
    TextArea testSkillsToOpenSelection;

    @UI("li[id^=select2-functional_testing_skills_crowd_filter-result-]")
    WebList testSkills;

    @UI("#project_onboarding_content")
    TextArea onboardingContent;

    //Team
    @UI("//div[contains(text(), 'Project Owners')]/button")
    Button addOwnerButton;

    @UI("//div[contains(text(), 'Test Managers')]/button")
    Button addManagerButton;

    @UI("//div[contains(text(), 'Core')]/button")
    Button addCoreButton;

    @UI("#project_member_email")
    TextField projectMemberField;

    @UI(".card-footer button[type='submit']")
    Button inviteButton;

    @UI(".car-footer button[type='button']")
    Button cancelInviteButton;

    @UI("#project_member_roles_automator")
    Checkbox automatorRoleCheckbox;

    @UI("#project_member_roles_qualifier")
    Checkbox qualifierRoleCheckbox;

    @UI("#project_member_roles_verifier")
    Checkbox verifierRoleCheckbox;

    @UI("#project_member_roles_reviewer")
    Checkbox reviewerRoleCheckbox;

    //Test cases
    @UI("div.radio-pills-item.false label.btn")
    List<Button> connectionTypeList;

    @UI("#test_rail_connection_csv_source_type")
    DropdownSelect cvsSourceTypeSelect;

    @UI("#test_rail_connection_syncable_field_name")
    TextField syncableFieldName;

    @UI("span.icon-file-csv")
    Icon fileIcon;

    @UI("input[type=file].dz-hidden-input")
    FileInput inputFile;

    @UI("div.dz-file-preview")
    UIElement filePreview;

    //other
    @UI(".section-content a.btn")
    Button continueButton;

    public FileInput getInputFile() {
        inputFile.core().strictSearch(false);
        inputFile.core().noValidation();
        return inputFile;
    }
}
