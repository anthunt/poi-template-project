package com.anthunt.poi.template;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.anthunt.poi.template.exception.ValidateException;
import com.anthunt.poi.template.helper.ExcelHelper;
import com.anthunt.poi.template.helper.PoiLogger;
import com.anthunt.poi.template.model.excel.ExcelColumn;
import com.anthunt.poi.template.model.excel.ExcelRow;
import com.anthunt.poi.template.model.excel.ExcelSheet;
import com.anthunt.poi.template.model.excel.ExcelSheets;
import com.anthunt.poi.template.model.excel.ValidationErrors;

public abstract class AbstractDataProvider {

	private static PoiLogger logger = PoiLogger.getLogger(AbstractDataProvider.class);
	
	private Sheet sheet;
	private ExcelSheets excelSheets;
	private List<ExcelColumn> excelColumns;
	private int firstDataRowNum;
	private int currentDataRowNum;


	protected void initialize(ExcelSheets excelSheets) {
		this.excelSheets = excelSheets;
	}
	
	protected void apply(ExcelSheet excelSheet, Sheet sheet, List<ExcelColumn> excelColumns, int firstDataRowNum) throws ValidateException {
		this.sheet = sheet;
		this.excelColumns = excelColumns;
		this.firstDataRowNum = new Integer(firstDataRowNum);
		this.currentDataRowNum = new Integer(firstDataRowNum);
		this.applyData(excelSheet);
		if(this.getExcelSheets().getValidateErrors().size() > 0) {
			throw new ValidateException(this.getExcelSheets().getValidateErrors());
		}
	}
	
	private Sheet getSheet() {
		return this.sheet;
	}
	
	private int getSheetIndex() {
		return this.getSheet().getWorkbook().getSheetIndex(this.getSheet());
	}
	
	protected String getSheetName() {
		return this.sheet.getSheetName();
	}
	
	private ExcelSheets getExcelSheets() {
		return this.excelSheets;
	}
	
	protected int getFirstDataRowNum() {
		return new Integer(this.firstDataRowNum);
	}
	
	protected int getCurrentDataRowNum() {
		return new Integer(this.currentDataRowNum);
	}
	
	private List<ExcelColumn> getExcelColumns() {
		return this.excelColumns;
	}
	
	protected int getNumOfColumns() {
		return this.excelColumns.size();
	}
	
	protected ExcelRow getRow() {
		return new ExcelRow(this.sheet.createRow(this.currentDataRowNum++));
	}

	protected void setCellData(ExcelRow excelRow, int cellIndex) {
		ExcelHelper.createDataCellValue(this.sheet, excelRow.getRow(), cellIndex, this.getExcelColumns().get(cellIndex), null);
	}
	
	protected void setCellData(ExcelRow excelRow, int cellIndex, String value) {
		if(value == null) {
			value = "";
		}
		ExcelColumn excelColumn = this.getExcelColumns().get(cellIndex);
		Cell cell = ExcelHelper.createDataCellValue(this.getSheet(), excelRow.getRow(), cellIndex, excelColumn, value);
		this.getExcelSheets().validate(this.getSheetIndex(), cell);
		
	}

	protected ValidationErrors getValidationErrors() {
		return this.getExcelSheets().getValidateErrors();
	}
	
	protected void finish() {
		this.applyFinish();
	}
	
	protected abstract void applyData(ExcelSheet excelSheet);
	protected abstract void applyFinish();
	
}
