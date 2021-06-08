package by.epam.project.util;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * The type Encrypt password util test.
 */
public class EncryptPasswordUtilTest {
    /**
     * Test encrypt password positive.
     */
    @Test
    public void testEncryptPasswordPositive() {
        String expected = "346d145247b73c887914a7036fdbd4a9";
        String actual = EncryptPasswordUtil.encryption("qwerQWER1");
        assertEquals(actual, expected);
    }

    /**
     * Test encrypt password negative.
     */
    @Test
    public void testEncryptPasswordNegative() {
        String expected = "346d145247b73c887914a7036fdbd4a9";
        String actual = EncryptPasswordUtil.encryption("qwewreretdfsd");
        assertNotEquals(actual, expected);
    }

    /**
     * Test encrypt password length.
     */
    @Test
    public void testEncryptPasswordLength() {
        String hashPassword1 = EncryptPasswordUtil.encryption("q32");
        String hashPassword2 = EncryptPasswordUtil.encryption("qwewreretdfsd");
        int expected1 = hashPassword1.length();
        int expected2 = hashPassword2.length();
        assertEquals(expected1, expected2);
    }

    /**
     * Test encrypt password identical.
     */
    @Test
    public void testEncryptPasswordIdentical() {
        String hashPassword1 = EncryptPasswordUtil.encryption("asfg5454$#@%G$RE$%R@$FF$#");
        String hashPassword2 = EncryptPasswordUtil.encryption("asfg5454$#@%G$RE$%R@$FF$#");
        assertEquals(hashPassword1, hashPassword2);
    }
}