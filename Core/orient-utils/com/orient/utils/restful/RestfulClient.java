package com.orient.utils.restful;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orient.utils.Base64;
import com.orient.utils.CommonTools;
import com.orient.utils.PathTools;
import com.orient.utils.PropertiesUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * RestfulClient
 *
 * @author Seraph
 *         2016-12-21 下午2:23
 */
public class RestfulClient {

    public static RestfulClient getHttpRestfulClient() {
        return httpRestfulClient;
    }

    private RestfulClient(boolean https) {
        this.https = https;
    }

    private static final String FTP_HOME = RestfulClient.getFtpHome();
    private static final RestfulClient httpRestfulClient = new RestfulClient(false);
    private static final String ACCEPT_TYPE = "accept";
    private static CloseableHttpClient httpClient = HttpClients.createDefault();
    private boolean https;

    public <T> RestfulResponse<T> getRequest(DestURI destURI, Class<T> retMessageClass, ContentType acceptCt) {
        URI uri = buildURI(destURI);

        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader(ACCEPT_TYPE, acceptCt.toString());
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            return extraResponseBody(response, retMessageClass);

        } catch (IOException e) {
            throw new RestfulRequestException(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public <U, V> RestfulResponse<U> postRequest(DestURI destURI, V requestObj, Class<U> retMessageClass, ContentType dataCt, ContentType acceptCt) {
        URI uri = buildURI(destURI);

        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader(ACCEPT_TYPE, acceptCt.toString());

        String requestBody = objectToString(requestObj, dataCt);
        HttpEntity httpEntity = new StringEntity(requestBody, dataCt);
        httpPost.setEntity(httpEntity);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            return extraResponseBody(response, retMessageClass);
        } catch (IOException e) {
            throw new RestfulRequestException(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public <U, V> RestfulResponse<U> putRequest(DestURI destURI, V requestObj, Class<U> retMessageClass, ContentType dataCt, ContentType acceptCt) {
        URI uri = buildURI(destURI);

        HttpPut httpPut = new HttpPut(uri);
        httpPut.addHeader(ACCEPT_TYPE, acceptCt.toString());

        String requestBody = objectToString(requestObj, dataCt);
        HttpEntity httpEntity = new StringEntity(requestBody, dataCt);
        httpPut.setEntity(httpEntity);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPut);
            return extraResponseBody(response, retMessageClass);
        } catch (IOException e) {
            throw new RestfulRequestException(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public <T> RestfulResponse<T> deleteRequest(DestURI destURI, Class<T> retMessageClass, ContentType acceptCt) {
        URI uri = buildURI(destURI);

        HttpDelete httpDelete = new HttpDelete(uri);
        httpDelete.addHeader(ACCEPT_TYPE, acceptCt.toString());
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpDelete);
            return extraResponseBody(response, retMessageClass);

        } catch (IOException e) {
            throw new RestfulRequestException(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public <U, V> RestfulResponse<U> upload(DestURI destURI, Map<String, Object> requestObj, Class<U> retMessageClass, ContentType acceptCt) {
        URI uri = buildURI(destURI);

        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader(ACCEPT_TYPE, acceptCt.toString());

        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .setContentType(ContentType.create("multipart/form-data", Consts.UTF_8));

        for (String key : requestObj.keySet()) {
            Object obj = requestObj.get(key);
            if (obj instanceof File) {
                FileBody file = new FileBody((File) obj, ContentType.create("application/octet-stream", (Charset) null), null);
                builder.addPart(key, file);
            } else {
                StringBody string = new StringBody(obj.toString(), ContentType.create("text/plain", Consts.UTF_8));
                builder.addPart(key, string);
            }
        }
        HttpEntity httpEntity = builder.build();
        httpPost.setEntity(httpEntity);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            return extraResponseBody(response, retMessageClass);
        } catch (IOException e) {
            throw new RestfulRequestException(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public RestfulResponse<File> download(DestURI destURI) {
        URI uri = buildURI(destURI);

        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader(ACCEPT_TYPE, ContentType.APPLICATION_OCTET_STREAM.toString());
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            return extraResponseBody(response, File.class);
        } catch (IOException e) {
            throw new RestfulRequestException(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public RestfulResponse<InputStream> downloadStream(DestURI destURI) {
        URI uri = buildURI(destURI);

        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader(ACCEPT_TYPE, ContentType.APPLICATION_OCTET_STREAM.toString());
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            int sc = response.getStatusLine().getStatusCode();
            RestfulResponse returnVal = new RestfulResponse(sc == HttpStatus.SC_OK ? true : false, sc);
            if (returnVal.isSuccess()) {
                String contentTypeName = entity.getContentType().getValue().toLowerCase();
                if (contentTypeName.contains("octet-stream")) {
                    String fileName = getFileName(response);
                    returnVal.setErrorMsg(fileName);
                    InputStream is = entity.getContent();
                    returnVal.setResult(is);
                    return returnVal;
                } else {
                    returnVal.setSuccess(false);
                    returnVal.setErrorMsg("非文件格式内容");
                    return returnVal;
                }
            } else {
                return returnVal;
            }
        } catch (IOException e) {
            throw new RestfulRequestException(e.getMessage(), e);
        }
    }

    private <U, V> RestfulResponse<U> extraResponseBody(CloseableHttpResponse response, Class<U> retMessageClass) {
        try {

            HttpEntity entity = response.getEntity();

            int sc = response.getStatusLine().getStatusCode();
            RestfulResponse returnVal = new RestfulResponse(sc == HttpStatus.SC_OK ? true : false, sc);
            if (returnVal.isSuccess()) {
                String contentTypeName = entity.getContentType().getValue().toLowerCase();
                if (contentTypeName.contains("json")) {
                    String content = IOUtils.toString(entity.getContent(), "UTF-8");
                    ObjectMapper jsonObjectMapper = new ObjectMapper();

                    returnVal.setResult(jsonObjectMapper.readValue(content, jsonObjectMapper.constructType(retMessageClass)));
                    return returnVal;
                } else if (contentTypeName.contains("xml")) {
                    JAXBContext jc = JAXBContext.newInstance(retMessageClass.getName());
                    Unmarshaller u = jc.createUnmarshaller();
                    returnVal.setResult(u.unmarshal(entity.getContent()));
                    return returnVal;
                } else if (contentTypeName.contains("octet-stream")) {
                    File retFile = null;
                    String fileName = getFileName(response);
                    if (fileName != null) {
                        InputStream is = entity.getContent();
                        String filePath = FTP_HOME + File.separator + "Restful下载文件" + File.separator + fileName;
                        File file = new File(filePath);
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                        FileOutputStream fos = new FileOutputStream(file);
                        byte[] buffer = new byte[8192];
                        int ch = 0;
                        while ((ch = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, ch);
                        }
                        fos.flush();
                        fos.close();
                        is.close();
                        retFile = file;
                    }
                    returnVal.setResult(retFile);
                    return returnVal;
                } else {
                    returnVal.setSuccess(false);
                    returnVal.setErrorMsg("不支持的内容格式");
                    return returnVal;
                }
            } else {
                return returnVal;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RestfulRequestException(e.getMessage(), e);
        }

    }

    private String objectToString(Object value, ContentType contentType) {
        if (contentType == ContentType.APPLICATION_JSON) {
            ObjectMapper jsonObjectMapper = new ObjectMapper();

            try {
                return jsonObjectMapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                throw new RestfulRequestException(e.getMessage(), e);
            }
        } else if (contentType == ContentType.APPLICATION_XML) {

            try {
                JAXBContext jc = JAXBContext.newInstance(value.getClass().getName());
                Marshaller m = jc.createMarshaller();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                m.marshal(value, outputStream);
                return outputStream.toString();
            } catch (JAXBException e) {
                throw new RestfulRequestException(e.getMessage(), e);
            }

        } else {
            throw new IllegalArgumentException("不支持的content type");
        }

    }

    private URI buildURI(DestURI destURI) {
        URI uri;
        try {
            URIBuilder uriBuilder = new URIBuilder();
            uriBuilder.setScheme("http")
                    .setHost(destURI.getHost())
                    .setPort(destURI.getPort())
                    .setPath(destURI.getPath());

            Map<String, String> queryStrings = destURI.getQueryStrings();
            if (queryStrings != null) {
                queryStrings.entrySet().forEach(entry -> {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue());
                });
            }
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new RestfulRequestException("URI语法错误:" + e.getMessage(), e);
        }

        return uri;
    }

    private String getFileName(HttpResponse response) {
        Header header = response.getFirstHeader("Content-Disposition");
        String filename = null;
        if (header != null) {
            HeaderElement[] values = header.getElements();
            if (values.length == 1) {
                NameValuePair param = values[0].getParameterByName("filename");
                if (param != null) {
                    try {
                        //filename= URLDecoder.decode(param.getValue(), "utf-8");
                        filename = param.getValue();
                        if (filename.startsWith("=?UTF-8?B?")) {
                            filename = filename.substring(10, filename.length() - 2);
                            filename = Base64.getFromBase64(filename);
                        } else {
                            filename = URLDecoder.decode(filename, "utf-8");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return filename;
    }

    private static String getFtpHome() {
        String filePath = PathTools.getRootPath() + File.separator + "WEB-INF" + File.separator + "classes"
                + File.separator + "ftpServer.properties";
        String value = PropertiesUtil.readValue(filePath, "ftpServer.ftpHome");

        if (CommonTools.isNullString(value)) {
            throw new NullPointerException("FtpHome配置未找到！");
        }
        return value;
    }


    public static void main(String[] args) {

        class CommonResponseObject {

            private boolean success;
            private String message;

            public CommonResponseObject() {

            }

            public CommonResponseObject(boolean success, String message) {
                this.success = success;
                this.message = message;
            }

            public boolean isSuccess() {
                return success;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }


//        Object get = RestfulClient.getHttpRestfulClient().getRequest(new DestURI("localhost", 8080, "/FileServiceWeb/lucene/工程/10/1", null), LuceneResponse.class, ContentType.APPLICATION_JSON);
//        Object delete = RestfulClient.getHttpRestfulClient().deleteRequest(new DestURI("localhost", 8080, "/OrientEDM/test/hello2", null), CommonResponseObject.class, ContentType.APPLICATION_JSON);
//
//        DestURI destURI = new DestURI("localhost", 8080, "/OrientEDM/test/hello", null);
//        CommonResponseObject request = new CommonResponseObject(true, "ok");
//        Object post = RestfulClient.getHttpRestfulClient().postRequest(destURI, request, CommonResponseObject.class, ContentType.APPLICATION_JSON, ContentType.APPLICATION_JSON);
//        Object put = RestfulClient.getHttpRestfulClient().putRequest(destURI, request, CommonResponseObject.class, ContentType.APPLICATION_JSON, ContentType.APPLICATION_JSON);

        System.out.println("x");
    }
}
