package com.anthunt.poi.template.model.excel;

public class ExcelCell {

	private String value;
	private String comment;
	private boolean skipRequired;
	private int rowMergeSize;
	private int cellMergeSize;

	protected ExcelCell() {
		this.skipRequired = false;
		this.rowMergeSize = 0;
		this.cellMergeSize = 0;
	}
	
	public String getValue() {
		return new String(value);
	}

	protected void setValue(String value) {
		this.value = new String(value);
	}

	public String getComment() {
		return comment == null ? null : new String(comment);
	}

	protected void setComment(String comment) {
		this.comment = comment == null ? null : new String(comment);
	}

	public boolean isSkipRequired() {
		return new Boolean(skipRequired);
	}

	protected void setSkipRequired(boolean skipRequired) {
		this.skipRequired = new Boolean(skipRequired);
	}

	public int getRowMergeSize() {
		return new Integer(rowMergeSize);
	}

	protected void setRowMergeSize(int rowMergeSize) {
		if(rowMergeSize < 2) {
			throw new RuntimeException("Row Merge Size must 2 or more");
		}
		this.rowMergeSize = new Integer(rowMergeSize);
	}

	public int getCellMergeSize() {
		return new Integer(cellMergeSize);
	}

	protected void setCellMergeSize(int cellMergeSize) {
		if(cellMergeSize < 2) {
			throw new RuntimeException("Cell Merge Size must 2 or more");
		}
		this.cellMergeSize = new Integer(cellMergeSize);
	}
	
}
