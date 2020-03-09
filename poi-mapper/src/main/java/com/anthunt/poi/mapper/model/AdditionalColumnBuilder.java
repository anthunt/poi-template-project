package com.anthunt.poi.mapper.model;

public class AdditionalColumnBuilder {
	
	private AdditionalDatasBuilder additionalDatasBuilder;
	private AdditionalColumns additionalColumns;
	
	public AdditionalColumnBuilder(AdditionalDatasBuilder additionalDatasBuilder, AdditionalColumns additionalColumns) {
		this.additionalDatasBuilder = additionalDatasBuilder;
		this.additionalColumns = additionalColumns;
	}
	
	public AdditionalColumnBuilder setAdditionalColumn(String columnName, String operation, String columnValue, UseType useType, boolean isPrimary, boolean useSingleQuote) {
	    AdditionalColumn additionalColumn = new AdditionalColumn();
        additionalColumn.setColumnName(columnName);
        additionalColumn.setOperation(operation);
        additionalColumn.setColumnValue(columnValue);
        additionalColumn.setUseType(useType);
        additionalColumn.setPrimary(isPrimary);
        additionalColumn.setUseSingleQuote(useSingleQuote);
        additionalColumns.add(additionalColumn);
        return this;
	}
	public AdditionalColumnBuilder setAdditionalColumn(String columnName, String operation, String columnValue, UseType useType, boolean isPrimary) {
		return this.setAdditionalColumn(columnName, operation, columnValue, useType, isPrimary, true);
	}
	
	public AdditionalDatasBuilder and() {
		return this.additionalDatasBuilder;
	}
	
	public AdditionalColumns build() {
		return this.additionalColumns;
	}	
	
}
