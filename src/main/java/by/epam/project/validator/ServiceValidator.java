package by.epam.project.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Service validator.
 */
public class ServiceValidator {
    private static final String LOGIN_REGEX = "^[a-zA-Z0-9_.-]{3,15}$";
    private static final String PASSWORD_REGEX = "^[A-Za-z0-9]{5,20}$";
    private static final String PASSWORD_REGEX_ONE_DIGIT = "(?=.*?[0-9])";
    private static final String PASSWORD_REGEX_ONE_LOWER_CASE_LETTER = "(?=.*?[a-z])";
    private static final String PASSWORD_REGEX_ONE_UPPER_CASE_LETTER = "(?=.*?[A-Z])";
    private static final String USER_NAME_REGEX = "^[a-zA-Z]{3,15}$";
    private static final String NAME_REGEX = "^.{3,15}$";
    private static final String ADDRESS_REGEX = "^.{3,50}$";
    private static final String PHONE_REGEX = "^(\\+375\\([\\d]{2}\\)[\\d]{3}\\-[\\d]{2}\\-[\\d]{2})$";
    private static final String EMAIL_REGEX = "^[a-zA-z0-9_.-]{1,35}@[a-zA-z0-9_-]{2,15}\\.[a-z]{2,5}$";
    private static final String UNIQUE_KEY_REGEX = "^\\d{6}$";

    private static final String ID_REGEX = "^\\d{1,17}$";
    private static final String PRICE_REGEX = "^[0-9]{1,8}(\\.[0-9]{2})?$";
    private static final String INFO_REGEX = "^.{3,100}$";

    private ServiceValidator() {
    }

    /**
     * Is login correct boolean.
     *
     * @param login the login
     * @return the boolean
     */
    public static boolean isLoginCorrect(String login) {
        return isNotEmptyOrNull(login) && isStringMatches(login, LOGIN_REGEX);
    }

    /**
     * Is password correct boolean.
     *
     * @param password the password
     * @return the boolean
     */
    public static boolean isPasswordCorrect(String password) {
        return isNotEmptyOrNull(password)
                && isStringMatches(password, PASSWORD_REGEX)
                && isStringContain(password, PASSWORD_REGEX_ONE_DIGIT)
                && isStringContain(password, PASSWORD_REGEX_ONE_LOWER_CASE_LETTER)
                && isStringContain(password, PASSWORD_REGEX_ONE_UPPER_CASE_LETTER);
    }

    /**
     * Is email correct boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean isEmailCorrect(String email) {
        return isNotEmptyOrNull(email) && isStringMatches(email, EMAIL_REGEX);
    }

    /**
     * Is name correct boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean isNameCorrect(String name) {
        return isNotEmptyOrNull(name) && isStringMatches(name, NAME_REGEX);
    }

    /**
     * Is first name correct boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean isFirstNameCorrect(String name) {
        return isNotEmptyOrNull(name) && isStringMatches(name, USER_NAME_REGEX);
    }

    /**
     * Is last name correct boolean.
     *
     * @param surname the surname
     * @return the boolean
     */
    public static boolean isLastNameCorrect(String surname) {
        return isNotEmptyOrNull(surname) && isStringMatches(surname, USER_NAME_REGEX);
    }

    /**
     * Is phone correct boolean.
     *
     * @param phone the phone
     * @return the boolean
     */
    public static boolean isPhoneCorrect(String phone) {
        return isNotEmptyOrNull(phone) && isStringMatches(phone, PHONE_REGEX);
    }

    /**
     * Is unique code correct boolean.
     *
     * @param uniqueCode the unique code
     * @return the boolean
     */
    public static boolean isUniqueCodeCorrect(String uniqueCode) {
        return isNotEmptyOrNull(uniqueCode) && isStringMatches(uniqueCode, UNIQUE_KEY_REGEX);
    }

    /**
     * Is id correct boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public static boolean isIdCorrect(String id) {
        return isNotEmptyOrNull(id) && isStringMatches(id, ID_REGEX);
    }

    /**
     * Is price correct boolean.
     *
     * @param price the price
     * @return the boolean
     */
    public static boolean isPriceCorrect(String price) {
        return isNotEmptyOrNull(price) && isStringMatches(price, PRICE_REGEX);
    }

    /**
     * Is info correct boolean.
     *
     * @param info the info
     * @return the boolean
     */
    public static boolean isInfoCorrect(String info) {
        return isNotEmptyOrNull(info) && isStringMatches(info, INFO_REGEX);
    }

    /**
     * Is address correct boolean.
     *
     * @param info the info
     * @return the boolean
     */
    public static boolean isAddressCorrect(String info) {
        return isNotEmptyOrNull(info) && isStringMatches(info, ADDRESS_REGEX);
    }

    private static boolean isNotEmptyOrNull(String string) {
        return string != null && !string.isEmpty();
    }

    private static boolean isStringMatches(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    private static boolean isStringContain(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }
}


