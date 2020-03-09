package com.anthunt.poi.mapper.dao;

import java.sql.Connection;
import java.util.List;

import com.anthunt.poi.mapper.model.CellDefinition;
import com.anthunt.poi.mapper.model.CellDefinitions;
import com.anthunt.poi.mapper.model.ColumnConstraintDefinition;
import com.anthunt.poi.mapper.model.ColumnDefinition;
import com.anthunt.poi.mapper.model.ColumnDefinitions;
import com.anthunt.poi.mapper.model.DAOParams;
import com.anthunt.poi.mapper.model.DBColumnDefinition;
import com.anthunt.poi.mapper.model.SheetDefinition;
import com.anthunt.poi.template.helper.PoiLogger;

public abstract class AbstractMapperDAO {

	private static PoiLogger logger = PoiLogger.getLogger(AbstractMapperDAO.class);
	
	protected AbstractMapperDAO() {}
	
	public Connection getConnection() {
		return this.applyConnection();
	}
		
	public List<SheetDefinition> getSheetDefinitions(int excelIdx) {
		
		logger.debug("called getSheetDefinitions({})", excelIdx);
		
		DAOParams daoParams = new DAOParams();
		daoParams.setExcelIdx(excelIdx);
		
		List<SheetDefinition> sheetDefinitions = this.applySheetDefinitions(daoParams);
		logger.debug("got sheet definitions from DAO. [SheetDefinitions Size = {}]", sheetDefinitions.size());
		
		for (SheetDefinition sheetDefinition : sheetDefinitions) {
			
			logger.trace("start SheetDefinition [SheetIndex = {}]", sheetDefinition.getSheetIndex());
			daoParams.setSheetIdx(sheetDefinition.getSheetIndex());
			
			List<ColumnDefinition> columnDefinitions = this.applyColumnDefinitions(daoParams);
			for (int iCol = 0; iCol < columnDefinitions.size(); iCol++) {
				
				logger.trace("start ColumnDefinition [ColumnIndex = {}]", iCol);
				
				ColumnDefinition columnDefinition = columnDefinitions.get(iCol);
				logger.trace("got ColumnDefinition [ColumnIndex = {}]", iCol);
				
				daoParams.setColumnIdx(iCol);
				logger.trace("set DAOParams [ColumnIndex = {}]", iCol);
				
				ColumnConstraintDefinition columnConstraintDefinition = this.applyColumnConstraintDefinition(daoParams);
				logger.trace("got ColumnConstraintDefinition {}", columnConstraintDefinition);
				
				columnDefinition.setColumnConstraintDefinition(columnConstraintDefinition);
				logger.trace("set ColumnDefinition setColumnConstraintDefinition({})", columnConstraintDefinition);
				
				List<CellDefinition> explainCellDefinitions = this.applyExplainCellDefinitions(daoParams);
				logger.trace("got explainCellDefinitions [Size = {}]", explainCellDefinitions.size());
				
				columnDefinition.setExplainCellDefinitions(new CellDefinitions(explainCellDefinitions));
				logger.trace("set ColumnDefinition setExplainCellDefinitions({})", explainCellDefinitions);
				
				List<CellDefinition> headerCellDefinitions = this.applyHeaderCellDefinitions(daoParams);
				logger.trace("got headerCellDefinitions [Size = {}]", headerCellDefinitions.size());
				
				columnDefinition.setHeaderCellDefinitions(new CellDefinitions(headerCellDefinitions));
				logger.trace("set ColumnDefinition setHeaderCellDefinitions({})", headerCellDefinitions);
				
				List<DBColumnDefinition> dbColumnDefinitions = this.applyDBColumnDefinitions(daoParams);
				logger.trace("got dbColumnDefinitions [Size = {}]", dbColumnDefinitions.size());
				
				columnDefinition.setDbColumnDefinitions(dbColumnDefinitions);
				logger.trace("set ColumnDefinition setDbColumnDefinitions({})", dbColumnDefinitions);
			}
			
			sheetDefinition.setColumnDefinitions(new ColumnDefinitions(columnDefinitions));
			logger.trace("finished SheetDefinition [SheetIndex = {}]", sheetDefinition.getSheetIndex());
		}
		logger.debug("finished sheet definitions from DAO. [SheetDefinitions Size = {}]", sheetDefinitions.size());
		
		return sheetDefinitions;
	}
	
	protected abstract Connection applyConnection();
	protected abstract List<SheetDefinition> applySheetDefinitions(DAOParams daoParams);
	protected abstract List<ColumnDefinition> applyColumnDefinitions(DAOParams daoParams);
	protected abstract ColumnConstraintDefinition applyColumnConstraintDefinition(DAOParams daoParams);
	protected abstract List<CellDefinition> applyExplainCellDefinitions(DAOParams daoParams);
	protected abstract List<CellDefinition> applyHeaderCellDefinitions(DAOParams daoParams);
	protected abstract List<DBColumnDefinition> applyDBColumnDefinitions(DAOParams daoParams);
	
}
