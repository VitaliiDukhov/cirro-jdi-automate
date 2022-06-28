package org.mytests.uiobjects.example.site.pages;

import com.epam.jdi.light.elements.common.Alerts;
import com.epam.jdi.light.elements.common.UIElement;
import com.epam.jdi.light.elements.composite.WebPage;
import com.epam.jdi.light.elements.pageobjects.annotations.locators.UI;
import com.epam.jdi.light.ui.html.elements.common.Text;
import lombok.Getter;
import org.mytests.uiobjects.example.custom.ProjectMenu;
import org.mytests.uiobjects.example.custom.UserMenu;

@Getter
public abstract class CommonPage extends WebPage {

    @UI(".user-menu")
    UserMenu userMenu;

    @UI("[class=header-dropdown]")
    ProjectMenu projectMenu;

    @UI(".alert")
    Text alertMessage;

}
