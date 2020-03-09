package com.anthunt.poi.template.model.excel;

import com.anthunt.poi.template.model.enums.DBDataType;

public class ExcelDBColumn {

    private int dbColIndex;
	private String tableName;
	private String columnName;
	private DBDataType dataType;
	private boolean isPivot;
	private String pivotColumnName;
	private String pivotColumnValue;
	private Boolean isPivotPrimary;
	private Boolean isPrimary;
	private String dateFormat;
	
	protected ExcelDBColumn() {
		this.setPivot(false);
		this.setPrimary(false);
		this.setPivotPrimary(false);
	}
	
	public String getTableName() {
		return tableName;
	}
	
	protected void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	protected void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public DBDataType getDataType() {
		return dataType;
	}
	
	protected void setDataType(DBDataType dataType) {
		this.dataType = dataType;
	}
	
	public boolean isPivot() {
		return isPivot;
	}

	private void setPivot(boolean isPivot) {
		this.isPivot = isPivot;
	}
	
	protected void setPivotColumn(String pivotColumnName, String pivotColumnValue, Boolean isPivotPrimary) {
		this.setPivot(true);
		this.setPivotColumnName(pivotColumnName);
		this.setPivotColumnValue(pivotColumnValue);
		this.setPivotPrimary(isPivotPrimary);
	}
	
	public String getPivotColumnName() {
		return pivotColumnName;
	}
	
	private void setPivotColumnName(String pivotColumnName) {
		this.pivotColumnName = pivotColumnName;
	}
	
	public String getPivotColumnValue() {
		return pivotColumnValue;
	}
	
	private void setPivotColumnValue(String pivotColumnValue) {
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

	private void setPivotPrimary(Boolean isPivotPrimary) {
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
    protected void setDbColIndex(int dbColIndex) {
        this.dbColIndex = dbColIndex;
    }

	public String getDateFormat() {
		return dateFormat;
	}

	protected void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}	
	
}
