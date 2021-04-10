package com.orient.weibao.bean.table;

import java.io.Serializable;
import java.util.List;
/**
 * @author fangbin
 * 2020/3/23
 */
public class Tr implements Serializable {

    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        StringBuilder StringBuilder = new StringBuilder("<tr>\n");
        for(T t:list){
            StringBuilder.append(t.toString());
        }
        StringBuilder.append("</tr>\n");
        return  StringBuilder.toString();
    }
}
