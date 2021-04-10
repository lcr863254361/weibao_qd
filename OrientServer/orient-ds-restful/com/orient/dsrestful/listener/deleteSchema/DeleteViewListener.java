package com.orient.dsrestful.listener.deleteSchema;

import com.orient.dsrestful.enums.SchemaDeleteResponseEnum;
import com.orient.dsrestful.event.DeleteSchemaEvent;
import com.orient.dsrestful.eventparam.DeleteSchemaParam;
import com.orient.metamodel.metadomain.*;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.springmvcsupport.exception.DSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 删除视图
 *
 * @author GNY
 * @create 2018-03-29 14:32
 */
@Component
public class DeleteViewListener extends OrientEventListener {

    @Autowired
    MetaDAOFactory metaDaoFactory;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == DeleteSchemaEvent.class || DeleteSchemaEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        DeleteSchemaParam param = (DeleteSchemaParam) orientEvent.getParams();
        try {
            for (View view : param.getSchema().getViews()) {
                //删除其他三张视图相关表中的记录
                deleteViewAttribute(view);
                //删除CWM_VIEWS表中的数据记录
                metaDaoFactory.getViewDAO().delete(view);
                //删除物理视图,这个功能暂时没用到，代码注销掉否则报错
                //metaDaoFactory.getJdbcTemplate().execute("DROP VIEW " + view.getViewName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DSException(SchemaDeleteResponseEnum.TYPE_EXCEPTION.getType(), e.getMessage());
        }
    }

    private void deleteViewAttribute(View view) {
        try {
            if (!view.getColumns().isEmpty()) {
                for (Column column : view.getColumns()) {
                    column.setView(view);
                    String cName = column.getName();
                    //这里只需要保存统计视图的属性
                    if (column.getCategory().equals(Column.CATEGORY_ARITH)) {
                        column.setName(cName);
                        if (column.getPurpose() == null || column.getPurpose().length() == 0) {
                            column.setPurpose("新增,修改,详细,列表");
                        }
                        column.setColumnName(column.getName() + "_" + view.getId());
                        metaDaoFactory.getColumnDAO().delete(column);//删除视图的统计属性到CWM_TAB_COLUMNS
                    }
                    if (!column.getArithAttribute().isEmpty()) {
                        for (ArithAttribute aa : column.getArithAttribute()) {
                            assert (!aa.getColumn().getId().equals(""));
                            aa.setAcolumn(column);
                            metaDaoFactory.getArithAttributeDAO().delete(aa);
                        }
                    }
                }
            }
            if (!view.getCanshuxiang().isEmpty()) {
                for (ArithViewAttribute ava : view.getCanshuxiang()) {
                    ava.setView(view);
                    metaDaoFactory.getArithViewAttributeDAO().delete(ava);
                }
            }
            Set<ViewRefColumn> relationTableViewSet = view.getViewRelationTables();
            if (!relationTableViewSet.isEmpty()) {
                for (ViewRefColumn viewRelTable : relationTableViewSet) {
                    metaDaoFactory.getViewRefColumnDAO().delete(viewRelTable);
                }
            }
            //将元数据库中和数据库中view相关的返回属性、排序属性删除
            Set<ViewResultColumn> resultColumnSet = view.getReturnColumns();
            if (resultColumnSet.size() != 0) {
                for (ViewResultColumn vrc : resultColumnSet) {
                    metaDaoFactory.getViewResultColumnDAO().delete(vrc);
                }
            }
            Set<ViewSortColumn> sortColumnSet = view.getPaixuColumns();
            if (sortColumnSet.size() != 0) {
                for (ViewSortColumn vsc : sortColumnSet) {
                    metaDaoFactory.getViewSortColumnDAO().delete(vsc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DSException(SchemaDeleteResponseEnum.TYPE_EXCEPTION.getType(), e.getMessage());
        }
    }

}
