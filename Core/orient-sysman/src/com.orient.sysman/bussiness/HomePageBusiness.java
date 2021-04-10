package com.orient.sysman.bussiness;

import com.orient.config.SystemMngConfig;
import com.orient.sysman.bean.FuncBean;
import com.orient.sysman.bean.LuceneSearchResult;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.domain.role.CwmSysRolePortalEntity;
import com.orient.sysmodel.domain.sys.CwmPortalEntity;
import com.orient.sysmodel.domain.sys.CwmSysUserLinkEntity;
import com.orient.sysmodel.domain.sys.CwmSysUserPortalEntity;
import com.orient.sysmodel.operationinterface.IFunction;
import com.orient.sysmodel.operationinterface.IRole;
import com.orient.sysmodel.service.file.FileService;
import com.orient.sysmodel.service.role.IRolePortalService;
import com.orient.sysmodel.service.sys.IPortalService;
import com.orient.sysmodel.service.sys.IUserLinkService;
import com.orient.sysmodel.service.sys.IUserPortalService;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.utils.restful.DestURI;
import com.orient.utils.restful.RestfulClient;
import com.orient.utils.restful.RestfulResponse;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.http.entity.ContentType;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 首页管理
 *
 * @author enjoy
 * @createTime 2016-06-01 16:46
 */
@Component
public class HomePageBusiness extends BaseBusiness {

    @Autowired
    IPortalService portalService;

    @Autowired
    IUserPortalService userPortalService;

    @Autowired
    IUserLinkService userLinkService;

    @Autowired
    IRolePortalService rolePortalService;

    @Autowired
    FileService fileService;

    public List<FuncBean> getUnSelectedFunction(Boolean selected) {
        //转化为前端展现对象
        List<FuncBean> retList = new ArrayList<>();
        String userId = UserContextUtil.getUserId();
        //当前用户的所有功能点信息
        List<IFunction> allFunction = new ArrayList<>();
        //过滤条件
        Criterion filter = Restrictions.eq("userId", Long.valueOf(userId));
        //过滤结果
        List<CwmSysUserLinkEntity> existLinks = userLinkService.list(filter, Order.asc("functionOrder"));
        List<Long> existFunctionIds = existLinks.stream().mapToLong(CwmSysUserLinkEntity::getFunctionId).boxed().collect(Collectors.toList());
        //获取当前用户的所有功能点信息
        List<IRole> RoleList = roleEngine.getRoleModel(false).getRolesOfUser(userId);
        Map<String, IFunction> allFunMap = new HashMap<>();
        // 遍历角色获取功能点，并去除掉重复的
        RoleList.forEach(role -> role.getAllFunctions().forEach(function -> {
            if (function.getFunctionid() > IFunction.webShowRootId) {
                allFunMap.put(function.getName(), function);
            }
        }));
        allFunction.addAll(allFunMap.keySet().stream().map(allFunMap::get).collect(Collectors.toList()));
        //去除已经设置过的
        List<IFunction> unSelectedFunctions = selected ? allFunction.stream().filter(function -> existFunctionIds.contains(function.getFunctionid())).sorted((f1, f2) -> existFunctionIds.indexOf(f1.getFunctionid()) - existFunctionIds.indexOf(f2.getFunctionid())).collect(Collectors.toList()) : allFunction.stream().filter(function -> !existFunctionIds.contains(function.getFunctionid())).collect(Collectors.toList());
        unSelectedFunctions.forEach(func -> {
            FuncBean funcBean = new FuncBean();
            try {
                PropertyUtils.copyProperties(funcBean, func);
            } catch (Exception e) {
                e.printStackTrace();
            }
            funcBean.setId(Long.toString(func.getFunctionid()));
            funcBean.setPid(Long.toString(func.getParentid()));
            funcBean.setText(funcBean.getName());
            funcBean.setIconCls("icon-function-64");
            funcBean.setHasChildrens(func.getChildrenFunction().size() > 0);
            retList.add(funcBean);
        });
        return retList;
    }

    public List<FuncBean> getAllFunction() {
        //转化为前端展现对象
        List<FuncBean> retList = new ArrayList<>();
        String userId = UserContextUtil.getUserId();
        //当前用户的所有功能点信息
        List<IFunction> allFunction = new ArrayList<>();
        //过滤条件
        Criterion filter = Restrictions.eq("userId", Long.valueOf(userId));
        //过滤结果
        List<CwmSysUserLinkEntity> existLinks = userLinkService.list(filter, Order.asc("functionOrder"));
        List<Long> existFunctionIds = existLinks.stream().mapToLong(CwmSysUserLinkEntity::getFunctionId).boxed().collect(Collectors.toList());
        //获取当前用户的所有功能点信息
        List<IRole> RoleList = roleEngine.getRoleModel(false).getRolesOfUser(userId);
        Map<String, IFunction> allFunMap = new HashMap<>();
        // 遍历角色获取功能点，并去除掉重复的
        RoleList.forEach(role -> role.getAllFunctions().forEach(function -> {
            if (function.getFunctionid() > IFunction.webShowRootId) {
                allFunMap.put(function.getName(), function);
            }
        }));
        allFunction.addAll(allFunMap.keySet().stream().map(allFunMap::get).collect(Collectors.toList()));
        allFunction.forEach(func -> {
            FuncBean funcBean = new FuncBean();
            try {
                PropertyUtils.copyProperties(funcBean, func);
            } catch (Exception e) {
                e.printStackTrace();
            }
            funcBean.setId(Long.toString(func.getFunctionid()));
            funcBean.setPid(Long.toString(func.getParentid()));
            funcBean.setText(funcBean.getName());
            funcBean.setIconCls("icon-function-64");
            funcBean.setHasChildrens(func.getChildrenFunction().size() > 0);
            retList.add(funcBean);
        });

        List<FuncBean> unselectedFunctions = getUnSelectedFunction(false);
        List<String> unselectedIds = new ArrayList<>();
        unselectedFunctions.forEach(func -> {
            unselectedIds.add(func.getId());
        });
        retList.forEach(func -> {
            String id = func.getId();
            if (!unselectedIds.contains(id)) {
                String oriIcon = func.getIcon();
                int index = oriIcon.indexOf('.');
                String name = oriIcon.substring(0, index) + "_checked.png";
                func.setIcon(name);
            }
        });
        return retList;
    }

    /**
     * 保存用户快捷方式
     *
     * @param functionId
     */
    public void saveUserLink(Long functionId) {
        String userId = UserContextUtil.getUserId();
        //过滤条件
        Criterion filter = Restrictions.eq("userId", Long.valueOf(userId));
        List<CwmSysUserLinkEntity> existLinks = userLinkService.list(filter);
        Long linkOrder = 1l;
        if (!existLinks.isEmpty()) {
            linkOrder = existLinks.stream().mapToLong(CwmSysUserLinkEntity::getFunctionOrder).max().getAsLong() + 1;
        }
        CwmSysUserLinkEntity toSaveEntity = new CwmSysUserLinkEntity();
        toSaveEntity.setFunctionId(functionId);
        toSaveEntity.setUserId(Long.valueOf(userId));
        toSaveEntity.setFunctionOrder(linkOrder);
        userLinkService.save(toSaveEntity);
    }

    /**
     * @param functionId 移除快捷方式
     */
    public void removeUserQuickLink(Long functionId) {
        String userId = UserContextUtil.getUserId();
        //过滤条件
        Criterion filter = Restrictions.and(Restrictions.eq("userId", Long.valueOf(userId)), Restrictions.eq("functionId", Long.valueOf(functionId)));
        userLinkService.list(filter).forEach(cwmSysUserLinkEntity -> userLinkService.delete(cwmSysUserLinkEntity));
    }

    /**
     * 保存快捷方式超链接顺序
     *
     * @param functionId
     * @param direction
     */
    public void saveUserQuickLinkOrder(Long functionId, String direction) {
        String userId = UserContextUtil.getUserId();
        //过滤条件
        Criterion filter = Restrictions.eq("userId", Long.valueOf(userId));
        //过滤结果
        List<CwmSysUserLinkEntity> existLinks = userLinkService.list(filter, Order.asc("functionOrder"));
        List<Long> existFunctionIds = existLinks.stream().mapToLong(CwmSysUserLinkEntity::getFunctionId).boxed().collect(Collectors.toList());
        CwmSysUserLinkEntity curentUserLink = existLinks.get(existFunctionIds.indexOf(functionId));
        CwmSysUserLinkEntity targetUserLink;
        if ("left".equals(direction.toLowerCase())) {
            targetUserLink = existLinks.get(existFunctionIds.indexOf(functionId) - 1);
        } else {
            targetUserLink = existLinks.get(existFunctionIds.indexOf(functionId) + 1);
        }
        if (null != curentUserLink && null != targetUserLink) {
            Long currentUserLinkOrder = curentUserLink.getFunctionOrder();
            Long targetUserLinkOrder = targetUserLink.getFunctionOrder();
            curentUserLink.setFunctionOrder(targetUserLinkOrder);
            targetUserLink.setFunctionOrder(currentUserLinkOrder);
            userLinkService.save(curentUserLink);
            userLinkService.save(targetUserLink);
        }
    }

    public List<CwmPortalEntity> listUserPortal(Boolean selected) {
        String userId = UserContextUtil.getUserId();
        Criterion filter = Restrictions.eq("userId", Long.valueOf(userId));
        //增加角色过滤
        List<String> roleIds = roleEngine.getRoleModel(false).getRolesOfUser(userId).stream().map(IRole::getId).collect(Collectors.toList());
        List<Long> hadPortalIds = rolePortalService.list(Restrictions.in("roleId", roleIds)).stream().map(CwmSysRolePortalEntity::getPortalId).collect(Collectors.toList());
        hadPortalIds.add(-1L);
        List<Long> portalIds = userPortalService.list(filter, Order.desc("order")).stream().mapToLong(CwmSysUserPortalEntity::getPortalId).boxed().collect(Collectors.toList());
        if (!portalIds.isEmpty()) {
            return selected ? portalService.list(Restrictions.in("id", portalIds)) : portalService.list(Restrictions.not(Restrictions.in("id", portalIds)), Restrictions.in("id", hadPortalIds));
        } else
            return selected ? new ArrayList<>() : portalService.list(Restrictions.in("id", hadPortalIds));
    }

    /**
     * 获取首页当前用户需要显示的磁贴集合,create by gny 2018-5-17
     *
     * @return
     */
    public List<CwmPortalEntity> listUserSelectPortals() {
        String userId = UserContextUtil.getUserId();
        Criterion filter = Restrictions.eq("userId", Long.valueOf(userId));
        List<String> roleIds = roleEngine.getRoleModel(false).getRolesOfUser(userId).stream().map(IRole::getId).collect(Collectors.toList());
        List<Long> hadPortalIds =  rolePortalService.list(new Criterion[]{Restrictions.in("roleId", roleIds)}).stream().map(CwmSysRolePortalEntity::getPortalId).collect(Collectors.toList());
        hadPortalIds.add(-1L);
        List<Long> portalIds = userPortalService.list(filter, Order.desc("order")).stream().mapToLong(CwmSysUserPortalEntity::getPortalId).boxed().collect(Collectors.toList());
        if(!portalIds.isEmpty()){
            List<CwmPortalEntity> list = portalService.list(Restrictions.in("id", portalIds));
            if (list != null) {
                int size = list.size();
                if (size > 4 && size % 2 == 1) {
                    //panel个数大于4且为奇数,需要在最后补一个默认面板来占位
                    CwmPortalEntity bean = new CwmPortalEntity();
                    bean.setId(-1L);
                    bean.setTitle("");
                    bean.setJspath("OrientTdm.HomePage.homePageShow.DefaultPortal");
                    list.add(bean);
                }
            }
            return list;
        }else{
            return new ArrayList<>();
        }
    }

    public void saveUserPortal(Long[] portalIds) {
        String userId = UserContextUtil.getUserId();
        Criterion filter = Restrictions.eq("userId", Long.valueOf(userId));
        List<CwmSysUserPortalEntity> userPortalEntities = userPortalService.list(filter);
        Long portalOrder = 1l;
        if (!userPortalEntities.isEmpty()) {
            portalOrder = userPortalEntities.stream().mapToLong(CwmSysUserPortalEntity::getOrder).max().getAsLong() + 1;
        }
        for (Long portalId : portalIds) {
            CwmSysUserPortalEntity cwmSysUserPortalEntity = new CwmSysUserPortalEntity();
            cwmSysUserPortalEntity.setUserId(Long.valueOf(userId));
            cwmSysUserPortalEntity.setPortalId(portalId);
            cwmSysUserPortalEntity.setOrder(portalOrder);
            userPortalService.save(cwmSysUserPortalEntity);
        }
    }

    public void removeUserPortal(Long portalId) {
        String userId = UserContextUtil.getUserId();
        Criterion filter = Restrictions.and(Restrictions.eq("userId", Long.valueOf(userId)), Restrictions.eq("portalId", Long.valueOf(portalId)));
        userPortalService.list(filter).forEach(cwmSysUserPortalEntity -> userPortalService.delete(cwmSysUserPortalEntity));
    }

    /**
     * @param keyWord
     * @return 全文检索关键词搜索
     */
    public ExtGridData<LuceneSearchResult> doLuceneSearch(String keyWord, Integer page, Integer limit) {
        ExtGridData<LuceneSearchResult> retVal = new ExtGridData<>();
        Map<String, String> paramters = new HashMap<>();
        paramters.put("page", String.valueOf(page));
        paramters.put("limit", String.valueOf(limit));
        DestURI destURI = new DestURI(SystemMngConfig.FILESERVICE_IP, Integer.valueOf(SystemMngConfig.FILESERVICE_PORT), SystemMngConfig.FILESERVICE_CONTEXT + "/lucene/" + keyWord, paramters);
        RestfulResponse restfulResponse = RestfulClient.getHttpRestfulClient().getRequest(destURI, ExtGridData.class, ContentType.APPLICATION_JSON);
        ExtGridData queryResult = (ExtGridData) restfulResponse.getResult();
        retVal.setTotalProperty(queryResult.getTotalProperty());
        List<LuceneSearchResult> luceneSearchResults = new ArrayList<LuceneSearchResult>((int) queryResult.getTotalProperty());
        queryResult.getResults().forEach(map -> {
            LuceneSearchResult luceneSearchResult = new LuceneSearchResult();
            luceneSearchResult.setContent(CommonTools.Obj2String(((Map) map).get("content")));
            luceneSearchResult.setFinalname(CommonTools.Obj2String(((Map) map).get("title")));
            luceneSearchResults.add(luceneSearchResult);
        });
        //补充数据库信息
        if (luceneSearchResults.size() > 0) {
            List<String> finalNames = luceneSearchResults.stream().map(LuceneSearchResult::getFinalname).collect(Collectors.toList());
            List<CwmFile> dbFiles = fileService.findByFinalNames(finalNames);
            dbFiles.forEach(cwmFile -> {
                String finalName = cwmFile.getFinalname();
                LuceneSearchResult catched = luceneSearchResults.stream().filter(luceneSearchResult -> finalName.equals(luceneSearchResult.getFinalname())).findFirst().get();
                BeanUtils.copyProperties(catched, cwmFile);
            });
        }
        //遗弃脏数据
        List<LuceneSearchResult> toRemoveData = luceneSearchResults.stream().filter(luceneSearchResult -> StringUtil.isEmpty(luceneSearchResult.getFilename())).collect(Collectors.toList());
        luceneSearchResults.removeAll(toRemoveData);
        retVal.setResults(luceneSearchResults);
        return retVal;
    }
}
