package com.anthunt.poi.template.model.enums;

public enum NumericFormatType {

	INTEGER("#,##0")
	, FLOAT1("#,##0.0")
	, FLOAT2("#,##0.00")
	, FLOAT3("#,##0.000")
	, FLOAT4("#,##0.0000")
	;
	
	private final String numericFormat;
	
	private NumericFormatType(String numericFormat) {
		this.numericFormat = numericFormat;
	}
	
	public String getNumericFormat() {
		return this.numericFormat;
	}
	
}
