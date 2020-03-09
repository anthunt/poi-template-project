package com.anthunt.poi.mapper.model;

import java.util.ArrayList;
import java.util.List;

public class CellDefinitions extends ArrayList<CellDefinition> {

	private static final long serialVersionUID = -2984305694580194443L;

	public CellDefinitions() {}
	public CellDefinitions(List<CellDefinition> cellDefinitions) {
		this.addAll(cellDefinitions);
	}
	
}
