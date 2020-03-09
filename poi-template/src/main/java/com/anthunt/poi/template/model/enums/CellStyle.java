package com.anthunt.poi.template.model.enums;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public enum CellStyle {

	DEFAULT_STYLE(ColorType.DEFAULT_COLOR, FontType.DEFAULT_FONT)
	, FORMULA_DATA_STYLE(ColorType.FORMULA_DATA_COLOR, FontType.DEFAULT_FONT)
	, FORMULA_INT_STYLE(ColorType.FORMULA_DATA_COLOR, FontType.DEFAULT_FONT, NumericFormatType.INTEGER)
	, FORMULA_FLOAT1_STYLE(ColorType.FORMULA_DATA_COLOR, FontType.DEFAULT_FONT, NumericFormatType.FLOAT1)
	, FORMULA_FLOAT2_STYLE(ColorType.FORMULA_DATA_COLOR, FontType.DEFAULT_FONT, NumericFormatType.FLOAT2)
	, FORMULA_FLOAT3_STYLE(ColorType.FORMULA_DATA_COLOR, FontType.DEFAULT_FONT, NumericFormatType.FLOAT3)
	, FORMULA_FLOAT4_STYLE(ColorType.FORMULA_DATA_COLOR, FontType.DEFAULT_FONT, NumericFormatType.FLOAT4)
	, REQUIRED_DATA_STYLE(ColorType.REQUIRED_DATA_COLOR, FontType.DEFAULT_FONT)
	, REQUIRED_INT_STYLE(ColorType.REQUIRED_DATA_COLOR, FontType.DEFAULT_FONT, NumericFormatType.INTEGER)
	, REQUIRED_FLOAT1_STYLE(ColorType.REQUIRED_DATA_COLOR, FontType.DEFAULT_FONT, NumericFormatType.FLOAT1)
	, REQUIRED_FLOAT2_STYLE(ColorType.REQUIRED_DATA_COLOR, FontType.DEFAULT_FONT, NumericFormatType.FLOAT2)
	, REQUIRED_FLOAT3_STYLE(ColorType.REQUIRED_DATA_COLOR, FontType.DEFAULT_FONT, NumericFormatType.FLOAT3)
	, REQUIRED_FLOAT4_STYLE(ColorType.REQUIRED_DATA_COLOR, FontType.DEFAULT_FONT, NumericFormatType.FLOAT4)
	, INT_STYLE(ColorType.DEFAULT_COLOR, FontType.DEFAULT_FONT, NumericFormatType.INTEGER)
	, FLOAT1_STYLE(ColorType.DEFAULT_COLOR, FontType.DEFAULT_FONT, NumericFormatType.FLOAT1)
	, FLOAT2_STYLE(ColorType.DEFAULT_COLOR, FontType.DEFAULT_FONT, NumericFormatType.FLOAT2)
	, FLOAT3_STYLE(ColorType.DEFAULT_COLOR, FontType.DEFAULT_FONT, NumericFormatType.FLOAT3)
	, FLOAT4_STYLE(ColorType.DEFAULT_COLOR, FontType.DEFAULT_FONT, NumericFormatType.FLOAT4)
	, REQUIRED_HEADER_STYLE(ColorType.REQUIRED_HEAD_COLOR, FontType.REQUIRED_HEADER_FONT)
	, HEADER_STYLE(ColorType.HEAD_COLOR, FontType.HEADER_FONT)
	, EXPLAIN_STYLE(ColorType.EXPLAIN_COLOR, FontType.EXPLAIN_FONT)
	, SHEET_TITLE_STYLE(ColorType.SHEET_TITLE_COLOR, FontType.SHEET_TITLE_FONT)
	;
	
	private final ColorType colorType;
	private final FontType fontType;
	private final NumericFormatType numericFormatType;
	private XSSFCellStyle xssfCellStyle;
	
	private CellStyle(ColorType colorType, FontType fontType, NumericFormatType numericFormatType) {
		this.colorType = colorType;
		this.fontType = fontType;
		this.numericFormatType = numericFormatType;
	}
	
	private CellStyle(ColorType colorType, FontType fontType) {
		this(colorType, fontType, null);
	}
		
	private ColorType getColorType() {
		return colorType;
	}

	private FontType getFontType() {
		return fontType;
	}

	public XSSFCellStyle getXSSFCellStyle() {
		return xssfCellStyle;
	}

	private void setXSSFCellStyle(XSSFCellStyle xssfCellStyle) {
		this.xssfCellStyle = xssfCellStyle;
	}
	
	private NumericFormatType getNumericFormatType() {
		return numericFormatType;
	}

	public static void initialize(XSSFWorkbook workbook) {
		
		CellStyle[] cellStyles = CellStyle.values();
		
		DataFormat format = workbook.createDataFormat();
		
		for (CellStyle cellStyle : cellStyles) {
			
			XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
			xssfCellStyle.setBorderTop(BorderStyle.THIN);
			xssfCellStyle.setBorderLeft(BorderStyle.THIN);
			xssfCellStyle.setBorderBottom(BorderStyle.THIN);
			xssfCellStyle.setBorderRight(BorderStyle.THIN);
			xssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
			xssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			xssfCellStyle.setFont(cellStyle.getFontType().getXSSFFont());
			xssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			xssfCellStyle.setFillForegroundColor(cellStyle.getColorType().getXSSFColor());
			xssfCellStyle.setWrapText(true);
			if(cellStyle.getNumericFormatType() != null) {
				xssfCellStyle.setDataFormat(format.getFormat(cellStyle.getNumericFormatType().getNumericFormat()));
			}
			cellStyle.setXSSFCellStyle(xssfCellStyle);
			
		}
		
	}
	
}
