package by.epam.project.controller.filter.typerole;

import java.util.Set;


/**
 * The enum Role permission.
 */
public enum RolePermission {
    /**
     * Admin role permission.
     */
    ADMIN(Set.of(
            "CHANGE_LANGUAGE",
            "CHANGE_PASSWORD_BY_OLD_PASSWORD",
            "CREATE_CATEGORY",
            "CREATE_PRODUCT",
            "LOAD_ALL_CATEGORIES",
            "LOAD_ALL_CLIENTS",
            "LOAD_ALL_ORDERS",
            "LOAD_ALL_PRODUCTS_BY_CATEGORY",
            "LOAD_PROFILE_IMAGE",
            "PASSING_BY_ADMIN",
            "REMOVE_CATEGORY",
            "REMOVE_PROFILE_IMAGE",
            "SIGN_OUT",
            "UPDATE_CATEGORY_NAME",
            "UPDATE_CLIENT_STATUS",
            "UPDATE_ORDER_STATUS",
            "UPDATE_PRODUCT_CATEGORY",
            "UPDATE_PRODUCT_INFO",
            "UPDATE_PRODUCT_STATUS",
            "UPDATE_PROFILE",
            "UPLOAD_PRODUCT_IMAGE",
            "UPLOAD_PROFILE_IMAGE"
    )),
    /**
     * Client role permission.
     */
    CLIENT(Set.of(
            "ADD_PRODUCT_TO_SHOPPING_CART",
            "CHANGE_LANGUAGE",
            "CHANGE_PASSWORD_BY_OLD_PASSWORD",
            "CREATE_ORDER",
            "LOAD_ALL_CATEGORIES",
            "LOAD_ALL_ORDERS",
            "LOAD_ALL_PRODUCTS_BY_CATEGORY",
            "LOAD_PROFILE_IMAGE",
            "LOAD_SHOPPING_CART",
            "PASSING_BY_CLIENT",
            "REMOVE_PRODUCT_FROM_SHOPPING_CART",
            "REMOVE_PROFILE_IMAGE",
            "SIGN_OUT",
            "UPDATE_PROFILE",
            "UPLOAD_PROFILE_IMAGE"
    )),
    /**
     * Guest role permission.
     */
    GUEST(Set.of(
            "PASSING_BY_GUEST",
            "CHANGE_LANGUAGE",
            "CHECK_LOGIN_EXISTENCE",
            "LOAD_ALL_PRODUCTS_BY_CATEGORY",
            "LOAD_ALL_CATEGORIES",
            "SIGN_IN",
            "SIGN_UP",
            "CONFIRM_SIGN_UP",
            "CHANGE_PASSWORD_BY_EMAIL"
    ));
    private final Set<String> commands;

    RolePermission(Set<String> commandNames) {
        this.commands = commandNames;
    }

    /**
     * Gets commands.
     *
     * @return the commands
     */
    public Set<String> getCommands() {
        return commands;
    }
}
