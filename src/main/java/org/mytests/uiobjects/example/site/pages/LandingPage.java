package org.mytests.uiobjects.example.site.pages;

import com.epam.jdi.light.elements.composite.WebPage;
import com.epam.jdi.light.elements.pageobjects.annotations.Url;
import com.epam.jdi.light.elements.pageobjects.annotations.locators.UI;
import com.epam.jdi.light.ui.html.elements.common.Button;
import lombok.Getter;

@Getter
@Url("/")
public class LandingPage extends WebPage {

    @UI("a[href='/users/auth/cirro_sso']")
    Button signButton;

}
