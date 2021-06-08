package by.epam.project.model.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static by.epam.project.controller.parameter.Parameter.IS_DEV_MODE;

/**
 * The type Database config.
 */
class DatabaseConfig {
    private static final DatabaseConfig instance = new DatabaseConfig();
    private static final Logger logger = LogManager.getLogger();

    private static final String DATABASE_PROPERTIES = "property/database.properties";
    private static final String DRIVER = "driver";

    private static final String LOCAL_URL = "local.url";
    private static final String LOCAL_USER = "local.user";
    private static final String LOCAL_PASSWORD = "local.password";

    private static final String CLOUD_URL = "cloud.url";
    private static final String CLOUD_USER = "cloud.user";
    private static final String CLOUD_PASSWORD = "cloud.password";

    private final String driverName;
    private final String url;
    private final String username;
    private final String password;

    private DatabaseConfig() {
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(DATABASE_PROPERTIES);
            properties.load(inputStream);
        } catch (IOException e) {
            logger.fatal("Error while reading properties file: {}", DATABASE_PROPERTIES, e);
            throw new RuntimeException("Error while reading properties file: " + DATABASE_PROPERTIES, e);
        }

        boolean isDevMode = Boolean.parseBoolean(System.getenv(IS_DEV_MODE));

        driverName = properties.getProperty(DRIVER);
        if (isDevMode) {
            url = properties.getProperty(LOCAL_URL);
            username = properties.getProperty(LOCAL_USER);
            password = properties.getProperty(LOCAL_PASSWORD);
        } else {
            url = properties.getProperty(CLOUD_URL);
            username = properties.getProperty(CLOUD_USER);
            password = properties.getProperty(CLOUD_PASSWORD);
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DatabaseConfig getInstance() {
        return instance;
    }

    /**
     * Gets driver name.
     *
     * @return the driver name
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}