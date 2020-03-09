package com.anthunt.poi.template.exception;

public class TemplateException extends Exception {

	private static final long serialVersionUID = 7914664505899142814L;

	public TemplateException() {
		super();
	}

	public TemplateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TemplateException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemplateException(String message) {
		super(message);
	}

	public TemplateException(Throwable cause) {
		super(cause);
	}

}
