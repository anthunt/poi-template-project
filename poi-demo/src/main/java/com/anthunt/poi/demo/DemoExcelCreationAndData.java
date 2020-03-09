package com.anthunt.poi.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.anthunt.poi.template.AbstractDataProvider;
import com.anthunt.poi.template.PoiTemplate;
import com.anthunt.poi.template.exception.TemplateException;
import com.anthunt.poi.template.exception.ValidateException;
import com.anthunt.poi.template.helper.PoiLogger;
import com.anthunt.poi.template.model.excel.ExcelRow;
import com.anthunt.poi.template.model.excel.ExcelSheet;
import com.anthunt.poi.template.model.excel.ExcelSheets;
import com.anthunt.poi.template.model.excel.ValidationError;

public class DemoExcelCreationAndData {

	private static PoiLogger logger = PoiLogger.getLogger(DemoExcelCreationAndData.class);
	
	public static void main(String[] args) {
						
		try {
			
			logger.info("start DemoExcelCreationAndData.");
			
			ExcelSheets excelSheets = DemoExcelTemplate.getExcelSheets();
			logger.info("got ExcelSheets [sheet size = {}]", excelSheets.size());
			
			PoiTemplate poiTemplate = new PoiTemplate(excelSheets);	
			logger.info("PoiTemplate instance created with ExcelSheets.");
			
			poiTemplate.createTemplate(new AbstractDataProvider() {
				
				@Override
				protected void applyData(ExcelSheet excelSheet) {
					
					logger.info("applyData() [sheetName = {}]", this.getSheetName());
					
					if("TestSheet1".equals(this.getSheetName())) {
						logger.info("apply TestSheet1 data. start.");
						applyTestSheet1Data();
						logger.info("apply TestSheet1 data. finished.");
					}
					
				}
				
				private void applyTestSheet1Data() {
					
					for(int i = 0; i < 10; i++) {
						
						ExcelRow excelRow = this.getRow();
						
						int cellIndex = 0;
						
						this.setCellData(excelRow, cellIndex++, "홍길동" + i);
						this.setCellData(excelRow, cellIndex++, "대리");
						this.setCellData(excelRow, cellIndex++, "직접");
						this.setCellData(excelRow, cellIndex++, "제조팀");
						this.setCellData(excelRow, cellIndex++, "정규직");
						this.setCellData(excelRow, cellIndex++, "급여");
						this.setCellData(excelRow, cellIndex++, "기본급");
						this.setCellData(excelRow, cellIndex++, "1800000");
						this.setCellData(excelRow, cellIndex++, "1800000");
						this.setCellData(excelRow, cellIndex++, "1800000");
						this.setCellData(excelRow, cellIndex++, "1800000");
						this.setCellData(excelRow, cellIndex++, "1800000");
						this.setCellData(excelRow, cellIndex++, "1800000");
						this.setCellData(excelRow, cellIndex++, "1800000");
						this.setCellData(excelRow, cellIndex++, "1800000");
						this.setCellData(excelRow, cellIndex++, "1800000");
						this.setCellData(excelRow, cellIndex++, "1800000");
						this.setCellData(excelRow, cellIndex++, "1800000");
						this.setCellData(excelRow, cellIndex++, "1800000");
						this.setCellData(excelRow, cellIndex++); // FORMULA 컬럼은 값없이 입력 가능
						this.setCellData(excelRow, cellIndex++, "비고");
						logger.info("Row data inserted. [RowNum = {}]", excelRow.getRowNum());
					}
					
				}

				@Override
				protected void applyFinish() {}
				
			});
			logger.info("Excel Creation completed.");
			
			String excelFilePath = "test2.xlsx";
			poiTemplate.save(new FileOutputStream(new File(excelFilePath)));
			logger.info("Created Excel saved. [{}]", excelFilePath);
			
		} catch (ValidateException e) {	
			for (ValidationError validationError : e.getValidationErrors().getErrors()) {
				logger.error(validationError.toString());
			}
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}

}
