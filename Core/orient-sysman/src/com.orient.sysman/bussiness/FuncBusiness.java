package com.orient.sysman.bussiness;

import com.orient.sysman.bean.FuncBean;
import com.orient.sysmodel.domain.role.Function;
import com.orient.sysmodel.operationinterface.IFunction;
import com.orient.sysmodel.operationinterface.IRole;
import com.orient.sysmodel.service.role.FunctionService;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import com.orient.web.base.BaseBusiness;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/4/21.
 */
@Service
@Transactional
public class FuncBusiness extends BaseBusiness {
    @Resource(name = "FunctionService")
    FunctionService functionService;

    public List<FuncBean> findByPid(String pid) {
        List<FuncBean> retList = new ArrayList<>();
        //获取第一层功能点节点信息
        List<Function> funcs = functionService.findByPid(Long.parseLong(pid));
        funcs.forEach(function -> {
            FuncBean funcBean = new FuncBean();
            try {
                PropertyUtils.copyProperties(funcBean, function);
            } catch (Exception e) {
                e.printStackTrace();
            }
            funcBean.setId(Long.toString(function.getFunctionid()));
            funcBean.setPid(Long.toString(function.getParentid()));
            funcBean.setText(funcBean.getName());
            funcBean.setIconCls("icon-function");
            funcBean.setResults(findByPid(function.getFunctionid().toString()));
            retList.add(funcBean);
        });
        return retList;
    }

    public Function findById(String id) {
        return functionService.findById(id);
    }

    public void create(Function func) {
        func.setAddFlg("1");
        func.setEditFlg("1");
        func.setDelFlg("1");
        func.setIsShow(1L);
        functionService.createFunction(func);
    }

    public void update(Function func) {
        Function funcDb = functionService.findById(Long.toString(func.getFunctionid()));
        func.setAddFlg(funcDb.getAddFlg());
        func.setEditFlg(funcDb.getEditFlg());
        func.setDelFlg(funcDb.getDelFlg());
        func.setIsShow(funcDb.getIsShow());
        try {
            PropertyUtils.copyProperties(funcDb, func);
        } catch (Exception e) {
            e.printStackTrace();
        }
        functionService.updateFunction(funcDb, true);
    }

    public void delete(String toDelIds) {
        String[] ids = toDelIds.split(",");
        for (String id : ids) {
            functionService.deleteFunction(id);
        }
    }

    /**
     * @return 返回第一层用户功能点描述
     */
    public List<FuncBean> getFirstLevelFunction() {
        List<IFunction> allFunction = new ArrayList<>();
        //转化为前端展现对象
        List<FuncBean> retList = new ArrayList<>();
        String userId = UserContextUtil.getUserId();
        List<IRole> RoleList = roleEngine.getRoleModel(false).getRolesOfUser(userId);
        Map<String, IFunction> allFunMap = new HashMap<>();
        // 遍历角色获取功能点，并去除掉重复的
        for (IRole role : RoleList) {
            List<IFunction> allfunc = role.getAllFunctions();
            for (IFunction function : allfunc) {
                long parentId = function.getParentid(); // Web展现的功能点是4
                if (parentId == IFunction.webShowRootId) {
                    allFunMap.put(function.getName(), function);
                }
            }
        }
        allFunction.addAll(allFunMap.keySet().stream().map(allFunMap::get).collect(Collectors.toList()));
        for (IFunction func : allFunction) {
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
        }
        return retList;
    }

    /**
     * @param node
     * @return 获取子功能点描述
     */
    public List<FuncBean> getSubFunctions(String node) {

        List<FuncBean> retList = new ArrayList<>();
        List<IFunction> allSubFunction = new ArrayList<>();
        String userId = UserContextUtil.getUserId();
        List<IRole> roleList = roleEngine.getRoleModel(false).getRolesOfUser(userId);
        Map<String, IFunction> allSubFunMap = new HashMap<>();
        // 遍历角色获取功能点，并去除掉重复的
        for (IRole role : roleList) {
            List<IFunction> allFun = role.getAllFunctions();
            for (IFunction function : allFun) {
                // 根据父功能点ID过滤出子功能点
                long parentId = Long.valueOf(node);
                if (function.getParentid() == parentId) {
                    allSubFunMap.put(function.getName(), function);
                }
            }
        }
        allSubFunction.addAll(allSubFunMap.keySet().stream().map(allSubFunMap::get).collect(Collectors.toList()));
        for (IFunction func : allSubFunction) {
            FuncBean funcBean = new FuncBean();
            try {
                PropertyUtils.copyProperties(funcBean, func);
            } catch (Exception e) {
                e.printStackTrace();
            }
            funcBean.setId(Long.toString(func.getFunctionid()));
            funcBean.setPid(Long.toString(func.getParentid()));
            funcBean.setText(funcBean.getName());
            funcBean.setIconCls("icon-function");
            funcBean.setHasChildrens(func.getChildrenFunction().size() > 0);
            funcBean.setResults(getSubFunctions(func.getFunctionid().toString()));
            retList.add(funcBean);
        }
        return retList;
    }

    public List<Map<String, String>> getFunImages() {
        List<Map<String, String>> retVal = new ArrayList<>();
        String rootPath = CommonTools.getRootPath();
        String functionImagesDirPath = rootPath + File.separator + "app" + File.separator + "images" + File.separator + "function";
        File funtionImageDir = new File(functionImagesDirPath);
        for (File file : funtionImageDir.listFiles()) {
            String fileName = file.getName();
            if (!fileName.endsWith("_small.png")) {
                Map<String, String> imageDesc = new HashMap<String, String>() {{
                    put("thumb", fileName);
                    put("name", fileName.substring(0, fileName.indexOf(FileOperator.getSuffix(fileName))));
                }};
                retVal.add(imageDesc);
            }
        }
        return retVal;
    }
}
