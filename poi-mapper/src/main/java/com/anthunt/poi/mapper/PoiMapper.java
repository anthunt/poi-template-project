package com.anthunt.poi.mapper;

import java.util.List;

import org.apache.poi.ss.usermodel.CellType;

import com.anthunt.poi.mapper.dao.AbstractMapperDAO;
import com.anthunt.poi.mapper.model.CellDefinition;
import com.anthunt.poi.mapper.model.CellDefinitions;
import com.anthunt.poi.mapper.model.ColumnConstraintDefinition;
import com.anthunt.poi.mapper.model.ColumnDefinition;
import com.anthunt.poi.mapper.model.ColumnDefinitions;
import com.anthunt.poi.mapper.model.DBColumnDefinition;
import com.anthunt.poi.mapper.model.SheetDefinition;
import com.anthunt.poi.mapper.model.SheetDefinitions;
import com.anthunt.poi.template.helper.PoiLogger;
import com.anthunt.poi.template.model.enums.DBDataType;
import com.anthunt.poi.template.model.enums.NumericFormatType;
import com.anthunt.poi.template.model.excel.ExcelCellBuilder;
import com.anthunt.poi.template.model.excel.ExcelColumnBuilder;
import com.anthunt.poi.template.model.excel.ExcelDBColumnBuilder;
import com.anthunt.poi.template.model.excel.ExcelSheetBuilder;
import com.anthunt.poi.template.model.excel.ExcelSheets;

public class PoiMapper {

	private static PoiLogger logger = PoiLogger.getLogger(PoiMapper.class);
	
	private AbstractMapperDAO abstractMapperDAO;
	private ExcelSheets excelSheets;
	
	public PoiMapper(AbstractMapperDAO abstractDAO) {
		this.abstractMapperDAO = abstractDAO;
		this.excelSheets = new ExcelSheets();
		logger.trace("PoiMapper instance created.");
	}
	
	public ExcelSheets getExcelSheets() {
		return this.excelSheets;
	}
	
	public ExcelSheets mapping(int excelIdx, boolean useSampleData) {
		logger.debug("mapping start [ExcelIdx={}, useSampleData={}]", excelIdx, useSampleData);
		this.mapping(new SheetDefinitions(abstractMapperDAO.getSheetDefinitions(excelIdx)), useSampleData);
		return this.excelSheets; 
	}
	
	public ExcelSheets mapping(int excelIdx) {
		return this.mapping(excelIdx, false);
	}
	
	private void mapping(SheetDefinitions sheetDefinitions, boolean useSampleData) {
		
		for (SheetDefinition sheetDefinition : sheetDefinitions) {
			
			ExcelSheetBuilder excelSheetBuilder = excelSheets.builder();
			
			excelSheetBuilder.setSheetName(sheetDefinition.getSheetName())
							 .setSheetTitle(sheetDefinition.getSheetTitle())
							 .setSkipSheet(sheetDefinition.isSkipSheet())
							 .setUseSampleData(useSampleData)
							 .setExplainRowSize(sheetDefinition.getExplainRowSize())
							 .setHeaderRowSize(sheetDefinition.getHeaderRowSize())
							 ;
			
			ColumnDefinitions columnDefinitions = sheetDefinition.getColumnDefinitions();
			for (ColumnDefinition columnDefinition : columnDefinitions) {
				ExcelColumnBuilder excelColumnBuilder = excelSheetBuilder.addExcelColumn();
				
				excelColumnBuilder.setRequired(columnDefinition.isRequired())
								  .setCellType(CellType.valueOf(columnDefinition.getDataCellType()))
								  .setColumnFormula(columnDefinition.getColumnFormula())
								  .setSampleValue(columnDefinition.getSampleValue())
								  .setDataExplicits(
										  columnDefinition.getDataExplicits() == null ?
												  null
												  :
												  columnDefinition.getDataExplicits().toArray(new String[columnDefinition.getDataExplicits().size()]))
								  .setNumericFormatType(
										  columnDefinition.getNumericFormatType() == null ?
												  null
												  :
												  NumericFormatType.valueOf(columnDefinition.getNumericFormatType()))
								  ;
				
				ColumnConstraintDefinition columnConstraintDefinition = columnDefinition.getColumnConstraintDefinition();
				if(columnConstraintDefinition != null) {
					excelColumnBuilder.addColumnConstraint()
									  .setMin(columnConstraintDefinition.getMin())
									  .setMax(columnConstraintDefinition.getMax())
									  .setRegExp(columnConstraintDefinition.getRegExp())
									  .and();
				}
				
				CellDefinitions explainCellDefinitions = columnDefinition.getExplainCellDefinitions();
				for (CellDefinition cellDefinition : explainCellDefinitions) {

					ExcelCellBuilder excelCellBuilder = excelColumnBuilder.addExplainColumn();

					if(cellDefinition.getCellMergeSize() != null) {
						excelCellBuilder.setCellMergeSize(cellDefinition.getCellMergeSize());
					}
					
					if(cellDefinition.getRowMergeSize() != null) {
						excelCellBuilder.setRowMergeSize(cellDefinition.getRowMergeSize());
					}
					  
					excelCellBuilder.setSkipRequired(cellDefinition.isSkipRequired())
								    .setValue(cellDefinition.getValue())
								    .setComment(cellDefinition.getComment())
								    .and();
					
				}
				
				CellDefinitions headerCellDefinitions = columnDefinition.getHeaderCellDefinitions();
				for (CellDefinition cellDefinition : headerCellDefinitions) {

					ExcelCellBuilder excelCellBuilder = excelColumnBuilder.addHeaderColumn();

					if(cellDefinition.getCellMergeSize() != null) {
						excelCellBuilder.setCellMergeSize(cellDefinition.getCellMergeSize());
					}
					
					if(cellDefinition.getRowMergeSize() != null) {
						excelCellBuilder.setRowMergeSize(cellDefinition.getRowMergeSize());
					}
					  
					excelCellBuilder.setSkipRequired(cellDefinition.isSkipRequired())
								    .setValue(cellDefinition.getValue())
								    .setComment(cellDefinition.getComment())
								    .and();
					
				}
				
				List<DBColumnDefinition> dbColumnDefinitions = columnDefinition.getDbColumnDefinitions();
				for (DBColumnDefinition dbColumnDefinition : dbColumnDefinitions) {
					
					ExcelDBColumnBuilder excelDBColumnBuilder = excelColumnBuilder.addDBColumn();
					
					excelDBColumnBuilder.setTableName(dbColumnDefinition.getTableName())
										.setColumnName(dbColumnDefinition.getColumnName())
										.setDataType(DBDataType.valueOf(dbColumnDefinition.getDataType()))
										.setDbColIndex(dbColumnDefinition.getDbColIndex())
										.setPrimary(dbColumnDefinition.isPrimary())
										.setDateFormat(dbColumnDefinition.getDateFormat());
					
					if(dbColumnDefinition.isPivot()) {
						excelDBColumnBuilder.setPivotColumn(dbColumnDefinition.getPivotColumnName(), dbColumnDefinition.getPivotColumnValue(), dbColumnDefinition.isPivotPrimary());
					}
					
					excelDBColumnBuilder.and();
					
				}
				
				excelColumnBuilder.and();
			}
			
			excelSheetBuilder.build();
		}
		
	}
	
}
