package com.orient.webservice.tbom.Impl;

import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metadomain.Table;
import com.orient.metamodel.metadomain.View;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IMetaModel;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.sysmodel.domain.tbom.*;
import com.orient.sysmodel.operationinterface.ITbom;
import com.orient.sysmodel.roleengine.impl.DTbomCompare;
import com.orient.utils.CommonTools;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Reader;
import java.io.StringReader;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class SetTbom extends TbomBean {
    private String saveSign;
    private ISchema curSchema;

    @SuppressWarnings("rawtypes")
    @Transactional(propagation = Propagation.REQUIRED)
    public String execute(String str, String schemaid, String username,
                          String tbomName) {
        // 校验schemaID
        IMetaModel metaModel = metaEngine.getMeta(false);
        curSchema = metaModel.getISchemaById(schemaid);
        if (null == curSchema) {
            return "schemaIdNotExists";
        }

        try {
            String tbomStr = str.substring(1, str.length());
            /*
             * 模型传来的字符串包含保存方式和XML数据，字符串的第一位是保存方式标记， 0表示保存，1表示另存为（用来清空现有的节点ID）。
			 */
            saveSign = str.substring(0, 1);
            TbomDir tbomDir = null;
            if (saveSign.equals("0")) {
                String[] tbom_arr = tbomName.split(";==;");
                String id = tbom_arr[0];
                String name = tbom_arr[1];
                String t_type = tbom_arr[2];
                tbomDir = roleEngine.getRoleModel(false).getTbomDirs().get(id);
                tbomName = tbomDir.getName();
                tbomDir.setName(name);
                tbomDir.setType(Long.parseLong(t_type));
                tbomDir.setModifiedTime(new Date());
                tbomDir.setIsdelete(new Long(1));
                tbomService.getTbomDirDAO().merge(tbomDir);
            } else if (saveSign.equals("1")) {
                String[] tbom_arr = tbomName.split(";==;");
                String name = tbom_arr[0];
                String type = tbom_arr[1];
                tbomDir = new TbomDir();
                tbomDir.setName(name);
                tbomDir.setType(Long.parseLong(type));
                tbomDir.setSchemaid(schemaid);
                tbomDir.setIsLock(new Long(1));
                tbomDir.setModifiedTime(new Date());
                tbomDir.setUserid(username);
                tbomDir.setLockModifiedTime(new Date());
                tbomDir.setIsdelete(new Long(1));
                Map<String, TbomDir> tbomDirMap = roleEngine.getRoleModel(false).getTbomDirs();
                List<TbomDir> tbomList = tbomDirMap.values().stream().sorted((t1, t2) -> t1.getOrder_sign().intValue() - t2.getOrder_sign().intValue()).collect(Collectors.toList());
                if (tbomList == null || tbomList.size() == 0) {
                    tbomDir.setOrder_sign(new Long(1));
                } else {
                    tbomDir.setOrder_sign(Long.valueOf(((TbomDir) tbomList
                            .get(tbomList.size() - 1)).getOrder_sign()
                            .toString()) + 1);
                }

                tbomService.getTbomDirDAO().save(tbomDir);
            }
            List<String> idList = new ArrayList<String>();
            // 读取客户端传来的xml字符串，并且利用dom4j解析出来，存放到TBOM类中
            Reader reader = new StringReader(tbomStr);

			/* 此MAP用于存放所有的TBOM，按照每个节点的xml_id存放 */
            Map<String, Tbom> tbomMap = new HashMap<String, Tbom>();

			/* 此MAP存放每个节点的模型关系关联节点集合，集合由节点的xml_id组成 */
            Map<Tbom, String> relationMap = new HashMap<Tbom, String>();
            SAXReader saxReader = new SAXReader();
            try {
                Document document = saxReader.read(reader);
                Element tree = document.getRootElement();
                Element root = tree.element("item");
                Set<Tbom> childset = new HashSet<Tbom>();
                int i = 0;// 用于分割出节点的顺序
                Tbom rootNode = getTbom(root, i, tbomMap, relationMap);
                rootNode.setSchema((Schema) curSchema);
                final String finalTbomName = tbomName;
                List<ITbom> tbomRoots = roleEngine.getRoleModel(false).getTbomRoots();
                Predicate<ITbom> filterFunc = tbom -> tbom.getName().equals(finalTbomName) && tbom.getSchema().getId().equals(schemaid);
                Boolean exists = tbomRoots.stream().filter(filterFunc).count() != 0;
                Tbom rtbom = null;
                if (exists) {
                    rtbom = (Tbom) tbomRoots.stream().filter(filterFunc).findFirst().get();
                }
                if (saveSign.equals("0") && exists) {
                    Tbom rootTbom;
                    if (rootNode.getId() == null
                            || rootNode.getId().length() == 0
                            || rootNode.getId().equals("")) {
                        rootTbom = rtbom;
                        rootNode.setId(rootTbom.getId());
                    } else {
                        rootTbom = tbomService.findById(rootNode.getId());
                    }
                    // 级联删除该节点的所有子节点
                    tbomService.updateTbom(rootTbom.getId());
                    tbomService.getDao().merge(rootNode);
                } else {
                    tbomService.getDao().save(rootNode);
                }
                idList.add(rootNode.getId());

                // 保存静态根节点的动态子节点
                saveDynamicColumn(root, rootNode);
                i = i + 1;

                // 保存静态子节点及其动态子节点
                childTbom(tbomMap, relationMap, root, childset, i, rootNode, idList);

                rootNode.setParenttbom(null);
                rootNode.setChildTboms(childset);
                rootNode.setCwmRelationTbomsForNodeId(new HashSet<>());
                rootNode.setCwmRelationTbomsForRelationId(new HashSet<>());
                /* 将结点的关联结点加到TBOM对象中 */
                for (Iterator it = relationMap.keySet().iterator(); it
                        .hasNext(); ) {
                    Tbom tbom = (Tbom) it.next();
                    String relations = relationMap.get(tbom);
                    if (relations != null) {
                        String[] relation = relations.split(",");
                        Set<Tbom> relationset = new HashSet<Tbom>();
                        for (int j = 0; j < relation.length; j++) {
                            relationset.add(tbomMap.get(relation[j]));
                            RelationTbom rt = new RelationTbom();
                            rt.setCwmTbomByNodeId(tbom);
                            rt.setCwmTbomByRelationId(tbomMap.get(relation[j]));
                            tbomService.getRelationTbomDAO().save(rt);// 保存结点的模型关系关联到数据库中
                        }
                        tbom.setCwmRelationTbomsForRelationId(relationset);
                    }
                }
                //刷新内存tbom根节点
                roleEngine.getRoleModel(false).freshTbomRoot(tbomDir, rootNode);
            } catch (DocumentException e) {
                e.printStackTrace();
                return "false";
            }
            StringBuffer idStr = new StringBuffer();
            for (String id : idList) {
                idStr.append(";==;").append(id);
            }

            //刷新内存中角色对应tbom
            //IRoleModel model = roleEngine.getRoleModel(true);
            return tbomDir.getId() + ";==;" + tbomDir.getName()
                    + idStr.toString();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "false";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    /**
     * 解析Element的内容，转成TBOM对象
     *
     * @param element
     * @param i
     * @param tbomMap
     * @param relationMap
     * @return
     * @Method: getTbom
     */
    private Tbom getTbom(Element element, int i, Map<String, Tbom> tbomMap,
                         Map<Tbom, String> relationMap) {
        Long isroot;// 是否是根节点 ，1表示是根节点，0反之
        if (i == 0) {
            isroot = new Long(1);
        } else {
            isroot = new Long(0);
        }

        String id = null;
        if (!saveSign.equals("1")) {
            id = element.attributeValue("id");// 结点ID
        }
        String name = element.attributeValue("text");// 结点名称
        // 结点层次结构的id，根节点是“000”，下一层是“000001”，“000002”，依此类推
        String xmlid = element.attributeValue("xml_id");
        String description = element.attributeValue("description");// 结点的描述
        String det_text = element.attributeValue("det_text");// 结点的详细文字
        String big_img = element.attributeValue("big_img");// 结点的大图标
        String nor_img = element.attributeValue("nor_img");// 结点的中图标
        String sma_img = element.attributeValue("sma_img");// 结点的小图标
        String url = element.attributeValue("url");// 链接地址

        // String type = element.attributeValue("type");
        String table = null;// table和view都是结点的数据源，存放的是数据源的ID；
        String templateId = null;
        String view = null;
        Long type = null;// 结点数据源的类型，0表示是数据类，1表示是视图
        Table tableBean = null;
        View viewBean = null;
        String data_source = null;
        if (element.attributeValue("tbomtable") != null) {
            String[] source = element.attributeValue("tbomtable").split(",.;,");
            table = source[0].indexOf("-") != -1 ? source[0].substring(0, source[0].indexOf("-")) : source[0];
            templateId = source[0].indexOf("-") != -1 ? source[0].substring(source[0].indexOf("-") + 4) : "";
            type = new Long(0);
            tableBean = (Table) curSchema.getTableById(table);
            data_source = "t" + table;
        } else if (element.attributeValue("tbomview") != null) {
            String[] source = element.attributeValue("tbomview").split(",.;,");
            view = source[0].indexOf("-") != -1 ? source[0].substring(0, source[0].indexOf("-")) : source[0];
            templateId = source[0].indexOf("-") != -1 ? source[0].substring(source[0].indexOf("-") + 4) : "";
            type = new Long(1);
            viewBean = (View) curSchema.getViewById(view);
            data_source = "v" + table;
        }
        String expression = null;
        String originexpression = element.attributeValue("expression");
        if (null != originexpression && !("").equals(originexpression)) {
            String[] str = originexpression.split(";;;;", -2);
            Boolean error = false;
            StringBuffer exps = new StringBuffer();
            for (int b = 0; b < str.length; b++) {
                if (str[b].indexOf(";;,,") > 0) {
                    StringBuffer exp = new StringBuffer();
                    String[] dcstr = str[b].split(";;,,", -2);
                    String columnid = dcstr[0].split(";,;")[0];// 字段所在的id
                    String datatype = dcstr[0].split(";,;")[1];
                    String columnname = null;
                    if("$当前部门".equals(columnid) || "$当前用户".equals(columnid) || "$当前角色".equals(columnid)) {
                        columnname = "'"+columnid+"'";
                    }
                    else {
                        if (table != null) {
                            IColumn col = tableBean.getColumnById(columnid);
                            if (null != col) {
                                if (col.getCategory() == 2) {
                                    columnname = col.getRelationColumnIF()
                                            .getRefTable() + "_ID";
                                } else {
                                    columnname = col.getColumnName();
                                }
                            }

                        } else if (view != null) {
                            IColumn col = viewBean.getColumnById(columnid);
                            if (null != col) {
                                if (col.getCategory() == 2) {
                                    columnname = col.getRelationColumnIF()
                                            .getRefTable() + "_ID";
                                } else {
                                    columnname = col.getColumnName();
                                }
                            }
                        }
                    }
                    if (columnname == null) {
                        error = true;
                        break;
                    }
                    exp.append(columnname).append(" ").append(dcstr[1]).append(" ");
                    if (dcstr[2] != null && dcstr[2] != "") {
                        String ziduanzhi = null;
                        if (datatype.equals("PlaceHolder")) {
                            if (dcstr[2].trim().length() != 0) {
                                String[] vals = dcstr[2].split(",");
                                ziduanzhi = "(";
                                for(String val : vals) {
                                    ziduanzhi = ziduanzhi + "'" + val + "',";
                                }
                                ziduanzhi = ziduanzhi.substring(0, ziduanzhi.length()-1) + ")";
                            }
                        } else if (datatype.equals("Date")) {
                            if (dcstr[2].trim().length() != 0) {
                                ziduanzhi = "to_date('" + dcstr[2]
                                        + "','yyyy-mm-dd') ";
                            }
                        } else if (datatype.equals("DateTime")) {
                            if (dcstr[2].trim().length() != 0) {
                                ziduanzhi = "to_date('" + dcstr[2]
                                        + "','yyyy-mm-dd hh24:mi:ss') ";
                            }
                        } else {
                            ziduanzhi = dcstr[2].trim().length() == 0 ? ""
                                    : ("'" + dcstr[2] + "' ");
                        }
                        exp.append(ziduanzhi);
                    }
                    if(exp.indexOf("$当前") >= 0) {
                        exps.append(" ##").append(exp).append("## ");
                    }
                    else {
                        exps.append(" ").append(exp).append(" ");
                    }
                    if (dcstr[3] != null && dcstr[3] != "") {
                        exps.append(dcstr[3]).append(" ");
                    }
                }
            }
            if (error) {
                expression = "";
                originexpression = "";
            } else {
                expression = exps.toString();
            }
        }

        String showType = "";
        StringBuffer role = new StringBuffer();
        if (isroot == 0)//静态节点非根节点，才有权限设置
        {
            showType = element.attributeValue("showType");// 显示类型
            if (showType == null || showType.isEmpty()) {
                showType = "0";
            }
            String str = element.attributeValue("role");// 角色定义
            if (!CommonTools.isNullString(str)) {

                String[] strArry;
                if (str.contains("null"))
                    strArry = str.substring(4, str.length()).split("..;;");
                else
                    strArry = str.split("..;;");
                for (int j = 0; j < strArry.length; j++) {
                    String[] co = strArry[j].split(",..;");
                    String roleId = co[0];
                    role.append(roleId);
                    if (j != strArry.length - 1) {
                        role.append(",");
                    }
                }
            }
        }

        String relation = element.attributeValue("relations");
        Long ordersign = new Long(xmlid.substring(i * 3));// 同一父节点的结点集合的顺序
        Tbom tbom = new Tbom(id, tableBean, viewBean, name, type, description,
                det_text, big_img, nor_img, sma_img, ordersign, isroot, xmlid,
                new Long(1), expression, originexpression, url, showType, templateId);
        if(isroot == 1) {
            tbom.setExpandLevel(element.attributeValue("expandLevel"));
        }
        //添加静态子节点角色定义
        if (isroot == 0)//静态节点非根节点，才有权限设置
        {
            TbomRole tbomRole = new TbomRole(null, role.toString(), data_source);
            tbom.getTbomRoles().add(tbomRole);
        }
        tbomMap.put(xmlid, tbom);
        relationMap.put(tbom, relation);
        return tbom;
    }

    /**
     * 递归遍历Element
     *
     * @param tbomMap
     * @param relationMap
     * @param parentElement
     * @param childset
     * @param i
     * @param parentNode
     * @param idList
     * @throws Exception
     * @Method: childTbom
     */
    @SuppressWarnings("rawtypes")
    @Transactional(propagation = Propagation.REQUIRED)
    private void childTbom(Map<String, Tbom> tbomMap,
                           Map<Tbom, String> relationMap, Element parentElement,
                           Set<Tbom> childset, int i, Tbom parentNode, List<String> idList)
            throws Exception {
        for (Iterator it = parentElement.elementIterator(); it.hasNext(); ) {
            Element element = (Element) it.next();
            Tbom node = getTbom(element, i, tbomMap, relationMap);
            node.setParenttbom(parentNode);
            if (node.getId() != null && !node.getId().equals("")) {
                tbomService.getDao().merge(node);
            } else {
                tbomService.getDao().save(node);// 保存结点到数据库
            }
            saveTbomRole(node, node.getTbomRoles());
            idList.add(node.getId());
            saveDynamicColumn(element, node);
            Set<Tbom> set = new HashSet<Tbom>();
            int child = i + 1;
            childTbom(tbomMap, relationMap, element, set, child, node, idList);
            node.setChildTboms(set);
            childset.add(node);
        }
    }

    /**
     * 保存动态子节点的信息
     *
     * @param element
     * @param node
     * @Method: saveDynamicColumn
     */
    @Transactional(propagation = Propagation.REQUIRED)
    private void saveDynamicColumn(Element element, Tbom node) {
        if (element.attributeValue("column") != null) {
            String[] str = element.attributeValue("column").split(";;;;", -2);
            List<DynamicTbom> tempDynamicTbomList = new ArrayList<DynamicTbom>();
            List<DynamicTbom> dynamicTbomList = new ArrayList<DynamicTbom>();
            for (int i = 0; i < str.length; i++) {
                if (str[i].indexOf(";;,,") > 0) {
                    DynamicTbom dt = new DynamicTbom();
                    dt.setTbom(node);

                    String[] dcstr = str[i].split(";;,,", -2);
                    String[] t_str1 = dcstr[0].split("#@#");
                    StringBuilder qbC = new StringBuilder();
                    for (int m = 0; m < t_str1.length; m++) {
                        String[] t_str2 = t_str1[m].split("@@");
                        qbC.append(t_str2[0]);
                        if (m < t_str1.length - 1)
                            qbC.append(",");
                    }
                    // 动态子节点所在字段
                    dt.setColumn(qbC.toString());
                    // 动态子节点所在字段所属的数据类
                    dt.setTable(dcstr[1].split(";,;")[0]);
                    StringBuffer sb = new StringBuffer();
                    // 动态子节点所在数据源
                    String[] sourcestr = dcstr[2].split(";,;");
                    for (int j = 0; j < sourcestr.length; j++) {
                        if (sourcestr[j] != null
                                && sourcestr[j].indexOf(",,,") > 0) {
                            sb.append(sourcestr[j].split(",,,", -2)[0]).append(
                                    ",");
                        }
                    }
                    dt.setSource(sb.substring(0, sb.length() - 1));

                    dt.setOrder(dcstr[3]);// 动态子节点的顺序
                    dt.setUrl(dcstr[4]);
                    dt.setDisplay(dcstr[5]);//
                    // 动态子节点的初始数据源视图

                    String expression = null;
                    String originexpression = null;
                    if (dcstr[6] != null && dcstr[6].length() > 0) {
                        String[] expressionstr = dcstr[6].split("#@#");
                        originexpression = dcstr[6];
                        Boolean error = false;
                        StringBuffer exps = new StringBuffer();
                        for (int b = 0; b < expressionstr.length; b++) {
                            if (expressionstr[b].indexOf("@@") > 0) {
                                StringBuffer exp = new StringBuffer();
                                String[] expstr = expressionstr[b].split("@@", -2);
                                String columnid = expstr[0].split(";,;")[0];// 字段所在的id
                                String datatype = expstr[0].split(";,;")[1];
                                String columnname = null;
                                String source = dt.getSource().split(",")[0];
                                source = source.indexOf("-mtl") != -1 ? source.substring(0, source.indexOf("-mtl")) : source;

                                if("$当前部门".equals(columnid) || "$当前用户".equals(columnid) || "$当前角色".equals(columnid)) {
                                    columnname = "'"+columnid+"'";
                                }
                                else {
                                    if (source.charAt(0) == 'v') {//试图
                                        List<Map<String, Object>> list = metadaofactory.getJdbcTemplate().queryForList("SELECT CTC.ID,CTC.S_COLUMN_NAME "
                                                + "FROM CWM_VIEW_RETURN_COLUMN CVRC,CWM_TAB_COLUMNS CTC WHERE VIEW_ID='"
                                                + source.substring(1, source.length()) + "' AND RETURN_COLUMN_ID=CTC.ID AND CTC.CATEGORY=1  "
                                                + "AND CTC.IS_VALID=1 ORDER BY CVRC.ORDER_SIGN ASC");
                                        for (Map map : list) {
                                            if (map.get("ID").equals(columnid)) {
                                                columnname = (String) map.get("S_COLUMN_NAME");
                                            }
                                        }
                                    } else {
                                        String sql = "SELECT CTC.ID,CTC.S_COLUMN_NAME FROM CWM_TAB_COLUMNS CTC, "
                                                + " CWM_TABLE_COLUMN TC WHERE TC.TABLE_ID='" + source.substring(1, source.length())
                                                + "' AND TC.TYPE=3 AND TC.COLUMN_ID=CTC.ID AND CTC.CATEGORY=1 AND "
                                                + " CTC.IS_VALID=1 ORDER BY TC.ORDER_SIGN ASC";
                                        List<Map<String, Object>> list = metadaofactory.getJdbcTemplate().queryForList(sql);
                                        for (Map map : list) {
                                            if (map.get("ID").equals(columnid)) {
                                                columnname = (String) map.get("S_COLUMN_NAME");
                                            }
                                        }
                                    }
                                }
                                if (columnname == null) {
                                    error = true;
                                    break;
                                }

                                exp.append(" ").append(columnname).append(" ").append(expstr[1]).append(" ");
                                if (expstr[2] != null && expstr[2] != "") {
                                    String ziduanzhi = null;
                                    if (datatype.equals("PlaceHolder")) {
                                        if (expstr[2].trim().length() != 0) {
                                            String[] vals = expstr[2].split(",");
                                            ziduanzhi = "(";
                                            for(String val : vals) {
                                                ziduanzhi = ziduanzhi + "'" + val + "',";
                                            }
                                            ziduanzhi = ziduanzhi.substring(0, ziduanzhi.length()-1) + ")";
                                        }
                                    } else if (datatype.equals("Date")) {
                                        if (expstr[2].trim().length() != 0) {
                                            ziduanzhi = "to_date('" + expstr[2] + "','yyyy-mm-dd') ";
                                        }
                                    } else if (datatype.equals("DateTime")) {
                                        if (expstr[2].trim().length() != 0) {
                                            ziduanzhi = "to_date('" + expstr[2] + "','yyyy-mm-dd hh24:mi:ss') ";
                                        }
                                    } else {
                                        ziduanzhi = expstr[2].trim().length() == 0 ? "" : ("'" + expstr[2] + "' ");
                                    }
                                    exp.append(ziduanzhi);
                                }
                                if(exp.indexOf("$当前") >= 0) {
                                    exps.append(" ##").append(exp).append("## ");
                                }
                                else {
                                    exps.append(" ").append(exp).append(" ");
                                }
                                if (expstr[3] != null && expstr[3] != "") {
                                    exps.append(expstr[3]).append(" ");
                                }
                            }
                        }

                        if (error) {
                            expression = "";
                            originexpression = "";
                        } else {
                            expression = exps.toString();
                        }
                    }
                    dt.setExp(expression);
                    dt.setOrigin_exp(originexpression);
                    if (dcstr[7] == null) {
                        dt.setShowType("0");
                    } else {
                        dt.setShowType(dcstr[7]);
                    }

                    if (dcstr.length >= 9 && dcstr[8] != null && dcstr[8].length() > 0) {
                        String[] competencestr = dcstr[8].split("#@#");
                        List<String> roleList = new ArrayList<>();
                        for (int b = 0; b < competencestr.length; b++) {
                            if (competencestr[b].indexOf(",,,") > 0) {
                                String[] expstr = competencestr[b].split(",,,", -2);
                                roleList.add(expstr[0]);

                            }
                        }
                        DynamicTbomRole dynamicTbomRole = new DynamicTbomRole(null,CommonTools.list2String(roleList), "");
                        dt.getDynamicTbomRoles().add(dynamicTbomRole);
                    }

                    if (dcstr.length >= 10 && dcstr[9] != null && dcstr[9].length() > 0) {
                        if (dcstr[9].indexOf(";,;") > 0) {
                            dt.setView(dcstr[9].split(";,;")[0]);
                        }
                    }
                    tempDynamicTbomList.add(dt);
                }
            }
            for (int j = tempDynamicTbomList.size() - 1; j >= 0; j--) {
                if (tempDynamicTbomList.get(j).getOrder().equalsIgnoreCase("1")) {
                    dynamicTbomList.add(0, tempDynamicTbomList.get(j));
                } else {
                    for (int k = j - 1; k >= 0; k--) {
                        if (Integer.valueOf(tempDynamicTbomList.get(j).getOrder()) == Integer.valueOf(tempDynamicTbomList.get(k).getOrder()) + 1) {
                            tempDynamicTbomList.get(k).getChildrenList().add(0, ((DynamicTbom) tempDynamicTbomList.get(j)));
                            //add zhy 2012-2-18
                            break;
                            //end
                        }
                    }
                }
            }
            //保存到数据库中
            List<DynamicTbom> dtlist = new ArrayList();
            SortedSet<DynamicTbom> dtset = new TreeSet<DynamicTbom>(new DTbomCompare());
            for (int j = 0; j < dynamicTbomList.size(); j++) {
                DynamicTbom dt = dynamicTbomList.get(j);
                tbomService.getDynamicTbomDAO().save(dt);

                if (dt.getDynamicTbomRoles() != null && dt.getDynamicTbomRoles().size() > 0) {
                    // 保存动态子节点权限
                    saveDynamicTbomRole(dt, dt.getDynamicTbomRoles());
                }

                if (dt.getChildrenList() != null && dt.getChildrenList().size() > 0) {
                    saveChildDynamicColumn(dt.getChildrenList(), dt, dtlist);
                }

                dtlist.add(dt);
            }
            for (DynamicTbom dt : dtlist) {

                dtset.add(dt);
            }
            node.setDynamicTbom(dtset);
        }
    }


    private void saveChildDynamicColumn(List<DynamicTbom> childrenList, DynamicTbom parent, List<DynamicTbom> dtlist) {
        for (Iterator it = childrenList.iterator(); it.hasNext(); ) {
            DynamicTbom dt = (DynamicTbom) it.next();
            dt.setPid(parent.getId());
            tbomService.getDynamicTbomDAO().save(dt);
            dtlist.add(dt);
            if (dt.getChildrenList() != null && dt.getChildrenList().size() > 0) {
                saveChildDynamicColumn(dt.getChildrenList(), dt, dtlist);
            }

            if (dt.getDynamicTbomRoles() != null && dt.getDynamicTbomRoles().size() > 0) {
                // 保存动态子节点权限
                saveDynamicTbomRole(dt, dt.getDynamicTbomRoles());
            }
        }
    }


    /**
     * 保存动态节点权限
     */
    private void saveDynamicTbomRole(DynamicTbom dynamicTbom, Set<DynamicTbomRole> dynamicTbomRoles) {
        List<DynamicTbomRole> exists = tbomService.getDynamicTbomRoleDAO().findByProperty("nodeId", dynamicTbom.getId());
        exists.forEach(exist -> tbomService.getDynamicTbomRoleDAO().delete(exist));
        for (DynamicTbomRole dynamicTbomRole : dynamicTbomRoles) {
            dynamicTbomRole.setNodeId(dynamicTbom.getId());
            tbomService.getDynamicTbomRoleDAO().save(dynamicTbomRole);
        }

    }

    /**
     * 保存静态节点权限
     */
    private void saveTbomRole(Tbom tbom, Set<TbomRole> tbomRoles) {
        List<TbomRole> exists = tbomService.getTbomRoleDAO().findByProperty("nodeId", tbom.getId());
        exists.forEach(exist -> tbomService.getTbomRoleDAO().delete(exist));
        for (TbomRole tbomRole : tbomRoles) {
            tbomRole.setNodeId(tbom.getId());
            tbomService.getTbomRoleDAO().save(tbomRole);
        }
    }
}
