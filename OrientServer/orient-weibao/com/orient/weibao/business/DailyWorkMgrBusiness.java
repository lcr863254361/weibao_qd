package com.orient.weibao.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.edm.init.FileServerConfig;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.sysmodel.service.file.FileService;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import com.orient.weibao.constants.PropertyConstant;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DailyWorkMgrBusiness extends BaseBusiness {
    @Autowired
    MetaDAOFactory metaDAOFactory;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileServerConfig fileServerConfig;

    public Map dailyWorkAttachDetail(String dailyWorkId) {
        IBusinessModel dailyWorkBM = businessModelService.getBusinessModelBySName(PropertyConstant.DAILY_WORK, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        String modelId = dailyWorkBM.getId();
        StringBuilder sql = new StringBuilder();
        sql.append("select C_WORK_CONTENT_" + modelId + " from T_DAILY_WORK_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " where id=?");
        List<Map<String, Object>> descList = metaDAOFactory.getJdbcTemplate().queryForList(sql.toString(), dailyWorkId);
        String detailContent = "";
        if (descList != null && descList.size() > 0) {
            detailContent = CommonTools.Obj2String(descList.get(0).get("C_WORK_CONTENT_" + dailyWorkBM.getId()));
        }
        Map detailMap = UtilFactory.newHashMap();
        detailMap.put("detailContent", detailContent);
        detailMap.put("voiceUrl", null);
        detailMap.put("imageUrls", 1);
        String voicesql = "select * from cwm_file where DATAID='" + dailyWorkId + "' and TABLEID='" + modelId + "'";
        List<Map<String, Object>> fileList = jdbcTemplate.queryForList(voicesql);
        if (fileList != null && fileList.size() > 0) {
            Set<String> imagesUrlsSet = new HashSet<>();
            for (Map fileMap : fileList) {
                String fileId = CommonTools.Obj2String(fileMap.get("FILEID"));
                String fileType = CommonTools.Obj2String(fileMap.get("FILETYPE"));
                String fileName = (String) fileMap.get("FINALNAME");
                String filelocation = CommonTools.Obj2String(fileMap.get("FILELOCATION"));
                if ("amr".equals(fileType)) {
                    filelocation = filelocation.substring(0, filelocation.lastIndexOf(".")) + ".mp3";
                    String mp3FilePath = fileServerConfig.getFtpHome() + filelocation;
                    mp3FilePath = FileOperator.toStanderds(mp3FilePath);
                    Boolean isFileExist = FileOperator.isFileExist(mp3FilePath);
                    if (isFileExist) {
                        String voiceUrl = "preview" + filelocation;
                        voiceUrl = FileOperator.toStanderds(voiceUrl);
                        detailMap.put("voiceUrl", voiceUrl);
                    }
                } else {
                    String imageStorePath = fileServerConfig.getFtpHome() + filelocation;
                    imageStorePath = FileOperator.toStanderds(imageStorePath);
                    Boolean isFileExist = FileOperator.isFileExist(imageStorePath);
                    if (isFileExist) {
                        String imageUrl = "preview" + filelocation;
                        imageUrl = FileOperator.toStanderds(imageUrl);
                        imagesUrlsSet.add(imageUrl);
                    }
                }
            }
            if (imagesUrlsSet != null && imagesUrlsSet.size() > 0) {
                //list集合转为json数组
                detailMap.put("imageUrls", JSONArray.fromObject(imagesUrlsSet));
            }
        }
        return detailMap;
    }
}
