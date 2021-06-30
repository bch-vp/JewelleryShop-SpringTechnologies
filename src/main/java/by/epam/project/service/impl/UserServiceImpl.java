package by.epam.project.service.impl;

import by.epam.project.builder.UserBuilder;
import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.parameter.ContentKey;
import by.epam.project.controller.parameter.ErrorKey;
import by.epam.project.controller.sync.command.CommandType;
import by.epam.project.entity.User;
import by.epam.project.exception.ServiceException;
import by.epam.project.repository.UserRepository;
import by.epam.project.security.ApplicationUserRole;
import by.epam.project.service.UserService;
import by.epam.project.util.ContentUtil;
import by.epam.project.util.EncryptPasswordUtil;
import by.epam.project.util.ImageUtil;
import by.epam.project.util.JsonUtil;
import by.epam.project.util.MailSenderUtil;
import by.epam.project.util.UniqueSixDigitKeyUtil;
import by.epam.project.validator.ServiceValidator;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
import static by.epam.project.controller.parameter.ContentKey.INFO_CHANGING_PASSWORD_EMAIL_CONFIRMATION;
import static by.epam.project.controller.parameter.ErrorKey.EMAIL_NOT_UNIQUE;
import static by.epam.project.controller.parameter.ErrorKey.ERROR;
import static by.epam.project.controller.parameter.ErrorKey.LOGIN_NOT_UNIQUE;
import static by.epam.project.controller.parameter.Parameter.UNIQUE_KEY;
import static by.epam.project.controller.parameter.Parameter.URL;
import static by.epam.project.controller.parameter.Parameter.USERS;
import static by.epam.project.service.impl.ImageCriterion.FILES_COUNT;
import static by.epam.project.service.impl.ImageCriterion.FILE_MAX_SIZE;
import static by.epam.project.service.impl.ImageCriterion.FILE_TYPE;
import static by.epam.project.service.impl.ImageCriterion.FIRST;


/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private static final int MAX_TIMER_SEC = 300;
    private static final int MILLISECONDS_PER_SECOND = 1000;

    private UserServiceImpl() {
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByLogin(login);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Unknown user by login: " + login);
        }
        User user = userOptional.get();

        String role = user.getRole().name();
        String[] authorities = ApplicationUserRole.valueOf(role).getPermissions().toArray(String[]::new);

        String password = userRepository.findPasswordByLogin(login).get();
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(login)
                .password(password)
                .roles(user.getRole().name())
                .authorities(authorities)
                .build();
        return userDetails;
    }

    @Override
    public AjaxData uploadUserImage(String userLogin, List<FileItem> fileItems, String language) throws ServiceException, IOException {
        AjaxData ajaxData = new AjaxData();

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        if (!ServiceValidator.isNameCorrect(userLogin)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

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

        Optional<String> URLOptional = userRepository.findAvatarURLByLogin(userLogin);
        if (URLOptional.isPresent()) {
            String avatarURL = URLOptional.get();
            ImageUtil.remove(avatarURL);
        }

        String fileURL = ImageUtil.save(file);
        userRepository.updateAvatarURLByLogin(fileURL, userLogin);
        JsonUtil.writeJsonToAjaxData(ajaxData, URL, fileURL);

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
        if (userRepository.findByLogin(newLogin).isPresent() && !user.getLogin().equals(newLogin)) {
            String error = ContentUtil.getWithLocale(language, ContentKey.ERROR_SIGN_UP_LOGIN_NOT_UNIQUE);
            JsonUtil.addNodeToJsonTree(jsonTree, LOGIN_NOT_UNIQUE, error, ERROR);
        }
        if (userRepository.findByTelephoneNumber(newTelephoneNumber).isPresent() &&
                !user.getTelephoneNumber().equals(newTelephoneNumber)) {
            String error = ContentUtil.getWithLocale(language,
                    ContentKey.ERROR_SIGN_UP_TELEPHONE_NUMBER_NOT_UNIQUE);
            JsonUtil.addNodeToJsonTree(jsonTree, ErrorKey.TELEPHONE_NUMBER_NOT_UNIQUE, error, ERROR);
        }
        if (userRepository.findByEmail(newEmail).isPresent() && !user.getEmail().equals(newEmail)) {
            String error = ContentUtil.getWithLocale(language, ContentKey.ERROR_SIGN_UP_EMAIL_NOT_UNIQUE);
            JsonUtil.addNodeToJsonTree(jsonTree, EMAIL_NOT_UNIQUE, error, ERROR);
        }

        if (!jsonTree.path(ERROR).isEmpty()) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.writeJsonTreeToResponse(ajaxData, jsonTree);
            return ajaxData;
        }

        User newUser = UserBuilder.builder()
                .setId(user.getId())
                .setLogin(newLogin)
                .setFirstName(newFirstName)
                .setLastName(newLastName)
                .setTelephoneNumber(newTelephoneNumber)
                .setEmail(newEmail)
                .setRole(user.getRole())
                .setStatus(user.getStatus())
                .build();

        userRepository.save(newUser);

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

        if (userRepository.findByLogin(login).isPresent()) {
            String error = ContentUtil.getWithLocale(language, ContentKey.ERROR_SIGN_UP_LOGIN_NOT_UNIQUE);
            JsonUtil.addNodeToJsonTree(jsonTree, LOGIN_NOT_UNIQUE, error, ERROR);
        }
        if (userRepository.findByTelephoneNumber(telephoneNumber).isPresent()) {
            String error = ContentUtil.getWithLocale(language,
                    ContentKey.ERROR_SIGN_UP_TELEPHONE_NUMBER_NOT_UNIQUE);
            JsonUtil.addNodeToJsonTree(jsonTree, ErrorKey.TELEPHONE_NUMBER_NOT_UNIQUE, error, ERROR);
        }
        if (userRepository.findByEmail(email).isPresent()) {
            String error = ContentUtil.getWithLocale(language, ContentKey.ERROR_SIGN_UP_EMAIL_NOT_UNIQUE);
            JsonUtil.addNodeToJsonTree(jsonTree, EMAIL_NOT_UNIQUE, error, ERROR);
        }
        if (!jsonTree.path(ERROR).isEmpty()) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.writeJsonTreeToResponse(ajaxData, jsonTree);
            return ajaxData;
        }

        User user = UserBuilder.builder()
                .setLogin(login)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setTelephoneNumber(telephoneNumber)
                .setEmail(email)
                .setRole(User.Role.CLIENT)
                .setStatus(User.Status.NOT_ACTIVATED)
                .build();

        String encryptPassword = passwordEncoder.encode(password);
        userRepository.updatePasswordByLogin(encryptPassword, login);

        String emailSubjectWithLocale = ContentUtil.getWithLocale(language,
                ContentKey.EMAIL_SUBJECT_ACTIVATION_SIGN_UP);
        String emailBodyWithLocale = ContentUtil.getWithLocale(language,
                ContentKey.EMAIL_BODY_ACTIVATION_SIGN_UP);

        String command = CommandType.CONFIRM_SIGN_UP.toString().toLowerCase();
        MailSenderUtil.sendActivationEmail(user, emailSubjectWithLocale,
                emailBodyWithLocale, confirmationLink, command);

        ajaxData.setStatusHttp(HttpServletResponse.SC_CREATED);

        return ajaxData;
    }

    @Override
    public AjaxData changePasswordByOldPassword(User user, String oldPassword, String newPassword,
                                                String language) throws ServiceException, IOException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isPasswordCorrect(oldPassword)
                || !ServiceValidator.isPasswordCorrect(newPassword)
                || oldPassword.equals(newPassword)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        String encryptOldPassword = EncryptPasswordUtil.encryption(oldPassword);

        Optional<String> userPasswordOptional = userRepository.findPasswordByLogin(user.getLogin());
        if (!encryptOldPassword.equals(userPasswordOptional.get())) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
            JsonUtil.writeJsonToAjaxData(ajaxData,
                    ERROR, ERROR_PROFILE_OLD_PASSWORD_NOT_EQUAL_LOGIN_PASSWORD, language);
            return ajaxData;
        }

        userRepository.updatePasswordByLogin(user.getLogin(), EncryptPasswordUtil.encryption(newPassword));

        return ajaxData;
    }

    @Override
    public AjaxData changePasswordByEmail(String login, String newPassword, String email, String sessionUniqueKey,
                                          String requestUniqueKey, String timeCreated, String language) throws ServiceException, IOException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isLoginCorrect(login)
                || !ServiceValidator.isEmailCorrect(email)
                || !ServiceValidator.isPasswordCorrect(newPassword)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        Optional<User> userOptional = userRepository.findByLogin(login);
        if (userOptional.isEmpty()) {
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
        userRepository.updatePasswordByLogin(login, EncryptPasswordUtil.encryption(newPassword));

        return ajaxData;
    }

    @Override
    public AjaxData removeUserImage(String login) throws ServiceException, IOException {
        AjaxData ajaxData = new AjaxData();

        Optional<String> URLOptional = userRepository.findAvatarURLByLogin(login);
        if (URLOptional.isEmpty()) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
            return ajaxData;
        }

        String avatarURL = URLOptional.get();
        ImageUtil.remove(avatarURL);
        userRepository.removeAvatarUrlByLogin(login);

        return ajaxData;
    }

    @Override
    public AjaxData findUserImage(String login) throws ServiceException, IOException {
        AjaxData ajaxData = new AjaxData();

        Optional<String> URLOptional = userRepository.findAvatarURLByLogin(login);
        if (URLOptional.isEmpty()) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
            return ajaxData;
        }

        String avatarURL = URLOptional.get();
        JsonUtil.writeJsonToAjaxData(ajaxData, URL, avatarURL);
        return ajaxData;
    }

    @Override
    public AjaxData checkLoginExistence(String login) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isLoginCorrect(login)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        Optional<User> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
            return ajaxData;
        }

        return ajaxData;
    }


    @Override
    public AjaxData findAllClients() throws ServiceException, IOException {
        AjaxData ajaxData = new AjaxData();

        List<User> users = userRepository.findAllClients();
        String json = JsonUtil.toJson(USERS, users);
        ajaxData.setJson(json);

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
        int idStatus = Integer.parseInt(idStatusString);

        String status;
        try {
            status = User.Status.values()[idStatus].name();
        } catch (IndexOutOfBoundsException ex) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
            return ajaxData;
        }

        boolean isUpdated = userRepository.updateStatusById(status, idUser);
        if (!isUpdated) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
        }

        return ajaxData;
    }

    @Override
    public boolean updateActivationStatusByLogin(String login, User.Status status) throws ServiceException {
        boolean isUpdated;

        if (!ServiceValidator.isLoginCorrect(login)) {
            return false;
        }

        isUpdated = userRepository.updateStatusByLogin(login, status.name());

        return isUpdated;
    }
}
