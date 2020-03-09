package com.anthunt.poi.template.exception;

import com.anthunt.poi.template.model.excel.ValidationErrors;

public class ValidateException extends Exception {

	private static final long serialVersionUID = -2873622553686338340L;
	
	private ValidationErrors validationErrors;
	
	public ValidateException(ValidationErrors validationErrors) {
		this.validationErrors = validationErrors;
	}
	
	public ValidationErrors getValidationErrors() {
		return this.validationErrors;
	}
	
}
