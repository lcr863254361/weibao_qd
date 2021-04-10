package com.orient.background.doctemplate.generate;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.orient.background.doctemplate.bean.DocHandlerData;
import com.orient.background.doctemplate.handler.IDocHandler;
import com.orient.background.doctemplate.transform.DocTransformRegister;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.BusinessModelEdge;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.metamodel.jbossgraph.JBossSchemaGraph;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.sysmodel.domain.doc.CwmDocHandlerEntity;
import com.orient.sysmodel.domain.doc.CwmDocReportItemsEntity;
import com.orient.sysmodel.domain.doc.CwmDocReportsEntity;
import com.orient.sysmodel.service.doc.IDocHandlerService;
import com.orient.utils.CommonTools;
import com.orient.web.base.BaseHibernateBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-12-07 14:57
 */
@Component
@Scope(value = "prototype")
public class ReportGenerator extends BaseHibernateBusiness {

    @Autowired
    IDocHandlerService docHandlerService;

    @Autowired
    DocTransformRegister docTransformRegister;

    private Map<String, IBusinessModel> modelCache = new HashMap<>();

    private Map<String, List<Map>> modelDataCache = new HashMap<>();

    private Map<Long, IDocHandler> docHandlerCache = new HashMap<>();

    private JBossSchemaGraph jBossSchemaGraph;

    public void doGenerate(CwmDocReportsEntity docReportsEntity, String dataId, String docPath) {
        modelCache.clear();
        modelDataCache.clear();
        //get mainModel
        String mainModelId = docReportsEntity.getModelId();
        IBusinessModel mainModel = getBusinessModel(mainModelId);
        //init schema graph
        jBossSchemaGraph = new JBossSchemaGraph(mainModel.getSchema());
        //get mainData
        getModelData(mainModel, new CustomerFilter("ID", EnumInter.SqlOperation.Equal, dataId));
        try {
            Document dsposeDoc = new Document(docPath);
            DocumentBuilder builder = new DocumentBuilder(dsposeDoc);
            List<CwmDocReportItemsEntity> docReportItems = docReportsEntity.getReportItemsEntityList();
            docReportItems.forEach(docReportItemsEntity -> {
                IDocHandler docHandler = getDocHandlerById(docReportItemsEntity.getDocHandlerId());
                DocHandlerData data = getBookMarkData(docReportItemsEntity);
                docHandler.doHandler(builder, docReportItemsEntity, data);
            });
            //save doc
            dsposeDoc.save(docPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param docHandlerId
     * @return get docHandler by handlerId
     */
    private IDocHandler getDocHandlerById(Long docHandlerId) {
        IDocHandler retVal = docHandlerCache.get(docHandlerId);
        if (null == retVal) {
            CwmDocHandlerEntity docHandlerEntity = docHandlerService.getById(docHandlerId);
            String springBeanName = docHandlerEntity.getBeanName();
            retVal = (IDocHandler) OrientContextLoaderListener.Appwac.getBean(springBeanName);
            docHandlerCache.put(docHandlerId, retVal);
        }
        return retVal;
    }

    private DocHandlerData getBookMarkData(CwmDocReportItemsEntity docReportItemsEntity) {

        DocHandlerData retVal = null;
        String bookMarkName = docReportItemsEntity.getBookmarkName();
        String[] splitArray = bookMarkName.split("\\.");
        int length = splitArray.length;
        if(length > 2){
            //the firt one is schema name
            String schemaId = splitArray[0];
            ISchema schema = metaEngine.getMeta(false).getISchemaById(schemaId);
            //the second one is model name
            String modelPath = splitArray[1];
            //split the relation path
            String[] arrayPath = modelPath.split("★");
            String modelName = arrayPath[arrayPath.length - 1];
            String modelId = schema.getAllTables().stream().filter(iTable -> modelName.equals(iTable.getDisplayName())).findFirst().get().getId();
            List<Map> dataList = modelDataCache.get(modelId);
            IBusinessModel model = modelCache.get(modelId);
            if (null == model) {
                IBusinessModel mainModel = modelCache.get(docReportItemsEntity.getBelongReport().getModelId());
                //get businessModel
                model = getBusinessModel(modelId);
                //get the shortest path
                initModelRelation(model, mainModel, arrayPath);
                //get data
                dataList = getModelData(model);
            }
            if (3 == length) {
                //model grid
                retVal = docTransformRegister.getDocTransform("businessModel").doTransform(model, dataList);
            } else if (4 == length) {
                //model column
                String columnName = splitArray[2];
                //get IBusinessColumn
                IBusinessColumn businessColumn = model.getAllBcCols().stream().filter(iBusinessColumn -> iBusinessColumn.getDisplay_name().equals(columnName)).findFirst().get();
                String colType = businessColumn.getColType().toString();
                retVal = docTransformRegister.getDocTransform(colType).doTransform(businessColumn, dataList);
            }
        }
        return retVal;
    }

    void initModelRelation(IBusinessModel source, IBusinessModel destinion, String[] path) {
        ISchema schema = source.getSchema();
        List<BusinessModelEdge> bmEdgeList = new ArrayList<>();
        int length = path.length;
        for (int i = 0; i < path.length; i++) {
            if (i < length - 1) {
                String fromName = path[i];
                String toName = path[i + 1];
                String fromModelId = schema.getAllTables().stream().filter(iTable -> fromName.equals(iTable.getDisplayName())).findFirst().get().getId();
                String toModelId = schema.getAllTables().stream().filter(iTable -> toName.equals(iTable.getDisplayName())).findFirst().get().getId();
                jBossSchemaGraph.getEdgeType().forEach(((edge, aBoolean) -> {
                    ITable fromTable = (ITable) edge.getFrom().getData();
                    ITable toTable = (ITable) edge.getTo().getData();
                    if (fromModelId.equals(fromTable.getId()) && toModelId.equals(toTable.getId())) {
                        IBusinessModel fromModel = fromModelId.equals(source.getId()) ? source : fromModelId.equals(destinion.getId()) ? destinion : businessModelService.getBusinessModelById(fromModelId, EnumInter.BusinessModelEnum.Table);
                        IBusinessModel toModel = toModelId.equals(source.getId()) ? source : toModelId.equals(destinion.getId()) ? destinion : businessModelService.getBusinessModelById(toModelId, EnumInter.BusinessModelEnum.Table);
                        BusinessModelEdge bmEdge = new BusinessModelEdge(fromModel, toModel, edge.getCost());
                        bmEdge.setManyToMany(aBoolean);
                        bmEdgeList.add(bmEdge);
                    }
                }));
            }
        }
        if (!CommonTools.isEmptyList(bmEdgeList)) {
            List<List<BusinessModelEdge>> bmEdgeListList = new ArrayList<List<BusinessModelEdge>>() {{
                add(bmEdgeList);
            }};
            source.setPedigree(bmEdgeListList);
        }
    }

    /**
     * @param businessModel
     * @param customerFilter
     * @return get model datas by businessModel and filters
     */
    List<Map> getModelData(IBusinessModel businessModel, CustomerFilter... customerFilter) {
        List<Map> retVal = modelDataCache.get(businessModel.getId());
        if (null == retVal) {
            for (CustomerFilter filter : customerFilter) {
                businessModel.appendCustomerFilter(filter);
            }
            retVal = orientSqlEngine.getBmService().createModelQuery(businessModel).list();
            //change to the realValue
            businessModelService.dataChangeModel(orientSqlEngine, businessModel, retVal, true);
            //cache
            modelDataCache.put(businessModel.getId(), retVal);
        }
        return retVal;
    }

    /**
     * @param modelId
     * @return get businessModel by modelId
     */
    IBusinessModel getBusinessModel(String modelId) {
        IBusinessModel retVal = modelCache.get(modelId);
        if (null == retVal) {
            retVal = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
            //cache
            modelCache.put(retVal.getId(), retVal);
        }
        return retVal;
    }

}
