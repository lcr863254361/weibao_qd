package com.orient.businessmodel.service.impl;

import com.orient.businessmodel.bean.IBusinessModel;

import java.io.Serializable;

/**
 * @ClassName BusinessModelEdge
 * 业务模型的连接边
 * @author zhulc@cssrc.com.cn
 * @date Apr 28, 2012
 */

public class BusinessModelEdge implements Serializable{
    /** 
    * @Fields start : 起点业务模型
    */
    private IBusinessModel start;
    /** 
    * @Fields end : 终点业务模型
    */
    private IBusinessModel end;
    /** 
    * @Fields edgeType : 1表示 起点模型依赖终点模型, 0表示起点模型不依赖终点模型
    *                              2表示自身与自身的关联
    */
    private int edgeType;
    /** 
    * @Fields manyToMany : 顶点和终点之间的数据是不是多对多关联
    */
    private boolean manyToMany;
    
	public BusinessModelEdge(IBusinessModel start, IBusinessModel end,
                             int edgeType) {
		this.start = start;
		this.end = end;
		this.edgeType = edgeType;
		manyToMany = false;
	}
	public IBusinessModel getStart() {
		return start;
	}
	public void setStart(IBusinessModel start) {
		this.start = start;
	}
	public IBusinessModel getEnd() {
		return end;
	}
	public void setEnd(IBusinessModel end) {
		this.end = end;
	}
	public int getEdgeType() {
		return edgeType;
	}
	public void setEdgeType(int edgeType) {
		this.edgeType = edgeType;
	}
	
	public boolean isManyToMany() {
		return manyToMany;
	}

	public void setManyToMany(boolean manyToMany) {
		this.manyToMany = manyToMany;
	}
	
   
    
}
