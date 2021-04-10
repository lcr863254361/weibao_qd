package com.orient.modeldata.analyze.strategy;


import com.orient.modeldata.analyze.bean.ColMapDesc;
import com.orient.modeldata.analyze.config.TxtAnalyzeConfig;
import com.orient.modeldata.analyze.parser.TxtParser;
import com.orient.modeldata.analyze.processor.DefalutValueProcessor;
import com.orient.modeldata.analyze.processor.SQLTimeProcessor;
import com.orient.modeldata.analyze.processor.SQLTimestampProcessor;
import com.orient.modeldata.analyze.processor.StringProcessor;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-08 10:41
 */
public class TxtAnalyzeStrategy extends AnalyzeStrategy {
    private String rowSplit = "\r\n";
    private String columnSplit = "\t";

    public TxtAnalyzeStrategy(TxtAnalyzeConfig config) {
        super(config);
        this.rowSplit = config.getRowSplit();
        this.columnSplit = config.getColumnSplit();

        this.setParser(new TxtParser(this.getFile(), this.rowSplit, this.columnSplit));

        List<String> headers = this.doHeadAnalyze();
        if(this.isPreview()) {
            for(int i=0; i<headers.size(); i++) {
                String header = headers.get(i);
                StringProcessor processor = new StringProcessor();
                processor.setColNum(i);
                processor.setFileColName(header);
                processor.setMappedColName(header);
                this.getProcessors().add(processor);
            }
        }
        else if(this.isDBInput()) {
            Map<String, ColMapDesc> relationMap = config.getRelationMap();
            for(String key : relationMap.keySet()) {
                ColMapDesc desc = relationMap.get(key);
                String dbColType = desc.getDbColType();

                int colNum = -1;
                String fileColName = desc.getFileColName();
                if(fileColName!=null && !"".equals(fileColName)) {
                    colNum = headers.indexOf(fileColName);
                }
                if("C_DateTime".equals(dbColType) || "C_Date".equals(dbColType)) {
                    if("时间戳".equals(desc.getTimeFormat())) {
                        SQLTimestampProcessor processor = new SQLTimestampProcessor();
                        processor.setColNum(colNum);
                        processor.setFileColName(desc.getFileColName());
                        processor.setMappedColName(desc.getDbColName());
                        this.getProcessors().add(processor);
                    }
                    else {
                        SQLTimeProcessor processor = new SQLTimeProcessor();
                        processor.setColNum(colNum);
                        processor.setFileColName(desc.getFileColName());
                        processor.setMappedColName(desc.getDbColName());
                        processor.setFormat(new SimpleDateFormat(desc.getTimeFormat()));
                        this.getProcessors().add(processor);
                    }
                }
                else if("C_Relation".equals(dbColType)) {
                    DefalutValueProcessor processor = new DefalutValueProcessor();
                    processor.setMappedColName(desc.getDbColName());
                    processor.setDefalutValue(desc.getDefalutValue());
                    this.getProcessors().add(processor);
                }
                else {
                    StringProcessor processor = new StringProcessor();
                    processor.setColNum(colNum);
                    processor.setFileColName(desc.getFileColName());
                    processor.setMappedColName(desc.getDbColName());
                    this.getProcessors().add(processor);
                }
            }
        }
        else if(this.isFileOutput()) {

        }
    }

    @Override
    public List<String> doHeadAnalyze()  {
        return super.doHeadAnalyze();
    }

    @Override
    public List<Map<String, Object>> doContentAnalyze() {
        return super.doContentAnalyze();
    }
}
