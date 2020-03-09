package com.anthunt.poi.template.model.excel;

import java.util.ArrayList;
import java.util.List;

public class ExcelSheet {

	private int sheetIndex;
	private String sheetName;
	private String sheetTitle;
	private int explainRowSize;
	private int headerRowSize;
	private List<ExcelColumn> excelColumns;
	private boolean skipSheet;
	private boolean useSampleData;
	
	protected ExcelSheet() {
		this.excelColumns = new ArrayList<ExcelColumn>();
		this.useSampleData = false;
		this.explainRowSize = 0;
		this.headerRowSize = 1;
	}
	
	public int getSheetIndex() {
		return new Integer(sheetIndex);
	}
	
	protected void setSheetIndex(int sheetIndex) {
		this.sheetIndex = new Integer(sheetIndex);
	}
	
	public String getSheetName() {
		return new String(sheetName);
	}
	
	protected void setSheetName(String sheetName) {
		this.sheetName = new String(sheetName);
	}
	
	public boolean hasSheetTitle() {
		return this.sheetTitle == null ? false : true;
	}
	
	public String getSheetTitle() {
		return new String(sheetTitle);
	}

	protected void setSheetTitle(String sheetTitle) {
		this.sheetTitle = new String(sheetTitle);
	}

	public int getExplainRowSize() {
		return new Integer(explainRowSize);
	}

	protected void setExplainRowSize(int explainRowSize) {
		this.explainRowSize = new Integer(explainRowSize);
	}

	public int getHeaderRowSize() {
		return new Integer(headerRowSize);
	}

	protected void setHeaderRowSize(int headerRowSize) {
		this.headerRowSize = new Integer(headerRowSize);
	}

	public List<ExcelColumn> getExcelColumns() {
		return new ArrayList<ExcelColumn>(this.excelColumns);
	}

	protected void addExcelColumn(ExcelColumn excelColumn) {
		this.excelColumns.add(excelColumn);
	}
	
	protected void setExcelColumns(List<ExcelColumn> excelColumns) {
		this.excelColumns.addAll(excelColumns);
	}

	public boolean isSkipSheet() {
		return skipSheet;
	}

	protected void setSkipSheet(boolean skipSheet) {
		this.skipSheet = new Boolean(skipSheet);
	}

	public boolean isUseSampleData() {
		return useSampleData;
	}

	protected void setUseSampleData(boolean useSampleData) {
		this.useSampleData = new Boolean(useSampleData);
	}

	public int getFirstDataRowIndex() {
		
		int firstDataRowIndex = 0;
		
		if(this.hasSheetTitle()) {
			firstDataRowIndex++;
		}
		
		firstDataRowIndex += this.getExplainRowSize();
		firstDataRowIndex += this.getHeaderRowSize();
		firstDataRowIndex++;
		
		return firstDataRowIndex;
	}
	
}
