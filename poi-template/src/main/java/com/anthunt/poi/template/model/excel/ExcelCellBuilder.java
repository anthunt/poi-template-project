package com.anthunt.poi.template.model.excel;

public class ExcelCellBuilder {
	
	private ExcelColumnBuilder excelColumnBuilder;
	private ExcelCell excelCell;
	private ExcelColumn.ExcelCellType excelCellType;
	
	protected ExcelCellBuilder(ExcelColumnBuilder excelColumnBuilder, ExcelColumn.ExcelCellType excelCellType) {
		this.excelCell = new ExcelCell();
		this.excelColumnBuilder = excelColumnBuilder;
		this.excelCellType = excelCellType;
	}

	public ExcelCellBuilder setValue(String value) {
		this.excelCell.setValue(value);
		return this;
	}

	public ExcelCellBuilder setComment(String headerMemo) {
		this.excelCell.setComment(headerMemo);
		return this;
	}

	public ExcelCellBuilder setSkipRequired(boolean skipRequired) {
		this.excelCell.setSkipRequired(skipRequired);
		return this;
	}

	public ExcelCellBuilder setRowMergeSize(int rowMergeSize) {
		this.excelCell.setRowMergeSize(rowMergeSize);
		return this;
	}

	public ExcelCellBuilder setCellMergeSize(int cellMergeSize) {
		this.excelCell.setCellMergeSize(cellMergeSize);
		return this;
	}
	
	public ExcelCell build() {
		return this.excelCell;
	}
	
	public ExcelColumnBuilder and() {
		switch (this.excelCellType) {
		case HEADER:
			this.excelColumnBuilder.addHeaderColumn(this.excelCell);
			break;

		case EXPLAIN:
			this.excelColumnBuilder.addExplainColumn(this.excelCell);
			break;
		}
		return this.excelColumnBuilder;
	}
	
}
