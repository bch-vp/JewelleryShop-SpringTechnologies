package by.epam.project.controller.async;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Ajax data.
 */
public class AjaxData {
    private String json;
    private int statusHttp;
    private Map<String, Object> dataSession = new HashMap<>();

    /**
     * Instantiates a new Ajax data.
     *
     * @param statusHttp the status http
     */
    public AjaxData(int statusHttp) {
        this.statusHttp = statusHttp;
    }

    /**
     * Instantiates a new Ajax data.
     */
    public AjaxData() {
        this.statusHttp = HttpServletResponse.SC_OK;
    }

    /**
     * Gets json.
     *
     * @return the json
     */
    public String getJson() {
        return json;
    }

    /**
     * Sets json.
     *
     * @param json the json
     */
    public void setJson(String json) {
        this.json = json;
    }

    /**
     * Gets status http.
     *
     * @return the status http
     */
    public int getStatusHttp() {
        return statusHttp;
    }

    /**
     * Sets status http.
     *
     * @param statusHttp the status http
     */
    public void setStatusHttp(int statusHttp) {
        this.statusHttp = statusHttp;
    }

    /**
     * Gets data session.
     *
     * @return the data session
     */
    public Map<String, Object> getDataSession() {
        return dataSession;
    }

    /**
     * Sets data session.
     *
     * @param dataSession the data session
     */
    public void setDataSession(Map<String, Object> dataSession) {
        this.dataSession = dataSession;
    }

    /**
     * Put data to data session.
     *
     * @param key   the key
     * @param value the value
     */
    public void putDataToDataSession(String key, Object value) {
        this.dataSession.put(key, value);
    }
}
