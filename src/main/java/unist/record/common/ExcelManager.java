package unist.record.common;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelManager {
	private Workbook workbook;
	private CellStyle style;
	private Sheet sheet;
	private Row row;
	private Cell cell;
	private int rowIdx = 0;
	private int cellIdx = 0;
	
	public ExcelManager() {
		workbook = new HSSFWorkbook();
		style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		sheet = workbook.createSheet();
	}
	public Workbook getWorkbook() {
		return workbook;
	}
	
	public void setRow(int idx) {
		rowIdx = idx;
		row = sheet.createRow(idx);
	}
	public void setCell(int idx) {
		cellIdx = idx;
		cell = row.createCell(idx);
	}
	public void nextRow() {
		row = sheet.createRow(++rowIdx);
	}
	public void nextCell() {
		cell = row.createCell(++cellIdx);
	}
	
	public void setCellValue(String val) {
		cell.setCellValue(val);
		cell.setCellStyle(style);
		cell = row.createCell(++cellIdx);
	}
	
	public void setMergeCellValue(String val, int margeRow, int mergeCol) {
		sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx + margeRow, cellIdx, cellIdx + mergeCol));
		cell.setCellValue(val);
		cell.setCellStyle(style);
		cellIdx += mergeCol;
		cell = row.createCell(++cellIdx);
	}

	/*
	sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
	sheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 1));
	sheet.addMergedRegion(new CellRangeAddress(2, 3, 2, 2));
	sheet.addMergedRegion(new CellRangeAddress(2, 3, 3, 3));
	
	row = sheet.createRow(2);
	cell = row.createCell(0);
	cell.setCellValue("순번");
	cell = row.createCell(1);
	cell.setCellValue("소속");
	*/
}
