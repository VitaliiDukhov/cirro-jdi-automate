package org.mytests.tests.test;

import com.jdiai.tools.Timer;
import org.hamcrest.Matchers;
import org.mytests.tests.TestsInit;
import org.testng.annotations.Test;
import org.mytests.utils.User;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mytests.uiobjects.example.site.SiteJdi.landingPage;

public class DomainTest implements TestsInit {

    @Test
    public void domainTest() {
        landingPage.open();
        landingPage.shouldBeOpened();
        Timer.sleep(2000);
    }

    @Test
    public void userTest() {
        List<String> keys = Arrays.asList("admin", "admin2", "reviewer", "automator", "qualifier");
        for (String key: keys) {
            User user = User.getUser(key);
            assertThat(user.getDisplayName(), Matchers.notNullValue());
        }
    }
}
