package com.anthunt.poi.template.model.excel;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrors {

	private List<ValidationError> validationErrors;
	
	protected ValidationErrors() {
		this.validationErrors = new ArrayList<>();
	}
	
	protected ValidationErrorBuilder addError() {
		return new ValidationErrorBuilder(this);
	}
	
	public List<ValidationError> getErrors() {
		return this.validationErrors;
	}
	
	public int size() {
		return this.validationErrors.size();
	}

	protected void addError(ValidationError validationError) {
		this.validationErrors.add(validationError);
	}
	
}
