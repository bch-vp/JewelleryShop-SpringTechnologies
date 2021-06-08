package by.epam.project.controller.async.command;

import by.epam.project.controller.async.command.impl.AddProductToShoppingCartCommand;
import by.epam.project.controller.async.command.impl.ChangePasswordByEmailCommand;
import by.epam.project.controller.async.command.impl.ChangePasswordByOldPassCommand;
import by.epam.project.controller.async.command.impl.CheckLoginExistenceCommand;
import by.epam.project.controller.async.command.impl.CreateCategoryCommand;
import by.epam.project.controller.async.command.impl.CreateOrderCommand;
import by.epam.project.controller.async.command.impl.CreateProductCommand;
import by.epam.project.controller.async.command.impl.LoadAllCategoriesCommand;
import by.epam.project.controller.async.command.impl.LoadAllClientsCommand;
import by.epam.project.controller.async.command.impl.LoadAllOrdersCommand;
import by.epam.project.controller.async.command.impl.LoadAllProductsByCategoryCommand;
import by.epam.project.controller.async.command.impl.LoadProfileImageCommand;
import by.epam.project.controller.async.command.impl.LoadShoppingCartCommand;
import by.epam.project.controller.async.command.impl.RemoveCategoryCommand;
import by.epam.project.controller.async.command.impl.RemoveProductFromShoppingCartCommand;
import by.epam.project.controller.async.command.impl.RemoveProfileImageCommand;
import by.epam.project.controller.async.command.impl.SignInCommand;
import by.epam.project.controller.async.command.impl.SignUpCommand;
import by.epam.project.controller.async.command.impl.UpdateCategoryNameCommand;
import by.epam.project.controller.async.command.impl.UpdateClientStatusCommand;
import by.epam.project.controller.async.command.impl.UpdateOrderStatusCommand;
import by.epam.project.controller.async.command.impl.UpdateProductCategoryCommand;
import by.epam.project.controller.async.command.impl.UpdateProductInfoCommand;
import by.epam.project.controller.async.command.impl.UpdateProductStatusCommand;
import by.epam.project.controller.async.command.impl.UpdateProfileCommand;
import by.epam.project.controller.async.command.impl.UploadProductImageCommand;
import by.epam.project.controller.async.command.impl.UploadProfileImageCommand;

/**
 * The enum Command type.
 */
public enum CommandType {
    /**
     * The Add product to shopping cart.
     */
    ADD_PRODUCT_TO_SHOPPING_CART(new AddProductToShoppingCartCommand()),
    /**
     * The Change password by email.
     */
    CHANGE_PASSWORD_BY_EMAIL(new ChangePasswordByEmailCommand()),
    /**
     * The Change password by old password.
     */
    CHANGE_PASSWORD_BY_OLD_PASSWORD(new ChangePasswordByOldPassCommand()),
    /**
     * The Check login existence.
     */
    CHECK_LOGIN_EXISTENCE(new CheckLoginExistenceCommand()),
    /**
     * The Create category.
     */
    CREATE_CATEGORY(new CreateCategoryCommand()),
    /**
     * The Create order.
     */
    CREATE_ORDER(new CreateOrderCommand()),
    /**
     * The Create product.
     */
    CREATE_PRODUCT(new CreateProductCommand()),
    /**
     * The Load all categories.
     */
    LOAD_ALL_CATEGORIES(new LoadAllCategoriesCommand()),
    /**
     * The Load all clients.
     */
    LOAD_ALL_CLIENTS(new LoadAllClientsCommand()),
    /**
     * The Load all orders.
     */
    LOAD_ALL_ORDERS(new LoadAllOrdersCommand()),
    /**
     * The Load all products by category.
     */
    LOAD_ALL_PRODUCTS_BY_CATEGORY(new LoadAllProductsByCategoryCommand()),
    /**
     * The Load profile image.
     */
    LOAD_PROFILE_IMAGE(new LoadProfileImageCommand()),
    /**
     * The Load shopping cart.
     */
    LOAD_SHOPPING_CART(new LoadShoppingCartCommand()),
    /**
     * The Remove category.
     */
    REMOVE_CATEGORY(new RemoveCategoryCommand()),
    /**
     * The Remove product from shopping cart.
     */
    REMOVE_PRODUCT_FROM_SHOPPING_CART(new RemoveProductFromShoppingCartCommand()),
    /**
     * The Remove profile image.
     */
    REMOVE_PROFILE_IMAGE(new RemoveProfileImageCommand()),
    /**
     * The Sign in.
     */
    SIGN_IN(new SignInCommand()),
    /**
     * The Sign up.
     */
    SIGN_UP(new SignUpCommand()),
    /**
     * The Update category name.
     */
    UPDATE_CATEGORY_NAME(new UpdateCategoryNameCommand()),
    /**
     * The Update client status.
     */
    UPDATE_CLIENT_STATUS(new UpdateClientStatusCommand()),
    /**
     * The Update order status.
     */
    UPDATE_ORDER_STATUS(new UpdateOrderStatusCommand()),
    /**
     * The Update product category.
     */
    UPDATE_PRODUCT_CATEGORY(new UpdateProductCategoryCommand()),
    /**
     * The Update product info.
     */
    UPDATE_PRODUCT_INFO(new UpdateProductInfoCommand()),
    /**
     * The Update product status.
     */
    UPDATE_PRODUCT_STATUS(new UpdateProductStatusCommand()),
    /**
     * The Update profile.
     */
    UPDATE_PROFILE(new UpdateProfileCommand()),
    /**
     * The Upload product image.
     */
    UPLOAD_PRODUCT_IMAGE(new UploadProductImageCommand()),
    /**
     * The Upload profile image.
     */
    UPLOAD_PROFILE_IMAGE(new UploadProfileImageCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;

    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }
}
