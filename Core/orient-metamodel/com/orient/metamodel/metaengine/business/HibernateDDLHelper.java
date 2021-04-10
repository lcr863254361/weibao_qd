package com.orient.metamodel.metaengine.business;

import com.orient.metamodel.metadomain.Column;
import com.orient.metamodel.metadomain.RelationColumns;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metadomain.Table;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * a helper that transfer schema to hibernate file
 *
 * @author Seraph
 *         2016-10-11 下午4:29
 */
public class HibernateDDLHelper {

    public static HibernateDDLHelper getInstance(Properties hibernateProperties) {
        return new HibernateDDLHelper(hibernateProperties);
    }

    /**
     * 根据hibernate的mapping文件，创建数据库或者修改数据库
     *
     * @throws Exception e
     */
    public void generateDB(Schema newSchema) throws Exception {
        File mappingFile = generateHibernateMappingFile(newSchema);
        Configuration cfg = new Configuration().addProperties(hibernateProperties).addCacheableFile(mappingFile);
        SchemaUpdate update = new SchemaUpdate(cfg);
        update.execute(true, true);
    }

    private HibernateDDLHelper(Properties hibernateProperties) {
        this.hibernateProperties = hibernateProperties;
    }

    private File generateHibernateMappingFile(Schema newSchema) {
        File file = new File("table.hbm.xml");
        if (file.exists()) {
            file.delete();
        }

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");// 设置该文件的编码格式
        XMLWriter output = null;

        try {
            output = new XMLWriter(new FileOutputStream(file), format);
            output.write(asXMLDocument(newSchema));// 向文件中写内容
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return file;
    }

    /**
     * 将Schema写成XML的document
     *
     * @param newSchema
     * @return Document
     * @throws
     */
    private Document asXMLDocument(Schema newSchema) {
        Document document = DocumentHelper.createDocument();
        document.addDocType("hibernate-mapping",
                "-//Hibernate/Hibernate Mapping DTD 3.0//EN",
                "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd");
        Element root = document.addElement("hibernate-mapping");
        Set tableList = newSchema.getTables();
        System.out.println(newSchema.getTables().size());
        for (Iterator tableIt = tableList.iterator(); tableIt.hasNext(); ) {
            Table table = (Table) tableIt.next();
            if (table.getType() != null && (table.getType().equals("1") || table.getType().equals("2"))) {
                continue;
            }
            Element treeRootElement = root.addElement("class");
            treeRootElement.addAttribute("entity-name", "schema." + table.getTableName());
            treeRootElement.addAttribute("schema", hibernateProperties.getProperty("hibernate.connection.username"));
            //ID
            Element idElement = treeRootElement.addElement("id");
            idElement.addAttribute("name", "id");
            idElement.addAttribute("type", "java.lang.String");
            Element columnElement = idElement.addElement("column");
            columnElement.addAttribute("name", "id");
            columnElement.addAttribute("length", "38");
            Element generatorElement = idElement.addElement("generator");// 自动生成序列
            generatorElement.addAttribute("class", "sequence");
            Element paramElement = generatorElement.addElement("param");
            paramElement.addAttribute("name", "sequence");
            paramElement.setText("seq_" + table.getTableName());
            //系统时间
            Element ptElment = treeRootElement.addElement("property");
            ptElment.addAttribute("name", "SYS_DATE_TIME");
            ptElment.addAttribute("type", "java.util.Date");
            Element ctElement = ptElment.addElement("column");
            ctElement.addAttribute("name", "SYS_DATE_TIME");
            //用户Id
            ptElment = treeRootElement.addElement("property");
            ptElment.addAttribute("name", "SYS_USERNAME");
            ptElment.addAttribute("type", "java.lang.String");
            ctElement = ptElment.addElement("column");
            ctElement.addAttribute("name", "SYS_USERNAME");
            ctElement.addAttribute("length", "100");
            //是否删除
            ptElment = treeRootElement.addElement("property");
            ptElment.addAttribute("name", "SYS_IS_DELETE");
            ptElment.addAttribute("type", "java.lang.Integer");
            ctElement = ptElment.addElement("column");
            ctElement.addAttribute("name", "SYS_IS_DELETE");
            //密级缺省字段
            ptElment = treeRootElement.addElement("property");
            ptElment.addAttribute("name", "SYS_SECRECY");
            ptElment.addAttribute("type", "java.lang.String");
            ctElement = ptElment.addElement("column");
            ctElement.addAttribute("name", "SYS_SECRECY");
            ctElement.addAttribute("length", "100");

            //数据所在模型字段（这个是用于共享模型）
            ptElment = treeRootElement.addElement("property");
            ptElment.addAttribute("name", "SYS_SCHEMA");
            ptElment.addAttribute("type", "java.lang.String");
            ctElement = ptElment.addElement("column");
            ctElement.addAttribute("name", "SYS_SCHEMA");
            ctElement.addAttribute("length", "38");

            //添加数据操作类型(是否可以删除、是否可以修改，主要受流程控制)
            ptElment = treeRootElement.addElement("property");
            ptElment.addAttribute("name", "SYS_OPERATE");
            ptElment.addAttribute("type", "java.lang.String");
            ctElement = ptElment.addElement("column");
            ctElement.addAttribute("name", "SYS_OPERATE");
            ctElement.addAttribute("length", "100");
            //添加数据所属流程(目前数据已经被流程占用)
            ptElment = treeRootElement.addElement("property");
            ptElment.addAttribute("name", "SYS_FLOW");
            ptElment.addAttribute("type", "java.lang.String");
            ctElement = ptElment.addElement("column");
            ctElement.addAttribute("name", "SYS_FLOW");
            ctElement.addAttribute("length", "200");

            for (Iterator columnIterator = table.getCwmTabColumnses().iterator(); columnIterator.hasNext(); ) {
                Column column = (Column) columnIterator.next();
                if (column.getCategory().equals(Column.CATEGORY_COMMON)) {//普通属性
                    Element commonElement = treeRootElement.addElement("property");
                    commonElement.addAttribute("name", column.getColumnName());
                    Element colElement = commonElement.addElement("column");
                    if (column.getType().equals("Decimal")) {
                        commonElement.addAttribute("type", "java.math.BigDecimal");
                        colElement.addAttribute("name", column.getColumnName());
                        colElement.addAttribute("precision", "30");
                        colElement.addAttribute("scale", "10");
                        colElement.addAttribute("not-null", "false");
                    } else {
                        if (column.getType().equals("String")) {
                            commonElement.addAttribute("type", "java.lang.String");
                        } else if (column.getType().equals("ODS") || column.getType().equals("Hadoop")) {
                            commonElement.addAttribute("type", "java.lang.String");
                        } else if (column.getType().equals("Boolean")) {
                            commonElement.addAttribute("type", "java.lang.Boolean");
                        } else if (column.getType().equals("Byte")) {
                            commonElement.addAttribute("type", "java.lang.String");
                        } else if (column.getType().equals("Date")) {
                            commonElement.addAttribute("type", "java.util.Date");
                        } else if (column.getType().equals("DateTime")) {
                            commonElement.addAttribute("type", "java.util.Calendar");
                        } else if (column.getType().equals("Double")) {
                            commonElement.addAttribute("type", "java.lang.Double");
                        } else if (column.getType().equals("Float")) {
                            commonElement.addAttribute("type", "java.lang.Float");
                        } else if (column.getType().equals("Integer")) {
                            commonElement.addAttribute("type", "java.lang.Integer");
                        } else if (column.getType().equals("BigInteger")) {
                            commonElement.addAttribute("type", "java.lang.Long");
                        } else if (column.getType().equals("Text")) {
                            commonElement.addAttribute("type", "java.sql.Clob");
                        } else if (column.getType().equals("SubTable")) {
                            //嵌套表
                            commonElement.addAttribute("type", "java.sql.Clob");
                        } else if (column.getType().equals("NameValue")) {
                            //名值对
                            commonElement.addAttribute("type", "java.sql.Clob");
                        } else if (column.getType().equals("Check")) {
                            //检查
                            commonElement.addAttribute("type", "java.sql.Clob");
                        } else if (column.getType().equals("File")) {
                            //文件
                            commonElement.addAttribute("type", "java.lang.String");
                            colElement.addAttribute("length", "2000");
                        }
                        colElement.addAttribute("name", column.getColumnName());
                        if (column.getType().equals("String")) {
                            Long maxlength = 2 * column.getMaxLength();
                            colElement.addAttribute("length", maxlength.toString());
                        } else if (column.getType().equals("Byte")) {
                            colElement.addAttribute("length", "1");
                        }

                        colElement.addAttribute("not-null", "false");
                    }
                } else if (column.getCategory().equals(Column.CATEGORY_RELATION)) {

                    /*table.getCwmRelationColumnses().forEach(relationColumn -> {
                        if (column.getIdentity().equals(relationColumn.getCwmTabColumnsByColumnId().getIdentity())) {
                            switch (relationColumn.getRelationType()) {
                                case RelationColumns.RELATIONTYPE_ONE2ONE:
                                    if (relationColumn.getIsFK() == 1) {
                                        Element gxElement = treeRootElement.addElement("property");
                                        gxElement.addAttribute("name", relationColumn.getRefTable().getTableName() + "_id");
                                        gxElement.addAttribute("type", "java.lang.String");
                                        Element colElement = gxElement.addElement("column");
                                        colElement.addAttribute("name", relationColumn.getRefTable().getTableName() + "_id");
                                        colElement.addAttribute("length", "38");
                                    }
                                    break;
                                case RelationColumns.RELATIONTYPE_MANY2ONE:
                                    assert (relationColumn.getIsFK().equals(new Long(1)));
                                    Element gxElement = treeRootElement.addElement("property");
                                    gxElement.addAttribute("name", relationColumn.getRefTable().getTableName() + "_id");
                                    gxElement.addAttribute("type", "java.lang.String");
                                    Element colElement = gxElement.addElement("column");
                                    colElement.addAttribute("name", relationColumn.getRefTable().getTableName() + "_id");
                                    colElement.addAttribute("length", "38");
                                    break;
                            }
                        }
                    });*/

                    RelationColumns rc = column.getRelationColumn();
                    if (rc.getRelationType() == RelationColumns.RELATIONTYPE_ONE2ONE) {
                        //一对一关系
                        if (rc.getIsFK().equals(new Long(1))) { //是外键时才创建
                            Element gxElement = treeRootElement.addElement("property");
                            gxElement.addAttribute("name", rc.getRefTable().getTableName() + "_id");
                            gxElement.addAttribute("type", "java.lang.String");
                            Element colElement = gxElement.addElement("column");
                            colElement.addAttribute("name", rc.getRefTable().getTableName() + "_id");
                            colElement.addAttribute("length", "38");
                        }
                    } else if (rc.getRelationType() == RelationColumns.RELATIONTYPE_MANY2ONE) {
                        assert (rc.getIsFK().equals(new Long(1)));
                        Element gxElement = treeRootElement.addElement("property");
                        gxElement.addAttribute("name", rc.getRefTable().getTableName() + "_id");
                        gxElement.addAttribute("type", "java.lang.String");
                        Element colElement = gxElement.addElement("column");
                        colElement.addAttribute("name", rc.getRefTable().getTableName() + "_id");
                        colElement.addAttribute("length", "38");
                    }
                }
            }

            if (table.getChildTables().size() != 0) {
                for (Iterator childTableit = table.getChildTables().iterator(); childTableit.hasNext(); ) {
                    Table childTable = (Table) childTableit.next();
                    autoCreateChildTable(treeRootElement, childTable);
                }
            }
        }
        return document;
    }

    /**
     * 生成数据类的子数据类.（利用了union-subclass策略标签来定义子类的,将父类的字段继承到子类）
     *
     * @param treeRootElement
     * @param childTable
     * @return void
     */
    private void autoCreateChildTable(Element treeRootElement, Table childTable) {
        Element sjlElment = treeRootElement.addElement("union-subclass");
        sjlElment.addAttribute("entity-name", "schema."
                + childTable.getTableName());
        sjlElment.addAttribute("table", childTable.getTableName());

        for (Iterator columnit = childTable.getCwmTabColumnses().iterator(); columnit.hasNext(); ) {
            Column column = (Column) columnit.next();
            if (column.getCategory().equals(Column.CATEGORY_COMMON)) {
                Element ptsxElment = sjlElment.addElement("property");
                ptsxElment.addAttribute("name", column.getColumnName());
                Element colElement = ptsxElment.addElement("column");
                if (column.getType().equals("Decimal")) {
                    ptsxElment.addAttribute("type", "java.math.BigDecimal");
                    colElement.addAttribute("name", column.getColumnName());
                    colElement.addAttribute("precision", "30");
                    colElement.addAttribute("scale", "10");
                    colElement.addAttribute("not-null", "false");
                } else {
                    if (column.getType().equals("String")) {
                        ptsxElment.addAttribute("type", "java.lang.String");
                    } else if (column.getType().equals("ODS") || column.getType().equals("Hadoop")) {
                        ptsxElment.addAttribute("type", "java.lang.String");
                    } else if (column.getType().equals("Boolean")) {
                        ptsxElment.addAttribute("type", "java.lang.Boolean");
                    } else if (column.getType().equals("Byte")) {
                        ptsxElment.addAttribute("type", "java.lang.String");
                    } else if (column.getType().equals("Date")) {
                        ptsxElment.addAttribute("type", "java.util.Date");
                    } else if (column.getType().equals("DateTime")) {
                        ptsxElment.addAttribute("type", "java.util.Calendar");
                    } else if (column.getType().equals("Double")) {
                        ptsxElment.addAttribute("type", "java.lang.Double");
                    } else if (column.getType().equals("Float")) {
                        ptsxElment.addAttribute("type", "java.lang.Float");
                    } else if (column.getType().equals("Integer")) {
                        ptsxElment.addAttribute("type", "java.lang.Integer");
                    } else if (column.getType().equals("BigInteger")) {
                        ptsxElment.addAttribute("type", "java.lang.Long");
                    } else if (column.getType().equals("Text")) {
                        ptsxElment.addAttribute("type", "java.sql.Clob");
                    } else if (column.getType().equals("SubTable") || column.getType().equals("Check") || column.getType().equals("NameValue")) {
                        ptsxElment.addAttribute("type", "java.sql.Clob");
                    } else if (column.getType().equals("File")) {
                        ptsxElment.addAttribute("type", "java.lang.String");
                    }
                    colElement.addAttribute("name", column.getColumnName());
                    if (column.getType().equals("String")) {
                        Long maxlength = 2 * column.getMaxLength();
                        colElement.addAttribute("length", maxlength.toString());
                    } else if (column.getType().equals("Byte")) {
                        colElement.addAttribute("length", "1");
                    }
                    colElement.addAttribute("not-null", "false");
                }
            } else if (column.getCategory().equals(Column.CATEGORY_RELATION)) {    //关系属性
                RelationColumns rc = column.getRelationColumn();
                if (rc.getRelationType() == RelationColumns.RELATIONTYPE_ONE2ONE) {
                    //一对一关系
                    if (rc.getIsFK().equals(new Long(1)))//是外键时才创建
                    {
                        Element gxElement = treeRootElement.addElement("property");
                        gxElement.addAttribute("name", rc.getRefTable().getTableName() + "_id");
                        gxElement.addAttribute("type", "java.lang.String");
                        Element colElement = gxElement.addElement("column");
                        colElement.addAttribute("name", rc.getRefTable().getTableName() + "_id");
                        colElement.addAttribute("length", "38");
                    }
                } else if (rc.getRelationType() == RelationColumns.RELATIONTYPE_MANY2ONE) {
                    assert (rc.getIsFK().equals(new Long(1)));
                    Element gxElement = treeRootElement.addElement("property");
                    gxElement.addAttribute("name", rc.getRefTable().getTableName() + "_id");
                    gxElement.addAttribute("type", "java.lang.String");
                    Element colElement = gxElement.addElement("column");
                    colElement.addAttribute("name", rc.getRefTable().getTableName() + "_id");
                    colElement.addAttribute("length", "38");
                }
            }
        }

        if (childTable.getChildTables().size() != 0) {
            childTable.getChildTables().forEach(childSonTable -> autoCreateChildTable(sjlElment, childSonTable));
        }
    }

    private Properties hibernateProperties;
}
