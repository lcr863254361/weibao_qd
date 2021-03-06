package com.orient.log.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)   
@Documented  
@Inherited 
public @interface Action {
	
	/**
	 * 操作类型
	 * @return
	 */
	//public String operateType() default "";
	/**
	 * 方法描述
	 * @return
	 */
	public String description() default "no description";
	
	
	/**
	 * 归属模块
	 * @return
	 */
	public String ownermodel() default "" ; 
	/**
	 * 日志类型
	 * @return
	 */
	public String exectype() default "操作日志";
	
	/**
	 * 详细信息
	 * @return
	 */
	public String detail() default "";
	
	/**
	 * 执行顺序
	 */
	public ActionExecOrder execOrder() default ActionExecOrder.AFTER;
}
