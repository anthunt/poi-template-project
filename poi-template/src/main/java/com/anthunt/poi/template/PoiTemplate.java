package com.anthunt.poi.template;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.anthunt.poi.template.exception.TemplateException;
import com.anthunt.poi.template.exception.ValidateException;
import com.anthunt.poi.template.helper.ExcelHelper;
import com.anthunt.poi.template.helper.PoiLogger;
import com.anthunt.poi.template.model.excel.ExcelCell;
import com.anthunt.poi.template.model.excel.ExcelColumn;
import com.anthunt.poi.template.model.excel.ExcelSheet;
import com.anthunt.poi.template.model.excel.ExcelSheets;
import com.anthunt.poi.template.model.enums.CellStyle;
import com.anthunt.poi.template.model.enums.FontType;

/**
 * <b>Poi Excel Template Generator Class</b>
 * <p>
 * easly make Excel Template and read/write
 * </p>
 * @author anthunt
 *
 */
public class PoiTemplate {

	private static PoiLogger logger = PoiLogger.getLogger(PoiLogger.class);
	
	private File excelFile;
	private XSSFWorkbook workbook;
	private ExcelSheets excelSheets;
	private CreationHelper factory;	
	private ClientAnchor anchor;
	
	/**
	 * Poi Template Class Constructor
	 * 
	 * @param excelSheets
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public PoiTemplate(ExcelSheets excelSheets) throws TemplateException {
		this(excelSheets, null);
	}
	
	/**
	 * Poi Template Class Constructor
	 * 
	 * @param excelSheets
	 * @param excelFilePath
	 * @throws TemplateException 
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public PoiTemplate(ExcelSheets excelSheets, String excelFilePath) throws TemplateException {
		try {
			if(excelFilePath != null) {
				this.excelFile = new File(excelFilePath);	
			}
			
			if(this.excelFile != null && this.excelFile.exists()) {
				this.workbook = new XSSFWorkbook(this.excelFile);
			} else {
				this.workbook = new XSSFWorkbook();
			}
			
			this.excelSheets = excelSheets;
			this.factory = this.workbook.getCreationHelper();	
			this.anchor = factory.createClientAnchor();
			
			this.excelSheets.setValidator(workbook);
			
			FontType.initialize(this.workbook);
			CellStyle.initialize(this.workbook);
		} catch (Exception e) {
			throw new TemplateException(e);
		}
		
	}
	
	/**
	 * 
	 * @throws ValidateException
	 */
	public void createTemplate() throws ValidateException {
		this.createTemplate(null);
	}
	
	/**
	 * 
	 * @param abstractDataProvider
	 * @throws ValidateException
	 */
	public void createTemplate(AbstractDataProvider abstractDataProvider) throws ValidateException {
				
		if(abstractDataProvider != null) {
			abstractDataProvider.initialize(this.excelSheets);
		}
		
		for (ExcelSheet excelSheet : this.excelSheets) {

			Sheet sheet = ExcelHelper.getSheet(this.workbook, excelSheet.getSheetName());
			
			if(!excelSheet.isSkipSheet()) {
				
				Drawing<?> drawing = sheet.getDrawingPatriarch();
		        if(drawing == null) {
		        	drawing = sheet.createDrawingPatriarch();
		        }
		        
		        List<ExcelColumn> excelColumns = excelSheet.getExcelColumns();
		        
		        int rowNum = 1;
		        
		        // Make Sheet Title Row
		        if(excelSheet.hasSheetTitle()) {
		        	Row titleRow = ExcelHelper.getRow(sheet, rowNum);
		        	for (int iCol = 0; iCol < excelColumns.size(); iCol++) {
						
						Cell titleCell = ExcelHelper.getCell(titleRow, iCol);
						titleCell.setCellType(CellType.STRING);
			        	titleCell.setCellStyle(CellStyle.SHEET_TITLE_STYLE.getXSSFCellStyle());
			        	
						if(iCol == 0) {
			        	
				        	titleCell.setCellValue(excelSheet.getSheetTitle());
				        	if(excelColumns.size() > 1) {
				        		ExcelHelper.addMergeCell(sheet, titleCell, 0, excelColumns.size());
				        	}
				        	
						} else {
							titleCell.setCellValue("");
						}
							
		        	}
		        	
		        	rowNum++;
		        }
		        
		        // Make Explain Rows
		        for(int iRow = 0; iRow < excelSheet.getExplainRowSize(); iRow++) {
					Row explainRow = ExcelHelper.getRow(sheet, rowNum);
					for (int iCol = 0; iCol < excelColumns.size(); iCol++) {
						ExcelColumn excelColumn = excelColumns.get(iCol);
						
						Cell cell = ExcelHelper.getCell(explainRow, iCol);
						cell.setCellType(CellType.STRING);
						cell.setCellStyle(CellStyle.EXPLAIN_STYLE.getXSSFCellStyle());
						
						if(excelColumn.getExplainColumns().size() > iRow) {
							
							ExcelCell excelCell = excelColumn.getExplainColumns().get(iRow);
							cell.setCellValue((iCol + 1) + "\n" + excelCell.getValue() + "\n");
							
							ExcelHelper.addMergeCell(sheet, cell, excelCell.getRowMergeSize(), excelCell.getCellMergeSize());
							
							if(excelCell.getComment() != null) {
								cell.setCellComment(ExcelHelper.createComment(drawing, factory, anchor, excelCell.getComment()));
							}
							
						} else {
							cell.setCellValue((iCol + 1) + "\n");
						}
											
					}
					rowNum++;
		        }
				
		        // Make Header Rows
		        for(int iRow = 0; iRow < excelSheet.getHeaderRowSize(); iRow++) {
					Row headerRow = ExcelHelper.getRow(sheet, rowNum);
					for (int iCol = 0; iCol < excelColumns.size(); iCol++) {
						ExcelColumn excelColumn = excelColumns.get(iCol);
	
						Cell cell = ExcelHelper.getCell(headerRow, iCol);
						cell.setCellType(CellType.STRING);
						cell.setCellStyle(excelColumn.isRequired() ? CellStyle.REQUIRED_HEADER_STYLE.getXSSFCellStyle() : CellStyle.HEADER_STYLE.getXSSFCellStyle());
						
						if(excelColumn.getHeaderColumns().size() > iRow) {
						
							ExcelCell excelCell = excelColumn.getHeaderColumns().get(iRow);
							cell.setCellValue((excelColumn.isRequired() && !excelCell.isSkipRequired() ? "*\n" : "") + excelCell.getValue());
	
							ExcelHelper.addMergeCell(sheet, cell, excelCell.getRowMergeSize(), excelCell.getCellMergeSize());
							
							if(excelCell.getComment() != null) {
								cell.setCellComment(ExcelHelper.createComment(drawing, factory, anchor, excelCell.getComment()));
							}
							
						} else {
							cell.setCellValue("");
						}
						
					}
					rowNum++;
		        }
				
				// Use Sample Data Row
				if(excelSheet.isUseSampleData()) {
					Row sampleDataRow = ExcelHelper.getRow(sheet, rowNum);
					for (int i = 0; i < excelColumns.size(); i++) {
						ExcelColumn excelColumn = excelColumns.get(i);
						ExcelHelper.createDataCellValue(sheet, sampleDataRow, i, excelColumn, excelColumn.getSampleValue());
					}
					rowNum++;
				}
				
				if(abstractDataProvider != null) {
					abstractDataProvider.apply(excelSheet, sheet, excelColumns, rowNum);
				}
				
			}
			
			ExcelHelper.setAutoSizeColumn(this.workbook);
		}
		
		if(abstractDataProvider != null) {
			abstractDataProvider.finish();
		}
		
	}
		
	/**
	 * 
	 * @param rowDataListener
	 * @return
	 * @throws ValidateException 
	 */
	public void readTemplate(AbstractDataListener rowDataListener) throws ValidateException {
		
		boolean isValid = this.excelSheets.validate();
		
		rowDataListener.startHandle();
		
		for(int iSheet = 0; iSheet < this.workbook.getNumberOfSheets(); iSheet++) {
			
			ExcelSheet excelSheet = this.excelSheets.get(iSheet);
			if(!excelSheet.isSkipSheet()) {

				Sheet sheet = this.workbook.getSheetAt(iSheet);	
				rowDataListener.handle(iSheet, sheet, excelSheet.getExcelColumns());
				for(int iRow = excelSheet.getFirstDataRowIndex(); iRow <= sheet.getLastRowNum(); iRow++) {
					
					Row row = sheet.getRow(iRow);
					isValid = this.excelSheets.validate(iSheet, row);
					
					rowDataListener.handle(sheet, row);
					for(int iCol = 0; iCol < row.getLastCellNum(); iCol++) {
						
						Cell cell = row.getCell(iCol);
						isValid = this.excelSheets.validate(iSheet, cell);
						
						rowDataListener.handle(sheet, row, cell);
						
					}
					rowDataListener.afterRowHandle(sheet, row);
					
				}
				
				rowDataListener.endOfSheetHandle();
				
			}
			
		}
		
		rowDataListener.lastHandle();
		
		if(!isValid) {
			if(this.excelSheets.getValidateErrors().size() > 0) {
				throw new ValidateException(this.excelSheets.getValidateErrors());
			}
		}
		
	}
	
	/**
	 * 
	 * @param outputStream
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void save(OutputStream outputStream) throws FileNotFoundException, IOException {
		this.workbook.write(outputStream);
		this.workbook.close();
	}
	
}
