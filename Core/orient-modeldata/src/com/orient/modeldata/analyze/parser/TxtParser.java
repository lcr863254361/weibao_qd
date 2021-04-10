package com.orient.modeldata.analyze.parser;

import com.orient.utils.CSVUtil.CsvReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-24 15:38
 */
public class TxtParser implements Parser {
    private CsvReader reader = null;

    public TxtParser(File file, String rowSplit, String columnSplit) {
        try {
            this.reader = new CsvReader(new FileInputStream(file), columnSplit.charAt(0), Charset.forName("GBK"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean readHeaders(int row) {
        try {
            for(int i=0; i<row; i++) {
                if(!this.reader.readRecord()) {
                    return false;
                }
            }
            return this.reader.readHeaders();
        }
        catch (IOException e) {
            return false;
        }
    }

    @Override
    public List<String> getHeaders() {
        try {
            return Arrays.asList(this.reader.getHeaders());
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean readRecord() {
        try {
            return this.reader.readRecord();
        }
        catch (IOException e) {
            return false;
        }
    }

    //添加判断当前行是否为空的接口 TeddyJohnson 2018.7.27
    @Override
    public boolean readCurrentRecord() {
        return false;
    }

    @Override
    public List<String> getValues() {
        try {
            return Arrays.asList(this.reader.getValues());
        }
        catch (IOException e) {
            return null;
        }
    }
}
