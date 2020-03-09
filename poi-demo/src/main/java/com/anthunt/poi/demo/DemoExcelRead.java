package com.anthunt.poi.demo;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.anthunt.poi.template.AbstractDataListener;
import com.anthunt.poi.template.PoiTemplate;
import com.anthunt.poi.template.exception.TemplateException;
import com.anthunt.poi.template.exception.ValidateException;
import com.anthunt.poi.template.helper.PoiLogger;
import com.anthunt.poi.template.model.excel.ExcelSheets;
import com.anthunt.poi.template.model.excel.ValidationError;
import com.anthunt.poi.template.model.excel.ExcelColumn;

public class DemoExcelRead {

	private static PoiLogger logger = PoiLogger.getLogger(DemoExcelRead.class);
	
	public static void main(String[] args) {
				
		try {
			
			ExcelSheets excelSheets = DemoExcelTemplate.getExcelSheets();
			
			PoiTemplate poiTemplate = new PoiTemplate(excelSheets, "test2.xlsx");
			
			poiTemplate.readTemplate(new AbstractDataListener() {
	
				@Override
				public void handleStart() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void handleSheet(Sheet sheet) {
									
				}
	
				@Override
				public void handleRow(Sheet sheet, Row row) {
					
				}
	
				@Override
				public void handleCell(Sheet sheet, Row row, Cell cell) {
					
					String cellValue = "";
					ExcelColumn excelColumn = this.getExcelColumns().get(cell.getColumnIndex());
					
					CellType cellType = excelColumn.getDataCellType();
					
					switch (cellType) {
						case BOOLEAN:
							cellValue = cell.getBooleanCellValue() ? "Y" : "N";
							break;
						case NUMERIC:
							cellValue = Double.toString(cell.getNumericCellValue());
							break;
						case STRING:
							cellValue = cell.getStringCellValue();
							break;
						case FORMULA:
							cellValue = cell.getCellFormula();
							break;
						default:
							cellValue = cell.getRichStringCellValue().getString();
							break;
					}
					
					logger.info("[{}, {}] {}", cell.getRowIndex(), cell.getColumnIndex(), cellValue);
				}
	
				@Override
				public void handleEndOfSheet() {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void handleLast() {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void handleAfterRow(Sheet sheet, Row row) {
					// TODO Auto-generated method stub
					
				}
				
			});
		} catch (ValidateException e) {
			e.printStackTrace();
			for (ValidationError validationError : e.getValidationErrors().getErrors()) {
				logger.error(validationError.toString());
			}
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
	}
	
}
