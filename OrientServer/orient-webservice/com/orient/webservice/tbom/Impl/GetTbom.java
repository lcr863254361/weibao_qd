package com.orient.webservice.tbom.Impl;

import com.orient.metamodel.metadomain.Table;
import com.orient.metamodel.metadomain.View;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.metamodel.operationinterface.IView;
import com.orient.sysmodel.domain.role.Role;
import com.orient.sysmodel.domain.tbom.*;
import com.orient.sysmodel.operationinterface.ITbom;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.dao.DataAccessException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author zhulc@cssrc.com.cn
 * @ClassName GetTbom
 * 获取Tbom的xml字符串
 * @date 2012-5-16
 */

public class GetTbom extends TbomBean {
    private ISchema curSchema;

    @SuppressWarnings("rawtypes")
    public String execute(String schemaIdTbomID) {
        Document document = DocumentHelper.createDocument();
        try {
            String[] array = schemaIdTbomID.split("=,=,=");
            String schemaid = array[0];
            String id = array[1];
            TbomDir td = roleEngine.getRoleModel(false).getTbomDirs().get(id);
            Map<String, Tbom> tbomMap = new HashMap<String, Tbom>();
            List<ITbom> tbomRoots = roleEngine.getRoleModel(false).getTbomRoots();
            Predicate<ITbom> filterFunc = tbom -> tbom.getName().equals(td.getName()) && tbom.getSchema().getId().equals(schemaid);
            Boolean exists = tbomRoots.stream().filter(filterFunc).count() != 0;
            Tbom tbom = null;
            if (exists) {
                tbom = (Tbom) tbomRoots.stream().filter(filterFunc).findFirst().get();
            } else {
                return "error";
            }
            curSchema = metaEngine.getMeta(false).getISchemaById(schemaid);
            tbomMap.put(tbom.getXmlid(), tbom);
            Map<String, Tbom> idMap = new HashMap<String, Tbom>();
            childTbom(tbomMap, idMap, tbom);
            Element root = document.addElement("tree");
            Element treeRootElement = root.addElement("item");
            elementAttribute(tbom, treeRootElement);
            if (td.getType() == null) {
                td.setType(new Long(0));
            }
            treeRootElement.addAttribute("type", Long.toString(td.getType()));
            treeRootElement.addAttribute("expandLevel", isNull(tbom.getExpandLevel()));
            int i = 0;
            String a = "000" + "00" + i;
            while (tbomMap.keySet().contains(a)) {
                Tbom childtbom = (Tbom) tbomMap.get(a);
                Element element = treeRootElement.addElement("item");
                elementAttribute(childtbom, element);
                childElement(tbomMap, a, element);
                i++;
                if (i < 10) {
                    a = "000" + "00" + i;
                } else if (i < 100) {
                    a = "000" + "0" + i;
                } else {
                    a = "000" + Integer.toString(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document.asXML();
    }

    /**
     * 构建TBOM树的DOM4J文件内容.
     *
     * @param tbomMap 子节点TBOM树对象
     * @param a       XMLID组成部分
     * @param element XML元素
     * @throws Exception 异常信息
     */
    private void childElement(Map<String, Tbom> tbomMap, String a, Element element) throws Exception {
        int j = 0;
        String b = a + "00" + j;
        while (tbomMap.keySet().contains(b)) {
            Tbom ctbom = (Tbom) tbomMap.get(b);
            Element childelement = element.addElement("item");
            elementAttribute(ctbom, childelement);
            childElement(tbomMap, b, childelement);
            j++;
            if (j < 10) {
                b = a + "00" + j;
            } else if (j < 100) {
                b = a + "0" + j;
            } else {
                b = a + j;
            }
        }
    }

    /**
     * 读取子节点信息.
     *
     * @param tbomMap Tbom信息map，key为结点的XMLID，value为Tbom对象
     * @param idMap   Tbom信息map，key为结点的id，value为Tbom对象
     * @param tbom    Tbom对象
     * @throws Exception 异常信息
     */
    private void childTbom(Map<String, Tbom> tbomMap, Map<String, Tbom> idMap, Tbom tbom) throws Exception {
        try {
            String id = tbom.getId();
            List<ITbom> tbomlist = tbom.getChildTboms().stream().filter(iTbom -> (null != iTbom.getIsValid() && 1 == iTbom.getIsValid().intValue())).sorted((t1, t2) -> t1.getOrder().intValue() - t2.getOrder().intValue()).collect(Collectors.toList());
            for (ITbom loop : tbomlist) {
                Tbom childtbom = (Tbom) loop;
                tbomMap.put(childtbom.getXmlid(), childtbom);
                idMap.put(childtbom.getId(), childtbom);
                childTbom(tbomMap, idMap, childtbom);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * 把Tbom的节点信息封装成Dom4j的Element
     *
     * @param childtbom
     * @param element
     * @throws Exception
     * @Method: elementAttribute
     */
    @SuppressWarnings("unchecked")
    private void elementAttribute(Tbom childtbom, Element element) throws Exception {
        element.addAttribute("text", childtbom.getName());
        element.addAttribute("id", childtbom.getId());
        element.addAttribute("xml_id", childtbom.getXmlid());
        element.addAttribute("description", isNull(childtbom.getDescription()));
        element.addAttribute("det_text", isNull(childtbom.getDetailText()));
        element.addAttribute("big_img", isNull(childtbom.getBigImg()));
        element.addAttribute("nor_img", isNull(childtbom.getNorImg()));
        element.addAttribute("sma_img", isNull(childtbom.getSmaImg()));
        element.addAttribute("url", isNull(childtbom.getUrl()));
        /**
         * 获取角色权限
         * 只有非Root节点有角色权限分配
         */
        if (!childtbom.getXmlid().equals("000")) {
            element.addAttribute("showType", isNull(childtbom.getShowType()));
            //Set tbomRoleSet = childtbom.getTbomRoles();
            TbomRole tbomRole = getTbomRoleById(childtbom.getId());
            StringBuffer sbRoleIds = new StringBuffer();
            /**
             * RoleId
             */
            String strRoleIdList = tbomRole.getRoleId();
            if (strRoleIdList == null) {
                element.addAttribute("role", "");
            } else {
                String[] roleIds = strRoleIdList.split(",");
                for (String roleId : roleIds) {

                    Role curRole = roleEngine.getRoleModel(false).getRoleById(roleId);
                    sbRoleIds.append(roleId)
                            .append(",..;")
                            .append(curRole.getName())
                            .append("..;;");
                }
                element.addAttribute("role", isNull(sbRoleIds.toString()));
            }
        }
        String templateId = childtbom.getTemplateid();
        if (childtbom.getTable() != null) {
            Table table = childtbom.getTable();
            if (table != null && table.getName().length() != 0) {
                if (table.getIsValid().equals(new Long(1))) {
                    StringBuffer source = new StringBuffer();
                    source.append(table.getId()).append(StringUtil.isEmpty(templateId) ? "" : "-mtl" + templateId).append(",.;,")
                            .append(table.getDisplayName())
                            .append(",.;,schema")
                            .append(table.getSchema().getId());
                    element.addAttribute("tbomtable", source.toString());
                }
            }
        } else if (childtbom.getView() != null) {
            View view = childtbom.getView();
            if (view != null && view.getName().length() != 0) {
                if (view.getIsValid().equals(new Long(1))) {
                    StringBuffer source = new StringBuffer();
                    source.append(view.getId()).append(",.;,")
                            .append(view.getDisplayName()).append(",.;,schema")
                            .append(view.getSchema().getId());
                    element.addAttribute("tbomview", source.toString());
                }
            }
        }
        final List<String> rtlist = new ArrayList<String>();
        Set<RelationTbom> relationTboms = childtbom
                .getCwmRelationTbomsForNodeId();
        for (RelationTbom rtbom : relationTboms) {
            rtlist.add(rtbom.getCwmTbomByRelationId().getXmlid());
        }

        if (rtlist.size() > 0) {
            StringBuffer relation = new StringBuffer();
            for (String rt : rtlist) {
                relation.append(rt).append(",");
            }
            String rel = relation.toString().substring(0,
                    relation.toString().length() - 1);
            element.addAttribute("relations", rel.toString());
        }

		/* dynamiclist为该节点的动态子节点的集合 */
        Set<DynamicTbom> dynamiclist = childtbom.getDynamicTbom();
        if (!dynamiclist.isEmpty()) {
            StringBuffer str = new StringBuffer();
            /* 遍历所有动态子节点 */
            for (DynamicTbom db : dynamiclist) {
                String cid = isNull(db.getColumn());/* 得到动态子节点的字段ID */
                String tid = isNull(db.getTable());/* 得到动态子节点的字段所在数据类的ID */
                String vid = isNull(db.getView());/* 得到动态子节点的字段所在视图的ID */
                String display = isNull(db.getDisplay());/* 得到动态子节点展现形式 */
                String order = isNull(db.getOrder());/* 得到动态子节点的级数 */
                String source = isNull(db.getSource());/* 得到动态子节点的数据源集合 */
                String url = isNull(db.getUrl());// 得到动态子节点的链接
                String showType = isNull(db.getShowType());
                //Set dynamicTbomRoleSet = db.getDynamicTbomRoles();
                List<DynamicTbomRole> dynamicTbomRoleList = getDynamicTbomRoleById(db.getId());
                ITable table = (ITable) curSchema.getTableById(tid);
                String origin_exp = isNull(db.getOrigin_exp());
                // 如果字段所在数据类或视图都不存在了，则该节点失效
                IView view = null;
                if (table == null) {
                    view = (IView) curSchema.getViewById(vid);
                    if (view == null) {
                        break;
                    }
                }
                String[] idArr = cid.split(",");
                int m = 0;
                for (String colid : idArr) {
                    if (!"".equals(isNull(colid))) {
                        IColumn col = null;
                        if (view != null) {
                            col = view.getColumnById(colid);
                        } else {
                            col = table.getColumnById(colid);

                        }
                        if (col!=null){
                            str.append(col.getId()).append("@@");
                            str.append(col.getType());
                            str.append("@@").append(col.getDisplayName());
                        }
                        if (m++ < idArr.length) {
                            str.append("#@#");
                        }
                    }
                }
                if (m == 0) {
                    break;
                }
                str.append(";;,,");// 得到节点的字段信息
                str.append(table.getId()).append(";,;")
                        .append(table.getDisplayName())
                                // 得到节点字段所在数据类的信息
                        .append(";,;schema").append(table.getSchema().getId())
                        .append(";;,,");
                String[] sourcestr = source.split(",");
                Boolean stutus = false;// 是否结束本循环的标记
                StringBuffer ss = new StringBuffer();// 动态子节点数据源的字符串标记
                for (int j = 0; j < sourcestr.length; j++) {
                    if (sourcestr[j].substring(0, 1).equals("t")) {// 数据源是数据类
                        String sourceLoop = sourcestr[j];
                        sourceLoop = sourceLoop.indexOf("-") != -1 ? sourceLoop.substring(1, sourceLoop.indexOf("-")) : sourceLoop.substring(1);
                        Table st = (Table) curSchema.getTableById(sourceLoop);
                        // 数据源不存在
                        if (st == null || st.getName().length() <= 0
                                || st.getIsValid() == 0) {
                            if (j == 0) {// 如果第一数据源不存在，则数据源改为缺省数据源
                                if (view != null
                                        && view.getTable().getIsValid() == 1) {
                                    str.append("v")
                                            .append(view.getId())
                                            .append(",,,")
                                            .append(view.getDisplayName())
                                                    // 得到节点字段所在数据视图的信息
                                            .append(",,,schema")
                                            .append(view.getSchema().getId())
                                            .append(";;,,").append("1;;,,;;;;");
                                } else {
                                    str.append("t")
                                            .append(table.getId())
                                            .append(",,,")
                                            .append(table.getDisplayName())
                                                    // 得到节点字段所在数据类的信息
                                            .append(",,,schema")
                                            .append(table.getSchema().getId())
                                            .append(";;,,").append("1;;,,;;;;");
                                }
                                stutus = true;// 标记上级循环结束
                                break;// 本for循环结束
                            }
                        } else {
                            ss.append(sourcestr[j]).append(",,,")
                                    .append(st.getDisplayName())
                                    .append(",,,schema")
                                    .append(st.getSchema().getId()).append(";,;");
                        }
                    } else if (sourcestr[j].substring(0, 1).equals("v")) {
                        View vi = (View) curSchema.getViewById(sourcestr[j]
                                .substring(1));
                        // 数据源（数据视图）不存在
                        if (vi == null || vi.getName().length() <= 0
                                || vi.getIsValid() == 0) {
                            if (j == 0) {// 如果第一数据源不存在，则数据源改为缺省数据源
                                if (view != null
                                        && view.getTable().getIsValid() == 1) {
                                    str.append("v")
                                            .append(view.getId())
                                            .append(",,,")
                                            .append(view.getDisplayName())
                                                    // 得到节点字段所在数据视图的信息
                                            .append(",,,schema")
                                            .append(view.getSchema().getId())
                                            .append(";;,,").append("1;;,,;;;;");
                                } else {
                                    str.append("t")
                                            .append(table.getId())
                                            .append(",,,")
                                            .append(table.getDisplayName())
                                                    // 得到节点字段所在数据类的信息
                                            .append(",,,schema")
                                            .append(table.getSchema().getId())
                                            .append(";;,,").append("1;;,,;;;;");
                                }
                                stutus = true;// 标记上级循环结束
                                break;// 本for循环结束
                            }
                        } else {// 数据源（数据视图）存在
                            ss.append(sourcestr[j]).append(",,,")
                                    .append(vi.getDisplayName())
                                    .append(",,,schema")
                                    .append(vi.getSchema().getId()).append(";,;");
                        }
                    }
                }
                if (stutus) {// 取消遍历动态节点
                    break;
                }
                str.append(ss.substring(0, ss.length() - 3)).append(";;,,");
                str.append(order).append(";;,,");// 动态子节点的顺序
                str.append(url).append(";;,,");// URL
                str.append(display).append(";;,,");
                str.append(origin_exp).append(";;,,");
                str.append(showType).append(";;,,");
                for (DynamicTbomRole dynamicTbomRole : dynamicTbomRoleList) {

                    /**
                     * DataSourceId
                     */
                    String strDataSourceIdList = CommonTools.null2String(dynamicTbomRole.getDataSource());
                    if (!StringUtil.isEmpty(strDataSourceIdList)) {
                        String[] dataSourceIds = strDataSourceIdList.split(",");
                        for (String dataSourceId : dataSourceIds) {

                            ITable curTable = curSchema.getTableById(dataSourceId.substring(1));
                            str.append(dataSourceId)
                                    .append(",,,")
                                    .append(curTable.getDisplayName())
                                            // 得到节点字段所在数据类的信息
                                    .append(",,,schema")
                                    .append(curTable.getSchema().getId())
                                    .append("#@@");
                        }
                    }

                    /**
                     * RoleId
                     */
                    String strRoleIdList = dynamicTbomRole.getRoleId();
                    String[] roleIds = strRoleIdList.split(",");
                    for (String roleId : roleIds) {

                        Role curRole = roleEngine.getRoleModel(false).getRoleById(roleId);
                        str.append(roleId)
                                .append(",,,")
                                .append(curRole.getName())
                                .append("#@#");
                    }
                }

                if (view != null && view.getName().length() > 0) {
                    str.append(view.getId()).append(";,;")
                            .append(view.getDisplayName())// 得到节点字段所在数据类的信息
                            .append(";,;schema").append(view.getSchema().getId());
                }
                str.append(";;,,");
                str.append(";;;;");
            }
            if (str == null || str.length() == 0) {
                element.addAttribute("column", "");
            } else {
                element.addAttribute("column",
                        str.toString().substring(0, str.length() - 4));
            }
        }
        if (childtbom.getOriginExpression() != null
                && childtbom.getOriginExpression().length() != 0) {
            element.addAttribute("expression",
                    isNull(childtbom.getOriginExpression()));
        }

    }

    private List<DynamicTbomRole> getDynamicTbomRoleById(String id) {
        List<DynamicTbomRole> dynamicTbomRoleList = new ArrayList<DynamicTbomRole>();
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ROLE_ID,DATA_SOURCE FROM CWM_DYNAMIC_TBOM_ROLE WHERE NODE_ID = '").append(id).append("'");

        List<Map<String, Object>> mapList = metadaofactory.getJdbcTemplate().queryForList(sql.toString());

        for (Map<String, Object> map : mapList) {
            DynamicTbomRole dynamicTbomRole = new DynamicTbomRole();
            String roleIds = (String) map.get("ROLE_ID");
            String datsSources = (String) map.get("DATA_SOURCE");
            dynamicTbomRole.setNodeId(id);
            dynamicTbomRole.setRoleId(roleIds);
            dynamicTbomRole.setDataSource(datsSources);

            dynamicTbomRoleList.add(dynamicTbomRole);
        }

        return dynamicTbomRoleList;
    }

    private TbomRole getTbomRoleById(String id) {
        TbomRole tbomRole = new TbomRole();
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ROLE_ID,DATA_SOURCE FROM CWM_TBOM_ROLE WHERE NODE_ID = '").append(id).append("'");

        List<Map<String, Object>> mapList = metadaofactory.getJdbcTemplate().queryForList(sql.toString());

        for (Map<String, Object> map : mapList) {
            String roleIds = (String) map.get("ROLE_ID");
            String datsSources = (String) map.get("DATA_SOURCE");
            tbomRole.setNodeId(id);
            tbomRole.setRoleId(roleIds);
            tbomRole.setDataSource(datsSources);

        }

        return tbomRole;
    }
}
