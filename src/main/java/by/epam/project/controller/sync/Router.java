package by.epam.project.controller.sync;

import by.epam.project.controller.parameter.PagePath;

/**
 * The type Router.
 */
public class Router {
    /**
     * The enum Type.
     */
    public enum Type {
        /**
         * Forward type.
         */
        FORWARD,
        /**
         * Redirect type.
         */
        REDIRECT;
    }

    private Type currentType = Type.FORWARD;
    private String currentPage = PagePath.PASSING_BY_GUEST;

    /**
     * Instantiates a new Router.
     */
    public Router() {
    }

    /**
     * Instantiates a new Router.
     *
     * @param currentPage the current page
     */
    public Router(String currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Instantiates a new Router.
     *
     * @param currentType the current type
     * @param currentPage the current page
     */
    public Router(Type currentType, String currentPage) {
        this.currentType = currentType;
        this.currentPage = currentPage;
    }

    /**
     * Gets current type.
     *
     * @return the current type
     */
    public Type getCurrentType() {
        return currentType;
    }

    /**
     * Sets redirect.
     */
    public void setRedirect() {
        this.currentType = Type.REDIRECT;
    }

    /**
     * Gets current page.
     *
     * @return the current page
     */
    public String getCurrentPage() {
        return currentPage;
    }

    /**
     * Sets current page.
     *
     * @param currentPage the current page
     */
    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
}

