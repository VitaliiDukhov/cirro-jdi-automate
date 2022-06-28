package org.mytests.uiobjects.example.custom;

import com.epam.jdi.light.elements.common.UIElement;
import com.epam.jdi.light.elements.interfaces.base.ICoreElement;

public interface IMenu extends ICoreElement {

    default boolean isOpened() {
        return core().hasAttribute("open");
    }

    default void expandMenu() {
        if(!isOpened()) {
            core().click();
        }
    }

    default void collapseMenu() {
        if(isOpened()) {
            core().click();
        }
    }

    default UIElement getMenu() {
        return core().find("div[role=menu]");
    }

    default void clickMenuItem(String item) {
        getMenuItem(item).click();
    }

    UIElement getMenuItem(String item);
}
