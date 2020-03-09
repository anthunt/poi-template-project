package com.anthunt.poi.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.anthunt.poi.template.PoiTemplate;
import com.anthunt.poi.template.exception.TemplateException;
import com.anthunt.poi.template.exception.ValidateException;
import com.anthunt.poi.template.helper.PoiLogger;
import com.anthunt.poi.template.model.excel.ExcelSheets;
import com.anthunt.poi.template.model.excel.ValidationError;

public class DemoExcelCreation {

	private static PoiLogger logger = PoiLogger.getLogger(DemoExcelCreation.class);
	
	public static void main(String[] args) {
				
		try {
			
			logger.info("start DemoExcelCreation.");
			
			ExcelSheets excelSheets = DemoExcelTemplate.getExcelSheets();
			logger.info("got ExcelSheets [sheet size = {}]", excelSheets.size());
			
			PoiTemplate poiTemplate = new PoiTemplate(excelSheets);	
			logger.info("PoiTemplate instance created with ExcelSheets.");
			
			poiTemplate.createTemplate();
			logger.info("Excel Creation completed.");
			
			String excelFilePath = "test2.xlsx";
			poiTemplate.save(new FileOutputStream(new File(excelFilePath)));
			logger.info("Created Excel saved. [{}]", excelFilePath);
			
		} catch (ValidateException e) {
			e.printStackTrace();
			for (ValidationError validationError : e.getValidationErrors().getErrors()) {
				logger.error(validationError.toString());
			}
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
				
	}
	
}
