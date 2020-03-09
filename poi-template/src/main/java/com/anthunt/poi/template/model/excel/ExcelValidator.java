package com.anthunt.poi.template.model.excel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.anthunt.poi.template.helper.ExcelHelper;
import com.anthunt.poi.template.helper.PoiLogger;

public class ExcelValidator {
	
	private static PoiLogger logger = PoiLogger.getLogger(ExcelValidator.class);
	
	private Workbook workbook;
	private ExcelSheets excelSheets;
	private ValidationErrors validationErrors;
	
	protected ExcelValidator(Workbook workbook, ExcelSheets excelSheets) {
		this.workbook = workbook;
		this.excelSheets = excelSheets;
		this.validationErrors = new ValidationErrors();
	}
	
	protected boolean validate() {
		return this.isValidSheet();
	}
	
	private boolean isValidSheet() {
		boolean isValidSheet = true;
		if(this.excelSheets.size() != this.workbook.getNumberOfSheets()) {
			this.validationErrors.addError()
								 	.setErrorMessage("정의된 Sheet(" + this.excelSheets.size() + "개) 와 다른 Sheet(" + this.workbook.getNumberOfSheets() + "개)가 있습니다.")
								 	.and();
			isValidSheet = false;
		}
		
		for(ExcelSheet excelSheet : this.excelSheets) {
			Sheet sheet = this.workbook.getSheetAt(excelSheet.getSheetIndex());
			if(!excelSheet.getSheetName().equals(sheet.getSheetName())) {
				this.validationErrors.addError()
										.setSheetIdx(excelSheet.getSheetIndex())
										.setSheetName(excelSheet.getSheetName())
										.setErrorMessage(excelSheet.getSheetIndex() + " Sheet 명은 반드시 " + excelSheet.getSheetName() + "이어야 합니다.")
										.and();
				
				isValidSheet = false;
			}
			
		}
		return isValidSheet;
	}

	public boolean isValidRow(int sheetIndex, Row row) {
		boolean isValidRow = true;
		ExcelSheet excelSheet = this.excelSheets.get(sheetIndex);
		if(excelSheet.getExcelColumns().size() != row.getLastCellNum()) {
			this.validationErrors.addError()
									.setSheetIdx(sheetIndex)
									.setRowNum(row.getRowNum())
									.setErrorMessage("정의된 컬럼(" + this.excelSheets.size() + "개) 과 다른 컬럼(" + this.workbook.getNumberOfSheets() + "개)이 있습니다.")
									.and();
			isValidRow = false;
		}
		return isValidRow;
	}

	protected boolean isValidCell(int sheetIndex, Cell cell) {
		
		boolean isValidCell = true;
		ExcelSheet excelSheet = this.excelSheets.get(sheetIndex);
		ExcelColumn excelColumn = excelSheet.getExcelColumns().get(cell.getColumnIndex());
				
		switch(excelColumn.getDataCellType()) {
		
		case FORMULA :
			if(excelColumn.getDataCellType() != cell.getCellTypeEnum()) {
				this.validationErrors.addError()
										.setSheetIdx(sheetIndex)
										.setRowNum(cell.getRowIndex())
										.setColumnIndex(cell.getColumnIndex())
										.setErrorMessage("계산식 컬럼이 아닙니다. - " + cell.getCellTypeEnum() + "]")
										.and();
				isValidCell = false;
			} else {
				String definedFormula = ExcelHelper.getParsedExcelFormula(excelColumn.getColumnFormula(), cell.getRowIndex());
				if(!definedFormula.equals(cell.getCellFormula())) {
					this.validationErrors.addError()
											.setSheetIdx(sheetIndex)
											.setRowNum(cell.getRowIndex())
											.setColumnIndex(cell.getColumnIndex())
											.setErrorMessage("계산 식이 변형 되었습니다. [원본 - " + definedFormula + "] [변경본 - " + cell.getCellFormula() + "]")
											.and();
					isValidCell = false;
				}
			}
			break;
			
		case NUMERIC :
			
			try {
				
				double value = cell.getNumericCellValue();
				if(excelColumn.hasExcelColumnConstraint()) {
					ExcelColumnConstraint excelColumnConstraint = excelColumn.getExcelColumnConstraint();
					if(excelColumnConstraint.hasMin()) {
						if(value < excelColumnConstraint.getMin()) {
							this.validationErrors.addError()
													.setSheetIdx(sheetIndex)
													.setRowNum(cell.getRowIndex())
													.setColumnIndex(cell.getColumnIndex())
													.setErrorMessage(value + " 는  " + excelColumnConstraint.getMin() + " 보다 작거나 같아야 합니다.")
													.and();
							isValidCell = false;
						}
					}
					if(excelColumnConstraint.hasMax()) {
						if(value > excelColumnConstraint.getMax()) {
							this.validationErrors.addError()
													.setSheetIdx(sheetIndex)
													.setRowNum(cell.getRowIndex())
													.setColumnIndex(cell.getColumnIndex())
													.setErrorMessage(value + " 는 " + excelColumnConstraint.getMin() + " 보다 커야 합니다.")
													.and();
							isValidCell = false;
						}
					}
					if(excelColumnConstraint.hasRegExp()) {
						Pattern pattern = Pattern.compile(excelColumnConstraint.getRegExp());
						Matcher matcher = pattern.matcher(Double.toString(value));
						if(!matcher.find() || !Double.toString(value).equals(matcher.group())) {
							this.validationErrors.addError()
													.setSheetIdx(sheetIndex)
													.setRowNum(cell.getRowIndex())
													.setColumnIndex(cell.getColumnIndex())
													.setErrorMessage(value + " 는 표현식과 다른 값입니다. [표현식 - " + excelColumnConstraint.getRegExp() + "]")
													.and();
							isValidCell = false;
						}
					}
				}
				
			} catch(IllegalStateException e) {
				this.validationErrors.addError()
										.setSheetIdx(sheetIndex)
										.setRowNum(cell.getRowIndex())
										.setColumnIndex(cell.getColumnIndex())
										.setErrorMessage("숫자형식 컬럼이 아닙니다. - " + e.getMessage())
										.and();
				isValidCell = false;
			} catch(NumberFormatException e) {
				this.validationErrors.addError()
										.setSheetIdx(sheetIndex)
										.setRowNum(cell.getRowIndex())
										.setColumnIndex(cell.getColumnIndex())
										.setErrorMessage("숫자형식이 잘못되었습니다. - " + e.getMessage())
										.and();
				isValidCell = false;
			}
			
			break;
			
		default :
			
			String value = cell.getStringCellValue();
			
			if(excelColumn.hasDataExplicits()) {
				
				if(!excelColumn.getDataExplicitList().contains(value)) {
					this.validationErrors.addError()
											.setSheetIdx(sheetIndex)
											.setRowNum(cell.getRowIndex())
											.setColumnIndex(cell.getColumnIndex())
											.setErrorMessage(value + " 는 유효하지 않은 선택 값입니다.")
											.and();
					isValidCell = false;
				}
				
			} else if(excelColumn.hasExcelColumnConstraint()) {
				
				ExcelColumnConstraint excelColumnConstraint = excelColumn.getExcelColumnConstraint();
				if(excelColumnConstraint.hasMin()) {
					if(value.length() < excelColumnConstraint.getMin()) {
						this.validationErrors.addError()
												.setSheetIdx(sheetIndex)
												.setRowNum(cell.getRowIndex())
												.setColumnIndex(cell.getColumnIndex())
												.setErrorMessage(value.length() + " 는 최소 " + excelColumnConstraint.getMin() + " 자리 이상이여야 합니다.")
												.and();
						isValidCell = false;
					}
				}
				
				if(excelColumnConstraint.hasMax()) {
					if(value.length() > excelColumnConstraint.getMax()) {
						this.validationErrors.addError()
												.setSheetIdx(sheetIndex)
												.setRowNum(cell.getRowIndex())
												.setColumnIndex(cell.getColumnIndex())
												.setErrorMessage(value.length() + " 는 최대 " + excelColumnConstraint.getMin() + " 자리 미만이어야 합니다.")
												.and();
						isValidCell = false;
					}
				}
				
				if(excelColumnConstraint.hasRegExp()) {
					Pattern pattern = Pattern.compile(excelColumnConstraint.getRegExp());
					Matcher matcher = pattern.matcher(Double.toString(cell.getNumericCellValue()));
					this.validationErrors.addError()
											.setSheetIdx(sheetIndex)
											.setRowNum(cell.getRowIndex())
											.setColumnIndex(cell.getColumnIndex())
											.setErrorMessage(value + " 는 표현식과 다른 값입니다. [표현식 - " + excelColumnConstraint.getRegExp() + "]")
											.and();
					isValidCell = matcher.find();
				}
				
			}
			
			break;
			
		}
		
		return isValidCell;
	}

	public ValidationErrors getValidateErrors() {
		return this.validationErrors;
	}
	
}
