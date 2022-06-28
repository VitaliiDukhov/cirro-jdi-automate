package org.mytests.uiobjects.example.site.custom;

import com.epam.jdi.light.asserts.generic.UIAssert;
import com.epam.jdi.light.common.JDIAction;
import com.epam.jdi.light.elements.base.UIBaseElement;
import com.epam.jdi.light.elements.common.UIElement;
import org.hamcrest.Matchers;

import java.util.Optional;

import static com.epam.jdi.light.asserts.core.SoftAssert.jdiAssert;

public class UserMenu extends UIBaseElement<UserMenu.UserMenuAssert> implements IMenu {

    private static final String PROFILE_NAME_CLZ = "profile-name";
    private static final String PROFILE_ROLE_CLZ = "profile-role";

    /**
     * Method returns string value of element by class
     *
     * @param clz css class
     * @return text or {@code null} if no elements were found by css class
     */
    public String getProfileLineByClass(String clz) {
        Optional<UIElement> line = core().finds("summary flex p").stream()
                .filter(el -> el.core().hasClass(clz)).findFirst();
        return line.map(UIElement::text).orElse(null);
    }

    /**
     * Method returns Profile Name text from User menu
     *
     * @return text
     */
    public String getProfileName() {
        return getProfileLineByClass(PROFILE_NAME_CLZ);
    }

    /**
     * Method returns Profile Role text from User menu
     *
     * @return text
     */
    public String getProfileRole() {
        return getProfileLineByClass(PROFILE_ROLE_CLZ);
    }

    @Override
    public UIElement getMenuItem(String item) {
        UIElement menu = getMenu();
        expandMenu();
        menu.waitFor().displayed();

        return menu.finds(".//a | .//form").stream().filter(el -> el.getText().trim().equalsIgnoreCase(item))
                .findAny().orElseThrow(() -> new IllegalArgumentException("User Menu: Can't find item by name " + item));
    }

    /**
     * Assert for UserMenu class
     *
     * @return assert as {@link UserMenuAssert}
     */
    @Override
    public UserMenuAssert is() {
        return new UserMenuAssert().set(this);
    }

    /**
     * Asserts for UserMenu class
     */
    public static class UserMenuAssert extends UIAssert<UserMenuAssert, UserMenu> {

        @JDIAction("Check '{name}' user profile has name '{0}'")
        public UserMenuAssert profileName(String profileName) {
            jdiAssert(element().getProfileName(), Matchers.equalToIgnoringCase(profileName));
            return this;
        }

        @JDIAction("Check '{name}' user profile has role '{0}'")
        public UserMenuAssert profileRole(String profileRole) {
            jdiAssert(element().getProfileRole(), Matchers.equalToIgnoringCase(profileRole));
            return this;
        }
    }
}
