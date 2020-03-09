# README #

POI Template Generator Project

### What is this repository for? ###

* Quick summary
	POI 라이브러리를 사용한 엑셀 생성/읽기 작업을 지원하기 위한 라이브러리 프로젝트
* Version
	0.0.1

## Usage ##

### Java 코드로 Excel Template 생성하는 방법 ###
		
```java
package com.anthunt.poi.demo;

import org.apache.poi.ss.usermodel.CellType;

import com.anthunt.poi.template.model.enums.DBDataType;
import com.anthunt.poi.template.model.enums.NumericFormatType;
import com.anthunt.poi.template.model.excel.ExcelSheets;

public class DemoExcelTemplate {

	public static ExcelSheets getExcelSheets() {
	
		ExcelSheets excelSheets = new ExcelSheets();
		
		// 프로그램에서 일기 및 쓰기 제외할 Sheet 설정.
		excelSheets.builder().setSheetName("Skip Sheet").setSkipSheet(true).build();		
		excelSheets.builder()
					.setSheetName("TestSheet1")			// 엑셀 하단 Sheet 명 설정
					.setSheetTitle("01. Sheet Title")	// 엑셀 Sheet내 최상단 2번쨰 줄에 들어갈 제목 설정
					.setUseSampleData(true)				// 샘플데이터를 포함하여 생성할지 여부 설정
					.setExplainRowSize(1)				// 설명 줄 수
					.setHeaderRowSize(2)				// 헤더 줄 수
					.addExcelColumn()					// 컬럼 정보 추가
			       		.addHeaderColumn()				// 해당 컬럼의 헤더 컬럼 정보 추가
			       			.setValue("이름")			  // 헤더 컬럼 값 설정
							// 엑셀 셀 메모 추가
			                .setComment("만약 \"동명이인\" 이 있을경우 이름뒤에 숫자를 붙여 구분\nEx> ~ 중략 ~ 일) 로 구분\nEx>직접 6개월, 간접 6개월 근무한 경우: 홍길동(직), 홍길동(간)  으로 기재") 
			                .setRowMergeSize(2)			// 헤더 셀 머지 설정
			                .and()
			            .addExplainColumn()				// 컬럼 설명 컬럼 정보 추가
			            	.setValue("-셀서식(일반)\n-사업참여자명(필수항목)")	// 설명 값 설정
			            	.and()
			    		.setRequired(true)				// 필수 컬럼 여부 설정 - 필수 컬럼인 경우 헤더 명 위에 * 표시 됨.
			    		.setCellType(CellType.STRING)	// 컬럼 타입 설정
			    		.setSampleValue("홍길동")		 // 컬럼 샘플 값 설정
			    		.addDBColumn()				   // DB 컬럼 매핑 정보 설정
			    			.setTableName("TABLE_NAME1")	// 매핑 테이블 명 설정
			    			.setColumnName("COLUMN1")		// 메핑 컬럼 명 설정
			    			.setDataType(DBDataType.STRING)	// 매핑 컬럼 데이터 타입 설정
			    			.and()
			    		.and()
			    	.addExcelColumn()
			    		.addHeaderColumn()
			    			.setValue("구분2")
			    			.setComment("정규직/계약직으로 기재")
			    			.setRowMergeSize(2)
			    			.and()
			    		.setRequired(true)
			    		.setCellType(CellType.STRING)
			    		.setDataExplicits(new String[]{"정규직", "계약직"})	// 콤보 박스 선택 값 설정
			    		.setSampleValue("정규직")
			    		.addDBColumn()
			    			.setTableName("TABLE_NAME1")
			    			.setColumnName("COLUMN5")
			    			.setDataType(DBDataType.STRING)
			    			.and()
			    		.and()
			    	.addExcelColumn()
			    		.addHeaderColumn()
			    			.setValue("급여대장")
			    			.setCellMergeSize(15)	// 헤더 컬럼 셀 머지 설정
			    			.setSkipRequired(true)	// 필수 컬럼인 경우 헤더 컬럼에 * 표시를 생략할지 여부 설정
			    			.and()
			    		.addHeaderColumn()
			    			.setValue("구분3")
			    			.and()
			    		.setRequired(true)
			    		.setCellType(CellType.STRING)
			    		.setDataExplicits(new String[]{"급여", "상여"})
			    		.setSampleValue("급여")
			    		.addDBColumn()
			    			.setTableName("TABLE_NAME1")
			    			.setColumnName("COLUMN6")
			    			.setDataType(DBDataType.STRING)
			    			.and()
			    		.and()
						
					~~~~~~~~~~~~~~~~~~~~~~~~~~~ 중 략 ~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
			    	.addExcelColumn()
			    		.addHeaderColumn()
			    			.setValue("")
			    			.and()
			    		.addHeaderColumn()
			    			.setValue("소계")
			    			.and()
			    		.setRequired(false)
			    		.setCellType(CellType.FORMULA)
			    		.setNumericFormatType(NumericFormatType.INTEGER)	// 숫자 형식 컬럼이 경우 숫자 형식 타입 설정
			    		.setColumnFormula("SUM({COL8}{ROW}:{COL19}{ROW})")	// 계산식 컬럼 인 경우 계산 식 설정
			    		.and()
			    	.addExcelColumn()
			    		.addHeaderColumn()
			    			.setValue("비고")
			    			.setRowMergeSize(2)
			    			.and()
			    		.setRequired(false)
			    		.setCellType(CellType.STRING)
			    		.setSampleValue("비고")
			    		.addDBColumn()
			    			.setTableName("TABLE_NAME1")
			    			.setColumnName("COLUMN10")
			    			.setDataType(DBDataType.STRING)
			    			.and()
			    		.and()
			    	.build();	
		
		return excelSheets;
	}
	
}
```

### Excel 파일 생성 ###
```java
package com.anthunt.poi.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.anthunt.poi.template.PoiTemplate;
import com.anthunt.poi.template.exception.ValidateException;
import com.anthunt.poi.template.model.excel.ExcelSheets;

public class DemoExcelCreation {

	public static void main(String[] args) throws InvalidFormatException, IOException {
		
		ExcelSheets excelSheets = DemoExcelTemplate.getExcelSheets();
		
		PoiTemplate poiTemplate = new PoiTemplate(excelSheets);	
		
		try {
			poiTemplate.createTemplate();
		} catch (ValidateException e) {
			e.printStackTrace();
			e.getValidateErrorMessages();
		}
		poiTemplate.save(new FileOutputStream(new File("test2.xlsx")));
				
	}
	
}
```

### 데이터를 포함하여 Excel 파일 생성 ###
```java
package com.anthunt.poi.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.anthunt.poi.template.PoiTemplate;
import com.anthunt.poi.template.exception.ValidateException;
import com.anthunt.poi.template.model.excel.AbstractDataProvider;
import com.anthunt.poi.template.model.excel.ExcelRow;
import com.anthunt.poi.template.model.excel.ExcelSheet;
import com.anthunt.poi.template.model.excel.ExcelSheets;

public class DemoExcelCreationAndData {

public static void main(String[] args) throws InvalidFormatException, IOException {
		
		ExcelSheets excelSheets = DemoExcelTemplate.getExcelSheets();
		
		PoiTemplate poiTemplate = new PoiTemplate(excelSheets);	
		
		try {
			
			poiTemplate.createTemplate(new AbstractDataProvider() {
				
				@Override
				protected void applyData(ExcelSheet excelSheet) {
					
					if("TestSheet1".equals(this.getSheetName())) {
						applyTestSheet1Data();
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
						
					}
					
				}

				@Override
				protected void applyFinish() {}
				
			});
			
		} catch (ValidateException e) {	
			for (String errorMessage : e.getValidateErrorMessages()) {
				System.err.println(errorMessage);
			}
			e.printStackTrace();
		}
		
		poiTemplate.save(new FileOutputStream(new File("test2.xlsx")));
				
	}

}
```

### Excel 파일 데이터 읽어오기 ###
```java
package com.anthunt.poi.demo;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.anthunt.poi.template.PoiTemplate;
import com.anthunt.poi.template.model.excel.ExcelSheets;
import com.anthunt.poi.template.model.excel.AbstractDataListener;
import com.anthunt.poi.template.model.excel.ExcelColumn;

public class DemoExcelRead {

	public static void main(String[] args) throws InvalidFormatException, IOException {
		
		ExcelSheets excelSheets = DemoExcelTemplate.getExcelSheets();
		
		PoiTemplate poiTemplate = new PoiTemplate(excelSheets, "test2.xlsx");
		boolean isValidExcel = poiTemplate.readTemplate(new AbstractDataListener() {

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
				
				System.out.println("[" + cell.getRowIndex() + ", " + cell.getColumnIndex() + "] " + cellValue);
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
		
		
		if(!isValidExcel) {
			List<String> errorMessages = excelSheets.getValidateErrorMessages();
			for (String errorMessage : errorMessages) {
				System.out.println(errorMessage);
			}
		}
		
	}
	
}
```

### DB 메타정보를 이용하기 위한 DAO 클래스 ###
```java
package com.anthunt.poi.demo.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.anthunt.poi.mapper.dao.AbstractMapperDAO;
import com.anthunt.poi.mapper.model.CellDefinition;
import com.anthunt.poi.mapper.model.ColumnConstraintDefinition;
import com.anthunt.poi.mapper.model.ColumnDefinition;
import com.anthunt.poi.mapper.model.DAOParams;
import com.anthunt.poi.mapper.model.DBColumnDefinition;
import com.anthunt.poi.mapper.model.SheetDefinition;

public class PoiDemoDAO extends AbstractMapperDAO {

	private SqlSession sqlSession;
	private static String MAPPER_NAMESPACE = "com.anthunt.poi.demo.db.PoiDemoDAO";
	
	public PoiDemoDAO() {
		try {
			InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			this.sqlSession = sqlSessionFactory.openSession(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getMapper(String mapperId) {
		return MAPPER_NAMESPACE + "." + mapperId;
	}

	@Override
	protected Connection applyConnection() {
		return this.sqlSession.getConnection();
	}

	@Override
	protected List<SheetDefinition> applySheetDefinitions(DAOParams daoParams) {
		return this.sqlSession.selectList(this.getMapper("getSheets"), daoParams.getExcelIdx());
	}

	@Override
	protected List<ColumnDefinition> applyColumnDefinitions(DAOParams daoParams) {
		return this.sqlSession.selectList(this.getMapper("getColumns"), daoParams);
	}

	@Override
	protected ColumnConstraintDefinition applyColumnConstraintDefinition(DAOParams daoParams) {
		return this.sqlSession.selectOne(this.getMapper("getColumnConst"), daoParams);
	}

	@Override
	protected List<CellDefinition> applyExplainCellDefinitions(DAOParams daoParams) {
		return this.sqlSession.selectList(this.getMapper("getExplains"), daoParams);
	}

	@Override
	protected List<CellDefinition> applyHeaderCellDefinitions(DAOParams daoParams) {
		return this.sqlSession.selectList(this.getMapper("getHeaders"), daoParams);
	}

	@Override
	protected List<DBColumnDefinition> applyDBColumnDefinitions(DAOParams daoParams) {
		return this.sqlSession.selectList(this.getMapper("getDBColumns"), daoParams);
	}
	
}
```

### DB 메타정보 Query XML ###
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
	PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.anthunt.poi.demo.db.PoiDemoDAO">

	<resultMap id="sheetResultMap" type="com.anthunt.poi.mapper.model.SheetDefinition">
		<result column="SHEET_IDX" property="sheetIndex"/>
		<result column="SHEET_NM" property="sheetName"/>
		<result column="SHEET_TITLE" property="sheetTitle"/>
		<result column="SKIP_YN" property="skipSheet"/>
		<result column="USE_SAMPLE_DATA_YN" property="useSampleData"/>
		<result column="EXPLAIN_SIZE" property="explainRowSize"/>
		<result column="HEADER_SIZE" property="headerRowSize"/>
	</resultMap>
	
	<resultMap id="columnResultMap" type="com.anthunt.poi.mapper.model.ColumnDefinition">
		<result column="REQUIRED_YN" property="isRequired"/>                        
		<result column="SAMPLE_VALUE" property="sampleValue"/>
		<result column="DATA_CELL_TP" property="dataCellType"/>
		<result column="NUMERIC_FORMAT_TP" property="numericFormatType"/>
		<result column="COLUMN_FORMULA" property="columnFormula"/>
	</resultMap>
	
	<resultMap id="columnConstResultMap" type="com.anthunt.poi.mapper.model.ColumnConstraintDefinition">
		<result column="MIN" property="min"/>
		<result column="MAX" property="max"/>
		<result column="REG_EXP" property="regExp"/>
	</resultMap>
	
	<resultMap id="explainCellResultMap" type="com.anthunt.poi.mapper.model.CellDefinition">
	    <result column="EXPLAIN_VAL" property="value"/>
	    <result column="EXPLAIN_CMNT" property="comment"/>
	    <result column="ROW_MERGE_SIZE" property="rowMergeSize"/>
	    <result column="CELL_MERGE_SIZE" property="cellMergeSize"/>
	</resultMap>
	
	<resultMap id="headerCellResultMap" type="com.anthunt.poi.mapper.model.CellDefinition">
		<result column="HEADER_VAL" property="value"/>
	    <result column="HEADER_CMNT" property="comment"/>
	    <result column="ROW_MERGE_SIZE" property="rowMergeSize"/>
	    <result column="CELL_MERGE_SIZE" property="cellMergeSize"/>
	    <result column="SKIP_REQUIRED_YN" property="skipRequired"/>
	</resultMap>
	
	<resultMap id="dbColumnResultMap" type="com.anthunt.poi.mapper.model.DBColumnDefinition">
		<result column="TABLE_NM" property="tableName"/>
		<result column="COLUMN_NM" property="columnName"/>
		<result column="DATA_TP" property="dataType"/>
		<result column="PIVOT_COL_YN" property="isPivot"/>
		<result column="PIVOT_COLUMN_NM" property="pivotColumnName"/>
		<result column="PIVOT_COLUMN_VAL" property="pivotColumnValue"/>
	</resultMap>
	
	<select id="getSheets" parameterType="int" resultMap="sheetResultMap">
	<![CDATA[
		SELECT X.SHEET_IDX
		     , X.SHEET_NM
		     , X.SHEET_TITLE
		     , CASE X.SKIP_YN WHEN 'Y' THEN 'true'
		                    ELSE 'false'
			   END AS SKIP_YN
		     , CASE X.USE_SAMPLE_DATA_YN WHEN 'Y' THEN 'true'
		                               ELSE 'N'
			   END AS USE_SAMPLE_DATA_YN
			 , MAX(Y.CNT) AS EXPLAIN_SIZE
		     , MAX(Z.CNT) AS HEADER_SIZE
		FROM   SCMS_EX_SHEETS X
		     LEFT JOIN (
		     	SELECT SHEET_IDX
		             , COUNT('X') CNT
		        FROM   SCMS_EX_EXPLAINS
		        WHERE  EXCEL_IDX = #{excelIdx}
		        GROUP BY EXCEL_IDX, SHEET_IDX, COLUMN_IDX) Y
		        ON ( Y.SHEET_IDX = X.SHEET_IDX )
			 LEFT JOIN (
			 	SELECT SHEET_IDX
		             , COUNT('X') CNT
		        FROM   SCMS_EX_HEADERS
		        WHERE  EXCEL_IDX = #{excelIdx}
		        GROUP BY EXCEL_IDX, SHEET_IDX, COLUMN_IDX) Z
		        ON ( Z.SHEET_IDX = X.SHEET_IDX )
		WHERE  X.EXCEL_IDX = #{excelIdx}
		GROUP BY X.SHEET_IDX, X.SHEET_NM, X.SHEET_TITLE, X.SKIP_YN, X.USE_SAMPLE_DATA_YN
		ORDER BY X.SHEET_IDX ASC
	]]>
	</select>
	
	<select id="getColumns" parameterType="com.anthunt.poi.mapper.model.DAOParams" resultMap="columnResultMap">
	<![CDATA[
		SELECT COLUMN_IDX
		     , CASE REQUIRED_YN WHEN 'Y' THEN 'true'
								ELSE 'N'
			   END AS REQUIRED_YN                        
		     , SAMPLE_VALUE
		     , DATA_CELL_TP
		     , NUMERIC_FORMAT_TP
		     , COLUMN_FORMULA
		FROM   SCMS_EX_COLUMNS
		WHERE  EXCEL_IDX = #{excelIdx}
		AND    SHEET_IDX = #{sheetIdx}
		ORDER BY COLUMN_IDX ASC
	]]>
	</select>
	
	<select id="getColumnConst" parameterType="com.anthunt.poi.mapper.model.DAOParams" resultMap="columnConstResultMap">
	<![CDATA[
		SELECT MIN
		     , MAX
		     , REG_EXP
		FROM   SCMS_EX_COLUMN_CONST
		WHERE  EXCEL_IDX = #{excelIdx}
		AND    SHEET_IDX = #{sheetIdx}
		AND    COLUMN_IDX = #{columnIdx}
	]]>
	</select>
	
	<select id="getExplains" parameterType="com.anthunt.poi.mapper.model.DAOParams" resultMap="explainCellResultMap">
	<![CDATA[
		SELECT EXPLAIN_IDX
		     , EXPLAIN_VAL
		     , EXPLAIN_CMNT
		     , ROW_MERGE_SIZE
		     , CELL_MERGE_SIZE
		FROM   SCMS_EX_EXPLAINS
		WHERE  EXCEL_IDX = #{excelIdx}
		AND    SHEET_IDX = #{sheetIdx}
		AND    COLUMN_IDX = #{columnIdx}
		ORDER BY EXPLAIN_IDX ASC
	]]>
	</select>
	
	<select id="getHeaders" parameterType="com.anthunt.poi.mapper.model.DAOParams" resultMap="headerCellResultMap">
	<![CDATA[
		SELECT HEADER_IDX
		     , HEADER_VAL
		     , HEADER_CMNT
		     , ROW_MERGE_SIZE
		     , CELL_MERGE_SIZE
		     , CASE SKIP_REQUIRED_YN WHEN 'Y' THEN 'true'
								ELSE 'N'
			   END AS SKIP_REQUIRED_YN
		FROM   SCMS_EX_HEADERS
		WHERE  EXCEL_IDX = #{excelIdx}
		AND    SHEET_IDX = #{sheetIdx}
		AND    COLUMN_IDX = #{columnIdx}
		ORDER BY HEADER_IDX ASC
	]]>
	</select>
	
	<select id="getDBColumns" parameterType="com.anthunt.poi.mapper.model.DAOParams" resultMap="dbColumnResultMap">
	<![CDATA[
		SELECT TABLE_NM
		     , COLUMN_NM
		     , DATA_TP
		     , CASE PIVOT_COL_YN WHEN 'Y' THEN 'true'
								ELSE 'N'
			   END AS PIVOT_COL_YN
			 , PIVOT_COLUMN_NM
             , PIVOT_COLUMN_VAL
		FROM   SCMS_EX_DB_COLUMNS
		WHERE  EXCEL_IDX = #{excelIdx}
		AND    SHEET_IDX = #{sheetIdx}
		AND    COLUMN_IDX = #{columnIdx}
		ORDER BY TABLE_NM, COLUMN_IDX ASC
	]]>
	</select>
</mapper>
```

### DB 메타정보를 이용해서 Excel 파일 양식 생성 ###
```java
package com.anthunt.poi.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.anthunt.poi.demo.db.PoiDemoDAO;
import com.anthunt.poi.mapper.PoiMapper;
import com.anthunt.poi.template.PoiTemplate;
import com.anthunt.poi.template.exception.ValidateException;
import com.anthunt.poi.template.model.excel.ExcelSheets;

public class DemoExcelFromDBCreation {

	public static void main(String[] args) throws InvalidFormatException, IOException {
		
		PoiDemoDAO poiDemoDAO = new PoiDemoDAO();
		
		PoiMapper poiMapper = new PoiMapper(poiDemoDAO);
		
		ExcelSheets excelSheets = poiMapper.mapping(1);
		
		PoiTemplate poiTemplate = new PoiTemplate(excelSheets);	
		
		try {
			poiTemplate.createTemplate();
		} catch (ValidateException e) {
			e.printStackTrace();
			e.getValidateErrorMessages();
		}
		poiTemplate.save(new FileOutputStream(new File("test2.xlsx")));
		
	}
	
}
```

### DB 메타 데이터를 이용하여 Excel을 생성하고 DB의 데이터를 포함하여 생성하기 ###
```java
package com.anthunt.poi.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.anthunt.poi.demo.db.PoiDemoDAO;
import com.anthunt.poi.mapper.PoiMapper;
import com.anthunt.poi.mapper.impl.BasicDataProvider;
import com.anthunt.poi.template.PoiTemplate;
import com.anthunt.poi.template.exception.ValidateException;
import com.anthunt.poi.template.model.excel.ExcelSheets;

public class DemoExcelDBCreationAndDBData {

	public static void main(String[] args) throws InvalidFormatException, IOException {

		PoiDemoDAO poiDemoDAO = new PoiDemoDAO();
		
		PoiMapper poiMapper = new PoiMapper(poiDemoDAO);
		
		ExcelSheets excelSheets = poiMapper.mapping(1);
		
		PoiTemplate poiTemplate = new PoiTemplate(excelSheets);	
		
		try {
			poiTemplate.createTemplate(new BasicDataProvider(poiDemoDAO));
			
		} catch (ValidateException e) {
			e.printStackTrace();
			e.getValidateErrorMessages();
		}
		poiTemplate.save(new FileOutputStream(new File("test2.xlsx")));
		
	}
	
}
```

### DB 메타 데이터 양식에 맞는 Excel 파일 읽어서 DB에 저장하기 ###
```java
package com.anthunt.poi.demo;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.anthunt.poi.demo.db.PoiDemoDAO;
import com.anthunt.poi.mapper.PoiMapper;
import com.anthunt.poi.mapper.impl.BasicDataListener;
import com.anthunt.poi.template.PoiTemplate;
import com.anthunt.poi.template.model.excel.ExcelSheets;

public class DemoExcelReadAndDBSave {
	
	public static void main(String[] args) throws InvalidFormatException, IOException {
		
		PoiDemoDAO poiDemoDAO = new PoiDemoDAO();
		
		PoiMapper poiMapper = new PoiMapper(poiDemoDAO);
		
		ExcelSheets excelSheets = poiMapper.mapping(1);
				
		PoiTemplate poiTemplate = new PoiTemplate(excelSheets, "test2.xlsx");
		boolean isValidExcel = poiTemplate.readTemplate(new BasicDataListener(poiDemoDAO.getConnection()));
		
		
		if(!isValidExcel) {
			List<String> errorMessages = excelSheets.getValidateErrorMessages();
			for (String errorMessage : errorMessages) {
				System.out.println(errorMessage);
			}
		}
		
	}
	
}
```