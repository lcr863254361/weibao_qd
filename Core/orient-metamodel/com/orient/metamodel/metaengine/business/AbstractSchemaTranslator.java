package com.orient.metamodel.metaengine.business;

import java.util.Properties;

/**
 * abstract implementation of {@link SchemaTranslator}
 *
 * @author Seraph
 *         2016-10-11 下午4:25
 */
public abstract class AbstractSchemaTranslator implements SchemaTranslator {

    //The hibernate properties
    protected Properties hibernateProperties;
    protected MetaDAOFactory metadaofactory = null;

    public MetaDAOFactory getMetadaofactory() {
        return metadaofactory;
    }

    public void setMetadaofactory(MetaDAOFactory metadaofactory) {
        this.metadaofactory = metadaofactory;
    }

    public Properties getHibernateProperties() {
        return hibernateProperties;
    }

    public void setHibernateProperties(Properties hibernateProperties) {
        this.hibernateProperties = hibernateProperties;
    }

}
