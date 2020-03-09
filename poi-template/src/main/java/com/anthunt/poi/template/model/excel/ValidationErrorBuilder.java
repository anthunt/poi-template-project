package com.anthunt.poi.template.model.excel;

public class ValidationErrorBuilder {

	private ValidationErrors validationErrors;
	private ValidationError validationError;
	
	protected ValidationErrorBuilder(ValidationErrors validationErrors) {
		this.validationErrors = validationErrors;
		this.validationError = new ValidationError();
	}
	
	public ValidationErrorBuilder setSheetIdx(int sheetIdx) {
		this.validationError.setSheetIdx(sheetIdx);
		return this;
	}
	
	public ValidationErrorBuilder setSheetName(String sheetName) {
		this.validationError.setSheetName(sheetName);
		return this;
	}
		
	public ValidationErrorBuilder setRowNum(int rowNum) {
		this.validationError.setRowNum(rowNum);
		return this;
	}
		
	public ValidationErrorBuilder setColumnIndex(int columnIndex) {
		this.validationError.setColumnIndex(columnIndex);
		return this;
	}
	
	public ValidationErrorBuilder setCellValue(String cellValue) {
		this.validationError.setCellValue(cellValue);
		return this;
	}
		
	public ValidationErrorBuilder setErrorMessage(String errorMessage) {
		this.validationError.setErrorMessage(errorMessage);
		return this;
	}

	public ValidationErrors and() {
		this.validationErrors.addError(this.validationError);
		return this.validationErrors;
	}
	
	public ValidationError build() {
		return this.validationError;
	}
	
}
