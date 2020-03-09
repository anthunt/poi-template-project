package com.anthunt.poi.mapper.model;

public class SheetDefinition {
	
	private int sheetIndex;
	private String sheetName;
	private String sheetTitle;
	private int explainRowSize;
	private int headerRowSize;
	private ColumnDefinitions columnDefinitions;
	private boolean skipSheet;
	
	public int getSheetIndex() {
		return sheetIndex;
	}
	
	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}
	
	public String getSheetName() {
		return sheetName;
	}
	
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	public String getSheetTitle() {
		return sheetTitle;
	}
	
	public void setSheetTitle(String sheetTitle) {
		this.sheetTitle = sheetTitle;
	}
	
	public int getExplainRowSize() {
		return explainRowSize;
	}
	
	public void setExplainRowSize(int explainRowSize) {
		this.explainRowSize = explainRowSize;
	}
	
	public int getHeaderRowSize() {
		return headerRowSize;
	}
	
	public void setHeaderRowSize(int headerRowSize) {
		this.headerRowSize = headerRowSize;
	}
	
	public ColumnDefinitions getColumnDefinitions() {
		return columnDefinitions;
	}

	public void setColumnDefinitions(ColumnDefinitions columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

	public boolean isSkipSheet() {
		return skipSheet;
	}
	
	public void setSkipSheet(boolean skipSheet) {
		this.skipSheet = skipSheet;
	}
		
}
