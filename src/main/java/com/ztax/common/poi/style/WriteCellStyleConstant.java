package com.ztax.common.poi.style;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * @author BAOXIN
 * @version 1.0
 * company: 北京中税汇金智能科技有限公司 Copyright (c) 2020, 中税汇金 All Rights Reserved.
 * @date 2020/7/24 17:44
 */
public class WriteCellStyleConstant {

    public static final WriteCellStyle DEFAULT_HEADER_CELL_STYLE;

    public static final WriteCellStyle WRITE_FOREGROUND_CELL_STYLE;

    public static final WriteCellStyle DEFAULT_LEFT_ALIGNMENT_HEADER_CELL_STYLE;

    static {
        DEFAULT_HEADER_CELL_STYLE = new WriteCellStyle();
        DEFAULT_HEADER_CELL_STYLE.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        DEFAULT_HEADER_CELL_STYLE.setWriteFont(WriteFontConstant.DEFAULT_FONT);

        DEFAULT_LEFT_ALIGNMENT_HEADER_CELL_STYLE = new WriteCellStyle();
        DEFAULT_LEFT_ALIGNMENT_HEADER_CELL_STYLE.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        DEFAULT_LEFT_ALIGNMENT_HEADER_CELL_STYLE.setWriteFont(WriteFontConstant.DEFAULT_FONT);
        DEFAULT_LEFT_ALIGNMENT_HEADER_CELL_STYLE.setHorizontalAlignment(HorizontalAlignment.LEFT);

        WRITE_FOREGROUND_CELL_STYLE = new WriteCellStyle();
        WRITE_FOREGROUND_CELL_STYLE.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WRITE_FOREGROUND_CELL_STYLE.setBorderTop(BorderStyle.NONE);
        WRITE_FOREGROUND_CELL_STYLE.setBorderLeft(BorderStyle.NONE);
        WRITE_FOREGROUND_CELL_STYLE.setBorderRight(BorderStyle.NONE);
        WRITE_FOREGROUND_CELL_STYLE.setBorderBottom(BorderStyle.NONE);

    }

}
