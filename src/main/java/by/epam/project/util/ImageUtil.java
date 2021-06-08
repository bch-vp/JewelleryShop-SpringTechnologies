package by.epam.project.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * The type File util.
 */
public class ImageUtil {
    private static final Logger logger = LogManager.getLogger();

    private static final Properties properties = new Properties();
    private static final String IMAGE_PROPERTIES = "property/image.properties";

    private static final String CLOUD_NAME = "cloud_name";
    private static final String API_KEY = "api_key";
    private static final String API_SECRET = "api_secret";

    private static final String SLASH = "/";
    private static final String URL = "url";
    private static final String DOT = ".";
    private static final String OK = "ok";
    private static final String RESULT = "result";

    static {
        try {
            ClassLoader classLoader = ImageUtil.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(IMAGE_PROPERTIES);
            properties.load(inputStream);
        } catch (IOException e) {
            logger.fatal("Error while reading properties file: {}", IMAGE_PROPERTIES, e);
            throw new RuntimeException("Error while reading properties file: " + IMAGE_PROPERTIES, e);
        }
    }

    private ImageUtil() {}

    /**
     * Save string.
     *
     * @param file the file
     * @return the string
     * @throws IOException the io exception
     */
    public static String save(FileItem file) throws IOException {
        byte[] fileByte = file.get();
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                CLOUD_NAME, properties.getProperty(CLOUD_NAME),
                API_KEY, properties.getProperty(API_KEY),
                API_SECRET, properties.getProperty(API_SECRET)
        ));
        Map<String, Object> result = cloudinary.uploader().upload(fileByte, ObjectUtils.emptyMap());
        return (String) result.get(URL);
    }

    /**
     * Remove boolean.
     *
     * @param url the url
     * @return the boolean
     * @throws IOException the io exception
     */
    public static boolean remove(String url) throws IOException {
        int lastIndexOfSlash = url.lastIndexOf(SLASH);
        int lastIndexOfDOT = url.lastIndexOf(DOT);
        String fileName = url.substring(lastIndexOfSlash + 1, lastIndexOfDOT);

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                CLOUD_NAME, properties.getProperty(CLOUD_NAME),
                API_KEY, properties.getProperty(API_KEY),
                API_SECRET, properties.getProperty(API_SECRET)
        ));

        Map<String, Object> result = cloudinary.uploader().destroy(fileName, ObjectUtils.emptyMap());
        return result.get(RESULT).equals(OK);
    }
}