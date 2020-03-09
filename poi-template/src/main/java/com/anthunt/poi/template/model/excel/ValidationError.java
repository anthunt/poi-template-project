package com.anthunt.poi.template.model.excel;

public class ValidationError {
	
	public enum ValidationType {
		WORKBOOK
		, SHEET
		, ROW
		, CELL
	}
	
	private Integer sheetIdx;
	private String sheetName;
	private Integer rowNum;
	private Integer columnIndex;
	private String cellValue;
	private String errorMessage;
	
	protected ValidationError() {}
		
	public Integer getSheetIdx() {
		return sheetIdx;
	}
	
	public void setSheetIdx(int sheetIdx) {
		this.sheetIdx = sheetIdx;
	}
	
	public String getSheetName() {
		return sheetName;
	}
	
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	public Integer getRowNum() {
		return rowNum;
	}
	
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	
	public Integer getColumnIndex() {
		return columnIndex;
	}
	
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
	
	public String getCellValue() {
		return cellValue;
	}
	
	public void setCellValue(String cellValue) {
		this.cellValue = cellValue;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public ValidationType getValidationType() {
		
		ValidationType validationType = null;
		
		if(this.getCellValue() != null || this.getColumnIndex() != null) {
			validationType = ValidationType.CELL;
		} else if(this.getRowNum() != null) {
			validationType = ValidationType.ROW;
		} else if(this.getSheetIdx() != null || this.getSheetName() != null) {
			validationType = ValidationType.SHEET;
		} else {
			validationType = ValidationType.WORKBOOK;
		}
		
		return validationType; 
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		if(this.getSheetIdx() != null) {
			if(sb.length() > 0) {
				sb.append(", ");
			}
			sb.append("sheet index : ");
			sb.append(this.getSheetIdx());
		}
		
		if(this.getSheetName() != null) {
			if(sb.length() > 0) {
				sb.append(", ");
			}
			sb.append("sheet name : ");
			sb.append(this.getSheetName());
		}
		
		if(this.getRowNum() != null) {
			if(sb.length() > 0) {
				sb.append(", ");
			}
			sb.append("row : ");
			sb.append(this.getRowNum());
		}
		
		if(this.getColumnIndex() != null) {
			if(sb.length() > 0) {
				sb.append(", ");
			}
			sb.append("cell : ");
			sb.append(this.getColumnIndex());
		}
		
		sb.append(", error [");
		sb.append(this.getErrorMessage());
		sb.append("]");
		
		return sb.toString();
	}
	
}

