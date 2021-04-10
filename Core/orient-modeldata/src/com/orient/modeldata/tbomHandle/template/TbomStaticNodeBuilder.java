package com.orient.modeldata.tbomHandle.template;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.bean.ITreeNodeFilterModelBean;
import com.orient.businessmodel.bean.IdQueryCondition;
import com.orient.businessmodel.bean.impl.TreeNodeFilterModelBean;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.modeldata.bean.*;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.form.ModelGridViewEntity;
import com.orient.sysmodel.domain.user.Department;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.IRole;
import com.orient.sysmodel.operationinterface.ITbom;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.sysmodel.service.form.IModelGridViewService;
import com.orient.utils.StringUtil;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 静态Tbom节点构造器
 *
 * @author enjoy
 * @createTime 2016-05-23 14:48
 */
@Component
public class TbomStaticNodeBuilder extends TbomNodeBuilder {
    @Autowired
    @Qualifier("RoleEngine")
    private IRoleUtil roleEngine;

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    protected IBusinessModelService businessModelService;

    @Autowired
    IModelGridViewService modelGridViewService;


    /**
     * 初始化静态属性信息
     *
     * @param tBomNodeMaterial 构建Tbom材料
     * @param tBomNodes        返回结果
     */
    @Override
    public void initNormalAttr(TBomNodeMaterial tBomNodeMaterial, List<TBomNode> tBomNodes) {
        //获取TBom构造材料
        TBomStaticNodeMaterial tBomStaticNodeMaterial = (TBomStaticNodeMaterial) tBomNodeMaterial;
        //获取静态Tbom后端描述
        ITbom hibernateTbomNode = tBomStaticNodeMaterial.getHibernateTbomNode();
        tBomNodes.forEach(tBomNode -> {
            //初始化基础属性
            tBomNode.setStaticDbId(tBomNodeMaterial.getStaticDbId());
            tBomNode.setTbomId(tBomNodeMaterial.getTreeDirId());
            tBomNode.setText(hibernateTbomNode.getName());
            tBomNode.setModelType(String.valueOf(hibernateTbomNode.getType()));
            tBomNode.setUrl(hibernateTbomNode.getUrl());
            tBomNode.setLevel(null == tBomNodeMaterial.getParentNode() ? 1 : tBomNodeMaterial.getParentNode().getLevel() + 1);
        });

    }

    /**
     * @param tBomNodeMaterial 构建Tbom材料
     * @param tBomNodes        返回结果
     */
    @Override
    public void initTbomModel(TBomNodeMaterial tBomNodeMaterial, List<TBomNode> tBomNodes) {
        String userId = UserContextUtil.getUserId();
        TBomStaticNode tBomStaticNode = new TBomStaticNode();
        //获取TBom构造材料
        TBomStaticNodeMaterial tBomStaticNodeMaterial = (TBomStaticNodeMaterial) tBomNodeMaterial;
        //获取静态Tbom后端描述
        ITbom hibernateTbomNode = tBomStaticNodeMaterial.getHibernateTbomNode();
        //注入过滤条件
        ITreeNodeFilterModelBean nodeFilter = null;
        if (hibernateTbomNode.getExpression() != null && !hibernateTbomNode.getExpression().isEmpty()) {
            nodeFilter = new TreeNodeFilterModelBean();
            String formatedExp = formatSqlFilterExpression(hibernateTbomNode.getExpression(), roleEngine);
            nodeFilter.setStatic_filter(formatedExp);
        }
        //注入模型、视图信息
        IBusinessModel bm = null;
        if (null == hibernateTbomNode.getType()) {

        } else if (hibernateTbomNode.getType() == 0) {
            tBomStaticNode.setModelId(hibernateTbomNode.getTable().getId());
            bm = businessModelService.getBusinessModelById(userId, hibernateTbomNode.getTable().getId(), null, EnumInter.BusinessModelEnum.Table);

        } else {
            tBomStaticNode.setModelId(hibernateTbomNode.getView().getId());
            bm = businessModelService.getBusinessModelById(userId, hibernateTbomNode.getView().getId(), null, EnumInter.BusinessModelEnum.View);
        }
        if (null != bm) {
            if (nodeFilter != null) {
                bm.setTreeNodeFilterModelBean(nodeFilter);
            }
            //注入动态过滤条件
            IBusinessModelQuery modelquery = orientSqlEngine.getBmService().createModelQuery(bm);
            IdQueryCondition idQueryCondition = modelquery.getIdQueryCondition();

            //绑定至前段对象
            TBomModel tBomModel = new TBomModel(bm.getId(), bm.getDisplay_name(), hibernateTbomNode.getType().toString());
            tBomModel.setTemplateId(hibernateTbomNode.getTemplateid());
            if (!StringUtil.isEmpty(hibernateTbomNode.getTemplateid())) {
                ModelGridViewEntity modelGridViewEntity = modelGridViewService.getById(Long.valueOf(hibernateTbomNode.getTemplateid()));
                if (null != modelGridViewEntity) {
                    tBomModel.setTemplateJS(modelGridViewEntity.getExtendclass());
                    tBomModel.setUsePage(modelGridViewEntity.getNeedpage().intValue() == 1);
                    tBomModel.setPageSize(modelGridViewEntity.getPagesize().intValue());
                }
            }
            CustomerFilter customerFilter = new CustomerFilter(idQueryCondition);
            tBomModel.setDefaultFilter(customerFilter);
            tBomStaticNode.gettBomModels().add(tBomModel);
        }
        tBomNodes.add(tBomStaticNode);
    }

    @Override
    public void initSpecial(TBomNodeMaterial tBomNodeMaterial, List<TBomNode> tBomNodes) {
        TBomStaticNodeMaterial tBomStaticNodeMaterial = (TBomStaticNodeMaterial) tBomNodeMaterial;
        tBomNodes.forEach(tBomNode -> {
            //静态节点排序
            TBomStaticNode tBomStaticNode = (TBomStaticNode) tBomNode;
            tBomStaticNode.setOrder(tBomStaticNodeMaterial.getHibernateTbomNode().getOrder());
        });
    }
}

