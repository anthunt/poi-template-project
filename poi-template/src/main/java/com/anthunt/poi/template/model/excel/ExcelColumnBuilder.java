package com.anthunt.poi.template.model.excel;

import java.sql.Date;

import org.apache.poi.ss.usermodel.CellType;

import com.anthunt.poi.template.model.enums.NumericFormatType;

public class ExcelColumnBuilder {

	private ExcelColumn excelColumn;
	private ExcelSheetBuilder excelSheetBuilder;
	
	protected ExcelColumnBuilder(ExcelSheetBuilder excelSheetBuilder) {
		this();
		this.excelSheetBuilder = excelSheetBuilder;
	}
	
	protected ExcelColumnBuilder() {
		this.excelColumn = new ExcelColumn();
	}

	public ExcelColumnConstraintBuilder addColumnConstraint() {
		return new ExcelColumnConstraintBuilder(this);
	}
	
	public ExcelCellBuilder addHeaderColumn() {
		return new ExcelCellBuilder(this, ExcelColumn.ExcelCellType.HEADER);
	}
	
	protected ExcelColumnBuilder addHeaderColumn(ExcelCell excelCell) {
		this.excelColumn.addHeaderColumn(excelCell);
		return this;
	}
	
	public ExcelCellBuilder addExplainColumn() {
		return new ExcelCellBuilder(this, ExcelColumn.ExcelCellType.EXPLAIN);
	}
	
	protected ExcelColumnBuilder addExplainColumn(ExcelCell excelCell) {
		this.excelColumn.addExplainColumn(excelCell);
		return this;
	}

	public ExcelDBColumnBuilder addDBColumn() {
		return new ExcelDBColumnBuilder(this);
	}
	
	protected ExcelColumnBuilder addDBColumn(ExcelDBColumn excelDBColumn) {
		this.excelColumn.addDBColumn(excelDBColumn);
		return this;
	}
	
	public ExcelColumnBuilder setCellType(CellType cellType) {
		this.excelColumn.setDataCellType(cellType);
		return this;
	}

	public ExcelColumnBuilder setColumnFormula(String columnFormula) {
		this.excelColumn.setColumnFormula(columnFormula);
		return this;
	}
	
	public ExcelColumnBuilder setRequired(boolean isRequired) {
		this.excelColumn.setRequired(isRequired);
		return this;
	}

	public ExcelColumnBuilder setDataExplicits(String[] dataExplicits) {
		this.excelColumn.setDataExplicits(dataExplicits);
		return this;
	}
	
	public ExcelColumnBuilder setSampleValue(String sampleValue) {
		this.excelColumn.setSampleValue(sampleValue);
		return this;
	}
	
	public ExcelColumnBuilder setSampleValue(boolean sampleValue) {
		this.excelColumn.setSampleValue(Boolean.toString(sampleValue));
		return this;
	}
	
	public ExcelColumnBuilder setSampleValue(Date sampleValue) {
		this.excelColumn.setSampleValue(sampleValue.toString());
		return this;
	}
	
	public ExcelColumnBuilder setSampleValue(double sampleValue) {
		this.excelColumn.setSampleValue(Double.toString(sampleValue));
		return this;
	}

	public ExcelColumnBuilder setNumericFormatType(NumericFormatType numericFormatType) {
		this.excelColumn.setNumericFormatType(numericFormatType);
		return this;
	}

	public ExcelColumnBuilder setExcelColumnConstraint(ExcelColumnConstraint excelColumnConstraint) {
		this.excelColumn.setExcelColumnConstraint(excelColumnConstraint);
		return this;
	}
	
	public ExcelSheetBuilder and() {
		this.excelSheetBuilder.addExcelColumn(this.excelColumn);
		return this.excelSheetBuilder;
	}
	
	public ExcelColumn build() {
		return this.excelColumn;
	}
	
}
