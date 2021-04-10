package com.orient.weibao.business;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.utils.DateFormatUtil;
import com.orient.utils.StringPool;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseBusiness;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.controller.WeibaoDataAnalysisController;
import com.orient.weibao.enums.CheckCellInstCellType;
import com.orient.weibao.utils.MongoDBUtil;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.text.ParseException;
import java.util.*;

@Service("WeibaoDataAnalysisBusiness")
public class WeibaoDataAnalysisBusiness extends BaseBusiness {


    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    private static  final String MODELID = "3586";

    @Autowired
    IBusinessModelService iBusinessModelService;



    public  Map<String,List> getAvailableFields(String checkTempId){
        IBusinessModel cellBusinessModel = iBusinessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL,schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowBusinessModel = iBusinessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW,schemaId, EnumInter.BusinessModelEnum.Table);

        cellBusinessModel.setReserve_filter("and T_CHECK_TEMP_"+schemaId+"_ID="+checkTempId);
        rowBusinessModel.setReserve_filter("and T_CHECK_TEMP_"+schemaId+"_ID="+checkTempId);
        List<Map> rowList = orientSqlEngine.getBmService().createModelQuery(cellBusinessModel).list();
        List<Map> cellList = orientSqlEngine.getBmService().createModelQuery(cellBusinessModel).list();
        Map<String,List>  retMap = new HashMap<>();
        Map<String,String> tempMap = new HashMap<>();
        for(Map cellMap:cellList){
            if(cellMap.get("C_CELL_TYPE_3219").equals("#0")){
                String rowId = (String) cellMap.get("T_CHECK_ROW_"+schemaId+"_ID");
                String rowContent = (String) cellMap.get("C_CONTENT_3219");
                if (!tempMap.containsKey(rowId)){
                    tempMap.put(rowId,rowContent);
                }
            }
        }
        for(Map cellMap:cellList){
            String cellType = (String) cellMap.get("C_CELL_TYPE_3219");
            String rowId = (String) cellMap.get("T_CHECK_ROW_"+schemaId+"_ID");
            if(cellType.equals(CheckCellInstCellType.TYPE_SHUZI)) {
                if (retMap.containsKey(tempMap.get(rowId))) {
                    List numberCellList = retMap.get(tempMap.get(rowId));
                    numberCellList.add(cellMap);
                } else {
                    List<Map> numberCellList = new ArrayList<>();
                    numberCellList.add(cellMap);
                    retMap.put(tempMap.get(rowId), numberCellList);
                }
            }
        }
        System.out.println(retMap);
        return retMap;
    }

    /**
     * 获取表格实例单元格的数据
     * @param cellId
     * @return
     * @throws IOException
     */

    public List<Map> getCellInstanceData(String cellId){
        List<Map> retList = new ArrayList<>();
        IBusinessModel cellBusinessModel = iBusinessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL,schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowBusinessModel = iBusinessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW,schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel headerBusinessModel = iBusinessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER,schemaId, EnumInter.BusinessModelEnum.Table);

        IBusinessModel tempInstanceModel = iBusinessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST,schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowInstanceModel = iBusinessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST,schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel headerInstanceModel = iBusinessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER_INST,schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellInstanceModel = iBusinessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST,schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellInstanceDataModel = iBusinessModelService.getBusinessModelBySName(PropertyConstant.CELL_INST_DATA,schemaId, EnumInter.BusinessModelEnum.Table);

        Map<String,String> result = orientSqlEngine.getBmService().createModelQuery(cellBusinessModel).findById(cellId);

        String  rowId = result.get("T_CHECK_ROW_"+schemaId+"_ID");
        Map<String,String> row = orientSqlEngine.getBmService().createModelQuery(rowBusinessModel).findById(rowId);
        String rowNumber = row.get("C_ROW_NUMBER_"+rowBusinessModel.getId());//获取行号

        String  headerId = result.get("T_CHECK_HEADER_"+schemaId+"_ID");
        Map<String,String> head = orientSqlEngine.getBmService().createModelQuery(headerBusinessModel).findById(headerId);
        String headName = head.get("C_NAME_"+headerBusinessModel.getId());//获取列名称

        String tempId =  result.get("T_CHECK_TEMP_"+schemaId+"_ID");
        tempInstanceModel.setReserve_filter("and C_CHECK_TEMP_ID_"+tempInstanceModel.getId()+"="+tempId);
        List<Map> tempInstanceList = orientSqlEngine.getBmService().createModelQuery(tempInstanceModel).orderAsc("C_CHECK_TIME_"+tempInstanceModel.getId()).list();//获取模板实例
        List<Map> completeTempInstanceList=getCompleteTempInstanceList(tempInstanceList);


        int count = 0;
        for(Map<String,String> tempInstance:completeTempInstanceList){
            Map map1  = new HashMap();
            //记录时间
            String time  = tempInstance.get("C_CHECK_TIME_"+tempInstanceModel.getId());
            if(StringUtil.isEmpty(time)){
                time = DateFormatUtil.formaDatetTime(new Date());
            }
            String tempInstanceId =  tempInstance.get("ID");
            rowInstanceModel.setReserve_filter("and T_CHECK_TEMP_INST_"+schemaId+"_ID="+tempInstanceId+" and C_ROW_NUMBER_"+rowInstanceModel.getId()+"="+rowNumber);
            List<Map<String,String>> rowInstance = orientSqlEngine.getBmService().createModelQuery(rowInstanceModel).list(); //根据模板实例和行号获取该实例下的行实例，也就是确定行
            if(rowInstance.size()==0){
                //todo 暂且先跳过，理论结果不会为0
                continue;
            }
            String rowInstanceId = rowInstance.get(0).get("ID");

            headerInstanceModel.setReserve_filter("and T_CHECK_TEMP_INST_"+schemaId+"_ID="+tempInstanceId+" and C_NAME_"+headerInstanceModel.getId()+"="+"\'"+headName+"\'");
            List<Map<String,String>> headerInstance = orientSqlEngine.getBmService().createModelQuery(headerInstanceModel).list(); //根据模板实例和列名称获取列实例，
            if(headerInstance.size()==0){
                //todo 同上，暂且先跳过
                continue;
            }
            String headerInstanceId = headerInstance.get(0).get("ID");

            cellInstanceModel.setReserve_filter("and T_CHECK_TEMP_INST_"+schemaId+"_ID="+tempInstanceId+" and T_CHECK_HEADER_INST_"+schemaId+"_ID="+headerInstanceId +" and T_CHECK_ROW_INST_"+schemaId+"_ID="+rowInstanceId);
            List<Map<String,String>> cellInstanceList = orientSqlEngine.getBmService().createModelQuery(cellInstanceModel).list(); //确定表格
            String cellInstanceId = cellInstanceList.get(0).get("ID");
            cellInstanceDataModel.setReserve_filter("and T_CHECK_CELL_INST_"+schemaId+"_ID="+cellInstanceId);
            List<Map<String,String>> cellDataInstanceList = orientSqlEngine.getBmService().createModelQuery(cellInstanceDataModel).list(); //根据模板实例获取单元格实例
            String cellContent="0";
            if(cellDataInstanceList.size()>0){
                cellContent = cellDataInstanceList.get(0).get("C_CONTENT_"+cellInstanceDataModel.getId());
            }
            map1.put("times",++count);
            map1.put("date",time);
            map1.put("number",Float.parseFloat(cellContent));
            retList.add(map1);
        }
        return retList;
    }

    private List<Map> getCompleteTempInstanceList(List<Map> tempInstanceList) {
        List<Map>  completeList = new ArrayList<>();
        IBusinessModel taskBusinessModel = iBusinessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK,schemaId, EnumInter.BusinessModelEnum.Table);
        for(Map<String,String> map:tempInstanceList){
           String taskId =  map.get("T_DIVING_TASK_"+schemaId+"_ID");
           if(StringUtil.isNotEmpty(taskId)){
               Map<String,String> taskmap = orientSqlEngine.getBmService().createModelQuery(taskBusinessModel).findById(taskId);
               String taskStataus = taskmap.get("C_STATE_"+taskBusinessModel.getId());
               if("已结束".equals(taskStataus)){
                  completeList.add(map);
               }

           }

        }
        return completeList;
    }

    /**
     *
     * @param file 上传文件路径
     * @param voyageId 潜次id
     * @throws IOException
     */

    public void uploadChartData(File file, String voyageId) throws IOException, ParseException {
        long startTime = System.currentTimeMillis();
        System.out.println("开始时间:"+DateFormatUtil.formaDatetTime(new Date()));
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader  = new BufferedReader(new FileReader(file));
        String str;
        bufferedReader.readLine();
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect();
        MongoCollection<Document> collection = mongoDatabase.getCollection(voyageId);
        Document  dataMap = new Document();
        try {
            while ((str = bufferedReader.readLine())!=null){
                dataMap.clear();
                String[] strings = str.split(",");
                dataMap.put("C_CONDUCTIVITY_"+MODELID,Double.parseDouble(strings[0]));
                dataMap.put("C_TEMPERATURE_"+MODELID,Double.parseDouble(strings[1]));
                dataMap.put("C_DEPTH_"+MODELID,Double.parseDouble(strings[2]));
                dataMap.put("C_SOUND_"+MODELID,Double.parseDouble(strings[3]));
                dataMap.put("C_SALINITY_"+MODELID,Double.parseDouble(strings[4]));
                dataMap.put("C_DENSITY_"+MODELID,Double.parseDouble(strings[5]));
                dataMap.put("C_DEPTH_2_"+MODELID,Double.parseDouble(strings[6]));
                dataMap.put("C_AFTER_LONGITUDE_"+MODELID,Double.parseDouble(strings[7]));
                dataMap.put("C_AFTER_LATITUDE_"+MODELID,Double.parseDouble(strings[8]));
                dataMap.put("C_TIME_"+MODELID,DateFormatUtil.parse(strings[9],StringPool.DATE_FORMAT_DATETIME));
                dataMap.put("voyageId",voyageId);
                collection.insertOne(dataMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(fileReader);
            close(bufferedReader);
        }
        System.out.println("结束时间:"+DateFormatUtil.formaDatetTime(new Date()));
        long endTime = System.currentTimeMillis();
        System.out.println((endTime-startTime)/1000+" s");
    }
    public static void close(Closeable... closeables) throws IOException {
        for (Closeable closeable:closeables){
            if(closeable!=null){
                closeable.close();
            }
        }
    }

    /**
     * 从Oracle数据库查询
     * @param x
     * @param y
     * @param beginTime
     * @param endTime
     * @return
     *
     */
    public List<List> getChartByParam(String x, String y, String beginTime, String endTime) throws ParseException {
        List<List> retList = new ArrayList<>();
        IBusinessModel dataChartModel = iBusinessModelService.getBusinessModelBySName("T_DATA_CHART",schemaId,EnumInter.BusinessModelEnum.Table);
        String modelId = dataChartModel.getId();
        List<Map<String,String>> list = orientSqlEngine.getBmService().createModelQuery(dataChartModel).list();
        for(Map<String,String> map:list){
            List list1 = new ArrayList();
            if(("C_TIME").equals(x)){
                Date date1 =  DateFormatUtil.parse(map.get(x+"_"+modelId), StringPool.DATE_FORMAT_DATETIME);
                list1.add(date1.getTime());
            }else {
                list1.add(Float.parseFloat(map.get(x+"_"+modelId)));
            }
            list1.add(Float.parseFloat(map.get(y+"_"+modelId)));
            retList.add(list1);
        }
        return retList;
    }

    /**
     *
     * @param x x轴属性
     * @param y Y轴属性
     * @param voyageId 潜次id
     * @return
     * @throws ParseException
     */
    public List<List> getChartByParamFromMongo(String x, String y,String voyageId) throws ParseException {
        List<List> retList = new ArrayList<>();
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect();
        MongoCollection collection = mongoDatabase.getCollection(voyageId);
        Bson filter = Filters.eq("voyageId", voyageId);
        FindIterable<Document> documents  = collection.find(filter).sort(new BasicDBObject(x+"_"+MODELID,1));
        for (Document document : documents) {
            List list1 = new ArrayList();
            list1.add(document.get(x+"_"+MODELID));
            list1.add(document.get(y+"_"+MODELID));
            retList.add(list1);

        }
        return retList;
    }

    /**
     * 获取字段列的最大值和最小值
     * @param field 列名
     * @param voyageId 任务id
     * @return
     */
    public String getMaxAndMinValue( String field, String voyageId){
        String result = "";
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect();
        MongoCollection collection = mongoDatabase.getCollection(voyageId);
        Bson filter = Filters.eq("voyageId", voyageId);
        FindIterable<Document> maxdocuments = collection.find(filter).sort(new BasicDBObject(field+"_"+MODELID,-1)).limit(1);
        FindIterable<Document> mindocuments = collection.find(filter).sort(new BasicDBObject(field+"_"+MODELID,1)).limit(1);
        for (Document document : maxdocuments) {
            Double maxValue  = (Double) document.get(field+"_"+MODELID);
            result+= "  "+WeibaoDataAnalysisController.params.get(field) +"·[最大值："+maxValue.toString()+",";
        }
        for (Document document : mindocuments) {
            Double minValue  = (Double) document.get(field+"_"+MODELID);
            result+="最小值："+minValue.toString()+"], "+"\r\n";
        }
        return  result;
    }
}
