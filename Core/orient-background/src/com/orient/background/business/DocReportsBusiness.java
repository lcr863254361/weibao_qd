package com.orient.background.business;

import com.aspose.words.Bookmark;
import com.aspose.words.BookmarkCollection;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.orient.background.bean.CwmDocReportsEntityWrapper;
import com.orient.background.bean.DocModelNode;
import com.orient.background.bean.ModelColumnHandler;
import com.orient.background.doctemplate.generate.ReportGenerator;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.edm.init.FileServerConfig;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.metamodel.jbossgraph.JBossSchemaGraph;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.sysmodel.domain.doc.CwmDocColumnScopeEntity;
import com.orient.sysmodel.domain.doc.CwmDocReportItemsEntity;
import com.orient.sysmodel.domain.doc.CwmDocReportsEntity;
import com.orient.sysmodel.service.doc.IDocReportItemsService;
import com.orient.sysmodel.service.doc.IDocReportsService;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.web.model.BaseNode;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Restrictions;
import org.jboss.util.graph.Edge;
import org.jboss.util.graph.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class DocReportsBusiness extends BaseHibernateBusiness<CwmDocReportsEntity> {

    @Autowired
    IDocReportsService docReportsService;

    @Autowired
    DocHandlerScopeBusiness docHandlerScopeBusiness;

    @Autowired
    FileServerConfig fileServerConfig;

    @Autowired
    IDocReportItemsService docReportItemsService;

    @Override
    public IDocReportsService getBaseService() {
        return docReportsService;
    }

    /**
     * @param page
     * @param limit
     * @param filter
     * @return 增加模型显示名
     */
    public ExtGridData<CwmDocReportsEntityWrapper> listSpecial(Integer page, Integer limit, CwmDocReportsEntity filter) {
        ExtGridData<CwmDocReportsEntity> sourceData = super.list(page, limit, filter);
        List<CwmDocReportsEntity> sourceList = sourceData.getResults();
        List<CwmDocReportsEntityWrapper> realList = new ArrayList<>();
        sourceList.forEach(cwmDocReportsEntity -> {
            String modelId = cwmDocReportsEntity.getModelId();
            IBusinessModel businessModel = businessModelService.getBusinessModelById(modelId, "0".equals(cwmDocReportsEntity.getIsView()) ? EnumInter.BusinessModelEnum.Table : EnumInter.BusinessModelEnum.View);
            CwmDocReportsEntityWrapper wrapper = new CwmDocReportsEntityWrapper(cwmDocReportsEntity);
            wrapper.setModelId_display(businessModel.getDisplay_name());
            realList.add(wrapper);
        });
        ExtGridData<CwmDocReportsEntityWrapper> retVal = new ExtGridData<>();
        retVal.setTotalProperty(sourceData.getTotalProperty());
        retVal.setResults(realList);
        return retVal;
    }

    /**
     * @param mainModelId
     * @param node
     * @return get related model info
     */
    public ExtGridData<BaseNode> getRelatedModels(String mainModelId, String node) {
        ExtGridData<BaseNode> extGridData = new ExtGridData<>();
        List<BaseNode> results = new ArrayList<>();
        if ("root".equals(node)) {
            IBusinessModel businessModel = businessModelService.getBusinessModelById(mainModelId, EnumInter.BusinessModelEnum.Table);
            DocModelNode baseNode = new DocModelNode(businessModel.getId(), businessModel.getDisplay_name(), "icon-model", false);
            baseNode.setModelId(businessModel.getId());
            baseNode.setSchemaId(businessModel.getSchema().getId());
            baseNode.setSchemaName(businessModel.getSchema().getName());
            results.add(baseNode);
        } else {
            node = node.indexOf("_") != -1 ? node.substring(node.indexOf("_") + 1, node.length()) : node;
            IBusinessModel businessModel = businessModelService.getBusinessModelById(node, EnumInter.BusinessModelEnum.Table);
            ISchema schema = businessModel.getSchema();
            JBossSchemaGraph jBossSchemaGraph = new JBossSchemaGraph(schema);
            Vertex<ITable> vertex = jBossSchemaGraph.getIdToVertexMap().get(businessModel.getId());
            final String finalNode = node;
            vertex.getOutgoingEdges().forEach(out -> {
                Edge<ITable> edge = (Edge) out;
                ITable destination = edge.getTo().getData();
                DocModelNode baseNode = new DocModelNode(finalNode + "_" + destination.getId(), destination.getDisplayName(), "icon-model", false);
                baseNode.setModelId(destination.getId());
                baseNode.setSchemaId(destination.getSchema().getId());
                baseNode.setSchemaName(destination.getSchema().getName());
                results.add(baseNode);
            });
        }
        extGridData.setResults(results);
        extGridData.setTotalProperty(results.size());
        return extGridData;
    }

    /**
     * @param modelId 模型id
     * @return 根据模型id 获取 该模型下所有字段描述
     */
    public ExtGridData<ModelColumnHandler> listColumns(String modelId) {
        ExtGridData<ModelColumnHandler> retVal = new ExtGridData<>();
        IBusinessModel model = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
        List<IBusinessColumn> columnList = model.getAllBcCols();
        Map<String, EnumInter> columnNameAndType = new LinkedHashMap<>();
        columnList.forEach(iBusinessColumn -> {
            String columnName = iBusinessColumn.getDisplay_name();
            EnumInter businessColumnEnum = iBusinessColumn.getColType();
            columnNameAndType.put(columnName, businessColumnEnum);
        });
        //get default column handler
        Map<String, CwmDocColumnScopeEntity> docColumnScopeEntityMap = docHandlerScopeBusiness.getDefaultColumnHandler(columnNameAndType);
        List<ModelColumnHandler> results = new ArrayList<>();
        docColumnScopeEntityMap.forEach((columnName, docColumnScope) -> {
            ModelColumnHandler modelColumnHandler = new ModelColumnHandler();
            modelColumnHandler.setColumnName(columnName);
            modelColumnHandler.setDocHandler(docColumnScope.getBelongDocHandler().getShowName());
            modelColumnHandler.setDocHandlerId(docColumnScope.getBelongDocHandler().getId().toString());
            results.add(modelColumnHandler);
        });
        retVal.setResults(results);
        retVal.setTotalProperty(results.size());
        return retVal;
    }

    /**
     * @param formValue 待新增模板
     *                  保存新增模板
     *                  1.保存至数据库
     *                  2.清理脏标签
     *                  3.同步至tomcat目录，方便下次修改
     */
    public void saveSpecial(CwmDocReportsEntity formValue) {
        //change filePath
        String reportRealName = formValue.getFilePath();
        String realPath = fileServerConfig.getFtpHome() + "docReports" + File.separator + reportRealName;
        formValue.setFilePath(realPath);
        formValue.setCreateUser(UserContextUtil.getUserAllName());
        formValue.setCreateTime(new Date());
        docReportsService.save(formValue);
        //analyze reports and link report items
        try {
            Document dsposeDoc = new Document(realPath);
            DocumentBuilder builder = new DocumentBuilder(dsposeDoc);
            BookmarkCollection bookmarks = builder.getDocument().getRange().getBookmarks();
            Iterator<Bookmark> bookmarkIterator = bookmarks.iterator();
            while (bookmarkIterator.hasNext()) {
                Bookmark bookmark = bookmarkIterator.next();
                String bookMarkName = bookmark.getName();
                Long bookMarkId = Long.valueOf(bookMarkName.substring(bookMarkName.lastIndexOf("_") + 1, bookMarkName.length()));
                CwmDocReportItemsEntity docReportItemsEntity = docReportItemsService.getById(bookMarkId);
                docReportItemsEntity.setBelongReport(formValue);
                formValue.getReportItemsEntityList().add(docReportItemsEntity);
                docReportItemsService.update(docReportItemsEntity);
            }
        } catch (Exception e) {
            throw new OrientBaseAjaxException("", "未知错误" + e.toString());
        }
        docReportsService.update(formValue);
        //clean dirty data
        docReportItemsService.list(Restrictions.isNull("belongReport")).forEach(docReportItemsEntity -> docReportItemsService.delete(docReportItemsEntity));
        //copy to tomcat dir
        String contextPath = CommonTools.getRootPath() + File.separator + "DocTemplate" + File.separator + reportRealName;
        FileOperator.copyFile(realPath, contextPath);
    }

    /**
     * 级联删除
     *
     * @param toDelIds
     */
    public void deleteSpecial(Long[] toDelIds) {
        docReportsService.list(Restrictions.in("id", toDelIds)).forEach(entity -> {
            //delete file
            String realPath = entity.getFilePath();
            String realName = FileOperator.getFileName(realPath);
            new File(realPath).delete();
            new File(CommonTools.getRootPath() + File.separator + "DocTemplate" + File.separator + realName).delete();
            docReportsService.delete(entity);
        });
    }


    /**
     * @param reportId   报告模板id
     * @param reportName 报告模板新名称
     *                   更新报告内容
     *                   1.更新名称
     *                   2.清理脏标签
     *                   3.同步ftpHome与tomcat目录下真实文件
     */
    public void updateSepcial(Long reportId, String reportName) {
        CwmDocReportsEntity docReport = docReportsService.getById(reportId);
        docReport.setReportName(reportName);
        docReportItemsService.list(Restrictions.eq("belongReport.id", reportId)).forEach(docReportItemsEntity -> {
            docReportItemsEntity.setBelongReport(null);
            docReportItemsService.update(docReportItemsEntity);
        });
        String realPath = docReport.getFilePath();
        try {
            docReport.getReportItemsEntityList().clear();
            Document dsposeDoc = new Document(realPath);
            DocumentBuilder builder = new DocumentBuilder(dsposeDoc);
            BookmarkCollection bookmarks = builder.getDocument().getRange().getBookmarks();
            Iterator<Bookmark> bookmarkIterator = bookmarks.iterator();
            while (bookmarkIterator.hasNext()) {
                Bookmark bookmark = bookmarkIterator.next();
                String bookMarkName = bookmark.getName();
                Long bookMarkId = Long.valueOf(bookMarkName.substring(bookMarkName.lastIndexOf("_") + 1, bookMarkName.length()));
                CwmDocReportItemsEntity docReportItemsEntity = docReportItemsService.getById(bookMarkId);
                docReportItemsEntity.setBelongReport(docReport);
                docReport.getReportItemsEntityList().add(docReportItemsEntity);
                docReportItemsService.update(docReportItemsEntity);
            }
        } catch (Exception e) {
            throw new OrientBaseAjaxException("", "未知错误" + e.toString());
        }
        docReportsService.update(docReport);
        //clean dirty data
        docReportItemsService.list(Restrictions.isNull("belongReport")).forEach(docReportItemsEntity -> docReportItemsService.delete(docReportItemsEntity));
        //copy to tomcat dir
        String reportRealName = FileOperator.getFileName(realPath);
        String contextPath = CommonTools.getRootPath() + File.separator + "DocTemplate" + File.separator + reportRealName;
        FileOperator.copyFile(realPath, contextPath);
    }

    /**
     * @param reportId 报告模板id
     * @param dataId   数据id
     * @return 生成报告
     */
    public String generateReport(Long reportId, String dataId) {
        CwmDocReportsEntity reportsEntity = docReportsService.getById(reportId);
        String filePath = reportsEntity.getFilePath();
        //copy to tomcat dir
        String tempName = System.currentTimeMillis() + ".doc";
        String contextPath = CommonTools.getRootPath() + File.separator + "DocTemplate" + File.separator + tempName;
        FileOperator.copyFile(filePath, contextPath);
        ReportGenerator reportGenerator = (ReportGenerator) OrientContextLoaderListener.Appwac.getBean("reportGenerator");
        reportGenerator.doGenerate(reportsEntity, dataId, contextPath);
        return tempName;
    }
}
