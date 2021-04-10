package com.orient.weibao.business;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.edm.init.FileServerConfig;
import com.orient.modeldata.business.ModelFileBusiness;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import com.orient.web.form.model.FileModel;
import com.orient.web.form.service.IFormService;
import com.orient.weibao.bean.table.*;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.enums.CheckCellInstCellType;
import com.orient.weibao.utils.WeibaoPropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.List;

@Service("generateHtmlBusiness")
public class GenerateHtmlBusiness extends BaseBusiness {

    @Autowired
    IBusinessModelService iBusinessModelService;
    @Autowired
    private FileServerConfig fileServerConfig;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    private int serverPort;
    private String contextPath;


    /**
     * 生成前端html页面
     *
     * @param instanced
     * @return
     */
    public Html generateHtml(String instanced, boolean isShowByInform, HttpServletRequest request,boolean needId) throws IOException, DocumentException {
        Html html = new Html();
        String contextPath = request.getContextPath();
        if (isShowByInform) {
            String OrientTdm = contextPath + "/app/javascript/orientjs/extjs";
            html.setHeader("    <script src=\"" + contextPath + "/app/javascript/lib/export/jquery.js\"></script>\n" +
                    "    <script src=\"" + contextPath + "/app/javascript/lib/export/FileSaver.js\"></script>\n" +
                    "    <script src=\"" + contextPath + "/app/javascript/lib/export/jquery.wordexport.js\"></script>\n" +
                    "<script type='text/javascript'' src=\"" + contextPath + "/app/javascript/lib/ext-4.2/examples/shared/include-ext.js\"></script>\n" +
                    "<script  src=\"" + OrientTdm + "/Common/Extend/Panel/OrientPanel.js\"></script>\n" +
                    "<script src=\"" + OrientTdm + "/Common/Util/OrientExtUtil.js\"></script>\n" +
                    "<script type='text/javascript''>" +
                    "var serviceName" + "='" + contextPath +"' </script>\n" +
                    "<script src=\"" + contextPath + "/app/javascript/orientjs/extjs/CurrentTaskMgr/CheckTableCaseHtmlPanel.js\"></script>");
        }
        Table table = new Table();
        table.setImagePrefix(contextPath);
        table.setNeedId(needId);
        List<String> filesId=new LinkedList<>();
        IBusinessModel businessModel = iBusinessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkHeaderInstModel = iBusinessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowbusinessModel1 = iBusinessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel uploadCellModel = businessModelService.getBusinessModelBySName(PropertyConstant.CELL_INST_DATA, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellBusinessModel = iBusinessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel troubleBM = businessModelService.getBusinessModelBySName(PropertyConstant.TROUBLE_DEVICE_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map<String, Object>> troubleList = orientSqlEngine.getBmService().createModelQuery(troubleBM).list();
        Map<String, String> ret = orientSqlEngine.getBmService().createModelQuery(businessModel).findById(instanced);
        String tableId = ret.get("ID");
        String tableName = ret.get("C_NAME_" + businessModel.getId());
        table.setTitle(tableName);
        //查询出该表格的所有单元格内容
        uploadCellModel.setReserve_filter("and T_CHECK_TEMP_INST_" + schemaId + "_ID=" + tableId);
        List<Map<String, String>> uploadCellMapList = orientSqlEngine.getBmService().createModelQuery(uploadCellModel).list();
        //查询出该表格所有实例
        cellBusinessModel.setReserve_filter(" and T_CHECK_TEMP_INST_" + schemaId + "_ID=" + tableId);
        List<Map> cellList = orientSqlEngine.getBmService().createModelQuery(cellBusinessModel).list();
        //查询出该表格所有列信息
        checkHeaderInstModel.setReserve_filter("AND T_CHECK_TEMP_INST_" + schemaId + "_ID=" + tableId);
        List<Map> headers = orientSqlEngine.getBmService().createModelQuery(checkHeaderInstModel).orderAsc("TO_NUMBER(ID)").list();
        Iterator listiterator = headers.iterator();
        while (listiterator.hasNext()) {
            Map<String, String> header = (Map<String, String>) listiterator.next();
            String headerName = header.get("C_NAME_" + checkHeaderInstModel.getId());
            if (StringUtil.isEmpty(headerName)) {
                listiterator.remove();
            }
        }
        //查询出表格所有行
        rowbusinessModel1.setReserve_filter("AND T_CHECK_TEMP_INST_" + schemaId + "_ID=" + tableId);
        List<Map> rowRets = orientSqlEngine.getBmService().createModelQuery(rowbusinessModel1).orderAsc("TO_NUMBER(ID)").list();
        List<Map> cellHeadList = new ArrayList<>();
        List<Map> cellEndList = new ArrayList<>();
        List<Map> cellRowList = new ArrayList<>();

        table.setColumnSize(headers.size());

        for (Map<String, String> map : cellList) {
            String status = map.get("C_IS_HEADER_" + cellBusinessModel.getId());
            if (StringUtil.isNotEmpty(status) && "true" .equals(status)) {
                cellHeadList.add(map);
            } else if (StringUtil.isNotEmpty(status) && "false" .equals(status)) {
                cellEndList.add(map);
            } else if (StringUtil.isEmpty(status)) {
                cellRowList.add(map);
            }
        }

        //开始拼接表主体内容
        List<Tr> trs = new ArrayList<>();
        List<T> thList = new ArrayList<>();
        Tr tr = new Tr();
        for (Map<String, String> map : headers) {
            String content = map.get("C_NAME_" + checkHeaderInstModel.getId());
            if (StringUtil.isNotEmpty(content) && content.startsWith("#")) {
                content = content.substring(content.indexOf("#") + 1);
            }
            Th th = new Th(content);
            thList.add(th);
        }
        tr.setList(thList);
        trs.add(tr);

        for (Map<String, String> map : rowRets) {  //遍历行
            String rowId = map.get("ID");
            Tr trCell = new Tr();
            List<T> tds = new ArrayList<>();
            for (Map<String, String> headerMap : headers) { //遍历列,一行一列确定唯一一个单元格
                String headerId = headerMap.get("ID");
                String columnName = headerMap.get("C_NAME_" + checkHeaderInstModel.getId());
                for (Map item : cellRowList) {
                    if (headerId.equals(item.get("T_CHECK_HEADER_INST_" + schemaId + "_ID")) && rowId.equals(item.get("T_CHECK_ROW_INST_" + schemaId + "_ID"))) {
                        Td td = appendRows(columnName, cellBusinessModel, item, uploadCellModel, uploadCellMapList, troubleList, troubleBM, contextPath, isShowByInform,filesId);
                        tds.add(td);
                    }
                }
            }
            trCell.setList(tds);
            trs.add(trCell);
        }

        //组装数据完毕后开始进行合并第一列名字相同单元格,获取第一次出现名字的行号，并记录重复名字的记录
        Map<String, Attr> firstColumn = new LinkedHashMap<>();
        for (int i = 1; i < trs.size(); i++) {
            Tr orderTr = trs.get(i);
            T orderTd = orderTr.getList().get(0);
            String key = orderTd.getContent();
            if (firstColumn.containsKey(key)) {
                Attr attr = firstColumn.get(key);
                attr.setCount(attr.getCount() + 1);
            } else {
                Attr attr = new Attr();
                attr.setCount(1);
                attr.setIndex(i);
                firstColumn.put(key, attr);
            }
        }
        //获取行号和记录后，开始迭代设置td标签rowspan属性，同时删除<td>
        Iterator iterator = firstColumn.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            int index = firstColumn.get(key).getIndex();
            int count = firstColumn.get(key).getCount();
            Td td = (Td) trs.get(index).getList().get(0);
            td.setRowspan(count);
            for (int i = (index + 1); i < (index + count); i++) {
                trs.get(i).getList().remove(0);
            }
        }

        //开始拼接表头和表尾
        List<Tr> tableHeads = appendHeaderEndContent(filesId,cellHeadList, cellBusinessModel, uploadCellModel, headers.size(), uploadCellMapList, troubleList, troubleBM, isShowByInform);
        List<Tr> tableTails = appendHeaderEndContent(filesId,cellEndList, cellBusinessModel, uploadCellModel, headers.size(), uploadCellMapList, troubleList, troubleBM, isShowByInform);

        table.setHeadList(tableHeads);
        table.setTrList(trs);
        table.setTailList(tableTails);
        table.setFilesId(filesId);
        html.setTableBody(table);
        return html;
    }

    /**
     * 导出PDf
     *
     * @param instanced
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    @Autowired
    IFormService formService;
    public File generateToPDf(String instanced, int serverPort, String contextPath, HttpServletRequest request) throws IOException, DocumentException {
        this.serverPort = serverPort;
        this.contextPath = contextPath;
        String diskLocation = WeibaoPropertyUtil.getPropertyValueConfigured("pdf.dir", "config.properties", "D:");
        Html html = generateHtml(instanced, false, request,true);
        Table table = html.getTableBody();
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(50, 50, 50, 50);
        String filename = table.getTitle() + ".pdf";
        File file = new File(diskLocation + "\\weibaopdf\\" + filename);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
        FontFactoryImp ffi = new FontFactoryImp();
        ffi.registerDirectories();
        Font font = ffi.getFont("宋体", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12, Font.UNDEFINED, null);
        document.addAuthor("Administrator");
        document.addCreator("win-pc");
        document.addSubject("导出");
        document.addTitle(table.getTitle());
        document.open();
        PdfPTable pfTable = new PdfPTable(table.getColumnSize());
        pfTable.setTotalWidth(PageSize.A4.getWidth() - 100);
        pfTable.setLockedWidth(true);
        List<Tr> trHeads = table.getHeadList();
        List<Tr> trs = table.getTrList();
        List<Tr> trTails = table.getTailList();
        generatePdfTable(pfTable, trHeads, font);
        generatePdfTable(pfTable, trs, font);
        generatePdfTable(pfTable, trTails, font);
        document.add(pfTable);
        table.getFilesId().stream().forEach(item->{
            Image image = null;
            try {
                FileModel fileModel = formService.getUploadFilePath(item);
                FileInputStream fileInputStream = new FileInputStream(fileModel.getFilePath());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                byte[] bytes=new byte[1024];
                int i=0;
                while ((i=fileInputStream.read(bytes))!=-1){
                    bos.write(bytes,0,i);
                }
                bos.close();
                image = Image.getInstance(bos.toByteArray());
                image.scalePercent(20);
                document.add(image);

            } catch (Exception e) {

            }
        });

        document.close();
        writer.close();
        return file;

    }

    public void generatePdfTable(PdfPTable pfTable, List<Tr> trs, Font font) throws IOException, BadElementException {
        // cacheManager.
        for (Tr tr : trs) {
            List<T> tds = tr.getList();
            for (T t : tds) {
                PdfPCell pdfPCell = new PdfPCell();
                if (t instanceof Th) {
                    pdfPCell.setRowspan(1);
                    pdfPCell.setColspan(1);
                    pdfPCell.setPhrase(new Paragraph(t.getContent(), font));
                }
                if (t instanceof Td) {
                    Td td = (Td) t;
                    pdfPCell.setRowspan(td.getRowspan());
                    pdfPCell.setColspan(td.getColspan());
                    if (StringUtil.isNotEmpty(td.getImgPath())) {
                        Image image = Image.getInstance(getImageUrl(td.getImgPath()));
                        pdfPCell.setImage(image);
                    } else {
                        pdfPCell.setPhrase(new Paragraph(t.getContent(), font));
                    }
                }
                pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);//设置单元格的垂直对齐方式
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);//设置单元格的水平对齐方式
                pfTable.addCell(pdfPCell);
            }
        }

    }

    private String getImageUrlByFileId(String fileId) {
        String retPath = "";
        retPath = "http://127.0.0.1:" + serverPort + contextPath + "/orientForm/download.rdm?fileId=" + fileId;
        System.out.println("返回路径:" + retPath);
        return retPath;
    }

    private String getImageUrl(String imgPath) {
        String retPath = "";
        if (imgPath.startsWith(".")) {
            retPath = "http://127.0.0.1:" + serverPort + contextPath + imgPath.substring(1);
        } else {
            imgPath = imgPath.replace("\\", "/");
            retPath = "http://127.0.0.1:" + serverPort + contextPath + "/" + imgPath;
        }

        System.out.println("返回路径:" + retPath);
        return retPath;
    }

    /**
     * @param columnName        列名
     * @param cellModel         单元格模型
     * @param cellTempMap       单元格数据实例
     * @param uploadCellModel   单元格具体数据模型
     * @param uploadCellMapList 单元格具体存储数据列表
     * @return
     */
    @Autowired
    ModelFileBusiness modelFileBusiness;
    @Autowired
    CurrentTaskMgrBusiness currentTaskMgrBusiness;


    private Td appendRows(String columnName, IBusinessModel cellModel,
                          Map cellTempMap, IBusinessModel uploadCellModel,
                          List<Map<String, String>> uploadCellMapList,
                          List<Map<String, Object>> troubleList,
                          IBusinessModel troubleBM, String contextPath,
                          boolean isShowByInform,List<String> filesId) {
        this.contextPath = contextPath;
        String isCheck = "false";
        Td td = new Td();
        StringBuilder htmlStr = new StringBuilder("");
        if (columnName.indexOf("#") == 0 && columnName.length() != 0) {
            isCheck = "true";
        }
        switch (isCheck) {

            case "false"://如果是描述项，去单元格实例里找
                String descContent = "";
                descContent = CommonTools.Obj2String(cellTempMap.get("C_CONTENT_" + cellModel.getId()));
                if ("" .equals(descContent)) {
                    descContent = "---";
                }
                htmlStr.append(descContent);
                break;

            case "true":
                String cellInstId = CommonTools.Obj2String(cellTempMap.get("ID"));
                String cellType = CommonTools.Obj2String(cellTempMap.get("C_CELL_TYPE_" + cellModel.getId()));
                String sql = "select * from cwm_file where DATAID='" + cellInstId + "' and TABLEID='" + cellModel.getId() + "'";
                List<Map<String, Object>> fileList = jdbcTemplate.queryForList(sql);
                switch (cellType) {
                    case CheckCellInstCellType.TYPE_CHECK:
                        String content = "";
                        if (uploadCellMapList.size() > 0) {
                            for (Map uploadMap : uploadCellMapList) {
                                String refCellInstId = CommonTools.Obj2String(uploadMap.get("T_CHECK_CELL_INST_" + schemaId + "_ID"));
                                if (cellInstId.equals(refCellInstId)) {
                                    String uploadCellContent = CommonTools.Obj2String(uploadMap.get("C_CONTENT_" + uploadCellModel.getId()));
                                    htmlStr.append(uploadCellContent.equals("是") ? "通过" : "不通过");
                                    break;
                                }
                            }
                        }
                        htmlStr.append(content);
                        break;
                    case CheckCellInstCellType.TYPE_FILL:
                        htmlStr.append(commonHtml(uploadCellMapList, cellInstId, uploadCellModel));
                        break;
                    case CheckCellInstCellType.TYPE_SHUZI:
                        htmlStr.append(commonHtml(uploadCellMapList, cellInstId, uploadCellModel));
                        break;
                    case CheckCellInstCellType.TYPE_KONG:
                        htmlStr.append(commonHtml(uploadCellMapList, cellInstId, uploadCellModel));
                        break;
                    case CheckCellInstCellType.TYPE_TIME:
                        htmlStr.append(commonHtml(uploadCellMapList, cellInstId, uploadCellModel));
                        break;
                    case CheckCellInstCellType.TYPE_PICTURE:
                        if (fileList.size() > 0) {
                            String picturePath = "./app/images/icons/default/upload/preview.png";
                            if (isShowByInform) {
                                picturePath = contextPath + "/app/images/icons/default/upload/preview.png";
                            }
                            td.setImgPath(picturePath);
//                            htmlStr.append("<div  style='text-align:center'><img src='./app/images/icons/default/upload/preview.png' style='cursor:pointer' onclick ='OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel.showPictures(" + cellInstId + ")'>")
//                                    .append("</img></div>");
                            htmlStr.append("<div  style='text-align:center'>").append("<img src=").append(picturePath).append(" style='cursor:pointer' onclick ='OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel.showPictures(").append(cellInstId).append(")'>")
                                    .append("</img></div>");

                        }

                        List<FileModel> list = modelFileBusiness.list("3216", cellInstId, "-4", null, null);
                        list.stream().forEach(item->{
                            filesId.add(item.getFileid());
                        });
                        break;
                    case CheckCellInstCellType.TYPE_SIGN:
                        if (fileList.size() > 0) {
                            String fileName = (String) fileList.get(0).get("FINALNAME");
                            String imageFolderPath = fileServerConfig.getFtpHome() + File.separator + "imageSuoluetu" + File.separator + fileName;
                            if (new File(imageFolderPath).exists()) {
//                                            String signPicturePath = "preview"+ File.separator+"imagePreview"+ File.separator+fileName;
                                String signPicturePath = "preview" + File.separator + "imageSuoluetu" + File.separator + fileName;
                                if (isShowByInform) {
                                    signPicturePath = contextPath + File.separator + "preview" + File.separator + "imageSuoluetu" + File.separator + fileName;
                                }
                                td.setImgPath(signPicturePath);
                                htmlStr.append("<div  style='text-align:center'>").append("<img width='40px' height='40px' src=").append(signPicturePath)
                                        .append(">")
                                        .append("</img></div>");
                            }
                        }
                        break;
                    case CheckCellInstCellType.TYPE_TROUBLE:
                        if (troubleList.size() > 0) {
                            for (Map troubleMap : troubleList) {
                                String troubelCellId = CommonTools.Obj2String(troubleMap.get("C_CELL_ID_" + troubleBM.getId()));
                                if (troubelCellId.equals(cellInstId)) {
                                    String troublePath = "./app/images/mediaImage/trouble.png";
                                    if (isShowByInform) {
                                        troublePath = contextPath + "/app/images/mediaImage/trouble.png";
                                    }
                                    td.setImgPath(troublePath);
                                    htmlStr.append("<div  style='text-align:center'><img src=").append(troublePath).append(" width='20px' height=20px'  style='cursor:pointer' onclick ='OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel.showCellTroubleRecord(").append(cellInstId).append(",").append(isShowByInform).append(")'>")
                                            .append("</img></div>");

                                    String troubleIdByCellId = currentTaskMgrBusiness.getTroubleIdByCellId(cellInstId);
                                    IBusinessModel model = businessModelService.getBusinessModelBySName("T_TROUBLE_DEVICE_INS", schemaId, EnumInter.BusinessModelEnum.getBusinessModelType("0"));
                                    String modelId = model.getMainModel().getId();
                                    List<FileModel> troublePics = modelFileBusiness.list(modelId, troubleIdByCellId, "-4", null, null);
                                    troublePics.stream().forEach(item->{
                                        filesId.add(item.getFileid());
                                    });
                                    break;
                                }
                            }
                        }
                        break;
                    case CheckCellInstCellType.TYPE_YDN:
                        if (uploadCellMapList.size() > 0) {
                            for (Map uploadMap : uploadCellMapList) {
                                String refCellInstId = CommonTools.Obj2String(uploadMap.get("T_CHECK_CELL_INST_" + schemaId + "_ID"));
                                if (cellInstId.equals(refCellInstId)) {
                                    String uploadCellContent = CommonTools.Obj2String(uploadMap.get("C_CONTENT_" + uploadCellModel.getId()));
                                    if (uploadCellContent.equals("是")) {
                                        htmlStr.append("通过");
                                    } else if (uploadCellContent.equals("否")) {
                                        htmlStr.append("不通过");



                                    } else if (uploadCellContent.equals("无")) {
                                        htmlStr.append("无");
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    case CheckCellInstCellType.TYPE_JWD:
                        htmlStr.append(commonHtml(uploadCellMapList, cellInstId, uploadCellModel));
                        break;
                }
                break;

        }
        td.setContent(htmlStr.toString());
        return td;
    }


    /**
     * @param headerContentList 表头信息内容集合，也就是表格前的信息内容
     * @param cellInstModel     表格视图模型
     * @param uploadCellModel   表格内容视图模型
     * @param columnsize        列数
     * @param uploadCellMapList 上传表格数据实例集合
     * @return
     * @Desc 拼接具体表头或者表尾信息
     */


    private List<Tr> appendHeaderEndContent(List<String> filesId,List<Map> headerContentList, IBusinessModel cellInstModel, IBusinessModel uploadCellModel, int columnsize, List<Map<String, String>> uploadCellMapList, List<Map<String, Object>> troubleList, IBusinessModel troubleBM, boolean isShowByInform) {
        List<Tr> trs = new ArrayList<>();
        for (Map headerContentMap : headerContentList) {
            Tr tr = new Tr();
            List<T> tds = new ArrayList<>();
            String headerCellId = headerContentMap.get("ID").toString();
            String headerName = CommonTools.Obj2String(headerContentMap.get("C_CONTENT_" + cellInstModel.getId()));
            String headerType = CommonTools.Obj2String(headerContentMap.get("C_CELL_TYPE_" + cellInstModel.getId()));
            Td td = new Td(headerName);
            tds.add(td);
            Td tdValue = headerEndHtml(headerType, headerCellId, cellInstModel, uploadCellModel, columnsize, uploadCellMapList, troubleList, troubleBM, isShowByInform, filesId);
            tds.add(tdValue);
            tr.setList(tds);
            trs.add(tr);
        }
        return trs;
    }

    private Td headerEndHtml(String headerType, String cellId,
                             IBusinessModel cellInstBM,
                             IBusinessModel uploadCellModel,
                             int columnsize,
                             List<Map<String, String>> uploadCellMapList,
                             List<Map<String, Object>> troubleList,
                             IBusinessModel troubleBM,
                             boolean isShowByInform,
                             List<String> filesId) {
        Td td = new Td();
        StringBuilder htmlStr = new StringBuilder("");
        if (headerType.indexOf("#") == 0 && headerType.length() != 0) {
            td.setColspan(columnsize - 1);
            List<Map<String, Object>> fileList = UtilFactory.newArrayList();
            String fileSql = "";
            switch (headerType) {
                case CheckCellInstCellType.TYPE_CHECK:
                    if (uploadCellMapList.size() > 0) {
                        for (Map uploadMap : uploadCellMapList) {
                            String refCellInstId = CommonTools.Obj2String(uploadMap.get("T_CHECK_CELL_INST_" + schemaId + "_ID"));
                            if (cellId.equals(refCellInstId)) {
                                String uploadCellContent = CommonTools.Obj2String(uploadMap.get("C_CONTENT_" + uploadCellModel.getId()));
                                htmlStr.append(uploadCellContent.equals("是") ? "通过" : "不通过");
                                break;
                            }
                        }
                    }
                    break;
                case CheckCellInstCellType.TYPE_FILL:
                    htmlStr.append(commonHtml(uploadCellMapList, cellId, uploadCellModel));
                    break;
                case CheckCellInstCellType.TYPE_SHUZI:
                    htmlStr.append(commonHtml(uploadCellMapList, cellId, uploadCellModel));
                    break;
                case CheckCellInstCellType.TYPE_TIME:
                    htmlStr.append(commonHtml(uploadCellMapList, cellId, uploadCellModel));
                    break;
                case CheckCellInstCellType.TYPE_PICTURE:
                    fileSql = "select * from cwm_file where DATAID='" + cellId + "' and TABLEID='" + cellInstBM.getId() + "'";
                    fileList = jdbcTemplate.queryForList(fileSql);
                    if (fileList.size() > 0) {
//                        td.setImgPath("./app/images/icons/default/upload/preview.png");
//                        htmlStr.append("<div  style='text-align:center'><img src='./app/images/icons/default/upload/preview.png' style='cursor:pointer' onclick ='OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel.showPictures(" + cellId + ")'>")
//                                .append("</img></div>");
                        String picturePath = "./app/images/icons/default/upload/preview.png";
                        String onclickPath = "OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel.showPictures(";

                        if (isShowByInform) {
                            picturePath = this.contextPath + "/app/images/icons/default/upload/preview.png";
//                            String OrientTdm = this.contextPath + "/app/javascript/orientjs/extjs";
//                            onclickPath = OrientTdm + "CurrentTaskMgr.CheckTableCaseHtmlPanel.showPictures(";
                        }
                        td.setImgPath(picturePath);
//                            htmlStr.append("<div  style='text-align:center'><img src='./app/images/icons/default/upload/preview.png' style='cursor:pointer' onclick ='OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel.showPictures(" + cellInstId + ")'>")
//                                    .append("</img></div>");
//                            htmlStr.append("<div  style='text-align:center'>").append("<img src=").append(picturePath).append(" style='cursor:pointer' onclick ='OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel.showPictures(").append(cellId).append(")'>")
//                                    .append("</img></div>");
                        htmlStr.append("<div  style='text-align:center'>").append("<img src=").append(picturePath).append(" style='cursor:pointer' onclick ='").append(onclickPath).append(cellId).append(")'>")
                                .append("</img></div>");

                        List<FileModel> list = modelFileBusiness.list("3216", cellId, "-4", null, null);
                        list.stream().forEach(item->{
                            filesId.add(item.getFileid());
                        });
                    }
                    break;
                case CheckCellInstCellType.TYPE_SIGN:
                    fileSql = "select * from cwm_file where DATAID='" + cellId + "' and TABLEID='" + cellInstBM.getId() + "'";
                    fileList = jdbcTemplate.queryForList(fileSql);
                    if (fileList.size() > 0) {
                        String fileName = (String) fileList.get(0).get("FINALNAME");
                        String signPicturePath = "preview" + File.separator + "imageSuoluetu" + File.separator + fileName;
                        if (isShowByInform) {
                            signPicturePath = this.contextPath + File.separator + "preview" + File.separator + "imageSuoluetu" + File.separator + fileName;
                        }
                        td.setImgPath(signPicturePath);
                        htmlStr.append("<div  style='text-align:center'>").append("<img width='40px' height=40px' src=").append(signPicturePath)
                                .append(">")
                                .append("</img></div>");
                    }
                    break;
                case CheckCellInstCellType.TYPE_TROUBLE:
//                    fileSql = "select * from cwm_file where DATAID='" + cellId + "' and TABLEID='" + cellInstBM.getId() + "'";
//                    fileList = jdbcTemplate.queryForList(fileSql);
//                    if (fileList.size() > 0) {
//                        td.setImgPath("./app/images/icons/default/upload/preview.png");
//                        htmlStr.append("<div  style='text-align:center'><img src='./app/images/icons/default/upload/preview.png' style='cursor:pointer' onclick ='OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel.showPictures(" + cellId + ")'>")
//                                .append("</img></div>");
//                    }
                    if (troubleList.size() > 0) {
                        for (Map troubleMap : troubleList) {
                            String troubelCellId = CommonTools.Obj2String(troubleMap.get("C_CELL_ID_" + troubleBM.getId()));
                            if (troubelCellId.equals(cellId)) {
//                                td.setImgPath("./app/images/mediaImage/trouble.png");
//                                htmlStr.append("<div  style='text-align:center'><img src='./app/images/mediaImage/trouble.png' width='20px' height=20px'  style='cursor:pointer' onclick ='OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel.showCellTroubleRecord(" + cellId + ")'>")
//                                        .append("</img></div>");
                                String troublePath = "./app/images/mediaImage/trouble.png";
                                if (isShowByInform) {
                                    troublePath = contextPath + "/app/images/mediaImage/trouble.png";
                                }
                                td.setImgPath(troublePath);
                                htmlStr.append("<div  style='text-align:center'><img src=").append(troublePath).append(" width='20px' height=20px'  style='cursor:pointer' onclick ='OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel.showCellTroubleRecord(").append(cellId).append(",").append(isShowByInform).append(")'>")
                                        .append("</img></div>");
                                break;
                            }
                        }
                    }
                    break;
                case CheckCellInstCellType.TYPE_YDN:
                    if (uploadCellMapList.size() > 0) {
                        for (Map uploadMap : uploadCellMapList) {
                            String refCellInstId = CommonTools.Obj2String(uploadMap.get("T_CHECK_CELL_INST_" + schemaId + "_ID"));
                            if (cellId.equals(refCellInstId)) {
                                String uploadCellContent = CommonTools.Obj2String(uploadMap.get("C_CONTENT_" + uploadCellModel.getId()));
                                if (uploadCellContent.equals("是")) {
                                    htmlStr.append("通过");
                                } else if (uploadCellContent.equals("否")) {
                                    htmlStr.append("不通过");
                                } else if (uploadCellContent.equals("无")) {
                                    htmlStr.append("无");
                                }
                                break;
                            }
                        }
                    }
                    break;
                case CheckCellInstCellType.TYPE_JWD:
                    htmlStr.append(commonHtml(uploadCellMapList, cellId, uploadCellModel));
                    break;
            }

        }
        td.setContent(htmlStr.toString());
        return td;
    }

    private String commonHtml(List<Map<String, String>> uploadCellMapList, String cellInstId, IBusinessModel uploadCellModel) {
        String content = "";
        if (uploadCellMapList.size() > 0) {
            for (Map uploadMap : uploadCellMapList) {
                String refCellInstId = CommonTools.Obj2String(uploadMap.get("T_CHECK_CELL_INST_" + schemaId + "_ID"));
                if (cellInstId.equals(refCellInstId)) {
                    String uploadCellContent = CommonTools.Obj2String(uploadMap.get("C_CONTENT_" + uploadCellModel.getId()));
                    if ("" .equals(uploadCellContent)) {
                        content = "---";
                    } else {
                        content = uploadCellContent;
                    }
                    break;
                }
            }
        }
        return content;
    }


}

/**
 * @author fangbin
 * @date 2020/3/22
 * @Desp 封装记录和 行号实体
 */

class Attr {
    private int count;
    private int index;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
