package com.orient.modeldata.persistent;

import com.orient.web.base.BaseBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@Service
public class PersistentService extends BaseBusiness {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public int batchInsert(String sql, List<String> columnNames, List<Map<String, Object>> columnMapping) {
        int retVal = 0;
        BatchPreparedStatementSetter setter = new ImportBatchPreparedStatementSetter(columnNames, columnMapping);
        int[] cnts = jdbcTemplate.batchUpdate(sql, setter);

        if(cnts != null) {
            for(int cnt : cnts) {
                retVal -= cnt;
            }
        }
        return retVal;
    }

    public class ImportBatchPreparedStatementSetter implements BatchPreparedStatementSetter {
        private List<String> columnNames;

        private List<Map<String, Object>> columnMapping;

        public ImportBatchPreparedStatementSetter(List<String> columnNames, List<Map<String, Object>> columnMapping) {
            this.columnNames = columnNames;
            this.columnMapping = columnMapping;
        }

        @Override
        public int getBatchSize() {
            if(columnMapping == null) {
                return 0;
            }
            else {
                return columnMapping.size();
            }
        }

        @Override
        public void setValues(PreparedStatement ps, int idx) throws SQLException {
            Map<String, Object> rowMap = columnMapping.get(idx);
            for(int i=0; i<columnNames.size(); i++) {
                String colName = columnNames.get(i);
                Object obj = rowMap.get(colName);
                ps.setObject(i+1, obj);
            }
        }
    }
}

