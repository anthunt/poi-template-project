package com.anthunt.poi.mapper.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.CellType;

import com.anthunt.poi.mapper.dao.AbstractMapperDAO;
import com.anthunt.poi.mapper.model.SelectQuery;
import com.anthunt.poi.mapper.model.AdditionalDatas;
import com.anthunt.poi.mapper.model.AdditionalColumns;
import com.anthunt.poi.template.AbstractDataProvider;
import com.anthunt.poi.template.helper.PoiLogger;
import com.anthunt.poi.template.model.enums.DBDataType;
import com.anthunt.poi.template.model.excel.ExcelColumn;
import com.anthunt.poi.template.model.excel.ExcelDBColumn;
import com.anthunt.poi.template.model.excel.ExcelRow;
import com.anthunt.poi.template.model.excel.ExcelSheet;

public class BasicDataProvider extends AbstractDataProvider {

	private static PoiLogger logger = PoiLogger.getLogger(BasicDataProvider.class);
	
	private Connection connection;
	private AbstractMapperDAO abstractMapperDAO;
	private AdditionalDatas additionalDatas;
	
	public BasicDataProvider(AbstractMapperDAO abstractMapperDAO, AdditionalDatas additionalDatas) {
		this.abstractMapperDAO = abstractMapperDAO;
		this.connection = this.abstractMapperDAO.getConnection();
		this.additionalDatas = additionalDatas;
	}
	
	@Override
	protected void applyData(ExcelSheet excelSheet) {
		
		LinkedHashMap<String, SelectQuery> queries = new LinkedHashMap<>();
		
		if(!excelSheet.isSkipSheet()) {
			
			AdditionalColumns additionalColumns = this.additionalDatas.getAdditionalColumns(excelSheet.getSheetIndex());
			
			List<ExcelColumn> excelColumns = excelSheet.getExcelColumns();
			for (ExcelColumn excelColumn : excelColumns) {
				
				List<ExcelDBColumn> dbColumns = excelColumn.getDBColumns();
				for (ExcelDBColumn excelDBColumn : dbColumns) {
					
					if(!queries.containsKey(excelDBColumn.getTableName())) {
						queries.put(excelDBColumn.getTableName(), new SelectQuery(excelDBColumn.getTableName(), additionalColumns));
					}

					SelectQuery selectQuery = queries.get(excelDBColumn.getTableName());
					
					//max(case when col7 = '1' then col8 end)
					if(excelDBColumn.isPivot()) {
						StringBuffer pivotBuffer = new StringBuffer();
						pivotBuffer.append("MAX(CASE WHEN ");
						pivotBuffer.append(excelDBColumn.getPivotColumnName());
						pivotBuffer.append(" = '");
						pivotBuffer.append(excelDBColumn.getPivotColumnValue());
						pivotBuffer.append("' THEN ");
						pivotBuffer.append(excelDBColumn.getColumnName());
						pivotBuffer.append(" END)");
						selectQuery.addColumn(pivotBuffer.toString(), true);
					} else {
						if(excelDBColumn.getDataType() == DBDataType.DATE) {
							selectQuery.addColumn("TO_CHAR(" + excelDBColumn.getColumnName() + ", '" + excelDBColumn.getDateFormat() + "')");
						} else {
							selectQuery.addColumn(excelDBColumn.getColumnName());
						}
					}
				}
			}
			
		
			Set<String> keys = queries.keySet();
			Iterator<String> ikeys = keys.iterator();
			
			while(ikeys.hasNext()) {
				
				String tableName = ikeys.next();
				SelectQuery selectQuery = queries.get(tableName);
				
				for (int iCol = 0; iCol < excelColumns.size(); iCol++) {
					ExcelColumn excelColumn = excelColumns.get(iCol);
					
					if(CellType.FORMULA == excelColumn.getDataCellType()) {
						selectQuery.addColumn(iCol);
					}
				}
				logger.debug("Generated Query [{}]", selectQuery.getQuery());
				PreparedStatement pstmt = null;
				try {
					pstmt = this.connection.prepareStatement(selectQuery.getQuery());
					ResultSet resultSet = pstmt.executeQuery();
					while(resultSet.next()) {
						int columnCount = resultSet.getMetaData().getColumnCount();
						ExcelRow excelRow = this.getRow();
						for(int cellIndex = 0; cellIndex < columnCount; cellIndex++) {
							this.setCellData(excelRow, cellIndex, resultSet.getString(cellIndex + 1));
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					if(pstmt != null) {
						try { pstmt.close(); } catch(Exception skip) {}
					}
				}
				
			}

		}
		
	}

	@Override
	protected void applyFinish() {
		if(this.connection != null) {
			try { this.connection.close(); } catch(Exception skip) {}
		}
	}
	
}
