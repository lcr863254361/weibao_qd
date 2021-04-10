package com.orient.component;


import com.orient.component.bean.ValidateComponentBean;

/**
 * the interface of custom component.
 *
 * <p>detailed comment</p>
 * @author [创建人]  Administrator <br/> 
 * 		   [创建时间] 2015-3-9 上午11:21:52 <br/> 
 * 		   [修改人] Administrator <br/>
 * 		   [修改时间] 2015-3-9 上午11:21:52
 * @see
 */
public interface ComponentInterface {
	
	/**
	 * 回调函数，在任务提交完成前执行
	 *
	 * <p>回调函数，在任务提交完成前执行</p>
	 * @author [创建人]  Administrator <br/> 
	 * 		   [创建时间] 2015-3-9 上午11:34:01 <br/> 
	 * 		   [修改人] Administrator <br/>
	 * 		   [修改时间] 2015-3-9 上午11:34:01
	 * @param validateComponentBean：验证组件的输入
	 * @return 空字符串表示组件合法 否则返回错误信息
	 * @see
	 */public String validateComponent(ValidateComponentBean validateComponentBean );

	
	/**
	 * 获取数据查看的界面的地址.
	 *
	 * <p>获取数据查看的界面的地址，主要用于协同项目管理的数据TAB页</p>
	 * @author [创建人]  Administrator <br/> 
	 * 		   [创建时间] 2015-3-31 上午10:26:19 <br/> 
	 * 		   [修改人] Administrator <br/>
	 * 		   [修改时间] 2015-3-31 上午10:26:19
	 * @return
	 * @see
	 */
	public String getDashBordExtClass();
	
	
	/**
	 * simple introduction.
	 *
	 * <p>detailed comment</p>
	 * @author [创建人]  Administrator <br/> 
	 * 		   [创建时间] 2015-3-9 下午01:51:43 <br/> 
	 * 		   [修改人] Administrator <br/>
	 * 		   [修改时间] 2015-3-9 下午01:51:43
	 * @return
	 * @see
	 */
	public String getComponentName();

}
