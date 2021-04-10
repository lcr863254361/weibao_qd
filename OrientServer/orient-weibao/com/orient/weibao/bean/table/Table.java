package com.orient.weibao.bean.table;

import java.io.Serializable;
import java.util.List;
/**
 * @author fangbin
 * 2020/3/23
 */
public class Table implements Serializable {

    private Integer columnSize;

    private List<Tr> headList;

    private List<Tr> tailList;

    private List<Tr> trList;


    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Tr> getTrList() {
        return trList;
    }

    public void setTrList(List<Tr> trList) {
        this.trList = trList;
    }

    public Integer getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(Integer columnSize) {
        this.columnSize = columnSize;
    }

    public List<Tr> getHeadList() {
        return headList;
    }

    public void setHeadList(List<Tr> headList) {
        this.headList = headList;
    }

    public List<Tr> getTailList() {
        return tailList;
    }

    public void setTailList(List<Tr> tailList) {
        this.tailList = tailList;
    }


    private List<String> filesId;

    public void setFilesId(List<String> filesId) {
        this.filesId = filesId;
    }

    public List<String> getFilesId() {
        return filesId;
    }

    public boolean needId=true;

    public boolean isNeedId() {
        return needId;
    }

    public void setNeedId(boolean needId) {
        this.needId = needId;
    }

    private String imagePrefix;

    public String getImagePrefix() {
        return imagePrefix;
    }

    public void setImagePrefix(String imagePrefix) {
        this.imagePrefix = imagePrefix;
    }

    @Override
    public String toString() {

        StringBuilder StringBuilder = new StringBuilder("<div "+(needId?"id=\"myDiv\">":">")+"<table width='100%' border='1' cellspacing='0'><caption "+(needId?"id=\"title\">":">")+"<h1>"+title+"</h1></caption>\n");
        for(Tr tr:headList){
            StringBuilder.append(tr);
        }
        for(Tr tr:trList){
            StringBuilder.append(tr);
        }
        for(Tr tr:tailList){
            StringBuilder.append(tr);
        }
        StringBuilder.append("</table>");
        for(String id:filesId){
            StringBuilder.append("<img style='display:none' src='"+imagePrefix+"/orientForm/download.rdm?fileId="+id+"'>");
//            StringBuilder.append("<img src='/OrientServerTDM/orientForm/download.rdm?fileId="+id+"'>");
        }
        StringBuilder.append("</div>");
        return StringBuilder.toString();
    }
}
