package com.anthunt.poi.template.model.excel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;

import com.anthunt.poi.template.model.enums.CellStyle;
import com.anthunt.poi.template.model.enums.NumericFormatType;

public class ExcelColumn {

	public enum ExcelCellType {
		EXPLAIN
		, HEADER
	}
	
	private List<ExcelCell> headerColumns;
	private List<ExcelCell> explainColumns;
	
	private CellType dataCellType;
	private NumericFormatType numericFormatType;
	private String columnFormula;
	
	private boolean isRequired;
	
	private String[] dataExplicits;
	private String sampleValue;
	
	private ExcelColumnConstraint excelColumnConstraint;
	
	private List<ExcelDBColumn> excelDBColumns;
	
	protected ExcelColumn() {
		this.headerColumns = new ArrayList<ExcelCell>();
		this.explainColumns = new ArrayList<ExcelCell>();
		this.excelDBColumns = new ArrayList<ExcelDBColumn>();
		this.isRequired = false;
	}
	
	public List<ExcelCell> getHeaderColumns() {
		return new ArrayList<ExcelCell>(this.headerColumns);
	}
	
	protected void addHeaderColumn(ExcelCell excelCell) {
		this.headerColumns.add(excelCell);
	}
	
	public List<ExcelCell> getExplainColumns() {
		return new ArrayList<ExcelCell>(this.explainColumns);
	}
	
	protected void addExplainColumn(ExcelCell excelCell) {
		this.explainColumns.add(excelCell);
	}
	
	public List<ExcelDBColumn> getDBColumns() {
		return new ArrayList<ExcelDBColumn>(this.excelDBColumns);
	}

	protected void addDBColumn(ExcelDBColumn excelDBColumn) {
		this.excelDBColumns.add(excelDBColumn);
	}
	
	public CellType getDataCellType() {
		return CellType.valueOf(dataCellType.name());
	}
	
	protected void setDataCellType(CellType dataCellType) {
		this.dataCellType = CellType.valueOf(dataCellType.name());
	}
	
	public String getColumnFormula() {
		return new String(columnFormula);
	}

	protected void setColumnFormula(String columnFormula) {
		this.columnFormula = columnFormula == null ? null : new String(columnFormula);
	}

	public boolean isRequired() {
		return isRequired;
	}
	
	protected void setRequired(boolean isRequired) {
		this.isRequired = new Boolean(isRequired);
	}
	
	public boolean hasDataExplicits() {
		return this.dataExplicits == null ? false : true;
	}

	public List<String> getDataExplicitList() {
		return Arrays.asList(dataExplicits);
	}
	
	public String[] getDataExplicits() {
		String[] c = null;
		if(this.dataExplicits != null) {
			c = new String[this.dataExplicits.length];
			System.arraycopy(this.dataExplicits, 0, c, 0, this.dataExplicits.length);
		}
		return c;
	}

	protected void setDataExplicits(String[] dataExplicits) {
		if(dataExplicits != null) {
			this.dataExplicits = new String[dataExplicits.length];
			System.arraycopy(dataExplicits, 0, this.dataExplicits, 0, dataExplicits.length);
		}
	}

	public String getSampleValue() {
		return new String(sampleValue == null ? "" : sampleValue);
	}

	protected void setSampleValue(String sampleValue) {
		this.sampleValue = sampleValue == null ? null : new String(sampleValue);
	}
	
	public NumericFormatType getNumericFormatType() {
		return this.numericFormatType == null ? null : NumericFormatType.valueOf(this.numericFormatType.name());
	}

	protected void setNumericFormatType(NumericFormatType numericFormatType) {
		this.numericFormatType = numericFormatType == null ? null : NumericFormatType.valueOf(numericFormatType.name());
	}

	public CellStyle getDataCellStyle() {
		
		CellStyle cellStyle = CellStyle.DEFAULT_STYLE;
		
		if(CellType.FORMULA == this.getDataCellType()) {
			if(this.getNumericFormatType() != null) {
				switch (this.getNumericFormatType()) {
				case INTEGER:
					cellStyle = CellStyle.FORMULA_INT_STYLE;
					break;
				case FLOAT1:
					cellStyle = CellStyle.FORMULA_FLOAT1_STYLE;
					break;
				case FLOAT2:
					cellStyle = CellStyle.FORMULA_FLOAT2_STYLE;
					break;
				case FLOAT3:
					cellStyle = CellStyle.FORMULA_FLOAT3_STYLE;
					break;
				case FLOAT4:
					cellStyle = CellStyle.FORMULA_FLOAT4_STYLE;
					break;
				default:
					cellStyle = CellStyle.FORMULA_DATA_STYLE;
					break;
				}
			}
		} else if(CellType.NUMERIC == this.getDataCellType()) {
			if(this.isRequired()) {
				switch (this.getNumericFormatType()) {
				case INTEGER:
					cellStyle = CellStyle.REQUIRED_INT_STYLE;
					break;
				case FLOAT1:
					cellStyle = CellStyle.REQUIRED_FLOAT1_STYLE;
					break;
				case FLOAT2:
					cellStyle = CellStyle.REQUIRED_FLOAT2_STYLE;
					break;
				case FLOAT3:
					cellStyle = CellStyle.REQUIRED_FLOAT3_STYLE;
					break;
				case FLOAT4:
					cellStyle = CellStyle.REQUIRED_FLOAT4_STYLE;
					break;
				default:
					cellStyle = CellStyle.REQUIRED_DATA_STYLE;
					break;
				}
			} else {
				switch (this.getNumericFormatType()) {
				case INTEGER:
					cellStyle = CellStyle.INT_STYLE;
					break;
				case FLOAT1:
					cellStyle = CellStyle.FLOAT1_STYLE;
					break;
				case FLOAT2:
					cellStyle = CellStyle.FLOAT2_STYLE;
					break;
				case FLOAT3:
					cellStyle = CellStyle.FLOAT3_STYLE;
					break;
				case FLOAT4:
					cellStyle = CellStyle.FLOAT4_STYLE;
					break;
				default:
					cellStyle = CellStyle.DEFAULT_STYLE;
					break;
				}
			}
		} else {
			if(this.isRequired()) {
				cellStyle = CellStyle.REQUIRED_DATA_STYLE;
			}
		}
		
		return cellStyle;
	}
	
	public boolean hasExcelColumnConstraint() {
		return this.getExcelColumnConstraint() == null ? false : true;
	}

	public ExcelColumnConstraint getExcelColumnConstraint() {
		return excelColumnConstraint;
	}

	protected void setExcelColumnConstraint(ExcelColumnConstraint excelColumnConstraint) {
		this.excelColumnConstraint = excelColumnConstraint;
	}
	
}
