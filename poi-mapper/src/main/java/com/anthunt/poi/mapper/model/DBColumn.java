package com.anthunt.poi.mapper.model;

public class DBColumn {
	
	private String columnName;
	private String columnValue;
	private boolean isFixed;
	private Boolean isPrimary;
	
	public DBColumn(String columnName, Boolean isPrimary) {
		this(columnName, null, false, isPrimary);
	}
	
	public DBColumn(String columnName, String columnValue, Boolean isPrimary) {
		this(columnName, columnValue, true, isPrimary);
	}
	
	private DBColumn(String columnName, String columnValue, boolean isFixed, Boolean isPrimary) {
		this.isFixed = isFixed;
		this.isPrimary = isPrimary == null ? false : isPrimary;
		this.columnName = columnName;
		this.columnValue = columnValue;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getColumnValue() {
		return isFixed ? "'" + columnValue + "'" : "?";
	}

	public boolean isFixed() {
		return isFixed;
	}

	public Boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
	
}
