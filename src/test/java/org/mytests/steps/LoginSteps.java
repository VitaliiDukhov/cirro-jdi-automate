package org.mytests.steps;

import org.mytests.utils.User;

import static org.mytests.uiobjects.example.site.SiteJdi.*;
import static org.mytests.uiobjects.example.site.SiteJdi.landingPage;

public class LoginSteps {

    public static void login(User user) {
        loginPage.getUsernameField().setValue(user.getUsername());
        loginPage.getPasswordField().setValue(user.getPassword());
        loginPage.getSignInButton().click();
    }

    public static void signOut() {
        dashboardPage.getUserMenu().clickMenuItem("Sign out");
        landingPage.shouldBeOpened();
        landingPage.getSignButton().is().visible();
    }

}
