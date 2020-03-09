package com.anthunt.poi.mapper.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.anthunt.poi.mapper.model.AdditionalColumn;
import com.anthunt.poi.mapper.model.AdditionalColumns;
import com.anthunt.poi.mapper.model.AdditionalDatas;
import com.anthunt.poi.mapper.model.DBColumn;
import com.anthunt.poi.mapper.model.QueryType;
import com.anthunt.poi.mapper.model.UseType;
import com.anthunt.poi.template.AbstractDataListener;
import com.anthunt.poi.template.helper.PoiLogger;
import com.anthunt.poi.template.model.excel.ExcelColumn;
import com.anthunt.poi.template.model.excel.ExcelDBColumn;

public class BasicDataListener extends AbstractDataListener {

	private static PoiLogger logger = PoiLogger.getLogger(BasicDataListener.class);
	
	private Connection connection;
	private HashMap<String, PreparedStatement> preparedStatements;

	private List<ExcelColumn> excelColumns;
	private LinkedHashMap<String, LinkedHashMap<String, DBColumn>> tables;

	private LinkedHashMap<String, List<List<String>>> rowDatas;
	private List<List<String>> primaryValues;
	
	private boolean isStartPivot;
	private int startPivotColIndex;
	private int lastPivotColIndex;
	private List<String> defaultPivotRow;
		
	private AdditionalDatas additionalDatas;
	private AdditionalColumns additionalColumns;
	
	private QueryType queryType;
	
	public BasicDataListener(Connection connection, AdditionalDatas additionalDatas, QueryType queryType) {
		this.connection = connection;
		this.preparedStatements = new HashMap<>();
		this.additionalDatas = additionalDatas;
		this.queryType = queryType;
	}
	
	@Override
	public void handleStart() {}
	
	@Override
	public void handleSheet(Sheet sheet) {
				
		this.excelColumns = this.getExcelColumns();
		this.tables = new LinkedHashMap<>();
		this.additionalColumns = this.additionalDatas.getAdditionalColumns(this.getSheetIndex());
		
		for (ExcelColumn excelColumn : this.excelColumns) {
			
			if(excelColumn.getDataCellType() != CellType.FORMULA) {
				
				List<ExcelDBColumn> excelDBColumns = excelColumn.getDBColumns();
				for(ExcelDBColumn excelDBColumn : excelDBColumns) {
					
					String tableName = excelDBColumn.getTableName();
					
					LinkedHashMap<String, DBColumn> columns;
					
					if(this.tables.containsKey(tableName)) {
						columns = this.tables.get(tableName);
						
						DBColumn dbColumn;
						if(excelDBColumn.isPivot()) {
							dbColumn = new DBColumn(excelDBColumn.getPivotColumnName(), excelDBColumn.getPivotColumnValue(), excelDBColumn.isPivotPrimary());
							if(!columns.containsKey(dbColumn.getColumnName())) {
								columns.put(dbColumn.getColumnName(), dbColumn);
							}
						}
						
						dbColumn = new DBColumn(excelDBColumn.getColumnName(), excelDBColumn.isPrimary());
						if(!columns.containsKey(dbColumn.getColumnName())) {
							columns.put(dbColumn.getColumnName(), dbColumn);
						}
						
					} else {
						columns = new LinkedHashMap<>();
						
						DBColumn dbColumn;
						if(excelDBColumn.isPivot()) {
							dbColumn = new DBColumn(excelDBColumn.getPivotColumnName(), excelDBColumn.getPivotColumnValue(), excelDBColumn.isPivotPrimary());
							columns.put(dbColumn.getColumnName(), dbColumn);
						}
						
						dbColumn = new DBColumn(excelDBColumn.getColumnName(), excelDBColumn.isPrimary());								
						columns.put(dbColumn.getColumnName(), dbColumn);	
					}
					
					this.tables.put(tableName, columns);
					
				}
				
			}
			
		}
		
		Set<String> tableNames = this.tables.keySet();
		Iterator<String> itableNames = tableNames.iterator();
		while(itableNames.hasNext()) {
			
			String tableName = itableNames.next();
			
			String query = this.getQuery(tableName);
			
			this.putPreparedStatements(tableName, query);
			logger.debug("Generated Query [{}]", query);
			
		}
		
	}
	
	private String getQuery(String tableName) {
		String query = "";
		switch (this.queryType) {
		case INSERT:
			query = this.getInsertQuery(tableName);
			break;
		case UPDATE:
			query = this.getUpdateQuery(tableName);
			break;
		default:
			query = this.getMergeQuery(tableName);
			break;
		}
		return query;
	}
	
	private String getMergeQuery(String tableName) {
		
		StringBuilder dynamicQuery = new StringBuilder();

		LinkedHashMap<String, DBColumn> columns = this.tables.get(tableName);
		List<AdditionalColumn> additionalColumnList = additionalColumns.getAdditionalColumns();
		
		dynamicQuery.append("MERGE INTO ");
		dynamicQuery.append(tableName);
		dynamicQuery.append(" SRC ");
		dynamicQuery.append(" USING (");
		dynamicQuery.append(" SELECT ");
		
		int iCol = 0;
		for (AdditionalColumn additionalColumn : additionalColumnList) {
			if(additionalColumn.getUseType() == UseType.INSERT || additionalColumn.getUseType() == UseType.UPDATE || additionalColumn.getUseType() == UseType.INSERT_UPDATE || additionalColumn.isPrimary()) {
				if(iCol > 0) {
					dynamicQuery.append(", ");
				}
				if(additionalColumn.isUseSingleQuote()) {
				    dynamicQuery.append("'");
				}
				dynamicQuery.append(additionalColumn.getColumnValue());
				if(additionalColumn.isUseSingleQuote()) {
				    dynamicQuery.append("'");
				}
				dynamicQuery.append(" AS ");
				dynamicQuery.append(additionalColumn.getColumnName());
				iCol++;
			}
		}
		
		for(DBColumn dbColumn : columns.values()) {
			if(iCol > 0) {
				dynamicQuery.append(", ");
			}
			dynamicQuery.append("? AS ");
			dynamicQuery.append(dbColumn.getColumnName());
			iCol++;
		}
		
		dynamicQuery.append(" FROM DUAL ) TGT");
		dynamicQuery.append(" ON ( ");
		
		iCol = 0;
		for (AdditionalColumn additionalColumn : additionalColumnList) {
			if(additionalColumn.isPrimary()) {
				if(iCol > 0) {
					dynamicQuery.append(" AND ");
				}
				dynamicQuery.append(" SRC.");
				dynamicQuery.append(additionalColumn.getColumnName());
				dynamicQuery.append(" = TGT.");
				dynamicQuery.append(additionalColumn.getColumnName());
				iCol++;
			}
		}
		
		for(DBColumn dbColumn : columns.values()) {
			if(dbColumn.isPrimary()) {
				if(iCol > 0) {
					dynamicQuery.append(" AND ");
				}
				dynamicQuery.append(" SRC.");
				dynamicQuery.append(dbColumn.getColumnName());
				dynamicQuery.append(" = TGT.");
				dynamicQuery.append(dbColumn.getColumnName());
				iCol++;
			}
		}
		
		dynamicQuery.append(" ) ");
		dynamicQuery.append(" WHEN MATCHED THEN ");
		dynamicQuery.append(" UPDATE ");
		dynamicQuery.append(" SET ");
		
		iCol = 0;
		for (AdditionalColumn additionalColumn : additionalColumnList) {
		    if(!additionalColumn.isPrimary()) {
    			if(additionalColumn.getUseType() == UseType.UPDATE || additionalColumn.getUseType() == UseType.INSERT_UPDATE) {
    				if(iCol > 0) {
    					dynamicQuery.append(", ");
    				}
    				dynamicQuery.append(additionalColumn.getColumnName());
    				dynamicQuery.append(" = TGT.");
    				dynamicQuery.append(additionalColumn.getColumnName());
    				iCol++;
    			}
		    }
		}
					
		for(DBColumn dbColumn : columns.values()) {
		    if(!dbColumn.isPrimary()) {
    			if(iCol > 0) {
    				dynamicQuery.append(", ");
    			}
    			dynamicQuery.append(dbColumn.getColumnName());
    			dynamicQuery.append(" = TGT.");
    			dynamicQuery.append(dbColumn.getColumnName());
    			iCol++;
		    }
		}
		
		dynamicQuery.append(" WHEN NOT MATCHED THEN ");
		dynamicQuery.append(" INSERT ( ");
		
		iCol = 0;
		for (AdditionalColumn additionalColumn : additionalColumnList) {
			if(additionalColumn.getUseType() == UseType.INSERT || additionalColumn.getUseType() == UseType.INSERT_UPDATE) {
				if(iCol > 0) {
					dynamicQuery.append(", ");
				}
				dynamicQuery.append(additionalColumn.getColumnName());
				iCol++;
			}
		}
					
		for(DBColumn dbColumn : columns.values()) {
			if(iCol > 0) {
				dynamicQuery.append(", ");
			}
			dynamicQuery.append(dbColumn.getColumnName());
			iCol++;
		}
		
		dynamicQuery.append(" ) VALUES ( ");
		
		iCol = 0;
		for (AdditionalColumn additionalColumn : additionalColumnList) {
			if(additionalColumn.getUseType() == UseType.INSERT || additionalColumn.getUseType() == UseType.INSERT_UPDATE) {
				if(iCol > 0) {
					dynamicQuery.append(", ");
				}
				dynamicQuery.append("TGT.");
				dynamicQuery.append(additionalColumn.getColumnName());
				iCol++;
			}
		}
					
		for(DBColumn dbColumn : columns.values()) {
			if(iCol > 0) {
				dynamicQuery.append(", ");
			}
			dynamicQuery.append("TGT.");
			dynamicQuery.append(dbColumn.getColumnName());
			iCol++;
		}
		
		dynamicQuery.append(") ");
				
		return dynamicQuery.toString();
	}

	private String getUpdateQuery(String tableName) {
		
		StringBuilder dynamicQuery = new StringBuilder();

		LinkedHashMap<String, DBColumn> columns = this.tables.get(tableName);
		
		dynamicQuery.append("UPDATE ");
		dynamicQuery.append(tableName);
		dynamicQuery.append(" SET ");
		
		int iCol = 0;
		List<AdditionalColumn> additionalColumnList = additionalColumns.getAdditionalColumns();
		for (AdditionalColumn additionalColumn : additionalColumnList) {
			if(additionalColumn.getUseType() == UseType.UPDATE || additionalColumn.getUseType() == UseType.INSERT_UPDATE) {
				if(iCol > 0) {
					dynamicQuery.append(", ");
				}
				dynamicQuery.append(additionalColumn.getColumnName());
				dynamicQuery.append(" = ");
				if(additionalColumn.isUseSingleQuote()) {
				    dynamicQuery.append("'");
				}
				dynamicQuery.append(additionalColumn.getColumnValue());
				if(additionalColumn.isUseSingleQuote()) {
				    dynamicQuery.append("'");
				}
				
				iCol++;
			}
		}
		
		for(DBColumn dbColumn : columns.values()) {
			if(iCol > 0) {
				dynamicQuery.append(", ");
			}
			dynamicQuery.append(dbColumn.getColumnName());
			dynamicQuery.append(" = ?");
			iCol++;
		}
		
		dynamicQuery.append(" WHERE ");
		
		iCol = 0;
		for (AdditionalColumn additionalColumn : additionalColumnList) {
			if(additionalColumn.isPrimary()) {
				if(iCol > 0) {
					dynamicQuery.append(" AND ");
				}
				dynamicQuery.append(additionalColumn.getColumnName());
				dynamicQuery.append(" = '");
				dynamicQuery.append(additionalColumn.getColumnValue());
				dynamicQuery.append("'");
				iCol++;
			}
		}
		
		for(DBColumn dbColumn : columns.values()) {
			if(dbColumn.isPrimary()) {
				if(iCol > 0) {
					dynamicQuery.append(" AND ");
				}
				dynamicQuery.append(dbColumn.getColumnName());
				dynamicQuery.append(" = ? ");
				iCol++;
			}
		}
		
		return dynamicQuery.toString();
	}	
	
	private String getInsertQuery(String tableName) {
		
		StringBuilder dynamicQuery = new StringBuilder();
		
		LinkedHashMap<String, DBColumn> columns = this.tables.get(tableName);
		
		dynamicQuery.append("INSERT INTO ");
		dynamicQuery.append(tableName);
		dynamicQuery.append(" (");
		
		int iCol = 0;
		List<AdditionalColumn> additionalColumnList = additionalColumns.getAdditionalColumns();
		for (AdditionalColumn additionalColumn : additionalColumnList) {
			if(additionalColumn.getUseType() == UseType.INSERT || additionalColumn.getUseType() == UseType.INSERT_UPDATE) {
				if(iCol > 0) {
					dynamicQuery.append(", ");
				}
				dynamicQuery.append(additionalColumn.getColumnName());
				iCol++;
			}
		}
					
		for(DBColumn dbColumn : columns.values()) {
			if(iCol > 0) {
				dynamicQuery.append(", ");
			}
			dynamicQuery.append(dbColumn.getColumnName());
			iCol++;
		}
		
		dynamicQuery.append(")");
		dynamicQuery.append(" VALUES ");
		dynamicQuery.append("(");
		
		iCol = 0;
		for (AdditionalColumn additionalColumn : additionalColumnList) {
			if(additionalColumn.getUseType() == UseType.INSERT || additionalColumn.getUseType() == UseType.INSERT_UPDATE) {
				if(iCol > 0) {
					dynamicQuery.append(", ");
				}
				if(additionalColumn.isUseSingleQuote()) {
				    dynamicQuery.append("'");
				}
				dynamicQuery.append(additionalColumn.getColumnValue());
				if(additionalColumn.isUseSingleQuote()) {
				    dynamicQuery.append("'");
				}
				iCol++;
			}
		}
		
		int columnSize = columns.values().size() + iCol;
		for(; iCol < columnSize; iCol++) {
			if(iCol > 0) {
				dynamicQuery.append(", ");
			}
			dynamicQuery.append("?");
		}
		
		dynamicQuery.append(")");
		
		return dynamicQuery.toString();
	}
	
	private void putPreparedStatements(String tableName, String query) {
		try {
			this.preparedStatements.put(tableName, this.connection.prepareStatement(query));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	@Override
	public void handleRow(Sheet sheet, Row row) {
		this.rowDatas = new LinkedHashMap<>();
		this.isStartPivot = false;
		this.startPivotColIndex = 0;
		this.lastPivotColIndex = 0;
		this.defaultPivotRow = new ArrayList<>();
		this.primaryValues = new ArrayList<>();
	}

	@Override
	public void handleAfterRow(Sheet sheet, Row row) {
		Set<String> keys = this.rowDatas.keySet();
		Iterator<String> ikeys = keys.iterator();
		while(ikeys.hasNext()) {
			String tableName = ikeys.next();
			PreparedStatement preparedStatement = this.preparedStatements.get(tableName);
			List<List<String>> rowDataRows = this.rowDatas.get(tableName);
			for (int i = 0; i < rowDataRows.size(); i++) {
				List<String> rowDataRow = rowDataRows.get(i);
				
				StringBuffer sbParam = new StringBuffer();
				
				int j = 0;
				for (; j < rowDataRow.size(); j++) {
					String value = rowDataRow.get(j); 
					try {						
						preparedStatement.setString(j + 1, value);
						if(logger.isDebugEnabled() || logger.isTraceEnabled()) {
							if(sbParam.length() > 0) {
								sbParam.append(", ");
							}
							sbParam.append(j+1);
							sbParam.append(":");
							sbParam.append(value);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}	
				}
				
				if(this.queryType == QueryType.UPDATE) {
					for(String value : this.primaryValues.get(i)) {
						try {
							preparedStatement.setString(j+1, value);
							if(logger.isDebugEnabled() || logger.isTraceEnabled()) {
								if(sbParam.length() > 0) {
									sbParam.append(", ");
								}
								sbParam.append(j+1);
								sbParam.append(":");
								sbParam.append(value);
							}
							j++;
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				
				logger.trace("DynamicQuery Parameters {}:[{}]", i, sbParam.toString());
				try {
					preparedStatement.addBatch();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}

			try {
				int[] results = preparedStatement.executeBatch();
				if(logger.isTraceEnabled()) {
					for (int result : results) {
						logger.trace("Query Result : {}", result);
					}
				}
				this.connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
		
//	@Override
//	public void handleCell(Sheet sheet, Row row, Cell cell) {
//		
//		ExcelColumn excelColumn = this.getExcelColumns().get(cell.getColumnIndex());
//		List<ExcelDBColumn> excelDBColumns = excelColumn.getDBColumns();
//		
//		if(excelColumn.getDataCellType() == CellType.FORMULA) {
//		    return;
//		}
//		
//		for (int iCol = 0; iCol < excelDBColumns.size(); iCol++) {
//			ExcelDBColumn excelDBColumn = excelDBColumns.get(iCol);
//			
//			String tableName = excelDBColumn.getTableName();
//			
//			if(!this.rowDatas.containsKey(tableName)) {
//				List<List<String>> rowData = new ArrayList<List<String>>();
//				rowData.add(0, new ArrayList<String>());
//				this.rowDatas.put(tableName, rowData);
//				this.primaryValues.add(new ArrayList<String>());
//			}					
//			
//			List<List<String>> rowData = this.rowDatas.get(tableName);
//
//			String cellValue = null;
//			switch (excelColumn.getDataCellType()) {
//			case BOOLEAN:
//				cellValue = cell.getBooleanCellValue() ? "Y" : "N";
//				break;
//			case NUMERIC:
//				cellValue = Double.toString(cell.getNumericCellValue());
//				break;
//			default:
//				cellValue = cell.getStringCellValue();
//				break;
//			}
//			
//			if(excelDBColumn.isPivot()) {
//				if(!this.isStartPivot) {
//					this.isStartPivot = true;
//					this.startPivotColIndex = cell.getColumnIndex();
//				} else {
//					if(this.defaultPivotRow.size() == 0) {
//						this.defaultPivotRow.addAll(rowData.get(rowData.size() - 1));
//						this.defaultPivotRow.remove(this.defaultPivotRow.size() - 1);
//						this.defaultPivotRow.remove(this.defaultPivotRow.size() - 1);
//					}
//					List<String> rowDataRow = new ArrayList<>();
//					rowDataRow.addAll(this.defaultPivotRow);
//					rowData.add(rowDataRow);
//					List<String> values = new ArrayList<>();
//					values.addAll(this.primaryValues.get(this.primaryValues.size() - 1));
//					values.remove(values.size() - 1);
//					this.primaryValues.add(values);
//				}
//
//				List<String> rowDataRow =  rowData.get(rowData.size() - 1);
//				rowDataRow.add(this.startPivotColIndex, excelDBColumn.getPivotColumnValue());
//
//				if(excelDBColumn.isPivotPrimary()) {
//					this.primaryValues.get(this.primaryValues.size() - 1).add(excelDBColumn.getPivotColumnValue());
//				}
//				
//			} else {
//				if(this.isStartPivot && this.lastPivotColIndex == 0) {
//					this.lastPivotColIndex = cell.getColumnIndex() - 1;
//				}
//				
//				if(excelDBColumn.isPrimary()) {
//					this.primaryValues.get(this.primaryValues.size() - 1).add(cellValue);
//				}
//			}
//			
//			List<String> rowDataRow = rowData.get(rowData.size() - 1);
//			rowDataRow.add((excelDBColumn.isPivot() ? this.startPivotColIndex + 1 : this.isStartPivot && this.lastPivotColIndex > 0 ? cell.getColumnIndex() - this.lastPivotColIndex + this.startPivotColIndex + 1 : cell.getColumnIndex()), cellValue);
//			if(this.isStartPivot && this.lastPivotColIndex > 0) {
//				for(int i = 0; i < rowData.size() - 1; i++) {
//					rowData.get(i).add((excelDBColumn.isPivot() ? this.startPivotColIndex + 1 : this.isStartPivot && this.lastPivotColIndex > 0 ? cell.getColumnIndex() - this.lastPivotColIndex + this.startPivotColIndex + 1 : cell.getColumnIndex()), cellValue);
//				}				
//			}
//
//		}
//		
//	}
	
	@Override
    public void handleCell(Sheet sheet, Row row, Cell cell) {
        
        ExcelColumn excelColumn = this.getExcelColumns().get(cell.getColumnIndex());
        List<ExcelDBColumn> excelDBColumns = excelColumn.getDBColumns();
        
        if(excelColumn.getDataCellType() == CellType.FORMULA) {
            return;
        }
        
        for (int iCol = 0; iCol < excelDBColumns.size(); iCol++) {
            ExcelDBColumn excelDBColumn = excelDBColumns.get(iCol);
            
            int dbColIndex = excelDBColumn.getDbColIndex();
            String tableName = excelDBColumn.getTableName();
            
            if(!this.rowDatas.containsKey(tableName)) {
                List<List<String>> rowData = new ArrayList<List<String>>();
                rowData.add(0, new ArrayList<String>());
                this.rowDatas.put(tableName, rowData);
                this.primaryValues.add(new ArrayList<String>());
            }                   
            
            List<List<String>> rowData = this.rowDatas.get(tableName);

            String cellValue = null;
            switch (excelColumn.getDataCellType()) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue() ? "Y" : "N";
                break;
            case NUMERIC:
                cellValue = Double.toString(cell.getNumericCellValue());
                break;
            default:
                cellValue = cell.getStringCellValue();
                break;
            }
            
            if(excelDBColumn.isPivot()) {
                if(!this.isStartPivot) {
                    this.isStartPivot = true;
                    this.startPivotColIndex = dbColIndex;
                } else {
                    if(this.defaultPivotRow.size() == 0) {
                        this.defaultPivotRow.addAll(rowData.get(rowData.size() - 1));
                        this.defaultPivotRow.remove(this.defaultPivotRow.size() - 1);
                        this.defaultPivotRow.remove(this.defaultPivotRow.size() - 1);
                    }
                    List<String> rowDataRow = new ArrayList<>();
                    rowDataRow.addAll(this.defaultPivotRow);
                    rowData.add(rowDataRow);
                    List<String> values = new ArrayList<>();
                    values.addAll(this.primaryValues.get(this.primaryValues.size() - 1));
                    values.remove(values.size() - 1);
                    this.primaryValues.add(values);
                }

                List<String> rowDataRow =  rowData.get(rowData.size() - 1);
                rowDataRow.add(this.startPivotColIndex, excelDBColumn.getPivotColumnValue());

                if(excelDBColumn.isPivotPrimary()) {
                    this.primaryValues.get(this.primaryValues.size() - 1).add(excelDBColumn.getPivotColumnValue());
                }
                
            } else {
                if(this.isStartPivot && this.lastPivotColIndex == 0) {
                    this.lastPivotColIndex = dbColIndex - 1;
                }
                
                if(excelDBColumn.isPrimary()) {
                    this.primaryValues.get(this.primaryValues.size() - 1).add(cellValue);
                }
            }
            
            List<String> rowDataRow = rowData.get(rowData.size() - 1);
            rowDataRow.add((excelDBColumn.isPivot() ? this.startPivotColIndex + 1 : this.isStartPivot && this.lastPivotColIndex > 0 ? dbColIndex - this.lastPivotColIndex + this.startPivotColIndex + 1 : dbColIndex), cellValue);
            if(this.isStartPivot && this.lastPivotColIndex > 0) {
                for(int i = 0; i < rowData.size() - 1; i++) {
                    rowData.get(i).add((excelDBColumn.isPivot() ? this.startPivotColIndex + 1 : this.isStartPivot && this.lastPivotColIndex > 0 ? dbColIndex - this.lastPivotColIndex + this.startPivotColIndex + 1 : dbColIndex), cellValue);
                }               
            }

        }
        
    }
	
	@Override
	public void handleEndOfSheet() {}

	@Override
	public void handleLast() {}
	
}
