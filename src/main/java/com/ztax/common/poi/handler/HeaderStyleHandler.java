package com.ztax.common.poi.handler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import com.ztax.common.poi.style.WriteCellStyleConstant;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.HashMap;
import java.util.Map;

/**
 * 表头样式处理器
 * @author BAOXIN
 * @version 1.0
 * company: 北京中税汇金智能科技有限公司 Copyright (c) 2020, 中税汇金 All Rights Reserved.
 * @date 2020/7/24 16:45
 */
public class HeaderStyleHandler extends AbstractCellStyleStrategy {

    private Workbook workbook;

    private Map<Integer, WriteCellStyle> headerRowIndexWriterCellStyleConfig;

    private Map<Integer, CellStyle> headerRowIndexCellStyleConfig;

    public HeaderStyleHandler(Map<Integer, WriteCellStyle> headerRowIndexWriterCellStyleConfig) {
        this.headerRowIndexWriterCellStyleConfig = headerRowIndexWriterCellStyleConfig;
    }

    @Override
    protected void initCellStyle(Workbook workbook) {
        this.workbook = workbook;
        if (headerRowIndexWriterCellStyleConfig != null) {
            headerRowIndexCellStyleConfig = new HashMap<>();
            headerRowIndexWriterCellStyleConfig.forEach((rowIndex,headWriteCellStyle) ->
                headerRowIndexCellStyleConfig.put(
                    rowIndex, StyleUtil.buildHeadCellStyle(workbook, headWriteCellStyle))
            );
        }
    }

    @Override
    protected void setHeadCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        if (headerRowIndexCellStyleConfig == null) {
            return;
        }
        CellStyle cellStyle;
        if ((cellStyle = headerRowIndexCellStyleConfig.get(relativeRowIndex)) == null) {
            cellStyle = StyleUtil.buildHeadCellStyle(
                    workbook, WriteCellStyleConstant.DEFAULT_HEADER_CELL_STYLE);
        }
        cell.setCellStyle(cellStyle);
    }

    @Override
    protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        return;
    }
}
