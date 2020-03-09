package com.anthunt.poi.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.anthunt.poi.demo.db.PoiDemoDAO;
import com.anthunt.poi.mapper.PoiMapper;
import com.anthunt.poi.mapper.impl.BasicDataProvider;
import com.anthunt.poi.mapper.model.UseType;
import com.anthunt.poi.mapper.model.AdditionalDatas;
import com.anthunt.poi.mapper.model.AdditionalDatasBuilder;
import com.anthunt.poi.template.PoiTemplate;
import com.anthunt.poi.template.exception.TemplateException;
import com.anthunt.poi.template.exception.ValidateException;
import com.anthunt.poi.template.helper.PoiLogger;
import com.anthunt.poi.template.model.excel.ExcelSheets;
import com.anthunt.poi.template.model.excel.ValidationError;

public class DemoExcelDBCreationAndDBData {

	private static PoiLogger logger = PoiLogger.getLogger(DemoExcelDBCreationAndDBData.class);
	
	public static void main(String[] args) {

		try {
			
			PoiDemoDAO poiDemoDAO = new PoiDemoDAO();
			
			PoiMapper poiMapper = new PoiMapper(poiDemoDAO);
			
			ExcelSheets excelSheets = poiMapper.mapping(1);
			
			PoiTemplate poiTemplate = new PoiTemplate(excelSheets);	
			
			AdditionalDatas additionalDatas = AdditionalDatasBuilder.newBuilder()
													 .addAdditionalColumns(1)
													 	.setAdditionalColumn("ID", "=", "1", UseType.ALL, false)
													 	.and()
													 .build();
		
			poiTemplate.createTemplate(new BasicDataProvider(poiDemoDAO, additionalDatas));

			poiTemplate.save(new FileOutputStream(new File("test2.xlsx")));
			
		} catch (ValidateException e) {
			e.printStackTrace();
			for (ValidationError validationError : e.getValidationErrors().getErrors()) {
				logger.error(validationError.toString());
			}
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
