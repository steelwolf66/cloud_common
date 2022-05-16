package com.ztax.common.poi;

import com.alibaba.excel.write.handler.CellWriteHandler;

import java.util.List;

/**
 * Excel  Sheet页内Table描述器
 */
public class PubExcelSheetTableDescriptor {

    /** 是否有表头 */
    private boolean needHeader;

    /** 表头总列数 */
    private int headerColumnCount;

    /** 表头总行数 */
    private int headerRowCount;

    /** 表头单元格描述 */
    private List<PubExcelHeaderCellDescriptor> headerCellList;

    /** 表格分页数据集 */
    private List<List<List<Object>>> pageDataList;

    /** 自定义拦截器链 */
    private List<CellWriteHandler> cellWriteHandlerChain;

    public boolean isNeedHeader() {
        return needHeader;
    }

    public void setNeedHeader(boolean needHeader) {
        this.needHeader = needHeader;
    }

    public int getHeaderColumnCount() {
        return headerColumnCount;
    }

    public void setHeaderColumnCount(int headerColumnCount) {
        this.headerColumnCount = headerColumnCount;
    }

    public int getHeaderRowCount() {
        return headerRowCount;
    }

    public void setHeaderRowCount(int headerRowCount) {
        this.headerRowCount = headerRowCount;
    }

    public List<PubExcelHeaderCellDescriptor> getHeaderCellList() {
        return headerCellList;
    }

    public void setHeaderCellList(List<PubExcelHeaderCellDescriptor> headerCellList) {
        this.headerCellList = headerCellList;
    }

    public List<List<List<Object>>> getPageDataList() {
        return pageDataList;
    }

    public void setPageDataList(List<List<List<Object>>> pageDataList) {
        this.pageDataList = pageDataList;
    }

    public List<CellWriteHandler> getCellWriteHandlerChain() {
        return cellWriteHandlerChain;
    }

    public void setCellWriteHandlerChain(List<CellWriteHandler> cellWriteHandlerChain) {
        this.cellWriteHandlerChain = cellWriteHandlerChain;
    }
}
