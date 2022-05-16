package com.ztax.common.poi;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;

import java.awt.*;

/**
 * Excel  表头单元格描述器
 */
public class PubExcelHeaderCellDescriptor {

    /** 左上角坐标 */
    private Point startCoordinate;

    /** 右下角坐标 */
    private Point endCoordinate;

    /** 表头单元格内显示的内容 */
    private String headerCellContent;

    /** 表头单元格样式 */
    private WriteCellStyle cellStyle;

    public Point getStartCoordinate() {
        return startCoordinate;
    }

    public void setStartCoordinate(Point startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

    public Point getEndCoordinate() {
        return endCoordinate;
    }

    public void setEndCoordinate(Point endCoordinate) {
        this.endCoordinate = endCoordinate;
    }

    public String getHeaderCellContent() {
        return headerCellContent;
    }

    public void setHeaderCellContent(String headerCellContent) {
        this.headerCellContent = headerCellContent;
    }

    public WriteCellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(WriteCellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }
}
