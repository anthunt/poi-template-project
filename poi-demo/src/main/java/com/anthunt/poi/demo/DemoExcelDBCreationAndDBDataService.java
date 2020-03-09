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
//import com.anthunt.poi.mapper.impl.BasicDataProvider;
//import com.anthunt.poi.mapper.model.AdditionalDatas;
//import com.anthunt.poi.mapper.model.AdditionalDatasBuilder;
//import com.anthunt.poi.mapper.model.UseType;
//import com.anthunt.poi.template.PoiTemplate;
//import com.anthunt.poi.template.exception.TemplateException;
//import com.anthunt.poi.template.exception.ValidateException;
//import com.anthunt.poi.template.helper.PoiLogger;
//import com.anthunt.poi.template.model.excel.ExcelSheets;
//import com.anthunt.poi.template.model.excel.ValidationError;
//import com.stw.framework.service.FrameworkService;
//
//@Service
//public class DemoExcelDBCreationAndDBDataService extends FrameworkService {
//
//	private static PoiLogger logger = PoiLogger.getLogger(DemoExcelDBCreationAndDBDataService.class);
//	
//	@Autowired
//    private DataSource dataSource;
//	
//	public void createExcelDemo() {
//
//		try {
//			
//			PoiDemoDAO poiDemoDAO = new PoiDemoDAO(dataSource, this.dao);
//			
//			PoiMapper poiMapper = new PoiMapper(poiDemoDAO);
//			
//			ExcelSheets excelSheets = poiMapper.mapping(1);
//			
//			PoiTemplate poiTemplate = new PoiTemplate(excelSheets);	
//			
//
//            String verKey = "001";
//            String bizYY = "2017";
//            String verID = "VER-001";
//            
//            AdditionalDatas additionalDatas = AdditionalDatasBuilder.newBuilder()
//                                                                    .addAdditionalColumns(1)
//                                                                       .setAdditionalColumn("CC_VER_KEY", "=", "001", UseType.ALL, true)
//                                                                       .setAdditionalColumn("NFS_BIZ_YY", "=", "2017", UseType.ALL, true)
//                                                                       .setAdditionalColumn("CC_VER_ID", "=", "VER-001", UseType.ALL, true)
//                                                                       .setAdditionalColumn("CRT_DT", "=", "SYSDATE", UseType.INSERT, false, false)
//                                                                       .setAdditionalColumn("MDFY_DT", "=", "SYSDATE", UseType.INSERT, false, false)
//                                                                       .and()
//                                                                    .addAdditionalColumns(2)
//                                                                       .setAdditionalColumn("CC_VER_KEY", "=", "001", UseType.ALL, true)
//                                                                       .setAdditionalColumn("NFS_BIZ_YY", "=", "2017", UseType.ALL, true)
//                                                                       .setAdditionalColumn("CC_VER_ID", "=", "VER-001", UseType.ALL, true)
//                                                                       .setAdditionalColumn("CRT_DT", "=", "SYSDATE", UseType.INSERT, false, false)
//                                                                       .setAdditionalColumn("MDFY_DT", "=", "SYSDATE", UseType.INSERT, false, false)
//                                                                       .and()
//                                                                    .addAdditionalColumns(3)
//                                                                       .setAdditionalColumn("CC_VER_KEY", "=", "001", UseType.ALL, true)
//                                                                       .setAdditionalColumn("NFS_BIZ_YY", "=", "2017", UseType.ALL, true)
//                                                                       .setAdditionalColumn("CC_VER_ID", "=", "VER-001", UseType.ALL, true)
//                                                                       .setAdditionalColumn("CRT_DT", "=", "SYSDATE", UseType.INSERT, false, false)
//                                                                       .setAdditionalColumn("MDFY_DT", "=", "SYSDATE", UseType.INSERT, false, false)
//                                                                       .and()
//                                                                    .build();
//		
//			poiTemplate.createTemplate(new BasicDataProvider(poiDemoDAO, additionalDatas));
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
