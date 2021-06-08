package by.epam.project.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * The type Encrypt password util.
 */
public class EncryptPasswordUtil {
    private EncryptPasswordUtil() {
    }

    /**
     * Encryption string.
     *
     * @param password the password
     * @return the string
     */
    public static String encryption(String password) {
        String encryptPassword = DigestUtils.md5Hex(password);
        return encryptPassword;
    }
}
