package com.anthunt.poi.template.model.excel;

public class ExcelSheetBuilder {

	private ExcelSheets excelSheets;
	private ExcelSheet excelSheet;
	
	protected ExcelSheetBuilder(ExcelSheets excelSheets) {
		this.excelSheet = new ExcelSheet();
		this.excelSheets = excelSheets;
	}
	
	public ExcelSheetBuilder setSheetName(String sheetName) {
		this.excelSheet.setSheetName(sheetName);
		return this;
	}

	public ExcelSheetBuilder setSheetTitle(String sheetTitle) {
		this.excelSheet.setSheetTitle(sheetTitle);
		return this;
	}

	public ExcelSheetBuilder setExplainRowSize(int explainRowSize) {
		this.excelSheet.setExplainRowSize(explainRowSize);
		return this;
	}

	public ExcelSheetBuilder setHeaderRowSize(int headerRowSize) {
		this.excelSheet.setHeaderRowSize(headerRowSize);
		return this;
	}

	public ExcelColumnBuilder addExcelColumn() {
		return new ExcelColumnBuilder(this);
	}
	
	public ExcelSheetBuilder addExcelColumn(ExcelColumn excelColumn) {
		this.excelSheet.addExcelColumn(excelColumn);
		return this;
	}

	public ExcelSheetBuilder setSkipSheet(boolean skipSheet) {
		this.excelSheet.setSkipSheet(skipSheet);
		return this;
	}

	public ExcelSheetBuilder setUseSampleData(boolean useSampleData) {
		this.excelSheet.setUseSampleData(useSampleData);
		return this;
	}
	
	public ExcelSheet build() {
		this.excelSheet.setSheetIndex(this.excelSheets.size());
		this.excelSheets.add(this.excelSheet.getSheetIndex(), this.excelSheet);
		return this.excelSheet;
	}
	
}
