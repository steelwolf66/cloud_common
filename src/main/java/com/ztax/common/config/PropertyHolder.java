package com.ztax.common.config;

public class PropertyHolder {

    private static String excelWaterMarkContent;

    private static boolean enableExcelWaterMark;


    public static String getExcelWaterMarkContent() {
        return excelWaterMarkContent;
    }

    public static void setExcelWaterMarkContent(String excelWaterMarkContent) {
        PropertyHolder.excelWaterMarkContent = excelWaterMarkContent;
    }

    public static boolean isEnableExcelWaterMark() {
        return enableExcelWaterMark;
    }

    public static void setEnableExcelWaterMark(boolean enableExcelWaterMark) {
        PropertyHolder.enableExcelWaterMark = enableExcelWaterMark;
    }

}