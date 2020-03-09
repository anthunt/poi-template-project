package com.anthunt.poi.mapper.model;

import java.util.HashMap;

public class AdditionalDatasBuilder {

	private AdditionalDatas additionalDatas;
	
	private AdditionalDatasBuilder() {
		this.additionalDatas = new AdditionalDatas();
	}
	
	public static AdditionalDatasBuilder newBuilder() {
		return new AdditionalDatasBuilder();
	}
	
	public AdditionalColumnBuilder addAdditionalColumns(int sheetIdx) {
		
		HashMap<Integer, AdditionalColumns> additionalDatas = this.additionalDatas.getAdditionalDatas();
		AdditionalColumns additionalColumns = null;
		if(additionalDatas.containsKey(sheetIdx)) {
			additionalColumns = additionalDatas.get(sheetIdx);
		} else {
			additionalColumns = new AdditionalColumns();
			additionalDatas.put(sheetIdx, additionalColumns);	
		}
		
		return new AdditionalColumnBuilder(this, additionalColumns);
	}

	public AdditionalDatas build() {
		return this.additionalDatas;
	}	
	
}
