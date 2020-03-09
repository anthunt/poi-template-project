//package com.anthunt.poi.demo;
//
//import java.io.IOException;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.anthunt.poi.demo.db.PoiDemoDAO;
//import com.anthunt.poi.mapper.PoiMapper;
//import com.anthunt.poi.mapper.impl.BasicDataListener;
//import com.anthunt.poi.mapper.model.AdditionalDatas;
//import com.anthunt.poi.mapper.model.AdditionalDatasBuilder;
//import com.anthunt.poi.mapper.model.QueryType;
//import com.anthunt.poi.mapper.model.UseType;
//import com.anthunt.poi.template.PoiTemplate;
//import com.anthunt.poi.template.exception.TemplateException;
//import com.anthunt.poi.template.exception.ValidateException;
//import com.anthunt.poi.template.helper.PoiLogger;
//import com.anthunt.poi.template.model.excel.ExcelSheets;
//import com.anthunt.poi.template.model.excel.ValidationError;
//import com.stw.framework.service.FrameworkService;
//
///**
// * <b>DB로 부터 엑셀 템플릿 양식 정보를 가져와 저장된 엑셀 파일을 읽어 매핑된 DB 테이블에 데이터를 저장하는 샘플</b>
// * 
// * @author anthunt
// *
// */
//@Service
//public class DemoExcelReadAndDBSaveService extends FrameworkService {
//	
//	private static PoiLogger logger = PoiLogger.getLogger(DemoExcelReadAndDBSaveService.class);
//	
//	private PoiDemoDAO poiDemoDAO;
//
//    @Autowired
//    private DataSource dataSource;
//    
//	public void readAndSave() {
//				
//		try {
//			
//			// DB 사용을 위한 DAO 인스턴스 생성
//			PoiDemoDAO poiDemoDAO = new PoiDemoDAO(dataSource, this.dao);
//			
//			// DB로부터 가져온 정보를 PoiTemplate에 매핑하기 위한 PoiMapper 인스턴스 생성
//			PoiMapper poiMapper = new PoiMapper(poiDemoDAO);
//			
//			// PoiMapper 인스턴스를 통해 ExcelSheets 인스턴스 생성
//			ExcelSheets excelSheets = poiMapper.mapping(1);
//					
//			// 엑셀 양식에 설정되지 않은 추가 컬럼 정보 생성
//			//
//			// 추가 컬럼 타입 정보
//			// UseType.SEARCH - 추가 조회 조건 형식 (데이터 파일 생성 시 사용 됨)
//			// UseType.INSERT - 데이터 DB 저장 시 추가 저장된 컬럼 데이터 형식
//			// UseType.ALL    - 조회/저장 시 모두 사용 될 형식
//			//
//			
//			String verKey = "001";
//			String bizYY = "2017";
//			String verID = "VER-001";
//			
//			AdditionalDatas additionalDatas = AdditionalDatasBuilder.newBuilder()
//																	 .addAdditionalColumns(1)
//																	 	.setAdditionalColumn("CC_VER_KEY", "=", "001", UseType.ALL, true)
//																	 	.setAdditionalColumn("NFS_BIZ_YY", "=", "2017", UseType.ALL, true)
//																	 	.setAdditionalColumn("CC_VER_ID", "=", "VER-001", UseType.ALL, true)
//																	 	.setAdditionalColumn("CRT_DT", "=", "SYSDATE", UseType.INSERT, false, false)
//																	 	.setAdditionalColumn("MDFY_DT", "=", "SYSDATE", UseType.INSERT, false, false)
//																	 	.and()
//																	 .addAdditionalColumns(2)
//                                                                        .setAdditionalColumn("CC_VER_KEY", "=", "001", UseType.ALL, true)
//                                                                        .setAdditionalColumn("NFS_BIZ_YY", "=", "2017", UseType.ALL, true)
//                                                                        .setAdditionalColumn("CC_VER_ID", "=", "VER-001", UseType.ALL, true)
//                                                                        .setAdditionalColumn("CRT_DT", "=", "SYSDATE", UseType.INSERT, false, false)
//                                                                        .setAdditionalColumn("MDFY_DT", "=", "SYSDATE", UseType.INSERT, false, false)
//                                                                        .and()
//                                                                     .addAdditionalColumns(3)
//                                                                        .setAdditionalColumn("CC_VER_KEY", "=", "001", UseType.ALL, true)
//                                                                        .setAdditionalColumn("NFS_BIZ_YY", "=", "2017", UseType.ALL, true)
//                                                                        .setAdditionalColumn("CC_VER_ID", "=", "VER-001", UseType.ALL, true)
//                                                                        .setAdditionalColumn("CRT_DT", "=", "SYSDATE", UseType.INSERT, false, false)
//                                                                        .setAdditionalColumn("MDFY_DT", "=", "SYSDATE", UseType.INSERT, false, false)
//                                                                        .and()
//																	 .build();
//			
//			// PoiTemplate 인스턴스 생성
//			PoiTemplate poiTemplate = new PoiTemplate(excelSheets, "D:/test2.xlsx");
//			
//			// 엑셀 파일을 읽어와서 DB에 저장
//			poiTemplate.readTemplate(new BasicDataListener(poiDemoDAO.getConnection(), additionalDatas, QueryType.MERGE));
//			
//		} catch (ValidateException e) {
//			e.printStackTrace();
//			// Excel Validation 에러 메세지 출력
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
