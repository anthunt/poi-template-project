package com.anthunt.poi.mapper.model;

import java.util.ArrayList;
import java.util.List;

import com.anthunt.poi.template.helper.PoiLogger;

public class SelectQuery {

	private static PoiLogger logger = PoiLogger.getLogger(SelectQuery.class);
	
	private class Column {
		
		private boolean isPivot;
		private String columnName;
		
		private Column(String columnName, boolean isPivot) {
			this.columnName = columnName;
			this.isPivot = isPivot;
		}
		
		private String getColumnName() {
			return this.columnName;
		}
		
		private boolean isPivot() {
			return this.isPivot;
		}
		
	}
	
	private static final String SELECT = "SELECT";
	
	private String tableName;
	private List<Column> columnNames;
	private boolean hasPivot;
	private AdditionalColumns whereConditions;
	
	public SelectQuery(String tableName, AdditionalColumns whereConditions) {
		this.tableName = tableName;
		this.columnNames = new ArrayList<>();
		this.hasPivot = false;
		this.whereConditions = whereConditions;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public List<Column> getColumnNames() {
		return columnNames;
	}
	
	public void addColumn(int index) {
		this.addColumn(index, "''", false);
	}

	public void addColumn(String columnName) {
		this.addColumn(null, columnName, false);
	}
	
	public void addColumn(String columnName, boolean isPivot) {
		this.addColumn(null, columnName, isPivot);
	}
	
	public void addColumn(Integer index, String columnName, boolean isPivot) {
		if(isPivot && !this.hasPivot) {
			this.hasPivot = true;
		}
		if(index == null) {
			this.columnNames.add(new Column(columnName, isPivot));
		} else {
			this.columnNames.add(index, new Column(columnName, isPivot));
		}
	}

	public String getQuery() {
		
		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append(SELECT);
		queryBuffer.append(" ");
		
		for (int iCol = 0; iCol < this.getColumnNames().size(); iCol++) {
			String columnName = this.getColumnNames().get(iCol).getColumnName();
			if(iCol > 0) {
				queryBuffer.append(", ");
			}
			
			queryBuffer.append(columnName);	
			queryBuffer.append(" AS COLUMN_");
			queryBuffer.append(iCol);
		}
		
		queryBuffer.append(" FROM ");
		queryBuffer.append(this.getTableName());
		
		if(this.whereConditions != null) {

			List<AdditionalColumn> whereConditions = this.whereConditions.getAdditionalColumns();
			if(whereConditions.size() > 0) {
				
				StringBuffer whereBuffer = new StringBuffer();
				for (AdditionalColumn whereCondition : whereConditions) {
					if(whereCondition.checkUseType(UseType.SEARCH)) {
						if(whereBuffer.length() > 0) {
							whereBuffer.append(" AND ");
						}
						whereBuffer.append(whereCondition.getColumnName());
						whereBuffer.append(" ");
						whereBuffer.append(whereCondition.getOperation());
						whereBuffer.append(" '");
						whereBuffer.append(whereCondition.getColumnValue());
						whereBuffer.append("' ");
					}
				}
				
				if(whereBuffer.length() > 0) {
					queryBuffer.append(" WHERE ");
					queryBuffer.append(whereBuffer.toString());
				}
			}
			
		}
		
		if(this.hasPivot) {
			queryBuffer.append(" GROUP BY ");
			for (int iCol = 0; iCol < this.getColumnNames().size(); iCol++) {
				Column column = this.getColumnNames().get(iCol);
				if(!column.isPivot()) {
					if(iCol > 0) {
						queryBuffer.append(", ");
					}
					
					queryBuffer.append(column.getColumnName());
				}
			}
		}
		
		return queryBuffer.toString();
	}
	
}
