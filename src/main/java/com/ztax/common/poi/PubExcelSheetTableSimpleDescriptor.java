package com.ztax.common.poi;

import com.ztax.common.exception.BizException;
import com.ztax.common.utils.ClassUtils;
import com.ztax.common.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * excel sheet table 信息简单描述
 * @param <T> 查询条件表头类型 String or PubExcelHeaderCellDescriptor
 */
public class PubExcelSheetTableSimpleDescriptor<T> {
    private String tableTitle;
    private List<T> qryCondHeaderList;
    private List<String> tableHeaderList;
    private List<List<List<Object>>> pagingTableDataCollection;

    private PubExcelSheetTableSimpleDescriptor(){}

    private static Logger logger = LoggerFactory.getLogger(PubExcelSheetTableSimpleDescriptor.class);
    public PubExcelSheetTableSimpleDescriptor(String tableTitle, List<T> qryCondHeaderList, List<String> tableHeaderList, List<List<List<Object>>> pagingTableDataCollection) {
        this.tableTitle = tableTitle;
        this.qryCondHeaderList = qryCondHeaderList;
        this.tableHeaderList = tableHeaderList;
        this.pagingTableDataCollection = pagingTableDataCollection;
    }

    public PubExcelSheetTableSimpleDescriptor(String tableTitle, List<T> qryCondHeaderList,
                                              List<String> tableHeaderList, List<String> tableHeaderFieldList, List<Object> beanList) {
        this.tableTitle = tableTitle;
        this.qryCondHeaderList = qryCondHeaderList;
        this.tableHeaderList = tableHeaderList;
        initDataByClz(tableHeaderFieldList, beanList);
    }

    private void initDataByClz(List<String> fieldList, List<Object> beanList) {
        if (ObjectUtils.isBlank(beanList)) {
            return;
        }
        Method[] methods = beanList.get(0).getClass().getMethods();
        List<Method> getterUsed = new ArrayList();
        for (int i = 0; i < fieldList.size(); i++) {
            String field = fieldList.get(i);
            boolean found = false;
            for (int j = 0; j < methods.length; j++) {
                Method method = methods[j];
                if (method.getName().equals(ClassUtils.buildGetMethodName(field, method.getReturnType()))) {
                    getterUsed.add(method);
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new BizException("属性未找到"+
                        beanList.get(0).getClass().getSimpleName()+":"+field);
            }
        }

        if (ObjectUtils.isBlank(getterUsed)) {
            return;
        }
        List<List<Object>> pageObj = new ArrayList<>();
        beanList.stream().forEach(bean -> {
            List<Object> rowObj = new ArrayList<>();
            for (int i = 0; i < getterUsed.size(); i++) {
                Object value;
                try {
                    value = getterUsed.get(i).invoke(bean);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    logger.error("处理字段属性异常",e);
                    throw new BizException("导出处理字段属性异常");
                }
                rowObj.add(value);
            }
            pageObj.add(rowObj);
        });
        this.pagingTableDataCollection = Arrays.asList(pageObj);
    }

    public String getTableTitle() {
        return tableTitle;
    }

    public List<T> getQryCondHeaderList() {
        return qryCondHeaderList;
    }

    public List<String> getTableHeaderList() {
        return tableHeaderList;
    }

    public List<List<List<Object>>> getPagingTableDataCollection() {
        return pagingTableDataCollection;
    }
}
