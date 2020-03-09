package com.anthunt.poi.mapper.model;

public class ColumnConstraintDefinition {

	private int min;
	private int max;
	private String regExp;
	private boolean hasMin;
	private boolean hasMax;	
	private boolean hasRegExp;

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getRegExp() {
		return regExp;
	}

	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}

	public boolean isHasMin() {
		return hasMin;
	}

	public void setHasMin(boolean hasMin) {
		this.hasMin = hasMin;
	}

	public boolean isHasMax() {
		return hasMax;
	}

	public void setHasMax(boolean hasMax) {
		this.hasMax = hasMax;
	}

	public boolean isHasRegExp() {
		return hasRegExp;
	}

	public void setHasRegExp(boolean hasRegExp) {
		this.hasRegExp = hasRegExp;
	}
	
}
