package com.anthunt.poi.template.model.enums;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public enum FontType {

	DEFAULT_FONT(false, "맑은 고딕", IndexedColors.BLACK.getIndex(), (short) 9)
	, SHEET_TITLE_FONT(true, "맑은 고딕", IndexedColors.BLACK.getIndex(), (short) 12)
	, EXPLAIN_FONT(false, "맑은 고딕", IndexedColors.BLUE.getIndex(), (short) 9)
	, REQUIRED_HEADER_FONT(true, "맑은 고딕", IndexedColors.BROWN.getIndex(), (short) 10)
	, HEADER_FONT(true, "맑은 고딕", IndexedColors.BROWN.getIndex(), (short) 10)
	;
	
	private final boolean isBold;
	private final String fontName;
	private final short color;
	private final short fontHeightInPoints;
	private XSSFFont xssfFont;
	
	private FontType(boolean isBold, String fontName, short color, short fontHeightInPoints) {
		this.isBold = isBold;
		this.fontName = fontName;
		this.color = color;
		this.fontHeightInPoints = fontHeightInPoints;
	}
	
	public static void initialize(XSSFWorkbook xssfWorkbook) {
		FontType[] fontTypes = FontType.values();
		for (FontType fontType : fontTypes) {
			fontType.create(xssfWorkbook);
		}
	}
	
	private void create(XSSFWorkbook xssfWorkbook) {
		this.xssfFont = xssfWorkbook.createFont();
		this.xssfFont.setBold(this.isBold);
		this.xssfFont.setFontName(this.fontName);
		this.xssfFont.setColor(this.color);
		this.xssfFont.setFontHeightInPoints(this.fontHeightInPoints);
	}
	
	protected XSSFFont getXSSFFont() {
		return this.xssfFont;
	}
	
}
