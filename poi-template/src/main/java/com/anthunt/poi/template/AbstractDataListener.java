package com.anthunt.poi.template;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.anthunt.poi.template.helper.PoiLogger;
import com.anthunt.poi.template.model.excel.ExcelColumn;

public abstract class AbstractDataListener {

	private static PoiLogger logger = PoiLogger.getLogger(AbstractDataListener.class);
	
	private List<ExcelColumn> excelColumns;
	private int sheetIdx;
	
	protected AbstractDataListener() {}
	
	protected List<ExcelColumn> getExcelColumns() {
		return new ArrayList<>(this.excelColumns);
	}

	protected void startHandle() {
		this.handleStart();
	}
	
	protected void handle(int sheetIdx, Sheet sheet, List<ExcelColumn> excelColumns) {
		this.excelColumns = excelColumns;
		this.sheetIdx = sheetIdx;
		this.handleSheet(sheet);
	}

	protected int getSheetIndex() {
		return this.sheetIdx;
	}
	
	protected void handle(Sheet sheet, Row row) {
		this.handleRow(sheet, row);
	}

	protected void handle(Sheet sheet, Row row, Cell cell) {
		this.handleCell(sheet, row, cell);
	}
	
	protected void endOfSheetHandle() {
		this.handleEndOfSheet();
	}

	protected void lastHandle() {
		this.handleLast();
	}

	protected void afterRowHandle(Sheet sheet, Row row) {
		this.handleAfterRow(sheet, row);
	}
	
	protected abstract void handleStart();
	protected abstract void handleSheet(Sheet sheet);
	protected abstract void handleRow(Sheet sheet, Row row);
	protected abstract void handleAfterRow(Sheet sheet, Row row);
	protected abstract void handleCell(Sheet sheet, Row row, Cell cell);
	protected abstract void handleEndOfSheet();
	protected abstract void handleLast();

	
}
