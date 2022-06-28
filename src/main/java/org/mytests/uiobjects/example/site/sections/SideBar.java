package org.mytests.uiobjects.example.site.sections;

import com.epam.jdi.light.elements.common.UIElement;
import com.epam.jdi.light.elements.complex.WebList;
import com.epam.jdi.light.elements.composite.Section;
import com.epam.jdi.light.elements.pageobjects.annotations.locators.UI;
import lombok.Getter;
import org.mytests.uiobjects.example.custom.IMenu;

import java.util.Optional;

@Getter
public class SideBar extends Section implements IMenu {

    @UI(".sidebar-projectcode")
    UIElement projectInfo;

    @UI("nav a.navlink")
    WebList sideBarLinks;

    @Override
    public UIElement getMenuItem(String item) {
        Optional<UIElement> opt = getSideBarLinks().stream().filter(el -> el.getText().trim().equalsIgnoreCase(item))
                .findFirst();
        if (opt.isPresent()) {
            return opt.get();
        } else {
            return getSideBarLinks().stream().filter(el -> el.getText().contains(item)).findAny()
                    .orElseThrow(() -> new IllegalArgumentException("Side Bar: can't find link by name " + item));
        }
    }
}
