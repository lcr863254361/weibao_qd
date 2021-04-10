package com.orient.weibao.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.sysmodel.domain.user.User;
import com.orient.utils.CommonTools;
import com.orient.utils.ExcelUtil.Excel;
import com.orient.utils.ExcelUtil.reader.DataEntity;
import com.orient.utils.ExcelUtil.reader.FieldEntity;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import com.orient.weibao.bean.PostBean;
import com.orient.weibao.constants.PropertyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-01-10 8:57
 */
@Service
public class PostMgrBusiness extends BaseBusiness {

    @Autowired
    JdbcTemplate jdbcTemplate;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    public void deletePostData(String id) {

        String refPostTableName = PropertyConstant.REF_POST_NODE;
        IBusinessModel refPostBM = businessModelService.getBusinessModelBySName(refPostTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel PostBM = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR, schemaId, EnumInter.BusinessModelEnum.Table);
        String modelId = refPostBM.getId();
        String sql = "select ID from " + refPostTableName + "_" + schemaId + " where c_post_id_" + modelId + " in (" + id + ")";

        List<Map<String, Object>> refPostList = jdbcTemplate.queryForList(sql);

        if (refPostList.size() != 0) {
            for (Map map : refPostList) {
                String refPostId = CommonTools.Obj2String(map.get("ID"));
                orientSqlEngine.getBmService().delete(refPostBM, refPostId);
            }
        }

        orientSqlEngine.getBmService().delete(PostBM, id);

    }

    public Map<String, Object> importPostData(TableEntity excelEntity) {

        IBusinessModel postBM = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR, schemaId, EnumInter.BusinessModelEnum.Table);

        Map<String, Object> retVal = new HashMap<>();
        List<PostBean> postBeanList = UtilFactory.newArrayList();

        List<DataEntity> dataEntities = excelEntity.getDataEntityList();
//        for (DataEntity dataEntity:dataEntities){
//            List<FieldEntity> fieldEntities = dataEntity.getFieldEntityList();
//            int fieldNum = fieldEntities.size();
//            if (fieldNum > 1) {
//                fieldNum = 1;
//            }
//            String postName="";
//            switch (fieldNum){
//                case 1:
//                   postName = fieldEntities.get(1).getValue();
//                    if (postName == null || postName.equals("")) {
//                        retVal.put("success", false);
//                        retVal.put("msg", "岗位名称不可为空，请修正后导入！");
//                        return retVal;
//                    }
//                    break;
//                default:
//                    retVal.put("success", false);
//                    retVal.put("msg", "岗位名称不可为空，请修正后导入！");
//                    return retVal;
//            }
//            Map map= UtilFactory.newHashMap();
//            map.put("C_POST_NAME_"+postBM.getId(),postName);
//            postBM.clearAllFilter();
//            postBM.setReserve_filter("and c_post_name_"+postBM.getId()+" in '"+postName+"'");
//            List<Map<String,Object>> postList=orientSqlEngine.getBmService().createModelQuery(postBM).list();
//            if (postList.size()>0){
//                continue;
//           }else{
//               PostBean postBean=new PostBean();
//               postBean.setPostName(postName);
//               postBeanList.add(postBean);
//           }
//        }
        for (int j = 0; j < dataEntities.size(); j++) {
            PostBean postBean = new PostBean();
            //取出每一行的单元格数据遍历，插入到数据库
            List<FieldEntity> fieldEntities = dataEntities.get(j).getFieldEntityList();
            if (fieldEntities != null) {
                for (int i = 0; i < fieldEntities.size(); i++) {
                    FieldEntity fieldEntity = fieldEntities.get(i);
                    if (fieldEntity.getIsKey() == 1) {
                        continue;
                    }
                    String name = fieldEntities.get(i).getName();
                    String value = fieldEntities.get(i).getValue();
                    if ("岗位名称".equals(name)) {
                        if (value == null || "".equals(value)) {
                            int rowNumer = j + 2;
                            retVal.put("success", false);
                            retVal.put("msg", "第" + rowNumer + "行第" + i + "列的岗位名称不可为空，请修正后导入！");
                            return retVal;
                        }
                        postBean.setPostName(value);
                    } else if ("岗位类型".equals(name)) {
                        if (value == null || "".equals(value)) {
                            value = "固化";
                            postBean.setPostType(value);
                        } else if ("固化".equals(value)) {
                            postBean.setPostType(value);
                        } else if ("流动".equals(value)) {
                            postBean.setPostType(value);
                        }else if (!"固化".equals(value)||(!"流动".equals(value))){
                            value="固化";
                            postBean.setPostType(value);
                        }
                    } else {
                        retVal.put("success", false);
                        retVal.put("msg", "导入失败,表头格式存在错误！");
                        return retVal;
                    }
                }
                if (!"".equals(postBean.getPostName())) {
                    postBeanList.add(postBean);
                }
            }
        }
        if (postBeanList.size() > 0) {
            for (PostBean postBean : postBeanList) {
                if (postBean != null) {
                    String postName = postBean.getPostName();
                    String postType = postBean.getPostType();
                    Map postMap = UtilFactory.newHashMap();
                    postBM.clearAllFilter();
                    postBM.setReserve_filter("AND C_POST_NAME_" + postBM.getId() + "='" + postName + "'");
                    List<Map<String,Object>> postList=orientSqlEngine.getBmService().createModelQuery(postBM).list();
                    if (postList.size()>0){
                        continue;
                    }
                    postMap.put("C_POST_NAME_" + postBM.getId(), postName);
                    postMap.put("C_POST_TYPE_" + postBM.getId(), postType);
                    orientSqlEngine.getBmService().insertModelData(postBM, postMap);
                }
            }
        }
        retVal.put("success", true);
        retVal.put("msg", "导入成功！");
        return retVal;
    }

    public void exportPostData(boolean exportAll, String toExportIds) {
        IBusinessModel postBM = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR, schemaId, EnumInter.BusinessModelEnum.Table);
        Excel excel = new Excel();
        Object[] headers = new Object[]{"岗位名称", "岗位类型"};
        excel.row(0).value(headers);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final int[] i = {1};
        if (exportAll) {
            List<Map<String, Object>> postList = orientSqlEngine.getBmService().createModelQuery(postBM).orderAsc("to_number(ID)").list();
            if (postList.size() > 0) {
                for (Map map : postList) {
                    excel.cell(i[0], 0).value(map.get("c_post_name_" + postBM.getId()));
                    excel.cell(i[0], 1).value(map.get("c_post_type_" + postBM.getId()));
                    i[0]++;
                }
            }
        } else {
            postBM.clearAllFilter();
            if (!toExportIds.equals("")) {
                postBM.setReserve_filter("and id in (" + toExportIds + ")");
                List<Map<String, Object>> postList = orientSqlEngine.getBmService().createModelQuery(postBM).orderAsc("to_number(ID)").list();
                if (postList.size() > 0) {
                    for (Map map : postList) {
                        excel.cell(i[0], 0).value(map.get("c_post_name_" + postBM.getId()));
                        excel.cell(i[0], 1).value(map.get("c_post_type_" + postBM.getId()));
                        i[0]++;
                    }
                }
            }
        }
        for (int j = 0; j < 2; j++) {
            excel.column(j).autoWidth();
        }
        excel.saveExcel("posts.xls");
    }
}
