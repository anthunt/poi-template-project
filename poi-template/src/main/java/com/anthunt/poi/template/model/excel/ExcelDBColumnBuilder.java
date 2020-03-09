package com.anthunt.poi.template.model.excel;

import com.anthunt.poi.template.model.enums.DBDataType;

public class ExcelDBColumnBuilder {

	private ExcelDBColumn excelDBColumn;
	private ExcelColumnBuilder excelColumnBuilder;
	
	protected ExcelDBColumnBuilder(ExcelColumnBuilder excelColumnBuilder) {
		this();
		this.excelColumnBuilder = excelColumnBuilder;
	}
	
	protected ExcelDBColumnBuilder() {
		this.excelDBColumn = new ExcelDBColumn();
	}
	
	public ExcelDBColumnBuilder setTableName(String tableName) {
		this.excelDBColumn.setTableName(tableName);
		return this;
	}
	
	public ExcelDBColumnBuilder setColumnName(String columnName) {
		this.excelDBColumn.setColumnName(columnName);
		return this;
	}
	
	public ExcelDBColumnBuilder setDataType(DBDataType dataType) {
		this.excelDBColumn.setDataType(dataType);
		return this;
	}
	
	public ExcelDBColumnBuilder setPivotColumn(String pivotColumnName, String pivotColumnValue, Boolean isPivotPrimary) {
		this.excelDBColumn.setPivotColumn(pivotColumnName, pivotColumnValue, isPivotPrimary);
		return this;
	}
	
	public ExcelDBColumnBuilder setPrimary(Boolean isPrimary) {
		this.excelDBColumn.setPrimary(isPrimary);
		return this;
	}
	
	public ExcelDBColumnBuilder setDbColIndex(int dbColIndex) {
	    this.excelDBColumn.setDbColIndex(dbColIndex);
	    return this;
	}
	
	public ExcelDBColumnBuilder setDateFormat(String dateFormat) {
		this.excelDBColumn.setDateFormat(dateFormat);
		return this;
	}
	
	public ExcelColumnBuilder and() {
		this.excelColumnBuilder.addDBColumn(this.excelDBColumn);
		return this.excelColumnBuilder;
	}
	
	public ExcelDBColumn build() {
		return this.excelDBColumn;
	}
	
}
