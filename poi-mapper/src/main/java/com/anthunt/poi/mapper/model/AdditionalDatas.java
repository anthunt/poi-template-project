package com.anthunt.poi.mapper.model;

import java.util.HashMap;

public class AdditionalDatas {
			
	private HashMap<Integer, AdditionalColumns> additionalDatas;
	
	protected AdditionalDatas() {
		this.additionalDatas = new HashMap<>();
	}
	
	protected HashMap<Integer, AdditionalColumns> getAdditionalDatas() {
		return this.additionalDatas;
	}
	
	public AdditionalColumns getAdditionalColumns(int sheetIdx) {
		return this.additionalDatas.containsKey(sheetIdx) ? new AdditionalColumns(this.additionalDatas.get(sheetIdx)) : new AdditionalColumns();
	}
	
}
