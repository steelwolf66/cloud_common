package com.ztax.common.poi.style;

import com.alibaba.excel.write.metadata.style.WriteFont;

/**
 * @author BAOXIN
 * @version 1.0
 * company: 北京中税汇金智能科技有限公司 Copyright (c) 2020, 中税汇金 All Rights Reserved.
 * @date 2020/7/24 17:47
 */
public class WriteFontConstant {

    public static final WriteFont DEFAULT_FONT;

    static {
        DEFAULT_FONT = new WriteFont();
        DEFAULT_FONT.setFontName("宋体");
        DEFAULT_FONT.setFontHeightInPoints((short)14);
        DEFAULT_FONT.setBold(true);
    }
}
