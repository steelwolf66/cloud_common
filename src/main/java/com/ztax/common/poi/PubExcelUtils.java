package com.ztax.common.poi;

import com.alibaba.excel.write.handler.CellWriteHandler;

import com.ztax.common.exception.BizException;
import com.ztax.common.utils.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PubExcelUtils {

    private static Logger logger = LoggerFactory.getLogger(PubExcelUtils.class);

    /**
     * 构建ExcelSheetTable表头
     *
     * @param tableDescriptor sheetTable描述器
     * @return 表头数据结构
     */
    public static final List<List<String>> buildExcelSheetTableHeader(PubExcelSheetTableDescriptor tableDescriptor) {
        int headerRowCount = tableDescriptor.getHeaderRowCount();
        int headerColumnCount = tableDescriptor.getHeaderColumnCount();
        List<PubExcelHeaderCellDescriptor> headerCellList = tableDescriptor.getHeaderCellList();

        // 表头结构
        String[][] headerConstruction = new String[headerColumnCount][headerRowCount];
        for (PubExcelHeaderCellDescriptor pubExcelHeaderCellDescriptor : headerCellList) {
            String headerCellContent = pubExcelHeaderCellDescriptor.getHeaderCellContent();
            // 起点坐标（左上角）
            Point startCoordinate = pubExcelHeaderCellDescriptor.getStartCoordinate();
            int startCoordinateX = (int) startCoordinate.getX();
            int startCoordinateY = (int) startCoordinate.getY();
            // 终点坐标（右下角）
            Point endCoordinate = pubExcelHeaderCellDescriptor.getEndCoordinate();
            int endCoordinateX = (int) endCoordinate.getX();
            int endCoordinateY = (int) endCoordinate.getY();

            if (startCoordinateX >= endCoordinateX || startCoordinateY >= endCoordinateY) {
                throw new BizException("invalid excel header coordinate");
            }
            for (int i = startCoordinateX; i < endCoordinateX; i++) {
                for (int j = startCoordinateY; j < endCoordinateY; j++) {
                    headerConstruction[i][j] = headerCellContent;
                }
            }
        }
        List<List<String>> header = new ArrayList<>();
        for (int i = 0; i < headerConstruction.length; i++) {
            List<String> colList = new ArrayList<>();
            for (int j = 0; j < headerConstruction[i].length; j++) {
                if (StringUtils.isBlank(headerConstruction[i][j])) {
                    headerConstruction[i][j] = StringUtils.EMPTY;
                }
                colList.add(j, headerConstruction[i][j]);
            }
            header.add(i, colList);
        }
        return header;
    }

    public static final int getLastRowNo(List<PubExcelHeaderCellDescriptor> headerCellList) {
        return CollectionUtils.isEmpty(headerCellList) ? 0 : (int) headerCellList.get(headerCellList.size() - 1).getEndCoordinate().getY();
    }

    public static final int getLastColNo(List<PubExcelHeaderCellDescriptor> headerCellList) {
        return CollectionUtils.isEmpty(headerCellList) ? 0 : (int) headerCellList.get(headerCellList.size() - 1).getEndCoordinate().getX();
    }

    public static final PubExcelSheetDescriptor convertSheetDescriptorSimple2Complex(PubExcelSheetSimpleDescriptor pubExcelSheetSimpleDescriptor) {
        String sheetName = pubExcelSheetSimpleDescriptor.getSheetName();
        PubExcelSheetTableSimpleDescriptor sheetTableSimpleDescriptor = pubExcelSheetSimpleDescriptor.getSheetTableSimpleDescriptor();
        if (StringUtils.isBlank(sheetName)) {
            sheetName = "Sheet1";
        }
        PubExcelSheetTableDescriptor sheetTableDescriptor = convertSheetTableDescriptorSimple2Complex(sheetTableSimpleDescriptor);
        PubExcelSheetDescriptor sheetDescriptor = new PubExcelSheetDescriptor();
        sheetDescriptor.setSheetName(sheetName);
        sheetDescriptor.setTableList(Arrays.asList(sheetTableDescriptor));
        return sheetDescriptor;
    }

    public static final <T> PubExcelSheetTableDescriptor buildSheetTableDescriptor(String tableTitle, List<T> qryCondHeaderList, List<String> tableHeaderList, List<List<List<Object>>> pagingTableDataCollection) {
        return convertSheetTableDescriptorSimple2Complex(new PubExcelSheetTableSimpleDescriptor(tableTitle, qryCondHeaderList, tableHeaderList, pagingTableDataCollection));
    }

    public static final <T> PubExcelSheetTableDescriptor convertSheetTableDescriptorSimple2Complex(PubExcelSheetTableSimpleDescriptor sheetTableSimpleDescriptor) {
        String tableTitle = sheetTableSimpleDescriptor.getTableTitle();
        List<T> qryCondHeaderList = sheetTableSimpleDescriptor.getQryCondHeaderList();
        List<String> tableHeaderList = sheetTableSimpleDescriptor.getTableHeaderList();
        List<List<List<Object>>> pagingTableDataCollection = sheetTableSimpleDescriptor.getPagingTableDataCollection();
        int dataColumnCount = 15;
        if (!CollectionUtils.isEmpty(pagingTableDataCollection)
                && !CollectionUtils.isEmpty(pagingTableDataCollection.get(0))
                && !CollectionUtils.isEmpty(pagingTableDataCollection.get(0).get(0))) {
            dataColumnCount = pagingTableDataCollection.get(0).get(0).size();
        }
        if (!CollectionUtils.isEmpty(tableHeaderList)) {
            dataColumnCount = tableHeaderList.size();
        }
        PubExcelSheetTableDescriptor sheetTableDescriptor = new PubExcelSheetTableDescriptor();
        sheetTableDescriptor.setPageDataList(pagingTableDataCollection);
        // 数据列数
        int headRowCount = 0;
        List<PubExcelHeaderCellDescriptor> headerCellList = new ArrayList<>();
        if (StringUtils.isNotBlank(tableTitle)) {
            int lastRowNo = getLastRowNo(headerCellList);
            PubExcelHeaderCellDescriptor headerCellDescriptor = new PubExcelHeaderCellDescriptor();
            headerCellDescriptor.setHeaderCellContent(tableTitle);
            headerCellDescriptor.setStartCoordinate(new Point(0, lastRowNo));
            headerCellDescriptor.setEndCoordinate(new Point(dataColumnCount, (lastRowNo + 1)));
            headerCellList.add(headerCellDescriptor);
            headRowCount++;
        }
        if (null != qryCondHeaderList && qryCondHeaderList.size() > 0) {
            int lastRowNo = getLastRowNo(headerCellList);
            if (qryCondHeaderList.stream().findFirst().get() instanceof String) {
                // 查询条件表头个数
                int qryCondHeaderCount = qryCondHeaderList.size();
                // 每一个表头占用的列数
                int perHeaderOccupyColCount = dataColumnCount / qryCondHeaderCount;
                for (int qryCondHeaderIndex = 0; qryCondHeaderIndex < qryCondHeaderCount; qryCondHeaderIndex++) {
                    PubExcelHeaderCellDescriptor qryCondHeaderDescriptor = new PubExcelHeaderCellDescriptor();
                    qryCondHeaderDescriptor.setHeaderCellContent((String) qryCondHeaderList.get(qryCondHeaderIndex));
                    qryCondHeaderDescriptor.setStartCoordinate(new Point(qryCondHeaderIndex * perHeaderOccupyColCount, lastRowNo));
                    qryCondHeaderDescriptor.setEndCoordinate(new Point((qryCondHeaderIndex + 1) * perHeaderOccupyColCount, (lastRowNo + 1)));
                    headerCellList.add(qryCondHeaderDescriptor);
                }
            } else if (qryCondHeaderList.stream().findFirst().get() instanceof PubExcelHeaderCellDescriptor) {
                headerCellList.addAll(headerCellList);
            }
            headRowCount++;
        }
        if (!CollectionUtils.isEmpty(tableHeaderList)) {
            int lastRowNo = getLastRowNo(headerCellList);
            for (int colIndex = 0; colIndex < tableHeaderList.size(); colIndex++) {
                PubExcelHeaderCellDescriptor headerDescriptor = new PubExcelHeaderCellDescriptor();
                headerDescriptor.setHeaderCellContent(tableHeaderList.get(colIndex));
                headerDescriptor.setStartCoordinate(new Point(colIndex, lastRowNo));
                headerDescriptor.setEndCoordinate(new Point(colIndex + 1, (lastRowNo + 1)));
                headerCellList.add(headerDescriptor);
            }
            headRowCount++;
        }
        if (headRowCount == 0) {
            sheetTableDescriptor.setNeedHeader(Boolean.FALSE);
            sheetTableDescriptor.setHeaderRowCount(0);
            sheetTableDescriptor.setHeaderColumnCount(0);
        } else {
            sheetTableDescriptor.setNeedHeader(Boolean.TRUE);
            sheetTableDescriptor.setHeaderRowCount(headRowCount);
            sheetTableDescriptor.setHeaderColumnCount(getLastColNo(headerCellList));
            sheetTableDescriptor.setHeaderCellList(headerCellList);
        }
        return sheetTableDescriptor;
    }

    /**
     * 构建空行
     *
     * @return
     */
    public static final PubExcelSheetTableDescriptor buildBlankRow() {
        List<List<List<Object>>> emptyData = Arrays.asList(Arrays.asList(Arrays.asList(StringUtils.EMPTY)));
        return convertSheetTableDescriptorSimple2Complex(
                new PubExcelSheetTableSimpleDescriptor<>(
                        null,
                        null,
                        null,
                        emptyData));
    }

    /**
     * 构建单行标题
     *
     * @return
     */
    public static final PubExcelSheetTableDescriptor buildSimpleTitle(String title) {
        return buildSimpleTitle(title, null);
    }

    /**
     * 构建单行标题
     *
     * @return
     */
    public static final PubExcelSheetTableDescriptor buildSimpleTitle(String title, List<CellWriteHandler> cellWriteHandlerChain) {
        PubExcelSheetTableDescriptor tableDescriptor =
                convertSheetTableDescriptorSimple2Complex(
                        new PubExcelSheetTableSimpleDescriptor<>(
                                title,
                                null,
                                null,
                                null));
        tableDescriptor.setCellWriteHandlerChain(cellWriteHandlerChain);
        return tableDescriptor;
    }

    /**
     * 构建单个表头
     */
    public static final PubExcelSheetTableDescriptor buildSingleHeader(String headerName) {
        return buildSingleHeader(headerName, null);
    }

    /**
     * 构建单个表头
     */
    public static final PubExcelSheetTableDescriptor buildSingleHeader(String headerName, List<CellWriteHandler> cellWriteHandlerChain) {
        PubExcelSheetTableDescriptor tableDescriptor = convertSheetTableDescriptorSimple2Complex(
                new PubExcelSheetTableSimpleDescriptor<>(
                        null,
                        Arrays.asList(headerName),
                        null,
                        null));
        tableDescriptor.setCellWriteHandlerChain(cellWriteHandlerChain);
        return tableDescriptor;
    }

    /**
     * 将查询结果构造成可导出的格式 List<List<List<Object>>>
     * @param resultListToProduce
     * @param fieldNameList
     * @param <T>
     * @return
     */
    public static <T> List<List<List<Object>>> produceResultForExport(List<T> resultListToProduce, List<String> fieldNameList) {
        List<List<List<Object>>> pagingTableDataCollection = new ArrayList<>();

        //校验
        if (ObjectUtils.isBlank(resultListToProduce) || ObjectUtils.isBlank(fieldNameList)) {
            logger.error("处理导出数据错误，导出数据和导出属性名不可为空");
            return pagingTableDataCollection;
        }

        List<List<Object>> resultList = new ArrayList<>();

        List<Object> tempList;

        //查询结果类型为map
        if(resultListToProduce.get(0) instanceof Map){

            for (Map<String, Object> tempMap : (List<Map>)resultListToProduce) {
                tempList = new ArrayList<>();

                for (String field : fieldNameList) {

                    tempList.add(tempMap.getOrDefault(field, ""));
                }

                resultList.add(tempList);
            }

            //对象
        }else{
            for (T item : resultListToProduce) {
                tempList = new ArrayList<>();
                for (String field : fieldNameList) {
                    try {
                        Object fieldValue = ObjectUtils.getFieldValue(item, field);
                        if(ObjectUtils.isBlank(fieldValue)){
                            fieldValue = " ";
                        }
                        tempList.add(fieldValue);
                    } catch (Exception e) {
                        logger.error("获取对象属性值异常");
                        tempList.add(" ");
                    }
                }
                resultList.add(tempList);
            }
        }
        pagingTableDataCollection.add(resultList);

        return pagingTableDataCollection;
    }
}
