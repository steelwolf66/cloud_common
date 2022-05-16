package com.ztax.common.poi;

import java.util.List;

/**
 * Excel  Sheet页描述器
 */
public class PubExcelSheetDescriptor {

    /** sheet页名称 */
    private String sheetName;

    /** 表格描述 */
    private List<PubExcelSheetTableDescriptor> tableList;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<PubExcelSheetTableDescriptor> getTableList() {
        return tableList;
    }

    public void setTableList(List<PubExcelSheetTableDescriptor> tableList) {
        this.tableList = tableList;
    }
}
