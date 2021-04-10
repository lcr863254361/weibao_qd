package com.orient.weibao.utils;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.orient.mongorequest.config.MongoConfig;

import java.util.ArrayList;
import java.util.List;

public class MongoDBUtil {

    private static MongoDatabase mongoDatabase;

    public static MongoDatabase getConnect(){
        if(mongoDatabase==null){
           MongoClient mongoClient = new MongoClient(MongoConfig.MONGO_HOST, MongoConfig.MONGO_PORT);
             mongoDatabase = mongoClient.getDatabase(MongoConfig.DATABASE);
        }
        return mongoDatabase;
    }
 
    //需要密码认证方式连接
    public static MongoDatabase getConnect2(){
        List<ServerAddress> adds = new ArrayList<>();
        ServerAddress serverAddress = new ServerAddress(MongoConfig.MONGO_HOST, MongoConfig.MONGO_PORT);
        adds.add(serverAddress);
        List<MongoCredential> credentials = new ArrayList<>();
        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential("username", "databaseName", "password".toCharArray());
        credentials.add(mongoCredential);
        MongoClient mongoClient = new MongoClient(adds, credentials);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        return mongoDatabase;
    }

}