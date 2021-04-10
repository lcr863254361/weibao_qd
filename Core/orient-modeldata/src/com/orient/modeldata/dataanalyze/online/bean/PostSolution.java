package com.orient.modeldata.dataanalyze.online.bean;

import java.util.List;
import java.util.Map;

public class PostSolution {
	
	private String columnCount;
	private String name;
	private String rowCount;
	private List<Map<String,Object>> columnData;

	public PostSolution() {

	}

	public PostSolution(String columnCount, String name, String rowCount,
			List<Map<String, Object>> columnData) {
		super();
		this.columnCount = columnCount;
		this.name = name;
		this.rowCount = rowCount;
		this.columnData = columnData;
	}

	public String getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(String columnCount) {
		this.columnCount = columnCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRowCount() {
		return rowCount;
	}

	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	public List<Map<String, Object>> getColumnData() {
		return columnData;
	}

	public void setColumnData(List<Map<String, Object>> columnData) {
		this.columnData = columnData;
	}
}
