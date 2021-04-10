package com.orient.modeldata.tbomHandle.template;

import com.orient.modeldata.bean.*;
import com.orient.sysmodel.operationinterface.IRole;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.web.util.UserContextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * tbom节点构造
 *
 * @author enjoy
 * @createTime 2016-05-23 14:44
 */
public abstract class TbomNodeBuilder {

    public abstract void initNormalAttr(TBomNodeMaterial tBomNodeMaterial, List<TBomNode> tBomNodes);

    public abstract void initTbomModel(TBomNodeMaterial tBomNodeMaterial, List<TBomNode> tBomNodes);

    public abstract void initSpecial(TBomNodeMaterial tBomNodeMaterial, List<TBomNode> tBomNodes);

    public List<TBomNode> builderTBomNode(TBomNodeMaterial tBomNodeMaterial) {
        List<TBomNode> tBomNodes = new ArrayList<>();
        //初始化节点信息 以及 模型相关信息
        initTbomModel(tBomNodeMaterial, tBomNodes);
        //初始化其他信息
        initSpecial(tBomNodeMaterial, tBomNodes);
        //初始化基本信息
        initNormalAttr(tBomNodeMaterial, tBomNodes);
        return tBomNodes;
    }

    protected String formatSqlFilterExpression(String sqlTemp, IRoleUtil roleEngine) {
        if(sqlTemp==null || "".equals(sqlTemp.trim())) {
            return null;
        }

        Pattern pattern = Pattern.compile("##.+?##");
        String userId = UserContextUtil.getUserId();
        String userName = UserContextUtil.getUserAllName();
        String deptId = UserContextUtil.getUserDepId();
        String deptName = UserContextUtil.getUserDepName();
        List<String> roleIds = new ArrayList<>();
        List<String> roleNames = new ArrayList<>();
        List<IRole> roles = roleEngine.getRoleModel(false).getRolesOfUser(userId);
        for(IRole role : roles) {
            roleIds.add(role.getId());
            roleNames.add(role.getName());
        }

        Matcher matcher = pattern.matcher(sqlTemp);
        StringBuffer buf = new StringBuffer();
        while(matcher.find()) {
            String placeHodler = matcher.group();
            String fakeSql = placeHodler.substring(2, placeHodler.length() - 2);
            String realSql = null;
            if(fakeSql.indexOf("$当前用户ID") >= 0) {
                realSql = fakeSql.replaceAll("\\$当前用户ID", userId);
            }
            else if(fakeSql.indexOf("$当前部门ID") >= 0) {
                realSql = fakeSql.replaceAll("\\$当前部门ID", deptId);
            }
            else if(fakeSql.indexOf("$当前角色ID") >= 0) {
                if(roleIds.size() > 0) {
                    realSql = "( 1=0";
                    for(String roleId : roleIds) {
                        realSql = realSql + " OR " + fakeSql.replaceAll("\\$当前角色ID", roleId);
                    }
                    realSql = realSql + " )";
                }
                else {
                    realSql = " 1=0 ";
                }
            }
            else if(fakeSql.indexOf("$当前用户") >= 0) {
                realSql = fakeSql.replaceAll("\\$当前用户", userName);
            }
            else if(fakeSql.indexOf("$当前部门") >= 0) {
                realSql = fakeSql.replaceAll("\\$当前部门", deptName);
            }
            else if(fakeSql.indexOf("$当前角色") >= 0) {
                if(roleNames.size() > 0) {
                    realSql = "( 1=0";
                    for(String roleName : roleNames) {
                        realSql = realSql + " OR " + fakeSql.replaceAll("\\$当前角色", roleName);
                    }
                    realSql = realSql + " )";
                }
                else {
                    realSql = " 1=0 ";
                }
            }
            matcher.appendReplacement(buf, realSql);
        }
        matcher.appendTail(buf);
        return buf.toString();
    }
}
