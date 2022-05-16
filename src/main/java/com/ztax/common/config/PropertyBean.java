package com.ztax.common.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertyBean implements InitializingBean {

    @Value("${excel-water-mark-content:Water Mark}")
    private String excelWaterMarkContent;

    @Value("${enable-excel-water-mark:false}")
    private boolean enableExcelWaterMark;


    @Override
    public void afterPropertiesSet(){
        PropertyHolder.setExcelWaterMarkContent(excelWaterMarkContent);
        PropertyHolder.setEnableExcelWaterMark(enableExcelWaterMark);
    }
}
