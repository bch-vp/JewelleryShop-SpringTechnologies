package by.epam.project.service;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.entity.User;
import org.apache.commons.fileupload.FileItem;

import java.io.IOException;
import java.util.List;

public interface UserService {
    AjaxData uploadUserImage(String userLogin, List<FileItem> fileItems, String language) throws IOException;

    AjaxData updateProfile(User user, String newLogin, String newFirstName, String newLastName,
                           String newTelephoneNumber, String newEmail, String language);

    AjaxData signUp(String login, String password, String firstName, String lastName, String telephoneNumber,
                    String email, String confirmationLink, String language);

    AjaxData changePasswordByOldPassword(User user, String oldPassword, String newPassword,
                                         String language) throws IOException;

    AjaxData changePasswordByEmail(String login, String newPassword, String email, String sessionUniqueKey,
                                   String requestUniqueKey, String timeCreated, String language) throws IOException;

    AjaxData findUserImage(String login) throws IOException;

    AjaxData removeUserImage(String login) throws IOException;

    AjaxData findAllClients() throws IOException;

    AjaxData checkLoginExistence(String login);

    AjaxData updateClientStatus(String idUserString, String idStatusString);
}
