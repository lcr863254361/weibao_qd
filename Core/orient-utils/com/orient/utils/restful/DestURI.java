package com.orient.utils.restful;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * destination URI
 *
 * @author Seraph
 *         2016-12-21 下午2:40
 */
public class DestURI {

    public DestURI(String host, int port, String path, Map<String, String> queryStrings){
        this.host = host;
        this.port = port;
        this.path = path;
        this.queryStrings = queryStrings;
    }

    public DestURI(String url, Map<String, String> queryStrings) {
        URI uri = null;
        try {
            uri = new URI(url);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.host = uri.getHost();
        this.port = uri.getPort();
        this.path = uri.getPath();
        this.queryStrings = queryStrings;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryStrings() {
        return queryStrings;
    }

    private final String host;
    private final int port;
    private final String path;
    private final Map<String, String> queryStrings;

}
