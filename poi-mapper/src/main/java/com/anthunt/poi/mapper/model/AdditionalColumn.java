package com.anthunt.poi.mapper.model;

public class AdditionalColumn {
	
	private String columnName;
	private String columnValue;
	private String operation;
	private UseType useType;
	private boolean isPrimary; 
	private boolean useSingleQuote;
	
	protected AdditionalColumn() {
		this.setPrimary(false);
		this.setUseSingleQuote(true);
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnValue() {
		return columnValue;
	}
	
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public UseType getUseType() {
		return useType;
	}
	
	public void setUseType(UseType useType) {
		this.useType = useType;
	}
	
	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public boolean checkUseType(UseType useType) {
		boolean isUseType = false;
		switch (this.getUseType()) {
		case INSERT:
			if(this.getUseType() == useType) {
				isUseType = true;
			}
			break;
		case UPDATE:
			if(this.getUseType() == useType) {
				isUseType = true;
			}
			break;
		case INSERT_UPDATE:
			if(this.getUseType() == useType) {
				isUseType = true;
			}
			break;
		case SEARCH:
			if(this.getUseType() == useType) {
				isUseType = true;
			}
			break;
		default:
			isUseType = true;
			break;
		}
		return isUseType;
	}

    /**
     * @return the useSingleQuote
     */
    public boolean isUseSingleQuote() {
        return useSingleQuote;
    }

    /**
     * @param useSingleQuote the useSingleQuote to set
     */
    public void setUseSingleQuote(boolean useSingleQuote) {
        this.useSingleQuote = useSingleQuote;
    }
	
}