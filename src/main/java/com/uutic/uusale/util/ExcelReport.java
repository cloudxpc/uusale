package com.uutic.uusale.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class ExcelReport {
    private enum Styles {
        Title,
        Text,
        DateTime,
        ColumnHeader,
        TextValue,
        CurrencyValue,
        DateValue,
        TimeValue,
        DateTimeValue
    }

    private Workbook wb;
    private Sheet sheet;
    private Row row;
    private Cell cell;
    private int rowIndex = -1;
    private int cellIndex = -1;
    private Map<Styles, CellStyle> styles;

    public ExcelReport() {
        wb = new XSSFWorkbook();
        styles = new HashMap<>();

        initStyles();
    }

    private void initStyleTitle() {
        CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        styles.put(Styles.Title, cellStyle);
    }

    private void initStyleText() {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        styles.put(Styles.Text, cellStyle);
    }

    private void initStyleDateTime() {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setDataFormat(wb.createDataFormat().getFormat("yyyy/MM/dd HH:mm:ss"));
        styles.put(Styles.DateTime, cellStyle);
    }

    private void initStyleColumnHeader() {
        CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        styles.put(Styles.ColumnHeader, cellStyle);
    }

    private void initStyleTextValue() {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        styles.put(Styles.TextValue, cellStyle);
    }

    private void initStyleCurrencyValue() {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        cellStyle.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
        styles.put(Styles.CurrencyValue, cellStyle);
    }

    private void initStyleDateValue() {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setDataFormat(wb.createDataFormat().getFormat("yyyy/MM/dd"));
        styles.put(Styles.DateValue, cellStyle);
    }

    private void initStyleTimeValue() {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setDataFormat(wb.createDataFormat().getFormat("HH:mm:ss"));
        styles.put(Styles.TimeValue, cellStyle);
    }

    private void initStyleDateTimeValue() {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setDataFormat(wb.createDataFormat().getFormat("yyyy/MM/dd HH:mm:ss"));
        styles.put(Styles.DateTimeValue, cellStyle);
    }

    private void initStyles() {
        initStyleTitle();
        initStyleText();
        initStyleDateTime();
        initStyleColumnHeader();
        initStyleTextValue();
        initStyleCurrencyValue();
        initStyleDateValue();
        initStyleTimeValue();
        initStyleDateTimeValue();
    }

    public void save(String filePath) throws IOException {
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(filePath);
            wb.write(fileOut);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    public byte[] save() throws IOException {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            wb.write(os);
            return os.toByteArray();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public ExcelReport createSheet(String name) {
        sheet = wb.createSheet(name);
        return this;
    }

    public ExcelReport createRow() {
        row = sheet.createRow(++rowIndex);
        cellIndex = -1;
        return this;
    }

    public ExcelReport setRowHeight(float height) {
        row.setHeightInPoints(height);
        return this;
    }

    public ExcelReport createCell() {
        cell = row.createCell(++cellIndex);
        return this;
    }

    public ExcelReport createCell(String value) {
        createCell();
        cell.setCellValue(value);
        return this;
    }

    public ExcelReport createCell(Date value) {
        createCell();
        cell.setCellValue(value);
        return this;
    }

    public ExcelReport createCell(double value) {
        createCell();
        cell.setCellValue(value);
        return this;
    }

    public ExcelReport asTitle() {
        cell.setCellStyle(styles.get(Styles.Title));
        return this;
    }

    public ExcelReport asColumnHeader() {
        cell.setCellStyle(styles.get(Styles.ColumnHeader));
        return this;
    }

    public ExcelReport asText() {
        cell.setCellStyle(styles.get(Styles.Text));
        return this;
    }

    public ExcelReport asDateTime() {
        cell.setCellStyle(styles.get(Styles.DateTime));
        return this;
    }

    public ExcelReport asTextValue() {
        cell.setCellStyle(styles.get(Styles.TextValue));
        return this;
    }

    public ExcelReport asCurrencyValue() {
        cell.setCellStyle(styles.get(Styles.CurrencyValue));
        return this;
    }

    public ExcelReport asDateValue() {
        cell.setCellStyle(styles.get(Styles.DateValue));
        return this;
    }

    public ExcelReport asTimeValue() {
        cell.setCellStyle(styles.get(Styles.TimeValue));
        return this;
    }

    public ExcelReport asDateTimeValue() {
        cell.setCellStyle(styles.get(Styles.DateTimeValue));
        return this;
    }

    public ExcelReport mergeCell(int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
        return this;
    }

    public ExcelReport span(int columnCount) {
        for (int i = 0; i < columnCount - 1; i++) {
            setStringValue(null);
        }
        mergeCell(rowIndex, rowIndex, cellIndex - columnCount + 1, cellIndex);
        return this;
    }

    public ExcelReport setTitle(String title) {
        createRow().setRowHeight(27).createCell(title).asTitle();
        mergeCell(0, 0, 0, 3);
        createRow().setRowHeight(20).createCell("日期: ").asText().createCell(Calendar.getInstance().getTime()).asDateTime();
        return this;
    }

    public ExcelReport setColumnHeader(String[] headers) {
        createRow().setRowHeight(20);
        for (String header : headers) {
            createCell(header).asColumnHeader();
        }
        return this;
    }

    public ExcelReport setStringValue(String value) {
        createCell(value).asTextValue();
        return this;
    }

    public ExcelReport setDateValue(Date value) {
        createCell(value).asDateValue();
        return this;
    }

    public ExcelReport setTimeValue(Date value) {
        createCell(value).asTimeValue();
        return this;
    }

    public ExcelReport setDateTimeValue(Date value) {
        createCell(value).asDateTimeValue();
        return this;
    }

    public ExcelReport setCurrencyValue(BigDecimal value) {
        createCell(value != null ? value.doubleValue() : null).asCurrencyValue();
        return this;
    }
}
