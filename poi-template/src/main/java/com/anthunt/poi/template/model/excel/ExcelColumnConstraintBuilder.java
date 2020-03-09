package com.anthunt.poi.template.model.excel;

public class ExcelColumnConstraintBuilder {

	private ExcelColumnBuilder excelColumnBuilder;
	private ExcelColumnConstraint excelColumnConstraint;
	
	protected ExcelColumnConstraintBuilder(ExcelColumnBuilder excelColumnBuilder) {
		this.excelColumnBuilder = excelColumnBuilder;
		this.excelColumnConstraint = new ExcelColumnConstraint();
	}

	public ExcelColumnConstraintBuilder setMin(int min) {
		this.excelColumnConstraint.setMin(min);
		return this;
	}

	public ExcelColumnConstraintBuilder setMax(int max) {
		this.excelColumnConstraint.setMax(max);
		return this;
	}

	public ExcelColumnConstraintBuilder setRegExp(String regExp) {
		this.excelColumnConstraint.setRegExp(regExp);
		return this;
	}
	
	public ExcelColumnBuilder and() {
		this.excelColumnBuilder.setExcelColumnConstraint(this.excelColumnConstraint);
		return this.excelColumnBuilder;
	}
	
	public ExcelColumnConstraint build() {
		return this.excelColumnConstraint;
	}
	
}
