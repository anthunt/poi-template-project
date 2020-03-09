package com.anthunt.poi.mapper.model;

import java.util.ArrayList;
import java.util.List;

public class AdditionalColumns {
	
	private List<AdditionalColumn> additionalColumns;
	
	protected AdditionalColumns() {
		this.additionalColumns = new ArrayList<>();
	}
	
	protected AdditionalColumns(AdditionalColumns additionalColumns) {
		this.additionalColumns = new ArrayList<>(additionalColumns.getAdditionalColumns());
	}
	
	public List<AdditionalColumn> getAdditionalColumns() {
		return this.additionalColumns;
	}
	
	protected void add(AdditionalColumn additionalColumn) {
		this.additionalColumns.add(additionalColumn);
	}

}
