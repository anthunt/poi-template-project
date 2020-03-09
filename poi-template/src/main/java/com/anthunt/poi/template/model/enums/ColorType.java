package com.anthunt.poi.template.model.enums;

import org.apache.poi.xssf.usermodel.XSSFColor;

enum ColorType {

	DEFAULT_COLOR(255,255,255)
	, SHEET_TITLE_COLOR(255,255,255)
	, EXPLAIN_COLOR(181,193,219)
	, REQUIRED_HEAD_COLOR(153,153,102)
	, HEAD_COLOR(204,204,153)
	, REQUIRED_DATA_COLOR(204, 153, 051)
	, FORMULA_DATA_COLOR(202,202,202)
	;
	
	private final XSSFColor xssfColor;
	
	private ColorType(int R, int G, int B) {
		this.xssfColor = new XSSFColor(new java.awt.Color(R, G, B));
	}
	
	protected XSSFColor getXSSFColor() {
		return this.xssfColor;
	}
	
}
