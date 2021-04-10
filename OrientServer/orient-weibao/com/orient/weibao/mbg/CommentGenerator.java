package com.orient.weibao.mbg;

import org.mybatis.generator.internal.DefaultCommentGenerator;

/**
 * @author Sun
 * @date:23:00 2019/10/1
 * @description: 自定义生成策略
 */
public class CommentGenerator extends DefaultCommentGenerator {

//    /**
//     * 去数据库的备注作为ApiModel
//     //     */
//    @Override
//    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
//                                IntrospectedColumn introspectedColumn) {
//        String remarks = introspectedColumn.getRemarks();
//        //根据参数和备注信息判断是否添加备注信息
//        if(StringUtility.stringHasValue(remarks)){
//            //数据库中特殊字符需要转义
//            if(remarks.contains("\"")){
//                remarks = remarks.replace("\"","'");
//            }
//            //给model的字段添加swagger注解
//            field.addJavaDocLine("@ApiModelProperty(value = \""+remarks+"\")");
//        }
//    }
}
