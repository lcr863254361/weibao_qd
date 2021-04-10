package com.orient.weibao.bean.table;
/**
 * @author fangbin
 * 2020/3/23
 */
public class Th extends  T {

    public Th(String content) {
        super(content);
    }

    @Override
    public String toString() {
        return  "  <th>"+content+"</th>\n";
    }
}
