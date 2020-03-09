package com.anthunt.poi.template.model.excel;

import org.apache.poi.ss.usermodel.Row;

public class ExcelRow {

	private Row row;
	
	public ExcelRow(Row row) {
		this.row = row;
	}
	
	public Row getRow() {
		return this.row;
	}
	
	public int getRowNum() {
		return new Integer(this.row.getRowNum());
	}
	
}
