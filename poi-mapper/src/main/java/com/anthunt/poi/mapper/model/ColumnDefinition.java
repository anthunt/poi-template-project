package com.anthunt.poi.mapper.model;

import java.util.List;

public class ColumnDefinition {
	
	private CellDefinitions headerCellDefinitions;
	private CellDefinitions explainCellDefinitions;
	
	private String dataCellType;
	private String numericFormatType;
	private String columnFormula;
	private String explicitSql;
	
	private boolean isRequired;
	
	private List<String> dataExplicits;
	private String sampleValue;
	
	private List<DBColumnDefinition> dbColumnDefinitions;
	
	private ColumnConstraintDefinition columnConstraintDefinition;

	public CellDefinitions getHeaderCellDefinitions() {
		return headerCellDefinitions;
	}

	public void setHeaderCellDefinitions(CellDefinitions headerCellDefinitions) {
		this.headerCellDefinitions = headerCellDefinitions;
	}

	public CellDefinitions getExplainCellDefinitions() {
		return explainCellDefinitions;
	}

	public void setExplainCellDefinitions(CellDefinitions explainCellDefinitions) {
		this.explainCellDefinitions = explainCellDefinitions;
	}

	public String getDataCellType() {
		return dataCellType;
	}

	public void setDataCellType(String dataCellType) {
		this.dataCellType = dataCellType;
	}

	public String getNumericFormatType() {
		return numericFormatType;
	}

	public void setNumericFormatType(String numericFormatType) {
		this.numericFormatType = numericFormatType;
	}

	public String getColumnFormula() {
		return columnFormula;
	}

	public void setColumnFormula(String columnFormula) {
		this.columnFormula = columnFormula;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public List<String> getDataExplicits() {
		return dataExplicits;
	}

	public void setDataExplicits(List<String> dataExplicits) {
		this.dataExplicits = dataExplicits;
	}

	public String getSampleValue() {
		return sampleValue;
	}

	public void setSampleValue(String sampleValue) {
		this.sampleValue = sampleValue;
	}

	public List<DBColumnDefinition> getDbColumnDefinitions() {
		return dbColumnDefinitions;
	}

	public void setDbColumnDefinitions(List<DBColumnDefinition> dbColumnDefinitions) {
		this.dbColumnDefinitions = dbColumnDefinitions;
	}

	public ColumnConstraintDefinition getColumnConstraintDefinition() {
		return columnConstraintDefinition;
	}

	public void setColumnConstraintDefinition(ColumnConstraintDefinition columnConstraintDefinition) {
		this.columnConstraintDefinition = columnConstraintDefinition;
	}

	public String getExplicitSql() {
		return explicitSql;
	}

	public void setExplicitSql(String explicitSql) {
		this.explicitSql = explicitSql;
	}
	
}
