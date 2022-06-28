package org.mytests.uiobjects.example.site.custom;

import com.epam.jdi.light.elements.common.UIElement;
import com.epam.jdi.light.elements.interfaces.base.ICoreElement;

public interface IMenu extends ICoreElement {

    /**
     * Check if menu is expanded or not
     *
     * @return {@code true} if menu is expanded, otherwise - {@code false}
     */
    default boolean isOpened() {
        return core().hasAttribute("open");
    }

    /**
     * Method expands menu
     */
    default void expandMenu() {
        if(!isOpened()) {
            core().click();
        }
    }

    /**
     * Method collapses menu
     */
    default void collapseMenu() {
        if(isOpened()) {
            core().click();
        }
    }

    /**
     * Method returns menu object
     *
     * @return menu as {@link UIElement}
     */
    default UIElement getMenu() {
        return core().find("div[role=menu]");
    }

    /**
     * Method performs click on menu item
     *
     * @param item to click on
     */
    default void clickMenuItem(String item) {
        getMenuItem(item).click();
    }

    /**
     * Method returns menu item
     *
     * @param item to look for
     * @return item as {@link UIElement}
     */
    UIElement getMenuItem(String item);
}
