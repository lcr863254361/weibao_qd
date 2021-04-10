package com.orient.dsrestful.eventparam;

import com.orient.metamodel.metadomain.Schema;
import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-29 10:15
 */
public class DeleteSchemaParam extends OrientEventParams {

    private Schema schema;

    public DeleteSchemaParam(Schema schema) {
        this.schema = schema;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

}
