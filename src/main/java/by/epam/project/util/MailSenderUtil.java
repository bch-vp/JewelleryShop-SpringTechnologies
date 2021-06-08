package by.epam.project.util;

import by.epam.project.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static by.epam.project.controller.parameter.Parameter.COMMAND;
import static by.epam.project.controller.parameter.Parameter.ENCODING;
import static by.epam.project.controller.parameter.Parameter.LOGIN;

/**
 * The type Mail sender util.
 */
public class MailSenderUtil {
    private final static Logger logger = LogManager.getLogger();

    private static final Properties properties = new Properties();
    private static final String IMAGE_PROPERTIES = "property/mail.properties";

    private static final String HOST = "mail.smtp.host";
    private static final String ADDRESS = "mail.from";
    private static final String PASSWORD = "mail.smtp.password";
    private static final String PORT = "mail.smtp.port";
    private static final String AUTH = "mail.smtp.auth";
    private static final String TLS = "mail.smtp.starttls.enable";

    private static final String SEPARATOR_SIGN = "/";
    private static final String AMPERSAND_SIGN = "&";
    private static final String EQUAL_SIGN = "=";
    private static final String QUESTION_MARK = "?";
    private static final String COMMAND_TYPE = "do";

    static  {
        try {
            ClassLoader classLoader = MailSenderUtil.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(IMAGE_PROPERTIES);
            properties.load(inputStream);
        } catch (IOException e) {
            logger.fatal("Error while reading properties file: {}", IMAGE_PROPERTIES, e);
            throw new RuntimeException("Error while reading properties file: " + IMAGE_PROPERTIES, e);
        }
    }

    private MailSenderUtil(){}

    /**
     * Send message.
     *
     * @param subject the subject
     * @param body    the body
     * @param email   the email
     */
    public static void sendMessage(String subject, String body, String email) {
        String address = properties.getProperty(ADDRESS);
        String password = properties.getProperty(PASSWORD);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(address, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(address));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject, ENCODING);
            message.setText(body, ENCODING);
            Transport.send(message);
        } catch (MessagingException exp) {
            logger.error("Error sending a message", exp);
        }
    }

    /**
     * Send confirmation changing password.
     *
     * @param user      the user
     * @param subject   the subject
     * @param body      the body
     * @param uniqueKey the unique key
     */
    public static void sendConfirmationChangingPassword(User user, String subject, String body, String uniqueKey) {
        String bodyEmail = String.format(body, user.getFirstName(), uniqueKey);
        sendMessage(subject, bodyEmail, user.getEmail());
    }

    /**
     * Send activation email.
     *
     * @param user    the user
     * @param subject the subject
     * @param body    the body
     * @param linkApp the link app
     * @param command the command
     */
    public static void sendActivationEmail(User user, String subject, String body, String linkApp, String command) {
        String bodyEmail = String.format(body, user.getFirstName(), linkApp
                + SEPARATOR_SIGN + COMMAND_TYPE + QUESTION_MARK + COMMAND + EQUAL_SIGN + command
                + AMPERSAND_SIGN + LOGIN + EQUAL_SIGN + user.getLogin());
        sendMessage(subject, bodyEmail, user.getEmail());
    }
}
