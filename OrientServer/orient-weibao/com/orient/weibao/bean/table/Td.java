package com.orient.weibao.bean.table;
/**
 * @author fangbin
 * 2020/3/23
 */
public class Td extends T {

    private String otherAttr;

    private String imgPath="";


    public String getOtherAttr() {
        return otherAttr;
    }

    public void setOtherAttr(String otherAttr) {
        this.otherAttr = otherAttr;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Td() {
       super();
    }

    public Td(String content) {
        super(content);
    }

    @Override
    public String toString() {
        return  "   <td rowspan="+rowspan+"  colspan="+colspan+" align='center'>"+content+"</td>\n";
    }
}
