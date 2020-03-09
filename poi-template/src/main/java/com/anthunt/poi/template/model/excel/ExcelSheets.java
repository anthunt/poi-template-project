package com.anthunt.poi.template.model.excel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelSheets implements List<ExcelSheet> {

	private ArrayList<ExcelSheet> excelSheets;
	private ExcelValidator excelValidator;
	
	public ExcelSheets() {
		this.excelSheets = new ArrayList<ExcelSheet>();
	}
	
	public ExcelSheetBuilder builder() {
		return new ExcelSheetBuilder(this);
	}
	
	public void setValidator(Workbook workbook) {
		this.excelValidator = new ExcelValidator(workbook, this);
	}
	
	public boolean validate() {
		return this.excelValidator.validate();
	}

	public boolean validate(int sheetIndex, Cell cell) {
		return this.excelValidator.isValidCell(sheetIndex, cell);
	}

	public boolean validate(int sheetIndex, Row row) {
		return this.excelValidator.isValidRow(sheetIndex, row);
	}
	
	public ValidationErrors getValidateErrors() {
		return this.excelValidator.getValidateErrors();
	}

	@Override
	public boolean add(ExcelSheet excelSheet) {
		return this.excelSheets.add(excelSheet);
	}

	@Override
	public void add(int index, ExcelSheet excelSheet) {
		this.excelSheets.add(index, excelSheet);
	}

	@Override
	public boolean addAll(Collection<? extends ExcelSheet> excelSheets) {
		return this.excelSheets.addAll(excelSheets);
	}

	@Override
	public boolean addAll(int index, Collection<? extends ExcelSheet> excelSheets) {
		return this.excelSheets.addAll(index, excelSheets);
	}

	@Override
	public void clear() {
		this.excelSheets.clear();
	}

	@Override
	public boolean contains(Object obj) {
		return this.excelSheets.contains(obj);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return this.excelSheets.containsAll(collection);
	}

	@Override
	public ExcelSheet get(int index) {
		return this.excelSheets.get(index);
	}

	@Override
	public int indexOf(Object obj) {
		return this.excelSheets.indexOf(obj);
	}

	@Override
	public boolean isEmpty() {
		return this.excelSheets.isEmpty();
	}

	@Override
	public Iterator<ExcelSheet> iterator() {
		return this.excelSheets.iterator();
	}

	@Override
	public int lastIndexOf(Object obj) {
		return this.excelSheets.lastIndexOf(obj);
	}

	@Override
	public ListIterator<ExcelSheet> listIterator() {
		return this.excelSheets.listIterator();
	}

	@Override
	public ListIterator<ExcelSheet> listIterator(int index) {
		return this.excelSheets.listIterator(index);
	}

	@Override
	public boolean remove(Object obj) {
		return this.excelSheets.remove(obj);
	}

	@Override
	public ExcelSheet remove(int index) {
		return this.excelSheets.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return this.excelSheets.removeAll(collection);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		return this.excelSheets.retainAll(collection);
	}

	@Override
	public ExcelSheet set(int index, ExcelSheet excelSheet) {
		return this.excelSheets.set(index, excelSheet);
	}

	@Override
	public int size() {
		return this.excelSheets.size();
	}

	@Override
	public List<ExcelSheet> subList(int arg0, int arg1) {
		return this.excelSheets.subList(arg0, arg1);
	}

	@Override
	public Object[] toArray() {
		return this.excelSheets.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return this.excelSheets.toArray(arg0);
	}
	
}
