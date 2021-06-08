package by.epam.project.util;

import by.epam.project.controller.async.AjaxData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Json util.
 */
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String CONTENT_TYPE = "application/json";
    private static final String ENCODING = "UTF-8";
    private static final int EMPTY_PRIMITIVE = 0;

    private JsonUtil() {
    }

    /**
     * Write json to ajax data.
     *
     * @param ajaxData   the ajax data
     * @param errorKey   the error key
     * @param contentKey the content key
     * @param language   the language
     * @throws IOException the io exception
     */
    public static void writeJsonToAjaxData(AjaxData ajaxData, String errorKey, String contentKey,
                                           String language) throws IOException {
        Map<String, String> responseMap = new HashMap<>();
        String contentValue = ContentUtil.getWithLocale(language, contentKey);
        responseMap.put(errorKey, contentValue);

        String json = toJson(responseMap);
        ajaxData.setJson(json);
    }

    /**
     * Write json to response.
     *
     * @param response the response
     * @param json     the json
     * @throws IOException the io exception
     */
    public static void writeJsonToResponse(HttpServletResponse response, String json) throws IOException {
        if (json != null && !json.isEmpty()) {
            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);
            response.getWriter().write(json);
        }
    }

    /**
     * Write ajax data to response.
     *
     * @param response the response
     * @param ajaxData the ajax data
     * @throws IOException the io exception
     */
    public static void writeAjaxDataToResponse(HttpServletResponse response, AjaxData ajaxData) throws IOException {
        if (ajaxData.getStatusHttp() != HttpServletResponse.SC_OK) {
            response.setStatus(ajaxData.getStatusHttp());
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        writeJsonToResponse(response, ajaxData.getJson());
    }

    /**
     * Write json to ajax data.
     *
     * @param ajaxData the ajax data
     * @param key      the key
     * @param value    the value
     * @throws IOException the io exception
     */
    public static void writeJsonToAjaxData(AjaxData ajaxData, String key, String value) throws IOException {
        String json = toJson(key, value);
        ajaxData.setJson(json);
    }

    /**
     * To json string.
     *
     * @param key   the key
     * @param value the value
     * @return the string
     * @throws IOException the io exception
     */
    public static String toJson(String key, String value) throws IOException {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put(key, value);
        String json = toJson(responseMap);
        return json;
    }

    /**
     * To json string.
     *
     * @param <T>  the type parameter
     * @param key  the key
     * @param list the list
     * @return the string
     * @throws IOException the io exception
     */
    public static <T> String toJson(String key, List<T> list) throws IOException {
        Map<String, List<T>> responseMap = new HashMap<>();
        responseMap.put(key, list);
        String json = toJson(responseMap);
        return json;
    }

    /**
     * To json string.
     *
     * @param map the map
     * @return the string
     * @throws JsonProcessingException the json processing exception
     */
    public static String toJson(Map map) throws JsonProcessingException {
        return objectMapper.writeValueAsString(map);
    }

    /**
     * To map hash map.
     *
     * @param inputStream the input stream
     * @return the hash map
     * @throws IOException the io exception
     */
    public static Map<String, String> toMap(InputStream inputStream) throws IOException {
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<>(){} ;
        return objectMapper.readValue(inputStream, typeRef);
    }

    /**
     * To json string.
     *
     * @param <T> the type parameter
     * @param t   the t
     * @return the string
     * @throws IOException the io exception
     */
    public static <T> String toJson(T t) throws IOException {
        return objectMapper.writeValueAsString(t);
    }

    /**
     * Add node to json tree json node.
     *
     * @param rootNode the root node
     * @param key      the key
     * @param value    the value
     * @param paths    the paths
     * @return the json node
     */
    public static JsonNode addNodeToJsonTree(JsonNode rootNode, String key, String value, String... paths) {
        JsonNode node = rootNode;
        for (String path : paths) {
            node = node.path(path);
        }
        ((ObjectNode) node).put(key, value);
        return rootNode;
    }

    /**
     * Create json tree json node.
     *
     * @param object the object
     * @return the json node
     */
    public static JsonNode createJsonTree(String object) {
        JsonNode rootNode = objectMapper.createObjectNode();
        ((ObjectNode) rootNode).putObject(object);
        return rootNode;
    }

    /**
     * Write json tree to response.
     *
     * @param ajaxData the ajax data
     * @param jsonNode the json node
     */
    public static void writeJsonTreeToResponse(AjaxData ajaxData, JsonNode jsonNode) {
        String json = jsonNode.toString();
        ajaxData.setJson(json);
    }
}
