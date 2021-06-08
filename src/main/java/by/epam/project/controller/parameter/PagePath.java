package by.epam.project.controller.parameter;

/**
 * The type Page path.
 */
public class PagePath {

    /**
     * The constant PASSING_BY_GUEST.
     */
    public final static String PASSING_BY_GUEST = "/WEB-INF/jsp/role/guest.jsp";
    /**
     * The constant PASSING_BY_CLIENT.
     */
    public final static String PASSING_BY_CLIENT = "/WEB-INF/jsp/role/client.jsp";
    /**
     * The constant PASSING_BY_ADMIN.
     */
    public final static String PASSING_BY_ADMIN = "/WEB-INF/jsp/role/admin.jsp";

    /**
     * The constant ERROR_403.
     */
    public final static String ERROR_403 = "/jsp/error403.jsp";
    /**
     * The constant ERROR_404.
     */
    public final static String ERROR_404 = "/jsp/error404.jsp";
    /**
     * The constant ERROR_500.
     */
    public final static String ERROR_500 = "/jsp/error500.jsp";

    /**
     * The constant NOTIFICATION_SUCCESS.
     */
    public final static String NOTIFICATION_SUCCESS = "/jsp/notificationSuccess.jsp";

    private PagePath() {
    }
}
