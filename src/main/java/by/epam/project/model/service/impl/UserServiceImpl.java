package by.epam.project.model.service.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.parameter.ContentKey;
import by.epam.project.controller.parameter.ErrorKey;
import by.epam.project.controller.sync.command.CommandType;
import by.epam.project.exception.DaoException;
import by.epam.project.exception.ServiceException;
import by.epam.project.model.dao.ProductDao;
import by.epam.project.model.dao.UserDao;
import by.epam.project.model.dao.impl.ProductDaoImpl;
import by.epam.project.model.dao.impl.UserDaoImpl;
import by.epam.project.model.entity.Order;
import by.epam.project.model.entity.Product;
import by.epam.project.model.entity.User;
import by.epam.project.model.service.UserService;
import by.epam.project.util.ContentUtil;
import by.epam.project.util.EncryptPasswordUtil;
import by.epam.project.util.ImageUtil;
import by.epam.project.util.JsonUtil;
import by.epam.project.util.MailSenderUtil;
import by.epam.project.util.UniqueSixDigitKeyUtil;
import by.epam.project.validator.ServiceValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static by.epam.project.controller.parameter.ContentKey.ERROR_CHANGING_PASSWORD_EMAIL_INCORRECT;
import static by.epam.project.controller.parameter.ContentKey.ERROR_CHANGING_PASSWORD_GUEST_TIME_EXPIRED;
import static by.epam.project.controller.parameter.ContentKey.ERROR_CHANGING_PASSWORD_UNIQUE_KEY_INCORRECT;
import static by.epam.project.controller.parameter.ContentKey.ERROR_PROFILE_AVATAR_COUNT_ALLOWED_FILES;
import static by.epam.project.controller.parameter.ContentKey.ERROR_PROFILE_AVATAR_FORMAT;
import static by.epam.project.controller.parameter.ContentKey.ERROR_PROFILE_AVATAR_FORM_FIELD;
import static by.epam.project.controller.parameter.ContentKey.ERROR_PROFILE_AVATAR_MAX_SIZE;
import static by.epam.project.controller.parameter.ContentKey.ERROR_PROFILE_OLD_PASSWORD_NOT_EQUAL_LOGIN_PASSWORD;
import static by.epam.project.controller.parameter.ContentKey.ERROR_SIGN_IN_BANNED;
import static by.epam.project.controller.parameter.ContentKey.ERROR_SIGN_IN_NOT_ACTIVATED;
import static by.epam.project.controller.parameter.ContentKey.INFO_CHANGING_PASSWORD_EMAIL_CONFIRMATION;
import static by.epam.project.controller.parameter.ErrorKey.EMAIL_NOT_UNIQUE;
import static by.epam.project.controller.parameter.ErrorKey.ERROR;
import static by.epam.project.controller.parameter.ErrorKey.LOGIN_NOT_UNIQUE;
import static by.epam.project.controller.parameter.Parameter.EMAIL;
import static by.epam.project.controller.parameter.Parameter.INFO;
import static by.epam.project.controller.parameter.Parameter.LOGIN;
import static by.epam.project.controller.parameter.Parameter.PASSING_BY_ADMIN;
import static by.epam.project.controller.parameter.Parameter.PASSING_BY_CLIENT;
import static by.epam.project.controller.parameter.Parameter.PRODUCTS;
import static by.epam.project.controller.parameter.Parameter.TELEPHONE_NUMBER;
import static by.epam.project.controller.parameter.Parameter.UNIQUE_KEY;
import static by.epam.project.controller.parameter.Parameter.URL;
import static by.epam.project.controller.parameter.Parameter.USER;
import static by.epam.project.controller.parameter.Parameter.USERS;
import static by.epam.project.model.service.impl.ImageCriterion.FILES_COUNT;
import static by.epam.project.model.service.impl.ImageCriterion.FILE_MAX_SIZE;
import static by.epam.project.model.service.impl.ImageCriterion.FILE_TYPE;
import static by.epam.project.model.service.impl.ImageCriterion.FIRST;


/**
 * The type User service.
 */
public class UserServiceImpl implements UserService {
    private static final UserServiceImpl instance = new UserServiceImpl();
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final ProductDao productDao = ProductDaoImpl.getInstance();

    private static final int MAX_TIMER_SEC = 300;
    private static final int MILLISECONDS_PER_SECOND = 1000;

    private UserServiceImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public AjaxData uploadUserImage(String userLogin, List<FileItem> fileItems, String language) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        if (!ServiceValidator.isNameCorrect(userLogin)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        try {
            if (fileItems.size() != FILES_COUNT) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_PROFILE_AVATAR_COUNT_ALLOWED_FILES, language);
                return ajaxData;
            }

            FileItem file = fileItems.get(FIRST);
            if (file.getSize() > FILE_MAX_SIZE) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_PROFILE_AVATAR_MAX_SIZE, language);
                return ajaxData;
            }

            if (file.isFormField()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_PROFILE_AVATAR_FORM_FIELD, language);
                return ajaxData;
            }

            String contentType = file.getContentType();
            if (!FILE_TYPE.contains(contentType)) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_PROFILE_AVATAR_FORMAT, language);
                return ajaxData;
            }

            Optional<String> URLOptional = userDao.findAvatarURLByLogin(userLogin);
            if (URLOptional.isPresent()) {
                String avatarURL = URLOptional.get();
                ImageUtil.remove(avatarURL);
            }

            String fileURL = ImageUtil.save(file);
            userDao.updateAvatarURLByLogin(userLogin, fileURL);
            JsonUtil.writeJsonToAjaxData(ajaxData, URL, fileURL);
        } catch (DaoException | IOException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData updateProfile(User user, String newLogin, String newFirstName, String newLastName,
                                  String newTelephoneNumber, String newEmail, String language) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isLoginCorrect(newLogin)
                || !ServiceValidator.isFirstNameCorrect(newFirstName)
                || !ServiceValidator.isLastNameCorrect(newLastName)
                || !ServiceValidator.isPhoneCorrect(newTelephoneNumber)
                || !ServiceValidator.isEmailCorrect(newEmail)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        JsonNode jsonTree = JsonUtil.createJsonTree(ERROR);
        try {
            if (userDao.findByLogin(newLogin).isPresent() && !user.getLogin().equals(newLogin)) {
                String error = ContentUtil.getWithLocale(language, ContentKey.ERROR_SIGN_UP_LOGIN_NOT_UNIQUE);
                JsonUtil.addNodeToJsonTree(jsonTree, LOGIN_NOT_UNIQUE, error, ERROR);
            }
            if (userDao.findByTelephoneNumber(newTelephoneNumber).isPresent() &&
                    !user.getTelephoneNumber().equals(newTelephoneNumber)) {
                String error = ContentUtil.getWithLocale(language,
                        ContentKey.ERROR_SIGN_UP_TELEPHONE_NUMBER_NOT_UNIQUE);
                JsonUtil.addNodeToJsonTree(jsonTree, ErrorKey.TELEPHONE_NUMBER_NOT_UNIQUE, error, ERROR);
            }
            if (userDao.findByEmail(newEmail).isPresent() && !user.getEmail().equals(newEmail)) {
                String error = ContentUtil.getWithLocale(language, ContentKey.ERROR_SIGN_UP_EMAIL_NOT_UNIQUE);
                JsonUtil.addNodeToJsonTree(jsonTree, EMAIL_NOT_UNIQUE, error, ERROR);
            }

            if (!jsonTree.path(ERROR).isEmpty()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonTreeToResponse(ajaxData, jsonTree);
                return ajaxData;
            }

            User newUser = new User(user.getId(), newLogin, newFirstName, newLastName, newTelephoneNumber,
                    newEmail, user.getRole(), user.getStatus());
            userDao.updateUser(newUser, user.getLogin());
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData signIn(String login, String password, String language) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isLoginCorrect(login)
                || !ServiceValidator.isPasswordCorrect(password)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        String encryptPassword = EncryptPasswordUtil.encryption(password);

        Optional<User> userOptional;
        try {
            userOptional = userDao.findByLoginAndPassword(login, encryptPassword);


            if (userOptional.isEmpty()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
                return ajaxData;
            }

            User user = userOptional.get();
            if (user.getStatus().equals(User.Status.BANNED)) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_SIGN_IN_BANNED, language);
                return ajaxData;
            }

            if (!user.getStatus().equals(User.Status.ACTIVATED)) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_SIGN_IN_NOT_ACTIVATED, language);
                return ajaxData;
            }

            User.Role role = user.getRole();
            if (role == User.Role.CLIENT) {
                JsonUtil.writeJsonToAjaxData(ajaxData, INFO, PASSING_BY_CLIENT);
            } else {
                JsonUtil.writeJsonToAjaxData(ajaxData, INFO, PASSING_BY_ADMIN);
            }
            ajaxData.putDataToDataSession(USER, user);
        } catch (DaoException | IOException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData signUp(String login, String password, String firstName, String lastName, String telephoneNumber,
                           String email, String confirmationLink, String language) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isLoginCorrect(login)
                || !ServiceValidator.isPasswordCorrect(password)
                || !ServiceValidator.isFirstNameCorrect(firstName)
                || !ServiceValidator.isLastNameCorrect(lastName)
                || !ServiceValidator.isPhoneCorrect(telephoneNumber)
                || !ServiceValidator.isEmailCorrect(email)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        JsonNode jsonTree = JsonUtil.createJsonTree(ERROR);
        try {
            if (userDao.findByLogin(login).isPresent()) {
                String error = ContentUtil.getWithLocale(language, ContentKey.ERROR_SIGN_UP_LOGIN_NOT_UNIQUE);
                JsonUtil.addNodeToJsonTree(jsonTree, LOGIN_NOT_UNIQUE, error, ERROR);
            }
            if (userDao.findByTelephoneNumber(telephoneNumber).isPresent()) {
                String error = ContentUtil.getWithLocale(language,
                        ContentKey.ERROR_SIGN_UP_TELEPHONE_NUMBER_NOT_UNIQUE);
                JsonUtil.addNodeToJsonTree(jsonTree, ErrorKey.TELEPHONE_NUMBER_NOT_UNIQUE, error, ERROR);
            }
            if (userDao.findByEmail(email).isPresent()) {
                String error = ContentUtil.getWithLocale(language, ContentKey.ERROR_SIGN_UP_EMAIL_NOT_UNIQUE);
                JsonUtil.addNodeToJsonTree(jsonTree, EMAIL_NOT_UNIQUE, error, ERROR);
            }
            if (!jsonTree.path(ERROR).isEmpty()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonTreeToResponse(ajaxData, jsonTree);
                return ajaxData;
            }

            User user = new User(login, firstName, lastName, telephoneNumber, email,
                    User.Role.CLIENT, User.Status.NOT_ACTIVATED);
            String encryptPassword = EncryptPasswordUtil.encryption(password);
            userDao.add(user, encryptPassword);

            String emailSubjectWithLocale = ContentUtil.getWithLocale(language,
                    ContentKey.EMAIL_SUBJECT_ACTIVATION_SIGN_UP);
            String emailBodyWithLocale = ContentUtil.getWithLocale(language,
                    ContentKey.EMAIL_BODY_ACTIVATION_SIGN_UP);

            String command = CommandType.CONFIRM_SIGN_UP.toString().toLowerCase();
            MailSenderUtil.sendActivationEmail(user, emailSubjectWithLocale,
                    emailBodyWithLocale, confirmationLink, command);

            ajaxData.setStatusHttp(HttpServletResponse.SC_CREATED);
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData changePasswordByOldPassword(User user, String oldPassword, String newPassword,
                                                String language) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isPasswordCorrect(oldPassword)
                || !ServiceValidator.isPasswordCorrect(newPassword)
                || oldPassword.equals(newPassword)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        String encryptOldPassword = EncryptPasswordUtil.encryption(oldPassword);
        try {
            Optional<String> userPasswordOptional = userDao.findPasswordByLogin(user.getLogin());
            if (!encryptOldPassword.equals(userPasswordOptional.get())) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
                JsonUtil.writeJsonToAjaxData(ajaxData,
                        ERROR, ERROR_PROFILE_OLD_PASSWORD_NOT_EQUAL_LOGIN_PASSWORD, language);
                return ajaxData;
            }

            userDao.updatePasswordByLogin(user.getLogin(), EncryptPasswordUtil.encryption(newPassword));
        } catch (DaoException | IOException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData changePasswordByEmail(String login, String newPassword, String email, String sessionUniqueKey,
                                          String requestUniqueKey, String timeCreated, String language) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isLoginCorrect(login)
                || !ServiceValidator.isEmailCorrect(email)
                || !ServiceValidator.isPasswordCorrect(newPassword)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        try {
            Optional<User> userOptional = userDao.findByLogin(login);
            if(userOptional.isEmpty()){
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
                return ajaxData;
            }

            User user = userOptional.get();
            if (!user.getEmail().equals(email)) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_CHANGING_PASSWORD_EMAIL_INCORRECT, language);
                return ajaxData;
            }

            if (sessionUniqueKey == null || sessionUniqueKey.isEmpty()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_UNAUTHORIZED);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, INFO_CHANGING_PASSWORD_EMAIL_CONFIRMATION, language);

                int key = UniqueSixDigitKeyUtil.generate();
                String uniqueKey = String.valueOf(key);

                ajaxData.putDataToDataSession(UNIQUE_KEY, uniqueKey);

                String emailSubjectWithLocale = ContentUtil.getWithLocale(language,
                        ContentKey.EMAIL_SUBJECT_GUEST_CHANGING_PASSWORD);
                String emailBodyWithLocale = ContentUtil.getWithLocale(language,
                        ContentKey.EMAIL_BODY_GUEST_CHANGING_PASSWORD);

                MailSenderUtil.sendConfirmationChangingPassword(user, emailSubjectWithLocale,
                        emailBodyWithLocale, uniqueKey);
                return ajaxData;
            }

            if (!ServiceValidator.isUniqueCodeCorrect(requestUniqueKey)) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                return ajaxData;
            }

            if (!requestUniqueKey.equals(sessionUniqueKey)) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_CHANGING_PASSWORD_UNIQUE_KEY_INCORRECT, language);
                return ajaxData;
            }

            long timeNow = System.currentTimeMillis();
            long diff = (timeNow - Long.parseLong(timeCreated)) / MILLISECONDS_PER_SECOND;
            boolean isTimeExpired = diff > MAX_TIMER_SEC;
            if (isTimeExpired) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_REQUEST_TIMEOUT);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_CHANGING_PASSWORD_GUEST_TIME_EXPIRED, language);
            }
            userDao.updatePasswordByLogin(login, EncryptPasswordUtil.encryption(newPassword));
        } catch (DaoException | IOException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData removeUserImage(String login) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        try {
            Optional<String> URLOptional = userDao.findAvatarURLByLogin(login);
            if (URLOptional.isEmpty()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
                return ajaxData;
            }

            String avatarURL = URLOptional.get();
            ImageUtil.remove(avatarURL);
            userDao.removeAvatarByLogin(login);
        } catch (DaoException | IOException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData findUserImage(String login) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        try {
            Optional<String> URLOptional = userDao.findAvatarURLByLogin(login);
            if (URLOptional.isEmpty()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
                return ajaxData;
            }

            String avatarURL = URLOptional.get();
            JsonUtil.writeJsonToAjaxData(ajaxData, URL, avatarURL);
        } catch (DaoException | IOException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData createOrder(User user, List<Product> shoppingCart, String orderAddress,
                                String orderComment) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isInfoCorrect(orderComment)
                || !ServiceValidator.isAddressCorrect(orderAddress)
                || shoppingCart.isEmpty()) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        BigDecimal totalPrice = shoppingCart.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order(orderComment, orderAddress, new Date(new Date().getTime()), totalPrice, Order.Status.NOT_CONFIRMED);
        try {
            userDao.createOrder(user, order, shoppingCart);
            shoppingCart.clear();
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData findAllOrders(User user) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        String json;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (user.getRole() == User.Role.CLIENT) {
                List<Order> orders = userDao.findAllOrdersToClient(user);

                ArrayNode arrayNodeOrders = objectMapper.valueToTree(orders);
                int size = orders.size();
                for (int i = 0; i < size; i++) {
                    JsonNode orderNode = arrayNodeOrders.path(i);

                    List<Product> products = productDao.findAllOrderProducts(orders.get(i));
                    ArrayNode arrayNodeProducts = objectMapper.valueToTree(products);

                    ((ObjectNode) orderNode).putArray(PRODUCTS).addAll(arrayNodeProducts);
                }
                json = arrayNodeOrders.toString();
            } else {
                List<Order> orders = userDao.findAllOrdersToAdmin();

                ArrayNode arrayNodeOrders = objectMapper.valueToTree(orders);
                int size = orders.size();
                for (int i = 0; i < size; i++) {
                    JsonNode orderNode = arrayNodeOrders.path(i);

                    Optional<User> userOrderOptional = userDao.findUserByOrderId(orders.get(i).getId());
                    User userOrder = userOrderOptional.get();

                    ((ObjectNode) orderNode).put(LOGIN, userOrder.getLogin());
                    ((ObjectNode) orderNode).put(TELEPHONE_NUMBER, userOrder.getTelephoneNumber());
                    ((ObjectNode) orderNode).put(EMAIL, userOrder.getEmail());

                    List<Product> products = productDao.findAllOrderProducts(orders.get(i));
                    ArrayNode arrayNodeProducts = objectMapper.valueToTree(products);

                    ((ObjectNode) orderNode).putArray(PRODUCTS).addAll(arrayNodeProducts);
                }
                json = arrayNodeOrders.toString();
            }
            ajaxData.setJson(json);
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData checkLoginExistence(String login) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isLoginCorrect(login)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        try {
            Optional<User> user = userDao.findByLogin(login);
            if (user.isEmpty()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
                return ajaxData;
            }
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }


    @Override
    public AjaxData findAllClients() throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        List<User> users = null;
        try {
            users = userDao.findAllClients();
            String json = JsonUtil.toJson(USERS, users);
            ajaxData.setJson(json);
        } catch (DaoException | IOException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData updateClientStatus(String idUserString, String idStatusString) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isIdCorrect(idUserString)
                || !ServiceValidator.isIdCorrect(idStatusString)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        long idUser = Long.parseLong(idUserString);
        long idStatus = Long.parseLong(idStatusString);

        try {
            Optional<User.Status> categoryOptional = userDao.findStatusById(idStatus);
            if (categoryOptional.isEmpty()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
                return ajaxData;
            }

            boolean isUpdated = userDao.updateUserStatus(idUser, idStatus);
            if (!isUpdated) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData updateOrderStatus(String idOrderString, String idStatusString) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isIdCorrect(idOrderString)
                || !ServiceValidator.isIdCorrect(idStatusString)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        long idOrder = Long.parseLong(idOrderString);
        long idStatus = Long.parseLong(idStatusString);

        try {
            Optional<Order.Status> statusOptional = userDao.findOrderStatusById(idStatus);
            if (statusOptional.isEmpty()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
                return ajaxData;
            }

            boolean isUpdated = userDao.updateOrderStatusById(idOrder, idStatus);
            if (!isUpdated) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public boolean updateActivationStatusByLogin(String login, User.Status status) throws ServiceException {
        boolean isUpdated;

        if (!ServiceValidator.isLoginCorrect(login)) {
            return false;
        }

        try {
            isUpdated = userDao.updateActivationStatusByLogin(login, status);
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return isUpdated;
    }
}
