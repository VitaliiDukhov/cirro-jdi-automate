package org.mytests.uiobjects.example.custom;

import com.epam.jdi.light.asserts.generic.UIAssert;
import com.epam.jdi.light.elements.base.UIBaseElement;
import com.epam.jdi.light.elements.common.UIElement;
import com.epam.jdi.light.elements.complex.WebList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjectMenu extends UIBaseElement<ProjectMenu.ProjectMenuAssert> implements IMenu {

    @Override
    public ProjectMenuAssert is() {
        return new ProjectMenuAssert().set(this);
    }

    @Override
    public UIElement getMenuItem(String item) {
        return getMenuItem(item, true);
    }

    public UIElement getMenuItem(String item, boolean exact) {
        List<UIElement> items = getMenuItems();
        if (exact) {
            return items.stream().filter(el -> el.text().trim().equalsIgnoreCase(item)).findAny()
                    .orElseThrow(() -> new IllegalArgumentException("Project Menu: can't find item by name " + item));
        } else {
            return items.stream().filter(el -> el.getText().toLowerCase().contains(item.toLowerCase())).findAny()
                    .orElseThrow(() -> new IllegalArgumentException("Project Menu: can't find item by name " + item));
        }
    }

    public UIElement getProjectByPrefixAndCode(String prefix, String code) {
        WebList items = getMenuItems();
        List<String> toMatch = Arrays.asList(prefix, code);
        return items.stream().filter(el -> el.finds(".dropdown-badge div").stream()
                .map(UIElement::getText).collect(Collectors.toList()).equals(toMatch))
                .findAny().orElseThrow(() -> new IllegalArgumentException(
                        String.format("Project Menu: can't find project by prefix '%s' and code '%s'", prefix, code)));
    }

    private WebList getMenuItems() {
        UIElement menu = getMenu();
        expandMenu();
        menu.waitFor(1).is().displayed();

        return menu.finds("a[role=menuitem]");
    }

    public void createProject() {
        expandMenu();
        UIElement createItem = getMenu().findFirst("a[href*='/projects/new']");
        if (createItem.isExist()) {
            createItem.click();
        } else {
            clickMenuItem("Create new project");
        }
    }

    public static class ProjectMenuAssert extends UIAssert<ProjectMenuAssert, ProjectMenu> {

    }
}
