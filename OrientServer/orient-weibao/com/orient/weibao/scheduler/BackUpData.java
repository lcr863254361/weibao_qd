package com.orient.weibao.scheduler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.orient.sysman.scheduler.BackUpScheduler;
import com.orient.utils.JsonUtil;
import com.orient.weibao.mbg.mapper.DivingStatisiticsMapper;
import com.orient.weibao.mbg.mapper.ProductStructureMapper;
import com.orient.weibao.mbg.model.DivingStatisitics;
import com.orient.weibao.mbg.model.ProductStructure;
import net.sf.json.util.JSONUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 定时上传数据到数据中心
 */
@Component
public class BackUpData {
    Logger log = Logger.getLogger(BackUpData.class);
    private final  GsonBuilder gsonBuilder = new GsonBuilder();

    @Autowired
    private  DivingStatisiticsMapper divingStatisiticsMapper;
    @Autowired
    private  ProductStructureMapper productStructureMapper;
    @Autowired
    private  Environment env;
    private ThreadPoolExecutor executor=new ThreadPoolExecutor(3,
            5,
            30,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),
            new ThreadPoolExecutor.AbortPolicy()
            );




    /**
     * 星期五，晚上8点，上传到数据中心
     */
//    @Scheduled(cron = "*")
    public void upToDataCenter(){
        updateProductStructure();
        updateDivingStatisitics();
    }
    private void updateDivingStatisitics(){
        List<DivingStatisitics> divingStatisitics =
                divingStatisiticsMapper.selectByExample(null);
        executor.execute(()->{
            gsonBuilder.setPrettyPrinting();
            Gson gson = gsonBuilder.create();
            DataEntity<DivingStatisitics> dataEntity = new DataEntity<>();
            dataEntity.setRequestTime(DateUtil.now());
            dataEntity.setModelId(Long.parseLong(env.getProperty("modelId")));
            dataEntity.setList(divingStatisitics);
            byte[] bytes = HexUtil.decodeHex(env.getProperty("key"));
            AES aes = SecureUtil.aes(bytes);
            setSign(dataEntity,aes);
            sendData(gson.toJson(dataEntity),env.getProperty("server")+env.getProperty("divingStatisitics"));
        });
    }
    private void updateProductStructure(){
        List<ProductStructure> productStructures =
                productStructureMapper.selectByExample(null);

        executor.execute(()->{
            gsonBuilder.setPrettyPrinting();
            Gson gson = gsonBuilder.create();
            DataEntity<ProductStructure> dataEntity = new DataEntity<>();
            dataEntity.setRequestTime(DateUtil.now());
            dataEntity.setModelId(Long.parseLong(env.getProperty("modelId")));
            dataEntity.setList(productStructures);
            byte[] bytes = HexUtil.decodeHex(env.getProperty("key"));
            AES aes = SecureUtil.aes(bytes);
            setSign(dataEntity,aes);
            sendData(gson.toJson(dataEntity),env.getProperty("server")+env.getProperty("productStructure"));
        });
    }
    private void sendData (String json,String url){
        try {
            HttpResponse result = HttpRequest.post(url)
                    .body(json)
                    .execute();
            if(result.getStatus()==200){
                JSONObject jsonObject = JSONUtil.parseObj(result.body());
                String code = jsonObject.getStr("code");
                if(!code.equals("200")){
                    log.warn("数据发送失败!"+jsonObject.getStr("message"));
                }else {
                    log.warn("数据发送成功!");
                }

            }else {
                log.warn("数据发送失败!"+result.getStatus());
            }
        }catch (Exception e){
            log.warn("数据发送失败!"+e.getMessage());
        }

    }

    public void setSign(DataEntity entity,AES aes){
        JSONObject jsonObject = JSONUtil.parseObj(entity);
        Set<String> keys = jsonObject.keySet();
        String sign = keys.stream().map(key -> {
            String s = "";
            if (!(jsonObject.get(key) instanceof JSONArray) && !key.equals("sign")) {
                s = key +"="+ jsonObject.get(key);
            }
            return s;
        }).filter(item-> !StrUtil.isEmpty(item)).collect(Collectors.joining("&"));

        byte[] bytes = DigestUtil.sha256(sign,"utf-8");
        entity.setSign(aes.encryptHex(HexUtil.encodeHexStr(bytes)));
    }
    class DataEntity<T>{
        private Long modelId;
        private String sign;
        private List<T> list;
        private String requestTime;

        public String getRequestTime() {
            return requestTime;
        }

        public void setRequestTime(String requestTime) {
            this.requestTime = requestTime;
        }

        public Long getModelId() {
            return modelId;
        }

        public void setModelId(Long modelId) {
            this.modelId = modelId;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public List<T> getList() {
            return list;
        }

        public void setList(List<T> list) {
            this.list = list;
        }
    }
}
