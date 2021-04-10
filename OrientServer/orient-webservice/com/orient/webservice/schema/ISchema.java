package com.orient.webservice.schema;

import java.util.List;
import java.util.Map;

public interface ISchema {

    /**
     * 数据模型的上锁和解锁(不包括强制解锁)
     *
     * @param schemaName 数据模型名称
     * @param version    数据模型版本号
     * @param lockTag    上锁解锁标记：0表示解锁，1表示上锁
     * @param username   用户名
     * @param ip         进行上锁解锁行为的电脑ip
     * @return
     */
    String setLock(String schemaName, String version, Integer lockTag, String username, String ip);

    /**
     * 数据模型的强制解锁
     *
     * @return string true为解锁成功;false为解锁失败
     * @Method: unlockSchema
     * @param: 解锁的数据模型id号的list数组
     */
    String unlockSchema(List<String> list);


    /**
     * 根据模型名称和版本号打开数据库中的数据模型
     *
     * @param name    数据模型名称
     * @param version 数据模型版本号
     * @return
     */
    String getSchema(String name, String version);

    /**
     * 打开数据库中所有的数据模型
     *
     * @return
     */
    List<String> getSchemaList();

    /**
     * 修改数据模型到数据库中.
     *
     * @return int 0 :成功 ;1：schema不存在; -1:异常
     * @Method: updateSchema
     * @param: xmlContent 数据模型信息字符串
     */
    int updateSchema(String xmlContent);

    /**
     * 保存数据模型到数据库中.
     *
     * @return int 0 :成功 ;1：schema已经存在; -1:异常
     * @Method: setSchema
     * @param: xmlContent 数据模型信息字符串
     */
    int setSchema(String xmlContent);

    /**
     * 获取数据库中的所有数据模型的信息.
     *
     * @return String 返回所有数据模型的集合字符串
     * @Method: getSchema
     */
    String getSchema();

    /**
     * 删除数据库中的数据模型.
     *
     * @return int 0 :成功 ;1：schema不存在; -1:异常
     * @Method: deleteSchema
     * @param: name 数据模型名称
     * @param: version 数据模型版本号
     */
    int deleteSchema(String name, String version);

    /**
     * 删除数据模型时校验该数据模型是否已经存在数据记录.
     *
     * @return string "1"+userid 表示该schema为加锁状态;2表示schema不存在;true表示有数据;false表示无数据;warn表示表不存在;error表示有异常
     * @Method: isExistData
     * @param: name 数据模型名称
     * @param: version 数据模型版本号
     */
    String isExistData(String name, String version);

    /**
     * 获取数据库中的算法库，将算法按照一定的规则组织成字符串传送给客户端.
     *
     * @return string 返回算法字符串
     * @Method: getArithmetic
     */
    String getArithmetic();

    /**
     * 删除数据模型中的附件.
     *
     * @return string 1表示删除成功;-1表示异常
     * @Method: deleteFile
     * @param: schemaid 数据模型ID
     */
    String deleteFile(String schemaId);

    /**
     * 重设自增属性的自增序列的初始值.
     *
     * @return string
     * error表示出现异常;
     * 0表示重新初始化值成功
     * 1为表不存在不需要重新初始化值;
     * 2为字段不存在不需要重新初始化值;
     * 3为字段存在，但表序列并没有创建;
     * 4为字段已有数据存在不需要重新初始化值；
     * @Method: updateSeqValue
     * @param: id 字段名称，数据类名称，数据模型名称，数据模型版本号的字符串
     * @param: value sequence值
     */
    String updateSeqValue(String id, int value);

    /**
     * 重设自增属性的自增序列的初始值和间隔.
     *
     * @return string
     * error表示出现异常;
     * 0表示重新初始化值成功
     * 1为表不存在不需要重新初始化值;
     * 2为字段不存在不需要重新初始化值;
     * 3为字段存在，但表序列并没有创建;
     * 4为字段已有数据存在不需要重新初始化值；
     * @Method: updateSeqValue
     * @param: id 字段名称，数据类名称，数据模型名称，数据模型版本号的字符串
     * @param: value sequence值
     * @param: interval 间隔
     */
    String updateSeqValue(String id, int value, int interval);

    /**
     * 根据ID查询脚本.
     *
     * @return string 返回脚本列表
     * @Method: query
     * @param: 脚本CWM_SEQGENERATOR中id号
     */
    Map<String, Object> query(Long id);

    /**
     * 取脚本列表.
     *
     * @return string 返回脚本列表
     * @Method: queryList
     */
    List<Map<String, Object>> queryList();

    /**
     * 新增脚本.
     *
     * @return string 脚本名称不能为空;脚本名称重名;保存成功_'新增脚本序列号'
     * @Method: insert
     * @param: scriptMap 新增脚本的数组
     */
    String insert(Map<String, Object> scriptMap);

    /**
     * 更新脚本.
     *
     * @return string 此脚本正在使用,不可更改;脚本名称不能为空;脚本名称重名;保存成功
     * @Method: update
     * @param: scriptMap 修改脚本的数组
     * @param: id 脚本id号
     */
    String update(Map<String, Object> scriptMap, Long id);

    /**
     * 删除脚本.
     *
     * @return string 数据模型被锁定;此脚本正在使用,不可更改;删除成功
     * @Method: delete
     * @param: id 脚本id号或id字符串用逗号分割
     */
    String delete(String id);

    /**
     * 获取当前系统中的系统表和共享数据模型.
     *
     * @return string 返回当前系统中的系统表和共享数据模型字符串
     * @Method: getShareAndDefault
     */
    Map<String, String> getShareAndDefault();

    /**
     * 获取具体要添加作为数据模型的数据类的固化表或系统表.
     *
     * @return string 返回table的xml文件
     * @Method: getTableDetail
     * @param: id 查询table的id集合
     */
    String getTableDetail(String id);

    /**
     * 判断共享数据模型里的数据类、普通属性和统计属性是否可以删除.
     *
     * @return string 0表示未删除；1表示已删除；2表示已加锁不能删除
     * @Method: canDelete
     * @param: schemaid 数据模型的ID
     * @param: type=0为判断数据类是否可以被删除；type=1为判断普通属性、统计属性是否可以删除
     */
    String canDelete(String id, int type);

}
