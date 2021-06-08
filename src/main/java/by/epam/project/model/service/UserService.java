package by.epam.project.model.service;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.exception.ServiceException;
import by.epam.project.model.entity.Product;
import by.epam.project.model.entity.User;
import org.apache.commons.fileupload.FileItem;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Upload user image ajax data.
     *
     * @param userLogin the user login
     * @param fileItems the file items
     * @param language  the language
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData uploadUserImage(String userLogin, List<FileItem> fileItems, String language) throws ServiceException;

    /**
     * Update profile ajax data.
     *
     * @param user               the user
     * @param newLogin           the new login
     * @param newFirstName       the new first name
     * @param newLastName        the new last name
     * @param newTelephoneNumber the new telephone number
     * @param newEmail           the new email
     * @param language           the language
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData updateProfile(User user, String newLogin, String newFirstName, String newLastName,
                           String newTelephoneNumber, String newEmail, String language) throws ServiceException;

    /**
     * Sign in ajax data.
     *
     * @param login    the login
     * @param password the password
     * @param language the language
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData signIn(String login, String password, String language) throws ServiceException;

    /**
     * Sign up ajax data.
     *
     * @param login            the login
     * @param password         the password
     * @param firstName        the first name
     * @param lastName         the last name
     * @param telephoneNumber  the telephone number
     * @param email            the email
     * @param confirmationLink the confirmation link
     * @param language         the language
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData signUp(String login, String password, String firstName, String lastName, String telephoneNumber,
                    String email, String confirmationLink, String language) throws ServiceException;

    /**
     * Change password by old password ajax data.
     *
     * @param user        the user
     * @param oldPassword the old password
     * @param newPassword the new password
     * @param language    the language
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData changePasswordByOldPassword(User user, String oldPassword, String newPassword,
                                         String language) throws ServiceException;

    /**
     * Change password by email ajax data.
     *
     * @param login            the login
     * @param newPassword      the new password
     * @param email            the email
     * @param sessionUniqueKey the session unique key
     * @param requestUniqueKey the request unique key
     * @param timeCreated      the time created
     * @param language         the language
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData changePasswordByEmail(String login, String newPassword, String email, String sessionUniqueKey,
                                   String requestUniqueKey, String timeCreated, String language) throws ServiceException;

    /**
     * Find user image ajax data.
     *
     * @param login the login
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData findUserImage(String login) throws ServiceException;

    /**
     * Remove user image ajax data.
     *
     * @param login the login
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData removeUserImage(String login) throws ServiceException;

    /**
     * Create order ajax data.
     *
     * @param user         the user
     * @param shoppingCart the shopping cart
     * @param orderAddress the order address
     * @param orderComment the order comment
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData createOrder(User user, List<Product> shoppingCart, String orderAddress,
                         String orderComment) throws ServiceException;

    /**
     * Find all orders ajax data.
     *
     * @param user the user
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData findAllOrders(User user) throws ServiceException;

    /**
     * Find all clients ajax data.
     *
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData findAllClients() throws ServiceException;

    /**
     * Check login existence ajax data.
     *
     * @param login the login
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData checkLoginExistence(String login) throws ServiceException;

    /**
     * Update client status ajax data.
     *
     * @param idUserString   the id user string
     * @param idStatusString the id status string
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData updateClientStatus(String idUserString, String idStatusString) throws ServiceException;

    /**
     * Update order status ajax data.
     *
     * @param idOrderString  the id order string
     * @param idStatusString the id status string
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData updateOrderStatus(String idOrderString, String idStatusString) throws ServiceException;

    /**
     * Update activation status by login boolean.
     *
     * @param login  the login
     * @param status the status
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateActivationStatusByLogin(String login, User.Status status) throws ServiceException;
}
