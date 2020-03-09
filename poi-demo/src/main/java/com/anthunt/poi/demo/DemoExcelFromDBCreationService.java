//package com.anthunt.poi.demo;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.anthunt.poi.demo.db.PoiDemoDAO;
//import com.anthunt.poi.mapper.PoiMapper;
//import com.anthunt.poi.template.PoiTemplate;
//import com.anthunt.poi.template.exception.TemplateException;
//import com.anthunt.poi.template.exception.ValidateException;
//import com.anthunt.poi.template.helper.PoiLogger;
//import com.anthunt.poi.template.model.excel.ExcelSheets;
//import com.anthunt.poi.template.model.excel.ValidationError;
//import com.stw.framework.service.FrameworkService;
//
//@Service
//public class DemoExcelFromDBCreationService extends FrameworkService {
//
//	private static PoiLogger logger = PoiLogger.getLogger(DemoExcelFromDBCreationService.class);
//
//    @Autowired
//    private DataSource dataSource;
//    
//	public void createExcel() {
//		
//		try {
//			
//			PoiDemoDAO poiDemoDAO = new PoiDemoDAO(dataSource, this.dao);
//			
//			PoiMapper poiMapper = new PoiMapper(poiDemoDAO);
//			
//			ExcelSheets excelSheets = poiMapper.mapping(1, true);
//			
//			PoiTemplate poiTemplate = new PoiTemplate(excelSheets);		
//		
//			poiTemplate.createTemplate();
//			
//			poiTemplate.save(new FileOutputStream(new File("D:/test2.xlsx")));
//			
//		} catch (ValidateException e) {
//			e.printStackTrace();
//			for (ValidationError validationError : e.getValidationErrors().getErrors()) {
//				logger.error(validationError.toString());
//			}
//		} catch (TemplateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}		
//		
//	}
//	
//}
