package by.epam.project.model.dao;

import by.epam.project.exception.DaoException;
import by.epam.project.model.entity.Order;
import by.epam.project.model.entity.Product;
import by.epam.project.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface User dao.
 */
public interface UserDao {
    /**
     * Add boolean.
     *
     * @param user     the user
     * @param password the password
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean add(User user, String password) throws DaoException;

    /**
     * Update password by login boolean.
     *
     * @param login    the login
     * @param password the password
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updatePasswordByLogin(String login, String password) throws DaoException;

    /**
     * Update user boolean.
     *
     * @param newUser  the new user
     * @param oldLogin the old login
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateUser(User newUser, String oldLogin) throws DaoException;

    /**
     * Update avatar url by login boolean.
     *
     * @param login   the login
     * @param fileURL the file url
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateAvatarURLByLogin(String login, String fileURL) throws DaoException;

    /**
     * Update activation status by login boolean.
     *
     * @param login  the login
     * @param status the status
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateActivationStatusByLogin(String login, User.Status status) throws DaoException;

    /**
     * Update user status boolean.
     *
     * @param idUser   the id user
     * @param idStatus the id status
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserStatus(long idUser, long idStatus) throws DaoException;

    /**
     * Find all users list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findAllUsers() throws DaoException;

    /**
     * Find all clients list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findAllClients() throws DaoException;

    /**
     * Find by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByLogin(String login) throws DaoException;

    /**
     * Find by telephone number optional.
     *
     * @param phone the phone
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByTelephoneNumber(String phone) throws DaoException;

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByEmail(String email) throws DaoException;

    /**
     * Find user by order id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByOrderId(long id) throws DaoException;

    /**
     * Find status by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User.Status> findStatusById(long id) throws DaoException;

    /**
     * Find avatar url by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<String> findAvatarURLByLogin(String login) throws DaoException;

    /**
     * Find password by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<String> findPasswordByLogin(String login) throws DaoException;

    /**
     * Find by login and password optional.
     *
     * @param login    the login
     * @param password the password
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByLoginAndPassword(String login, String password) throws DaoException;

    /**
     * Remove avatar by login boolean.
     *
     * @param login the login
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean removeAvatarByLogin(String login) throws DaoException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findById(long id) throws DaoException;

    /**
     * Create order boolean.
     *
     * @param user     the user
     * @param order    the order
     * @param products the products
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean createOrder(User user, Order order, List<Product> products) throws DaoException;

    /**
     * Find all orders to client list.
     *
     * @param user the user
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Order> findAllOrdersToClient(User user) throws DaoException;

    /**
     * Find all orders to admin list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Order> findAllOrdersToAdmin() throws DaoException;

    /**
     * Find order by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Order> findOrderById(long id) throws DaoException;

    /**
     * Update order status by id boolean.
     *
     * @param idOrder  the id order
     * @param idStatus the id status
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateOrderStatusById(long idOrder, long idStatus) throws DaoException;

    /**
     * Find order status by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Order.Status> findOrderStatusById(long id) throws DaoException;
}
