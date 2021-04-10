package com.orient.template.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.annotation.BindModel;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.metamodel.metadomain.Schema;
import com.orient.sysmodel.domain.template.CollabTemplate;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.domain.user.UserDAO;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.template.impl.CollabTemplateService;
import com.orient.template.model.CollabTemplateMngTreeNode;
import com.orient.utils.CommonTools;
import com.orient.utils.PageUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import org.apache.commons.lang.mutable.MutableLong;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.orient.sysmodel.domain.template.CollabTemplate.EXPORT_USER_ID;
import static com.orient.sysmodel.domain.template.CollabTemplate.PRIVATE_TEMP;

/**
 * TemplateInfoBusiness
 *
 * @author Seraph
 *         2016-09-21 下午4:33
 */
@Component
public class TemplateInfoBusiness extends BaseBusiness {

    public List<CollabTemplate> getMyTemplates(String userId, String modelName, String schemaId, Integer page, Integer limit, MutableLong outTotalCnt) {

        Class bindCls = this.orientSqlEngine.getTypeMappingBmService().getModelBindClass(modelName, schemaId, false);
        List<CollabTemplate> collabTemplates = this.collabTemplateService.list(Restrictions.or(Restrictions.eq(EXPORT_USER_ID, userId),
                        Restrictions.and(Restrictions.ne(EXPORT_USER_ID, userId), Restrictions.eq(PRIVATE_TEMP, Boolean.TRUE))),
                Restrictions.eq(CollabTemplate.TYPE, bindCls.getName()));
        outTotalCnt.setValue(collabTemplates.size());
        return PageUtil.page(collabTemplates, page, limit);
    }

    public List<CollabTemplate> getBmTemplates(Integer page, Integer limit, MutableLong outTotalCnt, String name, String creator, String modelName, String schemaId) {
        Class bindCls = this.orientSqlEngine.getTypeMappingBmService().getModelBindClass(modelName, schemaId, false);

        PageBean pageBean = new PageBean();
        pageBean.setRows(limit);
        pageBean.setPage(page);
        pageBean.addCriterion(Restrictions.eq(CollabTemplate.TYPE, bindCls.getName()));
        if (!CommonTools.isNullString(name)) {
            pageBean.addCriterion(Restrictions.like(CollabTemplate.NAME, name));
        }

        if (!CommonTools.isNullString(creator)) {
            List users = this.userDAO.findByAllName(creator);
            if (users.size() == 0) {
                outTotalCnt.setValue(0);
                return UtilFactory.newArrayList();
            }

            List<String> userIds = UtilFactory.newArrayList();
            for (Object user : users) {
                userIds.add(((User) user).getId());
            }
            pageBean.addCriterion(Restrictions.in(CollabTemplate.EXPORT_USER_ID, userIds));
        }

        List<CollabTemplate> collabTemplates = this.collabTemplateService.listByPage(pageBean);
        outTotalCnt.setValue(this.collabTemplateService.count());
        outTotalCnt.setValue(collabTemplates.size());
        return collabTemplates;
    }

    /**
     * get the tree node of template, nodes are template types which currently we assume are classes binding to business model with {@link BindModel}
     *
     * @param parDataId
     * @return
     */
    public List<CollabTemplateMngTreeNode> getNextLayerTreeNodes(String parDataId) {
        List<CollabTemplateMngTreeNode> retV = UtilFactory.newArrayList();

        if (!"-1".equals(parDataId)) {
            return retV;
        }

        List<String> types = this.collabTemplateService.getDistinctTypes();
        for (String type : types) {
            try {
                Class cls = Class.forName(type);
                BindModel bindModel = (BindModel) cls.getAnnotation(BindModel.class);
                Schema schema = this.metaEngine.getMeta(false).getLastVersionOfName(bindModel.schemaName());
                String schemaId = schema.getId();

                IBusinessModel bm = this.businessModelService.getBusinessModelBySName(bindModel.modelName(), schemaId, EnumInter.BusinessModelEnum.Table);

                CollabTemplateMngTreeNode node = new CollabTemplateMngTreeNode();
                node.setId(bm.getId());
                node.setBmTemplate(true);
                node.setIconCls("icon-" + bindModel.modelName());
                node.setText(bm.getDisplay_name());
                node.setModelName(bindModel.modelName());
                node.setSchemaId(schemaId);
                node.setType(type);
                node.setLeaf(true);
                node.setIconCls("icon-" + bm.getMatrix().getName().toUpperCase());

                retV.add(node);
            } catch (ClassNotFoundException e) {
                continue;
            }
        }
        return retV;
    }

    @Autowired
    private CollabTemplateService collabTemplateService;
    @Autowired
    private UserDAO userDAO;

}
