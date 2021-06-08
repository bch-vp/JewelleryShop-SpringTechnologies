package by.epam.project.model.service.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.exception.DaoException;
import by.epam.project.exception.ServiceException;
import by.epam.project.model.dao.UserDao;
import by.epam.project.model.entity.User;
import by.epam.project.model.service.UserService;
import org.mockito.Mockito;
import org.powermock.reflect.internal.WhiteboxImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.testng.Assert.assertEquals;

/**
 * The type User service impl test.
 */
public class UserServiceImplTest {
    /**
     * The User service.
     */
    UserService userService;
    /**
     * The Mock.
     */
    UserDao mock;

    /**
     * Sets up.
     */
    @BeforeMethod
    public void setUp() {
        mock = Mockito.mock(UserDao.class);
        userService = UserServiceImpl.getInstance();
        WhiteboxImpl.setInternalState(userService, "userDao", mock);
    }

    /**
     * Sign in data object [ ] [ ].
     *
     * @return the object [ ] [ ]
     */
    @DataProvider(name = "signInData")
    public Object[][] signInData() {
        return new Object[][]{
                {new AjaxData(SC_OK),
                        Optional.of(new User("ilya_user", User.Status.ACTIVATED, User.Role.CLIENT)),
                        "ilya_user", "Aa123", "en"},
                {new AjaxData(SC_BAD_REQUEST),
                        Optional.of(new User("ilya_user", User.Status.NOT_ACTIVATED, User.Role.CLIENT)),
                        "ilya_user", "Aa123", "en"},
                {new AjaxData(SC_BAD_REQUEST),
                        Optional.of(new User("ilya_user", User.Status.BANNED, User.Role.CLIENT)),
                        "ilya_user", "Aa123", "en"},
                {new AjaxData(SC_NOT_FOUND), Optional.empty(), "ilya_user", "Aa123", "en"}
        };
    }

    /**
     * Test sign in.
     *
     * @param ajaxDataExpected the ajax data expected
     * @param userOptional     the user optional
     * @param userArguments    the user arguments
     * @throws DaoException     the dao exception
     * @throws ServiceException the service exception
     */
    @Test(dataProvider = "signInData")
    public void testSignIn(AjaxData ajaxDataExpected, Optional<User> userOptional, String... userArguments) throws DaoException, ServiceException {
        Mockito.when(mock.findByLoginAndPassword(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(userOptional);

        AjaxData ajaxDataActual = userService.signIn(userArguments[0], userArguments[1], userArguments[2]);
        assertEquals(ajaxDataActual.getStatusHttp(), ajaxDataExpected.getStatusHttp());
    }

    /**
     * Sign up data object [ ] [ ].
     *
     * @return the object [ ] [ ]
     */
    @DataProvider(name = "signUpData")
    public Object[][] signUpData() {
        return new Object[][]{
                {new AjaxData(SC_CREATED),
                        List.of(Optional.empty(), Optional.empty(), Optional.empty()),
                        List.of("ilya_user", "Aa123", "fname", "sname", "+375(29)333-33-33", "aaa@gmail.com", "", "en")},
                {new AjaxData(SC_BAD_REQUEST),
                        List.of(Optional.of(new User()), Optional.empty(), Optional.empty()),
                        List.of("ilya_user", "Aa123", "fname", "sname", "+375(29)333-33-33", "aaa@gmail.com", "", "en")},
                {new AjaxData(SC_BAD_REQUEST),
                        List.of(Optional.of(new User()), Optional.empty(), Optional.empty()),
                        List.of("ilya_user", "Aa123", "fname", "sname", "+375(29)333-33-33", "aaa@gmail.com", "", "en")},
                {new AjaxData(SC_BAD_REQUEST),
                        List.of(Optional.empty(), Optional.empty(), Optional.of(new User())),
                        List.of("ilya_user", "Aa123", "fname", "sname", "+375(29)333-33-33", "aaa@gmail.com", "", "en")},
        };
    }

    /**
     * Test sign up.
     *
     * @param ajaxDataExpected the ajax data expected
     * @param users            the users
     * @param userArguments    the user arguments
     * @throws DaoException     the dao exception
     * @throws ServiceException the service exception
     */
    @Test(dataProvider = "signUpData")
    public void testSignUp(AjaxData ajaxDataExpected, List<Optional<User>> users, List<String> userArguments)
            throws DaoException, ServiceException {
        Mockito.when(mock.findByLogin(Mockito.anyString()))
                .thenReturn(users.get(0));
        Mockito.when(mock.findByTelephoneNumber(Mockito.anyString()))
                .thenReturn(users.get(1));
        Mockito.when(mock.findByEmail(Mockito.anyString()))
                .thenReturn(users.get(2));
        Mockito.when(mock.add(Mockito.any(), Mockito.anyString()))
                .thenReturn(true);
        AjaxData ajaxDataActual = userService.signUp(userArguments.get(0), userArguments.get(1), userArguments.get(2),
                userArguments.get(3), userArguments.get(4), userArguments.get(5), userArguments.get(6),
                userArguments.get(7));
        assertEquals(ajaxDataActual.getStatusHttp(), ajaxDataExpected.getStatusHttp());
    }

    /**
     * Change password by old password data object [ ] [ ].
     *
     * @return the object [ ] [ ]
     */
    @DataProvider(name = "changePasswordByOldPasswordData")
    public Object[][] changePasswordByOldPasswordData() {
        return new Object[][]{
                {new AjaxData(SC_OK),
                        Optional.of(new User("ilya_user")),
                        "46315d1d58cae3d8df137cd2ad9c4a70", "Aa123", "Aa234", "en"},
                {new AjaxData(SC_BAD_REQUEST),
                        Optional.of(new User("ilya_user")),
                        "46315d1d58cae3d8df137cd2ad9c4a70", "Aa123", "Aa123", "en"},
                {new AjaxData(SC_BAD_REQUEST),
                        Optional.of(new User("ilya_user")),
                        "46315d1d58cae3d8df137cd2ad9ca4a70", "Aa123", "Aa234", "en"}
        };
    }

    /**
     * Test change password by old password.
     *
     * @param ajaxDataExpected the ajax data expected
     * @param userOptional     the user optional
     * @param serviceArguments the service arguments
     * @throws DaoException     the dao exception
     * @throws ServiceException the service exception
     */
    @Test(dataProvider = "changePasswordByOldPasswordData")
    public void testChangePasswordByOldPassword(AjaxData ajaxDataExpected, Optional<User> userOptional,
                                                String... serviceArguments) throws DaoException, ServiceException {
        Mockito.when(mock.findPasswordByLogin(Mockito.anyString()))
                .thenReturn(Optional.of(serviceArguments[0]));
        Mockito.when(mock.updatePasswordByLogin(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(true);
        AjaxData ajaxDataActual = userService.changePasswordByOldPassword(userOptional.get(),
                serviceArguments[1], serviceArguments[2], serviceArguments[3]);
        assertEquals(ajaxDataActual.getStatusHttp(), ajaxDataExpected.getStatusHttp());

    }
}