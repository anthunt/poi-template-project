package com.anthunt.poi.template.model.excel;

public class ExcelColumnConstraint {

	private int min;
	private int max;
	private String regExp;
	private boolean hasMin;
	private boolean hasMax;
	private boolean hasRegExp;
	
	protected ExcelColumnConstraint() {
		this.hasMin = false;
		this.hasMax = false;
		this.hasRegExp = false;
	}
	
	public int getMin() {
		return new Integer(min);
	}

	protected void setMin(Integer min) {
		if(min != null) {
			this.min = new Integer(min);
			this.hasMin = true;
		}
	}

	public int getMax() {
		return new Integer(max);
	}

	protected void setMax(Integer max) {
		if(max != null) {
			this.max = new Integer(max);
			this.hasMax = true;
		}
	}

	public String getRegExp() {
		return new String(regExp);
	}

	protected void setRegExp(String regExp) {
		if(regExp != null) {
			this.regExp = new String(regExp);
			this.hasRegExp = true;
		}
	}

	public boolean hasMin() {
		return new Boolean(hasMin);
	}

	public boolean hasMax() {
		return new Boolean(hasMax);
	}

	public boolean hasRegExp() {
		return new Boolean(hasRegExp);
	}
	
}
