package com.anthunt.poi.mapper.model;

public class CellDefinition {
	
	private String value;
	private String comment;
	private boolean skipRequired;
	private Integer rowMergeSize;	
	private Integer cellMergeSize;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isSkipRequired() {
		return skipRequired;
	}

	public void setSkipRequired(boolean skipRequired) {
		this.skipRequired = skipRequired;
	}

	public Integer getRowMergeSize() {
		return rowMergeSize;
	}

	public void setRowMergeSize(Integer rowMergeSize) {
		this.rowMergeSize = rowMergeSize;
	}

	public Integer getCellMergeSize() {
		return cellMergeSize;
	}

	public void setCellMergeSize(Integer cellMergeSize) {
		this.cellMergeSize = cellMergeSize;
	}
	
}
