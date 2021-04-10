package com.orient.dsrestful.listener.deleteSchema;

import com.orient.dsrestful.enums.SchemaDeleteResponseEnum;
import com.orient.dsrestful.event.DeleteSchemaEvent;
import com.orient.dsrestful.eventparam.DeleteSchemaParam;
import com.orient.metamodel.metadomain.RelationTableEnum;
import com.orient.metamodel.metadomain.Restriction;
import com.orient.metamodel.metadomain.TableEnum;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.springmvcsupport.exception.DSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * 删除约束
 *
 * @author GNY
 * @create 2018-03-29 14:24
 */
@Component
public class DeleteRestrictionListener extends OrientEventListener {

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
            for (Restriction restriction : param.getSchema().getRestrictions()) {
                deleteRestrictions(restriction);
                metaDaoFactory.getRestrictionDAO().delete(restriction);
            }
        } catch (Exception e) {
            throw new DSException(SchemaDeleteResponseEnum.TYPE_EXCEPTION.getType(), e.getMessage());
        }
    }

    private void deleteRestrictions(Restriction restriction) {
        try {
            switch (restriction.getType().intValue()) {
                case Restriction.TYPE_ENUM:    //枚举约束
                    for (com.orient.metamodel.metadomain.Enum e : restriction.getCwmEnums()) {
                        e.setRestrictionID(restriction.getId());
                        metaDaoFactory.getEnumDAO().delete(e);
                    }
                    break;
                case Restriction.TYPE_TABLEENUM: //数据表枚举约束
                    TableEnum te = restriction.getTableEnum();
                    te.setRestrictionId(restriction.getId());
                    //saveTableEnumDetail(te);
                    break;
                case Restriction.TYPE_DYNAMICRANGEENUM://动态范围约束
                    TableEnum tableEnum = restriction.getTableEnum();
                    tableEnum.setRestrictionId(restriction.getId());
                    metaDaoFactory.getTableEnumDAO().delete(tableEnum);
                    for (RelationTableEnum rte : tableEnum.getRelationTableEnums()) {
                        metaDaoFactory.getRelationTableEnumDAO().delete(rte);
                    }
                    break;
                default:
                    break;

            }
        } catch (Exception e) {
            throw new DSException(SchemaDeleteResponseEnum.TYPE_EXCEPTION.getType(), e.getMessage());
        }
    }

}
