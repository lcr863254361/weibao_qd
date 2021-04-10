package com.orient.webservice.base;

import com.orient.web.base.dsbean.CommonResponse;
import com.orient.dsrestful.domain.schemaBaseInfo.SchemaResponse;
import com.orient.dsrestful.domain.lock.LockResponse;

public interface LoginChecking {

    /**
     * 验证用户名和密码
     *
     * @param username   用户名
     * @param ip         ip
     * @param password   密码
     * @param clientType 客户端 :rcp1,rcp2,rcp3,rcp4
     * @return error  "服务器出错，验证用户权限失败，请检查数据库和服务器配置"
     * -1     "服务器验证用户出现异常，请确认服务器正常工作"
     * -2     "登陆受限licence导致"
     * 1      "用户名密码不正确或该用户没有权限"
     * 2      "已有用户将该业务库锁定"
     * 0      "用户名不存在输入错误的用户名"
     * false  "系统线程运行异常，请重新操作"
     */
    CommonResponse loginCheck(String username, String ip, String password, String clientType);

    /**
     * 校验用户并获取schema
     *
     * @param username   用户名
     * @param ip         ip
     * @param password   密码
     * @param clientType 客户端 :rcp1,rcp2,rcp3,rcp4
     * @param isGetAll 0表示获取所有schema 1表示获取上锁schema
     * @return schema集合
     */
    SchemaResponse loginCheckAndGetSchema(String username, String ip, String password, String clientType,Integer isGetAll);

    /**
     * 校验用户并把当前操作的schema上锁
     *
     * @param username   用户名
     * @param ip         ip
     * @param password   密码
     * @param clientType 客户端 :rcp1,rcp2,rcp3,rcp4
     * @param schemaName schema名称
     * @param version    版本号
     * @return 0表示服务器异常, 1表示上锁成功, 2表示上锁失败但校验成功可正常打开
     */
    LockResponse loginCheckAndLockSchema(String username, String ip, String password, String clientType,String schemaName,String version);

}
