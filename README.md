# README #

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d596857a18ca4eed8dee7be997fb559d)](https://app.codacy.com/manual/anthunt01/poi-template-project?utm_source=github.com&utm_medium=referral&utm_content=anthunt/poi-template-project&utm_campaign=Badge_Grade_Dashboard)
[![Build Status](https://travis-ci.org/anthunt/poi-template-project.svg?branch=master)](https://travis-ci.org/anthunt/poi-template-project)

POI Template Generator Project

### What is this repository for? ###

* Quick summary
	Library project to support Excel creation / reading using POI library
* Version
	0.0.1

## Usage ##

### How to create Excel Template with Java code ###
		
```java
package com.anthunt.poi.demo;

import org.apache.poi.ss.usermodel.CellType;

import com.anthunt.poi.template.model.enums.DBDataType;
import com.anthunt.poi.template.model.enums.NumericFormatType;
import com.anthunt.poi.template.model.excel.ExcelSheets;

public class DemoExcelTemplate {

	public static ExcelSheets getExcelSheets() {
	
		ExcelSheets excelSheets = new ExcelSheets();
		
		// Sheet setting to exclude reading and writing from the program.
		excelSheets.builder().setSheetName("Skip Sheet").setSkipSheet(true).build();		
		excelSheets.builder()
					.setSheetName("TestSheet1")			// Sheet name setting at the bottom of Excel
					.setSheetTitle("01. Sheet Title")	// Set the title to enter the top 2 line of Excel Sheet
					.setUseSampleData(true)				// Set whether to generate including sample data
					.setExplainRowSize(1)				// Row size of comments
					.setHeaderRowSize(2)				// Row size of header
					.addExcelColumn()					// Add column information
			       		.addHeaderColumn()				// Add header column information of the column
			       			.setValue("Name")			  // Header column value setting
							// Add Excel cell notes
			                .setComment("set name") 
			                .setRowMergeSize(2)			// Header cell merge settings
			                .and()
			            .addExplainColumn()				// Column Description Add column information
			            	.setValue("-Cell Format (General) \ n-Business Participant Name (Required)")	// Setting Description Value
			            	.and()
			    		.setRequired(true)				// Required column setting-In case of required column, * is displayed above the header name.
			    		.setCellType(CellType.STRING)	// Column type setting
			    		.setSampleValue("Hong Gil Dong")		 // Setting column sample values
			    		.addDBColumn()				   // DB column mapping information setting
			    			.setTableName("TABLE_NAME1")	// Mapping table name setting
			    			.setColumnName("COLUMN1")		// Mapping column name setting
			    			.setDataType(DBDataType.STRING)	// Mapping column data type setting
			    			.and()
			    		.and()
			    	.addExcelColumn()
			    		.addHeaderColumn()
			    			.setValue("Category 2")
			    			.setComment("Listed as Full-time / contracted")
			    			.setRowMergeSize(2)
			    			.and()
			    		.setRequired(true)
			    		.setCellType(CellType.STRING)
			    		.setDataExplicits(new String[]{"Full-time", "contracted"})	// Set combo box selection value
			    		.setSampleValue("Full-time")
			    		.addDBColumn()
			    			.setTableName("TABLE_NAME1")
			    			.setColumnName("COLUMN5")
			    			.setDataType(DBDataType.STRING)
			    			.and()
			    		.and()
			    	.addExcelColumn()
			    		.addHeaderColumn()
			    			.setValue("Payroll")
			    			.setCellMergeSize(15)	// Header column cell merge settings
			    			.setSkipRequired(true)	// Whether to omit the * mark in the header column if it is a required column
			    			.and()
			    		.addHeaderColumn()
			    			.setValue("Category 3")
			    			.and()
			    		.setRequired(true)
			    		.setCellType(CellType.STRING)
			    		.setDataExplicits(new String[]{"salary", "Bonus"})
			    		.setSampleValue("salary")
			    		.addDBColumn()
			    			.setTableName("TABLE_NAME1")
			    			.setColumnName("COLUMN6")
			    			.setDataType(DBDataType.STRING)
			    			.and()
			    		.and()
						
					~~~~~~~~~~~~~~~~~~~~~~~~~~~ Shorten ~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
			    	.addExcelColumn()
			    		.addHeaderColumn()
			    			.setValue("")
			    			.and()
			    		.addHeaderColumn()
			    			.setValue("Sub Total")
			    			.and()
			    		.setRequired(false)
			    		.setCellType(CellType.FORMULA)
			    		.setNumericFormatType(NumericFormatType.INTEGER)	// In the case of a numeric format column, set the numeric format type
			    		.setColumnFormula("SUM({COL8}{ROW}:{COL19}{ROW})")	// If it is a formula column, set the formula expression
			    		.and()
			    	.addExcelColumn()
			    		.addHeaderColumn()
			    			.setValue("Summary")
			    			.setRowMergeSize(2)
			    			.and()
			    		.setRequired(false)
			    		.setCellType(CellType.STRING)
			    		.setSampleValue("Summary")
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

### Creating Excel file ###
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

### Generating Excel file containing data ###
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
						
						this.setCellData(excelRow, cellIndex++, "Hong Gil Dong" + i);
						this.setCellData(excelRow, cellIndex++, "Junior");
						this.setCellData(excelRow, cellIndex++, "directly");
						this.setCellData(excelRow, cellIndex++, "Manufacturing team");
						this.setCellData(excelRow, cellIndex++, "Full-time");
						this.setCellData(excelRow, cellIndex++, "Payroll");
						this.setCellData(excelRow, cellIndex++, "salary");
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
						this.setCellData(excelRow, cellIndex++); // FORMULA columns can be entered without values
						this.setCellData(excelRow, cellIndex++, "Summary");
						
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

### Reading Excel file data ###
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

### Database access object class to use database meta information ###
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

### XML query against metadata in the database ###
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

### Creating Excel template file using DB meta information ###
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

### Creating Excel using DB metadata and creating data including DB data ###
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

### Reading Excel file that fits DB metadata form and saving it in DB ###
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
