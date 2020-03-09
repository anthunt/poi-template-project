package com.anthunt.poi.mapper.model;

import java.util.ArrayList;
import java.util.List;

public class ColumnDefinitions extends ArrayList<ColumnDefinition>{

	private static final long serialVersionUID = -7902779448664820359L;

	public ColumnDefinitions() {}	
	public ColumnDefinitions(List<ColumnDefinition> columnDefinitions) {
		this.addAll(columnDefinitions);
	}
	
}
