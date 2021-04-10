package com.aptx.utils.orm;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

public abstract class CriteriaCallback {
    protected Criteria criteria;

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public abstract void buildCriteria();

    /******************* Commons *******************/
    public Order orderAsc(String prop) {
        return Order.asc(prop);
    }

    public Order orderDesc(String prop) {
        return Order.desc(prop);
    }
}
