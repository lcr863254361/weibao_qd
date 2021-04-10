package com.orient.sqlengine.api;

import com.orient.metamodel.operationinterface.IRestriction;

import java.util.List;
import java.util.Map;

public interface ISysModelService {
    /**
     * 返回约束类的动态范围约束
     * 含有两个Entry
     * key：mindata,value:String
     * key：maxdata,value:String
     *
     * @param res 约束类
     * @return Map<String,String>
     */
    Map<String, String> queryResDynamicRange(IRestriction res);

    /**
     * 查询数值类型单位的数据字典
     *
     * @Function Name:  queryNumberUnit
     * @Description: @return
     * @Date Created:  2015-11-26 下午08:13:26
     * @Author: changxk
     * @Last Modified:     ,  Date Modified:
     */
    List<Map<String, Object>> queryNumberUnit();

    /**
     * 根据单位id查找单位信息
     *
     * @Function Name:  queryNumberUnitById
     * @Description: @return
     * @Date Created:  2015-11-30 上午09:40:03
     * @Author: changxk
     * @Last Modified:     ,  Date Modified:
     */
    Map<String, Object> queryNumberUnitById(String id);

    /**
     * 根据单位名称，查找该单位的所有枚举
     *
     * @Function Name:  queryNumberUnitByName
     * @Description: @param name
     * @Description: @return
     * @Date Created:  2015-11-30 上午11:01:48
     * @Author: changxk
     * @Last Modified:     ,  Date Modified:
     */
    List<Map<String, Object>> queryNumberUnitByName(String name);

}
