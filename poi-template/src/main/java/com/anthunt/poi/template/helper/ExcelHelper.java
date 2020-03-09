package com.anthunt.poi.template.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;

import com.anthunt.poi.template.model.excel.ExcelColumn;

public class ExcelHelper {
	
	private static PoiLogger logger = PoiLogger.getLogger(ExcelHelper.class);
	
	public static Sheet getSheet(Workbook workbook, String sheetName) {
		logger.trace("start getSheet [{}]", sheetName);
		Sheet sheet = workbook.getSheet(sheetName);
		if(sheet == null) {
			sheet = workbook.createSheet(sheetName);
			logger.trace("created sheet [{}]", sheetName);
		}
		return sheet;
	}
	
	public static Row getRow(Sheet sheet, int rowNum) {
		logger.trace("start getRow [{} sheet - row {}]", sheet.getSheetName(), rowNum);
		Row row = sheet.getRow(rowNum);
		if(row == null) {
			row = sheet.createRow(rowNum);
			logger.trace("created row [{} sheet - row {}]", sheet.getSheetName(), rowNum);
		}
		return row;
	}
	
	public static Cell getCell(Row row, int cellIndex) {
		logger.trace("start getCell [{}, {}]", row.getRowNum(), cellIndex);
		Cell cell = row.getCell(cellIndex);
		if(cell == null) {
			cell = row.createCell(cellIndex);
			logger.trace("created cell [{}, {}]", row.getRowNum(), cellIndex);
		}
		return cell;
	}
	
	/**
	 * 엑셀 함수 변환 메소드
	 * <br/>
	 * ex) SUM({COL1}{ROW}:{COL4}{ROW})
	 * <br/>
	 * {COL[컬럼 인덱스]} - 수식 컬럼 인덱스 참조
	 * {ROW} - 수식 설정 Row 인덱스 참조
	 * 
	 * @param excelFormulaExpression
	 * @param rowIndex
	 * @return Excel에서 인식되어지는 함수 문자열
	 */
	public static String getParsedExcelFormula(String excelFormulaExpression, int rowIndex) {
		
		logger.trace("start parse excel formula [{} - row {}]", excelFormulaExpression, rowIndex);
		
		Pattern pattern = Pattern.compile("\\{COL\\d+\\}");
		
		String str = excelFormulaExpression;
		
		Matcher m = pattern.matcher(str);
		while(m.find()) {
			String colPattern = m.group();
			logger.trace("finded : {}", colPattern);
			
			Matcher subMatcher = Pattern.compile("\\d+").matcher(colPattern);
			if(subMatcher.find()) {
				logger.trace("sub replace before [{}]", str);
				str = str.replaceAll(colPattern.replaceAll("\\{", "\\\\{"), "\\$" + CellReference.convertNumToColString(Integer.parseInt(subMatcher.group()) - 1));
				logger.trace("sub replace after [{}]", str);
			}
		}
		
		return str.replaceAll("\\{ROW\\}", Integer.toString(rowIndex + 1));
	}
	
	public static void setDataValidation(Sheet sheet, String[] dataExplicitList, int firstRow, int lastRow, int firstCol, int lastCol) {
		    
		    CellRangeAddressList addressList = new  CellRangeAddressList(firstRow,lastRow,firstCol,lastCol);
		    
		    DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
		    DataValidationConstraint constraint = dataValidationHelper.createExplicitListConstraint(dataExplicitList);
		    DataValidation dataValidation = dataValidationHelper.createValidation(constraint, addressList);
		    
		    dataValidation.setSuppressDropDownArrow(true);
		    dataValidation.setEmptyCellAllowed(false);
		    dataValidation.setShowErrorBox(true);
		    
		    sheet.addValidationData(dataValidation);
		    
	}
	
	public static Comment createComment(Drawing<?> drawing, CreationHelper factory, ClientAnchor anchor, String comments) {
		Comment comment = drawing.createCellComment(anchor);						
		RichTextString commentString = factory.createRichTextString(comments);
		comment.setString(commentString);
		comment.setAuthor("Apache POI");
		return comment;
	}
	
	public static void addMergeCell(Sheet sheet, Cell cell, int rowMergeSize, int colMergeSize) {
		if(rowMergeSize > 0 || colMergeSize > 0) {
			int firstRowNum = cell.getRowIndex();
			int lastRowNum = firstRowNum + (rowMergeSize > 1 ? rowMergeSize - 1 : 0);
			int firstColNum = cell.getColumnIndex();
			int lastColNum = firstColNum + (colMergeSize > 1 ? colMergeSize - 1 : 0);
			sheet.addMergedRegion(new CellRangeAddress(firstRowNum, lastRowNum, firstColNum, lastColNum));
		}
	}
	
	public static Cell createDataCellValue(Sheet sheet, Row row, int cellIndex, ExcelColumn excelColumn, String value) {
		Cell cell = ExcelHelper.getCell(row, cellIndex);
		cell.setCellType(excelColumn.getDataCellType());
		cell.setCellStyle(excelColumn.getDataCellStyle().getXSSFCellStyle());
		if(excelColumn.getDataExplicits() != null) {
			ExcelHelper.setDataValidation(sheet, excelColumn.getDataExplicits(), cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(), cell.getColumnIndex());
		}
		
		if(CellType.FORMULA == excelColumn.getDataCellType()) {
			cell.setCellFormula(ExcelHelper.getParsedExcelFormula(excelColumn.getColumnFormula(), cell.getRowIndex()));
		} else {
			if(CellType.NUMERIC == excelColumn.getDataCellType()) {
				cell.setCellValue(Double.parseDouble(value));
			} else {
				cell.setCellValue(value);
			}
		}
		return cell;
	}
	
	public static void setAutoSizeColumn(Workbook workbook) {
		int iSheetCount = workbook.getNumberOfSheets();
		Sheet sheet = null;
		Row row = null;
		int iCellCount = 0;
		for (int iSheetIndex = 0; iSheetIndex < iSheetCount; iSheetIndex++) {
			sheet = workbook.getSheetAt(iSheetIndex);
			row = sheet.getRow(sheet.getLastRowNum());
			if(row != null) {
				iCellCount = row.getLastCellNum();
				for (int iCol = 0; iCol < iCellCount; iCol++) {
					sheet.autoSizeColumn((short) iCol);
					sheet.setColumnWidth(iCol, (sheet.getColumnWidth(iCol))+512 );  // 윗줄만으로는 컬럼의 width 가 부족하여 더 늘려야 함.
				}
			}
		}
	}
	
}
