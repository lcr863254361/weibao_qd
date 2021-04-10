package com.orient.testresource.business;

import com.orient.web.base.BaseBusiness;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 试验资源管理数据库初始化
 */
@Service
public class ResourceInitBusiness extends BaseBusiness {
    public static final Pattern pattern = Pattern.compile("#(SEQ_.+?-\\d+?)#");

    public Map<String, String> importSchema(String sqlFilePath) {
        List<String> sqlTempList = this.getSqlTemplateList(sqlFilePath);
        Map<String, String> mapper = new HashMap<>();
        List<String> sqlList = this.buildImportSql(sqlTempList, mapper);
        //TestResourceUtil.sqlListToFile(sqlList, "C:\\sql.sql");
        this.metaDaoFactory.getJdbcTemplate().batchUpdate(sqlList.toArray(new String[0]));
        return mapper;
    }

    public void createTables(String sqlFilePath, Map<String, String> mapper) {
        List<String> sqlTempList = this.getSqlTemplateList(sqlFilePath);
        List<String> sqlList = this.buildImportSql(sqlTempList, mapper);
        this.metaDaoFactory.getJdbcTemplate().batchUpdate(sqlList.toArray(new String[0]));
    }

    public void importTemplate(String sqlFilePath, Map<String, String> mapper) {
        List<String> sqlTempList = this.getSqlTemplateList(sqlFilePath);
        List<String> sqlList = this.buildImportSql(sqlTempList, mapper);
        this.metaDaoFactory.getJdbcTemplate().batchUpdate(sqlList.toArray(new String[0]));
    }

    public void importTBom(String sqlFilePath, Map<String, String> mapper) {
        List<String> sqlTempList = this.getSqlTemplateList(sqlFilePath);
        List<String> sqlList = this.buildImportSql(sqlTempList, mapper);
        this.metaDaoFactory.getJdbcTemplate().batchUpdate(sqlList.toArray(new String[0]));
    }

    private List<String> getSqlTemplateList(String sqlFilePath) {
        List<String> retList = new ArrayList<>();
        BufferedReader reader = null;
        try {
            File file = new File(sqlFilePath);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            reader = new BufferedReader(isr);
            String str = null;
            while((str=reader.readLine()) != null) {
                str = str.trim();
                if(!"".equals(str)) {
                    retList.add(str);
                }
            }
            reader.close();
        }
        catch (Exception e) {
            if(reader != null) {
                try {
                    reader.close();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return retList;
    }

    private List<String> buildImportSql(List<String> sqlTempList, Map<String, String> mapper) {
        List<String> retList = new ArrayList<>();
        for(String sqlTemp : sqlTempList) {
            Matcher matcher = pattern.matcher(sqlTemp);
            StringBuffer buf = new StringBuffer();
            while(matcher.find()) {
                String placeHodler = matcher.group();
                String sequence = placeHodler.substring(placeHodler.indexOf("#")+1, placeHodler.lastIndexOf("-"));
                String id = mapper.get(placeHodler);
                if(id == null) {
                    id = this.getSeqNextVal(sequence);
                    mapper.put(placeHodler, id);
                }
                matcher.appendReplacement(buf, id);
            }
            matcher.appendTail(buf);
            retList.add(buf.toString());
        }

        return retList;
    }

    private String getSeqNextVal(String sequence) {
        String sql = "SELECT "+sequence+".NEXTVAL FROM DUAL";
        String nextVal = this.metaDaoFactory.getJdbcTemplate().queryForObject(sql, String.class);
        return nextVal;
    }
}

