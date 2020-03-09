package com.anthunt.poi.mapper.model;

public class DBColumnDefinition {
	
    private int dbColIndex;
	private String tableName;
	private String columnName;
	private String dataType;
	private boolean isPivot;
	private String pivotColumnName;
	private String pivotColumnValue;
	private Boolean isPivotPrimary;
	private Boolean isPrimary;
	private String dateFormat;

	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public boolean isPivot() {
		return isPivot;
	}
	
	public void setPivot(boolean isPivot) {
		this.isPivot = isPivot;
	}
	
	public String getPivotColumnName() {
		return pivotColumnName;
	}
	
	public void setPivotColumnName(String pivotColumnName) {
		this.pivotColumnName = pivotColumnName;
	}
	
	public String getPivotColumnValue() {
		return pivotColumnValue;
	}
	
	public void setPivotColumnValue(String pivotColumnValue) {
		this.pivotColumnValue = pivotColumnValue;
	}

	public Boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public Boolean isPivotPrimary() {
		return isPivotPrimary;
	}

	public void setPivotPrimary(Boolean isPivotPrimary) {
		this.isPivotPrimary = isPivotPrimary;
	}

    /**
     * @return the dbColIndex
     */
    public int getDbColIndex() {
        return dbColIndex;
    }

    /**
     * @param dbColIndex the dbColIndex to set
     */
    public void setDbColIndex(int dbColIndex) {
        this.dbColIndex = dbColIndex;
    }

	public String getDateFormat() {
		return this.dateFormat;
	}
	
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
}
