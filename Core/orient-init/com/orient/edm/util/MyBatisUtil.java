package com.orient.edm.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.ibatis.common.resources.Resources;

public class MyBatisUtil {

	private static SqlSessionFactory sqlSessionFactory = null;
	public static void initMyBatis(){
		try{
			String resource = "myBatis/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static SqlSessionFactory getSqlSessionFactory(){
		if(sqlSessionFactory == null){
			initMyBatis();
		}
		return sqlSessionFactory;			
	}
	
}
