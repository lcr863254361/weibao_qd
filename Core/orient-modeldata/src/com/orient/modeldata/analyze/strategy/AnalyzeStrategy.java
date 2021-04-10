package com.orient.modeldata.analyze.strategy;

import com.orient.modeldata.analyze.config.AnalyzeConfig;
import com.orient.modeldata.analyze.parser.Parser;
import com.orient.modeldata.analyze.processor.Processor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-08 10:33
 */
public abstract class AnalyzeStrategy {
    private File file;
    private boolean hasHead = true;
    private int startLine = 0;
    private int endLine = Integer.MAX_VALUE;
    private boolean isPreview = false;
    private boolean isFileOutput = false;
    private boolean isDBInput = false;

    private Parser parser;
    private String tableName;
    private List<Processor> processors = new ArrayList<>();

    private List<String> head;

    public AnalyzeStrategy(AnalyzeConfig config) {
        this.file = config.getFile();
        this.hasHead = config.isHasHead();
        this.startLine = config.getStartLine();
        this.endLine = config.getEndLine();
        this.isPreview = config.isPreview();
        this.isFileOutput = config.isFileOutput();
        this.isDBInput = config.isDBInput();
        this.tableName = config.getTableName();
    }

    public List<String> doHeadAnalyze()  {
        if(this.head != null) {
            return this.head;
        }
        //if(this.parser.readHeaders(this.startLine)) {
        //数据预览的标题行都是取数据文件的第一行 TeddyJohnson 2018.7.27
        if(this.parser.readHeaders(0)) {
            List<String> data = this.parser.getHeaders();
            if(this.hasHead) {
                this.head = data;
            }
            else {
                List<String> headers = new ArrayList<>();
                for(int i=1; i<=data.size(); i++) {
                    headers.add("Col"+i);
                }
                this.head = headers;
            }
        }
        //获取第一行标题数据后，重新将数据文件的当前行置为界面设置的数据开始行 TeddyJohnson 2018.7.27
        //例：数据开始行为2时，startLine=1，xls文件也从第2行开始解析，因this.row是从0开始计数，则this.row=1。
        this.parser.readHeaders(this.startLine);
        return this.head;
    }

    public List<Map<String, Object>> doContentAnalyze() {
        List<Map<String, Object>> retList = new ArrayList<>();
        if(!this.hasHead) {
            List<String> firstLineData = this.parser.getHeaders();
            Map<String, Object> firstLineResult = new HashMap<>();
            for(Processor processor: this.processors) {
                processor.process(firstLineData, firstLineResult);
            }
            retList.add(firstLineResult);
        }
        for(int i=this.startLine+1; i<=this.endLine; i++){
            //1.添加当前行是否为空的判断 TeddyJohnson 2018.7.27
            if(this.parser.readCurrentRecord())
            {
                List<String> data = this.parser.getValues();
                Map<String, Object> result = new HashMap<>();
                for(Processor processor: this.processors) {
                    processor.process(data, result);
                }
                retList.add(result);
            }
            else
                break;
            //2.当前行解析完后进入下一行解析 TeddyJohnson 2018.7.27
            if(this.parser.readRecord())
                continue;
            else
                break;
        }
        return retList;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isHasHead() {
        return hasHead;
    }

    public void setHasHead(boolean hasHead) {
        this.hasHead = hasHead;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public boolean isPreview() {
        return isPreview;
    }

    public void setIsPreview(boolean isPreview) {
        this.isPreview = isPreview;
    }

    public boolean isFileOutput() {
        return isFileOutput;
    }

    public void setIsFileOutput(boolean isFileOutput) {
        this.isFileOutput = isFileOutput;
    }

    public boolean isDBInput() {
        return isDBInput;
    }

    public void setIsDBInput(boolean isDBInput) {
        this.isDBInput = isDBInput;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Processor> getProcessors() {
        return processors;
    }

    public void setProcessors(List<Processor> processors) {
        this.processors = processors;
    }

    public List<String> getHead() {
        return head;
    }

    public void setHead(List<String> head) {
        this.head = head;
    }
}
