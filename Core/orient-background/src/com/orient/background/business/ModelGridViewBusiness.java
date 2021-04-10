package com.orient.background.business;

import com.orient.background.bean.CompareResult;
import com.orient.background.bean.ModelAndTemplateVO;
import com.orient.background.bean.ModelGridViewEntityWrapper;
import com.orient.background.util.DBModelCompare;
import com.orient.background.util.ModelAndBtnValidator;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.edm.init.FileServerConfig;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.sysmodel.domain.form.ModelBtnInstanceEntity;
import com.orient.sysmodel.domain.form.ModelGridViewEntity;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.form.IModelBtnInstanceService;
import com.orient.sysmodel.service.form.IModelGridViewService;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.thoughtworks.xstream.XStream;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 模型表格模板业务处理类
 *
 * @author enjoy
 * @creare 2016-04-06 9:52
 */
@Component
public class ModelGridViewBusiness extends BaseHibernateBusiness<ModelGridViewEntity> {

    @Autowired
    IModelGridViewService modelGridViewService;

    @Autowired
    FileServerConfig fileServerConfig;

    @Autowired
    IModelBtnInstanceService btnInstanceService;

    @Override
    public IModelGridViewService getBaseService() {
        return modelGridViewService;
    }


    public ExtGridData<ModelGridViewEntityWrapper> listByCustomer(Integer page, Integer limit, ModelGridViewEntity filter) {
        ExtGridData<ModelGridViewEntityWrapper> retVal = new ExtGridData<>();
        filter.setIsvalid(1l);
        PageBean pageBean = new PageBean();
        pageBean.setRows(limit);
        pageBean.setPage(page);

        String modelId = filter.getModelid();
        if(modelId!=null && !"".equals(modelId)) {
            filter.setModelid(null);
            pageBean.addCriterion(Restrictions.eq("modelid", modelId));
        }
        pageBean.setExampleFilter(filter);

        List<ModelGridViewEntity> queryData = modelGridViewService.listByPage(pageBean);
        List<ModelGridViewEntityWrapper> results = dataChange(queryData);
        retVal.setTotalProperty(pageBean.getTotalCount());
        retVal.setResults(results);
        retVal.setTotalProperty(pageBean.getTotalCount());
        return retVal;
    }

    private List<ModelGridViewEntityWrapper> dataChange(List<ModelGridViewEntity> originalValue) {
        List<ModelGridViewEntityWrapper> retVal = new ArrayList<>();
        originalValue.forEach(modelGridViewEntity -> {
            ModelGridViewEntityWrapper loopItem = new ModelGridViewEntityWrapper();
            BeanUtils.copyProperties(loopItem, modelGridViewEntity);
            String modelId = modelGridViewEntity.getModelid();
            if (!StringUtil.isEmpty(modelId)) {
                IBusinessModel model = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
                if (null != model) {
                    String modelDisplayName = model.getDisplay_name();
                    loopItem.setModelid_display(modelDisplayName);
                }
            }
            retVal.add(loopItem);
        });
        return retVal;
    }

    public List<ModelGridViewEntity> getModelTemplateByModelId(String modelId) {
        return modelGridViewService.list(Restrictions.eq("modelid", modelId), Restrictions.eq("isvalid", 1l));
    }

    public void doClearData() {
        List<ModelGridViewEntity> toDelData = modelGridViewService.list().stream().filter(modelGridViewEntity -> null == getBusinessModelById(modelGridViewEntity.getModelid())).collect(Collectors.toList());
        if (!CommonTools.isEmptyList(toDelData)) {
            toDelData.forEach(modelGridViewEntity -> modelGridViewService.delete(modelGridViewEntity));
        }
    }

    @Override
    public void delete(Long[] toDelIds) {
        for (Long toDelId : toDelIds) {
            ModelGridViewEntity modelGridViewEntity = modelGridViewService.getById(toDelId);
            modelGridViewEntity.setIsvalid(0l);
            modelGridViewService.save(modelGridViewEntity);
        }
    }

    public File doExport(String ids) {

        List<ModelGridViewEntity> modelGridViewEntities = modelGridViewService.list(Restrictions.in("id", CommonTools.stringListToLongList(CommonTools.arrayToList(ids.split(",")))));
        //save to tmp file
        String rootPath = CommonTools.getRootPath();
        File tmpDir = new File(rootPath + File.separator + "tmp");
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
        //
        File retVal = null;
        String fileName = System.currentTimeMillis() + ".xml";
        try {
            XStream xStream = new XStream();
            byte[] bytes = xStream.toXML(modelGridViewEntities).getBytes("UTF-8");
            String filePath = CommonTools.saveFile(new ByteArrayInputStream(bytes), tmpDir.getAbsolutePath(), fileName);
            retVal = new File(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    public String doImport(MultipartFile dataFile) {
        final String[] retVal = {""};
        try {
            XStream xStream = new XStream();
            List<ModelGridViewEntity> modelGridViewEntities = (List<ModelGridViewEntity>) xStream.fromXML(new String(dataFile.getBytes(), "UTF-8"));
            //compare and validate
            CompareResult<ModelGridViewEntity> compareResult = this.doCompare(modelGridViewEntities);
            //insert and update and delete
            compareResult.getToDeleteData().forEach(modelGridViewEntity -> modelGridViewService.delete(modelGridViewEntity));
            compareResult.getToUpdateData().forEach(modelGridViewEntity -> modelGridViewService.merge(modelGridViewEntity));
            compareResult.getToCreateData().forEach(modelGridViewEntity -> modelGridViewService.save(modelGridViewEntity));
            //
            compareResult.getInvalidData().entrySet().forEach(entry -> retVal[0] += entry.getValue().getName() + "：" + entry.getKey() + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retVal[0];
    }

    /**
     * @param sourceData
     * @return
     */
    private CompareResult<ModelGridViewEntity> doCompare(List<ModelGridViewEntity> sourceData) {
        CompareResult<ModelGridViewEntity> compareResult = new CompareResult<>();
        List<ModelGridViewEntity> currentDatas = modelGridViewService.list();
        DBModelCompare<ModelGridViewEntity> dbModelCompare = new DBModelCompare<>(currentDatas);
        //get all models
        List<ITable> allModels = new ArrayList<>();
        metaEngine.getMeta(false).getSchemas().entrySet().forEach(stringSchemaEntry -> {
            allModels.addAll(stringSchemaEntry.getValue().getAllTables());
        });
        //get all buttons
        List<ModelBtnInstanceEntity> btnInstanceEntities = btnInstanceService.list();
        ModelAndBtnValidator validator = new ModelAndBtnValidator(allModels, btnInstanceEntities);
        sourceData.forEach(source -> {
            //check model exists
            String modelId = source.getModelid();
            //check column exists
            Set<String> columnIds = new HashSet<>();
            Set<String> empty = new HashSet<>();
            columnIds.addAll(StringUtil.isEmpty(source.getAddfield()) ? empty : CommonTools.arrayToList(CommonTools.null2String(source.getAddfield()).split(",")));
            columnIds.addAll(StringUtil.isEmpty(source.getEditfield()) ? empty : CommonTools.arrayToList(CommonTools.null2String(source.getEditfield()).split(",")));
            columnIds.addAll(StringUtil.isEmpty(source.getDisplayfield()) ? empty : CommonTools.arrayToList(CommonTools.null2String(source.getDisplayfield()).split(",")));
            columnIds.addAll(StringUtil.isEmpty(source.getDetailfield()) ? empty : CommonTools.arrayToList(CommonTools.null2String(source.getDetailfield()).split(",")));
            //check button exists
            Set<String> buttonIds = new HashSet<>();
            buttonIds.addAll(StringUtil.isEmpty(source.getBtns()) ? empty : CommonTools.arrayToList(source.getBtns().split(",")));
            String errorStr = validator.doValidate(modelId, columnIds, buttonIds);
            if (StringUtil.isEmpty(errorStr)) {
                int actionType = dbModelCompare.doCompare(source, "id");
                if (actionType == DBModelCompare.ADD_ACTION) {
                    compareResult.getToCreateData().add(source);
                } else if (actionType == DBModelCompare.UPDATE_ACTION) {
                    compareResult.getToUpdateData().add(source);
                } else if (actionType == DBModelCompare.DELETE_ACTION) {
                    compareResult.getToDeleteData().add(source);
                }
            } else {
                compareResult.getInvalidData().put(errorStr, source);
            }
        });
        return compareResult;
    }

    /**
     * @param templateName
     * @return 根据模板名称获取模板id 及 模型id
     */
    public ModelAndTemplateVO getModelIdAndTempIdByTemplateName(String templateName) {
        ModelAndTemplateVO modelAndTemplate = new ModelAndTemplateVO();
        List<ModelGridViewEntity> modelGridViewEntities = modelGridViewService.list(Restrictions.eq("name", templateName),Restrictions.eq("isvalid",1l));
        if (!CommonTools.isEmptyList(modelGridViewEntities)) {
            String templateId = modelGridViewEntities.get(0).getId().toString();
            modelAndTemplate.setTemplateId(templateId);
            modelAndTemplate.setModelId(modelGridViewEntities.get(0).getModelid());
        } else {
            throw new OrientBaseAjaxException("", "未找到模板名称为【" + templateName + "】的模板");
        }
        return modelAndTemplate;
    }
}
