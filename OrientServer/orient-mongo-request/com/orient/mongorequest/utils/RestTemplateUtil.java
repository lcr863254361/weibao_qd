package com.orient.mongorequest.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 * http请求封装
 *
 * @author GNY
 * @create 2017-09-12 20:47
 */
public class RestTemplateUtil {

    private static class SingletonRestTemplate {
        static final RestTemplate INSTANCE = new RestTemplate();
    }

    private static void resetEncode(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> cos = new ArrayList<>();
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("utf-8"));
        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        List<HttpMessageConverter<?>> cos2 = new ArrayList<>();
        cos2.add(stringHttpMessageConverter);
        cos2.add(new ByteArrayHttpMessageConverter());
        cos2.add(new ResourceHttpMessageConverter());
        formHttpMessageConverter.setPartConverters(cos2);
        cos.add(formHttpMessageConverter);
        cos.add(stringHttpMessageConverter);
        restTemplate.setMessageConverters(cos);
    }

    private RestTemplateUtil() {
    }

    public static RestTemplate getInstance() {
        RestTemplate instance = SingletonRestTemplate.INSTANCE;
        resetEncode(instance);
        return instance;
    }

    /**
     * get请求获取数据，直接返回最外层json数据的对象
     *
     * @param url
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T getForObject(String url, Class<T> clz) {
        return RestTemplateUtil.getInstance().getForObject(url, clz, new Object[]{});
    }

    /**
     * get请求，返回json字符串
     *
     * @param url
     * @return
     */
    public static String getForString(String url) {
        return RestTemplateUtil.getInstance().getForObject(url, String.class);
    }

    /**
     * post请求获取数据，直接返回最外层json数据的对象
     *
     * @param url
     * @param requestEntity
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T postForObject(String url, MultiValueMap requestEntity, Class<T> clz) {
        return RestTemplateUtil.getInstance().postForObject(url, requestEntity, clz);
    }

    /**
     * post请求获取数据，返回json字符串
     *
     * @param url
     * @param requestEntity
     * @return
     */
    public static String postForString(String url, MultiValueMap requestEntity) {
        return RestTemplateUtil.getInstance().postForObject(url, requestEntity, String.class);
    }

    /**
     * @return
     */
    public static String postObject(String api, Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Object> entity = new HttpEntity<>(object, headers);
        return RestTemplateUtil.getInstance().postForObject(api, entity, String.class);
    }

}
