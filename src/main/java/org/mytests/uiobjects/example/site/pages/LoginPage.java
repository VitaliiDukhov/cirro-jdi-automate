package org.mytests.uiobjects.example.site.pages;

import com.epam.jdi.light.elements.composite.WebPage;
import com.epam.jdi.light.elements.pageobjects.annotations.Url;
import com.epam.jdi.light.elements.pageobjects.annotations.locators.UI;
import com.epam.jdi.light.ui.html.elements.common.Button;
import com.epam.jdi.light.ui.html.elements.common.Image;
import com.epam.jdi.light.ui.html.elements.common.TextField;
import lombok.Getter;

@Url("/users/sign_in")
@Getter
public class LoginPage extends WebPage {

    @UI(".logo-signin img")
    Image logoImage;

    @UI("#user_email")
    TextField usernameField;

    @UI("#user_password")
    TextField passwordField;

    @UI("input[type='submit']")
    Button signInButton;
}
