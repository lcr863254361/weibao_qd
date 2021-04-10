package com.orient.pvm.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.edm.init.FileServerConfig;
import com.orient.pvm.bean.CheckModelDataTemplateWrapper;
import com.orient.pvm.bean.CheckTemplateInfo;
import com.orient.pvm.bean.CheckTemplateParseResult;
import com.orient.pvm.validate.builderpattern.Builder;
import com.orient.pvm.validate.builderpattern.director.DefaultDirector;
import com.orient.pvm.validate.builderpattern.director.Director;
import com.orient.sysmodel.domain.pvm.CheckModelDataTemplate;
import com.orient.sysmodel.service.IBaseService;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.pvm.ICheckModelDataTemplateService;
import com.orient.utils.BeanUtils;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengbin
 * @create 2016-07-30 下午9:42
 */
@Component
public class CheckModelDataTemplateBusiness extends BaseHibernateBusiness<CheckModelDataTemplate> {

    @Autowired
    private ICheckModelDataTemplateService checkModelDataTemplateService;

    @Autowired
    FileServerConfig fileServerConfig;

    @Autowired
    @Qualifier("defaultValidatorBuilder")
    Builder builder;

    @Override
    public IBaseService getBaseService() {
        return checkModelDataTemplateService;
    }

    public ExtGridData<CheckModelDataTemplateWrapper> listSpecial(Integer page, Integer limit, CheckModelDataTemplate filter) {
        ExtGridData<CheckModelDataTemplateWrapper> retVal = new ExtGridData<>();
        PageBean pageBean = new PageBean();
        pageBean.setRows(null == limit ? Integer.MAX_VALUE : limit);
        pageBean.setPage(null == page ? -1 : page);
        pageBean.setExampleFilter(filter);
        pageBean.addOrder(Order.asc("groupname"));
        List<CheckModelDataTemplate> queryResult = checkModelDataTemplateService.listByPage(pageBean);
        //转化为包装类
        List<CheckModelDataTemplateWrapper> dataChangedQueryResult = dataChange(queryResult);
        retVal.setTotalProperty(pageBean.getTotalCount());
        retVal.setResults(dataChangedQueryResult);
        return retVal;
    }

    private List<CheckModelDataTemplateWrapper> dataChange(List<CheckModelDataTemplate> queryResult) {
        List<CheckModelDataTemplateWrapper> retVal = new ArrayList<>();
        queryResult.forEach(checkModelDataTemplate -> {
            CheckModelDataTemplateWrapper tmpBean = new CheckModelDataTemplateWrapper();
            BeanUtils.copyProperties(tmpBean, checkModelDataTemplate);
            //加入额外信息
            String checkModelId = checkModelDataTemplate.getCheckmodelid().toString();
            if (!StringUtil.isEmpty(checkModelId)) {
                IBusinessModel model = businessModelService.getBusinessModelById(checkModelId, EnumInter.BusinessModelEnum.BusinessModelEnum.Table);
                if (null != model) {
                    tmpBean.setCheckmodelid_display(model.getDisplay_name());
                    retVal.add(tmpBean);
                }
            }
        });
        return retVal;
    }


    public CheckTemplateInfo getCheckTemplateInfo(CheckModelDataTemplate tmeplate) {
        if (null != tmeplate) {
            String filePath = tmeplate.getTemplatepath();
            Director defaultDirector = new DefaultDirector(filePath, tmeplate.getCheckmodelid().toString());
            CheckTemplateParseResult checkTemplateParseResult = defaultDirector.doBuild(builder);
            return checkTemplateParseResult.getCheckTemplateInfo();
        }
        return null;
    }

    /**
     * 根据传入的模型id,取得所有的列头信息
     *
     * @param modelId
     * @return
     */
    public List<String> getColumns(String modelId) {
        IBusinessModel businessModel = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
        List<IBusinessColumn> columns = businessModel.getGridShowColumns();
        List<String> columnNames = new ArrayList<>();
        for (IBusinessColumn column : columns) {
            columnNames.add(column.getDisplay_name());
        }
        return columnNames;
    }
}
