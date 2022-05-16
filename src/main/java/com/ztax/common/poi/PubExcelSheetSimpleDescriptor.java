package com.ztax.common.poi;

/**
 * excel sheet 页信息简单描述
 * @param <T> 查询条件表头类型 String or PubExcelHeaderCellDescriptor
 */
public class PubExcelSheetSimpleDescriptor<T> {

    private String sheetName;

    private PubExcelSheetTableSimpleDescriptor<T> sheetTableSimpleDescriptor;

    private PubExcelSheetSimpleDescriptor(){}

    public PubExcelSheetSimpleDescriptor(String sheetName, PubExcelSheetTableSimpleDescriptor<T> sheetTableSimpleDescriptor) {
        this.sheetName = sheetName;
        this.sheetTableSimpleDescriptor = sheetTableSimpleDescriptor;
    }

    public String getSheetName() {
        return sheetName;
    }

    public PubExcelSheetTableSimpleDescriptor<T> getSheetTableSimpleDescriptor() {
        return sheetTableSimpleDescriptor;
    }
}
