package com.orient.metamodel.service;

import com.orient.metamodel.DBUtil;
import com.orient.metamodel.metadomain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-04-02 21:56
 */
@Service
public class UpdateViewService extends DBUtil {

    @Autowired
    SaveViewService saveViewService;

    /**
     * 更新视图
     *
     * @param newSchema
     * @param oldSchema
     */
    public void updateView(Schema newSchema, Schema oldSchema, Map<Integer, String> createViewSqlMap, Map<String, Column> columnMap) throws Exception {
        //更新数据视图
        Map<String, View> viewMap = new HashMap<>();
        Map<View, Integer> intervalViewMap = new HashMap<>();
        Set<Column> arithColumnFromViewSet = new HashSet<>();
        for (View view : newSchema.getViews()) {
            View oldView = (View) oldSchema.getViewByName(view.getName());
            viewMap.put(view.getIdentity(), view);
            if (oldView != null) {
                view.setId(oldView.getId());
                deleteViewDetail(oldView);
                view.setViewName(view.getName() + "_" + newSchema.getId());
                //设置View的Expression
                if (view.getExpression() != null && !view.getExpression().isEmpty()) {
                    String expression = saveViewService.convertColumnIdentity2ColumnName(view.getExpression(), columnMap);
                    view.setExpression(expression);
                }
                if (view.getType().equals(View.TPYE_ARITH) && !intervalViewMap.containsKey(view)) {
                    saveViewService.saveIntervalView(view, intervalViewMap, viewMap);
                }
                saveViewService.saveArithColumnForView(view, arithColumnFromViewSet, columnMap);  //保存统计视图的统计属性
                saveViewService.saveCanshuxiang(view);                                           //保存视图的统计参数项
                saveViewService.saveViewSql(view, newSchema, intervalViewMap, createViewSqlMap); //保存生成视图的SQL语句

                for (Column arithColumn : arithColumnFromViewSet) { //保存视图统计属性所使用的参数
                    String id = arithColumn.getId();
                    if (id.equalsIgnoreCase("")) {
                        assert (false);
                    }
                    saveViewService.saveArithColumnAttributeForView(arithColumn);      //确保所有的Column已经有Id了
                }
                metaDAOFactory.getHibernateTemplate().flush();
                saveViewService.saveViewAttribute(view, viewMap);                     //视图返回属性、关联数据类以及排序属性信息的保存.
                if (view.getType().equals(View.TPYE_ARITH)) {                         //如果是统计属性
                    List<String> refList = new ArrayList<>();                         //引用了该视图和该视图引用的视图
                    refList.add(view.getId());    //自生也是引用的view
                    saveViewService.addRefView(view, newSchema, refList);
                    //addPreView(view,schema,refList);
                    StringBuffer sb = new StringBuffer();
                    if (!refList.isEmpty()) {
                        for (String str : refList) {
                            sb.append(str).append(",");
                        }
                        view.setRefviewid(sb.substring(0, sb.length() - 1));
                    }
                }
                metaDAOFactory.getViewDAO().merge(view);// 保存视图
            } else {
                view.setViewName(view.getName() + "_" + newSchema.getId());
                //设置View 的Expression
                if (view.getExpression() != null && !view.getExpression().isEmpty()) {
                    String expression = saveViewService.convertColumnIdentity2ColumnName(view.getExpression(), columnMap);
                    view.setExpression(expression);
                }
                metaDAOFactory.getViewDAO().save(view);// 保存视图
                if (view.getType().equals(View.TPYE_ARITH) && !intervalViewMap.containsKey(view)) {
                    saveViewService.saveIntervalView(view, intervalViewMap, viewMap);
                }
                saveViewService.saveArithColumnForView(view, arithColumnFromViewSet, columnMap);   //保存统计视图的统计属性
                saveViewService.saveCanshuxiang(view);                                             //保存视图的统计参数项
                saveViewService.saveViewSql(view, newSchema, intervalViewMap, createViewSqlMap);   //保存生成视图的SQL语句
                for (Column arithColumn : arithColumnFromViewSet) {                                //保存视图统计属性所使用的参数
                    String id = arithColumn.getId();
                    if (id.equalsIgnoreCase("")) {
                        assert (false);
                    }
                    saveViewService.saveArithColumnAttributeForView(arithColumn);      //确保所有的Column已经有Id了
                }
                metaDAOFactory.getHibernateTemplate().flush();
                saveViewService.saveViewAttribute(view, viewMap);                      //视图返回属性、关联数据类以及排序属性信息的保存.
                if (view.getType().equals(View.TPYE_ARITH)) {                         //如果是统计属性
                    List<String> refList = new ArrayList<>();                         //引用了该视图和该视图引用的视图
                    refList.add(view.getId());    //自生也是引用的view
                    saveViewService.addRefView(view, newSchema, refList);
                    StringBuffer sb = new StringBuffer();
                    if (!refList.isEmpty()) {
                        for (String str : refList) {
                            sb.append(str).append(",");
                        }
                        view.setRefviewid(sb.substring(0, sb.length() - 1));
                    }
                }
                metaDAOFactory.getViewDAO().merge(view);// 保存视图
            }
        }
        //设置统计视图被引用到的次数
        metaDAOFactory.getHibernateTemplate().flush();
    }

    /**
     * 删除数据库中的与view相关的关联数据类，返回属性，排序属性，参数项等等
     *
     * @param oldView
     * @throws Exception
     */
    private void deleteViewDetail(View oldView) throws Exception {
        // 删除数据库中view的关联数据类
        Set<ViewRefColumn> relationTableViewSet = oldView.getViewRelationTables();
        if (!relationTableViewSet.isEmpty()) {
            for (ViewRefColumn viewRelTable : relationTableViewSet) {
                metaDAOFactory.getViewRefColumnDAO().delete(viewRelTable);
            }
        }
        // 将元数据库中和数据库中view相关的返回属性、排序属性删除，
        Set<ViewResultColumn> resultColumnSet = oldView.getReturnColumns();
        if (resultColumnSet.size() != 0) {
            for (ViewResultColumn vrc : resultColumnSet) {
                metaDAOFactory.getViewResultColumnDAO().delete(vrc);
            }
        }
        Set<ViewSortColumn> sortColumnSet = oldView.getPaixuColumns();
        if (sortColumnSet.size() != 0) {
            for (ViewSortColumn vsc : sortColumnSet) {
                metaDAOFactory.getViewSortColumnDAO().delete(vsc);
            }
        }
        //将元数据库中和数据库中view相关的统计属性和统计参数项
        Set<Column> columnSet = oldView.getColumns();
        if (columnSet.size() != 0) {
            for (Column column : columnSet) {
                Set<ArithAttribute> aaSet = column.getArithAttribute();
                for (ArithAttribute dbaa : aaSet) {
                    metaDAOFactory.getArithAttributeDAO().delete(dbaa);
                }
                metaDAOFactory.getColumnDAO().delete(column);
            }
        }

        Set<ArithViewAttribute> canshuxiangSet = oldView.getCanshuxiang();
        if (!canshuxiangSet.isEmpty()) {
            for (ArithViewAttribute ava : canshuxiangSet) {
                metaDAOFactory.getArithViewAttributeDAO().delete(ava);
            }
        }
    }

}
