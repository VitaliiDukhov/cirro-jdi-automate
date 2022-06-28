package org.mytests.steps;

import org.mytests.utils.User;

import static com.jdiai.tools.Timer.waitCondition;

import static org.mytests.uiobjects.example.site.SiteJdi.dashboardPage;
import static org.mytests.uiobjects.example.site.SiteJdi.landingPage;
import static org.mytests.uiobjects.example.site.SiteJdi.loginPage;

/**
 * Authorization staff
 */
public class LoginSteps {

    /**
     * Method performs user authorization
     *
     * @param user as {@link User}
     */
    public static void login(User user) {
        loginPage.getUsernameField().setValue(user.getUsername());
        loginPage.getPasswordField().setValue(user.getPassword());
        loginPage.getSignInButton().click();
    }

    /**
     * Method performs sign out
     */
    public static void signOut() {
        dashboardPage.getUserMenu().clickMenuItem("Sign out");
        landingPage.shouldBeOpened();
        waitCondition(() -> landingPage.getSignButton().isDisplayed());
        landingPage.getSignButton().is().visible();
    }

}
